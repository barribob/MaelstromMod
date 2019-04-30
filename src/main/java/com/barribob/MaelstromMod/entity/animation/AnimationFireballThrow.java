package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelAnimatedBiped;

/*
 * Throw arms and body pack, then throw the fireball forward
 */
public class AnimationFireballThrow extends Animation<ModelAnimatedBiped>
{
    private static float[] leftArmX =  {000, 000, 000, -30, -60, -90,-120,-150,-180,-180,-180,-180,-150,-120, -90, -60, -30, 000, 030, 015, 000};
    private static float[] leftArmZ =  {000, 000, 000, 000, 010, 020, 030, 030, 030, 030, 030, 030, 020, 010, 000, 000, 000, 000, 000, 000, 000};
    
    private static float[] rightArmX = {000, 000, 000, -30, -60, -90,-120,-150,-180,-180,-180,-180,-150,-120, -90, -60, -30, 000, 030, 015, 000};
    private static float[] rightArmZ = {000, 000, 000, 000, -10, -20, -30, -30, -30, -30, -30, -30, -20, -10, 000, 000, 000, 000, 000, 000, 000};
    
    private static float[] bodyX =     {000, 020, 040, 020, 000, -10, -20, -20, -20, -20, -20, -20, 000, 015, 030, 030, 030, 015, 000, 000, 000};
    private static float[] leftLegZ =  {000, 000, 000, 000, 000, 000, 000, 000, 000, 000, -05, -10, -15, -15, -15, -15, -10, -05, 000, 000, 000};
    private static float[] rightLegZ = {000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 005, 010, 015, 015, 015, 015, 010, 005, 000, 000, 000};
    
    public AnimationFireballThrow()
    {
	super(leftArmX.length);
    }

    @Override
    public void setModelRotations(ModelAnimatedBiped model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(leftArmX, partialTicks));
	model.bipedLeftArm.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(leftArmZ, partialTicks));
	
	model.bipedRightArm.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(rightArmX, partialTicks));
	model.bipedRightArm.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(rightArmZ, partialTicks));
	
	model.bipedBody.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(bodyX, partialTicks));
	
	model.bipedLeftLeg.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(leftLegZ, partialTicks));
	model.bipedRightLeg.rotateAngleZ = (float) Math.toRadians(this.getInterpolatedFrame(rightLegZ, partialTicks));
    }
}
