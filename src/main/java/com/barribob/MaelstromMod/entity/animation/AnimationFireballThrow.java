package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelAnimatedBiped;

public class AnimationFireballThrow extends Animation<ModelAnimatedBiped>
{
    private static float[] leftArm = {};
    private static float[] rightArm = {};
    
    public AnimationFireballThrow(int animationLength)
    {
	super(animationLength);
    }

    @Override
    public void setModelRotations(ModelAnimatedBiped model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
    }
}
