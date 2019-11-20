package com.barribob.MaelstromMod.world.gen;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.barribob.MaelstromMod.util.IStructure;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

/**
 * 
 * Loads a structure by nbt file
 *
 */
public class WorldGenStructure extends WorldGenerator implements IStructure
{
    public String structureName;
    protected PlacementSettings placeSettings;
    private static final PlacementSettings DEFAULT_PLACE_SETTINGS = new PlacementSettings();
    private Template template;
    private float chanceToFail;
    private int maxVariation;

    /**
     * @param name
     *            The name of the structure to load in the nbt file
     */
    public WorldGenStructure(String name)
    {
	this.structureName = name;
	this.placeSettings = DEFAULT_PLACE_SETTINGS.setIgnoreEntities(true).setReplacedBlock(Blocks.AIR);
    }

    public float getChanceToFail()
    {
	return chanceToFail;
    }

    public void setChanceToFail(float chanceToFail)
    {
	this.chanceToFail = chanceToFail;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
	this.generateStructure(worldIn, position, true);
	return true;
    }

    private Template getTemplate(World world)
    {
	if (template != null)
	{
	    return template;
	}

	MinecraftServer mcServer = world.getMinecraftServer();
	TemplateManager manager = worldServer.getStructureTemplateManager();
	ResourceLocation location = new ResourceLocation(Reference.MOD_ID, structureName);
	template = manager.get(mcServer, location);
	if (template == null)
	{
	    System.out.println("The template, " + location + " could not be loaded");
	    return null;
	}
	return template;
    }

    public BlockPos getSize(World world)
    {
	if (getTemplate(world) == null)
	{
	    return BlockPos.ORIGIN;
	}

	return template.getSize();
    }

    public BlockPos getCenter(World world)
    {
	if (getTemplate(world) == null)
	{
	    return BlockPos.ORIGIN;
	}
	
	return new BlockPos(Math.floorDiv(template.getSize().getX(), 2), Math.floorDiv(template.getSize().getY(), 2), Math.floorDiv(template.getSize().getZ(), 2));
    }

    public void setMaxVariation(int maxVariation)
    {
	this.maxVariation = maxVariation;
    }

    public int getMaxVariation(World world)
    {
	if (maxVariation != 0)
	{
	    return maxVariation;
	}

	if (getTemplate(world) == null)
	{
	    return 0;
	}

	return (int) Math.floor(template.getSize().getY() * 0.25);
    }

    public int getYGenHeight(World world, int x, int z)
    {
	BlockPos templateSize = this.getSize(world);
	return ModUtils.getAverageGroundHeight(world, x, z, templateSize.getX(), templateSize.getZ(), this.getMaxVariation(world));
    }

    /**
     * Loads the structure from the nbt file and generates it
     * 
     * @param world
     * @param pos
     */
    public void generateStructure(World world, BlockPos pos, Boolean doRandomRotation)
    {
	if (getTemplate(world) != null)
	{
	    Tuple<Rotation, BlockPos>[] rotations = new Tuple[] { new Tuple(Rotation.NONE, new BlockPos(0, 0, 0)),
		    new Tuple(Rotation.CLOCKWISE_90, new BlockPos(template.getSize().getX() - 1, 0, 0)),
		    new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, 0, template.getSize().getZ() - 1)),
		    new Tuple(Rotation.CLOCKWISE_180, new BlockPos(template.getSize().getX() - 1, 0, template.getSize().getZ() - 1)) };

	    Tuple<Rotation, BlockPos> randomRotation = doRandomRotation ? ModRandom.choice(rotations, world.rand) : rotations[0];
	    PlacementSettings rotatedSettings = settings.setRotation(randomRotation.getFirst());
	    BlockPos rotatedPos = pos.add(randomRotation.getSecond());

	    template.addBlocksToWorld(world, rotatedPos, rotatedSettings, 18);
	    Map<BlockPos, String> dataBlocks = template.getDataBlocks(rotatedPos, rotatedSettings);
	    for (Entry<BlockPos, String> entry : dataBlocks.entrySet())
	    {
		String s = entry.getValue();
		this.handleDataMarker(s, entry.getKey(), world, world.rand);
	    }
	}
    }

    /**
     * Called when a data structure block is found, in order to replace it with
     * something else
     * 
     * @param function
     * @param pos
     * @param worldIn
     * @param rand
     */
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand)
    {
    }
}
