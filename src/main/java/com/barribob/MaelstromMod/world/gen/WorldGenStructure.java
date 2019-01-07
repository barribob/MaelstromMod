package com.barribob.MaelstromMod.world.gen;

import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.barribob.MaelstromMod.util.IStructure;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
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
    public static String structureName;

    /**
     * @param name
     *            The name of the structure to load in the nbt file
     */
    public WorldGenStructure(String name)
    {
	this.structureName = name;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
	this.generateStructure(worldIn, position);
	return true;
    }

    /**
     * Loads the structure from the nbt file and generates it
     * 
     * @param world
     * @param pos
     */
    public void generateStructure(World world, BlockPos pos)
    {
	MinecraftServer mcServer = world.getMinecraftServer();
	TemplateManager manager = worldServer.getStructureTemplateManager();
	ResourceLocation location = new ResourceLocation(Reference.MOD_ID, structureName);
	Template template = manager.get(mcServer, location);
	if (template != null)
	{
	    template.addBlocksToWorldChunk(world, pos, settings);

	    Map<BlockPos, String> dataBlocks = template.getDataBlocks(pos, new PlacementSettings());
	    for (Entry<BlockPos, String> entry : dataBlocks.entrySet())
	    {
		String s = entry.getValue();
		this.handleDataMarker(s, entry.getKey(), world, world.rand);
	    }
	}
	else
	{
	    System.out.println("The template, " + location + " could not be loaded");
	}
    }

    /**
     * Called when a data structure block is found, in order to replace it with something else
     * @param function
     * @param pos
     * @param worldIn
     * @param rand
     */
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand)
    {
    }
}
