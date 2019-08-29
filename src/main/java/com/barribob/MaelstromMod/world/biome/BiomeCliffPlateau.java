package com.barribob.MaelstromMod.world.biome;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenCliffMushroom;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenCliffShrub;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenSwampVines;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenWaterfall;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 
 * The biome for the tall part of the cliff dimension
 *
 */
public class BiomeCliffPlateau extends BiomeDifferentStone
{
    public BiomeCliffPlateau()
    {
	super(new BiomeProperties("Cliff Plateau").setBaseHeight(11F).setHeightVariation(0.15F).setTemperature(0.6F).setRainfall(0.3F), ModBlocks.CLIFF_STONE,
		ModBlocks.CLIFF_STONE);
	
	this.decorator.treesPerChunk = 0;
	this.decorator.grassPerChunk = 3;
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
	super.decorate(worldIn, rand, pos);
	// Generate vines, with less near the top
	ModUtils.generateN(worldIn, rand, pos, 4, 70, 1, new WorldGenCliffMushroom(ModBlocks.CLIFF_STONE));
	ModUtils.generateN(worldIn, rand, pos, 35, 65, 1, new WorldGenCliffShrub(BiomeCliffSwamp.log, BiomeCliffSwamp.leaf));
	ModUtils.generateN(worldIn, rand, pos, 400, 60, 40, new WorldGenSwampVines());
	ModUtils.generateN(worldIn, rand, pos, 200, 100, 40, new WorldGenSwampVines());
	if (rand.nextInt(15) == 0)
	{
	    ModUtils.generateN(worldIn, rand, pos, 1, 200, 50, new WorldGenWaterfall(ModBlocks.CLIFF_STONE));
	}
    }
}
