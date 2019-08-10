package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.world.biome.BiomeAzure;
import com.barribob.MaelstromMod.world.biome.BiomeCliffPlateau;
import com.barribob.MaelstromMod.world.biome.BiomeCliffSwamp;
import com.barribob.MaelstromMod.world.biome.BiomeNexus;

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
    public static final Biome NEXUS = new BiomeNexus();
    public static final Biome HIGH_CLIFF = new BiomeCliffPlateau();
    public static final Biome CLIFF_SWAMP = new BiomeCliffSwamp();

    public static void registerBiomes()
    {
	initBiome(AZURE, "azure", BiomeType.WARM, false, Type.HILLS);
	initBiome(NEXUS, "nexus", BiomeType.WARM, false, Type.PLAINS);
	initBiome(HIGH_CLIFF, "high_cliff", BiomeType.WARM, false, Type.PLAINS);
	initBiome(CLIFF_SWAMP, "cliff_swamp", BiomeType.WARM, false, Type.SWAMP);
    }

    private static void initBiome(Biome biome, String name, BiomeType biomeType, boolean addToOverworld, Type... types)
    {
	biome.setRegistryName(name);
	ForgeRegistries.BIOMES.register(biome);
	BiomeDictionary.addTypes(biome, types);

	if (addToOverworld)
	{
	    BiomeManager.addBiome(biomeType, new BiomeEntry(biome, 10));
	    BiomeManager.addSpawnBiome(biome);
	}
    }
}
