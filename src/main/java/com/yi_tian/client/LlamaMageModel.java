package com.yi_tian.client;

import com.yi_tian.entity.llama.LlamaMageEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static com.yi_tian.Yitianmod.MOD_ID;

// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class LlamaMageModel extends GeoModel<LlamaMageEntity> {
	@Override
	public Identifier getModelResource(LlamaMageEntity animatable) {
		return new Identifier(MOD_ID,"geo/llama_mage.geo.json");
	}

	@Override
	public Identifier getTextureResource(LlamaMageEntity animatable) {
		return new Identifier(MOD_ID,"textures/entity/llama_mage.png");
	}

	@Override
	public Identifier getAnimationResource(LlamaMageEntity animatable) {
		return new Identifier(MOD_ID,"animations/llama_mage.animation.json");
	}

	@Override
	public void setCustomAnimations(LlamaMageEntity animatable, long instanceId, AnimationState<LlamaMageEntity> animationState) {
		CoreGeoBone head=getAnimationProcessor().getBone("head");
		if(head!=null){
			EntityModelData entityModelData=animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityModelData.headPitch()*MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityModelData.netHeadYaw()*MathHelper.RADIANS_PER_DEGREE);
		}
//		super.setCustomAnimations(animatable, instanceId, animationState);
	}
	//	private final ModelPart LlamaMage;
