package com.yi_tian.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ModEvents {

    public static void registerModEvents() {
        ShatterEnchantmentHandler.register();//破碎附魔
    }
}