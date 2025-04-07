package com.yi_tian.item;

import com.yi_tian.Yitianmod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    // 定义并注册自定义物品组
    public static final ItemGroup MAO_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Yitianmod.MOD_ID, "mao_group"), // 注册物品组ID
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemGroup.mao_group"))  // 设置物品组名称
                    .icon(() -> new ItemStack(ModItems.OBSIDIAN_INGOT)) // 设置物品组图标
                    .entries((displayContext, entries) -> { // 添加物品到物品组中
                        entries.add(ModItems.OBSIDIAN_INGOT);
                        entries.add(ModItems.OBSIDIAN_AXE);
                        entries.add(ModItems.OBSIDIAN_HOE);
                        entries.add(ModItems.OBSIDIAN_PICKAXE);
                        entries.add(ModItems.OBSIDIAN_SHOVEL);
                        entries.add(ModItems.OBSIDIAN_SWORD);
                        entries.add(ModItems.LLAMA_MATE_EGG);
                        entries.add(ModItems.LLAMA_SKULL);
                        entries.add(ModItems.WANDERING_LLAMA_EGG);
                        entries.add(ModItems.VILLAGER_LLAMA_EGG);
                        entries.add(ModItems.POS_SPIT);
                        entries.add(ModItems.EXP_SPIT);
                        entries.add(ModItems.SPIT);
                        entries.add(ModItems.SICK_SPIT);
                        entries.add(ModItems.SPIT_EXTRACTOR);
                    }).build());

    // 注册物品组的方法
    public static void registerModItemsGroup() {
    }
}
