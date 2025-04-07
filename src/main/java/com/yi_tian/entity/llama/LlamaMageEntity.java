package com.yi_tian.entity.llama;

import com.yi_tian.entity.ModEntities;
import com.yi_tian.entity.ModExplosionSpitEntity;
import com.yi_tian.entity.ModPoisonousSpitEntity;
import com.yi_tian.entity.ModSickSpitEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class LlamaMageEntity extends AnimalEntity implements GeoEntity , Angerable {
    private SheepEntity targetSheep; // 记录目标羊
    public static int spittime=0;
    private int isSpellTime=0;
    private boolean isSpell=false;
    private AnimatableInstanceCache cache=GeckoLibUtil.createInstanceCache(this);
    private int playTimeout=(this.random.nextInt(20)+20)*40;
    private int isplay=0;
    private static final Ingredient TAMING_INGREDIENT = Ingredient.ofItems(Items.WHEAT, Blocks.HAY_BLOCK.asItem());
    // 仇恨机制相关字段（类似僵尸猪灵）
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(200, 390);
    private int angerTime;
    @Nullable
    private UUID angryAt;
    public static final UniformIntProvider ANGER_PASSING_COOLDOWN_RANGE = TimeHelper.betweenSeconds(400,600);
    private int angerPassingCooldown;
    // 攻击控制字段（类似烈焰人）
    private static final TrackedData<Integer> LLAMA_MAGE_STATE = DataTracker.registerData(LlamaMageEntity.class, TrackedDataHandlerRegistry.INTEGER);
    // 在实体初始化方法中
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(LLAMA_MAGE_STATE, random.nextInt(3));
    }
    public int getLlamaMageState() {
        return this.getDataTracker().get(LLAMA_MAGE_STATE);
    }
    // 4. 提供服务端专用设置方法
    public void setLlamaMageState(int state) {
        if (!this.getWorld().isClient()) { // 确保只在服务端修改
            this.getDataTracker().set(LLAMA_MAGE_STATE, state);
            // 重要！标记数据已更改
        }
    }
    public LlamaMageEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F); // 避免进入水中
//        this.llama_mage_state=random.nextInt(3);
    }
    // 注册 TrackedData
