package com.yi_tian.entity.llama;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VillagerLlamaEntity extends AnimalEntity implements GeoEntity{
    private static final Ingredient TAMING_INGREDIENT = Ingredient.ofItems(Items.WHEAT, Blocks.HAY_BLOCK.asItem());
    private AnimatableInstanceCache cache= GeckoLibUtil.createInstanceCache(this);
    public VillagerLlamaEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
    public static DefaultAttributeContainer.Builder createwaAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH,20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.33f)
                .add(EntityAttributes.GENERIC_ARMOR,0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, (double)48.0F);
    }
    @Override
    protected void initGoals() {
        super.initGoals();

        // 添加逃跑目标（优先级 1，逃离速度 1.5，触发距离 4-6 格）
        this.goalSelector.add(1, new FleeEntityGoal<>(
                this,         // 当前实体
                PlayerEntity.class,  // 逃离目标类型（玩家）
                4.0f,        // 触发逃跑的最小距离
                1.3D,        // 逃跑速度
                1.3D,       // 逃离后的移动速度（可选）
                entity -> {
                    // 触发条件：当实体被玩家攻击且未被驯服
                    return this.getAttacker() != null;
                }
        ));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new TemptGoal(this, 1.25D, TAMING_INGREDIENT, false));
        this.goalSelector.add(4, new WanderAroundGoal(this, 1.0D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }
    @Override
    public boolean damage(DamageSource source, float amount) {
        // 触发逃跑逻辑
        if (source.getAttacker() != null) {
            this.setAttacker((LivingEntity) source.getAttacker()); // 标记攻击者
        }
        return super.damage(source, amount);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,"controller",0,this::predicate));
    }
    private PlayState predicate(software.bernie.geckolib.core.animation.AnimationState<VillagerLlamaEntity> WangeringLlamaEntityAnimationState) {
        if(WangeringLlamaEntityAnimationState.isMoving()){
            WangeringLlamaEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.villager_llama.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        WangeringLlamaEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.villager_llama.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VILLAGER_NO;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }


    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
