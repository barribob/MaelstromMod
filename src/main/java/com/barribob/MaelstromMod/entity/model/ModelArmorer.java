package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 7.0.0
 */
public class ModelArmorer extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer leftLeg;
    public ModelRenderer body;
    public ModelRenderer cloak;
    public ModelRenderer rightLeg;
    public ModelRenderer cloak_1;
    public ModelRenderer nose;
    public ModelRenderer leftArm;
    public ModelRenderer rightArm;
    public ModelRenderer leftArmor;
    public ModelRenderer rightArmor;

    public ModelArmorer() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.nose = new ModelRenderer(this, 0, 0);
        this.nose.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.nose.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
        this.leftArm = new ModelRenderer(this, 104, 12);
        this.leftArm.setRotationPoint(4.0F, 3.0F, -1.0F);
        this.leftArm.addBox(0.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(leftArm, 0.4363323129985824F, 0.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 104, 0);
        this.rightLeg.setRotationPoint(-2.0F, 20.0F, -10.0F);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F);
        this.rightArmor = new ModelRenderer(this, 67, 18);
        this.rightArmor.setRotationPoint(-0.5F, 0.0F, 0.0F);
        this.rightArmor.addBox(-4.5F, -2.5F, -2.5F, 5, 6, 5, 0.0F);
        this.cloak = new ModelRenderer(this, 76, 0);
        this.cloak.setRotationPoint(0.0F, 11.5F, -2.0F);
        this.cloak.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.5F);
        this.cloak_1 = new ModelRenderer(this, 28, 14);
        this.cloak_1.setRotationPoint(0.0F, 0.0F, -9.0F);
        this.cloak_1.addBox(-4.0F, 19.5F, -1.0F, 8, 4, 4, 0.5F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 12.0F, -2.0F);
        this.head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.body = new ModelRenderer(this, 48, 0);
        this.body.setRotationPoint(0.0F, 12.0F, -2.0F);
        this.body.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.rightArm = new ModelRenderer(this, 0, 18);
        this.rightArm.setRotationPoint(-4.0F, 3.0F, -1.0F);
        this.rightArm.addBox(-4.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(rightArm, 0.4363323129985824F, 0.0F, 0.0F);
        this.leftArmor = new ModelRenderer(this, 47, 18);
        this.leftArmor.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftArmor.addBox(0.0F, -2.5F, -2.5F, 5, 6, 5, 0.0F);
        this.leftLeg = new ModelRenderer(this, 32, 0);
        this.leftLeg.mirror = true;
        this.leftLeg.setRotationPoint(2.0F, 20.0F, -10.0F);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F);
        this.head.addChild(this.nose);
        this.body.addChild(this.leftArm);
        this.rightArm.addChild(this.rightArmor);
        this.body.addChild(this.rightArm);
        this.leftArm.addChild(this.leftArmor);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.rightLeg.render(f5);
        this.cloak.render(f5);
        this.cloak_1.render(f5);
        this.head.render(f5);
        this.body.render(f5);
        this.leftLeg.render(f5);
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.leftLeg.rotateAngleZ = 0.0F;
        this.leftLeg.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.leftLeg.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.rightLeg.rotateAngleZ = 0.0F;
        this.rightLeg.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.rightLeg.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
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
