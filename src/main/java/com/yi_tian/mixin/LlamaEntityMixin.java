package com.yi_tian.mixin;

import com.yi_tian.entity.ModEntities;
import com.yi_tian.entity.llama.LlamaMageEntity;
import com.yi_tian.entity.llama.VillagerLlamaEntity;
import com.yi_tian.entity.llama.WanderingLlamaEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.passive.TraderLlamaEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class LlamaEntityMixin {

    @Shadow public abstract boolean entityDataRequiresOperator();

    @Inject(
            method = "onStruckByLightning",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onLightningStrike(
            ServerWorld world,
            LightningEntity lightning,
            CallbackInfo ci
    ) {
        Entity entity = (Entity) (Object) this; // 被击中的实体

        // 仅处理羊驼
        if (entity instanceof LlamaEntity llama&&!(entity instanceof TraderLlamaEntity)) {
            // 确保只在服务端执行
            if (!world.isClient) {
                // 创建 LlamaMageEntity
                LlamaMageEntity llamaMage = new LlamaMageEntity(ModEntities.LLAMA_MAGE, world);

                // 继承原位置和角度
                llamaMage.refreshPositionAndAngles(
                        llama.getX(),
                        llama.getY(),
                        llama.getZ(),
                        llama.getYaw(),
                        llama.getPitch()
                );

                // 生成新实体
                world.spawnEntity(llamaMage);

                // 移除原版羊驼
                llama.discard();

                // 取消原版逻辑（防止羊驼变成僵尸马等行为）
                ci.cancel();
            }
        }
        if(entity instanceof TraderLlamaEntity){
            TraderLlamaEntity traderLlama = (TraderLlamaEntity) entity;

            // 生成新实体
            WanderingLlamaEntity wanderingLlama = ModEntities.WARDERING_LLAMA.create(world);
            VillagerLlamaEntity llama1 = ModEntities.VILLAGER_LLAMA.create(world);
            VillagerLlamaEntity llama2 = ModEntities.VILLAGER_LLAMA.create(world);

            // 设置位置和旋转
            BlockPos pos = traderLlama.getBlockPos();
            wanderingLlama.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
            llama1.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
            llama2.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);

            // 设置永久存在
            wanderingLlama.setPersistent();
            llama1.setPersistent();
            llama2.setPersistent();

            // 移除原版相关实体
            Entity trader = traderLlama.getOwner();
            traderLlama.discard();
            if (trader instanceof WanderingTraderEntity) {
                trader.discard();
            }

            // 生成新实体到世界
            world.spawnEntity(wanderingLlama);
            world.spawnEntity(llama1);
            world.spawnEntity(llama2);
            // 设置拴绳连接
            llama1.attachLeash(wanderingLlama,true);
            llama2.attachLeash(wanderingLlama,true);
            ci.cancel();
        }
        if (entity instanceof WanderingLlamaEntity){
            ci.cancel();
        }
    }
}