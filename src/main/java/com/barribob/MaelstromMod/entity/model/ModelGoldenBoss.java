package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created using Tabula 7.0.0
 */
public class ModelGoldenBoss extends ModelAnimated {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer leftArm1;
    public ModelRenderer leftArm2;
    public ModelRenderer leftArm3;
    public ModelRenderer leftArm4;
    public ModelRenderer leftShoulder;
    public ModelRenderer rightShoulder;
    public ModelRenderer rightArm1;
    public ModelRenderer rightArm2;
    public ModelRenderer rightArm3;
    public ModelRenderer rightArm4;
    public ModelRenderer leftLeg;
    public ModelRenderer rightLeg;
    public ModelRenderer sunThingy;
    public ModelRenderer headband;
    public ModelRenderer sunThingy2;
    public ModelRenderer miniHead1;
    public ModelRenderer miniHead2;
    public ModelRenderer miniHead3;
    public ModelRenderer miniHead4;
    public ModelRenderer miniHead5;
    public ModelRenderer leftForearm1;
    public ModelRenderer leftForearm2;
    public ModelRenderer leftForearm3;
    public ModelRenderer leftForearm4;
    public ModelRenderer rightForearm1;
    public ModelRenderer rightForearm2;
    public ModelRenderer rightForearm3;
    public ModelRenderer rightForearm4;
    public ModelRenderer leftCalf;
    public ModelRenderer rightCalf;

