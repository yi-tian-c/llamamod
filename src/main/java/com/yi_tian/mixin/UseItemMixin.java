package com.yi_tian.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class UseItemMixin {
    @Shadow public abstract TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand);

    @Inject(
            method = "use",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack stack = user.getStackInHand(hand);

        // 检查是否为金锭
        if (stack.getItem() == Items.GOLD_INGOT
                ||stack.getItem() == Items.IRON_INGOT
                ||stack.getItem() == Items.COPPER_INGOT
                ||stack.getItem() == Items.COBBLESTONE
                ||stack.getItem() == Items.DIAMOND
                ||stack.getItem() == Items.REDSTONE
                ||stack.getItem()==Items.LAPIS_LAZULI
                ||stack.getItem()==Items.NETHERITE_INGOT
                ||stack.getItem()==Items.BEDROCK
                ||stack.getItem()==Items.EMERALD
                ||stack.getItem()==Items.COAL) {
            // 检查是否穿戴了钻石头盔
                user.setCurrentHand(hand);
                cir.setReturnValue(TypedActionResult.consume(stack));
        }
    }
    @Inject(
            method = "finishUsing",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onFinishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        // 检查是否为金锭
        if ((stack.getItem() == Items.GOLD_INGOT
                ||stack.getItem() == Items.IRON_INGOT
                ||stack.getItem() == Items.COPPER_INGOT
                ||stack.getItem() == Items.COBBLESTONE
                ||stack.getItem() == Items.DIAMOND
                ||stack.getItem() == Items.REDSTONE
                ||stack.getItem()==Items.LAPIS_LAZULI
                ||stack.getItem()==Items.NETHERITE_INGOT
                ||stack.getItem()==Items.BEDROCK
                ||stack.getItem()==Items.EMERALD
                ||stack.getItem()==Items.COAL)  && user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;

            // 添加效果，例如恢复饥饿值
            Item m_Item=stack.getItem();
            if(m_Item==Items.GOLD_INGOT){
                player.getHungerManager().add(6, 0.8F);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1,false,false,true));
            }
            else if(m_Item==Items.IRON_INGOT){
                player.getHungerManager().add(4, 0.5F);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 1800, 0,false,false,true));
            }
            else if(m_Item==Items.COBBLESTONE){
                player.getHungerManager().add(2, 0.5F);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, 0,false,false,true));
            }
            else if(m_Item==Items.REDSTONE){
                player.getHungerManager().add(2, 0.5F);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 2400, 0,false,false,true));
            }
            else if(m_Item==Items.DIAMOND){
                player.getHungerManager().add(8, 0.8F);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 2400, 1,false,false,true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 2400, 1,false,false,true));
            }
            else if(m_Item==Items.LAPIS_LAZULI){
                player.getHungerManager().add(2, 0.5F);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 2400, 0,false,false,true));
            }
            else if(m_Item==Items.COPPER_INGOT){
                player.getHungerManager().add(4, 0.5F);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 300, 0,false,false,true));
            }
            else if(m_Item==Items.NETHERITE_INGOT){
                player.getHungerManager().add(20, 0.8F);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 400, 2,false,false,true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 4800, 4,false,false,true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 9600, 0,false,false,true));
            }
            else if(m_Item==Items.BEDROCK){
                player.getHungerManager().add(20, 1.0F);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1000000000, 9,false,false,true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 1000000000, 4,false,false,true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1000000000, 4,false,false,true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 1000000000, 255,false,false,true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 1000000000, 255,false,false,true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 1000000000, 4,false,false,true));
            }
            else if(m_Item==Items.EMERALD){
                player.getHungerManager().add(4, 0.5F);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, 2400, 0,false,false,true));
            }
            else if(m_Item==Items.COAL){
                player.getHungerManager().add(2, 0.5F);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 200, 1,false,false,true));
            }
            // 消耗金锭
            if (!player.isCreative()) {
                stack.decrement(1);
            }

            // 返回结果
            cir.setReturnValue(stack);
        }
    }

    @Inject(
            method = "getMaxUseTime",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onGetMaxUseTime(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        // 检查是否为金锭
        if (stack.getItem() == Items.GOLD_INGOT
                ||stack.getItem() == Items.IRON_INGOT
                ||stack.getItem() == Items.COPPER_INGOT
                ||stack.getItem() == Items.COBBLESTONE
                ||stack.getItem() == Items.DIAMOND
                ||stack.getItem() == Items.REDSTONE
                ||stack.getItem()==Items.LAPIS_LAZULI
                ||stack.getItem()==Items.NETHERITE_INGOT
                ||stack.getItem()==Items.BEDROCK
                ||stack.getItem()==Items.EMERALD
                ||stack.getItem()==Items.COAL ) {
            cir.setReturnValue(32); // 设置使用时间为 32 ticks（1.6 秒）
        }
    }

    @Inject(
            method = "getUseAction",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onGetUseAction(ItemStack stack, CallbackInfoReturnable<UseAction> cir) {
        // 检查是否为金锭
        if (stack.getItem() == Items.GOLD_INGOT
                ||stack.getItem() == Items.IRON_INGOT
                ||stack.getItem() == Items.COPPER_INGOT
                ||stack.getItem() == Items.COBBLESTONE
                ||stack.getItem() == Items.DIAMOND
                ||stack.getItem() == Items.REDSTONE
                ||stack.getItem()==Items.LAPIS_LAZULI
                ||stack.getItem()==Items.NETHERITE_INGOT
                ||stack.getItem()==Items.BEDROCK
                ||stack.getItem()==Items.EMERALD
                ||stack.getItem()==Items.COAL ) {
            cir.setReturnValue(UseAction.EAT); // 设置使用动作为“吃”
        }
    }
}

