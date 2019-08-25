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

    @Override
    public String getStructureName()
    {
	return "Golden Ruins";
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
	return new MapGenGoldenRuins.Start(this.world, this.rand, chunkX, chunkZ, provider);
    }

    public static class Start extends StructureStart
    {
	ChunkGeneratorCliff provider;
	public Start()
	{
	}

	public Start(World worldIn, Random random, int chunkX, int chunkZ, ChunkGeneratorCliff provider)
	{
	    super(chunkX, chunkZ);
	    this.provider = provider;
	    this.create(worldIn, random, chunkX, chunkZ);
	}

	private void create(World worldIn, Random rnd, int chunkX, int chunkZ)
	{
	    Random random = new Random(chunkX + chunkZ * 10387313);
	    Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
	    int mod = (ChunkGeneratorCliff.STRUCTURE_SPACING_CHUNKS + ChunkGeneratorCliff.GOLDEN_RUINS_NUMBER) * 2;
	    boolean isXEven = ((((chunkX - ChunkGeneratorCliff.GOLDEN_RUINS_NUMBER) / ChunkGeneratorCliff.STRUCTURE_SPACING_CHUNKS) % 2) & 1) == 0;
	    boolean isZEven = ((((chunkZ - ChunkGeneratorCliff.GOLDEN_RUINS_NUMBER) / ChunkGeneratorCliff.STRUCTURE_SPACING_CHUNKS) % 2) & 1) == 0;
	    int y = isXEven ^ isZEven ? 135 : 170;

	    BlockPos blockpos = new BlockPos(chunkX * 16 + 8, y, chunkZ * 16 + 8);
	    GoldenRuins stronghold = new GoldenRuins(worldIn, worldIn.getSaveHandler().getStructureTemplateManager(), provider, components);
	    stronghold.startStronghold(blockpos, Rotation.NONE);
	    this.updateBoundingBox();
	}
    }
}