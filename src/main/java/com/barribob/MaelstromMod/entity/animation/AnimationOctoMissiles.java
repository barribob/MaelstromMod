package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelGoldenBoss;

public class AnimationOctoMissiles extends AnimationGoldenBoss
{
    private static float[] arm1X = 	{ 000, -15, -30, -45, -60, -75, -90,-105,-120,-135,-150,-165,-180,-180,-180,-180,-180,-180,-180,-180,-180,-180,-180,-180,-180,-180,-180,-150,-120, -90, -60, -30, 000 };
    private static float[] leftArm2Z = 	{ 000, -15, -30, -45, -60, -75, -90,-105,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120, -90, -60, -30, 000 };
    private static float[] leftArm3Z = 	{ 000, -15, -30, -45, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -30, 000 };
    private static float[] leftArm4Z = 	{ 000, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, 000 };

    public AnimationOctoMissiles()
    {
	super(arm1X.length);
    }

    @Override
    public void setModelRotations(ModelGoldenBoss model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	super.setModelRotations(model, limbSwing, limbSwingAmount, partialTicks);
	model.leftArm1.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(arm1X, partialTicks));
	model.leftArm2.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(leftArm2Z, partialTicks));
	model.leftArm3.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(leftArm3Z, partialTicks));
	model.leftArm4.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(leftArm4Z, partialTicks));

	model.rightArm1.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(arm1X, partialTicks));
	model.rightArm2.rotateAngleZ = (float) Math.toRadians(-this.getInterpolatedFrame(leftArm2Z, partialTicks));
	model.rightArm3.rotateAngleZ = (float) Math.toRadians(-this.getInterpolatedFrame(leftArm3Z, partialTicks));
	model.rightArm4.rotateAngleZ = (float) Math.toRadians(-this.getInterpolatedFrame(leftArm4Z, partialTicks));
    }
}
