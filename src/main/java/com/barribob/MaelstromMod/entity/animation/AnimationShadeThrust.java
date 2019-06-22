package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelGoldenShade;

public class AnimationShadeThrust extends Animation<ModelGoldenShade>
{
    private static float[] armX = { -15, -30, -60, -90, -120, -150, -180, -180, -180, -180, -120, -60, -15, 0, 0, 0 };
    private static float[] bodyX = { 0, 0, 0, -5, -10, -15, -15, -15, -15, -10, -5, 0, 5, 10, 10, 0 };

    public AnimationShadeThrust()
    {
	super(armX.length);
    }

    @Override
    public void setModelRotations(ModelGoldenShade model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	model.leftArm.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(armX, partialTicks));
	model.rightArm.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(armX, partialTicks));
	model.body.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(bodyX, partialTicks));
    }
}
