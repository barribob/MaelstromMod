package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelMaelstromIllager - Barribob
 * Created using Tabula 7.0.0
 */
public class ModelMaelstromIllager extends ModelAnimated
{
    public ModelRenderer head;
    public ModelRenderer bipedRightArm;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer body;
    public ModelRenderer mainBody;
    public ModelRenderer arms;
    public ModelRenderer villagerWrist;
    public ModelRenderer nose;
    public ModelRenderer hatBrim;
    public ModelRenderer hatTop;
    public ModelRenderer villagerLeftArm;

    public ModelMaelstromIllager() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.villagerWrist = new ModelRenderer(this, 40, 38);
        this.villagerWrist.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.villagerWrist.addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, 0.0F);
        this.setRotateAngle(villagerWrist, -0.7499679795819634F, 0.0F, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 40, 46);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.hatBrim = new ModelRenderer(this, 64, 0);
        this.hatBrim.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hatBrim.addBox(-6.0F, -10.0F, -6.0F, 12, 1, 12, 0.0F);
        this.body = new ModelRenderer(this, 16, 20);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.villagerLeftArm = new ModelRenderer(this, 44, 22);
        this.villagerLeftArm.mirror = true;
        this.villagerLeftArm.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.villagerLeftArm.addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.head = new ModelRenderer(this, 0, 1);
        this.head.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.head.addBox(-4.0F, -10.0F, -4.0F, 8, 9, 8, 0.0F);
        this.leftLeg = new ModelRenderer(this, 0, 22);
        this.leftLeg.mirror = true;
        this.leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.arms = new ModelRenderer(this, 44, 22);
        this.arms.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.arms.addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(arms, -0.7499679795819634F, 0.0F, 0.0F);
        this.hatTop = new ModelRenderer(this, 64, 22);
        this.hatTop.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hatTop.addBox(-4.0F, -18.0F, -4.0F, 8, 8, 8, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 40, 46);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.nose = new ModelRenderer(this, 24, 0);
        this.nose.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.nose.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
        this.mainBody = new ModelRenderer(this, 0, 38);
        this.mainBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.mainBody.addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, 0.5F);
        this.rightLeg = new ModelRenderer(this, 0, 22);
        this.rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.head.addChild(this.hatBrim);
        this.arms.addChild(this.villagerLeftArm);
        this.head.addChild(this.hatTop);
        this.head.addChild(this.nose);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
        this.head.render(f5);
        this.leftLeg.render(f5);
        this.mainBody.render(f5);
        this.rightLeg.render(f5);
                
        // Only render the standard villager wrists when not swinging (summoning)
        if (((EntityMaelstromMob)entity).isSwingingArms())
        {
            this.bipedLeftArm.render(f5);
            this.bipedRightArm.render(f5);
        }
        else
        {
            this.arms.render(f5);
            this.villagerWrist.render(f5);
        }
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.arms.rotationPointY = 3.0F;
        this.arms.rotationPointZ = -1.0F;
        this.arms.rotateAngleX = -0.75F;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.leftLeg.rotateAngleY = 0.0F;
        this.leftLeg.rotateAngleY = 0.0F;
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
