package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelSwampCrawler;

public class AnimationOpenJaws extends Animation<ModelSwampCrawler>
{
    private static float[] rightJawAnimation = { 0, 10, 15, 20, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 20, 15, 10, 0 };
    private static float[] leftJawAnimation =  { 0,-10,-15,-20,-25,-25,-25,-25,-25,-25,-25,-25,-25,-25,-25,-25,-25,-25,-25,-25,-25,-25, 20,-15,-10, 0 };

    public AnimationOpenJaws()
    {
	super(rightJawAnimation.length);
    }

    @Override
    public void setModelRotations(ModelSwampCrawler model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	model.rightSideJaw.rotateAngleY = (float) Math.toRadians(this.getInterpolatedFrame(rightJawAnimation, partialTicks));
	model.leftSideJaw.rotateAngleY = (float) Math.toRadians(this.getInterpolatedFrame(leftJawAnimation, partialTicks));
    }
}