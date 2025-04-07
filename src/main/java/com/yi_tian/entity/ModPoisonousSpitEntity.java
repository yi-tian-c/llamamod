package com.yi_tian.entity;

import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ModPoisonousSpitEntity extends ModSpitEntity {
    private String name;
    public ModPoisonousSpitEntity(EntityType<? extends LlamaSpitEntity> entityType, World world) {
        super(entityType, world);
        this.name="poisonousSpitEntity";
    }

    public ModPoisonousSpitEntity(World world, LlamaEntity owner) {
        this(EntityType.LLAMA_SPIT, world);
        this.name="poisonousSpitEntity";
        this.setOwner(owner);
        this.setPosition(
                owner.getX() - (double)(owner.getWidth() + 1.0F) * 0.5 * (double) MathHelper.sin(owner.bodyYaw * (float) (Math.PI / 180.0)),
                owner.getEyeY() - 0.1F,
                owner.getZ() + (double)(owner.getWidth() + 1.0F) * 0.5 * (double)MathHelper.cos(owner.bodyYaw * (float) (Math.PI / 180.0))
        );
    }
    @Override
    public String get_name(){
        return this.name;
    }
    @Override
    public void tick() {
        super.tick();
        Vec3d vec3d = this.getVelocity();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.onCollision(hitResult);
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();
        float g = 0.99F;
        float h = 0.06F;
        if (this.getWorld().getStatesInBox(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
            this.discard();
        } else if (this.isInsideWaterOrBubbleColumn()) {
            this.discard();
        } else {
            this.setVelocity(vec3d.multiply(0.99F));
            if (!this.hasNoGravity()) {
                this.setVelocity(this.getVelocity().add(0.0, -0.06F, 0.0));
            }

            this.setPosition(d, e, f);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.getOwner() instanceof LivingEntity livingEntity&&entityHitResult.getEntity() instanceof LivingEntity) {
            entityHitResult.getEntity().damage(this.getDamageSources().mobProjectile(this, livingEntity), 6.0F);
            ((LivingEntity)entityHitResult.getEntity()).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 2,false,false,true));
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            // 创建药水云实体
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
            areaEffectCloudEntity.setRadius(1.5F); // 设置药水云半径
            areaEffectCloudEntity.setRadiusOnUse(-0.5F); // 每次使用后半径减少
            areaEffectCloudEntity.setWaitTime(5); // 药水云生成后的等待时间（ticks）
            areaEffectCloudEntity.setDuration(200); // 药水云持续时间（ticks，30 秒）
            areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration()); // 半径随时间减小

            // 添加药水效果
            StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.POISON, 100, 1); // 中毒效果，持续 5 秒，等级 1
            areaEffectCloudEntity.addEffect(effect);

            // 生成药水云
            this.getWorld().spawnEntity(areaEffectCloudEntity);
            this.discard();
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        double d = packet.getVelocityX();
        double e = packet.getVelocityY();
        double f = packet.getVelocityZ();

        for (int i = 0; i < 4; i++) {
            double g = 0.4 + 0.1 * (double)i;
            this.getWorld().addParticle(ParticleTypes.SPIT, this.getX(), this.getY(), this.getZ(), d * g, e, f * g);
        }

        this.setVelocity(d, e, f);
    }
}
