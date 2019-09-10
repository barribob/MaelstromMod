package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Barribob Created using Tabula 7.0.0
 */
public class ModelMaelstromBeast extends ModelAnimated
{
    public ModelRenderer body;
    public ModelRenderer leftLeg;
    public ModelRenderer rightLeg;
    public ModelRenderer tail;
    public ModelRenderer neck;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer head;
    public ModelRenderer mane;
    public ModelRenderer snout;
    public ModelRenderer lowerJaw;
    public ModelRenderer horseLeftEar;
    public ModelRenderer horseRightEar;
    public ModelRenderer leftHorn1;
    public ModelRenderer rightHorn1;
    public ModelRenderer leftHorn2;
    public ModelRenderer leftHorn3;
    public ModelRenderer leftHorn4;
    public ModelRenderer rightHorn2;
    public ModelRenderer rightHorn3;
    public ModelRenderer rightHorn4;
    public ModelRenderer rightArm2;
    public ModelRenderer hammer_handle;
    public ModelRenderer hammer1;
    public ModelRenderer hammer2;
    public ModelRenderer hammer3;
    public ModelRenderer leftArm2;
    public ModelRenderer leftLeg1;
    public ModelRenderer leftLeg2;
    public ModelRenderer rightLeg1;
    public ModelRenderer rightLeg2;

    private float defaultNeckRotation = 0.4553564018453205F;
    private float defaultLegRotation = -0.3839724354387525F;
    public static final float defaultBodyRotation = 0.27314402793711257F;

