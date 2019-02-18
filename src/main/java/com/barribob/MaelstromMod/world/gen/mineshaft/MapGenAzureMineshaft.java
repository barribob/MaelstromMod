package com.barribob.MaelstromMod.world.gen.mineshaft;

import java.util.Random;

import com.barribob.MaelstromMod.world.gen.MapGenModStructure;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

/**
 * 
 * Determines where to spawn the maelstrom fortress
 *
 */
public class MapGenAzureMineshaft extends MapGenModStructure
{
    public MapGenAzureMineshaft(int spacing, int offset, int odds)
    {
	super(spacing, offset, odds);
    }

    public String getStructureName()
    {
	return "Azure Mineshaft";
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
	return new MapGenAzureMineshaft.Start(this.world, this.rand, chunkX, chunkZ);
    }

    public static class Start extends StructureStart
    {
	public Start()
	{
	}

	public Start(World worldIn, Random random, int chunkX, int chunkZ)
	{
	    super(chunkX, chunkZ);
	    this.create(worldIn, random, chunkX, chunkZ);
	}

	private void create(World worldIn, Random rnd, int chunkX, int chunkZ)
	{
	    Random random = new Random((long) (chunkX + chunkZ * 10387313));
	    Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
	    int y = 35 + random.nextInt(10);

	    BlockPos blockpos = new BlockPos(chunkX * 16 + 8, y, chunkZ * 16 + 8);
	    AzureMineshaft.startMineshaft(worldIn, worldIn.getSaveHandler().getStructureTemplateManager(), blockpos, Rotation.NONE, this.components);
	    this.updateBoundingBox();
	}
    }
}