package com.yi_tian.item.custom;

import com.yi_tian.entity.ModExplosionSpitEntity;
import com.yi_tian.entity.ModPoisonousSpitEntity;
import com.yi_tian.entity.ModSickSpitEntity;
import com.yi_tian.entity.ModSpitEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class HatItem extends Item implements Equipment {
    public HatItem(Settings settings, Type type) {
        super(settings);
        this.type = type;
    }
    protected final Type type;
    @Override
    public EquipmentSlot getSlotType() {
        return this.type.getEquipmentSlot();
    }
    public static enum Type {
        HELMET(EquipmentSlot.HEAD, "helmet");

        private final EquipmentSlot equipmentSlot;
        private final String name;

        private Type(EquipmentSlot equipmentSlot, String name) {
            this.equipmentSlot = equipmentSlot;
            this.name = name;
        }

        public EquipmentSlot getEquipmentSlot() {
            return this.equipmentSlot;
        }

        public String getName() {
            return this.name;
        }
        public boolean canRepair(ItemStack stack, ItemStack ingredient) {
            return false;
        }
    }
//    @Override
//    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        ItemStack itemStack = user.getStackInHand(hand);
//        boolean hasSpit = false;
//
//        // 遍历玩家的所有物品栏，找到口水
//        for (ItemStack stack : user.getInventory().main) {
//            if (stack.getItem() instanceof SpitItem) {
//                    hasSpit = true;
//                    break;
//            }
//        }
//        if (hasSpit) {
//            return TypedActionResult.consume(itemStack);
//        } else {
//            return TypedActionResult.fail(itemStack);
//        }
//    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand); // 获取当前手持的物品（发射器）
        //检查冷却时间
//        if (user.getItemCooldownManager().isCoolingDown(this)) {
//            return TypedActionResult.fail(itemStack); // 如果冷却中，直接返回失败
//        }
        // 1. 查找玩家背包中的口水物品
        ItemStack spitStack = findSpitStack(user);
        boolean isCreative = user.getAbilities().creativeMode;

        // 2. 无口水且非创造模式时直接失败
        if (spitStack == null && !isCreative) {
            return TypedActionResult.fail(itemStack);
        }

        // 3. 非创造模式时消耗口水
        if (!isCreative) {
            spitStack.decrement(1);
            if (spitStack.isEmpty()) {
                user.getInventory().removeOne(spitStack); // 清理空堆叠
            }
        }

        // 4. 发射口水实体（仅服务端逻辑）
        if (!world.isClient) {
            if(spitStack.getItem() instanceof PoisonousSpitItem){
                ModPoisonousSpitEntity spitEntity = new ModPoisonousSpitEntity(EntityType.LLAMA_SPIT, user.getWorld());
                spitEntity.setOwner(user);
                spitEntity.setPosition(
                        user.getX() - (double)(user.getWidth() + 1.0F) * 0.5 * (double)MathHelper.sin(user.bodyYaw * (float) (Math.PI / 180.0)),
                        user.getEyeY() - 0.1F,
                        user.getZ() + (double)(user.getWidth() + 1.0F) * 0.5 * (double)MathHelper.cos(user.bodyYaw * (float) (Math.PI / 180.0))
                );
                spitEntity.setVelocity(
                        user, // 发射者
                        user.getPitch(), // 俯仰角
                        user.getYaw(),   // 偏航角
                        0.0F,           // 滚动角
                        1.5F,           // 速度
                        1.0F            // 精度
                );
                user.getWorld().spawnEntity(spitEntity);
            }
            else if(spitStack.getItem() instanceof ExplosionSpitItem){
                ModExplosionSpitEntity spitEntity = new ModExplosionSpitEntity(EntityType.LLAMA_SPIT, user.getWorld());
                spitEntity.setOwner(user);
                spitEntity.setPosition(
                        user.getX() - (double)(user.getWidth() + 1.0F) * 0.5 * (double)MathHelper.sin(user.bodyYaw * (float) (Math.PI / 180.0)),
                        user.getEyeY() - 0.1F,
                        user.getZ() + (double)(user.getWidth() + 1.0F) * 0.5 * (double)MathHelper.cos(user.bodyYaw * (float) (Math.PI / 180.0))
                );
                spitEntity.setVelocity(
                        user, // 发射者
                        user.getPitch(), // 俯仰角
                        user.getYaw(),   // 偏航角
                        0.0F,           // 滚动角
                        1.5F,           // 速度
                        1.0F            // 精度
                );
                user.getWorld().spawnEntity(spitEntity);
            }
            else if(spitStack.getItem() instanceof SickSpitItem){
                ModSickSpitEntity spitEntity = new ModSickSpitEntity(EntityType.LLAMA_SPIT, user.getWorld());
                spitEntity.setOwner(user);
                spitEntity.setPosition(
                        user.getX() - (double)(user.getWidth() + 1.0F) * 0.5 * (double)MathHelper.sin(user.bodyYaw * (float) (Math.PI / 180.0)),
                        user.getEyeY() - 0.1F,
                        user.getZ() + (double)(user.getWidth() + 1.0F) * 0.5 * (double)MathHelper.cos(user.bodyYaw * (float) (Math.PI / 180.0))
                );
                spitEntity.setVelocity(
                        user, // 发射者
                        user.getPitch(), // 俯仰角
                        user.getYaw(),   // 偏航角
                        0.0F,           // 滚动角
                        1.5F,           // 速度
                        1.0F            // 精度
                );
                user.getWorld().spawnEntity(spitEntity);
            }
            else {
                ModSpitEntity spitEntity = new ModSpitEntity(EntityType.LLAMA_SPIT, user.getWorld());
                spitEntity.setOwner(user);
                spitEntity.setPosition(
                        user.getX() - (double)(user.getWidth() + 1.0F) * 0.5 * (double)MathHelper.sin(user.bodyYaw * (float) (Math.PI / 180.0)),
                        user.getEyeY() - 0.1F,
                        user.getZ() + (double)(user.getWidth() + 1.0F) * 0.5 * (double)MathHelper.cos(user.bodyYaw * (float) (Math.PI / 180.0))
                );
                spitEntity.setVelocity(
                        user, // 发射者
                        user.getPitch(), // 俯仰角
                        user.getYaw(),   // 偏航角
                        0.0F,           // 滚动角
                        1.5F,           // 速度
                        1.0F            // 精度
                );
                user.getWorld().spawnEntity(spitEntity);
            }
        }

        // 5. 播放音效和更新统计
        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                SoundEvents.ENTITY_LLAMA_SPIT,
                SoundCategory.PLAYERS,
                1.0F,
                1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F)
        );
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        ItemStack m_itemStack = user.getStackInHand(hand);
//        user.getItemCooldownManager().set(m_itemStack.getItem(), 20);
        return TypedActionResult.success(itemStack, world.isClient());
    }

    // 辅助方法：查找主物品栏中的口水
    private ItemStack findSpitStack(PlayerEntity player) {
        for (ItemStack stack : player.getInventory().main) {
            if (stack.getItem() instanceof SpitItem) {
                return stack;
            }
        }
        return null;
    }
}
