package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromHealer;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

// Made with Blockbench

public class ModelMaelstromHealer extends ModelBBAnimated {
    private final ModelRenderer root;
    private final ModelRenderer wisps;
    private final ModelRenderer body;
    private final ModelRenderer leftArm;
    private final ModelRenderer leftForearm;
    private final ModelRenderer leftHand;
    private final ModelRenderer leftCuff;
    private final ModelRenderer leftShoulderpad;
    private final ModelRenderer rightArm;
    private final ModelRenderer rightForearm;
    private final ModelRenderer rightHand;
    private final ModelRenderer rightCuff;
    private final ModelRenderer rightShoulderpad;
    private final ModelRenderer head;
    private final ModelRenderer headFrill;
    private final ModelRenderer wings;
    private final ModelRenderer left_wing;
    private final ModelRenderer left_wing2;
    private final ModelRenderer left_wing3;
    private final ModelRenderer left_wing4;
    private final ModelRenderer right_wing5;
    private final ModelRenderer right_wing6;
    private final ModelRenderer right_wing7;
    private final ModelRenderer right_wing8;

    public ModelMaelstromHealer() {
        textureWidth = 128;
        textureHeight = 128;

        root = new ModelRenderer(this);
        root.setRotationPoint(0.0F, 0.0F, 0.0F);

        wisps = new ModelRenderer(this);
        wisps.setRotationPoint(0.0F, 12.0F, 0.0F);
        root.addChild(wisps);
        wisps.cubeList.add(new ModelBox(wisps, 48, 48, -3.5F, 2.0F, -2.0F, 7, 8, 4, 0.0F, false));

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 0.0F, 0.0F);
        wisps.addChild(body);
        body.cubeList.add(new ModelBox(body, 0, 15, -4.5F, -12.0F, -3.0F, 9, 14, 6, 0.0F, false));

        leftArm = new ModelRenderer(this);
        leftArm.setRotationPoint(4.5F, -10.0F, 0.0F);
        body.addChild(leftArm);
        leftArm.cubeList.add(new ModelBox(leftArm, 39, 43, 1.0F, -2.0F, -1.5F, 3, 6, 3, 0.0F, false));

        leftForearm = new ModelRenderer(this);
        leftForearm.setRotationPoint(2.5F, 4.0F, 0.0F);
        leftArm.addChild(leftForearm);
        leftForearm.cubeList.add(new ModelBox(leftForearm, 12, 58, -1.51F, 0.0F, -1.5F, 3, 4, 3, 0.0F, false));

        leftHand = new ModelRenderer(this);
        leftHand.setRotationPoint(0.0F, 4.0F, 0.0F);
        leftForearm.addChild(leftHand);
        leftHand.cubeList.add(new ModelBox(leftHand, 26, 43, -1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F, false));

        leftCuff = new ModelRenderer(this);
        leftCuff.setRotationPoint(2.5F, -4.0F, -1.0F);
        leftForearm.addChild(leftCuff);
        leftCuff.cubeList.add(new ModelBox(leftCuff, 50, 39, -4.5F, 6.0F, -1.0F, 4, 1, 4, 0.0F, false));

        leftShoulderpad = new ModelRenderer(this);
        leftShoulderpad.setRotationPoint(5.0F, 0.0F, -1.0F);
        leftArm.addChild(leftShoulderpad);
        leftShoulderpad.cubeList.add(new ModelBox(leftShoulderpad, 30, 19, -4.5F, -2.5F, -1.0F, 4, 2, 4, 0.0F, false));

        rightArm = new ModelRenderer(this);
        rightArm.setRotationPoint(-4.5F, -10.0F, 0.0F);
        body.addChild(rightArm);
        rightArm.cubeList.add(new ModelBox(rightArm, 0, 58, -4.0F, -2.0F, -1.5F, 3, 6, 3, 0.0F, false));

        rightForearm = new ModelRenderer(this);
        rightForearm.setRotationPoint(-2.5F, 4.0F, 0.0F);
        rightArm.addChild(rightForearm);
        rightForearm.cubeList.add(new ModelBox(rightForearm, 45, 60, -1.51F, 0.0F, -1.5F, 3, 4, 3, 0.0F, false));

        rightHand = new ModelRenderer(this);
        rightHand.setRotationPoint(0.0F, 4.0F, 0.0F);
        rightForearm.addChild(rightHand);
        rightHand.cubeList.add(new ModelBox(rightHand, 28, 61, -1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F, false));

        rightCuff = new ModelRenderer(this);
        rightCuff.setRotationPoint(2.5F, -4.0F, -1.0F);
        rightForearm.addChild(rightCuff);
        rightCuff.cubeList.add(new ModelBox(rightCuff, 54, 32, -4.5F, 6.0F, -1.0F, 4, 1, 4, 0.0F, false));

