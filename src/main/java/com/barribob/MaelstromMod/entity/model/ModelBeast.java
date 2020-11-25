package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Beast - Barribob Created using Tabula 7.0.0
 */
public class ModelBeast extends ModelAnimated {
    public ModelRenderer backLeftThigh;
    public ModelRenderer backRightThigh;
    public ModelRenderer middleLeftThigh;
    public ModelRenderer middleRightThigh;
    public ModelRenderer frontLeftThigh;
    public ModelRenderer frontRightThigh;
    public ModelRenderer body;
    public ModelRenderer ridge1;
    public ModelRenderer ridge2;
    public ModelRenderer ridge3;
    public ModelRenderer ridge4;
    public ModelRenderer ridge5;
    public ModelRenderer ridge6;
    public ModelRenderer rearEnd;
    public ModelRenderer head;
    public ModelRenderer backLeftLeg;
    public ModelRenderer backLeftFoot;
    public ModelRenderer backRightLeg;
    public ModelRenderer backRightFoot;
    public ModelRenderer middleLeftLeg;
    public ModelRenderer middleLeftFoot;
    public ModelRenderer middleLeftShoulder;
    public ModelRenderer middleRightLeg;
    public ModelRenderer middleRightFoot;
    public ModelRenderer middleRightShoulder;
    public ModelRenderer frontLeftLeg;
    public ModelRenderer frontLeftFoot;
    public ModelRenderer frontLeftShoulder;
    public ModelRenderer frontRightLeg;
    public ModelRenderer frontRightFoot;
    public ModelRenderer frontRightShoulder;
    public ModelRenderer jaw;
    public ModelRenderer upperFrill;
    public ModelRenderer lowerFrill;

