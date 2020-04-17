package com.barribob.MaelstromMod.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.animation.AnimationManager;
import com.barribob.MaelstromMod.packets.MessageAnimation;
import com.barribob.MaelstromMod.util.Reference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.util.JsonException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

/**
 * Handle animation registration automatically
 * 
 * @author Barribob
 *
 */
public class ModBBAnimations
{
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Map<Integer, JsonObject> animations = new HashMap<Integer, JsonObject>();
    private static final Map<String, Integer> nameToId = new HashMap<String, Integer>();
    private static final Map<Integer, String> idToName = new HashMap<Integer, String>();

    private static int id = -1;

    /**
     * Animation id of the format: animation_filename.animation_name. For example if I have an animation file called "anim.json" and inside it there is one animation under the "animations" object named
     * "walk", then the id would be "anim.walk"
     * 
     * @param animationId
     */
    public static void addAnimationToEntity(EntityLivingBase entity, String animationId)
    {
	if (!entity.world.isRemote)
	{
	    Main.network.sendToAllTracking(new MessageAnimation(ModBBAnimations.getAnimationId(animationId), entity.getEntityId()), entity);
	}
	else
	{
	    AnimationManager.addAnimation(entity, animationId);
	}
    }

    public static String getAnimationName(int id)
    {
	if (idToName.containsKey(id))
	{
	    return idToName.get(id);
	}
	return "";
    }

    public static int getAnimationId(String id)
    {
	if (nameToId.containsKey(id))
	{
	    return nameToId.get(id);
	}
	System.err.println("Could not find registered animation with id " + id);
	return -1;
    }

    /**
     * Animation id of the format: animation_filename.animation_name. For example if I have an animation file callsed "anim.json" and inside it there is one animation under the "animations" object named
     * "walk", then the id would be "anim.walk"
     * 
     * @param id
     * @return
     */
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
     * Used to easily hot replace animations during debug when constructing new animations It probably doesn't work during production, so make sure to switch to getAnimation()
     * 
     * @param id
     * @return
     */
    public static JsonObject getAnimationUncached(String animationId)
    {
	System.out.println("Warning: using the uncached version of animation loading");
	String[] s = animationId.split("(\\.)", 2);
	String filename = s[0];
	String animName = s[1];
	ResourceLocation loc = new ResourceLocation(Reference.MOD_ID, "animations/" + filename + ".json");
	JsonParser jsonparser = new JsonParser();
	IResource resource = null;

	try
	{
	    resource = Minecraft.getMinecraft().getResourceManager().getResource(loc);
	    JsonObject animationObject = jsonparser.parse(IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8)).getAsJsonObject();
	    return animationObject.getAsJsonObject("animations").getAsJsonObject(animName);
	}
	catch (IOException e)
	{
	    System.err.println("Failed to load animation: " + filename + e);
	}
	finally
	{
	    IOUtils.closeQuietly(resource);
	}

	return new JsonObject();
    }

    /**
     * Add the animation file's animations to the animation registry
     * 
     * @param filename
     */
    private static void registerAnimations(JsonObject animationFile, String filename)
    {
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
	    idToName.put(id, filename + "." + animation.getKey());
	}
    }

    /**
     * Loads all animations from a bedrock animation json file. Walks through file folder using code from CraftingHelper in FML
     * 
     * @param filename
     * @return
     * @throws JsonException
     */
    public static void registerAnimations()
    {
	// Sort of hacky way to get the ModContainer for my mod
	ModContainer myMod = null;
	for (ModContainer mod : Loader.instance().getActiveModList())
	{
	    if (mod.getModId().equals("mm"))
	    {
		myMod = mod;
	    }
	}

	final ModContainer mod = myMod; // Because lambda function can't take non final values :(
	JsonContext ctx = new JsonContext(myMod.getModId());

	// This part is pretty similar CraftingHelper.java does to load recipes
	CraftingHelper.findFiles(myMod, "assets/" + myMod.getModId() + "/animations",
		root -> {
		    return true;
		},
		(root, file) -> {
		    Loader.instance().setActiveModContainer(mod);

		    String relative = root.relativize(file).toString();
		    if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_"))
			return true;

		    String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
		    ResourceLocation key = new ResourceLocation(ctx.getModId(), name);

		    BufferedReader reader = null;
		    try
		    {
			reader = Files.newBufferedReader(file);
			JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
			registerAnimations(json, name);
		    }
		    catch (IOException e)
		    {
			LogManager.getLogger().error("Couldn't read animation {} from {}", key, file, e);
			return false;
		    }
		    finally
		    {
			IOUtils.closeQuietly(reader);
		    }
		    return true;
		}, true, true);
    }
}
