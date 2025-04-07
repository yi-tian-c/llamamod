package com.yi_tian.client.render;

import com.yi_tian.client.WanderingLlamaModel;
import com.yi_tian.entity.llama.WanderingLlamaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static com.yi_tian.Yitianmod.MOD_ID;


public class WanderingLlamaRenderer extends GeoEntityRenderer<WanderingLlamaEntity> {
    public WanderingLlamaRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new WanderingLlamaModel());
    }

    @Override
    public Identifier getTexture(WanderingLlamaEntity animatable) {
        return new Identifier(MOD_ID, "textures/entity/wandering_llama_trader.png");
    }

    @Override
    public void render(WanderingLlamaEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if (entity.isBaby()) {

            poseStack.scale(0.4f, 0.4f, 0.4f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}