//	private final ModelPart arm2;
//	private final ModelPart leg3;
//	private final ModelPart stave;
//	private final ModelPart leg1;
//	private final ModelPart body;
//	private final ModelPart arm1;
//	private final ModelPart head;
//	private final ModelPart bone;
//	public LlamaMageModel(ModelPart root) {
//		this.LlamaMage = root.getChild("LlamaMage");
//		this.arm2 = this.LlamaMage.getChild("arm2");
//		this.leg3 = this.LlamaMage.getChild("leg3");
//		this.stave = this.LlamaMage.getChild("stave");
//		this.leg1 = this.LlamaMage.getChild("leg1");
//		this.body = this.LlamaMage.getChild("body");
//		this.arm1 = this.LlamaMage.getChild("arm1");
//		this.head = this.LlamaMage.getChild("head");
//		this.bone = this.head.getChild("bone");
//	}
//	public static TexturedModelData getTexturedModelData() {
//		ModelData modelData = new ModelData();
//		ModelPartData modelPartData = modelData.getRoot();
//		ModelPartData LlamaMage = modelPartData.addChild("LlamaMage", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
//
//		ModelPartData arm2 = LlamaMage.addChild("arm2", ModelPartBuilder.create().uv(29, 29).cuboid(2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, new Dilation(0.0F))
//				.uv(79, 56).cuboid(1.0F, -1.0F, 3.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F))
//				.uv(79, 56).cuboid(1.0F, -1.0F, -3.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, -30.0F, -2.0F, -0.3054F, 0.0F, 0.0F));
//
//		ModelPartData cube_r1 = arm2.addChild("cube_r1", ModelPartBuilder.create().uv(79, 56).cuboid(-5.0F, -6.0F, -1.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, 0.0F, -3.0F, -1.5708F, 0.0F, 0.0F));
//
//		ModelPartData cube_r2 = arm2.addChild("cube_r2", ModelPartBuilder.create().uv(79, 56).cuboid(-5.0F, -8.0F, -1.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, 7.0F, 2.0F, 0.0F, -1.5708F, 0.0F));
//
//		ModelPartData cube_r3 = arm2.addChild("cube_r3", ModelPartBuilder.create().uv(79, 56).cuboid(-5.0F, -8.0F, -1.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 7.0F, 2.0F, 0.0F, -1.5708F, 0.0F));
//
//		ModelPartData leg3 = LlamaMage.addChild("leg3", ModelPartBuilder.create().uv(29, 29).cuboid(-2.0F, -2.0F, -3.0F, 4.0F, 14.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -12.0F, 0.0F));
//
//		ModelPartData stave = LlamaMage.addChild("stave", ModelPartBuilder.create().uv(2, 58).cuboid(0.0F, -30.0F, -16.0F, 1.0F, 35.0F, 2.0F, new Dilation(0.0F))
//				.uv(71, 71).cuboid(-1.0F, -30.0F, -14.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
//				.uv(53, 78).cuboid(-2.0F, -31.0F, -13.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(30, 81).cuboid(-3.0F, -34.0F, -12.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
//				.uv(30, 81).cuboid(-3.0F, -34.0F, -12.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
//				.uv(37, 71).cuboid(-2.0F, -31.0F, -12.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-4.0F, -35.0F, -11.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-3.0F, -31.0F, -13.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-3.0F, -31.0F, -13.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(30, 81).cuboid(-2.0F, -38.0F, -13.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-1.0F, -29.0F, -15.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(0.0F, -29.0F, -14.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(59, 94).cuboid(0.0F, -34.0F, -16.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
//				.uv(59, 96).cuboid(1.0F, -32.0F, -16.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(59, 96).cuboid(-1.0F, -35.0F, -16.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(59, 96).cuboid(1.0F, -34.0F, -14.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-10.0F, -9.0F, 9.0F, 0.2182F, 0.0F, 0.0F));
//
//		ModelPartData cube_r4 = stave.addChild("cube_r4", ModelPartBuilder.create().uv(2, 80).cuboid(1.0F, -1.0F, -1.0F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -24.0F, -21.0F, 0.6062F, -0.0545F, 1.0093F));
//
//		ModelPartData cube_r5 = stave.addChild("cube_r5", ModelPartBuilder.create().uv(2, 80).cuboid(1.0F, -1.0F, -1.0F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-6.0F, -29.0F, -12.0F, 0.6062F, -0.0545F, 1.0093F));
//
//		ModelPartData cube_r6 = stave.addChild("cube_r6", ModelPartBuilder.create().uv(2, 80).cuboid(1.0F, -1.0F, -1.0F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-6.0F, -26.0F, -22.0F, 0.1868F, -0.2209F, 1.6155F));
//
//		ModelPartData cube_r7 = stave.addChild("cube_r7", ModelPartBuilder.create().uv(32, 66).cuboid(-0.5F, -0.75F, 0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-1.5F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(30, 81).cuboid(-2.5F, -9.75F, 1.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-3.5F, -2.75F, 1.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-3.5F, -2.75F, 1.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(37, 71).cuboid(-2.5F, -2.75F, 2.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-4.5F, -6.75F, 3.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(30, 81).cuboid(-3.5F, -5.75F, 2.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
//				.uv(30, 81).cuboid(-3.5F, -5.75F, 2.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
//				.uv(53, 78).cuboid(-2.5F, -2.75F, 1.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(71, 71).cuboid(-1.5F, -1.75F, 0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -28.25F, -14.5F, 0.0F, 1.5708F, 0.0F));
//
//		ModelPartData cube_r8 = stave.addChild("cube_r8", ModelPartBuilder.create().uv(32, 66).cuboid(-0.5F, -0.75F, 0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-1.5F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(30, 81).cuboid(-2.5F, -9.75F, 1.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-3.5F, -2.75F, 1.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-3.5F, -2.75F, 1.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(37, 71).cuboid(-2.5F, -2.75F, 2.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-4.5F, -6.75F, 3.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(30, 81).cuboid(-3.5F, -5.75F, 2.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
//				.uv(30, 81).cuboid(-3.5F, -5.75F, 2.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
//				.uv(53, 78).cuboid(-2.5F, -2.75F, 1.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(71, 71).cuboid(-1.5F, -1.75F, 0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -28.25F, -15.5F, 0.0F, 3.1416F, 0.0F));
//
//		ModelPartData cube_r9 = stave.addChild("cube_r9", ModelPartBuilder.create().uv(32, 66).cuboid(-0.5F, -0.75F, 0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-1.5F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(30, 81).cuboid(-2.5F, -9.75F, 1.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-3.5F, -2.75F, 1.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-3.5F, -2.75F, 1.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(32, 66).cuboid(-4.5F, -6.75F, 3.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(37, 71).cuboid(-2.5F, -2.75F, 2.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(30, 81).cuboid(-3.5F, -5.75F, 2.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
//				.uv(30, 81).cuboid(-3.5F, -5.75F, 2.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
//				.uv(53, 78).cuboid(-2.5F, -2.75F, 1.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(71, 71).cuboid(-1.5F, -1.75F, 0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -28.25F, -15.5F, 0.0F, -1.5708F, 0.0F));
//
//		ModelPartData cube_r10 = stave.addChild("cube_r10", ModelPartBuilder.create().uv(2, 0).cuboid(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, -3.0F, -11.0F, -0.4606F, -0.3152F, 0.1526F));
//
//		ModelPartData cube_r11 = stave.addChild("cube_r11", ModelPartBuilder.create().uv(2, 0).cuboid(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -3.0F, -11.0F, -0.8687F, -0.5917F, 0.6211F));
//
//		ModelPartData leg1 = LlamaMage.addChild("leg1", ModelPartBuilder.create().uv(29, 29).cuboid(-1.0F, -1.0F, -1.0F, 4.0F, 14.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -13.0F, -2.0F));
//
//		ModelPartData body = LlamaMage.addChild("body", ModelPartBuilder.create().uv(29, 0).cuboid(2.0F, -18.0F, -1.0F, 12.0F, 18.0F, 10.0F, new Dilation(0.0F))
//				.uv(32, 88).cuboid(1.0F, -19.0F, -2.0F, 14.0F, 11.0F, 0.0F, new Dilation(0.0F))
//				.uv(18, 88).cuboid(1.0F, -19.0F, 10.0F, 14.0F, 11.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.0F, -13.0F, -4.0F));
//
//		ModelPartData cube_r12 = body.addChild("cube_r12", ModelPartBuilder.create().uv(58, 86).cuboid(-11.0F, -14.0F, -1.0F, 12.0F, 14.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(15.0F, -18.0F, 9.0F, -1.5708F, -1.5708F, 0.0F));
//
//		ModelPartData cube_r13 = body.addChild("cube_r13", ModelPartBuilder.create().uv(20, 88).cuboid(-11.0F, -11.0F, -1.0F, 12.0F, 11.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(14.0F, -8.0F, 9.0F, 0.0F, -1.5708F, 0.0F));
//
//		ModelPartData cube_r14 = body.addChild("cube_r14", ModelPartBuilder.create().uv(20, 88).cuboid(-11.0F, -11.0F, -1.0F, 12.0F, 11.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, 9.0F, 0.0F, -1.5708F, 0.0F));
//
//		ModelPartData arm1 = LlamaMage.addChild("arm1", ModelPartBuilder.create().uv(29, 29).cuboid(0.0F, -3.0F, -6.0F, 4.0F, 14.0F, 4.0F, new Dilation(0.0F))
//				.uv(79, 56).cuboid(-1.0F, -4.0F, -1.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F))
//				.uv(79, 56).cuboid(-1.0F, -4.0F, -7.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -26.0F, 1.0F, -0.3054F, 0.0F, 0.0F));
//
//		ModelPartData cube_r15 = arm1.addChild("cube_r15", ModelPartBuilder.create().uv(79, 56).cuboid(-5.0F, -6.0F, -1.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, -3.0F, -7.0F, -1.5708F, 0.0F, 0.0F));
//
//		ModelPartData cube_r16 = arm1.addChild("cube_r16", ModelPartBuilder.create().uv(79, 56).cuboid(-5.0F, -8.0F, -1.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 4.0F, -2.0F, 0.0F, -1.5708F, 0.0F));
//
//		ModelPartData cube_r17 = arm1.addChild("cube_r17", ModelPartBuilder.create().uv(79, 56).cuboid(-5.0F, -8.0F, -1.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, 4.0F, -2.0F, 0.0F, -1.5708F, 0.0F));
//
//		ModelPartData head = LlamaMage.addChild("head", ModelPartBuilder.create().uv(0, 14).cuboid(-7.0F, -16.0F, -3.0F, 8.0F, 18.0F, 6.0F, new Dilation(0.0F))
//				.uv(5, 5).cuboid(-5.0F, -14.0F, -7.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F))
//				.uv(17, 0).cuboid(-7.0F, -19.0F, -1.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
//				.uv(17, 0).cuboid(-2.0F, -19.0F, -1.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
//				.uv(11, 52).cuboid(1.0F, -6.0F, -4.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F))
//				.uv(11, 52).cuboid(-8.0F, -6.0F, -4.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F))
//				.uv(84, 39).cuboid(-6.0F, -5.0F, -4.0F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F))
//				.uv(84, 39).cuboid(-2.0F, -5.0F, -4.0F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F))
//				.uv(11, 52).cuboid(-8.0F, -17.0F, -4.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F))
//				.uv(11, 52).cuboid(1.0F, -17.0F, -4.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F))
//				.uv(-1, -2).cuboid(1.0F, -15.0F, -2.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
//				.uv(2, 1).cuboid(1.0F, -16.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(2, 1).cuboid(1.0F, -16.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(-1, -2).cuboid(-8.0F, -15.0F, -2.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
//				.uv(2, 1).cuboid(-8.0F, -16.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
//				.uv(2, 1).cuboid(-8.0F, -16.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -33.0F, -2.0F, 0.0436F, 0.0F, 0.0F));
//
//		ModelPartData cube_r18 = head.addChild("cube_r18", ModelPartBuilder.create().uv(84, 39).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F))
//				.uv(84, 39).cuboid(-1.0F, 2.0F, -1.0F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -7.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
//
//		ModelPartData cube_r19 = head.addChild("cube_r19", ModelPartBuilder.create().uv(84, 39).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F))
//				.uv(84, 39).cuboid(-1.0F, 2.0F, -1.0F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, -7.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
//
//		ModelPartData cube_r20 = head.addChild("cube_r20", ModelPartBuilder.create().uv(11, 52).cuboid(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F))
//				.uv(11, 52).cuboid(0.0F, 10.0F, -1.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -16.0F, 3.0F, 0.0F, -1.5708F, 0.0F));
//
//		ModelPartData cube_r21 = head.addChild("cube_r21", ModelPartBuilder.create().uv(11, 52).cuboid(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F))
//				.uv(11, 52).cuboid(0.0F, 10.0F, -1.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -16.0F, -4.0F, 0.0F, -1.5708F, 0.0F));
//
//		ModelPartData bone = head.addChild("bone", ModelPartBuilder.create().uv(76, 5).cuboid(-5.0F, 5.0F, -7.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -16.0F, 0.0F));
//		return TexturedModelData.of(modelData, 100, 100);
//	}
////	@Override
////	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
////		LlamaMage.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
////	}
//
//	@Override
//	public ModelPart getPart() {
//		return this.LlamaMage;
//	}
//	private void setHeadAngles(float headYaw, float headPitch) {
//		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
//		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);
//		this.head.yaw = headYaw * 0.017453292f;
//		this.head.pitch = headPitch * 0.017453292f;
//	}
//	@Override
//	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
//		this.getPart().traverse().forEach(ModelPart::resetTransform);
//		this.setHeadAngles(headYaw,headPitch);
//		this.animateMovement(LlamaMageAnimation.WALK, limbAngle, limbDistance, 2f, 2.5f);
//		this.updateAnimation(LlamaMageEntity.idleAnimation, LlamaMageAnimation.IDLE, animationProgress, 1f);
//		this.animateMovement(LlamaMageAnimation.PLAY, limbAngle, limbDistance, 2f,2.5f);
//	}
//	@Override
//	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
//		if (this.child) {
//			float f = 2.0F;
//			matrices.push();
//			float g = 0.7F;
//			matrices.scale(0.71428573F, 0.64935064F, 0.7936508F);
//			matrices.translate(0.0F, 3.0325F, 0.09F);
//			this.head.render(matrices, vertices, light, overlay, red, green, blue, alpha);
//			matrices.pop();
//			matrices.push();
//			float h = 1.1F;
//			matrices.scale(0.625F, 0.45454544F, 0.45454544F);
//			matrices.translate(0.0F, 3.4000F, 0.0F);
//			this.body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
//			matrices.pop();
//			matrices.push();
//			matrices.scale(0.45454544F, 0.41322312F, 0.45454544F);
//			matrices.translate(0.0F, 3.4500F, 0.0F);
//			ImmutableList.of(this.stave, this.arm1, this.arm2, this.leg1, this.leg3)
//					.forEach(part -> part.render(matrices, vertices, light, overlay, red, green, blue, alpha));
//			matrices.pop();
//		} else {
//			LlamaMage.render(matrices, vertices, light, overlay, red, green, blue, alpha);
//		}
//	}
}