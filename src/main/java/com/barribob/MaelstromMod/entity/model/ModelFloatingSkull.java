package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * FloatingSkull - by Daniel Yoshimura Created using Tabula 7.0.0
 */
public class ModelFloatingSkull extends ModelAnimated {
    public ModelRenderer headBase;
    public ModelRenderer jaw;
    public ModelRenderer upperJaw;
    public ModelRenderer nose;
    public ModelRenderer nose2;
    public ModelRenderer nose3;
    public ModelRenderer face;
    public ModelRenderer cheeks;
    public ModelRenderer skull1;
    public ModelRenderer skull2;
    public ModelRenderer skull3;
    public ModelRenderer skull4;
    public ModelRenderer skull5;
    public ModelRenderer skull6;
    public ModelRenderer skull7;
    public ModelRenderer skull8;
    public ModelRenderer skull9;
    public ModelRenderer skull10;
    public ModelRenderer Skull11;
    public ModelRenderer skull12;
    public ModelRenderer jaw1;
    public ModelRenderer jaw3;
    public ModelRenderer jaw2;
    public ModelRenderer jaw4;

    public ModelFloatingSkull() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.skull3 = new ModelRenderer(this, 28, 12);
        this.skull3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.skull3.addBox(-2.5F, -8.0F, 2.5F, 5, 1, 1, 0.0F);
        this.skull8 = new ModelRenderer(this, 0, 20);
        this.skull8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.skull8.addBox(-4.5F, -1.0F, -3.5F, 1, 1, 6, 0.0F);
        this.skull4 = new ModelRenderer(this, 40, 12);
        this.skull4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.skull4.addBox(-2.5F, -7.0F, 3.5F, 5, 1, 1, 0.0F);
        this.skull9 = new ModelRenderer(this, 44, 17);
        this.skull9.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.skull9.addBox(-4.5F, -7.0F, -2.0F, 1, 1, 4, 0.0F);
        this.headBase = new ModelRenderer(this, 0, 6);
        this.headBase.setRotationPoint(0.0F, 7.0F, 0.5F);
        this.headBase.addBox(-3.5F, -7.0F, -3.5F, 7, 7, 7, 0.0F);
        this.jaw4 = new ModelRenderer(this, 0, 6);
        this.jaw4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jaw4.addBox(2.5F, 1.0F, -2.5F, 1, 1, 2, 0.0F);
        this.cheeks = new ModelRenderer(this, 28, 14);
        this.cheeks.setRotationPoint(-4.5F, -3.0F, -4.5F);
        this.cheeks.addBox(0.0F, 0.0F, 0.0F, 9, 3, 1, 0.0F);
        this.skull1 = new ModelRenderer(this, 23, 5);
        this.skull1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.skull1.addBox(-2.5F, -8.0F, -3.5F, 5, 1, 1, 0.0F);
        this.skull5 = new ModelRenderer(this, 37, 0);
        this.skull5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.skull5.addBox(-3.5F, -6.0F, 3.5F, 7, 5, 1, 0.0F);
        this.skull12 = new ModelRenderer(this, 46, 0);
        this.skull12.setRotationPoint(3.5F, -6.0F, -3.5F);
        this.skull12.addBox(0.0F, 0.0F, 0.0F, 1, 5, 7, 0.0F);
        this.Skull11 = new ModelRenderer(this, 48, 19);
        this.Skull11.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Skull11.addBox(3.5F, -1.0F, -3.5F, 1, 1, 6, 0.0F);
        this.jaw3 = new ModelRenderer(this, 37, 21);
        this.jaw3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jaw3.addBox(2.5F, 2.0F, -4.5F, 1, 2, 4, 0.0F);
        this.nose2 = new ModelRenderer(this, 55, 0);
        this.nose2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.nose2.addBox(-1.5F, -3.0F, -5.5F, 3, 1, 1, 0.0F);
        this.skull10 = new ModelRenderer(this, 44, 17);
        this.skull10.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.skull10.addBox(3.5F, -7.0F, -2.0F, 1, 1, 4, 0.0F);
        this.jaw = new ModelRenderer(this, 19, 21);
        this.jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jaw.addBox(-2.5F, 2.0F, -5.5F, 5, 2, 1, 0.0F);
        this.skull2 = new ModelRenderer(this, 0, 0);
        this.skull2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.skull2.addBox(-3.5F, -8.0F, -2.5F, 7, 1, 5, 0.0F);
        this.face = new ModelRenderer(this, 19, 0);
        this.face.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.face.addBox(-3.5F, -7.0F, -4.5F, 7, 4, 1, 0.0F);
        this.skull6 = new ModelRenderer(this, 8, 20);
        this.skull6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.skull6.addBox(-2.5F, -1.0F, 3.5F, 5, 1, 1, 0.0F);
        this.skull7 = new ModelRenderer(this, 28, 0);
        this.skull7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.skull7.addBox(-4.5F, -6.0F, -3.5F, 1, 5, 7, 0.0F);
        this.nose3 = new ModelRenderer(this, 0, 0);
        this.nose3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.nose3.addBox(-0.5F, -4.0F, -5.5F, 1, 1, 1, 0.0F);
        this.nose = new ModelRenderer(this, 28, 18);
        this.nose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.nose.addBox(-3.5F, -2.0F, -5.5F, 7, 2, 1, 0.0F);
        this.upperJaw = new ModelRenderer(this, 28, 16);
        this.upperJaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.upperJaw.addBox(-2.5F, 0.0F, -5.5F, 5, 1, 1, 0.0F);
        this.jaw2 = new ModelRenderer(this, 55, 2);
        this.jaw2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jaw2.addBox(-3.5F, 1.0F, -2.4F, 1, 1, 2, 0.0F);
        this.jaw1 = new ModelRenderer(this, 27, 21);
        this.jaw1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jaw1.addBox(-3.5F, 2.0F, -4.5F, 1, 2, 4, 0.0F);
        this.headBase.addChild(this.skull3);
        this.headBase.addChild(this.skull8);
        this.headBase.addChild(this.skull4);
        this.headBase.addChild(this.skull9);
        this.jaw.addChild(this.jaw4);
        this.headBase.addChild(this.cheeks);
        this.headBase.addChild(this.skull1);
        this.headBase.addChild(this.skull5);
        this.headBase.addChild(this.skull12);
        this.headBase.addChild(this.Skull11);
        this.jaw.addChild(this.jaw3);
        this.headBase.addChild(this.nose2);
        this.headBase.addChild(this.skull10);
        this.headBase.addChild(this.jaw);
        this.headBase.addChild(this.skull2);
        this.headBase.addChild(this.face);
        this.headBase.addChild(this.skull6);
        this.headBase.addChild(this.skull7);
        this.headBase.addChild(this.nose3);
        this.headBase.addChild(this.nose);
        this.headBase.addChild(this.upperJaw);
        this.jaw.addChild(this.jaw2);
        this.jaw.addChild(this.jaw1);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.headBase.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

        // The up and down bobbing motion of the skull
        this.headBase.offsetY = (float) Math.cos(Math.toRadians(ageInTicks * 10)) * 0.2f;

        this.headBase.rotateAngleY = netHeadYaw * 0.017453292F;
        this.headBase.rotateAngleX = headPitch * 0.017453292F;
    }
}