//    private static final TrackedData<Integer> STATE = DataTracker.registerData(LlamaMageEntity.class, TrackedDataHandlerRegistry.INTEGER);
//    @Override
//    protected void initDataTracker() {
//        super.initDataTracker();
//        this.dataTracker.startTracking(STATE, llama_mage_state); // 初始状态为 IDLE
//    }
//
//    // 状态同步方法
//    public int getState() {
//        return this.dataTracker.get(STATE);
//    }
//
//    public void setState(int state) {
//        this.dataTracker.set(STATE, state);
//    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
        }
        else{
            if(this.age%1000==0){
                this.setHealth(this.getHealth()+1.0f);
            }
        }
        if (this.getWorld().isClient()) {
            if(this.age%60==0){
//                Maomod.LOGGER.info("粒子效果目前state"+Integer.toString(getLlamaMageState()));
            }
            if(this.age%5==0){
                Vector3f color;
                if(getLlamaMageState()==0){
                    color = new Vector3f(0.5f, 1.0f, 0.5f); // 亮绿色
                }
                else if(getLlamaMageState()==1){
                    color = new Vector3f(0.0f, 1.0f, 0.0f);//绿色
                }
                else{
                    color=new Vector3f(0.5f, 0.5f, 0.5f);//灰色
                }
                this.getWorld().addParticle(new DustParticleEffect(color, 1.0f), this.getX() + this.random.nextGaussian() * (double)0.60F,
                        this.getBoundingBox().maxY - (double)0.5F + this.random.nextGaussian() * (double)0.63F,
                        this.getZ() + this.random.nextGaussian() * (double)0.60F, (double)0.0F, (double) new Random().nextFloat() * 0.3f, (double)0.0F);
            }
        }
        else{
            if(this.age%20==0){
                this.setTarget(null);
            }
            if(spittime>0){
                spittime--;
            }
            else{
                if (this.age % 5 == 0) {
                            ((ServerWorld) this.getWorld()).spawnParticles(new DustParticleEffect(new Vector3f(1.0f, 1.0f, 1.0f), 1.0f),
                                    this.getX() + this.random.nextGaussian() * (double)0.60F,
                                    this.getBoundingBox().maxY - (double)0.5F + this.random.nextGaussian() * (double)0.63F,
                                    this.getZ() + this.random.nextGaussian() * (double)0.60F,
                                    1,
                                    0.1f, 0.1f, 0.1f, 0.0f);
                }
            }
            if(isSpellTime>0&&isSpell==true){
                isSpellTime--;
                if(isSpellTime>=100) {
                    if (this.age % 5 == 0) {
                        if (isSpell == true) {
                            double tx = this.getX();
                            double ty = this.getY() + 1.5f;
                            double tz = this.getZ();
                            double sx = targetSheep.getX();
                            double sy = targetSheep.getY() + 0.6f;
                            double sz = targetSheep.getZ();
                            double dx = (sx - tx) / 50;
                            double dy = (sy - ty) / 50;
                            double dz = (sz - tz) / 50;
                            for (int i = 1; i <= 50; ++i) {
                                ((ServerWorld) this.getWorld()).spawnParticles(new DustParticleEffect(new Vector3f(0.2f, 0.2f, 1.0f), 1.0f),
                                        tx + i * dx,
                                        ty + i * dy,
                                        tz + i * dz,
                                        1,
                                        0.1f, 0.1f, 0.1f, 0.0f);
                            }
                        }
                    }
                }
                if(isSpellTime==100){
                    this.targetSheep.setColor(DyeColor.BLUE);
                }
                if(isSpellTime==0){
                    isSpell=false;
                }
            }
            if(this.age%60==0) {
//                Maomod.LOGGER.info("当前状态" + Integer.toString(getLlamaMageState()));
            }
            if(!isSpell&&this.age%20==0){
//                Maomod.LOGGER.info("寻找羊");
                Box box = new Box(
                        this.getX() - 10, this.getY() - 10, this.getZ() - 10,
                        this.getX() + 10, this.getY() + 10, this.getZ() + 10
                );

                // 获取范围内的所有 SheepEntity
                List<SheepEntity> sheepEntities = this.getWorld().getEntitiesByClass(SheepEntity.class, box, entity -> true);

                // 遍历检测到的羊
                for (SheepEntity sheep : sheepEntities) {
                    // 检查羊的颜色是否是红色
                    if (sheep.getColor() == DyeColor.RED) {
                        // 记录目标羊
                        this.targetSheep = sheep;
                        // 触发动画
                        isSpell=true;
                        isSpellTime=180;
                        targetSheep=sheep;
                        break; // 只处理一只羊
                    }
                }
            }
            if(this.getTarget()==null){
                double range = this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
                Box box = new Box(this.getBlockPos()).expand(range);
                List<LivingEntity> list = this.getWorld().getEntitiesByClass(
                        LivingEntity.class,
                        box,
                        entity -> entity instanceof WolfEntity);
//        List<LlamaMageEntity> list = this.getWorld().getEntitiesByClass(
//                LlamaMageEntity.class,
//                box,
//                entity -> entity != this && entity.getTarget() == null
//        );

                for (LivingEntity m_entity : list) {
                    this.setTarget(m_entity);
                    break;
                }
            }
            if(this.getTarget()==null){
                double range = this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
                Box box = new Box(this.getBlockPos()).expand(range);
                List<LivingEntity> list = this.getWorld().getEntitiesByClass(
                        LivingEntity.class,
                        box,
                        entity -> entity instanceof Monster);
//        List<LlamaMageEntity> list = this.getWorld().getEntitiesByClass(
//                LlamaMageEntity.class,
//                box,
//                entity -> entity != this && entity.getTarget() == null
//        );

                for (LivingEntity m_entity : list) {
                    this.setTarget(m_entity);
                    break;
                }
            }

        }

    }
    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.LLAMA_MAGE.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return TAMING_INGREDIENT.test(stack);
    }
    @Override
    protected void initGoals() {
        // 目标选择器（优先级顺序）
        this.targetSelector.add(5, new RevengeGoal(this, new Class[0]).setGroupRevenge(new Class[]{LlamaMageEntity.class})); // 群体仇恨
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, false, false, this::shouldAngerAt));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, WolfEntity.class, false));
        this.targetSelector.add(4, new UniversalAngerGoal<>(this, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, false, false, (entity) -> entity instanceof Monster));

        // 行为控制器
        this.goalSelector.add(0,new SwimGoal(this));
        this.goalSelector.add(2, new MageAttackGoal(this));  // 自定义攻击逻辑

        this.goalSelector.add(6, new GoToWalkTargetGoal(this, 1.0));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(9, new LookAroundGoal(this));
    }
    public boolean isAngryAt(PlayerEntity player) {
        return this.shouldAngerAt(player);
    }
    public static DefaultAttributeContainer.Builder createLlamagateAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH,60)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.33f)
                .add(EntityAttributes.GENERIC_ARMOR,10)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, (double)6.0F)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, (double)48.0F);
    }
    @Override
    protected void updateLimbs(float posDelta) {
        float f=this.getPose()== EntityPose.STANDING?Math.min(posDelta*6.0f,1.0f):0.0f;
        this.limbAnimator.updateLimbs(f,0.2f);
    }
    @Override
    public boolean tryAttack(Entity target) {
        boolean attacked = super.tryAttack(target);
        if (attacked && target instanceof LivingEntity) {
            // 水平击退（力度 0.5，方向为攻击者视角方向）
            double knockbackPower = 0.7;
            Vec3d knockback = this.getRotationVector().multiply(knockbackPower);
            target.addVelocity(knockback.x, 0.3, knockback.z); // 垂直速度 0.3 实现击飞
        }
        return attacked;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,"controller",0,this::predicate));
        // 新增触发式法术动画控制器
    }

    private PlayState predicate(software.bernie.geckolib.core.animation.AnimationState<LlamaMageEntity> llamaMageEntityAnimationState) {
//        if(spellTimeout<=0){
//            spellTimeout=(this.random.nextInt(60)+60)*30;
//            llamaMageEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.llama_mage.spell",Animation.LoopType.LOOP));
//            isspell=400;
//            return PlayState.CONTINUE;
//        }
//        else{
//            spellTimeout--;
//        }
//        if(isspell>0){
//            isspell--;
//            if(isspell==0){
//                setLlamaMageState(random.nextInt(3));
////                Maomod.LOGGER.info("状态转化为"+Integer.toString(llama_mage_state));
//            }
//            return PlayState.CONTINUE;
//        }
        if(llamaMageEntityAnimationState.isMoving()){
            isplay=0;
            llamaMageEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.llama_mage.walk",Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if(playTimeout<=0){
            playTimeout=(this.random.nextInt(20)+20)*40;
            llamaMageEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.llama_mage.play",Animation.LoopType.LOOP));
            isplay=(this.random.nextInt(20)+20)*8;
            return PlayState.CONTINUE;
        }
        else{
            playTimeout--;
        }
        if(isplay>0){
            llamaMageEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.llama_mage.play",Animation.LoopType.LOOP));
            isplay--;
            return PlayState.CONTINUE;
        }
        llamaMageEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.llama_mage.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
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

//    @Override
//    public void onDamaged(DamageSource damageSource) {
//        if(isPotionDamage(damageSource)){
//
//        }
//        super.onDamaged(damageSource);
//    }
@Override
protected float modifyAppliedDamage(DamageSource source, float amount) {
    // 先调用父类逻辑（护甲、附魔等计算）
    float finalDamage = super.modifyAppliedDamage(source, amount);

    // 药水类伤害减免
    if (isPotionDamage(source)) {
        finalDamage *= 0.15f; // 减免85%
    }

    return finalDamage;
}
    @Override
    protected void mobTick() {
        super.mobTick();
        // 更新仇恨状态
        if (!this.getWorld().isClient) {
            this.tickAngerLogic((ServerWorld) this.getWorld(), true);
            // 传递仇恨给附近同类
            if (this.angerPassingCooldown > 0) {
                this.angerPassingCooldown--;
            } else if (this.getTarget() != null && this.getVisibilityCache().canSee(this.getTarget())) {
                this.angerNearbyMages();
                this.angerPassingCooldown = ANGER_PASSING_COOLDOWN_RANGE.get(this.random);
            }
        }


    }

    private void angerNearbyMages() {
        double range = this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
        Box box = new Box(this.getBlockPos()).expand(range);
        List<LlamaMageEntity> list = this.getWorld().getEntitiesByClass(
                LlamaMageEntity.class,
                box,
                entity -> entity != this && entity.getTarget() == null&& !entity.isTeammate(this.getTarget())
        );
//        List<LlamaMageEntity> list = this.getWorld().getEntitiesByClass(
//                LlamaMageEntity.class,
//                box,
//                entity -> entity != this && entity.getTarget() == null
//        );

        for (LlamaMageEntity mage : list) {
            if (this.getTarget() instanceof LlamaMageEntity) continue;
            mage.setTarget(this.getTarget());
        }
//        for (LlamaMageEntity mage : list) {
//            mage.setTarget(this.getTarget());
//        }
    }
    private boolean isPotionDamage(DamageSource source) {
        return source.isIn(DamageTypeTags.WITHER_IMMUNE_TO);
    }

    @Nullable
    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_LLAMA_STEP, 0.15F, 1.0F);
    }
    public void spitAt(LivingEntity target) {
        if(getLlamaMageState()==0) {
//            Maomod.LOGGER.info("羊驼发射毒口水");
            ModPoisonousSpitEntity spitEntity = new ModPoisonousSpitEntity(EntityType.LLAMA_SPIT, this.getWorld());
            spitEntity.setPosition(
                    this.getX() - (double) (this.getWidth() + 1.0F) * 0.5 * (double) MathHelper.sin(this.bodyYaw * (float) (Math.PI / 180.0)),
                    this.getEyeY() ,
                    this.getZ() + (double) (this.getWidth() + 1.0F) * 0.5 * (double) MathHelper.cos(this.bodyYaw * (float) (Math.PI / 180.0))
            );
            double d = target.getX() - this.getX();
            double e = target.getBodyY(0.3333333333333333) - spitEntity.getY();
            double f = target.getZ() - this.getZ();
            double g = Math.sqrt(d * d + f * f) * 0.2F;
            spitEntity.setVelocity(d, (e + g)*0.8, f, 1.5F, 10.0F);
            spitEntity.setOwner(this);
            this.getWorld().spawnEntity(spitEntity);
        }
        else if (getLlamaMageState()==1) {
//            Maomod.LOGGER.info("羊驼发射恶心口水");
            ModSickSpitEntity spitEntity = new ModSickSpitEntity(EntityType.LLAMA_SPIT, this.getWorld());
            spitEntity.setPosition(
                    this.getX() - (double) (this.getWidth() + 1.0F) * 0.5 * (double) MathHelper.sin(this.bodyYaw * (float) (Math.PI / 180.0)),
                    this.getEyeY(),
                    this.getZ() + (double) (this.getWidth() + 1.0F) * 0.5 * (double) MathHelper.cos(this.bodyYaw * (float) (Math.PI / 180.0))
            );
            double d = target.getX() - this.getX();
            double e = target.getBodyY(0.3333333333333333) - spitEntity.getY();
            double f = target.getZ() - this.getZ();
            double g = Math.sqrt(d * d + f * f) * 0.2F;
            spitEntity.setVelocity(d, e + g, f, 1.5F, 10.0F);
            spitEntity.setOwner(this);
            this.getWorld().spawnEntity(spitEntity);
        }
        else if(getLlamaMageState()==2){
//            Maomod.LOGGER.info("羊驼发射爆炸口水");
            ModExplosionSpitEntity spitEntity = new ModExplosionSpitEntity(EntityType.LLAMA_SPIT, this.getWorld());
            spitEntity.setPosition(
                    this.getX() - (double) (this.getWidth() + 1.0F) * 0.5 * (double) MathHelper.sin(this.bodyYaw * (float) (Math.PI / 180.0)),
                    this.getEyeY() ,
                    this.getZ() + (double) (this.getWidth() + 1.0F) * 0.5 * (double) MathHelper.cos(this.bodyYaw * (float) (Math.PI / 180.0))
            );
            double d = target.getX() - this.getX();
            double e = target.getBodyY(0.3333333333333333) - spitEntity.getY();
            double f = target.getZ() - this.getZ();
            double g = Math.sqrt(d * d + f * f) * 0.2F;
            spitEntity.setVelocity(d, e + g, f, 1.5F, 10.0F);
            spitEntity.setOwner(this);
            this.getWorld().spawnEntity(spitEntity);
        }
        if (!this.isSilent()) {
            this.getWorld()
                    .playSound(
                            null,
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            SoundEvents.ENTITY_LLAMA_SPIT,
                            this.getSoundCategory(),
                            1.0F,
                            1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F
                    );
        }
    }
    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.getWorld().isClient && source.getAttacker() instanceof LivingEntity attacker) {
            this.setTarget(attacker);
            this.angerNearbyEntities(); // 触发群体仇恨
        }
        return super.damage(source, amount);
    }
    private void angerNearbyEntities() {
        // 获取附近 24 格内所有同类生物
        List<LlamaMageEntity> list = this.getWorld().getEntitiesByClass(
                LlamaMageEntity.class,
                this.getBoundingBox().expand(48.0),
                entity -> entity != this && entity.getTarget() == null
        );

        for (LlamaMageEntity entity : list) {
//            Maomod.LOGGER.info("群体仇恨");
            entity.setTarget(this.getTarget());
        }
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }
    @Override public int getAngerTime() { return angerTime; }
    @Override public void setAngerTime(int ticks) { this.angerTime = ticks; }
    @Override public UUID getAngryAt() { return angryAt; }
    @Override public void setAngryAt(@Nullable UUID uuid) { this.angryAt = uuid; }
}
