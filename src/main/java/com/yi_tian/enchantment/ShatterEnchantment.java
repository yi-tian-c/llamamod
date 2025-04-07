package com.yi_tian.enchantment;

import com.yi_tian.item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class ShatterEnchantment extends Enchantment {

    public ShatterEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        // 只允许黑曜石剑斧类物品接受该附魔
        return stack.getItem() == ModItems.OBSIDIAN_SWORD||stack.getItem() == ModItems.OBSIDIAN_AXE;
    }
    @Override
    public int getMinPower(int level) {
        return 10; // 附魔所需的最低附魔等级
    }

    @Override
    public int getMaxPower(int level) {
        return 50; // 附魔所需的最高附魔等级
    }

    @Override
    public int getMaxLevel() {
        return 3; // 附魔的最大等级
    }

    @Override
    public boolean isTreasure() {
        return false; // 是否只能在宝箱或交易中获得
    }

    @Override
    public boolean isCursed() {
        return false; // 是否为诅咒附魔
    }
}
