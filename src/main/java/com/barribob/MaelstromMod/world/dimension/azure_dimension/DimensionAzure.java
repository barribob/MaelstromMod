package com.barribob.MaelstromMod.world.dimension.azure_dimension;

import com.barribob.MaelstromMod.init.BiomeInit;
import com.barribob.MaelstromMod.init.DimensionInit;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

/**
 * 
 * The Azure dimension attributes are defined here
 *
 */
public class DimensionAzure extends WorldProvider
{
	// Overridden to change the biome provider
	@Override
    protected void init()
    {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderSingle(BiomeInit.AZURE);
    }

	@Override
	public DimensionType getDimensionType() {
		return DimensionInit.AZURE;
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorAzure(world, world.getSeed(), true, "");
	}
	
	@Override
	public boolean canRespawnHere() {
		return false;
	}
	
	@Override
	public boolean isSurfaceWorld() {
		return false;
	}
	
}
