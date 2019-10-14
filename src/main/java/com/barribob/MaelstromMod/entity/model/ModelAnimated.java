package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.util.IAnimatedMob;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;

public abstract class ModelAnimated extends ModelBase
{
    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
	if (entity instanceof IAnimatedMob)
	{
	    ((IAnimatedMob) entity).getCurrentAnimation().setModelRotations(this, limbSwing, limbSwingAmount, partialTickTime);
	}
	else
	{
	    throw new IllegalArgumentException("The entity class " + entity.getClass().getName() + " was not an instance of EntityLeveledMob");
	}
    }
}
