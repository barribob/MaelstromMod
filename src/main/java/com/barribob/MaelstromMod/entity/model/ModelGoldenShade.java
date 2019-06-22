package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelGoldenShade - Barribob
 * Created using Tabula 7.0.0
 */
public class ModelGoldenShade extends ModelAnimated {
    public ModelRenderer wisps;
    public ModelRenderer body;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer head;
    public ModelRenderer rightHand;
    public ModelRenderer rightShoulderpad;
    public ModelRenderer rightCuff;
    public ModelRenderer leftHand;
    public ModelRenderer leftShoulderpad;
    public ModelRenderer leftCuff;
    public ModelRenderer headFrill;

    public ModelGoldenShade() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.headFrill = new ModelRenderer(this, 26, 36);
        this.headFrill.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headFrill.addBox(-1.0F, -9.0F, -5.0F, 2, 9, 10, 0.0F);
        this.body = new ModelRenderer(this, 22, 0);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-4.5F, -14.0F, -3.0F, 9, 14, 6, 0.0F);
        this.leftShoulderpad = new ModelRenderer(this, 0, 36);
        this.leftShoulderpad.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftShoulderpad.addBox(0.5F, -2.5F, -2.5F, 4, 2, 5, 0.0F);
        this.leftCuff = new ModelRenderer(this, 18, 36);
        this.leftCuff.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftCuff.addBox(0.5F, 6.0F, -2.5F, 4, 1, 5, 0.0F);
        this.wisps = new ModelRenderer(this, 0, 0);
        this.wisps.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.wisps.addBox(-3.5F, 0.0F, -2.0F, 7, 8, 4, 0.0F);
        this.rightArm = new ModelRenderer(this, 0, 12);
        this.rightArm.setRotationPoint(-4.5F, -12.0F, 0.0F);
        this.rightArm.addBox(-4.0F, -2.0F, -2.0F, 3, 10, 4, 0.0F);
        this.rightHand = new ModelRenderer(this, 46, 0);
        this.rightHand.setRotationPoint(-2.5F, 8.0F, 0.0F);
        this.rightHand.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.leftHand = new ModelRenderer(this, 54, 0);
        this.leftHand.setRotationPoint(2.5F, 8.0F, 0.0F);
        this.leftHand.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.rightShoulderpad = new ModelRenderer(this, 30, 20);
        this.rightShoulderpad.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightShoulderpad.addBox(-4.5F, -2.5F, -2.5F, 4, 2, 5, 0.0F);
        this.rightCuff = new ModelRenderer(this, 38, 30);
        this.rightCuff.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightCuff.addBox(-4.5F, 6.0F, -2.5F, 4, 1, 5, 0.0F);
        this.leftArm = new ModelRenderer(this, 48, 16);
        this.leftArm.setRotationPoint(4.5F, -12.0F, 0.0F);
        this.leftArm.addBox(1.0F, -2.0F, -2.0F, 3, 10, 4, 0.0F);
        this.head = new ModelRenderer(this, 6, 20);
        this.head.setRotationPoint(0.0F, -14.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.head.addChild(this.headFrill);
        this.wisps.addChild(this.body);
        this.leftArm.addChild(this.leftShoulderpad);
        this.leftArm.addChild(this.leftCuff);
        this.body.addChild(this.rightArm);
        this.rightArm.addChild(this.rightHand);
        this.leftArm.addChild(this.leftHand);
        this.rightArm.addChild(this.rightShoulderpad);
        this.rightArm.addChild(this.rightCuff);
        this.body.addChild(this.leftArm);
        this.body.addChild(this.head);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.wisps.render(f5);
    }
    
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
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
