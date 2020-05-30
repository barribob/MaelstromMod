package com.barribob.MaelstromMod.entity.animation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.model.ModelBase;

/**
 * Allows for multiple streams of animations to proceed, using lists of
 * animation clips
 */
public class StreamAnimation<T extends ModelBase> implements Animation<T>
{
    // First dimension represents the animation stream, and the second represents
    // the order of the animations
    private static final Map<String, List<List<AnimationClip>>> animationsCache = new HashMap<String, List<List<AnimationClip>>>();
    private final List<List<AnimationClip<T>>> animations;
    private int[] activeAnimations;
    private boolean loop = false;

    public StreamAnimation<T> loop(boolean loop)
    {
	this.loop = loop;
	return this;
    }

    public StreamAnimation(List<List<AnimationClip<T>>> animations)
    {
	this.animations = animations;
	activeAnimations = new int[animations.size()];
	for (int stream = 0; stream < animations.size(); stream++)
	{
	    activeAnimations[stream] = animations.get(stream).size() - 1;
	}
    }

    public static void initStaticAnimations(String csv)
    {

    }

    @Override
    public void startAnimation()
    {
	this.activeAnimations = new int[animations.size()];
	for (List<AnimationClip<T>> animationClips : animations)
	{
	    for (AnimationClip<T> clip : animationClips)
	    {
		clip.startAnimation();
	    }
	}
    }

    @Override
    public void update()
    {
	for (int stream = 0; stream < animations.size(); stream++)
	{
	    if (animations.get(stream).size() > 0)
	    {
		AnimationClip<T> currentClip = animations.get(stream).get(activeAnimations[stream]);
		currentClip.update();

		// Move on to the next clip if there is one
		if (currentClip.isEnded())
		{
		    if (activeAnimations[stream] < animations.get(stream).size() - 1)
		    {
			activeAnimations[stream]++;
		    }
		    else if (loop)
		    {
			startAnimation();
		    }
		}
	    }
	}
    }

    @Override
    public void setModelRotations(T model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	for (int stream = 0; stream < animations.size(); stream++)
	{
	    if (animations.get(stream).size() > 0)
	    {
		animations.get(stream).get(activeAnimations[stream]).setModelRotations(model, limbSwing, limbSwingAmount, partialTicks);
	    }
	}
    }

    public static class AnimationData
    {
	public String[] movers;
	public List<int[]> animations;
	public int numStreams;

	public AnimationData()
	{
	}

	public AnimationData(String[] movers, List<int[]> animations, int numStreams)
	{
	    this.movers = movers;
	    this.animations = animations;
	    this.numStreams = numStreams;
	}
    }
}
