package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Swamp_Crawler - Barribob
 * Created using Tabula 7.0.0
 */
public class ModelSwampCrawler extends ModelAnimated {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer frontRightUpperLeg1;
    public ModelRenderer frontLeftUpperLeg1;
    public ModelRenderer backRightLeg;
    public ModelRenderer backLeftLeg;
    public ModelRenderer tail1;
    public ModelRenderer tail2;
    public ModelRenderer tail3;
    public ModelRenderer headFin;
    public ModelRenderer headFin2;
    public ModelRenderer headFin3;
    public ModelRenderer rightSideJaw;
    public ModelRenderer leftSideJaw;
    public ModelRenderer frontRightLeg1;
    public ModelRenderer frontRightFoot1;
    public ModelRenderer frontLeftLeg1;
    public ModelRenderer frontLeftFoot1;
    public ModelRenderer backLeftFoot;
    public ModelRenderer backRightFoot;

    public ModelSwampCrawler() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 10.0F, -7.0F);
        this.body.addBox(-5.0F, -3.0F, 16.0F, 10, 6, 6, 0.0F);
        this.setRotateAngle(body, -0.4363323129985824F, 0.0F, 0.0F);
        this.headFin = new ModelRenderer(this, 26, 0);
        this.headFin.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headFin.addBox(1.0F, -2.0F, -7.0F, 4, 4, 1, 0.0F);
        this.setRotateAngle(headFin, 0.0F, -0.5009094953223726F, 0.008117812814921174F);
        this.headFin2 = new ModelRenderer(this, 112, 0);
        this.headFin2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headFin2.addBox(-5.0F, -2.0F, -7.0F, 4, 4, 1, 0.0F);
        this.setRotateAngle(headFin2, 0.0F, 0.5009094953223726F, 0.0F);
        this.backLeftFoot = new ModelRenderer(this, 16, 26);
        this.backLeftFoot.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.backLeftFoot.addBox(-3.0F, 0.0F, -2.0F, 6, 2, 4, 0.0F);
        this.setRotateAngle(backLeftFoot, 0.0F, 0.0F, -0.8726646259971648F);
        this.head = new ModelRenderer(this, 32, 0);
        this.head.setRotationPoint(0.0F, 10.0F, -6.0F);
        this.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.frontLeftLeg1 = new ModelRenderer(this, 50, 16);
        this.frontLeftLeg1.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.frontLeftLeg1.addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
        this.setRotateAngle(frontLeftLeg1, -1.3962634015954636F, 0.8726646259971648F, 0.0F);
        this.frontLeftFoot1 = new ModelRenderer(this, 0, 22);
        this.frontLeftFoot1.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.frontLeftFoot1.addBox(-2.0F, -1.0F, -2.0F, 6, 2, 4, 0.0F);
        this.setRotateAngle(frontLeftFoot1, 0.5235987755982988F, 0.091106186954104F, 0.6829473363053812F);
        this.backRightFoot = new ModelRenderer(this, 0, 28);
        this.backRightFoot.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.backRightFoot.addBox(-3.0F, 0.0F, -2.0F, 6, 2, 4, 0.0F);
        this.setRotateAngle(backRightFoot, 0.0F, 0.0F, 0.8726646259971648F);
        this.tail3 = new ModelRenderer(this, 110, 14);
        this.tail3.setRotationPoint(0.0F, 10.0F, -7.0F);
        this.tail3.addBox(-3.0F, -1.0F, 26.0F, 6, 2, 2, 0.0F);
        this.setRotateAngle(tail3, -0.4363323129985824F, 0.0F, 0.0F);
        this.leftSideJaw = new ModelRenderer(this, 104, 18);
        this.leftSideJaw.setRotationPoint(0.0F, 0.0F, -8.0F);
        this.leftSideJaw.addBox(0.0F, -3.0F, -8.0F, 3, 6, 8, 0.0F);
        this.frontRightLeg1 = new ModelRenderer(this, 38, 16);
        this.frontRightLeg1.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.frontRightLeg1.addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
        this.setRotateAngle(frontRightLeg1, -1.3962634015954636F, -0.8726646259971648F, 0.0F);
        this.frontRightFoot1 = new ModelRenderer(this, 88, 16);
        this.frontRightFoot1.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.frontRightFoot1.addBox(-4.0F, -1.0F, -2.0F, 6, 2, 4, 0.0F);
        this.setRotateAngle(frontRightFoot1, 0.5235987755982988F, 0.09047060990185808F, -0.6076610908958738F);
        this.headFin3 = new ModelRenderer(this, 112, 5);
        this.headFin3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headFin3.addBox(-2.0F, -5.4F, -6.2F, 4, 4, 1, 0.0F);
        this.setRotateAngle(headFin3, -0.5009094953223726F, 0.0F, 0.0F);
        this.backLeftLeg = new ModelRenderer(this, 100, 0);
        this.backLeftLeg.setRotationPoint(6.0F, 14.0F, 4.0F);
        this.backLeftLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 13, 3, 0.0F);
        this.setRotateAngle(backLeftLeg, 0.0F, 0.0F, -0.8726646259971648F);
        this.tail1 = new ModelRenderer(this, 48, 13);
        this.tail1.setRotationPoint(0.0F, 10.0F, -7.0F);
        this.tail1.addBox(-6.0F, -4.0F, 0.0F, 12, 8, 16, 0.0F);
        this.setRotateAngle(tail1, -0.4363323129985824F, 0.0F, 0.0F);
        this.frontLeftUpperLeg1 = new ModelRenderer(this, 76, 0);
        this.frontLeftUpperLeg1.setRotationPoint(6.0F, 10.0F, -4.0F);
        this.frontLeftUpperLeg1.addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
        this.setRotateAngle(frontLeftUpperLeg1, 0.0F, 0.0F, -0.8726646259971648F);
        this.rightSideJaw = new ModelRenderer(this, 16, 12);
        this.rightSideJaw.setRotationPoint(0.0F, 0.0F, -8.0F);
        this.rightSideJaw.addBox(-3.0F, -3.0F, -8.0F, 3, 6, 8, 0.0F);
        this.frontRightUpperLeg1 = new ModelRenderer(this, 64, 0);
        this.frontRightUpperLeg1.setRotationPoint(-6.0F, 10.0F, -4.0F);
        this.frontRightUpperLeg1.addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
        this.setRotateAngle(frontRightUpperLeg1, 1.3835211708767574E-15F, 0.0F, 0.8726646259971648F);
        this.backRightLeg = new ModelRenderer(this, 88, 0);
        this.backRightLeg.setRotationPoint(-6.0F, 14.0F, 4.0F);
        this.backRightLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 13, 3, 0.0F);
        this.setRotateAngle(backRightLeg, 5.038032274901357E-17F, 0.0F, 0.8726646259971648F);
        this.tail2 = new ModelRenderer(this, 0, 12);
        this.tail2.setRotationPoint(0.0F, 10.0F, -7.0F);
        this.tail2.addBox(-4.0F, -2.0F, 22.0F, 8, 4, 4, 0.0F);
        this.setRotateAngle(tail2, -0.4363323129985824F, 0.0F, 0.0F);
        this.head.addChild(this.headFin);
        this.head.addChild(this.headFin2);
        this.backRightLeg.addChild(this.backLeftFoot);
        this.frontLeftUpperLeg1.addChild(this.frontLeftLeg1);
        this.frontLeftLeg1.addChild(this.frontLeftFoot1);
        this.backLeftLeg.addChild(this.backRightFoot);
        this.head.addChild(this.leftSideJaw);
        this.frontRightUpperLeg1.addChild(this.frontRightLeg1);
        this.frontRightLeg1.addChild(this.frontRightFoot1);
        this.head.addChild(this.headFin3);
        this.head.addChild(this.rightSideJaw);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.render(f5);
        this.head.render(f5);
        this.tail3.render(f5);
        this.backLeftLeg.render(f5);
        this.tail1.render(f5);
        this.frontLeftUpperLeg1.render(f5);
        this.frontRightUpperLeg1.render(f5);
        this.backRightLeg.render(f5);
        this.tail2.render(f5);
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
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.backLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.backRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.backLeftLeg.rotateAngleY = -MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.backRightLeg.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.backLeftLeg.rotateAngleZ = (float) (-Math.toRadians(50) - MathHelper.cos((float) (limbSwing * 0.6662F)) * 1.4F * limbSwingAmount * 0.25F);
        this.backRightLeg.rotateAngleZ = (float) (Math.toRadians(50) + MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.25F);

        this.frontLeftUpperLeg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.frontRightUpperLeg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.frontLeftUpperLeg1.rotateAngleY = -MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.frontRightUpperLeg1.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
    }
}
