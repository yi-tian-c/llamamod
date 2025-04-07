package com.yi_tian.client;

import com.yi_tian.entity.llama.WanderingLlamaEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static com.yi_tian.Yitianmod.MOD_ID;


public class WanderingLlamaModel extends GeoModel<WanderingLlamaEntity> {
    @Override
    public Identifier getModelResource(WanderingLlamaEntity animatable) {
        return new Identifier(MOD_ID, "geo/wandering_llama_trader.geo.json");
    }

    @Override
    public Identifier getTextureResource(WanderingLlamaEntity animatable) {
        return new Identifier(MOD_ID, "textures/entity/wandering_llama_trader.png");
    }

    @Override
    public Identifier getAnimationResource(WanderingLlamaEntity animatable) {
        return new Identifier(MOD_ID, "animations/wandering_llama_trader.animation.json");
    }

    @Override
    public void setCustomAnimations(WanderingLlamaEntity animatable, long instanceId, AnimationState<WanderingLlamaEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityModelData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityModelData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityModelData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
//		super.setCustomAnimations(animatable, instanceId, animationState);
    }
}