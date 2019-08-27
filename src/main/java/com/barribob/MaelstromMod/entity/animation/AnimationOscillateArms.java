package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromIllager;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromIllager;

import net.minecraft.util.math.MathHelper;

public class AnimationOscillateArms extends ArrayAnimation<ModelMaelstromIllager>
{
    EntityMaelstromIllager entity;

    public AnimationOscillateArms(int animationLength, EntityMaelstromIllager entity)
    {
	super(animationLength);
	this.entity = entity;
    }

    @Override
    public void setModelRotations(ModelMaelstromIllager model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	model.bipedRightArm.rotationPointZ = 0.0F;
	model.bipedRightArm.rotationPointX = -5.0F;
	model.bipedLeftArm.rotationPointZ = 0.0F;
	model.bipedLeftArm.rotationPointX = 5.0F;
	model.bipedRightArm.rotateAngleX = MathHelper.cos(entity.ticksExisted * 0.6662F) * 0.25F;
	model.bipedLeftArm.rotateAngleX = MathHelper.cos(entity.ticksExisted * 0.6662F) * 0.25F;
	model.bipedRightArm.rotateAngleZ = 2.3561945F;
	model.bipedLeftArm.rotateAngleZ = -2.3561945F;
	model.bipedRightArm.rotateAngleY = 0.0F;
	model.bipedLeftArm.rotateAngleY = 0.0F;
    }
}
