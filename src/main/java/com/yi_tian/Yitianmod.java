package com.yi_tian;

import com.yi_tian.enchantment.ModEnchantments;
import com.yi_tian.entity.ModEntities;
import com.yi_tian.event.ModEvents;
import com.yi_tian.item.ModItemGroup;
import com.yi_tian.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class Yitianmod implements ModInitializer {
	public static final String MOD_ID = "yitianllama-mod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static String message = null; // 当前显示的提示信息
	private static int remainingTicks = 0; // 剩余显示时间（ticks）
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModItems.registerModItems(); // 注册自定义物品
		ModItemGroup.registerModItemsGroup(); // 注册自定义物品组
		ModEnchantments.registerModEnchantments(); // 注册自定义附魔
		ModEntities.registerEntities(); // 注册自定义实体
		ModEntities.registerAttributes(); // 注册实体属性
		GeckoLib.initialize();
		AttackEntityCallback.EVENT.register(this::onAttackEntity);
		ModEvents.registerModEvents(); // 注册所有事件处理器
		HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
			if (message != null && remainingTicks > 0) {
				MinecraftClient client = MinecraftClient.getInstance();
				int x = (int)(client.getWindow().getScaledWidth() / 2.2)-10;
				int y = client.getWindow().getScaledHeight() / 2 + 80;
				drawContext.drawText(client.textRenderer, Text.of(message), x, y, 0x5FFFFF, true);
			}
		});
		// 注册 ClientTickEvents 更新计时器
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (remainingTicks > 0) {
				remainingTicks--;
				if (remainingTicks == 0) {
					message = null; // 时间到达后清除提示信息
				}
			}
		});
		LOGGER.info("Hello Fabric world!");
	}
	private ActionResult onAttackEntity(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
		LOGGER.info("在调用");
		if (entity instanceof LivingEntity) {
			LOGGER.info("在攻击");
			ItemStack stack = player.getStackInHand(hand);

			// 检查武器是否带有 Shatter 附魔
			if (EnchantmentHelper.getLevel(ModEnchantments.SHATTER, stack) > 0) {
				// 检查是否处于冷却时间
				LOGGER.info("还在冷却");
				if (player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
					// 如果处于冷却时间，取消攻击并显示提示信息
					message = "破碎附魔正在冷却！";
					remainingTicks = 40; // 40 ticks = 2 秒
					return ActionResult.FAIL; // 取消攻击
				}

				// 设置冷却时间
				player.getItemCooldownManager().set(stack.getItem(), 100);
			}
		}

		// 允许攻击
		return ActionResult.PASS;
	}
}