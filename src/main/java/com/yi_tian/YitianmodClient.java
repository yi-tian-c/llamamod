package com.yi_tian;

import com.yi_tian.client.render.LlamaMageRenderer;
import com.yi_tian.client.render.VillagerLlamaRenderer;
import com.yi_tian.client.render.WanderingLlamaRenderer;
import com.yi_tian.entity.ModEntities;
import com.yi_tian.event.ModSpitParticleHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public class YitianmodClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // 注册实体渲染器
        EntityRendererRegistry.register(ModEntities.LLAMA_MAGE , LlamaMageRenderer::new);
        EntityRendererRegistry.register(ModEntities.WARDERING_LLAMA, WanderingLlamaRenderer::new);
        EntityRendererRegistry.register(ModEntities.VILLAGER_LLAMA, VillagerLlamaRenderer::new);
        ModSpitParticleHandler.register();
    }
}
