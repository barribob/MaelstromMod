package com.barribob.MaelstromMod.entity.animation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * An animation that parses the animation output from block bench.
 * 
 * @author Barribob
 *
 */
public class BBAnimation
{
    private JsonObject animation;
    private long ticksSinceStart = 0;
    private String animationId;
    private float animationLength;
    private boolean loop = false;

    /**
     * Animation id of the format: animation_filename.animation_name. For example if I have an animation file callsed "anim.json" and inside it there is one animation under the "animations" object named
     * "walk", then the id would be "anim.walk"
     * 
     * @param animationId
     */
    public BBAnimation(String animationId)
    {
	this.animationId = animationId;
	animation = ModBBAnimations.getAnimationUncached(animationId);
	animationLength = animation.get("animation_length").getAsFloat();
	if (animation.has("loop"))
	{
	    loop = animation.get("loop").getAsBoolean();
	}
    }

    public void setModelRotations(ModelBase model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	float timeInSeconds;

	// Looping is achieved by just using the remainder of the ticksSinceStart / animationLength
	if (loop)
	{
	    timeInSeconds = (ticksSinceStart + partialTicks) * 0.05f;
	    float numRepetitions = (float) Math.floor(timeInSeconds / animationLength);
	    timeInSeconds -= animationLength * numRepetitions;
	}
	else
	{
	    timeInSeconds = (ticksSinceStart + partialTicks) * 0.05f;
	}

	for (Entry<String, JsonElement> elementEntry : animation.getAsJsonObject("bones").entrySet())
	{
	    JsonObject element = elementEntry.getValue().getAsJsonObject();
	    ModelRenderer component;

	    // Use reflection to get the component we want from the model
	    try
	    {
		Field field = model.getClass().getDeclaredField(elementEntry.getKey());
		if (!field.isAccessible())
		{
		    field.setAccessible(true);
		}
		component = ((ModelRenderer) field.get(model));
	    }
	    catch (NoSuchFieldException | SecurityException | IllegalAccessException e)
	    {
		System.err.println("Animation failure: Failed to access field " + elementEntry.getKey() + " for animationid " + animationId + " " + e);
		break;
	    }

	    if (element.has("rotation"))
	    {
		float[] rotations = getInterpotatedValues(timeInSeconds, element.getAsJsonObject("rotation").entrySet());
		component.rotateAngleX += (float) Math.toRadians(rotations[0]);
		component.rotateAngleY += (float) Math.toRadians(rotations[1]);
		component.rotateAngleZ += (float) Math.toRadians(rotations[2]);
	    }

	    if (element.has("position"))
	    {
		float[] offsets = getInterpotatedValues(timeInSeconds, element.getAsJsonObject("position").entrySet());
		component.rotationPointX -= offsets[0];
		component.rotationPointY -= offsets[1];
		component.rotationPointZ -= offsets[2];
	    }
	}
    }

    /**
     * Interpolates between some values. The equation is basically begin + ((end - begin) * progress) where progress is (time - timeBegin) / (clipLength), assuming that time is larger than timeBegin.
     * 
     * @param timeInSeconds
     * @param set
     * @return
     */
    private static float[] getInterpotatedValues(float timeInSeconds, Set<Entry<String, JsonElement>> set)
    {
	List<Entry<String, JsonElement>> entries = Lists.newArrayList(set);

	// Sort the event because the animations aren't guarenteed to be in order
	entries.sort((entry1, entry2) -> {
	    float timeStamp1 = Float.parseFloat(entry1.getKey());
	    float timeStamp2 = Float.parseFloat(entry2.getKey());
	    return timeStamp1 > timeStamp2 ? 1 : -1;
	});

	List<Entry<String, JsonElement>> values = findElementsBetweenTime(timeInSeconds, entries);
	float clipBegin = Float.parseFloat(values.get(0).getKey());
	float clipEnd = Float.parseFloat(values.get(1).getKey());
	float clipLength = clipEnd - clipBegin;
	float progress = clipLength == 0 ? 1 : (timeInSeconds - clipBegin) / clipLength;
	JsonArray beginValues = values.get(0).getValue().getAsJsonArray();
	JsonArray endValues = values.get(1).getValue().getAsJsonArray();

	float finalValues[] = new float[3];
	for (int i = 0; i < 3; i++)
	{
	    float begin = beginValues.get(i).getAsFloat();
	    float end = endValues.get(i).getAsFloat();
	    finalValues[i] = begin + ((end - begin) * progress);
	}
	return finalValues;
    }

    /**
     * Returns a list of two elements that correspond to the previous state and the current state the current time will be in between the two of these elements
     * 
     * @param timeInSeconds
     * @param allEntries
     * @return
     */
    private static List<Entry<String, JsonElement>> findElementsBetweenTime(float timeInSeconds, List<Entry<String, JsonElement>> allEntries)
    {
	Entry<String, JsonElement> previousEntry = null;
	List<Entry<String, JsonElement>> entries = new ArrayList<Entry<String, JsonElement>>();
	for (Entry<String, JsonElement> rotationEntry : allEntries)
	{
	    float timeStamp = Float.parseFloat(rotationEntry.getKey());
	    if (timeStamp > timeInSeconds)
	    {
		if (previousEntry != null)
		{
		    entries.add(previousEntry);
		    entries.add(rotationEntry);
		}
		else
		{
		    entries.add(rotationEntry);
		    entries.add(rotationEntry);
		}
		return entries;
	    }
	    previousEntry = rotationEntry;
	}
	entries.add(previousEntry);
	entries.add(previousEntry);
	return entries;
    }

    public void update()
    {
	this.ticksSinceStart++;
    }

    public void startAnimation()
    {
	this.ticksSinceStart = 0;
    }
    
    /**
     * How many ticks since the animation began playing. Also effectively how many times update() has been called.
     */
    public long getTicksSinceStart()
    {
        return ticksSinceStart;
    }

    /**
     * An animation is ended if it isn't looping and the time since it started has passed its animation length
     */
    public boolean isEnded()
    {
	return !loop && (this.ticksSinceStart * 0.05f) > this.animationLength;
    }
}
