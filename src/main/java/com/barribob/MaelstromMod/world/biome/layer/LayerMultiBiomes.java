package com.barribob.MaelstromMod.world.biome.layer;

import java.util.List;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

/*
 * Credit for assistance in creating this class: 
 * https://github.com/TeamTwilight/twilightforest/tree/1.12.x/src/main/java/twilightforest/world/layer/
 */
public class LayerMultiBiomes extends GenLayer
{
    List<Biome> biomes;
    public LayerMultiBiomes(long l, List<Biome> biomes)
    {
	super(l);
	this.biomes = biomes;
    }

    @Override
    public int[] getInts(int areaX, int areaZ, int width, int depth)
    {
	int ints[] = IntCache.getIntCache(width * depth);
	
	for(int z = 0; z < depth; z++)
	{
	    for(int x = 0; x < width; x++)
	    {
		this.initChunkSeed(x + areaX, z + areaZ);
		ints[x + z * width] = Biome.getIdForBiome(biomes.get(this.nextInt(biomes.size())));
	    }
	}
	
	return ints;
    }
}
