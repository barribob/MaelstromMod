package com.barribob.MaelstromMod.entity.model;
//Made with Blockbench

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMaelstromLancer extends ModelAnimated {
    private final ModelRenderer root;
    private final ModelRenderer wisps;
    public final ModelRenderer body;
    public final ModelRenderer leftArm;
    public final ModelRenderer leftForearm;
    private final ModelRenderer leftHand;
    public final ModelRenderer spear;
    private final ModelRenderer leftCuff;
    private final ModelRenderer leftShoulderpad;
    public final ModelRenderer rightArm;
    private final ModelRenderer rightForearm;
    private final ModelRenderer rightHand;
    private final ModelRenderer rightCuff;
    private final ModelRenderer rightShoulderpad;
    private final ModelRenderer head;
    private final ModelRenderer headFrill;

    public ModelMaelstromLancer() {
        textureWidth = 128;
        textureHeight = 128;

        root = new ModelRenderer(this);
        root.setRotationPoint(0.0F, 0.0F, 0.0F);

        wisps = new ModelRenderer(this);
        wisps.setRotationPoint(0.0F, 12.0F, 0.0F);
        root.addChild(wisps);
        wisps.cubeList.add(new ModelBox(wisps, 0, 20, -3.5F, 2.0F, -2.0F, 7, 8, 4, 0.0F, false));

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 0.0F, 0.0F);
        wisps.addChild(body);
        body.cubeList.add(new ModelBox(body, 0, 0, -4.5F, -12.0F, -3.0F, 9, 14, 6, 0.0F, false));

        leftArm = new ModelRenderer(this);
        leftArm.setRotationPoint(4.5F, -10.0F, 0.0F);
        body.addChild(leftArm);
        leftArm.cubeList.add(new ModelBox(leftArm, 34, 6, 1.0F, -2.0F, -1.5F, 3, 6, 3, 0.0F, false));

        leftForearm = new ModelRenderer(this);
        leftForearm.setRotationPoint(2.5F, 4.0F, 0.0F);
        setRotationAngle(leftForearm, -0.8727F, 0.0F, 0.0F);
        leftArm.addChild(leftForearm);
        leftForearm.cubeList.add(new ModelBox(leftForearm, 34, 25, -1.51F, 0.0F, -1.5F, 3, 4, 3, 0.0F, false));

        leftHand = new ModelRenderer(this);
        leftHand.setRotationPoint(0.0F, 4.0F, 0.0F);
        leftForearm.addChild(leftHand);
        leftHand.cubeList.add(new ModelBox(leftHand, 24, 0, -1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F, false));

        spear = new ModelRenderer(this);
        spear.setRotationPoint(0.0F, 2.1246F, -0.2158F);
        leftHand.addChild(spear);
        spear.cubeList.add(new ModelBox(spear, 0, 0, -0.5F, -0.6246F, -26.2842F, 1, 1, 32, 0.0F, false));
        spear.cubeList.add(new ModelBox(spear, 39, 1, -1.0F, -1.1246F, 5.2158F, 2, 2, 1, 0.0F, false));
        spear.cubeList.add(new ModelBox(spear, 39, 16, -1.0F, -1.1246F, -26.7842F, 2, 2, 1, 0.0F, false));
        spear.cubeList.add(new ModelBox(spear, 22, 22, -0.5F, -0.6246F, -30.7842F, 1, 1, 4, 0.0F, false));

        leftCuff = new ModelRenderer(this);
        leftCuff.setRotationPoint(2.5F, -4.0F, -1.0F);
        leftForearm.addChild(leftCuff);
        leftCuff.cubeList.add(new ModelBox(leftCuff, 34, 15, -4.5F, 6.0F, -1.0F, 4, 1, 4, 0.0F, false));

        leftShoulderpad = new ModelRenderer(this);
        leftShoulderpad.setRotationPoint(5.0F, 0.0F, -1.0F);
        leftArm.addChild(leftShoulderpad);
        leftShoulderpad.cubeList.add(new ModelBox(leftShoulderpad, 24, 33, -4.5F, -2.5F, -1.0F, 4, 2, 4, 0.0F, false));

        rightArm = new ModelRenderer(this);
        rightArm.setRotationPoint(-4.5F, -10.0F, 0.0F);
        body.addChild(rightArm);
        rightArm.cubeList.add(new ModelBox(rightArm, 45, 33, -4.0F, -2.0F, -1.5F, 3, 6, 3, 0.0F, false));

        rightForearm = new ModelRenderer(this);
        rightForearm.setRotationPoint(-2.5F, 4.0F, 0.0F);
        rightArm.addChild(rightForearm);
        rightForearm.cubeList.add(new ModelBox(rightForearm, 46, 6, -1.51F, 0.0F, -1.5F, 3, 4, 3, 0.0F, false));

        rightHand = new ModelRenderer(this);
        rightHand.setRotationPoint(0.0F, 4.0F, 0.0F);
        rightForearm.addChild(rightHand);
        rightHand.cubeList.add(new ModelBox(rightHand, 0, 33, -1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F, false));

        rightCuff = new ModelRenderer(this);
        rightCuff.setRotationPoint(2.5F, -4.0F, -1.0F);
        rightForearm.addChild(rightCuff);
        rightCuff.cubeList.add(new ModelBox(rightCuff, 34, 20, -4.5F, 6.0F, -1.0F, 4, 1, 4, 0.0F, false));

        rightShoulderpad = new ModelRenderer(this);
        rightShoulderpad.setRotationPoint(0.0F, 0.0F, -1.0F);
        rightArm.addChild(rightShoulderpad);
        rightShoulderpad.cubeList.add(new ModelBox(rightShoulderpad, 34, 0, -4.5F, -2.5F, -1.0F, 4, 2, 4, 0.0F, false));

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -14.0F, 0.0F);
        body.addChild(head);
        head.cubeList.add(new ModelBox(head, 0, 33, -4.0F, -6.0F, -4.0F, 8, 8, 8, 0.0F, false));

        headFrill = new ModelRenderer(this);
        headFrill.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(headFrill);
        headFrill.cubeList.add(new ModelBox(headFrill, 32, 33, -1.0F, -7.1F, -5.1F, 2, 9, 9, 0.0F, false));
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.root.offsetY = (float) Math.cos(Math.toRadians(ageInTicks * 12)) * 0.1f;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        root.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}