    public ModelGoldenBoss() {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.rightLeg = new ModelRenderer(this, 104, 16);
        this.rightLeg.setRotationPoint(-3.5F, 22.0F, 0.0F);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(rightLeg, -1.5707963267948966F, 0.6981317007977318F, 0.3141592653589793F);
        this.miniHead2 = new ModelRenderer(this, 78, 28);
        this.miniHead2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.miniHead2.addBox(-2.0F, -18.0F, -11.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(miniHead2, 0.0F, -0.7853981633974483F, 0.0F);
        this.leftArm1 = new ModelRenderer(this, 104, 0);
        this.leftArm1.setRotationPoint(8.0F, 2.0F, 0.0F);
        this.leftArm1.addBox(0.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.headband = new ModelRenderer(this, 33, 28);
        this.headband.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headband.addBox(-7.5F, -14.1F, -7.5F, 15, 2, 15, 0.0F);
        this.leftForearm3 = new ModelRenderer(this, 93, 32);
        this.leftForearm3.setRotationPoint(2.0F, 10.0F, 0.1F);
        this.leftForearm3.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.rightArm1 = new ModelRenderer(this, 216, 0);
        this.rightArm1.setRotationPoint(-8.0F, 2.0F, 0.0F);
        this.rightArm1.addBox(-4.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.leftForearm1 = new ModelRenderer(this, 0, 32);
        this.leftForearm1.setRotationPoint(2.0F, 10.0F, 0.1F);
        this.leftForearm1.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.rightShoulder = new ModelRenderer(this, 192, 0);
        this.rightShoulder.setRotationPoint(-11.0F, -1.0F, 0.0F);
        this.rightShoulder.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6, 0.0F);
        this.miniHead4 = new ModelRenderer(this, 178, 28);
        this.miniHead4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.miniHead4.addBox(-2.0F, -18.0F, -9.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(miniHead4, 0.0F, -1.5707963267948966F, 0.0F);
        this.leftForearm2 = new ModelRenderer(this, 16, 32);
        this.leftForearm2.setRotationPoint(2.0F, 10.0F, 0.1F);
        this.leftForearm2.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.head = new ModelRenderer(this, 48, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-7.0F, -14.0F, -7.0F, 14, 14, 14, 0.0F);
        this.rightForearm3 = new ModelRenderer(this, 109, 37);
        this.rightForearm3.setRotationPoint(-2.0F, 10.0F, 0.1F);
        this.rightForearm3.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.sunThingy = new ModelRenderer(this, 120, 16);
        this.sunThingy.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.sunThingy.addBox(-15.0F, -15.0F, 8.5F, 20, 20, 1, 0.0F);
        this.setRotateAngle(sunThingy, 0.0F, 0.0F, 0.7853981633974483F);
        this.leftLeg = new ModelRenderer(this, 196, 12);
        this.leftLeg.setRotationPoint(3.5F, 22.0F, 0.0F);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(leftLeg, -1.5707963267948966F, -0.6981317007977318F, -0.3141592653589793F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.body.addBox(-8.0F, 0.0F, -4.0F, 16, 24, 8, 0.0F);
        this.leftArm3 = new ModelRenderer(this, 136, 0);
        this.leftArm3.setRotationPoint(8.0F, 2.0F, 0.0F);
        this.leftArm3.addBox(0.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.rightForearm1 = new ModelRenderer(this, 174, 36);
        this.rightForearm1.setRotationPoint(-2.0F, 10.0F, 0.1F);
        this.rightForearm1.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.rightCalf = new ModelRenderer(this, 206, 37);
        this.rightCalf.setRotationPoint(0.0F, 12.0F, -0.1F);
        this.rightCalf.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(rightCalf, 0.0F, 0.0F, -1.7453292519943295F);
        this.leftArm4 = new ModelRenderer(this, 152, 0);
        this.leftArm4.setRotationPoint(8.0F, 2.0F, 0.0F);
        this.leftArm4.addBox(0.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.leftCalf = new ModelRenderer(this, 141, 37);
        this.leftCalf.setRotationPoint(0.0F, 12.0F, -0.1F);
        this.leftCalf.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(leftCalf, 0.0F, 0.0F, 1.7453292519943295F);
        this.rightArm3 = new ModelRenderer(this, 164, 12);
        this.rightArm3.setRotationPoint(-8.0F, 2.0F, 0.0F);
        this.rightArm3.addBox(-4.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.miniHead3 = new ModelRenderer(this, 162, 28);
        this.miniHead3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.miniHead3.addBox(-2.0F, -18.0F, -9.0F, 4, 4, 4, 0.0F);
        this.sunThingy2 = new ModelRenderer(this, 212, 16);
        this.sunThingy2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.sunThingy2.addBox(-10.0F, -17.0F, 8.0F, 20, 20, 1, 0.0F);
        this.leftShoulder = new ModelRenderer(this, 168, 0);
        this.leftShoulder.setRotationPoint(11.0F, -1.0F, 0.0F);
        this.leftShoulder.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6, 0.0F);
        this.rightForearm4 = new ModelRenderer(this, 125, 37);
        this.rightForearm4.setRotationPoint(-2.0F, 10.0F, 0.1F);
        this.rightForearm4.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.miniHead5 = new ModelRenderer(this, 194, 28);
        this.miniHead5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.miniHead5.addBox(-2.0F, -18.0F, -9.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(miniHead5, 0.0F, 1.5707963267948966F, 0.0F);
        this.miniHead1 = new ModelRenderer(this, 40, 0);
        this.miniHead1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.miniHead1.addBox(-2.0F, -18.0F, -11.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(miniHead1, 0.0F, 0.7853981633974483F, 0.0F);
        this.rightArm4 = new ModelRenderer(this, 180, 12);
        this.rightArm4.setRotationPoint(-8.0F, 2.0F, 0.0F);
        this.rightArm4.addBox(-4.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.rightForearm2 = new ModelRenderer(this, 190, 36);
        this.rightForearm2.setRotationPoint(-2.0F, 10.0F, 0.1F);
        this.rightForearm2.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.leftForearm4 = new ModelRenderer(this, 158, 36);
        this.leftForearm4.setRotationPoint(2.0F, 10.0F, 0.1F);
        this.leftForearm4.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.leftArm2 = new ModelRenderer(this, 120, 0);
        this.leftArm2.setRotationPoint(8.0F, 2.0F, 0.0F);
        this.leftArm2.addBox(0.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.rightArm2 = new ModelRenderer(this, 232, 0);
        this.rightArm2.setRotationPoint(-8.0F, 2.0F, 0.0F);
        this.rightArm2.addBox(-4.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.body.addChild(this.rightLeg);
        this.head.addChild(this.miniHead2);
        this.body.addChild(this.leftArm1);
        this.head.addChild(this.headband);
        this.leftArm3.addChild(this.leftForearm3);
        this.body.addChild(this.rightArm1);
        this.leftArm1.addChild(this.leftForearm1);
        this.body.addChild(this.rightShoulder);
        this.head.addChild(this.miniHead4);
        this.leftArm2.addChild(this.leftForearm2);
        this.body.addChild(this.head);
        this.rightArm3.addChild(this.rightForearm3);
        this.head.addChild(this.sunThingy);
        this.body.addChild(this.leftLeg);
        this.body.addChild(this.leftArm3);
        this.rightArm1.addChild(this.rightForearm1);
        this.rightLeg.addChild(this.rightCalf);
        this.body.addChild(this.leftArm4);
        this.leftLeg.addChild(this.leftCalf);
        this.body.addChild(this.rightArm3);
        this.head.addChild(this.miniHead3);
        this.head.addChild(this.sunThingy2);
        this.body.addChild(this.leftShoulder);
        this.rightArm4.addChild(this.rightForearm4);
        this.head.addChild(this.miniHead5);
        this.head.addChild(this.miniHead1);
        this.body.addChild(this.rightArm4);
        this.rightArm2.addChild(this.rightForearm2);
        this.leftArm4.addChild(this.leftForearm4);
        this.body.addChild(this.leftArm2);
        this.body.addChild(this.rightArm2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
	this.head.rotateAngleY = netHeadYaw * 0.017453292F;
	this.head.rotateAngleX = headPitch * 0.017453292F;
	
	this.body.offsetY = (float) Math.cos(Math.toRadians(ageInTicks * 2)) * 0.2f - 0.5f;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
