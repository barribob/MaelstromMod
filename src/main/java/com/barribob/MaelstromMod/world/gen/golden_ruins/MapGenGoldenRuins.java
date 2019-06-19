package com.barribob.MaelstromMod.world.gen.golden_ruins;

import java.util.Random;

import com.barribob.MaelstromMod.world.dimension.cliff.ChunkGeneratorCliff;
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
public class MapGenGoldenRuins extends MapGenModStructure
{
    ChunkGeneratorCliff provider;
    public MapGenGoldenRuins(int spacing, int offset, int odds, ChunkGeneratorCliff provider)
    {
	super(spacing, offset, odds);
	this.provider = provider;
    }

    public String getStructureName()
    {
	return "Golden Ruins";
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
	return new MapGenGoldenRuins.Start(this.world, this.rand, this.provider, chunkX, chunkZ);
    }

    public class Start extends StructureStart
    {
	public Start()
	{
	}

	public Start(World worldIn, Random random, ChunkGeneratorCliff provider, int chunkX, int chunkZ)
	{
	    super(chunkX, chunkZ);
	    this.create(worldIn, random, chunkX, chunkZ);
	}

	private void create(World worldIn, Random rnd, int chunkX, int chunkZ)
	{
	    Random random = new Random((long) (chunkX + chunkZ * 10387313));
	    Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
	    int y = 130;

	    BlockPos blockpos = new BlockPos(chunkX * 16 + 8, y, chunkZ * 16 + 8);
	    GoldenRuins stronghold = new GoldenRuins(worldIn, worldIn.getSaveHandler().getStructureTemplateManager(), provider, components);
	    stronghold.startStronghold(blockpos, Rotation.NONE);
	    this.updateBoundingBox();
	    System.out.println(blockpos);
	}
    }
}