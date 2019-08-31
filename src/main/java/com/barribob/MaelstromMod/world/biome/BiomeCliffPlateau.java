package com.barribob.MaelstromMod.world.biome;

import com.barribob.MaelstromMod.init.ModBlocks;

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
}
