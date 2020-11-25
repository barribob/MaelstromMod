package com.barribob.MaelstromMod.entity.model;// Made with Blockbench 3.6.6

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelStatueOfNirvana extends ModelBBAnimated {
    private final ModelRenderer root;
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer sunThingy;
    private final ModelRenderer headband;
    private final ModelRenderer sunThingy2;
    private final ModelRenderer miniHead1;
    private final ModelRenderer miniHead2;
    private final ModelRenderer miniHead3;
    private final ModelRenderer miniHead4;
    private final ModelRenderer miniHead5;
    private final ModelRenderer leftShoulder;
    private final ModelRenderer leftArm4;
    private final ModelRenderer leftForearm4;
    private final ModelRenderer leftArm3;
    private final ModelRenderer leftForearm3;
    private final ModelRenderer leftArm2;
    private final ModelRenderer leftForearm2;
    private final ModelRenderer leftArm1;
    private final ModelRenderer leftForearm1;
    private final ModelRenderer rightShoulder;
    private final ModelRenderer rightArm1;
    private final ModelRenderer rightForearm1;
    private final ModelRenderer rightArm2;
    private final ModelRenderer rightForearm2;
    private final ModelRenderer rightArm3;
    private final ModelRenderer rightForearm3;
    private final ModelRenderer rightArm4;
    private final ModelRenderer rightForearm4;
    private final ModelRenderer leftLeg;
    private final ModelRenderer leftCalf;
    private final ModelRenderer rightLeg;
    private final ModelRenderer rightCalf;

    public ModelStatueOfNirvana() {
        textureWidth = 256;
        textureHeight = 128;

        root = new ModelRenderer(this);
        root.setRotationPoint(0, 0, 0);


        body = new ModelRenderer(this);
        body.setRotationPoint(0, -6, 0);
        root.addChild(body);
        body.setTextureOffset(0, 0).addBox(-8, 0, -4, 16, 24, 8);

        head = new ModelRenderer(this);
        head.setRotationPoint(0, 0, 0);
        body.addChild(head);
        head.setTextureOffset(48, 0).addBox(-7, -14, -7, 14, 14, 14);

        sunThingy = new ModelRenderer(this);
        sunThingy.setRotationPoint(0, 0, -1);
        head.addChild(sunThingy);
        setRotationAngle(sunThingy, 0, 0, -0.7854F);
        sunThingy.setTextureOffset(120, 16).addBox(-5, -15, 8.5F, 20, 20, 1);

        headband = new ModelRenderer(this);
        headband.setRotationPoint(0, 0, 0);
        head.addChild(headband);
        headband.setTextureOffset(33, 28).addBox(-7.5F, -14.1F, -7.5F, 15, 2, 15);

        sunThingy2 = new ModelRenderer(this);
        sunThingy2.setRotationPoint(0, 0, 0);
        head.addChild(sunThingy2);
        sunThingy2.setTextureOffset(212, 16).addBox(-10, -17, 7, 20, 20, 1);

        miniHead1 = new ModelRenderer(this);
        miniHead1.setRotationPoint(0, 0, 0);
        head.addChild(miniHead1);
        setRotationAngle(miniHead1, 0, -0.7854F, 0);
        miniHead1.setTextureOffset(40, 0).addBox(-2, -18, -11, 4, 4, 4);

        miniHead2 = new ModelRenderer(this);
        miniHead2.setRotationPoint(0, 0, 0);
        head.addChild(miniHead2);
        setRotationAngle(miniHead2, 0, 0.7854F, 0);
        miniHead2.setTextureOffset(78, 28).addBox(-2, -18, -11, 4, 4, 4);

        miniHead3 = new ModelRenderer(this);
        miniHead3.setRotationPoint(0, 0, 0);
        head.addChild(miniHead3);
        miniHead3.setTextureOffset(162, 28).addBox(-2, -18, -9, 4, 4, 4);

        miniHead4 = new ModelRenderer(this);
        miniHead4.setRotationPoint(0, 0, 0);
        head.addChild(miniHead4);
        setRotationAngle(miniHead4, 0, 1.5708F, 0);
        miniHead4.setTextureOffset(178, 28).addBox(-2, -18, -9, 4, 4, 4);

        miniHead5 = new ModelRenderer(this);
        miniHead5.setRotationPoint(0, 0, 0);
        head.addChild(miniHead5);
        setRotationAngle(miniHead5, 0, -1.5708F, 0);
        miniHead5.setTextureOffset(194, 28).addBox(-2, -18, -9, 4, 4, 4);

        leftShoulder = new ModelRenderer(this);
        leftShoulder.setRotationPoint(-11, 2, 0);
        body.addChild(leftShoulder);
        setRotationAngle(leftShoulder, 0, 0, 0);
        leftShoulder.setTextureOffset(168, 0).addBox(-3, -3, -3, 6, 6, 6);

        leftArm4 = new ModelRenderer(this);
        leftArm4.setRotationPoint(0, 0, 0);
        leftShoulder.addChild(leftArm4);
        leftArm4.setTextureOffset(152, 0).addBox(-2, -2, -1.94F, 4, 12, 4);

        leftForearm4 = new ModelRenderer(this);
        leftForearm4.setRotationPoint(0, 10, 0.1F);
        leftArm4.addChild(leftForearm4);
        leftForearm4.setTextureOffset(158, 36).addBox(-2, 0, -2.04F, 4, 10, 4);

        leftArm3 = new ModelRenderer(this);
        leftArm3.setRotationPoint(0, 0, 0);
        leftShoulder.addChild(leftArm3);
        setRotationAngle(leftArm3, 0, 0, 0);
        leftArm3.setTextureOffset(136, 0).addBox(-2, -2, -1.93F, 4, 12, 4);

        leftForearm3 = new ModelRenderer(this);
        leftForearm3.setRotationPoint(0, 10, 0.1F);
        leftArm3.addChild(leftForearm3);
        leftForearm3.setTextureOffset(93, 32).addBox(-2, 0, -2.03F, 4, 10, 4);

        leftArm2 = new ModelRenderer(this);
        leftArm2.setRotationPoint(0, 0, 0);
        leftShoulder.addChild(leftArm2);
        leftArm2.setTextureOffset(120, 0).addBox(-2, -2, -1.92F, 4, 12, 4);

        leftForearm2 = new ModelRenderer(this);
        leftForearm2.setRotationPoint(0, 10, 0.1F);
        leftArm2.addChild(leftForearm2);
        leftForearm2.setTextureOffset(16, 32).addBox(-2, 0, -2.02F, 4, 10, 4);

        leftArm1 = new ModelRenderer(this);
        leftArm1.setRotationPoint(0, 0, 0);
        leftShoulder.addChild(leftArm1);
        leftArm1.setTextureOffset(104, 0).addBox(-2, -2, -1.91F, 4, 12, 4);

        leftForearm1 = new ModelRenderer(this);
        leftForearm1.setRotationPoint(0, 10, 0.1F);
        leftArm1.addChild(leftForearm1);
        leftForearm1.setTextureOffset(0, 32).addBox(-2, 0, -2.01F, 4, 10, 4);

        rightShoulder = new ModelRenderer(this);
        rightShoulder.setRotationPoint(11, 2, 0);
        body.addChild(rightShoulder);
        rightShoulder.setTextureOffset(192, 0).addBox(-3, -3, -3, 6, 6, 6);

        rightArm1 = new ModelRenderer(this);
        rightArm1.setRotationPoint(0, 0, 0);
        rightShoulder.addChild(rightArm1);
        rightArm1.setTextureOffset(216, 0).addBox(-2, -2, -1.91F, 4, 12, 4);

        rightForearm1 = new ModelRenderer(this);
        rightForearm1.setRotationPoint(0, 10, 0.1F);
        rightArm1.addChild(rightForearm1);
        rightForearm1.setTextureOffset(174, 36).addBox(-2, 0, -2.01F, 4, 10, 4);

        rightArm2 = new ModelRenderer(this);
        rightArm2.setRotationPoint(0, 0, 0);
        rightShoulder.addChild(rightArm2);
        rightArm2.setTextureOffset(232, 0).addBox(-2, -2, -1.92F, 4, 12, 4);

        rightForearm2 = new ModelRenderer(this);
        rightForearm2.setRotationPoint(0, 10, 0.1F);
        rightArm2.addChild(rightForearm2);
        rightForearm2.setTextureOffset(190, 36).addBox(-2, 0, -2.02F, 4, 10, 4);

        rightArm3 = new ModelRenderer(this);
        rightArm3.setRotationPoint(0, 0, 0);
        rightShoulder.addChild(rightArm3);
        rightArm3.setTextureOffset(164, 12).addBox(-2, -2, -1.93F, 4, 12, 4);

        rightForearm3 = new ModelRenderer(this);
        rightForearm3.setRotationPoint(0, 10, 0.1F);
        rightArm3.addChild(rightForearm3);
        rightForearm3.setTextureOffset(109, 37).addBox(-2, 0, -2.03F, 4, 10, 4);

        rightArm4 = new ModelRenderer(this);
        rightArm4.setRotationPoint(0, 0, 0);
        rightShoulder.addChild(rightArm4);
        rightArm4.setTextureOffset(180, 12).addBox(-2, -2, -1.94F, 4, 12, 4);

        rightForearm4 = new ModelRenderer(this);
        rightForearm4.setRotationPoint(0, 10, 0.1F);
        rightArm4.addChild(rightForearm4);
        rightForearm4.setTextureOffset(125, 37).addBox(-2, 0, -2.04F, 4, 10, 4);

        leftLeg = new ModelRenderer(this);
        leftLeg.setRotationPoint(-3.5F, 22, 0);
        body.addChild(leftLeg);
        setRotationAngle(leftLeg, -1.5708F, 0.6981F, -0.3142F);
        leftLeg.setTextureOffset(196, 12).addBox(-2, 0, -2, 4, 12, 4);

        leftCalf = new ModelRenderer(this);
        leftCalf.setRotationPoint(0, 12, -0.1F);
        leftLeg.addChild(leftCalf);
        setRotationAngle(leftCalf, 2.618F, 0, 1.3526F);
        leftCalf.setTextureOffset(141, 37).addBox(-2, 0, -2, 4, 12, 4);

        rightLeg = new ModelRenderer(this);
        rightLeg.setRotationPoint(3.5F, 22, 0);
        body.addChild(rightLeg);
        setRotationAngle(rightLeg, -1.5708F, -0.6981F, 0.3142F);
        rightLeg.setTextureOffset(104, 16).addBox(-2, 0, -2, 4, 12, 4);

        rightCalf = new ModelRenderer(this);
        rightCalf.setRotationPoint(0, 12, -0.1F);
        rightLeg.addChild(rightCalf);
        setRotationAngle(rightCalf, 2.618F, 0, -1.3526F);
        rightCalf.setTextureOffset(206, 37).addBox(-2, 0, -2, 4, 12, 4);
    }


    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        this.body.offsetY = (float) Math.cos(Math.toRadians(ageInTicks * 2)) * 0.2f - 0.5f;
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}