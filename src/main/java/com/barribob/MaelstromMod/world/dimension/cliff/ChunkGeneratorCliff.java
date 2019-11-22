package com.barribob.MaelstromMod.world.dimension.cliff;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.world.dimension.WorldChunkGenerator;
import com.barribob.MaelstromMod.world.gen.WorldGenLongVein;
import com.barribob.MaelstromMod.world.gen.cliff.MapGenHoleTemple;
import com.barribob.MaelstromMod.world.gen.golden_ruins.MapGenGoldenRuins;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.MapGenStructure;

public class ChunkGeneratorCliff extends WorldChunkGenerator
{
    public static final int STRUCTURE_SPACING_CHUNKS = 25;
    public static final int GOLDEN_RUINS_NUMBER = 0;

    public ChunkGeneratorCliff(World worldIn, long seed, boolean mapFeaturesEnabledIn, String generatorOptions)
    {
	super(worldIn, seed, mapFeaturesEnabledIn, generatorOptions, ModBlocks.CLIFF_STONE, Blocks.WATER, null);
	MapGenStructure[] structures = { new MapGenGoldenRuins(STRUCTURE_SPACING_CHUNKS, GOLDEN_RUINS_NUMBER, 1, this),
		new MapGenHoleTemple(STRUCTURE_SPACING_CHUNKS, 10, 1, this) };
	this.structures = structures;
    }

    @Override
    protected void generateCaves(int x, int z, ChunkPrimer cp)
    {

    }

    @Override
    protected void generateFeatures(BlockPos pos, Biome biome)
    {
	WorldGenLongVein gen = new WorldGenLongVein();
	int x1 = rand.nextInt(8) + 16;
	int y = 256;
	int z1 = rand.nextInt(8) + 16;
	gen.generate(this.world, rand, pos.add(x1, y, z1));
    }
}