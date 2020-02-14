package com.barribob.MaelstromMod.entity.animation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import com.barribob.MaelstromMod.init.ModAnimations;

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
    private boolean loop = false;;

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

    /**
     * Constructs a stream animation from a csv file
     * 
     * @param csv
     * @param modelMovers
     */
    public StreamAnimation(int id)
    {
	AnimationData data = ModAnimations.getAnimationByIdUncached(id);
	List<List<AnimationClip<T>>> animation = new ArrayList<List<AnimationClip<T>>>();

	int[] previousAngles = new int[data.numStreams]; // Stores the previous angles
	int[] previousTicks = new int[data.numStreams]; // Stores the previous ticks that the previous angle was at
	int ticks = 0; // Initialize ticks to be 0
	List<BiConsumer<T, Float>> modelMovers = new ArrayList<BiConsumer<T, Float>>();
	for (int stream = 0; stream < data.numStreams; stream++)
	{
	    // Build the model movers specified by the first line in the animation file
	    final int streamFinal = stream;
	    animation.add(new ArrayList<AnimationClip<T>>());
	    modelMovers.add((model, f) -> {
		String[] fields = data.movers[streamFinal].split(" ");
		Field modelBox;
		try
		{
		    modelBox = model.getClass().getField(fields[0]);
		    Field angle = modelBox.get(model).getClass().getField("rotateAngle" + fields[1]);
		    angle.set(modelBox.get(model), fields[1].startsWith("Z") ? f : -f); // Negative because blockbench makes x and y angle negative for some reason
		}
		catch (NoSuchFieldException | SecurityException | IllegalAccessException e)
		{
		    System.err.println("Animation failure: Failed to access field " + data.movers[streamFinal] + " " + e);
		    return;
		}
	    });
	}

	for (int[] animationAngles : data.animations)
	{
	    ticks++;
	    for (int stream = 0; stream < data.numStreams; stream++)
	    {
		int angle = animationAngles[stream];
		if (angle != Integer.MAX_VALUE) // Only add an animation clip to a cell that has a number
		{
		    animation.get(stream).add(new AnimationClip<T>(ticks - previousTicks[stream], previousAngles[stream], angle, modelMovers.get(stream)));
		    previousAngles[stream] = angle;
		    previousTicks[stream] = ticks;
		}
	    }
	}

	this.animations = animation;
	activeAnimations = new int[animations.size()];
	for (int stream = 0; stream < animations.size(); stream++)
	{
	    activeAnimations[stream] = animations.get(stream).size() - 1;
	}
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
