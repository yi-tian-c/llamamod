package com.yi_tian.event;

import com.yi_tian.enchantment.ModEnchantments;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class ShatterEnchantmentHandler implements AttackEntityCallback {
    public static void register(){
        AttackEntityCallback.EVENT.register(new ShatterEnchantmentHandler());
    }
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity,@Nullable EntityHitResult hitResult) {
        if (entity instanceof LivingEntity) {
            ItemStack stack = player.getStackInHand(hand);
            // 检查武器是否附有 Shatter 附魔
            if (EnchantmentHelper.getLevel(ModEnchantments.SHATTER, stack) > 0) {
                // 检查是否为跳劈（暴击攻击）
                if (player.fallDistance > 0.0F && !player.isOnGround() && !player.isClimbing() && !player.isTouchingWater() && !player.hasStatusEffect(net.minecraft.entity.effect.StatusEffects.BLINDNESS)) {
                    // 触发 5 倍伤害
                    LivingEntity target = (LivingEntity) entity;
                    // 获取玩家的攻击伤害（包括武器伤害和附魔加成）
                    float attackDamage = (float) player.getAttributeValue(net.minecraft.entity.attribute.EntityAttributes.GENERIC_ATTACK_DAMAGE);

                    // 计算此次攻击的实际伤害（包括攻击冷却进度）
                    float attackCooldownProgress = player.getAttackCooldownProgressPerTick();
                    float actualDamage = attackDamage * player.getAttackCooldownProgress(0.5F);
                    Vec3d m_pos = target.getPos(); // 目标生物的位置
                    int particleCount = 36; // 粒子数量
                    double radius = 2.0; // 粒子扩散半径
                    // 定义紫色 RGB 颜色
                    float red = 1.0F; // 红色分量
                    float green = 0.0F; // 绿色分量
                    float blue = 1.0F; // 蓝色分量
                    float scale = 1.0F; // 粒子大小

// 创建 DustParticleEffect
                    DustParticleEffect m_effect = new DustParticleEffect(new Vector3f(red, green, blue), scale);
                    for (int i = 0; i < particleCount; i++) {
                        double angle = 2 * Math.PI * i / particleCount; // 计算角度
                        double x = m_pos.x + radius * Math.cos(angle); // 计算粒子 x 坐标
                        double z = m_pos.z + radius * Math.sin(angle); // 计算粒子 z 坐标

                        // 生成粒子
                        world.addParticle(m_effect, x, m_pos.y+1, z, 0, 0, 0);
                    }
                    DustParticleEffect m_effect2 = new DustParticleEffect(new Vector3f(0.0f, 0.0f, 0.0f), scale);
                    for (int i = 0; i < particleCount; i++) {
                        double angle = 2 * Math.PI * i / particleCount+5; // 计算角度
                        double x = m_pos.x + radius * Math.cos(angle); // 计算粒子 x 坐标
                        double z = m_pos.z + radius * Math.sin(angle); // 计算粒子 z 坐标

                        // 生成粒子
                        world.addParticle(m_effect2, x, m_pos.y+1, z, 0, 0, 0);
                    }
                    for (int i = 0; i < particleCount; i++) {
                        double angle = 2 * Math.PI * i / particleCount;
                        double x = m_pos.x + radius * Math.cos(angle);
                        double z = m_pos.z + radius * Math.sin(angle);

                        // 模拟粒子运动轨迹
                        for (double t = 0; t < radius; t += 0.1) {
                            double particleX = m_pos.x + t * Math.cos(angle);
                            double particleZ = m_pos.z + t * Math.sin(angle);

                            // 检测粒子路径上的生物
                            Box box = new Box(particleX - 0.5, m_pos.y - 0.5, particleZ - 0.5, particleX + 0.5, m_pos.y + 0.5, particleZ + 0.5);
                            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, box, e -> e != player && e != target);

                            // 对检测到的生物造成伤害
                            for (LivingEntity m_entity : entities) {
                                m_entity.damage(world.getDamageSources().playerAttack(player), 10.0F); // 造成 5 点伤害
                            }
                        }
                    }
                    // 在粒子生成后，检测粒子路径上的方块
                    for (int i = 0; i < particleCount; i++) {
                        double angle = 2 * Math.PI * i / particleCount;
                        double x = m_pos.x + radius * Math.cos(angle);
                        double z = m_pos.z + radius * Math.sin(angle);

                        // 模拟粒子运动轨迹
                        for (double t = 0; t < radius; t += 0.1) {
                            double particleX = m_pos.x + t * Math.cos(angle);
                            double particleZ = m_pos.z + t * Math.sin(angle);

                            // 获取粒子当前位置的方块
                            BlockPos blockPos = new BlockPos((int) particleX, (int) m_pos.y, (int) particleZ);
                            BlockPos blockPos2 = new BlockPos((int) particleX, (int) m_pos.y-1, (int) particleZ);
                            BlockState blockState = world.getBlockState(blockPos);
                            BlockState blockState2 = world.getBlockState(blockPos2);

                            // 如果方块不是空气，则破坏方块
                            if (!blockState.isAir()) {
                                world.breakBlock(blockPos, true, player); // 破坏方块并掉落物品
                            }
                            // 如果方块不是空气，则破坏方块
                            if (!blockState2.isAir()) {
                                world.breakBlock(blockPos2, true, player); // 破坏方块并掉落物品
                            }
                        }
                    }
                    if(EnchantmentHelper.getLevel(ModEnchantments.SHATTER, stack) == 1){
                        // 计算额外伤害（4 倍伤害）
                        float extraDamage = actualDamage * 4.0F;

                        // 应用额外伤害
                        target.damage(world.getDamageSources().playerAttack(player), extraDamage);

                        // 减少武器 50 点耐久度
                        stack.damage(50, player, (p) -> p.sendToolBreakStatus(hand));
                    }
                    else if(EnchantmentHelper.getLevel(ModEnchantments.SHATTER, stack) == 2){
                        // 计算额外伤害（7 倍伤害）
                        float extraDamage = actualDamage * 7.0F;
                        particleCount = 72; // 粒子数量
                        radius = 4.0; // 粒子扩散半径
                        DustParticleEffect m_effect3 = new DustParticleEffect(new Vector3f(1.0f, 0.0f, 1.0f), 1.0f);
                        for (int i = 0; i < particleCount; i++) {
                            double angle = 2 * Math.PI * i / particleCount; // 计算角度
                            double x = m_pos.x + radius * Math.cos(angle); // 计算粒子 x 坐标
                            double z = m_pos.z + radius * Math.sin(angle); // 计算粒子 z 坐标

                            // 生成粒子
                            world.addParticle(m_effect3, x, m_pos.y+1, z, 0, 0, 0);
                        }
                        DustParticleEffect m_effect4 = new DustParticleEffect(new Vector3f(0.0f, 0.0f, 0.0f), 1.0f);
                        for (int i = 0; i < particleCount; i++) {
                            double angle = 2 * Math.PI * i / particleCount+5; // 计算角度
                            double x = m_pos.x + radius * Math.cos(angle); // 计算粒子 x 坐标
                            double z = m_pos.z + radius * Math.sin(angle); // 计算粒子 z 坐标

                            // 生成粒子
                            world.addParticle(m_effect3, x, m_pos.y+1, z, 0, 0, 0);
                        }
                        for (int i = 0; i < particleCount; i++) {
                            double angle = 2 * Math.PI * i / particleCount;
                            double x = m_pos.x + radius * Math.cos(angle);
                            double z = m_pos.z + radius * Math.sin(angle);

                            // 模拟粒子运动轨迹
                            for (double t = 0; t < radius; t += 0.1) {
                                double particleX = m_pos.x + t * Math.cos(angle);
                                double particleZ = m_pos.z + t * Math.sin(angle);

                                // 检测粒子路径上的生物
                                Box box = new Box(particleX - 0.5, m_pos.y - 0.5, particleZ - 0.5, particleX + 0.5, m_pos.y + 0.5, particleZ + 0.5);
                                List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, box, e -> e != player && e != target);

                                // 对检测到的生物造成伤害
                                for (LivingEntity m_entity : entities) {
                                    m_entity.damage(world.getDamageSources().playerAttack(player), 20.0F); // 造成 5 点伤害
                                }
                            }
                        }
                        // 在粒子生成后，检测粒子路径上的方块
                        for (int i = 0; i < particleCount; i++) {
                            double angle = 2 * Math.PI * i / particleCount;
                            double x = m_pos.x + radius * Math.cos(angle);
                            double z = m_pos.z + radius * Math.sin(angle);

                            // 模拟粒子运动轨迹
                            for (double t = 0; t < radius; t += 0.1) {
                                double particleX = m_pos.x + t * Math.cos(angle);
                                double particleZ = m_pos.z + t * Math.sin(angle);

                                // 获取粒子当前位置的方块
                                BlockPos blockPos = new BlockPos((int) particleX, (int) m_pos.y, (int) particleZ);
                                BlockPos blockPos2 = new BlockPos((int) particleX, (int) m_pos.y-1, (int) particleZ);
                                BlockState blockState = world.getBlockState(blockPos);
                                BlockState blockState2 = world.getBlockState(blockPos2);

                                // 如果方块不是空气，则破坏方块
                                if (!blockState.isAir()) {
                                    world.breakBlock(blockPos, true, player); // 破坏方块并掉落物品
                                }
                                // 如果方块不是空气，则破坏方块
                                if (!blockState2.isAir()) {
                                    world.breakBlock(blockPos2, true, player); // 破坏方块并掉落物品
                                }
                            }
                        }
                        // 应用额外伤害
                        target.damage(world.getDamageSources().playerAttack(player), extraDamage);

                        // 减少武器 50 点耐久度
                        stack.damage(100, player, (p) -> p.sendToolBreakStatus(hand));
                    }
                    else{
                        // 计算额外伤害（10 倍伤害）
                        float extraDamage = actualDamage * 10.0F;
                        particleCount = 72; // 粒子数量
                        radius = 4.0; // 粒子扩散半径
                        for (int i = 0; i < particleCount; i++) {
                            double angle = 2 * Math.PI * i / particleCount; // 计算角度
                            double x = m_pos.x + radius * Math.cos(angle); // 计算粒子 x 坐标
                            double z = m_pos.z + radius * Math.sin(angle); // 计算粒子 z 坐标

                            // 生成粒子
                            world.addParticle(ParticleTypes.DRAGON_BREATH, x, m_pos.y+1, z, 0, 0, 0);
                        }
                        for (int i = 0; i < particleCount; i++) {
                            double angle = 2 * Math.PI * i / particleCount;
                            double x = m_pos.x + radius * Math.cos(angle);
                            double z = m_pos.z + radius * Math.sin(angle);

                            // 模拟粒子运动轨迹
                            for (double t = 0; t < radius; t += 0.1) {
                                double particleX = m_pos.x + t * Math.cos(angle);
                                double particleZ = m_pos.z + t * Math.sin(angle);

                                // 检测粒子路径上的生物
                                Box box = new Box(particleX - 0.5, m_pos.y - 0.5, particleZ - 0.5, particleX + 0.5, m_pos.y + 0.5, particleZ + 0.5);
                                List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, box, e -> e != player && e != target);

                                // 对检测到的生物造成伤害
                                for (LivingEntity m_entity : entities) {
                                    m_entity.damage(world.getDamageSources().playerAttack(player), 20.0F); // 造成 5 点伤害
                                }
                            }
                        }
                        // 在粒子生成后，检测粒子路径上的方块
                        for (int i = 0; i < particleCount; i++) {
                            double angle = 2 * Math.PI * i / particleCount;
                            double x = m_pos.x + radius * Math.cos(angle);
                            double z = m_pos.z + radius * Math.sin(angle);

                            // 模拟粒子运动轨迹
                            for (double t = 0; t < radius; t += 0.1) {
                                double particleX = m_pos.x + t * Math.cos(angle);
                                double particleZ = m_pos.z + t * Math.sin(angle);

                                // 获取粒子当前位置的方块
                                BlockPos blockPos = new BlockPos((int) particleX, (int) m_pos.y, (int) particleZ);
                                BlockPos blockPos2 = new BlockPos((int) particleX, (int) m_pos.y-1, (int) particleZ);
                                BlockState blockState = world.getBlockState(blockPos);
                                BlockState blockState2 = world.getBlockState(blockPos2);

                                // 如果方块不是空气，则破坏方块
                                if (!blockState.isAir()) {
                                    world.breakBlock(blockPos, true, player); // 破坏方块并掉落物品
                                }
                                // 如果方块不是空气，则破坏方块
                                if (!blockState2.isAir()) {
                                    world.breakBlock(blockPos2, true, player); // 破坏方块并掉落物品
                                }
                            }
                        }
                        particleCount = 108; // 粒子数量
                        radius = 6.0; // 粒子扩散半径
                        for (int i = 0; i < particleCount; i++) {
                            double angle = 2 * Math.PI * i / particleCount; // 计算角度
                            double x = m_pos.x + radius * Math.cos(angle); // 计算粒子 x 坐标
                            double z = m_pos.z + radius * Math.sin(angle); // 计算粒子 z 坐标

                            // 生成粒子
                            world.addParticle(ParticleTypes.DRAGON_BREATH, x, m_pos.y+1, z, 0, 0, 0);
                        }
                        for (int i = 0; i < particleCount; i++) {
                            double angle = 2 * Math.PI * i / particleCount;
                            double x = m_pos.x + radius * Math.cos(angle);
                            double z = m_pos.z + radius * Math.sin(angle);

                            // 模拟粒子运动轨迹
                            for (double t = 0; t < radius; t += 0.1) {
                                double particleX = m_pos.x + t * Math.cos(angle);
                                double particleZ = m_pos.z + t * Math.sin(angle);

                                // 检测粒子路径上的生物
                                Box box = new Box(particleX - 0.5, m_pos.y - 0.5, particleZ - 0.5, particleX + 0.5, m_pos.y + 0.5, particleZ + 0.5);
                                List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, box, e -> e != player && e != target);

                                // 对检测到的生物造成伤害
                                for (LivingEntity m_entity : entities) {
                                    m_entity.damage(world.getDamageSources().playerAttack(player), 30.0F); // 造成 5 点伤害
                                }
                            }
                        }
                        // 在粒子生成后，检测粒子路径上的方块
                        for (int i = 0; i < particleCount; i++) {
                            double angle = 2 * Math.PI * i / particleCount;
                            double x = m_pos.x + radius * Math.cos(angle);
                            double z = m_pos.z + radius * Math.sin(angle);

                            // 模拟粒子运动轨迹
                            for (double t = 0; t < radius; t += 0.1) {
                                double particleX = m_pos.x + t * Math.cos(angle);
                                double particleZ = m_pos.z + t * Math.sin(angle);

                                // 获取粒子当前位置的方块
                                BlockPos blockPos = new BlockPos((int) particleX, (int) m_pos.y, (int) particleZ);
                                BlockPos blockPos2 = new BlockPos((int) particleX, (int) m_pos.y-1, (int) particleZ);
                                BlockState blockState = world.getBlockState(blockPos);
                                BlockState blockState2 = world.getBlockState(blockPos2);

                                // 如果方块不是空气，则破坏方块
                                if (!blockState.isAir()) {
                                    world.breakBlock(blockPos, true, player); // 破坏方块并掉落物品
                                }
                                // 如果方块不是空气，则破坏方块
                                if (!blockState2.isAir()) {
                                    world.breakBlock(blockPos2, true, player); // 破坏方块并掉落物品
                                }
                            }
                        }
                        // 应用额外伤害
                        target.damage(world.getDamageSources().playerAttack(player), extraDamage);

                        // 减少武器 50 点耐久度
                        stack.damage(5000, player, (p) -> p.sendToolBreakStatus(hand));
                    }
                    // 播放粒子效果（使用 ITEM 粒子）
                    Vec3d pos = target.getPos();
                    float pitch = player.getPitch();
                    float yaw = player.getYaw();
                    Double v=0.5;
                    ItemStackParticleEffect effect = new ItemStackParticleEffect(ParticleTypes.ITEM,player.getStackInHand(hand));
                    world.addParticle(effect, pos.x, pos.y + 2.5, pos.z, 0, 0, 0);
//                    world.addParticle(effect, pos.x, pos.y + 1.0, pos.z, v*Math.cos(pitch*Math.PI/180)*Math.cos((yaw-90)*Math.PI/180), v*Math.sin(pitch*Math.PI/180), v*Math.cos(pitch*Math.PI/180)*Math.sin((yaw-90)*Math.PI/180));
                    // 播放音效
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ITEM_BREAK, player.getSoundCategory(), 1.0F, 1.0F);

                    // 设置武器冷却时间（20 ticks = 1 秒）
                    player.getItemCooldownManager().set(stack.getItem(),100);

                    // 返回 SUCCESS 表示事件已处理
                    return ActionResult.SUCCESS;
                }
            }
        }

        // 返回 PASS 表示事件未处理，继续传递给其他监听器
        return ActionResult.PASS;
    }
}
