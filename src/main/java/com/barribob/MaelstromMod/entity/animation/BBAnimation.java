package com.barribob.MaelstromMod.entity.animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.barribob.MaelstromMod.init.ModBBAnimations;
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
public class BBAnimation implements Animation<ModelBase>
{
    private JsonObject animation;
    private long ticksSinceStart = 0;
    private String animationId;

    /**
     * Animation id of the format: animation_filename.animation_name
     * 
     * @param animationId
     */
    public BBAnimation(String animationId)
    {
	this.animationId = animationId;
	String[] s = animationId.split("(\\.)", 2);
	animation = ModBBAnimations.getAnimationUncached(s[0], s[1]);
    }

    @Override
    public void setModelRotations(ModelBase model, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	boolean loop = false;
	if (animation.has("loop"))
	{
	    loop = animation.get("loop").getAsBoolean();
	}

	float animationLength = animation.get("animation_length").getAsFloat();
	float timeInSeconds;

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

	    try
	    {
		component = ((ModelRenderer) model.getClass().getField(elementEntry.getKey()).get(model));
	    }
	    catch (NoSuchFieldException | SecurityException | IllegalAccessException e)
	    {
		System.err.println("Animation failure: Failed to access field " + elementEntry.getKey() + " for animationid " + animationId + e);
		break;
	    }

	    if (element.has("rotation"))
	    {
		float[] rotations = getInterpotatedValues(timeInSeconds, element.getAsJsonObject("rotation").entrySet());
		component.rotateAngleX = (float) Math.toRadians(rotations[0]);
		component.rotateAngleY = (float) Math.toRadians(rotations[1]);
		component.rotateAngleZ = (float) Math.toRadians(rotations[2]);
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
	List<Entry<String, JsonElement>> values = findElementsBetweenTime(timeInSeconds, set);
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
     * @param set
     * @return
     */
    private static List<Entry<String, JsonElement>> findElementsBetweenTime(float timeInSeconds, Set<Entry<String, JsonElement>> set)
    {
	Entry<String, JsonElement> previousEntry = null;
	List<Entry<String, JsonElement>> entries = new ArrayList<Entry<String, JsonElement>>();
	for (Entry<String, JsonElement> rotationEntry : set)
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

    @Override
    public void update()
    {
	this.ticksSinceStart++;
    }

    @Override
    public void startAnimation()
    {
	this.ticksSinceStart = 0;
    }
}
