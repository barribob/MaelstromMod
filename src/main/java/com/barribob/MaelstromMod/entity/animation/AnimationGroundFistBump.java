package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelAzureGolem;

import net.minecraft.client.model.ModelBase;

public class AnimationGroundFistBump extends Animation<ModelAzureGolem>
{
    // Animation frames for the pound attack
    private static float[] armFramesDegrees = 	{ 000, 000, 015, 030, 045, 060, 060, 060, 060, 060, 060, 030, 000, -30, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -60, -45, -30, -15, 000, 000, 000};
    private static float[] backFramesDegreesX = { 000, 000, 000, 000, -05, -15, -15, -15, -15, -15, -15, 000, 015, 030, 045, 055, 065, 065, 065, 065, 065, 065, 065, 065, 065, 065, 050, 040, 030, 020, 010, 000 };
    private static float[] backFramesDegreesZ = { 000, 000, 000, 000, -05, -10, -15, -15, -15, -15, -15, 000, 000, 015, 030, 030, 030, 030, 030, 030, 030, 030, 030, 030, 030, 030, 020, 010, 000, 000, 000, 000 };

    public AnimationGroundFistBump()
    {
	super(armFramesDegrees.length);
    }

    @Override
    public void setModelRotations(ModelAzureGolem model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	model.rightBicep.rotateAngleX = (-0.2F + model.limbSwingFactor * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
	float armsRotationX = (float) Math.toRadians(this.getInterpolatedFrame(armFramesDegrees, partialTicks));
	if (armsRotationX == 0)
	{
	    // Normal walking animation
	    model.leftBicep.rotateAngleX = (-0.2F - model.limbSwingFactor * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
	}
	else
	{
	    model.leftBicep.rotateAngleX = armsRotationX;
	}

	model.waist.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(backFramesDegreesX, partialTicks));
	model.waist.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(backFramesDegreesZ, partialTicks));
    }

    private float triangleWave(float p_78172_1_, float p_78172_2_)
    {
	return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }
}
