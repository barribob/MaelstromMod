package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelAzureGolem;

public class AnimationAzureGolem extends ArrayAnimation<ModelAzureGolem>
{
    // Animation frames for the pound attack
    private static float[] armFramesDegrees = { -45, -60, -75, -90, -105, -120, -135, -150, -165, -180, -180, -180, -180, -165, -135, -105, -90, -90, -90, -90, - 90, -75, -60, -45, -30, -15, 0 };
    private static float[] backFramesDegrees = { 0, 0, 0, 0, -5, -15, -15, -15, -15, -15, -15, 0, 15, 30, 45, 55, 65, 65, 65, 65, 65, 50, 40, 30, 20, 10, 0};

    public AnimationAzureGolem()
    {
	super(armFramesDegrees.length);
    }
    
    @Override
    public void setModelRotations(ModelAzureGolem model, float limbSwing, float limbSwingAmount, float partialTicks)
    {        
        float armsRotationX = (float) Math.toRadians(this.getInterpolatedFrame(armFramesDegrees, partialTicks));
	float waistRotationX = (float) Math.toRadians(this.getInterpolatedFrame(backFramesDegrees, partialTicks));
	
	if (armsRotationX == 0)
	{
	    // Normal walking animation
	    model.rightBicep.rotateAngleX = (-0.2F + model.limbSwingFactor * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
	    model.leftBicep.rotateAngleX = (-0.2F - model.limbSwingFactor * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
	}
	else
	{
	    model.rightBicep.rotateAngleX = armsRotationX;
	    model.leftBicep.rotateAngleX = armsRotationX;
	}
	
	if(waistRotationX == 0)
	{
	    model.waist.rotateAngleX = 0;
	}
	else
	{
	    model.waist.rotateAngleX = waistRotationX;
	}
	model.waist.rotateAngleZ = 0;
    }
    
    private float triangleWave(float p_78172_1_, float p_78172_2_)
    {
	return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }
}
