package com.yi_tian.item;

import com.yi_tian.Yitianmod;
import com.yi_tian.entity.ModEntities;
import com.yi_tian.item.custom.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModItems {
    // 定义并注册自定义物品
    //黑曜石工具
    public static final Item OBSIDIAN_INGOT=registerItems("obsidian_ingot",new Item(new Item.Settings()));
    public static final Item OBSIDIAN_AXE=registerItems("obsidian_axe",new AxeItem(ModToolMaterials.OBSIDIAN_MATERIAL,5,1.0F,new Item.Settings()));
    public static final Item OBSIDIAN_SWORD=registerItems("obsidian_sword",new SwordItem(ModToolMaterials.OBSIDIAN_MATERIAL,3,-2.4F,new Item.Settings()));
    public static final Item OBSIDIAN_PICKAXE=registerItems("obsidian_pickaxe",new PickaxeItem(ModToolMaterials.OBSIDIAN_MATERIAL,1,-3.0F,new Item.Settings()));
    public static final Item OBSIDIAN_HOE=registerItems("obsidian_hoe",new HoeItem(ModToolMaterials.OBSIDIAN_MATERIAL,-4,0.0F,new Item.Settings()));
    public static final Item OBSIDIAN_SHOVEL=registerItems("obsidian_shovel",new ShovelItem(ModToolMaterials.OBSIDIAN_MATERIAL,2,-3.0F,new Item.Settings()));
    //生物头颅
    public static final Item LLAMA_SKULL=registerItems("llama_skull",new HatItem(new Item.Settings().maxDamage(500),HatItem.Type.HELMET));
    //口水
    public static final Item SPIT = registerItems("spit",new SpitItem(new Item.Settings()));
    public static final Item POS_SPIT = registerItems("pos_spit",new PoisonousSpitItem(new Item.Settings()));
    public static final Item EXP_SPIT=registerItems("exp_spit",new ExplosionSpitItem(new Item.Settings()));
    public static final Item SICK_SPIT=registerItems("sick_spit",new SickSpitItem(new Item.Settings()));
    //生物蛋
    public static final Item LLAMA_MATE_EGG=registerItems("llama_mate_egg",new SpawnEggItem(ModEntities.LLAMA_MAGE,0x252525,0x4d4d4d,new Item.Settings()));
    public static final Item WANDERING_LLAMA_EGG=registerItems("wandering_llama_egg",new SpawnEggItem(ModEntities.WARDERING_LLAMA,0x252525,0x233426,new Item.Settings()));
    public static final Item VILLAGER_LLAMA_EGG=registerItems("villager_llama_egg",new SpawnEggItem(ModEntities.VILLAGER_LLAMA,0x121212,0x212132,new Item.Settings()));
    //提取器
    public static final Item SPIT_EXTRACTOR=registerItems("spit_extractor",new SpitExtractorItem(new Item.Settings()));
    // 将自定义物品添加到物品组中
    private static void addItemsToItemGroup(FabricItemGroupEntries fabricItemGroupEntries) {
        fabricItemGroupEntries.add(OBSIDIAN_AXE);
        fabricItemGroupEntries.add(OBSIDIAN_SWORD);
        fabricItemGroupEntries.add(OBSIDIAN_PICKAXE);
        fabricItemGroupEntries.add(OBSIDIAN_HOE);
        fabricItemGroupEntries.add(OBSIDIAN_SHOVEL);
        fabricItemGroupEntries.add(OBSIDIAN_INGOT);
        fabricItemGroupEntries.add(LLAMA_SKULL);
        fabricItemGroupEntries.add(SPIT);
        fabricItemGroupEntries.add(POS_SPIT);
        fabricItemGroupEntries.add(EXP_SPIT);
        fabricItemGroupEntries.add(SICK_SPIT);
        fabricItemGroupEntries.add(LLAMA_MATE_EGG);
        fabricItemGroupEntries.add(WANDERING_LLAMA_EGG);
        fabricItemGroupEntries.add(VILLAGER_LLAMA_EGG);
        fabricItemGroupEntries.add(SPIT_EXTRACTOR);
    }

    // 注册单个物品的方法
    public static Item registerItems(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Yitianmod.MOD_ID, name), item);
    }

    // 注册所有自定义物品的方法，并将它们添加到指定的物品组中
    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToItemGroup);
    }
    public static Item registerItemBlock(BlockItem item) {
        return register((Block)item.getBlock(), (Item)item);
    }
    public static Item register(Block block, Item item) {
        return register(Registries.BLOCK.getId(block), item);
    }
    public static Item register(Identifier id, Item item) {
        return register(RegistryKey.of(Registries.ITEM.getKey(), id), item);
    }
    public static Item register(RegistryKey<Item> key, Item item) {
        if (item instanceof BlockItem) {
            ((BlockItem)item).appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return (Item)Registry.register(Registries.ITEM, key, item);
    }

}
