package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.world.biome.BiomeAzure;
import com.barribob.MaelstromMod.world.biome.BiomeCliffPlateau;
import com.barribob.MaelstromMod.world.biome.BiomeCliffSwamp;
import com.barribob.MaelstromMod.world.biome.BiomeCrimsonKingdom;
import com.barribob.MaelstromMod.world.biome.BiomeNexus;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Credit for Jabelar for giving basic structure of biome init
 * https://github.com/jabelar/ExampleMod-1.12/blob/master/src/main/java/com/blogspot/jabelarminecraft/examplemod/init/ModBiomes.java#L65
 */
@ObjectHolder(Reference.MOD_ID)
public class BiomeInit
{
    public static final Biome AZURE = null;
    public static final Biome AZURE_LIGHT = null;
    public static final Biome AZURE_PLAINS = null;
    public static final Biome NEXUS = null;
    public static final Biome HIGH_CLIFF = null;
    public static final Biome CLIFF_SWAMP = null;
    public static final Biome CRIMSON_KINGDOM = null;

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class RegistrationHandler
    {
	@SubscribeEvent
	public static void onEvent(final RegistryEvent.Register<Biome> event)
	{
	    final IForgeRegistry<Biome> registry = event.getRegistry();

	    initBiome(registry, new BiomeAzure(5, 8), "azure", BiomeType.WARM, Type.HILLS);
	    initBiome(registry, new BiomeAzure(1, 8), "azure_light", BiomeType.WARM, Type.HILLS);
	    initBiome(registry, new BiomeAzure(1, 2), "azure_plains", BiomeType.WARM, Type.HILLS);
	    initBiome(registry, new BiomeNexus(), "nexus", BiomeType.WARM, Type.PLAINS);
	    initBiome(registry, new BiomeCliffPlateau(), "high_cliff", BiomeType.WARM, Type.PLAINS);
	    initBiome(registry, new BiomeCliffSwamp(), "cliff_swamp", BiomeType.WARM, Type.SWAMP);
	    initBiome(registry, new BiomeCrimsonKingdom(), "crimson_kingdom", BiomeType.WARM, Type.PLAINS);
	}
    }

    private static void initBiome(IForgeRegistry<Biome> registry, Biome biome, String name, BiomeType biomeType, Type... types)
    {
	biome.setRegistryName(name);
	registry.register(biome);
	BiomeDictionary.addTypes(biome, types);
    }
}
