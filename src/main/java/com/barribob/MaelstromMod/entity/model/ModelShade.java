package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 7.0.0
 */
public class ModelShade extends ModelBase {
    public ModelRenderer right_arm;
    public ModelRenderer right_leg;
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer left_arm;
    public ModelRenderer left_leg;

    public ModelShade() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.right_arm = new ModelRenderer(this, 40, 16);
        this.right_arm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.right_arm.addBox(-3.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F);
        this.left_leg = new ModelRenderer(this, 16, 48);
        this.left_leg.setRotationPoint(1.9F, 13.0F, 0.0F);
        this.left_leg.addBox(-1.0F, 0.0F, -2.0F, 3, 11, 4, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -7.0F, -4.0F, 8, 6, 8, 0.0F);
        this.left_arm = new ModelRenderer(this, 32, 48);
        this.left_arm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.left_arm.addBox(0.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F);
        this.body = new ModelRenderer(this, 16, 16);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.right_leg = new ModelRenderer(this, 0, 16);
        this.right_leg.setRotationPoint(-1.9F, 13.0F, 0.0F);
        this.right_leg.addBox(-2.0F, 0.0F, -2.0F, 3, 11, 4, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.right_arm.render(f5);
        this.left_leg.render(f5);
        this.head.render(f5);
        this.left_arm.render(f5);
        this.body.render(f5);
        this.right_leg.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    /**
     *
     * Set the rotation angles to mimic the zombie for the arms, and the biped for the legs
     *
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        this.body.rotateAngleY = 0.0F;

        this.right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.left_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.right_leg.rotateAngleY = 0.0F;
        this.left_leg.rotateAngleY = 0.0F;
        this.right_leg.rotateAngleZ = 0.0F;
        this.left_leg.rotateAngleZ = 0.0F;

        this.right_arm.rotateAngleY = 0.0F;
        this.right_arm.rotateAngleZ = 0.0F;

        this.left_arm.rotateAngleY = 0.0F;
        this.right_arm.rotateAngleY = 0.0F;


        this.body.rotateAngleX = 0.0F;
        this.right_leg.rotationPointZ = 0.1F;
        this.left_leg.rotationPointZ = 0.1F;
        this.right_leg.rotationPointY = 12.0F;
        this.left_leg.rotationPointY = 12.0F;
        this.head.rotationPointY = 0.0F;
        
        boolean flag = entityIn instanceof EntityZombie && ((EntityZombie)entityIn).isArmsRaised();
        float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
        this.right_arm.rotateAngleZ = 0.0F;
        this.left_arm.rotateAngleZ = 0.0F;
        this.right_arm.rotateAngleY = -(0.1F - f * 0.6F);
        this.left_arm.rotateAngleY = 0.1F - f * 0.6F;
        float f2 = -(float)Math.PI / (flag ? 1.5F : 2.25F);
        this.right_arm.rotateAngleX = f2;
        this.left_arm.rotateAngleX = f2;
        this.right_arm.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.left_arm.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.right_arm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.left_arm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.right_arm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.left_arm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }
}
