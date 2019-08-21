package com.barribob.MaelstromMod.world.biome;

import net.minecraft.world.biome.Biome;

/**
 * 
 * The biome for the azure dimension.
 *
 */
public class BiomeNexus extends Biome
{
    public BiomeNexus()
    {
	super(new BiomeProperties("Nexus").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F)
		.setRainDisabled().setWaterColor(10252253));

	// Clear all existing creatures
	this.spawnableCaveCreatureList.clear();
	this.spawnableCreatureList.clear();
	this.spawnableMonsterList.clear();
	this.spawnableWaterCreatureList.clear();
    }
}
