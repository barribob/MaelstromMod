package com.barribob.MaelstromMod.entity.animation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

/**
 * Allows for multiple streams of animations to proceed, using lists of
 * animation clips
 */
public class StreamAnimation<T extends ModelBase> implements Animation<T>
{
    // First dimension represents the animation stream, and the second represents
    // the order of the animations
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

    /**
     * Constructs a stream animation from a csv file
     * @param csv
     * @param modelMovers
     */
    public StreamAnimation(String csv, List<BiConsumer<T, Float>> modelMovers)
    {
	List<List<AnimationClip<T>>> animation = new ArrayList<List<AnimationClip<T>>>();
	try
	{
	    ResourceLocation loc = new ResourceLocation(Reference.MOD_ID, "animations/" + csv);
	    InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    reader.readLine(); // Move line pointer down because of extra title
	    String line = reader.readLine();
	    String delimiter = ",";

	    // Determine the number of streams by looking at the first row of data
	    int numStreams = 0;
	    if (line != null)
	    {
		numStreams = line.split(delimiter, -1).length;
	    }

	    // Make sure animation file matches the model movers given
	    if (modelMovers.size() != numStreams)
	    {
		System.err.println("Number of model movers does not equal streams read in animation file " + csv);
	    }

	    int[] previousAngles = new int[numStreams]; // Stores the previous angles
	    int[] previousTicks = new int[numStreams]; // Stores the previous ticks that the previous angle was at
	    String[] data = line.split(delimiter, -1); // The first set of data
	    int ticks = 1; // Initialize ticks to be 0
	    for (int stream = 0; stream < numStreams; stream++)
	    {
		animation.add(new ArrayList<AnimationClip<T>>());
		previousAngles[stream] = ModUtils.tryParseInt(data[stream], 0);
	    }

	    while ((line = reader.readLine()) != null)
	    {
		ticks++;
		data = line.split(delimiter, -1);
		for (int stream = 0; stream < numStreams; stream++)
		{
		    int angle = ModUtils.tryParseInt(data[stream], Integer.MAX_VALUE);
		    if(angle != Integer.MAX_VALUE) // Only add an animation clip to a cell that has a number
		    {
			animation.get(stream).add(new AnimationClip<T>(ticks - previousTicks[stream], previousAngles[stream], angle, modelMovers.get(stream)));
			previousAngles[stream] = angle;
			previousTicks[stream] = ticks;
		    }
		}
	    }

	    reader.close();
	}
	catch (IOException e)
	{
	    System.err.println("Error reading animation file " + csv + ": " + e);
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
}
