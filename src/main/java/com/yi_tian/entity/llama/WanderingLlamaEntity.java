package com.yi_tian.entity.llama;

import com.yi_tian.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class WanderingLlamaEntity extends VillagerEntity implements GeoEntity{
    private static final Ingredient TAMING_INGREDIENT = Ingredient.ofItems(Items.WHEAT, Blocks.HAY_BLOCK.asItem());
    private AnimatableInstanceCache cache= GeckoLibUtil.createInstanceCache(this);
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    public WanderingLlamaEntity(EntityType<? extends VillagerEntity> entityType, World world) {
        super(entityType, world);
        this.setVillagerData(this.getVillagerData().withProfession(VillagerProfession.NITWIT));
    }

    public static DefaultAttributeContainer.Builder createwaAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH,20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.33f)
                .add(EntityAttributes.GENERIC_ARMOR,0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, (double)48.0F);
    }
    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
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
    private PlayState predicate(software.bernie.geckolib.core.animation.AnimationState<WanderingLlamaEntity> WangeringLlamaEntityAnimationState) {
        if(WangeringLlamaEntityAnimationState.isMoving()){
            WangeringLlamaEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.wandering_llama_trader.walk",Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        WangeringLlamaEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.wandering_llama_trader.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    @Override
    protected void fillRecipes() {
        // 添加自定义交易
        TradeOfferList tradeOfferList = this.getOffers();
        tradeOfferList.add(new TradeOffer(
                new ItemStack(Items.HAY_BLOCK, 64),
                new ItemStack(ModItems.LLAMA_SKULL, 1),
                16, // 最大使用次数
                2, // 交易后的经验值
                0.05f // 价格乘数
        ));
        tradeOfferList.add(new TradeOffer(
                new ItemStack(Items.WHEAT, 1),
                new ItemStack(ModItems.SPIT, 1),
                16, // 最大使用次数
                2, // 交易后的经验值
                0.05f // 价格乘数
        ));
        super.fillRecipes();
        this.setVillagerData(this.getVillagerData().withProfession(VillagerProfession.NITWIT));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_LLAMA_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_LLAMA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_LLAMA_DEATH;
    }

    // 显示名称
    @Override
    public Text getDisplayName() {
        // 自定义交易界面中的名称
        return Text.translatable("entity.maomod.wandering_llama_trader");
    }
}
