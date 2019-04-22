package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * Adds built in animation manipulation via the Animation class
 */
public class ModelAnimatedBiped extends ModelBiped
{
    public ModelAnimatedBiped(float modelSize, float f, int textureWidthIn, int textureHeightIn)
    {
	super(modelSize, f, textureWidthIn, textureHeightIn);
    }
    
    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
	if (entity instanceof EntityLeveledMob)
	{
	    ((EntityLeveledMob) entity).getCurrentAnimation().setModelRotations(this, limbSwing, limbSwingAmount, partialTickTime);
	}
	else
	{
	    throw new IllegalArgumentException("The entity class " + entity.getClass().getName() + " was not an instance of EntityLeveledMob");
	}
    }
}
