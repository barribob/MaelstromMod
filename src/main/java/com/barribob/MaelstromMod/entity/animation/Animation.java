package com.barribob.MaelstromMod.entity.animation;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * In charge of a single animation for an entity
 */
@SideOnly(Side.CLIENT)
public abstract class Animation<T extends ModelBase>
{
    private int attackTimer;
    private int animationLength;

    public Animation(int animationLength)
    {
	this.animationLength = animationLength;
	this.attackTimer = animationLength - 1;
    }

    /*
     * Reset the animation back to the beginning of its frames
     */
    public void startAnimation()
    {
	this.attackTimer = 0;
    }

    /*
     * Called by the entity's model to get the rotations it needs
     */
    public abstract void setModelRotations(T model, float limbSwing, float limbSwingAmount, float partialTicks);

    /*
     * Called by the entity to move the animation forward
     */
    public void update()
    {
	if (this.attackTimer < this.animationLength - 1)
	{
	    this.attackTimer++;
	}
    }
    
    protected float getFrame(float[] animation)
    {
	if(animation.length != this.animationLength)
	{
	    throw new IllegalArgumentException("Animation is not the right length.");
	}
	return animation[this.attackTimer];
    }
    
    /**
     * Get the next frame, return the same frame as the last if already at the last frame
     */
    protected float getNextFrame(float[] animation)
    {
	if(animation.length != this.animationLength)
	{
	    throw new IllegalArgumentException("Animation is not the right length.");
	}
	int frame = Math.min(this.attackTimer + 1, this.animationLength - 1);
	return animation[frame];
    }
    
    /**
     * Get the frame in between the current and next frame using the partial ticks
     * to make the animation smooth
     */
    protected float getInterpolatedFrame(float[] animation, float partialTicks)
    {
	float currentFrame = this.getFrame(animation);
	float nextFrame = this.getNextFrame(animation);
	float difference = nextFrame - currentFrame;
	return currentFrame + (difference * partialTicks);
    }
}
