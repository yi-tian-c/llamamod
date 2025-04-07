package com.yi_tian.mixin;

import com.yi_tian.entity.llama.LlamaMageEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(
            method = "damage",
            at = @At("HEAD")
    )
    private void onEntityDamaged(
            DamageSource source,
            float amount,
            CallbackInfoReturnable<Boolean> cir
    ) {
        // 获取被攻击的实体和攻击者
        LivingEntity victim = (LivingEntity) (Object) this;
        Entity attacker = source.getAttacker();

        // 过滤条件：攻击者是玩家，且受害者是动物
        if (attacker instanceof LivingEntity && victim instanceof PassiveEntity) {
            if(!(victim instanceof WolfEntity)) {
                World world = victim.getWorld();

                // 搜索附近 16 格内的羊驼法师
                List<LlamaMageEntity> llamaMages = world.getEntitiesByClass(
                        LlamaMageEntity.class,
                        victim.getBoundingBox().expand(16.0),
                        llamaMage -> true // 无条件筛选
                );

                // 触发每个羊驼法师的愤怒
                for (LlamaMageEntity llamaMage : llamaMages) {
                    llamaMage.setAngryAt(attacker.getUuid());
                    llamaMage.setTarget((LivingEntity) attacker);
                    llamaMage.chooseRandomAngerTime();
                }
            }
        }
    }
}
