package com.barribob.MaelstromMod.entity.animation;

import java.util.function.BiConsumer;

import net.minecraft.client.model.ModelBase;

public class AnimationClip<T extends ModelBase> extends ArrayAnimation<T>
{
    private float startValue;
    private float endValue;
    private BiConsumer<T, Float> setModelRotationsImpl;
    boolean isEnded = false;

    public AnimationClip(int animationLength, float startValue, float endValue, BiConsumer<T, Float> setModelRotationsImpl)
    {
	super(animationLength);
	this.startValue = startValue;
	this.endValue = endValue;
	this.setModelRotationsImpl = setModelRotationsImpl;
    }

    @Override
    public void startAnimation()
    {
	this.attackTimer = 0;
	isEnded = false;
    }

    @Override
    public void update()
    {
	super.update();
	if (this.animationLength - 1 == this.attackTimer)
	{
	    isEnded = true;
	}
    }

    @Override
    public final void setModelRotations(T model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	float progress = 0;
	if (this.animationLength > 1)
	{
	    progress = Math.max(0, Math.min(1, (this.attackTimer + partialTicks) / (this.animationLength - 1)));
	}
	float degrees = ((endValue - startValue) * progress) + startValue;
	setModelRotationsImpl.accept(model, (float) Math.toRadians(degrees));
    }

    public boolean isEnded()
    {
	return isEnded;
    }
}
