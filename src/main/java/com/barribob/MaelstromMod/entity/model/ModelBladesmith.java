package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Modeled by Daniel Yoshimura
 * Created using Tabula 7.0.0
 */
public class ModelBladesmith extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer arm1;
    public ModelRenderer arm2;
    public ModelRenderer arm3;
    public ModelRenderer leftLeg;
    public ModelRenderer body;
    public ModelRenderer armor;
    public ModelRenderer rightLeg;
    public ModelRenderer Rpad;
    public ModelRenderer Lpad;
    public ModelRenderer mainsheathSword;
    public ModelRenderer nose;
    public ModelRenderer sheathBot;
    public ModelRenderer mainguard;
    public ModelRenderer Grip;
    public ModelRenderer Guardcorner1;
    public ModelRenderer GuardCorner2;
    public ModelRenderer Grip2;

    public ModelBladesmith() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.mainsheathSword = new ModelRenderer(this, 29, 44);
        this.mainsheathSword.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.mainsheathSword.addBox(3.0F, -2.0F, 3.5F, 4, 17, 1, 0.0F);
        this.setRotateAngle(mainsheathSword, 0.0F, 0.0F, 0.40980330836826856F);
        this.armor = new ModelRenderer(this, 0, 38);
        this.armor.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.armor.addBox(-4.0F, 0.0F, -3.0F, 8, 11, 6, 0.5F);
        this.Rpad = new ModelRenderer(this, 32, 10);
        this.Rpad.setRotationPoint(0.0F, 2.0F, -1.0F);
        this.Rpad.addBox(-9.0F, -2.0F, -2.0F, 4, 5, 4, 0.0F);
        this.setRotateAngle(Rpad, -0.7499679795819634F, 0.0F, 0.0F);
        this.Lpad = new ModelRenderer(this, 32, 10);
        this.Lpad.setRotationPoint(0.0F, 2.0F, -1.0F);
        this.Lpad.addBox(5.0F, -2.0F, -2.0F, 4, 5, 4, 0.0F);
        this.setRotateAngle(Lpad, -0.7499679795819634F, 0.0F, 0.0F);
        this.Guardcorner1 = new ModelRenderer(this, 36, 0);
        this.Guardcorner1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Guardcorner1.addBox(1.2F, -8.0F, 3.0F, 2, 1, 2, 0.0F);
        this.setRotateAngle(Guardcorner1, 0.0F, 0.0F, 1.0471975511965976F);
        this.arm3 = new ModelRenderer(this, 40, 38);
        this.arm3.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.arm3.addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, 0.0F);
        this.setRotateAngle(arm3, -0.7499679795819634F, 0.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 0, 22);
        this.rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.sheathBot = new ModelRenderer(this, 30, 45);
        this.sheathBot.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.sheathBot.addBox(4.0F, 15.0F, 3.5F, 2, 1, 1, 0.0F);
        this.arm1 = new ModelRenderer(this, 44, 22);
        this.arm1.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.arm1.addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(arm1, -0.7499679795819634F, 0.0F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 0, 22);
        this.leftLeg.mirror = true;
        this.leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Grip = new ModelRenderer(this, 5, 33);
        this.Grip.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Grip.addBox(4.5F, -7.0F, 3.5F, 1, 4, 1, 0.0F);
        this.arm2 = new ModelRenderer(this, 44, 22);
        this.arm2.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.arm2.addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(arm2, -0.7499679795819634F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.Grip2 = new ModelRenderer(this, 35, 0);
        this.Grip2.setRotationPoint(5.0F, -9.3F, 3.0F);
        this.Grip2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(Grip2, 0.0F, 0.0F, 0.7853981633974483F);
        this.body = new ModelRenderer(this, 16, 20);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.mainguard = new ModelRenderer(this, 33, 0);
        this.mainguard.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.mainguard.addBox(2.5F, -3.0F, 3.0F, 5, 1, 2, 0.0F);
        this.nose = new ModelRenderer(this, 24, 0);
        this.nose.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.nose.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
        this.GuardCorner2 = new ModelRenderer(this, 34, 0);
        this.GuardCorner2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.GuardCorner2.addBox(0.6F, -3.8F, 3.0F, 1, 2, 2, 0.0F);
        this.setRotateAngle(GuardCorner2, 0.0F, 0.0F, 0.5235987755982988F);
        this.mainguard.addChild(this.Guardcorner1);
        this.mainsheathSword.addChild(this.sheathBot);
        this.mainsheathSword.addChild(this.Grip);
        this.Grip.addChild(this.Grip2);
        this.mainsheathSword.addChild(this.mainguard);
        this.head.addChild(this.nose);
        this.mainguard.addChild(this.GuardCorner2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.mainsheathSword.render(f5);
        this.armor.render(f5);
        this.Rpad.render(f5);
        this.Lpad.render(f5);
        this.arm3.render(f5);
        this.rightLeg.render(f5);
        this.arm1.render(f5);
        this.leftLeg.render(f5);
        this.arm2.render(f5);
        this.head.render(f5);
        this.body.render(f5);
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
