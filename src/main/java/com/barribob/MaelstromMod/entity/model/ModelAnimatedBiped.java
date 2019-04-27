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
    private static int textureWidth = 64;
    private static int textureHeight = 64;
    public ModelRenderer centerPivot;

    public ModelAnimatedBiped(float modelSize, float f)
    {
	super(modelSize, f, textureWidth, textureHeight);
	this.bipedHead = new ModelRenderer(this, 0, 0);
	this.bipedHead.setRotationPoint(0.0F, -12.0F, 0.0F);
	this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
	this.centerPivot = new ModelRenderer(this, 0, 0);
	this.centerPivot.setRotationPoint(0.0F, 0.0F, 0.0F);
	this.centerPivot.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
	this.bipedRightArm = new ModelRenderer(this, 40, 16);
	this.bipedRightArm.setRotationPoint(-5.0F, -10.0F, 0.0F);
	this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
	this.bipedBody = new ModelRenderer(this, 16, 16);
	this.bipedBody.setRotationPoint(0.0F, 12.0F, 0.0F);
	this.bipedBody.addBox(-4.0F, -12.0F, -2.0F, 8, 12, 4, 0.0F);
	this.bipedLeftArm = new ModelRenderer(this, 32, 48);
	this.bipedLeftArm.setRotationPoint(5.0F, -10.0F, 0.0F);
	this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
	this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
	this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
	this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
	this.bipedRightLeg = new ModelRenderer(this, 0, 16);
	this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
	this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
	this.bipedBody.addChild(this.bipedHead);
	this.bipedBody.addChild(this.bipedRightArm);
	this.centerPivot.addChild(this.bipedBody);
	this.bipedBody.addChild(this.bipedLeftArm);
	this.centerPivot.addChild(this.bipedLeftLeg);
	this.centerPivot.addChild(this.bipedRightLeg);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
	this.centerPivot.render(f5);
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

    public void postRenderArm(float scale, EnumHandSide side)
    {
	// Translate because the postRender does not detect that the arm is a child of
	// centerPivot
	GlStateManager.translate(0.0F, 12 * scale, 0.0F);
	this.getArmForSide(side).postRender(scale);
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
	this.bipedHead.rotationPointY -= 12;
    }
}
