package com.barribob.MaelstromMod.world.gen.maelstrom_stronghold;

import java.util.List;
import java.util.Random;

import com.barribob.MaelstromMod.entity.entities.EntityHorror;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.google.common.collect.Lists;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureEndCityPieces;
import net.minecraft.world.gen.structure.StructureStart;

/**
 * 
 * Determines where to spawn the maelstrom fortress
 *
 */
public class MapGenMaelstromStronghold extends MapGenStructure
{
    public MapGenMaelstromStronghold()
    {
    }

    public String getStructureName()
    {
	return "Maelstrom Stronghold";
    }

    /**
     * Uses the same spawning logic as the nether fortress
     */
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
	if(Math.abs(chunkX % 16) == 8 && Math.abs(chunkZ % 16) == 8)
	{
	    return true;
	}
	return false;
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
	return new MapGenMaelstromStronghold.Start(this.world, this.rand, chunkX, chunkZ);
    }

    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
	this.world = worldIn;
	return findNearestStructurePosBySpacing(worldIn, this, pos, 20, 11, 10387313, true, 100, findUnexplored);
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
//	    Random random = new Random((long) (chunkX + chunkZ * 10387313));
//	    Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
//	    int y = 95;
//
//	    BlockPos blockpos = new BlockPos(chunkX * 16 + 8, y, chunkZ * 16 + 8);
//	    MaelstromStronghold.startStronghold(worldIn, worldIn.getSaveHandler().getStructureTemplateManager(), blockpos, rotation, this.components);
//	    this.updateBoundingBox();
//	    System.out.println(blockpos);
	}
    }
}