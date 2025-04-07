package com.yi_tian.client;

import com.yi_tian.entity.llama.VillagerLlamaEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static com.yi_tian.Yitianmod.MOD_ID;

public class VillagerLlamaModel extends GeoModel<VillagerLlamaEntity> {
    @Override
    public Identifier getModelResource(VillagerLlamaEntity animatable) {
        return new Identifier(MOD_ID, "geo/villager_llama.geo.json");
    }

    @Override
    public Identifier getTextureResource(VillagerLlamaEntity animatable) {
        return new Identifier(MOD_ID, "textures/entity/villager_llama.png");
    }

    @Override
    public Identifier getAnimationResource(VillagerLlamaEntity animatable) {
        return new Identifier(MOD_ID, "animations/villager_llama.animation.json");
    }

    @Override
    public void setCustomAnimations(VillagerLlamaEntity animatable, long instanceId, AnimationState<VillagerLlamaEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityModelData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityModelData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityModelData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
//		super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
