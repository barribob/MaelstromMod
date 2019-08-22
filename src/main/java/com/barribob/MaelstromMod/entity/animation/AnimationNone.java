package com.barribob.MaelstromMod.entity.animation;

import net.minecraft.client.model.ModelBase;

public class AnimationNone extends ArrayAnimation
{
    public AnimationNone()
    {
	super(0);
    }

    @Override
    public void setModelRotations(ModelBase model, float limbSwing, float limbSwingAmount, float partialTicks)
    {	
    }
}