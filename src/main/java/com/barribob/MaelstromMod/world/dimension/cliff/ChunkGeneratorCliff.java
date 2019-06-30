package com.barribob.MaelstromMod.world.dimension.cliff;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.world.dimension.WorldChunkGenerator;
import com.barribob.MaelstromMod.world.gen.golden_ruins.MapGenGoldenRuins;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.MapGenStructure;

public class ChunkGeneratorCliff extends WorldChunkGenerator
{
    private static final int STRUCTURE_SPACING_CHUNKS = 40;
    private static final int GOLDEN_RUINS_NUMBER = 0;

    public ChunkGeneratorCliff(World worldIn, long seed, boolean mapFeaturesEnabledIn, String generatorOptions)
    {
	super(worldIn, seed, mapFeaturesEnabledIn, generatorOptions, ModBlocks.CLIFF_STONE, Blocks.WATER, null);
	MapGenStructure[] structures = { new MapGenGoldenRuins(STRUCTURE_SPACING_CHUNKS, GOLDEN_RUINS_NUMBER, 1, this) };
	this.structures = structures;
    }

    @Override
    protected void generateCaves(int x, int z, ChunkPrimer cp)
    {

    }

    @Override
    protected void generateFeatures(BlockPos pos, Biome biome)
    {

    }
}