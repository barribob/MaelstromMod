package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelGoldenShade;

public class AnimationDualThrow extends Animation<ModelGoldenShade>
{
    private static float[] armX = 	{ 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 015, 030, 030, 030, 030, 015, 000, -30, -60, -60, -60, -45, -30, -15, 000 };
    private static float[] leftArmZ = 	{ 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, -15, -30, -30, -30, -30, -30, -30, -30, -30, -15, 000, 000, 000, 000, 000 };
    private static float[] rightArmZ = 	{ 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 015, 030, 030, 030, 030, 030, 000, 030, 030, 015, 000, 000, 000, 000, 000 };
    private static float[] bodyX = 	{ 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 005, 010, 015, 015, 010, 005, 000, -05, -15, -15, -15, -15, -10, -05, 000 };

    public AnimationDualThrow()
    {
	super(armX.length);
    }

    @Override
    public void setModelRotations(ModelGoldenShade model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	model.leftArm.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(armX, partialTicks));
	model.rightArm.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(armX, partialTicks));
	model.leftArm.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(leftArmZ, partialTicks));
	model.rightArm.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(rightArmZ, partialTicks));
	model.body.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(bodyX, partialTicks));
    }

}
