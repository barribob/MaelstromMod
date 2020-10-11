package com.barribob.MaelstromMod.entity.model;
// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12

import com.barribob.MaelstromMod.entity.util.IAcceleration;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nonnull;

public class ModelMaelstromFury extends ModelBBAnimated {
	private final ModelRenderer root;
	private final ModelRenderer wisps;
	private final ModelRenderer body;
	private final ModelRenderer leftArm;
	private final ModelRenderer leftForearm;
	private final ModelRenderer leftHand;
	private final ModelRenderer spear;
	private final ModelRenderer guard;
	private final ModelRenderer leftCuff;
	private final ModelRenderer leftShoulderpad;
	private final ModelRenderer rightArm;
	private final ModelRenderer rightForearm;
	private final ModelRenderer rightHand;
	private final ModelRenderer rightCuff;
	private final ModelRenderer rightShoulderpad;
	private final ModelRenderer head;
	private final ModelRenderer headFrill;
	private final ModelRenderer wings;
	private final ModelRenderer left_wing;
	private final ModelRenderer left_wing2;
	private final ModelRenderer left_wing3;
	private final ModelRenderer left_wing4;
	private final ModelRenderer right_wing5;
	private final ModelRenderer right_wing6;
	private final ModelRenderer right_wing7;
	private final ModelRenderer right_wing8;

