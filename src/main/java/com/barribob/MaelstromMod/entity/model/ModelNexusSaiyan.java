package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created using Tabula 7.0.0
 */
public class ModelNexusSaiyan extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer cloak;
    public ModelRenderer arm3;
    public ModelRenderer arm2;
    public ModelRenderer arm1;
    public ModelRenderer Cape;
    public ModelRenderer Cape1;
    public ModelRenderer Cape2;
    public ModelRenderer Cape3;
    public ModelRenderer Cape4;
    public ModelRenderer Cape5;
    public ModelRenderer Cape6;
    public ModelRenderer Cape7;
    public ModelRenderer Cape8;
    public ModelRenderer Cape9;
    public ModelRenderer Cape10;
    public ModelRenderer Cape11;
    public ModelRenderer head;
    public ModelRenderer nose;
    public ModelRenderer hair0;
    public ModelRenderer hair0_1;
    public ModelRenderer hair0_2;

    public ModelNexusSaiyan() {
        this.textureWidth = 100;
        this.textureHeight = 100;
        this.Cape5 = new ModelRenderer(this, 85, 0);
        this.Cape5.setRotationPoint(0.5F, 0.0F, 3.0F);
        this.Cape5.addBox(0.0F, 23.0F, 0.0F, 3, 1, 1, 0.0F);
        this.setRotateAngle(Cape5, 0.04241150082346221F, 0.0F, 0.0F);
        this.arm2 = new ModelRenderer(this, 0, 18);
        this.arm2.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.arm2.addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(arm2, -0.7499679795819634F, 0.0F, 0.0F);
        this.Cape3 = new ModelRenderer(this, 40, 0);
        this.Cape3.setRotationPoint(-0.5F, 0.0F, 3.0F);
        this.Cape3.addBox(-3.0F, 22.0F, 0.0F, 2, 2, 1, 0.0F);
        this.setRotateAngle(Cape3, 0.029845130209103034F, 0.0F, 0.0F);
        this.Cape = new ModelRenderer(this, 16, 24);
        this.Cape.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Cape.addBox(-4.0F, 0.0F, 0.0F, 8, 12, 1, 0.0F);
        this.setRotateAngle(Cape, 0.10349802464326374F, 0.0F, 0.0F);
        this.Cape10 = new ModelRenderer(this, 12, 18);
        this.Cape10.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Cape10.addBox(-4.0F, 15.0F, 0.0F, 7, 1, 1, 0.0F);
        this.setRotateAngle(Cape10, 0.07871434926494425F, 0.0F, 0.0F);
        this.arm3 = new ModelRenderer(this, 24, 16);
        this.arm3.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.arm3.addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, 0.0F);
        this.setRotateAngle(arm3, -0.7499679795819634F, 0.0F, 0.0F);
        this.Cape2 = new ModelRenderer(this, 0, 0);
        this.Cape2.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Cape2.addBox(-3.0F, 24.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(Cape2, 0.01727875959474386F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 52, 31);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.cloak = new ModelRenderer(this, 60, 0);
        this.cloak.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.cloak.addBox(-4.5F, 0.0F, -3.5F, 9, 18, 7, 0.0F);
        this.rightLeg = new ModelRenderer(this, 28, 0);
        this.rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Cape11 = new ModelRenderer(this, 60, 25);
        this.Cape11.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Cape11.addBox(-4.0F, 16.0F, 0.0F, 8, 5, 1, 0.0F);
        this.setRotateAngle(Cape11, 0.06579891280018622F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 90, 23);
        this.nose.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.nose.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
        this.hair0_2 = new ModelRenderer(this, 32, 43);
        this.hair0_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hair0_2.addBox(-2.0F, -20.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Cape6 = new ModelRenderer(this, 93, 0);
        this.Cape6.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Cape6.addBox(1.0F, 24.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(Cape6, 0.03612831551628262F, 0.0F, 0.0F);
        this.Cape9 = new ModelRenderer(this, 85, 2);
        this.Cape9.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Cape9.addBox(-4.0F, 13.0F, 0.0F, 6, 2, 1, 0.0F);
        this.setRotateAngle(Cape9, 0.0853466004225227F, 0.0F, 0.0F);
        this.hair0_1 = new ModelRenderer(this, 28, 32);
        this.hair0_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hair0_1.addBox(-3.0F, -17.2F, -3.0F, 6, 5, 6, 0.0F);
        this.leftLeg = new ModelRenderer(this, 44, 0);
        this.leftLeg.mirror = true;
        this.leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Cape7 = new ModelRenderer(this, 0, 2);
        this.Cape7.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Cape7.addBox(1.5F, 25.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Cape7, 0.02356194490192345F, 0.0F, 0.0F);
        this.arm1 = new ModelRenderer(this, 44, 20);
        this.arm1.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.arm1.addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(arm1, -0.7499679795819634F, 0.0F, 0.0F);
        this.Cape8 = new ModelRenderer(this, 44, 16);
        this.Cape8.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Cape8.addBox(-4.0F, 12.0F, 0.0F, 7, 1, 1, 0.0F);
        this.setRotateAngle(Cape8, 0.09756390518648302F, 0.0F, 0.0F);
        this.Cape1 = new ModelRenderer(this, 22, 0);
        this.Cape1.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Cape1.addBox(-4.0F, 21.0F, 0.0F, 3, 1, 1, 0.0F);
        this.setRotateAngle(Cape1, 0.04869468613064179F, 0.0F, 0.0F);
        this.hair0 = new ModelRenderer(this, 0, 37);
        this.hair0.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hair0.addBox(-4.0F, -15.0F, -4.0F, 8, 5, 8, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, -4.9F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.Cape4 = new ModelRenderer(this, 56, 0);
        this.Cape4.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Cape4.addBox(0.0F, 21.0F, 0.0F, 4, 2, 1, 0.0F);
        this.setRotateAngle(Cape4, 0.04869468613064179F, 0.0F, 0.0F);
        this.body.addChild(this.Cape5);
        this.body.addChild(this.arm2);
        this.body.addChild(this.Cape3);
        this.body.addChild(this.Cape);
        this.body.addChild(this.Cape10);
        this.body.addChild(this.arm3);
        this.body.addChild(this.Cape2);
        this.body.addChild(this.head);
        this.body.addChild(this.cloak);
        this.body.addChild(this.rightLeg);
        this.body.addChild(this.Cape11);
        this.head.addChild(this.nose);
        this.head.addChild(this.hair0_2);
        this.body.addChild(this.Cape6);
        this.body.addChild(this.Cape9);
        this.head.addChild(this.hair0_1);
        this.body.addChild(this.leftLeg);
        this.body.addChild(this.Cape7);
        this.body.addChild(this.arm1);
        this.body.addChild(this.Cape8);
        this.body.addChild(this.Cape1);
        this.head.addChild(this.hair0);
        this.body.addChild(this.Cape4);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.render(f5);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used
     * for animating the movement of arms and legs, where par1 represents the
     * time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        this.body.offsetY = (float) Math.cos(Math.toRadians(ageInTicks * 2)) * 0.2f - 0.5f;
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
