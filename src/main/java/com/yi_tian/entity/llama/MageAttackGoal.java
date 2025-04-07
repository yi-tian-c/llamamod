package com.yi_tian.entity.llama;

import com.yi_tian.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;
import java.util.Random;



public class MageAttackGoal extends Goal {
    private final LlamaMageEntity mage;
    private LivingEntity target;
    private int attackStep;
    private int attackCooldown;
    private int spitattackCooldown;

    public MageAttackGoal(LlamaMageEntity mage) {
        this.mage = mage;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
//            this.target = mage.getTarget();
//            return target != null && target.isAlive() && mage.canTarget(target);
        this.target = mage.getTarget();
        if(this.target!=null){
            if (this.target instanceof PlayerEntity player){
                ItemStack helmet = player.getInventory().getArmorStack(3);
                if(helmet.getItem() == ModItems.LLAMA_SKULL){
                    this.target=null;
                }
            }
        }
        return target != null
                && !(target instanceof LlamaMageEntity) // 排除同类作为目标
                && target.isAlive()
                && mage.canTarget(target);
    }

    @Override
    public void start() {
        this.attackStep = 0;
        this.attackCooldown = 0;
        this.spitattackCooldown=0;
    }

    @Override
    public void tick() {
        double distanceSq = mage.squaredDistanceTo(target);
        boolean canSee = mage.getVisibilityCache().canSee(target);

        // 根据距离切换行为
        if (distanceSq > 10 * 10) { // 超过10格：追击玩家
            mage.getNavigation().startMovingTo(target, 1.0);
        }
        if (distanceSq <= 3 * 3 && canSee) { // 3格内：近战攻击
            if (attackCooldown <= 0) {
                mage.tryAttack(target);
                attackCooldown = 20; // 近战冷却1秒
            }
        }
        if (distanceSq < 10 * 10&&canSee) { // 3~12格：远程魔法攻击
            mage.getLookControl().lookAt(target, 30.0F, 30.0F);
//            mage.getNavigation().startMovingTo(target, -0.5); // 停止移动以专注射击
            Random rand = new Random();
            mage.setLlamaMageState(rand.nextInt(3));
            mage.getNavigation().stop();
            if (spitattackCooldown <= 0) {
                mage.setAngerTime(400);
                mage.spitAt(target);
                spitattackCooldown = 15; // 远程冷却0.75秒
            }
        }

        if (attackCooldown > 0) {
            attackCooldown--;
        }
        if(spitattackCooldown>0){
            spitattackCooldown--;
        }
    }

}