	public ModelMaelstromFury() {
		textureWidth = 128;
		textureHeight = 128;

		root = new ModelRenderer(this);
		root.setRotationPoint(0.0F, 5.0F, 9.0F);


		wisps = new ModelRenderer(this);
		wisps.setRotationPoint(0.0F, 7.0F, -9.0F);
		root.addChild(wisps);
		wisps.cubeList.add(new ModelBox(wisps, 0, 15, -3.5F, 2.0F, -2.0F, 7, 8, 4, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		wisps.addChild(body);
		body.cubeList.add(new ModelBox(body, 29, 0, -4.5F, -12.0F, -3.0F, 9, 14, 6, 0.0F, false));

		leftArm = new ModelRenderer(this);
		leftArm.setRotationPoint(4.5F, -10.0F, 0.0F);
		body.addChild(leftArm);
		leftArm.cubeList.add(new ModelBox(leftArm, 56, 22, 1.0F, -2.0F, -1.5F, 3, 6, 3, 0.0F, false));

		leftForearm = new ModelRenderer(this);
		leftForearm.setRotationPoint(2.5F, 4.0F, 0.0F);
		leftArm.addChild(leftForearm);
		leftForearm.cubeList.add(new ModelBox(leftForearm, 60, 36, -1.51F, 0.0F, -1.5F, 3, 4, 3, 0.0F, false));

		leftHand = new ModelRenderer(this);
		leftHand.setRotationPoint(0.0F, 4.0F, 0.0F);
		leftForearm.addChild(leftHand);
		leftHand.cubeList.add(new ModelBox(leftHand, 0, 43, -1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F, false));

		spear = new ModelRenderer(this);
		spear.setRotationPoint(0.0F, 4.0F, 0.0F);
		leftHand.addChild(spear);
		spear.cubeList.add(new ModelBox(spear, 0, 0, -0.5F, -1.0F, -21.0F, 1, 1, 27, 0.0F, false));

		guard = new ModelRenderer(this);
		guard.setRotationPoint(0.5F, 0.0F, 2.0F);
		spear.addChild(guard);
		guard.cubeList.add(new ModelBox(guard, 18, 15, -1.5F, -1.5F, -20.0F, 2, 2, 1, 0.0F, false));

		leftCuff = new ModelRenderer(this);
		leftCuff.setRotationPoint(2.5F, -4.0F, -1.0F);
		leftForearm.addChild(leftCuff);
		leftCuff.cubeList.add(new ModelBox(leftCuff, 53, 0, -4.5F, 6.0F, -1.0F, 4, 1, 4, 0.0F, false));

		leftShoulderpad = new ModelRenderer(this);
		leftShoulderpad.setRotationPoint(5.0F, 0.0F, -1.0F);
		leftArm.addChild(leftShoulderpad);
		leftShoulderpad.cubeList.add(new ModelBox(leftShoulderpad, 13, 43, -4.5F, -2.5F, -1.0F, 4, 2, 4, 0.0F, false));

		rightArm = new ModelRenderer(this);
		rightArm.setRotationPoint(-4.5F, -10.0F, 0.0F);
		body.addChild(rightArm);
		rightArm.cubeList.add(new ModelBox(rightArm, 59, 5, -4.0F, -2.0F, -1.5F, 3, 6, 3, 0.0F, false));

		rightForearm = new ModelRenderer(this);
		rightForearm.setRotationPoint(-2.5F, 4.0F, 0.0F);
		rightArm.addChild(rightForearm);
		rightForearm.cubeList.add(new ModelBox(rightForearm, 0, 61, -1.51F, 0.0F, -1.5F, 3, 4, 3, 0.0F, false));

		rightHand = new ModelRenderer(this);
		rightHand.setRotationPoint(0.0F, 4.0F, 0.0F);
		rightForearm.addChild(rightHand);
		rightHand.cubeList.add(new ModelBox(rightHand, 59, 14, -1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F, false));

		rightCuff = new ModelRenderer(this);
		rightCuff.setRotationPoint(2.5F, -4.0F, -1.0F);
		rightForearm.addChild(rightCuff);
		rightCuff.cubeList.add(new ModelBox(rightCuff, 56, 31, -4.5F, 6.0F, -1.0F, 4, 1, 4, 0.0F, false));

		rightShoulderpad = new ModelRenderer(this);
		rightShoulderpad.setRotationPoint(0.0F, 0.0F, -1.0F);
		rightArm.addChild(rightShoulderpad);
		rightShoulderpad.cubeList.add(new ModelBox(rightShoulderpad, 44, 28, -4.5F, -2.5F, -1.0F, 4, 2, 4, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -14.0F, 0.0F);
		body.addChild(head);
		head.cubeList.add(new ModelBox(head, 36, 36, -4.0F, -6.0F, -4.0F, 8, 8, 8, 0.0F, false));

		headFrill = new ModelRenderer(this);
		headFrill.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(headFrill);
		headFrill.cubeList.add(new ModelBox(headFrill, 0, 43, -1.0F, -7.1F, -5.1F, 2, 9, 9, 0.0F, false));

		wings = new ModelRenderer(this);
		wings.setRotationPoint(0.0F, -10.0F, 3.0F);
		body.addChild(wings);
		setRotationAngle(wings, 0.2618F, 0.0F, 0.0F);


		left_wing = new ModelRenderer(this);
		left_wing.setRotationPoint(0.0F, 0.5F, 0.5F);
		wings.addChild(left_wing);
		setRotationAngle(left_wing, 0.0F, 0.0F, -0.3491F);
		left_wing.cubeList.add(new ModelBox(left_wing, 29, 22, 0.0F, -0.5F, -0.5F, 10, 1, 1, 0.0F, false));
		left_wing.cubeList.add(new ModelBox(left_wing, 22, 52, 0.0F, 0.5F, 0.0F, 12, 15, 0, 0.0F, false));

		left_wing2 = new ModelRenderer(this);
		left_wing2.setRotationPoint(10.0F, 0.0F, 0.0F);
		left_wing.addChild(left_wing2);
		setRotationAngle(left_wing2, 0.0F, 0.2618F, -0.3491F);
		left_wing2.cubeList.add(new ModelBox(left_wing2, 13, 49, 0.0F, -0.5F, -0.5F, 8, 1, 1, 0.0F, false));
		left_wing2.cubeList.add(new ModelBox(left_wing2, 0, 0, -3.0F, 0.5F, 0.1F, 13, 15, 0, 0.0F, false));

		left_wing3 = new ModelRenderer(this);
		left_wing3.setRotationPoint(8.0F, 0.0F, 0.0F);
		left_wing2.addChild(left_wing3);
		setRotationAngle(left_wing3, 0.0F, 0.0873F, -0.3491F);
		left_wing3.cubeList.add(new ModelBox(left_wing3, 29, 24, 0.0F, -0.5F, -0.5F, 8, 1, 1, 0.0F, false));
		left_wing3.cubeList.add(new ModelBox(left_wing3, 46, 52, -3.0F, 0.5F, 0.0F, 11, 15, 0, 0.0F, false));

		left_wing4 = new ModelRenderer(this);
		left_wing4.setRotationPoint(8.0F, 0.0F, 0.0F);
		left_wing3.addChild(left_wing4);
		setRotationAngle(left_wing4, 0.0F, 0.0873F, 0.6109F);
		left_wing4.cubeList.add(new ModelBox(left_wing4, 0, 28, 0.0F, 0.5F, 0.1F, 22, 15, 0, 0.0F, false));
		left_wing4.cubeList.add(new ModelBox(left_wing4, 29, 20, 0.0F, -0.5F, -0.5F, 22, 1, 1, 0.0F, false));

		right_wing5 = new ModelRenderer(this);
		right_wing5.setRotationPoint(0.0F, 0.5F, 0.5F);
		wings.addChild(right_wing5);
		setRotationAngle(right_wing5, 0.0F, 0.0F, 0.3491F);
		right_wing5.cubeList.add(new ModelBox(right_wing5, 29, 22, -10.0F, -0.5F, -0.5F, 10, 1, 1, 0.0F, true));
		right_wing5.cubeList.add(new ModelBox(right_wing5, 22, 52, -12.0F, 0.5F, 0.0F, 12, 15, 0, 0.0F, true));

		right_wing6 = new ModelRenderer(this);
		right_wing6.setRotationPoint(-10.0F, 0.0F, 0.0F);
		right_wing5.addChild(right_wing6);
		setRotationAngle(right_wing6, 0.0F, -0.2618F, 0.3491F);
		right_wing6.cubeList.add(new ModelBox(right_wing6, 13, 49, -8.0F, -0.5F, -0.5F, 8, 1, 1, 0.0F, true));
		right_wing6.cubeList.add(new ModelBox(right_wing6, 0, 0, -10.0F, 0.5F, 0.1F, 13, 15, 0, 0.0F, true));

		right_wing7 = new ModelRenderer(this);
		right_wing7.setRotationPoint(-8.0F, 0.0F, 0.0F);
		right_wing6.addChild(right_wing7);
		setRotationAngle(right_wing7, 0.0F, -0.0873F, 0.3491F);
		right_wing7.cubeList.add(new ModelBox(right_wing7, 29, 24, -8.0F, -0.5F, -0.5F, 8, 1, 1, 0.0F, true));
		right_wing7.cubeList.add(new ModelBox(right_wing7, 46, 52, -8.0F, 0.5F, 0.0F, 11, 15, 0, 0.0F, true));

		right_wing8 = new ModelRenderer(this);
		right_wing8.setRotationPoint(-8.0F, 0.0F, 0.0F);
		right_wing7.addChild(right_wing8);
		setRotationAngle(right_wing8, 0.0F, -0.0873F, -0.6109F);
		right_wing8.cubeList.add(new ModelBox(right_wing8, 0, 28, -22.0F, 0.5F, 0.1F, 22, 15, 0, 0.0F, true));
		right_wing8.cubeList.add(new ModelBox(right_wing8, 29, 20, -22.0F, -0.5F, -0.5F, 22, 1, 1, 0.0F, true));
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, @Nonnull Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.head.rotateAngleX = headPitch * 0.017453292F;

		if (entity instanceof IAcceleration) {
			Vec3d acceleration = ((IAcceleration) entity).getAcceleration();
			this.root.rotateAngleX = (float) Math.toRadians(ModUtils.toPitch(acceleration.add(entity.getLookVec().scale(0.1))) + 90);
		}
	}

	@Override
	public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		root.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}