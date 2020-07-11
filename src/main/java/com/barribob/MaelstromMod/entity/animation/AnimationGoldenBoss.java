package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelGoldenBoss;

public class AnimationGoldenBoss extends ArrayAnimation<ModelGoldenBoss> {
    public AnimationGoldenBoss(int animationLength) {
        super(animationLength);
    }

    @Override
    public void setModelRotations(ModelGoldenBoss model, float limbSwing, float limbSwingAmount, float partialTicks) {
        model.leftArm1.rotateAngleX = 0;
        model.leftArm2.rotateAngleX = 0;
        model.leftArm3.rotateAngleX = 0;
        model.leftArm4.rotateAngleX = 0;
        model.leftArm3.rotateAngleZ = 0;
        model.leftArm4.rotateAngleZ = 0;
        model.leftArm1.rotateAngleZ = 0;
        model.leftArm1.rotateAngleZ = 0;
        model.leftForearm1.rotateAngleZ = 0;
        model.leftForearm2.rotateAngleZ = 0;
        model.leftForearm3.rotateAngleZ = 0;
        model.leftForearm4.rotateAngleZ = 0;
        model.rightArm1.rotateAngleX = 0;
        model.rightArm2.rotateAngleX = 0;
        model.rightArm3.rotateAngleX = 0;
        model.rightArm4.rotateAngleX = 0;
        model.rightArm1.rotateAngleZ = 0;
        model.rightArm2.rotateAngleZ = 0;
        model.rightArm3.rotateAngleZ = 0;
        model.rightArm4.rotateAngleZ = 0;
        model.rightForearm1.rotateAngleZ = 0;
        model.rightForearm2.rotateAngleZ = 0;
        model.rightForearm3.rotateAngleZ = 0;
        model.rightForearm4.rotateAngleZ = 0;
    }
}
