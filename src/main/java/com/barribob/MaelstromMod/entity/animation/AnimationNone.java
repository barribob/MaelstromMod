package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelAnimated;

public class AnimationNone extends Animation
{
    public AnimationNone()
    {
	super(0);
    }

    @Override
    public void setModelRotations(ModelAnimated model, float limbSwing, float limbSwingAmount, float partialTicks)
    {	
    }
}