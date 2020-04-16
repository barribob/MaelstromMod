package com.barribob.MaelstromMod.init;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;

import com.barribob.MaelstromMod.util.Reference;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;

/**
 * Handle animation registration
 * 
 * @author Barribob
 *
 */
public class ModBBAnimations
{
    private static final Map<Integer, JsonObject> animations = new HashMap<Integer, JsonObject>();
    private static final Map<Integer, String> idToAnimation = new HashMap<Integer, String>();
    private static final Map<String, Integer> nameToId = new HashMap<String, Integer>();

    private static int id = -1;

    public static void registerAnimations()
    {
	animations.put(-1, new JsonObject()); // Default animation for any animations that fail to load
	registerAnimations("scout");
    }

    public static JsonObject getAnimation(String id)
    {
	if (nameToId.containsKey(id))
	{
	    return animations.get(nameToId.get(id));
	}
	System.err.println("Could not find registered animation with id " + id);
	return new JsonObject();
    }

    /**
     * Used to easily hot replace animations during debug when constructing new animations
     * 
     * @param id
     * @return
     */
    public static JsonObject getAnimationUncached(String filename, String animName)
    {
	System.out.println("Warning: using the uncached version of animation loading");
	String name = filename + "." + animName;
	nameToId.remove(name);
	registerAnimations(filename);
	return getAnimation(name);
    }

    public static String getAnimationName(int id)
    {
	return idToAnimation.get(id);
    }

    /**
     * Add the animation file's animations to the animation registry
     * 
     * @param filename
     */
    private static void registerAnimations(String filename)
    {
	try
	{
	    JsonObject animationFile = loadAnimationFile(filename);

	    // Check the animation's version
	    if (!animationFile.get("format_version").getAsString().startsWith("1.8.0"))
	    {
		System.err.println("Animation format not included for animation file: " + filename);
	    }

	    for (Entry<String, JsonElement> animation : animationFile.getAsJsonObject("animations").entrySet())
	    {
		id++;
		nameToId.put(filename + "." + animation.getKey(), id);
		animations.put(id, animation.getValue().getAsJsonObject());
	    }
	}
	catch (JsonException e)
	{
	    System.err.println("Failed to load animation: " + e);
	}
    }

    /**
     * Loads all animations from a bedrock animation json file
     * 
     * @param filename
     * @return
     * @throws JsonException
     */
    private static JsonObject loadAnimationFile(String filename) throws JsonException
    {
	JsonParser jsonparser = new JsonParser();
	ResourceLocation loc = new ResourceLocation(Reference.MOD_ID, "animations/" + filename + ".json");
	IResource iresource = null;

	try
	{
	    iresource = Minecraft.getMinecraft().getResourceManager().getResource(loc);
	    return jsonparser.parse(IOUtils.toString(iresource.getInputStream(), StandardCharsets.UTF_8)).getAsJsonObject();
	}
	catch (IOException e)
	{
	    JsonException jsonexception = JsonException.forException(e);
	    jsonexception.setFilenameAndFlush(loc.getResourcePath());
	    throw jsonexception;
	}
	finally
	{
	    IOUtils.closeQuietly(iresource);
	}
    }
}