        rightShoulderpad = new ModelRenderer(this);
        rightShoulderpad.setRotationPoint(0.0F, 0.0F, -1.0F);
        rightArm.addChild(rightShoulderpad);
        rightShoulderpad.cubeList.add(new ModelBox(rightShoulderpad, 0, 35, -4.5F, -2.5F, -1.0F, 4, 2, 4, 0.0F, false));

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -14.0F, 0.0F);
        body.addChild(head);
        head.cubeList.add(new ModelBox(head, 22, 27, -4.0F, -6.0F, -4.0F, 8, 8, 8, 0.0F, false));

        headFrill = new ModelRenderer(this);
        headFrill.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(headFrill);
        headFrill.cubeList.add(new ModelBox(headFrill, 26, 43, -1.0F, -7.1F, -5.1F, 2, 9, 9, 0.0F, false));

        wings = new ModelRenderer(this);
        wings.setRotationPoint(0.0F, -10.0F, 3.0F);
        setRotationAngle(wings, 0.2618F, 0.0F, 0.0F);
        body.addChild(wings);

        left_wing = new ModelRenderer(this);
        left_wing.setRotationPoint(0.0F, 0.5F, 0.5F);
        setRotationAngle(left_wing, 0.0F, (float) Math.toRadians(-75), -0.3491F);
        wings.addChild(left_wing);
        left_wing.cubeList.add(new ModelBox(left_wing, 24, 17, 0.0F, -0.5F, -0.5F, 10, 1, 1, 0.0F, false));
        left_wing.cubeList.add(new ModelBox(left_wing, 44, 0, 0.0F, 0.5F, 0.0F, 12, 15, 0, 0.0F, false));

        left_wing2 = new ModelRenderer(this);
        left_wing2.setRotationPoint(10.0F, 0.0F, 0.0F);
        setRotationAngle(left_wing2, 0.0F, 0.0F, -0.3491F);
        left_wing.addChild(left_wing2);
        left_wing2.cubeList.add(new ModelBox(left_wing2, 48, 44, 0.0F, -0.5F, -0.5F, 8, 1, 1, 0.0F, false));
        left_wing2.cubeList.add(new ModelBox(left_wing2, 0, 43, -3.0F, 0.5F, 0.1F, 13, 15, 0, 0.0F, false));

        left_wing3 = new ModelRenderer(this);
        left_wing3.setRotationPoint(8.0F, 0.0F, 0.0F);
        setRotationAngle(left_wing3, 0.0F, 0.0F, -0.3491F);
        left_wing2.addChild(left_wing3);
        left_wing3.cubeList.add(new ModelBox(left_wing3, 0, 41, 0.0F, -0.5F, -0.5F, 8, 1, 1, 0.0F, false));
        left_wing3.cubeList.add(new ModelBox(left_wing3, 46, 17, -3.0F, 0.5F, 0.0F, 11, 15, 0, 0.0F, false));

        left_wing4 = new ModelRenderer(this);
        left_wing4.setRotationPoint(8.0F, 0.0F, 0.0F);
        setRotationAngle(left_wing4, 0.0F, 0.0F, 0.6109F);
        left_wing3.addChild(left_wing4);
        left_wing4.cubeList.add(new ModelBox(left_wing4, 0, 0, 0.0F, 0.5F, 0.1F, 22, 15, 0, 0.0F, false));
        left_wing4.cubeList.add(new ModelBox(left_wing4, 24, 15, 0.0F, -0.5F, -0.5F, 22, 1, 1, 0.0F, false));

        right_wing5 = new ModelRenderer(this);
        right_wing5.setRotationPoint(0.0F, 0.5F, 0.5F);
        setRotationAngle(right_wing5, 0.0F, (float) Math.toRadians(75), 0.3491F);
        wings.addChild(right_wing5);
        right_wing5.cubeList.add(new ModelBox(right_wing5, 24, 17, -10.0F, -0.5F, -0.5F, 10, 1, 1, 0.0F, true));
        right_wing5.cubeList.add(new ModelBox(right_wing5, 44, 0, -12.0F, 0.5F, 0.0F, 12, 15, 0, 0.0F, true));

        right_wing6 = new ModelRenderer(this);
        right_wing6.setRotationPoint(-10.0F, 0.0F, 0.0F);
        setRotationAngle(right_wing6, 0.0F, 0.0F, 0.3491F);
        right_wing5.addChild(right_wing6);
        right_wing6.cubeList.add(new ModelBox(right_wing6, 48, 44, -8.0F, -0.5F, -0.5F, 8, 1, 1, 0.0F, true));
        right_wing6.cubeList.add(new ModelBox(right_wing6, 0, 43, -10.0F, 0.5F, 0.1F, 13, 15, 0, 0.0F, true));

        right_wing7 = new ModelRenderer(this);
        right_wing7.setRotationPoint(-8.0F, 0.0F, 0.0F);
        setRotationAngle(right_wing7, 0.0F, 0.0F, 0.3491F);
        right_wing6.addChild(right_wing7);
        right_wing7.cubeList.add(new ModelBox(right_wing7, 0, 41, -8.0F, -0.5F, -0.5F, 8, 1, 1, 0.0F, true));
        right_wing7.cubeList.add(new ModelBox(right_wing7, 46, 17, -8.0F, 0.5F, 0.0F, 11, 15, 0, 0.0F, true));

        right_wing8 = new ModelRenderer(this);
        right_wing8.setRotationPoint(-8.0F, 0.0F, 0.0F);
        setRotationAngle(right_wing8, 0.0F, 0.0F, -0.6109F);
        right_wing7.addChild(right_wing8);
        right_wing8.cubeList.add(new ModelBox(right_wing8, 0, 0, -22.0F, 0.5F, 0.1F, 22, 15, 0, 0.0F, true));
        right_wing8.cubeList.add(new ModelBox(right_wing8, 24, 15, -22.0F, -0.5F, -0.5F, 22, 1, 1, 0.0F, true));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        root.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.root.offsetY += (float) Math.cos(Math.toRadians(ageInTicks * 12)) * 0.1f;
        this.leftArm.offsetY = (float) Math.cos(Math.toRadians(ageInTicks * 4)) * 0.05f;
        this.rightArm.offsetY = (float) Math.cos(Math.toRadians(ageInTicks * 4)) * 0.05f;

        if (entity instanceof EntityMaelstromHealer) {
            EntityMaelstromHealer healer = ((EntityMaelstromHealer) entity);
            double acceleration = healer.getAcceleration().dotProduct(entity.getForward());
            root.rotateAngleX += (float) Math.toRadians(acceleration * 300 + (healer.isFlying() ? 20 : 0));
        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}