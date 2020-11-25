package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelGoldenShade - Barribob
 * Created using Tabula 7.0.0
 */
public class ModelIronShade extends ModelAnimated {
    public ModelRenderer wisps;
    public ModelRenderer body;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer head;
    public ModelRenderer rightHand;
    public ModelRenderer rightShoulderpad;
    public ModelRenderer rightCuff;
    public ModelRenderer chainLink1;
    public ModelRenderer chainLink2;
    public ModelRenderer chainLink3;
    public ModelRenderer chainLink4;
    public ModelRenderer chainLink5;
    public ModelRenderer chainLink6;
    public ModelRenderer ball;
    public ModelRenderer leftHand;
    public ModelRenderer leftShoulderpad;
    public ModelRenderer leftCuff;
    public ModelRenderer chainLink1_1;
    public ModelRenderer chainLink2_1;
    public ModelRenderer chainLink3_1;
    public ModelRenderer chainLink4_1;
    public ModelRenderer chainLink5_1;
    public ModelRenderer chainLink6_1;
    public ModelRenderer ball_1;
    public ModelRenderer headFrill;

    public ModelIronShade() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body = new ModelRenderer(this, 22, 0);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-4.5F, -14.0F, -3.0F, 9, 14, 6, 0.0F);
        this.rightShoulderpad = new ModelRenderer(this, 30, 20);
        this.rightShoulderpad.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightShoulderpad.addBox(-4.5F, -2.5F, -2.5F, 4, 2, 5, 0.0F);
        this.ball_1 = new ModelRenderer(this, 12, 43);
        this.ball_1.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.ball_1.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
        this.chainLink1_1 = new ModelRenderer(this, 50, 40);
        this.chainLink1_1.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.chainLink1_1.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.chainLink1 = new ModelRenderer(this, 22, 0);
        this.chainLink1.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.chainLink1.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.chainLink3_1 = new ModelRenderer(this, 56, 40);
        this.chainLink3_1.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.chainLink3_1.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.chainLink4 = new ModelRenderer(this, 57, 10);
        this.chainLink4.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.chainLink4.addBox(-1.0F, 0.0F, -0.5F, 2, 4, 1, 0.0F);
        this.chainLink5 = new ModelRenderer(this, 14, 12);
        this.chainLink5.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.chainLink5.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.leftHand = new ModelRenderer(this, 54, 34);
        this.leftHand.setRotationPoint(2.5F, 8.0F, 0.0F);
        this.leftHand.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.headFrill = new ModelRenderer(this, 34, 42);
        this.headFrill.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headFrill.addBox(-1.0F, -9.0F, -5.0F, 2, 9, 10, 0.0F);
        this.wisps = new ModelRenderer(this, 0, 0);
        this.wisps.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.wisps.addBox(-3.5F, 0.0F, -2.0F, 7, 8, 4, 0.0F);
        this.chainLink6_1 = new ModelRenderer(this, 38, 42);
        this.chainLink6_1.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.chainLink6_1.addBox(-1.0F, 0.0F, -0.5F, 2, 4, 1, 0.0F);
        this.leftCuff = new ModelRenderer(this, 34, 36);
        this.leftCuff.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftCuff.addBox(0.5F, 6.0F, -2.5F, 4, 1, 5, 0.0F);
        this.rightArm = new ModelRenderer(this, 0, 12);
        this.rightArm.setRotationPoint(-4.5F, -12.0F, 0.0F);
        this.rightArm.addBox(-4.0F, -2.0F, -2.0F, 3, 10, 4, 0.0F);
        this.chainLink5_1 = new ModelRenderer(this, 32, 42);
        this.chainLink5_1.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.chainLink5_1.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.chainLink4_1 = new ModelRenderer(this, 29, 36);
        this.chainLink4_1.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.chainLink4_1.addBox(-1.0F, 0.0F, -0.5F, 2, 4, 1, 0.0F);
        this.rightHand = new ModelRenderer(this, 46, 0);
        this.rightHand.setRotationPoint(-2.5F, 8.0F, 0.0F);
        this.rightHand.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.rightCuff = new ModelRenderer(this, 38, 30);
        this.rightCuff.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightCuff.addBox(-4.5F, 6.0F, -2.5F, 4, 1, 5, 0.0F);
        this.chainLink3 = new ModelRenderer(this, 52, 5);
        this.chainLink3.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.chainLink3.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.leftArm = new ModelRenderer(this, 48, 16);
        this.leftArm.setRotationPoint(4.5F, -12.0F, 0.0F);
        this.leftArm.addBox(1.0F, -2.0F, -2.0F, 3, 10, 4, 0.0F);
        this.chainLink6 = new ModelRenderer(this, 0, 26);
        this.chainLink6.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.chainLink6.addBox(-1.0F, 0.0F, -0.5F, 2, 4, 1, 0.0F);
        this.ball = new ModelRenderer(this, 0, 36);
        this.ball.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.ball.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
        this.leftShoulderpad = new ModelRenderer(this, 16, 36);
        this.leftShoulderpad.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftShoulderpad.addBox(0.5F, -2.5F, -2.5F, 4, 2, 5, 0.0F);
        this.head = new ModelRenderer(this, 6, 20);
        this.head.setRotationPoint(0.0F, -14.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.chainLink2_1 = new ModelRenderer(this, 0, 31);
        this.chainLink2_1.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.chainLink2_1.addBox(-1.0F, 0.0F, -0.5F, 2, 4, 1, 0.0F);
        this.chainLink2 = new ModelRenderer(this, 54, 0);
        this.chainLink2.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.chainLink2.addBox(-1.0F, 0.0F, -0.5F, 2, 4, 1, 0.0F);
        this.wisps.addChild(this.body);
        this.rightArm.addChild(this.rightShoulderpad);
        this.chainLink6_1.addChild(this.ball_1);
        this.leftHand.addChild(this.chainLink1_1);
        this.rightHand.addChild(this.chainLink1);
        this.chainLink2_1.addChild(this.chainLink3_1);
        this.chainLink3.addChild(this.chainLink4);
        this.chainLink4.addChild(this.chainLink5);
        this.leftArm.addChild(this.leftHand);
        this.head.addChild(this.headFrill);
        this.chainLink5_1.addChild(this.chainLink6_1);
        this.leftArm.addChild(this.leftCuff);
        this.body.addChild(this.rightArm);
        this.chainLink4_1.addChild(this.chainLink5_1);
        this.chainLink3_1.addChild(this.chainLink4_1);
        this.rightArm.addChild(this.rightHand);
        this.rightArm.addChild(this.rightCuff);
        this.chainLink2.addChild(this.chainLink3);
        this.body.addChild(this.leftArm);
        this.chainLink5.addChild(this.chainLink6);
        this.chainLink6.addChild(this.ball);
        this.leftArm.addChild(this.leftShoulderpad);
        this.body.addChild(this.head);
        this.chainLink1_1.addChild(this.chainLink2_1);
        this.chainLink1.addChild(this.chainLink2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.wisps.render(f5);
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
