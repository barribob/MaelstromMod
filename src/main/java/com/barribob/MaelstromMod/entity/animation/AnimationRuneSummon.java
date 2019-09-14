package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelGoldenBoss;

public class AnimationRuneSummon extends AnimationGoldenBoss
{
    private static float[] arm1X = 		{ 000, -15, -30, -45, -60, -75, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -60, -30, 000 };
    private static float[] arm2X = 		{ 000, -15, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -15, 000 };
    private static float[] arm3Z = 		{ 000, -15, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -15, 000 };
    private static float[] arm4Z = 		{ 000, -15, -30, -45, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -30, 000 };
    private static float[] rightForearmZ = 	{ 000, -15, -30, -45, -60, -75, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -60, -30, 000 };

    public AnimationRuneSummon()
    {
	super(arm1X.length);
    }

    @Override
    public void setModelRotations(ModelGoldenBoss model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	super.setModelRotations(model, limbSwing, limbSwingAmount, partialTicks);
	model.leftArm1.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(arm1X, partialTicks));
	model.leftArm2.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(arm2X, partialTicks));
	model.leftArm3.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(arm3Z, partialTicks));
	model.leftArm4.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(arm4Z, partialTicks));
	model.leftForearm1.rotateAngleZ = (float) Math.toRadians(-this.getInterpolatedFrame(rightForearmZ, partialTicks));
	model.leftForearm2.rotateAngleZ = (float) Math.toRadians(-this.getInterpolatedFrame(rightForearmZ, partialTicks));
	model.rightArm1.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(arm1X, partialTicks));
	model.rightArm2.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(arm2X, partialTicks));
	model.rightArm3.rotateAngleZ = (float) Math.toRadians(-this.getInterpolatedFrame(arm3Z, partialTicks));
	model.rightArm4.rotateAngleZ = (float) Math.toRadians(-this.getInterpolatedFrame(arm4Z, partialTicks));
	model.rightForearm1.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(rightForearmZ, partialTicks));
	model.rightForearm2.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(rightForearmZ, partialTicks));
    }
}
