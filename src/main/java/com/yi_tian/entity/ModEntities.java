package com.yi_tian.entity;

import com.yi_tian.entity.llama.LlamaMageEntity;
import com.yi_tian.entity.llama.VillagerLlamaEntity;
import com.yi_tian.entity.llama.WanderingLlamaEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.yi_tian.Yitianmod.MOD_ID;


public class ModEntities {
    public static final EntityType<LlamaMageEntity> LLAMA_MAGE=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID,"llama_mage"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE,LlamaMageEntity::new)
                    .dimensions(EntityDimensions.fixed(0.65f,2.75f))
                    .build()
    );
    public static final EntityType<WanderingLlamaEntity> WARDERING_LLAMA=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID,"wandering_llama_trader"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WanderingLlamaEntity::new)
                    .dimensions(EntityDimensions.fixed(0.65f,2.75f))
                    .build()
    );
    public static final EntityType<VillagerLlamaEntity> VILLAGER_LLAMA=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID,"villager_llama"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, VillagerLlamaEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f,0.65f))
                    .build()
    );
    // 注册所有自定义实体的方法
    public static void registerEntities() {
    }

    // 为自定义实体注册属性
    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(LLAMA_MAGE, LlamaMageEntity.createLlamagateAttributes());
        FabricDefaultAttributeRegistry.register(WARDERING_LLAMA, WanderingLlamaEntity.createwaAttributes());
        FabricDefaultAttributeRegistry.register(VILLAGER_LLAMA, VillagerLlamaEntity.createwaAttributes());
    }
}
