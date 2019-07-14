package com.barribob.MaelstromMod.world.gen.nexus;

import java.util.Random;

import com.barribob.MaelstromMod.world.gen.MapGenModStructure;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

/**
 * 
 * Generates the nexus entrance island
 *
 */
public class MapGenNexusEntrance extends MapGenModStructure
{
    public static final int NEXUS_ISLAND_SPAWN_HEIGHT = 100;
    public MapGenNexusEntrance(int spacing, int offset, int odds)
    {
	super(spacing, offset, odds);
    }

    public String getStructureName()
    {
	return "Nexus Island";
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
	return new MapGenNexusEntrance.Start(this.world, this.rand, chunkX, chunkZ);
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

	    BlockPos blockpos = new BlockPos(chunkX * 16 + 8, NEXUS_ISLAND_SPAWN_HEIGHT, chunkZ * 16 + 8);
	    NexusTemplate theNexus = new NexusTemplate(worldIn.getSaveHandler().getStructureTemplateManager(), "nexus_entrance_island", blockpos, Rotation.NONE, false);
	    this.components.add(theNexus);
	    this.updateBoundingBox();
	}
    }
}