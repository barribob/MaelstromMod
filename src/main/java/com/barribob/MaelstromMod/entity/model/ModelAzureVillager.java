package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityAzureVillager;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelAzureVillager extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer arms;
    public ModelRenderer leg0;
    public ModelRenderer leg1;
    public ModelRenderer nose;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    
    private static final float scaleFactor = 0;
    private static final float yRotation = 0;
    private static final int textureWidth = 64;
    private static final int textureHeight = 64;

    public ModelAzureVillager()
    {
	this.head = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
	this.head.setRotationPoint(0.0F, 0.0F + yRotation, 0.0F);
	this.head.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, scaleFactor);
	this.nose = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
	this.nose.setRotationPoint(0.0F, yRotation - 2.0F, 0.0F);
	this.nose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, scaleFactor);
	this.head.addChild(this.nose);
	this.body = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
	this.body.setRotationPoint(0.0F, 0.0F + yRotation, 0.0F);
	this.body.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, scaleFactor);
	this.body.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, scaleFactor + 0.5F);
	this.arms = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
	this.arms.setRotationPoint(0.0F, 0.0F + yRotation + 2.0F, 0.0F);
	this.arms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, scaleFactor);
	ModelRenderer modelrenderer = (new ModelRenderer(this, 44, 22)).setTextureSize(textureWidth, textureHeight);
	modelrenderer.mirror = true;
	modelrenderer.addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, scaleFactor);
	this.arms.addChild(modelrenderer);
	this.arms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, scaleFactor);
	this.leg0 = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidth, textureHeight);
	this.leg0.setRotationPoint(-2.0F, 12.0F + yRotation, 0.0F);
	this.leg0.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scaleFactor);
	this.leg1 = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidth, textureHeight);
	this.leg1.mirror = true;
	this.leg1.setRotationPoint(2.0F, 12.0F + yRotation, 0.0F);
	this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scaleFactor);
	this.rightArm = (new ModelRenderer(this, 40, 46)).setTextureSize(textureWidth, textureHeight);
	this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scaleFactor);
	this.rightArm.setRotationPoint(-5.0F, 2.0F + yRotation, 0.0F);
	this.leftArm = (new ModelRenderer(this, 40, 46)).setTextureSize(textureWidth, textureHeight);
	this.leftArm.mirror = true;
	this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, scaleFactor);
	this.leftArm.setRotationPoint(5.0F, 2.0F + yRotation, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
	this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
	this.head.render(scale);
	this.body.render(scale);
	this.leg0.render(scale);
	this.leg1.render(scale);

	if (((EntityAzureVillager) entityIn).isAggressive())
	{
	    this.rightArm.render(scale);
	    this.leftArm.render(scale);
	}
	else
	{
	    this.arms.render(scale);
	}
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used
     * for animating the movement of arms and legs, where par1 represents the
     * time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
	this.head.rotateAngleY = netHeadYaw * 0.017453292F;
	this.head.rotateAngleX = headPitch * 0.017453292F;
	this.arms.rotationPointY = 3.0F;
	this.arms.rotationPointZ = -1.0F;
	this.arms.rotateAngleX = -0.75F;
	this.leg0.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
	this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
	this.leg0.rotateAngleY = 0.0F;
	this.leg1.rotateAngleY = 0.0F;

	/**
	 * Taken from the vindicator class for attack animation
	 */
	if (((EntityAzureVillager) entityIn).isAggressive())
	{
	    float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
	    float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
	    this.rightArm.rotateAngleZ = 0.0F;
	    this.leftArm.rotateAngleZ = 0.0F;
	    this.rightArm.rotateAngleY = 0.15707964F;
	    this.leftArm.rotateAngleY = -0.15707964F;

	    if (((EntityLivingBase) entityIn).getPrimaryHand() == EnumHandSide.RIGHT)
	    {
		this.rightArm.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
		this.leftArm.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.19F) * 0.5F;
		this.rightArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
		this.leftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
	    }
	    else
	    {
		this.rightArm.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.19F) * 0.5F;
		this.leftArm.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
		this.rightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
		this.leftArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
	    }

	    this.rightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
	    this.leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
	    this.rightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
	    this.leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
	}
    }
    
    public ModelRenderer getArm(EnumHandSide p_191216_1_)
    {
        return p_191216_1_ == EnumHandSide.LEFT ? this.leftArm : this.rightArm;
    }
}