package com.yi_tian.client.render;

import com.yi_tian.client.LlamaMageModel;
import com.yi_tian.entity.llama.LlamaMageEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static com.yi_tian.Yitianmod.MOD_ID;

public class LlamaMageRenderer extends GeoEntityRenderer<LlamaMageEntity> {
    public LlamaMageRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new LlamaMageModel());
    }

    @Override
    public Identifier getTexture(LlamaMageEntity animatable) {
        return new Identifier(MOD_ID,"textures/entity/llama_mage.png");
    }

    @Override
    public void render(LlamaMageEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if (entity.isBaby()){

            poseStack.scale(0.4f,0.4f,0.4f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
    //    public static final Identifier TEXTURE=new Identifier(MOD_ID,"textures/entity/llama_mage.png");
//    public LlamaMageRenderer(EntityRendererFactory.Context context) {
//        super(context,new LlamaMageModel<>(context.getPart(ModModelLayers.LLAMA_MAGE)),0.7f);
//    }
//
//    @Override
//    public Identifier getTexture(LlamaMageEntity entity) {
//        return TEXTURE;
//    }
//    @Override
//    public void render(LlamaMageEntity entity, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider,int light){
//        if (entity.isBaby()) {
//            matrices.scale(0.6f, 0.6f, 0.6f);
////            Vector3f v=new Vector3f(0.8f,0.8f,0.8f);
////            this.head.scale(v);
//        } else {
//            matrices.scale(1.0f, 1.0f, 1.0f);
//        }
//        super.render(entity, f, g, matrices, vertexConsumerProvider, light);
//    }
}
