package com.yi_tian.client.render;

import com.yi_tian.client.VillagerLlamaModel;
import com.yi_tian.entity.llama.VillagerLlamaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static com.yi_tian.Yitianmod.MOD_ID;


public class VillagerLlamaRenderer extends GeoEntityRenderer<VillagerLlamaEntity> {
    public VillagerLlamaRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new VillagerLlamaModel());
    }

    @Override
    public Identifier getTexture(VillagerLlamaEntity animatable) {
        return new Identifier(MOD_ID, "textures/entity/villager_llama.png");
    }

    @Override
    public void render(VillagerLlamaEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if (entity.isBaby()) {

            poseStack.scale(0.4f, 0.4f, 0.4f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
