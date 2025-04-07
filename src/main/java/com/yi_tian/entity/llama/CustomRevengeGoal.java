package com.yi_tian.entity.llama;

import com.yi_tian.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class CustomRevengeGoal extends RevengeGoal {


    public CustomRevengeGoal(PathAwareEntity mob, Class<?>... noRevengeTypes) {
        super(mob, noRevengeTypes);
    }

    @Override
    public boolean canTrack(LivingEntity attacker, TargetPredicate predicate) {
        // 检查攻击者是否戴了羊驼头颅
        if (attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            ItemStack helmet = player.getInventory().getArmorStack(3);

            if (helmet.getItem() ==ModItems.LLAMA_SKULL) {
                return false; // 不触发仇恨
            }
        }
        return super.canTrack(attacker, predicate);
    }
}