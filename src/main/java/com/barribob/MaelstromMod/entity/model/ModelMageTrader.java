package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 7.0.0
 */
public class ModelMageTrader extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer leftLeg;
    public ModelRenderer body;
    public ModelRenderer cloak;
    public ModelRenderer rightLeg;
    public ModelRenderer nose;
    public ModelRenderer hatBrim;
    public ModelRenderer hat1;
    public ModelRenderer hat2;
    public ModelRenderer hat3;
    public ModelRenderer leftArm;
    public ModelRenderer rightArm;
    public ModelRenderer staff1;
    public ModelRenderer staff2;
    public ModelRenderer staff3;
    public ModelRenderer staff4;
    public ModelRenderer staff5;
    public ModelRenderer staff6;
    public ModelRenderer staff7;
    public ModelRenderer staff8;
    public ModelRenderer staff9;
    public ModelRenderer staff10;
    public ModelRenderer staff11;

    public ModelMageTrader() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.rightArm = new ModelRenderer(this, 62, 20);
        this.rightArm.setRotationPoint(-4.0F, 3.0F, -1.0F);
        this.rightArm.addBox(-4.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(rightArm, -1.5707963267948966F, 0.40980330836826856F, 0.0F);
        this.staff1 = new ModelRenderer(this, 8, 24);
        this.staff1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.staff1.addBox(-2.0F, 8.0F, -11.0F, 1, 1, 32, 0.0F);
        this.staff6 = new ModelRenderer(this, 44, 0);
        this.staff6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.staff6.addBox(-5.1F, 8.0F, -13.9F, 2, 1, 2, 0.0F);
        this.hat3 = new ModelRenderer(this, 24, 0);
        this.hat3.setRotationPoint(1.75F, -2.0F, 2.0F);
        this.hat3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(hat3, -0.20943951023931953F, 0.0F, 0.10471975511965977F);
        this.staff4 = new ModelRenderer(this, 78, 24);
        this.staff4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.staff4.addBox(-5.0F, 7.5F, -18.0F, 1, 2, 5, 0.0F);
        this.hat2 = new ModelRenderer(this, 30, 16);
        this.hat2.setRotationPoint(1.75F, -4.0F, 2.0F);
        this.hat2.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(hat2, -0.10471975511965977F, 0.0F, 0.05235987755982988F);
        this.staff7 = new ModelRenderer(this, 98, 0);
        this.staff7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.staff7.addBox(-3.0F, 8.0F, -20.0F, 3, 1, 1, 0.0F);
        this.staff5 = new ModelRenderer(this, 70, 3);
        this.staff5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.staff5.addBox(-4.0F, 7.5F, -19.0F, 5, 2, 1, 0.0F);
        this.staff3 = new ModelRenderer(this, 70, 0);
        this.staff3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.staff3.addBox(-4.0F, 7.5F, -13.0F, 5, 2, 1, 0.0F);
        this.cloak = new ModelRenderer(this, 76, 0);
        this.cloak.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.cloak.addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, 0.5F);
        this.body = new ModelRenderer(this, 48, 0);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.staff8 = new ModelRenderer(this, 116, 0);
        this.staff8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.staff8.addBox(-5.1F, 8.0F, -19.1F, 2, 1, 2, 0.0F);
        this.leftArm = new ModelRenderer(this, 46, 18);
        this.leftArm.setRotationPoint(4.0F, 3.0F, -1.0F);
        this.leftArm.addBox(0.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.rightLeg = new ModelRenderer(this, 104, 0);
        this.rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.staff9 = new ModelRenderer(this, 24, 3);
        this.staff9.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.staff9.addBox(-6.0F, 8.0F, -17.0F, 1, 1, 3, 0.0F);
        this.staff10 = new ModelRenderer(this, 29, 2);
        this.staff10.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.staff10.addBox(-0.1F, 8.0F, -18.1F, 2, 1, 1, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.leftLeg = new ModelRenderer(this, 32, 0);
        this.leftLeg.mirror = true;
        this.leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.nose = new ModelRenderer(this, 0, 0);
        this.nose.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.nose.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
        this.hat1 = new ModelRenderer(this, 97, 17);
        this.hat1.setRotationPoint(1.75F, -4.0F, 2.0F);
        this.hat1.addBox(0.0F, 0.0F, 0.0F, 7, 4, 7, 0.0F);
        this.setRotateAngle(hat1, -0.05235987755982988F, 0.0F, 0.02617993877991494F);
        this.staff2 = new ModelRenderer(this, 28, 0);
        this.staff2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.staff2.addBox(-3.0F, 8.0F, -12.0F, 3, 1, 1, 0.0F);
        this.hatBrim = new ModelRenderer(this, 0, 18);
        this.hatBrim.setRotationPoint(-5.0F, -10.03F, -5.0F);
        this.hatBrim.addBox(0.0F, 0.0F, 0.0F, 10, 2, 10, 0.0F);
        this.staff11 = new ModelRenderer(this, 98, 2);
        this.staff11.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.staff11.addBox(-0.1F, 8.0F, -13.9F, 2, 1, 1, 0.0F);
        this.body.addChild(this.rightArm);
        this.rightArm.addChild(this.staff1);
        this.rightArm.addChild(this.staff6);
        this.hat2.addChild(this.hat3);
        this.rightArm.addChild(this.staff4);
        this.hat1.addChild(this.hat2);
        this.rightArm.addChild(this.staff7);
        this.rightArm.addChild(this.staff5);
        this.rightArm.addChild(this.staff3);
        this.rightArm.addChild(this.staff8);
        this.body.addChild(this.leftArm);
        this.rightArm.addChild(this.staff9);
        this.rightArm.addChild(this.staff10);
        this.head.addChild(this.nose);
        this.hatBrim.addChild(this.hat1);
        this.rightArm.addChild(this.staff2);
        this.head.addChild(this.hatBrim);
        this.rightArm.addChild(this.staff11);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.cloak.render(f5);
        this.body.render(f5);
        this.rightLeg.render(f5);
        this.head.render(f5);
        this.leftLeg.render(f5);
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used
     * for animating the movement of arms and legs, where par1 represents the
     * time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
	this.head.rotateAngleY = netHeadYaw * 0.017453292F;
	this.head.rotateAngleX = headPitch * 0.017453292F;
	this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
	this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
	this.leftLeg.rotateAngleY = 0.0F;
	this.rightLeg.rotateAngleY = 0.0F;
	
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.leftArm.rotateAngleZ = 0.0F;
        this.leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
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