    public ModelMaelstromBeast() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.rightArm = new ModelRenderer(this, 82, 13);
        this.rightArm.setRotationPoint(-6.0F, -12.0F, -2.0F);
        this.rightArm.addBox(-6.0F, -3.0F, -3.0F, 6, 12, 6, 0.0F);
        this.hammer2 = new ModelRenderer(this, 16, 35);
        this.hammer2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hammer2.addBox(-3.0F, -9.0F, -27.0F, 6, 6, 6, 0.0F);
        this.leftHorn2 = new ModelRenderer(this, 58, 30);
        this.leftHorn2.setRotationPoint(2.5F, 0.0F, 0.1F);
        this.leftHorn2.addBox(0.0F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
        this.setRotateAngle(leftHorn2, 0.0F, 0.0F, -0.22759093446006054F);
        this.rightHorn2 = new ModelRenderer(this, 70, 30);
        this.rightHorn2.setRotationPoint(-2.5F, 0.0F, 0.1F);
        this.rightHorn2.addBox(0.0F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
        this.setRotateAngle(rightHorn2, 0.0F, 0.0F, -2.86844862565268F);
        this.rightLeg2 = new ModelRenderer(this, 40, 48);
        this.rightLeg2.setRotationPoint(0.0F, 10.4F, 0.0F);
        this.rightLeg2.addBox(-3.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.rightLeg1 = new ModelRenderer(this, 24, 47);
        this.rightLeg1.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.rightLeg1.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(rightLeg1, 0.5009094953223726F, 0.0F, 0.0F);
        this.leftLeg2 = new ModelRenderer(this, 0, 47);
        this.leftLeg2.setRotationPoint(0.0F, 10.4F, 0.0F);
        this.leftLeg2.addBox(-3.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.snout = new ModelRenderer(this, 0, 24);
        this.snout.setRotationPoint(0.0F, 0.02F, 0.02F);
        this.snout.addBox(-2.0F, -10.0F, -7.0F, 4, 3, 6, 0.0F);
        this.hammer1 = new ModelRenderer(this, 0, 33);
        this.hammer1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hammer1.addBox(-2.0F, -3.0F, -26.0F, 4, 6, 4, 0.0F);
        this.hammer_handle = new ModelRenderer(this, 74, 31);
        this.hammer_handle.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.hammer_handle.addBox(-1.0F, -1.0F, -24.0F, 2, 2, 24, 0.0F);
        this.leftHorn3 = new ModelRenderer(this, 116, 0);
        this.leftHorn3.setRotationPoint(2.4F, 0.0F, 0.0F);
        this.leftHorn3.addBox(0.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F);
        this.setRotateAngle(leftHorn3, 0.0F, 0.0F, -0.5462880558742251F);
        this.rightArm2 = new ModelRenderer(this, 82, 31);
        this.rightArm2.setRotationPoint(-3.0F, 10.0F, 0.0F);
        this.rightArm2.addBox(-2.0F, -3.0F, -2.0F, 4, 14, 4, 0.0F);
        this.setRotateAngle(rightArm2, -0.8651597102135892F, 0.0F, 0.0F);
        this.leftArm2 = new ModelRenderer(this, 64, 36);
        this.leftArm2.setRotationPoint(3.0F, 10.0F, 0.0F);
        this.leftArm2.addBox(-2.0F, -3.0F, -2.0F, 4, 14, 4, 0.0F);
        this.setRotateAngle(leftArm2, -0.8651597102135892F, 0.0F, 0.0F);
        this.lowerJaw = new ModelRenderer(this, 15, 28);
        this.lowerJaw.setRotationPoint(0.0F, -7.0F, -1.5F);
        this.lowerJaw.addBox(-2.0F, 0.0F, -5.0F, 4, 2, 5, 0.0F);
        this.neck = new ModelRenderer(this, 100, 0);
        this.neck.setRotationPoint(0.0F, -12.0F, -3.9F);
        this.neck.addBox(-2.05F, -9.8F, -2.0F, 4, 11, 8, 0.0F);
        this.setRotateAngle(neck, 0.4553564018453205F, 0.0F, 0.0F);
        this.rightHorn4 = new ModelRenderer(this, 88, 10);
        this.rightHorn4.setRotationPoint(2.4F, 0.2F, 0.1F);
        this.rightHorn4.addBox(0.0F, -1.0F, -1.0F, 3, 1, 2, 0.0F);
        this.setRotateAngle(rightHorn4, 0.0F, 0.0F, 0.5918411493512771F);
        this.leftArm = new ModelRenderer(this, 34, 18);
        this.leftArm.setRotationPoint(6.0F, -12.0F, -2.0F);
        this.leftArm.addBox(0.0F, -3.0F, -3.0F, 6, 12, 6, 0.0F);
        this.leftLeg1 = new ModelRenderer(this, 102, 39);
        this.leftLeg1.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.leftLeg1.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(leftLeg1, 0.5009094953223726F, 0.0F, 0.0F);
        this.rightHorn3 = new ModelRenderer(this, 116, 4);
        this.rightHorn3.setRotationPoint(2.4F, 0.0F, 0.0F);
        this.rightHorn3.addBox(0.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F);
        this.setRotateAngle(rightHorn3, 0.0F, 0.0F, 0.5462880558742251F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.body.addBox(-6.0F, -16.0F, -6.0F, 12, 16, 8, 0.0F);
        this.setRotateAngle(body, 0.27314402793711257F, 0.0F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 40, 0);
        this.leftLeg.setRotationPoint(3.0F, 0.6F, -2.0F);
        this.leftLeg.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(leftLeg, -0.3839724354387525F, 0.0F, -0.17453292519943295F);
        this.tail = new ModelRenderer(this, 88, 0);
        this.tail.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tail.addBox(-2.0F, 0.0F, -1.0F, 4, 8, 2, 0.0F);
        this.setRotateAngle(tail, 0.7285004297824331F, 0.0F, 0.0F);
        this.mane = new ModelRenderer(this, 106, 19);
        this.mane.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.mane.addBox(-1.0F, -11.5F, 5.0F, 2, 16, 4, 0.0F);
        this.rightLeg = new ModelRenderer(this, 64, 0);
        this.rightLeg.setRotationPoint(-3.0F, 0.6F, -2.0F);
        this.rightLeg.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(rightLeg, -0.3839724354387525F, 0.0F, 0.17453292519943295F);
        this.hammer3 = new ModelRenderer(this, 40, 36);
        this.hammer3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hammer3.addBox(-3.0F, 3.0F, -27.0F, 6, 6, 6, 0.0F);
        this.rightHorn1 = new ModelRenderer(this, 52, 18);
        this.rightHorn1.setRotationPoint(-2.0F, -7.5F, 4.0F);
        this.rightHorn1.addBox(-3.0F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
        this.horseLeftEar = new ModelRenderer(this, 0, 0);
        this.horseLeftEar.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.horseLeftEar.addBox(0.45F, -12.0F, 4.0F, 2, 3, 1, 0.0F);
        this.leftHorn1 = new ModelRenderer(this, 58, 0);
        this.leftHorn1.setRotationPoint(2.0F, -7.5F, 4.0F);
        this.leftHorn1.addBox(0.0F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
        this.head = new ModelRenderer(this, 58, 18);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-2.5F, -10.0F, -1.5F, 5, 5, 7, 0.0F);
        this.horseRightEar = new ModelRenderer(this, 32, 0);
        this.horseRightEar.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.horseRightEar.addBox(-2.45F, -12.0F, 4.0F, 2, 3, 1, 0.0F);
        this.leftHorn4 = new ModelRenderer(this, 36, 2);
        this.leftHorn4.setRotationPoint(2.4F, 0.7F, 0.1F);
        this.leftHorn4.addBox(0.0F, -1.0F, -1.0F, 3, 1, 2, 0.0F);
        this.setRotateAngle(leftHorn4, 0.0F, 0.0F, -0.5462880558742251F);
        this.body.addChild(this.rightArm);
        this.hammer1.addChild(this.hammer2);
        this.leftHorn1.addChild(this.leftHorn2);
        this.rightHorn1.addChild(this.rightHorn2);
        this.rightLeg1.addChild(this.rightLeg2);
        this.rightLeg.addChild(this.rightLeg1);
        this.leftLeg1.addChild(this.leftLeg2);
        this.head.addChild(this.snout);
        this.hammer_handle.addChild(this.hammer1);
        this.rightArm2.addChild(this.hammer_handle);
        this.leftHorn2.addChild(this.leftHorn3);
        this.rightArm.addChild(this.rightArm2);
        this.leftArm.addChild(this.leftArm2);
        this.head.addChild(this.lowerJaw);
        this.body.addChild(this.neck);
        this.rightHorn3.addChild(this.rightHorn4);
        this.body.addChild(this.leftArm);
        this.leftLeg.addChild(this.leftLeg1);
        this.rightHorn2.addChild(this.rightHorn3);
        this.neck.addChild(this.mane);
        this.hammer1.addChild(this.hammer3);
        this.head.addChild(this.rightHorn1);
        this.head.addChild(this.horseLeftEar);
        this.head.addChild(this.leftHorn1);
        this.neck.addChild(this.head);
        this.head.addChild(this.horseRightEar);
        this.leftHorn3.addChild(this.leftHorn4);
	this.body.addChild(tail);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
        this.leftLeg.render(f5);
        this.rightLeg.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
	float limbSwingFactor = 0.4f;
	this.leftLeg.rotateAngleX = defaultLegRotation + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * limbSwingFactor;
	this.rightLeg.rotateAngleX = defaultLegRotation + MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * limbSwingFactor;

	this.neck.rotateAngleY = Math.min(Math.max(netHeadYaw * 0.017453292F, -0.20F * (float) Math.PI), 0.20F * (float) Math.PI);
	this.neck.rotateAngleX = this.defaultNeckRotation;
    }
}
