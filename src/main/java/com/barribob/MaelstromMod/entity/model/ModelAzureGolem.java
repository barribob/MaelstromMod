package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * CrystalGolem - Barribob Created using Tabula 7.0.0
 */
public class ModelAzureGolem extends ModelAnimated
{
    public ModelRenderer waist;
    public ModelRenderer leftLeg;
    public ModelRenderer rightLeg;
    public ModelRenderer back;
    public ModelRenderer leftBicep;
    public ModelRenderer rightBicep;
    public ModelRenderer head;
    public ModelRenderer leftArm;
    public ModelRenderer leftShoulderpad;
    public ModelRenderer leftHand;
    public ModelRenderer rightArm;
    public ModelRenderer rightShoulderpad;
    public ModelRenderer rightHand;
    public ModelRenderer nose;
    
    public float limbSwingFactor = 1.5f;

    public ModelAzureGolem() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.nose = new ModelRenderer(this, 64, 0);
        this.nose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.nose.addBox(-1.0F, -4.0F, -6.0F, 2, 4, 2, 0.0F);
        this.leftHand = new ModelRenderer(this, 0, 29);
        this.leftHand.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.leftHand.addBox(-3.0F, -3.0F, -3.0F, 6, 10, 6, 0.0F);
        this.rightShoulderpad = new ModelRenderer(this, 32, 44);
        this.rightShoulderpad.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightShoulderpad.addBox(-7.0F, -5.0F, -4.0F, 7, 3, 8, 0.0F);
        this.back = new ModelRenderer(this, 11, 59);
        this.back.setRotationPoint(0.0F, -4.0F, 0.0F);
        this.back.addBox(-9.0F, -17.0F, -4.0F, 18, 18, 9, 0.0F);
        this.setRotateAngle(back, 0.08726646259971647F, 0.0F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 32, 0);
        this.leftLeg.setRotationPoint(5.0F, 8.0F, 0.0F);
        this.leftLeg.addBox(0.0F, -1.0F, -2.0F, 4, 17, 4, 0.0F);
        this.leftArm = new ModelRenderer(this, 80, 26);
        this.leftArm.setRotationPoint(3.0F, 8.0F, 0.0F);
        this.leftArm.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.setRotateAngle(leftArm, -0.5235987755982988F, 0.0F, 0.0F);
        this.rightHand = new ModelRenderer(this, 62, 44);
        this.rightHand.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.rightHand.addBox(-3.0F, -3.0F, -3.0F, 6, 10, 6, 0.0F);
        this.rightLeg = new ModelRenderer(this, 48, 0);
        this.rightLeg.setRotationPoint(-5.0F, 8.0F, 0.0F);
        this.rightLeg.addBox(-4.0F, -1.0F, -2.0F, 4, 17, 4, 0.0F);
        this.leftBicep = new ModelRenderer(this, 0, 12);
        this.leftBicep.setRotationPoint(9.0F, -13.0F, 0.0F);
        this.leftBicep.addBox(0.0F, -3.0F, -3.0F, 6, 11, 6, 0.0F);
        this.head = new ModelRenderer(this, 48, 26);
        this.head.setRotationPoint(0.0F, -18.0F, 0.0F);
        this.head.addBox(-4.0F, -9.0F, -4.0F, 8, 10, 8, 0.0F);
        this.leftShoulderpad = new ModelRenderer(this, 96, 26);
        this.leftShoulderpad.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftShoulderpad.addBox(0.0F, -5.0F, -4.0F, 7, 3, 8, 0.0F);
        this.waist = new ModelRenderer(this, 0, 0);
        this.waist.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.waist.addBox(-5.0F, -3.0F, -3.0F, 10, 6, 6, 0.0F);
        this.rightBicep = new ModelRenderer(this, 24, 21);
        this.rightBicep.setRotationPoint(-9.0F, -13.0F, 0.0F);
        this.rightBicep.addBox(-6.0F, -3.0F, -3.0F, 6, 11, 6, 0.0F);
        this.rightArm = new ModelRenderer(this, 24, 38);
        this.rightArm.setRotationPoint(-3.0F, 8.0F, 0.0F);
        this.rightArm.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.setRotateAngle(rightArm, -0.5235987755982988F, 0.0F, 0.0F);
        this.head.addChild(this.nose);
        this.leftArm.addChild(this.leftHand);
        this.rightBicep.addChild(this.rightShoulderpad);
        this.waist.addChild(this.back);
        this.leftBicep.addChild(this.leftArm);
        this.rightArm.addChild(this.rightHand);
        this.back.addChild(this.leftBicep);
        this.back.addChild(this.head);
        this.leftBicep.addChild(this.leftShoulderpad);
        this.back.addChild(this.rightBicep);
        this.rightBicep.addChild(this.rightArm);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
	this.waist.render(f5);
	this.rightLeg.render(f5);
	this.leftLeg.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
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
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
	this.leftLeg.rotateAngleX = -limbSwingFactor * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
	this.rightLeg.rotateAngleX = limbSwingFactor * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
	
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
    }

    private float triangleWave(float p_78172_1_, float p_78172_2_)
    {
	return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }
}
