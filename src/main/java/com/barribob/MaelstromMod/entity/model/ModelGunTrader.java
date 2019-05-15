package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityAzureVillager;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * Author: Daniel Yoshimura
 * Created using Tabula 7.0.0
 */
public class ModelGunTrader extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer arm1;
    public ModelRenderer arm2;
    public ModelRenderer arm3;
    public ModelRenderer leftLeg;
    public ModelRenderer body;
    public ModelRenderer cloak;
    public ModelRenderer rightLeg;
    public ModelRenderer sidepack;
    public ModelRenderer strap;
    public ModelRenderer nose;
    public ModelRenderer pipeshaft;
    public ModelRenderer Fedora;
    public ModelRenderer FedorarRim;
    public ModelRenderer pipe;

    public ModelGunTrader() {
        this.textureWidth = 100;
        this.textureHeight = 100;
        this.Fedora = new ModelRenderer(this, 64, 0);
        this.Fedora.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Fedora.addBox(-4.5F, -8.4F, -4.5F, 9, 2, 9, 0.0F);
        this.pipe = new ModelRenderer(this, 56, 4);
        this.pipe.setRotationPoint(-0.5F, -1.1F, -1.6F);
        this.pipe.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(pipe, -0.27314402793711257F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.arm1 = new ModelRenderer(this, 44, 22);
        this.arm1.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.arm1.addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(arm1, -0.7499679795819634F, 0.0F, 0.0F);
        this.strap = new ModelRenderer(this, 30, 41);
        this.strap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strap.addBox(-3.5F, -3.51F, -4.0F, 1, 15, 8, 0.0F);
        this.setRotateAngle(strap, 0.0F, 0.0F, -0.7777187146886733F);
        this.body = new ModelRenderer(this, 16, 20);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.FedorarRim = new ModelRenderer(this, 60, 12);
        this.FedorarRim.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.FedorarRim.addBox(-5.5F, -6.4F, -5.5F, 11, 0, 11, 0.0F);
        this.leftLeg = new ModelRenderer(this, 0, 22);
        this.leftLeg.mirror = true;
        this.leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.rightLeg = new ModelRenderer(this, 0, 22);
        this.rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.nose = new ModelRenderer(this, 24, 0);
        this.nose.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.nose.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
        this.sidepack = new ModelRenderer(this, 34, 0);
        this.sidepack.setRotationPoint(-0.4F, 0.0F, -0.5F);
        this.sidepack.addBox(3.0F, 10.0F, -3.5F, 4, 6, 8, 0.0F);
        this.setRotateAngle(sidepack, 0.0F, 0.0F, -0.136659280431156F);
        this.cloak = new ModelRenderer(this, 0, 38);
        this.cloak.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.cloak.addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, 0.5F);
        this.arm2 = new ModelRenderer(this, 44, 22);
        this.arm2.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.arm2.addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(arm2, -0.7499679795819634F, 0.0F, 0.0F);
        this.arm3 = new ModelRenderer(this, 40, 38);
        this.arm3.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.arm3.addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, 0.0F);
        this.setRotateAngle(arm3, -0.7499679795819634F, 0.0F, 0.0F);
        this.pipeshaft = new ModelRenderer(this, 56, 0);
        this.pipeshaft.setRotationPoint(-3.0F, -1.1F, -6.0F);
        this.pipeshaft.addBox(0.0F, 0.0F, -0.4F, 1, 1, 3, 0.0F);
        this.setRotateAngle(pipeshaft, 0.22759093446006054F, 0.4553564018453205F, 0.091106186954104F);
        this.head.addChild(this.Fedora);
        this.pipeshaft.addChild(this.pipe);
        this.head.addChild(this.FedorarRim);
        this.head.addChild(this.nose);
        this.head.addChild(this.pipeshaft);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.head.render(f5);
        this.arm1.render(f5);
        this.strap.render(f5);
        this.body.render(f5);
        this.leftLeg.render(f5);
        this.rightLeg.render(f5);
        this.sidepack.render(f5);
        this.cloak.render(f5);
        this.arm2.render(f5);
        this.arm3.render(f5);
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
	this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
	this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
	this.leftLeg.rotateAngleY = 0.0F;
	this.rightLeg.rotateAngleY = 0.0F;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
