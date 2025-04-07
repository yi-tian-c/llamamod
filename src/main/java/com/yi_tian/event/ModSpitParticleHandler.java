package com.yi_tian.event;

import com.yi_tian.entity.llama.LlamaMageEntity;
import com.yi_tian.item.custom.ExplosionSpitItem;
import com.yi_tian.item.custom.PoisonousSpitItem;
import com.yi_tian.item.custom.SickSpitItem;
import com.yi_tian.item.custom.SpitItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class ModSpitParticleHandler {
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != null) {
                // 遍历所有实体
                for (Entity entity : client.world.getEntities()) {
                    if (entity instanceof LlamaSpitEntity) {
                        Entity owner=((LlamaSpitEntity) entity).getOwner();
                        if(owner instanceof PlayerEntity) {
                            PlayerEntity owner2 = (PlayerEntity) owner;
                            ItemStack spitStack = findSpitStack(owner2);
                            if (spitStack != null && spitStack.getItem() instanceof PoisonousSpitItem) {
                                Vector3f color = new Vector3f(0.5f, 1.0f, 0.5f);
                                Vec3d m_v = entity.getVelocity();
                                double d = m_v.getX();
                                double e = m_v.getY();
                                double f = m_v.getZ();
                                double m_x;
                                double m_y;
                                double m_z;
                                for(int i=0;i<7;++i){
                                    m_x=Math.random()-0.5;
                                    m_y=Math.random()-0.5;
                                    m_z=Math.random()-0.5;
                                    double g = 0.4 + 0.1 * (double)i;
                                    client.world.addParticle(
                                            new DustParticleEffect(color, 1.0f), // 使用 DustParticleEffect 定义颜色
                                            entity.getX()+m_x,
                                            entity.getY()+m_y,
                                            entity.getZ()+m_z,
                                            d * g, e, f * g
                                    );
                                }
                            } else if (spitStack != null && spitStack.getItem() instanceof ExplosionSpitItem) {
                                Vector3f color = new Vector3f(0.5f, 0.5f, 0.5f);
                                Vec3d m_v = entity.getVelocity();
                                double d = m_v.getX();
                                double e = m_v.getY();
                                double f = m_v.getZ();
                                double m_x;
                                double m_y;
                                double m_z;
                                for(int i=0;i<7;++i){
                                    m_x=Math.random()-0.5;
                                    m_y=Math.random()-0.5;
                                    m_z=Math.random()-0.5;
                                    double g = 0.4 + 0.1 * (double)i;
                                    client.world.addParticle(
                                            new DustParticleEffect(color, 1.0f), // 使用 DustParticleEffect 定义颜色
                                            entity.getX()+m_x,
                                            entity.getY()+m_y,
                                            entity.getZ()+m_z,
                                            d * g, e, f * g
                                    );
                                }
                            }else if (spitStack != null && spitStack.getItem() instanceof SickSpitItem) {
                                Vector3f color = new Vector3f(0.0f, 1.0f, 0.0f);
                                Vec3d m_v = entity.getVelocity();
                                double d = m_v.getX();
                                double e = m_v.getY();
                                double f = m_v.getZ();
                                double m_x;
                                double m_y;
                                double m_z;
                                for(int i=0;i<7;++i){
                                    m_x=Math.random()-0.5;
                                    m_y=Math.random()-0.5;
                                    m_z=Math.random()-0.5;
                                    double g = 0.4 + 0.1 * (double)i;
                                    client.world.addParticle(
                                            new DustParticleEffect(color, 1.0f), // 使用 DustParticleEffect 定义颜色
                                            entity.getX()+m_x,
                                            entity.getY()+m_y,
                                            entity.getZ()+m_z,
                                            d * g, e, f * g
                                    );
                                }
                            }

                        }
                        if(owner instanceof LlamaMageEntity) {
                            if (((LlamaMageEntity) ((LlamaSpitEntity) entity).getOwner()).getLlamaMageState()==0) {
//                                if(entity instanceof ModPoisonousSpitEntity) {
//                                    Maomod.LOGGER.info("羊驼毒口水渲染");
                                    Vector3f color = new Vector3f(0.5f, 1.0f, 0.5f);
                                    Vec3d m_v = entity.getVelocity();
                                    double d = m_v.getX();
                                    double e = m_v.getY();
                                    double f = m_v.getZ();
                                    double m_x;
                                    double m_y;
                                    double m_z;
                                    for (int i = 0; i < 7; ++i) {
                                        m_x = Math.random() - 0.5;
                                        m_y = Math.random() - 0.5;
                                        m_z = Math.random() - 0.5;
                                        double g = 0.4 + 0.1 * (double) i;
                                        client.world.addParticle(
                                                new DustParticleEffect(color, 1.0f), // 使用 DustParticleEffect 定义颜色
                                                entity.getX() + m_x,
                                                entity.getY() + m_y,
                                                entity.getZ() + m_z,
                                                d * g, e, f * g
                                        );
                                    }
                            } else if (((LlamaMageEntity) ((LlamaSpitEntity) entity).getOwner()).getLlamaMageState()==2) {
//                                }else if(entity instanceof ModExplosionSpitEntity) {
//                                    Maomod.LOGGER.info("羊驼爆炸口水渲染");
                                    Vector3f color = new Vector3f(0.5f, 0.5f, 0.5f);
                                    Vec3d m_v = entity.getVelocity();
                                    double d = m_v.getX();
                                    double e = m_v.getY();
                                    double f = m_v.getZ();
                                    double m_x;
                                    double m_y;
                                    double m_z;
                                    for (int i = 0; i < 7; ++i) {
                                        m_x = Math.random() - 0.5;
                                        m_y = Math.random() - 0.5;
                                        m_z = Math.random() - 0.5;
                                        double g = 0.4 + 0.1 * (double) i;
                                        client.world.addParticle(
                                                new DustParticleEffect(color, 1.0f), // 使用 DustParticleEffect 定义颜色
                                                entity.getX() + m_x,
                                                entity.getY() + m_y,
                                                entity.getZ() + m_z,
                                                d * g, e, f * g
                                        );
                                    }
                            }else if (((LlamaMageEntity) ((LlamaSpitEntity) entity).getOwner()).getLlamaMageState()==1) {
//                                }else if(entity instanceof ModSickSpitEntity){
//                                Maomod.LOGGER.info("羊驼恶心口水渲染");
                                Vector3f color = new Vector3f(0.0f, 1.0f, 0.0f);
                                Vec3d m_v = entity.getVelocity();
                                double d = m_v.getX();
                                double e = m_v.getY();
                                double f = m_v.getZ();
                                double m_x;
                                double m_y;
                                double m_z;
                                for(int i=0;i<7;++i){
                                    m_x=Math.random()-0.5;
                                    m_y=Math.random()-0.5;
                                    m_z=Math.random()-0.5;
                                    double g = 0.4 + 0.1 * (double)i;
                                    client.world.addParticle(
                                            new DustParticleEffect(color, 1.0f), // 使用 DustParticleEffect 定义颜色
                                            entity.getX()+m_x,
                                            entity.getY()+m_y,
                                            entity.getZ()+m_z,
                                            d * g, e, f * g
                                    );
                                }
                            }

                        }
                    }
                }
            }
        });
    }
    private static ItemStack findSpitStack(PlayerEntity player) {
        for (ItemStack stack : player.getInventory().main) {
            if (stack.getItem() instanceof SpitItem) {
                return stack;
            }
        }
        return null;
    }
}
