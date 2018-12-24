package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityModMobBase;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 7.0.0
 */
public class ModelShade extends ModelBase {
    public ModelRenderer Wisps;
    public ModelRenderer Body;
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;
    public ModelRenderer Head;
    public ModelRenderer RightHand;
    public ModelRenderer LeftHand;
    public ModelRenderer HeadShape;

    public ModelShade() {
        this.textureWidth = 74;
        this.textureHeight = 36;
        this.Wisps = new ModelRenderer(this, 26, 22);
        this.Wisps.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.Wisps.addBox(-3.5F, 0.0F, -2.0F, 7, 8, 4, 0.0F);
        this.LeftArm = new ModelRenderer(this, 0, 22);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(3.5F, 0.0F, 0.0F);
        this.LeftArm.addBox(1.0F, -2.0F, -2.0F, 3, 10, 4, 0.0F);
        this.Head = new ModelRenderer(this, 32, 0);
        this.Head.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.Head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.LeftHand = new ModelRenderer(this, 16, 22);
        this.LeftHand.mirror = true;
        this.LeftHand.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftHand.addBox(1.0F, 8.0F, -1.0F, 2, 4, 2, 0.0F);
        this.HeadShape = new ModelRenderer(this, 50, 17);
        this.HeadShape.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.HeadShape.addBox(-1.0F, -9.0F, -5.0F, 2, 9, 10, 0.0F);
        this.RightHand = new ModelRenderer(this, 16, 22);
        this.RightHand.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightHand.addBox(-3.0F, 8.0F, -1.0F, 2, 4, 2, 0.0F);
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.Body.addBox(-4.5F, 0.0F, -3.0F, 9, 14, 6, 0.0F);
        this.RightArm = new ModelRenderer(this, 0, 22);
        this.RightArm.setRotationPoint(-3.5F, 0.0F, 0.0F);
        this.RightArm.addBox(-4.0F, -2.0F, -2.0F, 3, 10, 4, 0.0F);
        this.LeftArm.addChild(this.LeftHand);
        this.Head.addChild(this.HeadShape);
        this.RightArm.addChild(this.RightHand);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Wisps.render(f5);
        this.LeftArm.render(f5);
        this.Head.render(f5);
        this.Body.render(f5);
        this.RightArm.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        
        boolean armsUp = entityIn instanceof EntityModMobBase && ((EntityModMobBase)entityIn).isSwingingArms();
        float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
        this.RightArm.rotateAngleZ = 0.0F;
        this.LeftArm.rotateAngleZ = 0.0F;
        this.RightArm.rotateAngleY = -(0.1F - f * 0.6F);
        this.LeftArm.rotateAngleY = 0.1F - f * 0.6F;
        float f2 = -(float)Math.PI / (armsUp ? 1.5F : 2.25F);
        this.RightArm.rotateAngleX = f2;
        this.LeftArm.rotateAngleX = f2;
        this.RightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.LeftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.RightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.LeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.RightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.LeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }
}
