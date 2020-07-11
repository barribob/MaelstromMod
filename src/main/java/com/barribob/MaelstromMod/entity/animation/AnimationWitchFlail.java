package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelMaelstromWitch;

public class AnimationWitchFlail extends ArrayAnimation<ModelMaelstromWitch> {
    private static float[] leftArmX = {000, 060, 120, 180, 240, 300, 360, 420, 480, 540, 600, 660, 720, 780, 840, 900, 960, 1020, 1080, 1140, 1200, 1260, 1320, 1380, 1440, 1500, 1560, 1620, 1680, 1740, 000};
    private static float[] rightArmX = {000, 000, 000, 000, 000, 060, 120, 180, 240, 300, 360, 420, 480, 540, 600, 660, 720, 780, 840, 900, 960, 1020, 1080, 1140, 1200, 1260, 1320, 1380, 1440, 1500, 000};

    public AnimationWitchFlail() {
        super(leftArmX.length);
    }

    @Override
    public void setModelRotations(ModelMaelstromWitch model, float limbSwing, float limbSwingAmount, float partialTicks) {
        model.leftArm.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(leftArmX, partialTicks));
        model.rightArm.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(rightArmX, partialTicks));
    }

}
