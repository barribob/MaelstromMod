package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelAzureGolem;

import net.minecraft.client.model.ModelBase;

public class AnimationAzureGolem extends Animation<ModelAzureGolem>
{
    // Animation frames for the pound attack
    private static float[] armFramesDegrees = { 0, -15, -30, -45, -60, -75, -90, -90, -90, -90, -90, -105, -135, -165, -180, -180, -180, -180, -165, -150, -135, -120,
	    -105, -90, -75, -60, -45 };
    private static float[] backFramesDegrees = { 0, 10, 20, 30, 40, 50, 65, 65, 65, 65, 65, 55, 45, 30, 15, 0, -15, -15, -15, -15, -15, -15, -5, 0, 0, 0, 0 };

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
    }
    
    private float triangleWave(float p_78172_1_, float p_78172_2_)
    {
	return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }
}
