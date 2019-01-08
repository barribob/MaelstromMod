package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * 
 * Updates attack animation to be the waving arms seen in the Evoker illager
 *
 */
public class ModelMaelstromMage extends ModelShade
{
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        boolean armsUp = entityIn instanceof EntityMaelstromMob && ((EntityMaelstromMob)entityIn).isSwingingArms();

        if (armsUp)
        {
            this.RightArm.rotationPointZ = 0.0F;
            this.RightArm.rotationPointX = -5.0F;
            this.LeftArm.rotationPointZ = 0.0F;
            this.LeftArm.rotationPointX = 5.0F;
            this.RightArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
            this.LeftArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
            this.RightArm.rotateAngleZ = 2.3561945F;
            this.LeftArm.rotateAngleZ = -2.3561945F;
            this.RightArm.rotateAngleY = 0.0F;
            this.LeftArm.rotateAngleY = 0.0F;
        }
    }
}
