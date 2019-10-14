package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelGoldenBoss;

public class AnimationMegaMissile extends AnimationGoldenBoss
{
    private static float[] arm1X = 		{ 000, -15, -30, -45, -60, -75, -90,-105,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120,-120, -90, -60, -30, 000 };
    private static float[] arm2X = 		{ 000, -15, -30, -45, -60, -75, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -90, -60, -30, 000 };
    private static float[] arm3X = 		{ 000, -15, -30, -45, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -30, 000 };
    private static float[] arm4X = 		{ 000, -15, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -15, 000 };
    private static float[] rightForearmZ = 	{ 000, -15, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -30, -15, 000 };

    public AnimationMegaMissile()
    {
	super(arm1X.length);
    }

    @Override
    public void setModelRotations(ModelGoldenBoss model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	super.setModelRotations(model, limbSwing, limbSwingAmount, partialTicks);
	model.leftArm1.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(arm1X, partialTicks));
	model.leftArm2.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(arm2X, partialTicks));
	model.leftArm3.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(arm3X, partialTicks));
	model.leftArm4.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(arm4X, partialTicks));
	model.leftForearm1.rotateAngleZ = (float) Math.toRadians(-this.getInterpolatedFrame(rightForearmZ, partialTicks));
	model.leftForearm2.rotateAngleZ = (float) Math.toRadians(-this.getInterpolatedFrame(rightForearmZ, partialTicks));
	model.leftForearm3.rotateAngleZ = (float) Math.toRadians(-this.getInterpolatedFrame(rightForearmZ, partialTicks));
	model.leftForearm4.rotateAngleZ = (float) Math.toRadians(-this.getInterpolatedFrame(rightForearmZ, partialTicks));
	model.rightArm1.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(arm1X, partialTicks));
	model.rightArm2.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(arm2X, partialTicks));
	model.rightArm3.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(arm3X, partialTicks));
	model.rightArm4.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(arm4X, partialTicks));
	model.rightForearm1.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(rightForearmZ, partialTicks));
	model.rightForearm2.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(rightForearmZ, partialTicks));
	model.rightForearm3.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(rightForearmZ, partialTicks));
	model.rightForearm4.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(rightForearmZ, partialTicks));
    }
}
