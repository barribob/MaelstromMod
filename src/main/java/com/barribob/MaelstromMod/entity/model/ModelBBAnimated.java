package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.animation.AnimationManager;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;

public class ModelBBAnimated extends ModelBase
{
    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
	AnimationManager.setModelRotations(this, entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
    }
}
