package com.barribob.MaelstromMod.world.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

/**
 * 
 * The biome for the azure dimension.
 *
 */
public class BiomeNexus extends Biome
{
    public BiomeNexus()
    {
	super(new BiomeProperties("Nexus").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F).setRainDisabled().setWaterColor(10252253));
    }

    @Override
    public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType)
    {
	return new ArrayList<SpawnListEntry>(); // Don't spawn any mobs
    }

    // Don't do generation
    @Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
    }
}
