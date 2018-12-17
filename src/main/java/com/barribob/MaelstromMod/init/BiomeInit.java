package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.world.biome.BiomeAzure;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeInit 
{
	public static final Biome AZURE = new BiomeAzure();
	
	public static void registerBiomes()
	{
		initBiome(AZURE, "azure", BiomeType.WARM, false, Type.HILLS);
	}
	
	private static void initBiome(Biome biome, String name, BiomeType biomeType, boolean addToOverworld, Type... types)
	{
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
		
		if(addToOverworld) 
		{
			BiomeManager.addBiome(biomeType, new BiomeEntry(biome, 10));
			BiomeManager.addSpawnBiome(biome);
		}
	}
}
