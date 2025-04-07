package com.yi_tian.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.yi_tian.Yitianmod.MOD_ID;

public class ModEnchantments {
    public static final Enchantment SHATTER=new ShatterEnchantment();
    // 注册自定义附魔的方法
    public static void registerModEnchantments() {
        Registry.register(Registries.ENCHANTMENT,new Identifier(MOD_ID,"shatter"),SHATTER);// 注册破碎附魔

    }
}
