package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelBeast;

public class AnimationBeastSpit extends Animation<ModelBeast>
{
    private static float[] jawAnimation = { 0, 15, 25, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 25, 15, 0 };

    public AnimationBeastSpit()
    {
	super(jawAnimation.length);
    }

    @Override
    public void setModelRotations(ModelBeast model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	model.jaw.rotateAngleX = (float) Math.toRadians(this.getInterpolatedFrame(jawAnimation, partialTicks));
    }
}