    public ModelBeast() {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.backRightLeg = new ModelRenderer(this, 100, 20);
        this.backRightLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.backRightLeg.addBox(-5.0F, 8.0F, -2.0F, 4, 8, 4, 0.0F);
        this.upperFrill = new ModelRenderer(this, 0, 54);
        this.upperFrill.setRotationPoint(0.0F, -0.9F, -1.0F);
        this.upperFrill.addBox(-2.0F, -6.0F, -12.0F, 4, 9, 14, 0.0F);
        this.body = new ModelRenderer(this, 96, 0);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-8.0F, -5.0F, -24.0F, 16, 10, 48, 0.0F);
        this.setRotateAngle(body, -0.3490658503988659F, 0.0F, 0.0F);
        this.frontLeftLeg = new ModelRenderer(this, 112, 28);
        this.frontLeftLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.frontLeftLeg.addBox(9.0F, 8.0F, -2.0F, 4, 8, 4, 0.0F);
        this.middleRightFoot = new ModelRenderer(this, 0, 26);
        this.middleRightFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.middleRightFoot.addBox(-10.0F, 16.0F, -3.0F, 6, 8, 6, 0.0F);
        this.ridge4 = new ModelRenderer(this, 212, 12);
        this.ridge4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ridge4.addBox(-8.0F, 5.0F, -20.0F, 16, 2, 4, 0.0F);
        this.setRotateAngle(ridge4, -0.3490658503988659F, 0.0F, 0.0F);
        this.lowerFrill = new ModelRenderer(this, 78, 28);
        this.lowerFrill.setRotationPoint(0.0F, 8.1F, -1.0F);
        this.lowerFrill.addBox(-2.0F, -6.0F, -12.0F, 4, 5, 14, 0.0F);
        this.ridge1 = new ModelRenderer(this, 176, 0);
        this.ridge1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ridge1.addBox(-8.0F, -9.0F, -20.0F, 16, 4, 4, 0.0F);
        this.setRotateAngle(ridge1, -0.3490658503988659F, 0.0F, 0.0F);
        this.frontLeftThigh = new ModelRenderer(this, 96, 0);
        this.frontLeftThigh.setRotationPoint(8.0F, -6.0F, -16.0F);
        this.frontLeftThigh.addBox(8.0F, -6.0F, -3.0F, 6, 14, 6, 0.0F);
        this.head = new ModelRenderer(this, 24, 20);
        this.head.setRotationPoint(0.0F, -10.0F, -22.0F);
        this.head.addBox(-6.0F, -6.0F, -12.0F, 12, 8, 12, 0.0F);
        this.middleLeftShoulder = new ModelRenderer(this, 216, 24);
        this.middleLeftShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.middleLeftShoulder.addBox(0.0F, -4.0F, -3.0F, 4, 8, 6, 0.0F);
        this.middleRightShoulder = new ModelRenderer(this, 72, 28);
        this.middleRightShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.middleRightShoulder.addBox(-4.0F, -4.0F, -3.0F, 4, 8, 6, 0.0F);
        this.backLeftFoot = new ModelRenderer(this, 76, 20);
        this.backLeftFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.backLeftFoot.addBox(0.0F, 16.0F, -3.0F, 6, 2, 6, 0.0F);
        this.jaw = new ModelRenderer(this, 40, 56);
        this.jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jaw.addBox(-6.0F, 2.0F, -12.0F, 12, 4, 12, 0.0F);
        this.backRightFoot = new ModelRenderer(this, 116, 20);
        this.backRightFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.backRightFoot.addBox(-6.0F, 16.0F, -3.0F, 6, 2, 6, 0.0F);
        this.middleRightThigh = new ModelRenderer(this, 72, 0);
        this.middleRightThigh.setRotationPoint(-8.0F, 0.0F, 0.0F);
        this.middleRightThigh.addBox(-10.0F, -6.0F, -3.0F, 6, 14, 6, 0.0F);
        this.ridge6 = new ModelRenderer(this, 212, 18);
        this.ridge6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ridge6.addBox(-8.0F, 5.0F, 14.0F, 16, 2, 4, 0.0F);
        this.setRotateAngle(ridge6, -0.3490658503988659F, 0.0F, 0.0F);
        this.rearEnd = new ModelRenderer(this, 0, 20);
        this.rearEnd.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rearEnd.addBox(-8.0F, -2.0F, 24.0F, 16, 4, 2, 0.0F);
        this.setRotateAngle(rearEnd, -0.3490658503988659F, 0.0F, 0.0F);
        this.ridge2 = new ModelRenderer(this, 212, 4);
        this.ridge2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ridge2.addBox(-8.0F, -9.0F, -3.0F, 16, 4, 4, 0.0F);
        this.setRotateAngle(ridge2, -0.3490658503988659F, 0.0F, 0.0F);
        this.backLeftThigh = new ModelRenderer(this, 0, 0);
        this.backLeftThigh.setRotationPoint(8.0F, 6.0F, 16.0F);
        this.backLeftThigh.addBox(0.0F, -6.0F, -3.0F, 6, 14, 6, 0.0F);
        this.ridge3 = new ModelRenderer(this, 176, 8);
        this.ridge3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ridge3.addBox(-8.0F, -9.0F, 14.0F, 16, 4, 4, 0.0F);
        this.setRotateAngle(ridge3, -0.3490658503988659F, 0.0F, 0.0F);
        this.frontLeftFoot = new ModelRenderer(this, 230, 36);
        this.frontLeftFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.frontLeftFoot.addBox(8.0F, 16.0F, -3.0F, 6, 14, 6, 0.0F);
        this.frontLeftShoulder = new ModelRenderer(this, 0, 40);
        this.frontLeftShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.frontLeftShoulder.addBox(0.0F, -4.0F, -3.0F, 8, 8, 6, 0.0F);
        this.frontRightLeg = new ModelRenderer(this, 128, 28);
        this.frontRightLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.frontRightLeg.addBox(-13.0F, 8.0F, -2.0F, 4, 8, 4, 0.0F);
        this.backRightThigh = new ModelRenderer(this, 24, 0);
        this.backRightThigh.setRotationPoint(-8.0F, 6.0F, 16.0F);
        this.backRightThigh.addBox(-6.0F, -6.0F, -3.0F, 6, 14, 6, 0.0F);
        this.frontRightShoulder = new ModelRenderer(this, 52, 42);
        this.frontRightShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.frontRightShoulder.addBox(-8.0F, -4.0F, -3.0F, 8, 8, 6, 0.0F);
        this.frontRightFoot = new ModelRenderer(this, 28, 40);
        this.frontRightFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.frontRightFoot.addBox(-14.0F, 16.0F, -3.0F, 6, 14, 6, 0.0F);
        this.frontRightThigh = new ModelRenderer(this, 120, 0);
        this.frontRightThigh.setRotationPoint(-8.0F, -6.0F, -16.0F);
        this.frontRightThigh.addBox(-14.0F, -6.0F, -3.0F, 6, 14, 6, 0.0F);
        this.middleLeftFoot = new ModelRenderer(this, 192, 22);
        this.middleLeftFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.middleLeftFoot.addBox(4.0F, 16.0F, -3.0F, 6, 8, 6, 0.0F);
        this.ridge5 = new ModelRenderer(this, 176, 16);
        this.ridge5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ridge5.addBox(-8.0F, 5.0F, -3.0F, 16, 2, 4, 0.0F);
        this.setRotateAngle(ridge5, -0.3490658503988659F, 0.0F, 0.0F);
        this.middleLeftLeg = new ModelRenderer(this, 176, 22);
        this.middleLeftLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.middleLeftLeg.addBox(5.0F, 8.0F, -2.0F, 4, 8, 4, 0.0F);
        this.middleRightLeg = new ModelRenderer(this, 236, 24);
        this.middleRightLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.middleRightLeg.addBox(-9.0F, 8.0F, -2.0F, 4, 8, 4, 0.0F);
        this.backLeftLeg = new ModelRenderer(this, 60, 20);
        this.backLeftLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.backLeftLeg.addBox(1.0F, 8.0F, -2.0F, 4, 8, 4, 0.0F);
        this.middleLeftThigh = new ModelRenderer(this, 48, 0);
        this.middleLeftThigh.setRotationPoint(8.0F, 0.0F, 0.0F);
        this.middleLeftThigh.addBox(4.0F, -6.0F, -3.0F, 6, 14, 6, 0.0F);
        this.backRightThigh.addChild(this.backRightLeg);
        this.head.addChild(this.upperFrill);
        this.frontLeftThigh.addChild(this.frontLeftLeg);
        this.middleRightThigh.addChild(this.middleRightFoot);
        this.jaw.addChild(this.lowerFrill);
        this.middleLeftThigh.addChild(this.middleLeftShoulder);
        this.middleRightThigh.addChild(this.middleRightShoulder);
        this.backLeftThigh.addChild(this.backLeftFoot);
        this.head.addChild(this.jaw);
        this.backRightThigh.addChild(this.backRightFoot);
        this.frontLeftThigh.addChild(this.frontLeftFoot);
        this.frontLeftThigh.addChild(this.frontLeftShoulder);
        this.frontRightThigh.addChild(this.frontRightLeg);
        this.frontRightThigh.addChild(this.frontRightShoulder);
        this.frontRightThigh.addChild(this.frontRightFoot);
        this.middleLeftThigh.addChild(this.middleLeftFoot);
        this.middleLeftThigh.addChild(this.middleLeftLeg);
        this.middleRightThigh.addChild(this.middleRightLeg);
        this.backLeftThigh.addChild(this.backLeftLeg);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.render(f5);
        this.ridge4.render(f5);
        this.ridge1.render(f5);
        this.frontLeftThigh.render(f5);
        this.head.render(f5);
        this.middleRightThigh.render(f5);
        this.ridge6.render(f5);
        this.rearEnd.render(f5);
        this.ridge2.render(f5);
        this.backLeftThigh.render(f5);
        this.ridge3.render(f5);
        this.backRightThigh.render(f5);
        this.frontRightThigh.render(f5);
        this.ridge5.render(f5);
        this.middleLeftThigh.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used
     * for animating the movement of arms and legs, where par1 represents the
     * time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor,
                                  Entity entityIn) {
        float limbSwingFactor = 0.4f;
        this.frontLeftThigh.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * limbSwingFactor;
        this.frontRightThigh.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * limbSwingFactor;
        this.middleLeftThigh.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * limbSwingFactor;
        this.middleRightThigh.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * limbSwingFactor;
        this.backLeftThigh.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * limbSwingFactor;
        this.backRightThigh.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * limbSwingFactor;

        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
    }
}
