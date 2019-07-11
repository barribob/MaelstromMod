package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * Created using Tabula 7.0.0
 */
public class ModelMaelstromWitch extends ModelAnimated {
    public ModelRenderer leftLeg;
    public ModelRenderer body;
    public ModelRenderer rightLeg;
    public ModelRenderer cloak;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer head;
    public ModelRenderer nose;
    public ModelRenderer brim;
    public ModelRenderer wart;
    public ModelRenderer hat2;
    public ModelRenderer hat1;
    public ModelRenderer hat;

    public ModelMaelstromWitch() {
        this.textureWidth = 64;
        this.textureHeight = 128;
        this.leftLeg = new ModelRenderer(this, 0, 22);
        this.leftLeg.mirror = true;
        this.leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.cloak = new ModelRenderer(this, 0, 38);
        this.cloak.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.cloak.addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.leftArm = new ModelRenderer(this, 44, 22);
        this.leftArm.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.leftArm.addBox(4.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.rightArm = new ModelRenderer(this, 44, 22);
        this.rightArm.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.rightArm.addBox(-8.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.hat = new ModelRenderer(this, 0, 95);
        this.hat.setRotationPoint(1.75F, -2.0F, 2.0F);
        this.hat.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(hat, -0.20943951023931953F, 0.0F, 0.10471975511965977F);
        this.body = new ModelRenderer(this, 16, 20);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.brim = new ModelRenderer(this, 0, 64);
        this.brim.setRotationPoint(-5.0F, -10.03F, -5.0F);
        this.brim.addBox(0.0F, 0.0F, 0.0F, 10, 2, 10, 0.0F);
        this.wart = new ModelRenderer(this, 0, 0);
        this.wart.mirror = true;
        this.wart.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.wart.addBox(0.0F, 3.0F, -6.75F, 1, 1, 1, 0.0F);
        this.hat1 = new ModelRenderer(this, 0, 87);
        this.hat1.setRotationPoint(1.75F, -4.0F, 2.0F);
        this.hat1.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(hat1, -0.10471975511965977F, 0.0F, 0.05235987755982988F);
        this.nose = new ModelRenderer(this, 24, 0);
        this.nose.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.nose.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(nose, 0.0F, 0.0F, 0.04363323129985824F);
        this.hat2 = new ModelRenderer(this, 0, 76);
        this.hat2.setRotationPoint(1.75F, -4.0F, 2.0F);
        this.hat2.addBox(0.0F, 0.0F, 0.0F, 7, 4, 7, 0.0F);
        this.setRotateAngle(hat2, -0.05235987755982988F, 0.0F, 0.02617993877991494F);
        this.rightLeg = new ModelRenderer(this, 0, 22);
        this.rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.body.addChild(this.head);
        this.body.addChild(this.leftArm);
        this.body.addChild(this.rightArm);
        this.hat1.addChild(this.hat);
        this.head.addChild(this.brim);
        this.nose.addChild(this.wart);
        this.hat2.addChild(this.hat1);
        this.head.addChild(this.nose);
        this.brim.addChild(this.hat2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.leftLeg.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.cloak.offsetX, this.cloak.offsetY, this.cloak.offsetZ);
        GlStateManager.translate(this.cloak.rotationPointX * f5, this.cloak.rotationPointY * f5, this.cloak.rotationPointZ * f5);
        GlStateManager.scale(1.1D, 1.1D, 1.1D);
        GlStateManager.translate(-this.cloak.offsetX, -this.cloak.offsetY, -this.cloak.offsetZ);
        GlStateManager.translate(-this.cloak.rotationPointX * f5, -this.cloak.rotationPointY * f5, -this.cloak.rotationPointZ * f5);
        this.cloak.render(f5);
        GlStateManager.popMatrix();
        this.body.render(f5);
        this.rightLeg.render(f5);
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.nose.offsetX = 0.0F;
        this.nose.offsetY = 0.0F;
        this.nose.offsetZ = 0.0F;
        float f = 0.01F * (float)(entityIn.getEntityId() % 10);
        this.nose.rotateAngleX = MathHelper.sin((float)entityIn.ticksExisted * f) * 4.5F * 0.017453292F;
        this.nose.rotateAngleY = 0.0F;
        this.nose.rotateAngleZ = MathHelper.cos((float)entityIn.ticksExisted * f) * 2.5F * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.rightLeg.rotateAngleY = 0.0F;
        this.leftLeg.rotateAngleY = 0.0F;
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
