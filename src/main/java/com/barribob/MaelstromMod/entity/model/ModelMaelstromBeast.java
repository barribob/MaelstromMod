package com.barribob.MaelstromMod.entity.model;

// Made with Blockbench 3.6.6

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelMaelstromBeast extends ModelBBAnimated {
	private final ModelRenderer root;
	private final ModelRenderer body;
	private final ModelRenderer neck;
	private final ModelRenderer head;
	private final ModelRenderer snout;
	private final ModelRenderer lowerJaw;
	private final ModelRenderer horseLeftEar;
	private final ModelRenderer horseRightEar;
	private final ModelRenderer leftHorn1;
	private final ModelRenderer leftHorn2;
	private final ModelRenderer leftHorn3;
	private final ModelRenderer leftHorn4;
	private final ModelRenderer rightHorn1;
	private final ModelRenderer rightHorn2;
	private final ModelRenderer rightHorn3;
	private final ModelRenderer rightHorn4;
	private final ModelRenderer mane;
	private final ModelRenderer rightArm;
	private final ModelRenderer rightArm2;
	private final ModelRenderer hammer_handle;
	private final ModelRenderer hammer1;
	private final ModelRenderer hammer2;
	private final ModelRenderer hammer3;
	private final ModelRenderer leftArm;
	private final ModelRenderer leftArm2;
	private final ModelRenderer tail;
	private final ModelRenderer leftLeg;
	private final ModelRenderer leftLeg1;
	private final ModelRenderer leftLeg2;
	private final ModelRenderer rightLeg;
	private final ModelRenderer rightLeg1;
	private final ModelRenderer rightLeg2;

	public ModelMaelstromBeast() {
		textureWidth = 128;
		textureHeight = 128;

		root = new ModelRenderer(this);
		root.setRotationPoint(0.0F, 0.0F, 0.0F);
		

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 2.0F, 0.0F);
		root.addChild(body);
		body.cubeList.add(new ModelBox(body, 0, 0, -6.0F, -16.0F, -6.0F, 12, 16, 8, 0.0F, true));

		neck = new ModelRenderer(this);
		neck.setRotationPoint(0.0F, -12.0F, -3.9F);
		body.addChild(neck);
		setRotationAngle(neck, 0.4554F, 0.0F, 0.0F);
		neck.cubeList.add(new ModelBox(neck, 100, 0, -1.95F, -9.8F, -2.0F, 4, 11, 8, 0.0F, true));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		neck.addChild(head);
		head.cubeList.add(new ModelBox(head, 58, 18, -2.5F, -10.0F, -1.5F, 5, 5, 7, 0.0F, true));

		snout = new ModelRenderer(this);
		snout.setRotationPoint(0.0F, 0.02F, 0.02F);
		head.addChild(snout);
		snout.cubeList.add(new ModelBox(snout, 0, 24, -2.0F, -10.0F, -7.0F, 4, 3, 6, 0.0F, true));

		lowerJaw = new ModelRenderer(this);
		lowerJaw.setRotationPoint(0.0F, -7.0F, -1.5F);
		head.addChild(lowerJaw);
		lowerJaw.cubeList.add(new ModelBox(lowerJaw, 15, 28, -2.0F, 0.0F, -5.0F, 4, 2, 5, 0.0F, true));

		horseLeftEar = new ModelRenderer(this);
		horseLeftEar.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(horseLeftEar);
		horseLeftEar.cubeList.add(new ModelBox(horseLeftEar, 0, 0, -2.45F, -12.0F, 4.0F, 2, 3, 1, 0.0F, true));

		horseRightEar = new ModelRenderer(this);
		horseRightEar.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(horseRightEar);
		horseRightEar.cubeList.add(new ModelBox(horseRightEar, 32, 0, 0.45F, -12.0F, 4.0F, 2, 3, 1, 0.0F, true));

		leftHorn1 = new ModelRenderer(this);
		leftHorn1.setRotationPoint(-2.0F, -7.5F, 4.0F);
		head.addChild(leftHorn1);
		leftHorn1.cubeList.add(new ModelBox(leftHorn1, 58, 0, -3.0F, -1.5F, -1.5F, 3, 3, 3, 0.0F, true));

		leftHorn2 = new ModelRenderer(this);
		leftHorn2.setRotationPoint(-2.5F, 0.0F, 0.1F);
		leftHorn1.addChild(leftHorn2);
		setRotationAngle(leftHorn2, 0.0F, 0.0F, 0.2276F);
		leftHorn2.cubeList.add(new ModelBox(leftHorn2, 58, 30, -3.0F, -1.5F, -1.5F, 3, 3, 3, 0.0F, true));

		leftHorn3 = new ModelRenderer(this);
		leftHorn3.setRotationPoint(-2.4F, 0.0F, 0.0F);
		leftHorn2.addChild(leftHorn3);
		setRotationAngle(leftHorn3, 0.0F, 0.0F, 0.5463F);
		leftHorn3.cubeList.add(new ModelBox(leftHorn3, 116, 0, -3.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F, true));

		leftHorn4 = new ModelRenderer(this);
		leftHorn4.setRotationPoint(-2.4F, 0.7F, 0.1F);
		leftHorn3.addChild(leftHorn4);
		setRotationAngle(leftHorn4, 0.0F, 0.0F, 0.5463F);
		leftHorn4.cubeList.add(new ModelBox(leftHorn4, 36, 2, -3.0F, -1.0F, -1.0F, 3, 1, 2, 0.0F, true));

		rightHorn1 = new ModelRenderer(this);
		rightHorn1.setRotationPoint(2.0F, -7.5F, 4.0F);
		head.addChild(rightHorn1);
		rightHorn1.cubeList.add(new ModelBox(rightHorn1, 52, 18, 0.0F, -1.5F, -1.5F, 3, 3, 3, 0.0F, true));

		rightHorn2 = new ModelRenderer(this);
		rightHorn2.setRotationPoint(2.5F, 0.0F, 0.1F);
		rightHorn1.addChild(rightHorn2);
		setRotationAngle(rightHorn2, 0.0F, 0.0F, 2.8684F);
		rightHorn2.cubeList.add(new ModelBox(rightHorn2, 70, 30, -3.0F, -1.5F, -1.5F, 3, 3, 3, 0.0F, true));

		rightHorn3 = new ModelRenderer(this);
		rightHorn3.setRotationPoint(-2.4F, 0.0F, 0.0F);
		rightHorn2.addChild(rightHorn3);
		setRotationAngle(rightHorn3, 0.0F, 0.0F, -0.5463F);
		rightHorn3.cubeList.add(new ModelBox(rightHorn3, 116, 4, -3.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F, true));

		rightHorn4 = new ModelRenderer(this);
		rightHorn4.setRotationPoint(-2.4F, 0.2F, 0.1F);
		rightHorn3.addChild(rightHorn4);
		setRotationAngle(rightHorn4, 0.0F, 0.0F, -0.5918F);
		rightHorn4.cubeList.add(new ModelBox(rightHorn4, 88, 10, -3.0F, -1.0F, -1.0F, 3, 1, 2, 0.0F, true));

		mane = new ModelRenderer(this);
		mane.setRotationPoint(0.0F, 0.0F, 0.0F);
		neck.addChild(mane);
		mane.cubeList.add(new ModelBox(mane, 106, 19, -1.0F, -11.5F, 5.0F, 2, 16, 4, 0.0F, true));

		rightArm = new ModelRenderer(this);
		rightArm.setRotationPoint(6.0F, -12.0F, -2.0F);
		body.addChild(rightArm);
		rightArm.cubeList.add(new ModelBox(rightArm, 82, 13, 0.0F, -3.0F, -3.0F, 6, 12, 6, 0.0F, true));

		rightArm2 = new ModelRenderer(this);
		rightArm2.setRotationPoint(3.0F, 10.0F, 0.0F);
		rightArm.addChild(rightArm2);
		setRotationAngle(rightArm2, -0.8652F, 0.0F, 0.0F);
		rightArm2.cubeList.add(new ModelBox(rightArm2, 82, 31, -2.0F, -3.0F, -2.0F, 4, 14, 4, 0.0F, true));

		hammer_handle = new ModelRenderer(this);
		hammer_handle.setRotationPoint(0.0F, 9.0F, 0.0F);
		rightArm2.addChild(hammer_handle);
		hammer_handle.cubeList.add(new ModelBox(hammer_handle, 74, 31, -1.0F, -1.0F, -24.0F, 2, 2, 24, 0.0F, true));

		hammer1 = new ModelRenderer(this);
		hammer1.setRotationPoint(0.0F, 0.0F, 0.0F);
		hammer_handle.addChild(hammer1);
		hammer1.cubeList.add(new ModelBox(hammer1, 0, 33, -2.0F, -3.0F, -26.0F, 4, 6, 4, 0.0F, true));

		hammer2 = new ModelRenderer(this);
		hammer2.setRotationPoint(0.0F, 0.0F, 0.0F);
		hammer1.addChild(hammer2);
		hammer2.cubeList.add(new ModelBox(hammer2, 16, 35, -3.0F, -9.0F, -27.0F, 6, 6, 6, 0.0F, true));

		hammer3 = new ModelRenderer(this);
		hammer3.setRotationPoint(0.0F, 0.0F, 0.0F);
		hammer1.addChild(hammer3);
		hammer3.cubeList.add(new ModelBox(hammer3, 40, 36, -3.0F, 3.0F, -27.0F, 6, 6, 6, 0.0F, true));

		leftArm = new ModelRenderer(this);
		leftArm.setRotationPoint(-6.0F, -12.0F, -2.0F);
		body.addChild(leftArm);
		leftArm.cubeList.add(new ModelBox(leftArm, 34, 18, -6.0F, -3.0F, -3.0F, 6, 12, 6, 0.0F, true));

		leftArm2 = new ModelRenderer(this);
		leftArm2.setRotationPoint(-3.0F, 10.0F, 0.0F);
		leftArm.addChild(leftArm2);
		setRotationAngle(leftArm2, -0.8652F, 0.0F, 0.0F);
		leftArm2.cubeList.add(new ModelBox(leftArm2, 64, 36, -2.0F, -3.0F, -2.0F, 4, 14, 4, 0.0F, true));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(tail);
		setRotationAngle(tail, 0.7285F, 0.0F, 0.0F);
		tail.cubeList.add(new ModelBox(tail, 88, 0, -2.0F, 0.0F, -1.0F, 4, 8, 2, 0.0F, true));

		leftLeg = new ModelRenderer(this);
		leftLeg.setRotationPoint(-3.0F, 0.6F, -2.0F);
		root.addChild(leftLeg);
		setRotationAngle(leftLeg, -0.384F, 0.0F, 0.1745F);
		leftLeg.cubeList.add(new ModelBox(leftLeg, 40, 0, -3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F, true));

		leftLeg1 = new ModelRenderer(this);
		leftLeg1.setRotationPoint(0.0F, 10.0F, 0.0F);
		leftLeg.addChild(leftLeg1);
		setRotationAngle(leftLeg1, 0.5009F, 0.0F, 0.0F);
		leftLeg1.cubeList.add(new ModelBox(leftLeg1, 102, 39, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, true));

		leftLeg2 = new ModelRenderer(this);
		leftLeg2.setRotationPoint(0.0F, 10.4F, 0.0F);
		leftLeg1.addChild(leftLeg2);
		leftLeg2.cubeList.add(new ModelBox(leftLeg2, 0, 47, -3.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F, true));

		rightLeg = new ModelRenderer(this);
		rightLeg.setRotationPoint(3.0F, 0.6F, -2.0F);
		root.addChild(rightLeg);
		setRotationAngle(rightLeg, -0.2793F, 0.0F, -0.1745F);
		rightLeg.cubeList.add(new ModelBox(rightLeg, 64, 0, -3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F, true));

		rightLeg1 = new ModelRenderer(this);
		rightLeg1.setRotationPoint(0.0F, 10.0F, 0.0F);
		rightLeg.addChild(rightLeg1);
		setRotationAngle(rightLeg1, 0.5009F, 0.0F, 0.0F);
		rightLeg1.cubeList.add(new ModelBox(rightLeg1, 24, 47, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, true));

		rightLeg2 = new ModelRenderer(this);
		rightLeg2.setRotationPoint(0.0F, 10.4F, 0.0F);
		rightLeg1.addChild(rightLeg2);
		rightLeg2.cubeList.add(new ModelBox(rightLeg2, 40, 48, -3.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		root.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		float limbSwingFactor = 0.4f;
		this.leftLeg.rotateAngleX = -0.384F + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * limbSwingFactor;
		this.rightLeg.rotateAngleX = -0.2793F + MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * limbSwingFactor;

		this.neck.rotateAngleY = Math.min(Math.max(netHeadYaw * 0.017453292F, -0.20F * (float) Math.PI), 0.20F * (float) Math.PI);
		this.neck.rotateAngleX = 0.4554F;
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
	}
}