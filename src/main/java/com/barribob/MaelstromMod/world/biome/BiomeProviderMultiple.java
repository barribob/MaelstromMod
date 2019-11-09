package com.barribob.MaelstromMod.world.biome;

import com.barribob.MaelstromMod.world.biome.layer.LayerMultiBiomes;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.storage.WorldInfo;

/*
 * Credit to this post for a solution on how to make multi biome dimensions
 * https://www.minecraftforge.net/forum/topic/64024-1122-biome-and-dimension-tutorials/
 * WARNING this uses hardcoded biomes, so passing them though the constructor on instantiation doesn't work
 */
public abstract class BiomeProviderMultiple extends BiomeProvider
{
    public BiomeProviderMultiple(WorldInfo worldInfo)
    {
	super(worldInfo);
    }

    @Override
    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original)
    {
	GenLayer biomes = new LayerMultiBiomes(1, this.getBiomesToSpawnIn());
	biomes = new GenLayerZoom(1000, biomes);
	biomes = new GenLayerZoom(1001, biomes);
	biomes = new GenLayerZoom(1002, biomes);
	biomes = new GenLayerZoom(1003, biomes);
	biomes = new GenLayerZoom(1004, biomes);
	GenLayer voronoi = new GenLayerVoronoiZoom(10L, biomes);
	voronoi.initWorldGenSeed(seed);

	return new GenLayer[] { biomes, voronoi };
    }
}
