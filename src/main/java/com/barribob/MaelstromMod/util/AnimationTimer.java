package com.barribob.MaelstromMod.util;

/**
 * 
 * Basically keeps track of the counter, and makes sure animation frames are the correct length
 *
 */
public class AnimationTimer
{
    private int attackTimer;
    private int animationLength;

    public AnimationTimer(int animationLength)
    {
	this.animationLength = animationLength;
    }

    public void nextFrame()
    {
	if (this.attackTimer > 0)
	{
	    this.attackTimer--;
	}
    }

    public int getFrameNumber()
    {
	return this.attackTimer;
    }

    public float getFrame(float[] animation)
    {
	if(animation.length != this.animationLength)
	{
	    throw new IllegalArgumentException("Animation is not the right length.");
	}
	return animation[this.attackTimer];
    }

    public void startAnimation()
    {
	this.attackTimer = animationLength - 1;
    }
}
