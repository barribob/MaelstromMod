package com.barribob.MaelstromMod.world.biome;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeCrimsonKingdom extends Biome
{
    public BiomeCrimsonKingdom()
    {
	super(new BiomeProperties("Nexus").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F).setRainDisabled().setWaterColor(10252253));

	// Clear all existing creatures
	this.spawnableCaveCreatureList.clear();
	this.spawnableCreatureList.clear();
	this.spawnableMonsterList.clear();
	this.spawnableWaterCreatureList.clear();
    }
    
    // Don't do generation
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
    }
}
