package com.barribob.MaelstromMod.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barribob.MaelstromMod.entity.animation.StreamAnimation.AnimationData;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * Registers animations for entities
 *
 */
public class ModAnimations
{
    private static Map<Integer, String> idToAnimation = new HashMap<Integer, String>();
    private static Map<Integer, AnimationData> animations = new HashMap<Integer, AnimationData>();
    private static int id = -1;

    public static int SCOUT_SLASH;
    public static int CHAOS_KNIGHT_SINGLE_SWIPE;
    public static int CHAOS_KNIGHT_LEAP_SLAM;
    public static int CHAOS_KNIGHT_DASH;
    public static int CHAOS_KNIGHT_SPIN_SLASH;

    public static void registerAnimations()
    {
	SCOUT_SLASH = registerAnimation("animation_scout.csv");
	CHAOS_KNIGHT_SINGLE_SWIPE = registerAnimation("chaos_knight/single_swipe.csv");
	CHAOS_KNIGHT_LEAP_SLAM = registerAnimation("chaos_knight/leap_slam.csv");
	CHAOS_KNIGHT_DASH = registerAnimation("chaos_knight/dash.csv");
	CHAOS_KNIGHT_SPIN_SLASH = registerAnimation("chaos_knight/spin_slash.csv");
    }

    public static AnimationData getAnimationById(int id)
    {
	if (animations.containsKey(id))
	{
	    return animations.get(id);
	}
	System.err.println("Could not load animation with id " + id);
	return new AnimationData();
    }

    /**
     * Used to easily hot replace animations during debug when constructing new
     * animations
     * 
     * @param id
     * @return
     */
    public static AnimationData getAnimationByIdUncached(int id)
    {
	System.out.println("Warning: using the uncached version of animation loading");
	return loadAnimations(idToAnimation.get(id));
    }

    private static int registerAnimation(String filename)
    {
	id++;
	idToAnimation.put(id, filename);
	animations.put(id, loadAnimations(filename));
	return id;
    }

    /**
     * Loads an animation data and caches it. Otherwise, loads the animation data
     * from the cache.
     * 
     * @param csv
     * @return
     */
    private static AnimationData loadAnimations(String csv)
    {
	ResourceLocation loc = new ResourceLocation(Reference.MOD_ID, "animations/" + csv);
	try (BufferedReader reader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream())))
	{
	    String delimiter = ",";
	    String line = reader.readLine();

	    int numStreams = 0;
	    if (line != null)
	    {
		numStreams = line.split(delimiter, -1).length;
	    }

	    final String[] titles = line.split(delimiter, -1); // The first set of data are the model fields

	    List<int[]> animations = new ArrayList<int[]>();
	    while ((line = reader.readLine()) != null)
	    {
		String[] unparsed = line.split(delimiter, -1);
		int[] data = new int[numStreams];
		for (int stream = 0; stream < numStreams; stream++)
		{
		    data[stream] = ModUtils.tryParseInt(unparsed[stream], Integer.MAX_VALUE);
		}
		animations.add(data);
	    }

	    reader.close();
	    AnimationData data = new AnimationData(titles, animations, numStreams);
	    return data;
	}
	catch (IOException e)
	{
	    System.err.println("Error reading animation file " + csv + ": " + e);
	    return new AnimationData();
	}
    }
}
