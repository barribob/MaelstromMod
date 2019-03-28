package com.barribob.MaelstromMod.event_handlers;

import com.barribob.MaelstromMod.world.dimension.nexus.DimensionNexus;
import com.barribob.MaelstromMod.world.gen.nexus.MapGenNexusEntrance;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.event.terraingen.ChunkGeneratorEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/*
 * Allows for large structure generation in the overworld
 */
@Mod.EventBusSubscriber()
public class StructureGenerator
{
    private static MapGenNexusEntrance nexusIsland = new MapGenNexusEntrance(DimensionNexus.NexusStructureSpacing, 0, 1);

    @SubscribeEvent()
    public static void onReplaceBiomeBlocks(ChunkGeneratorEvent.ReplaceBiomeBlocks event)
    {
	nexusIsland.generate(event.getWorld(), event.getX(), event.getZ(), event.getPrimer());
    }
    
    @SubscribeEvent()
    public static void onPopulateChunk(PopulateChunkEvent.Pre event)
    {
	nexusIsland.generateStructure(event.getWorld(), event.getRand(), new ChunkPos(event.getChunkX(), event.getChunkZ()));
    }
}
