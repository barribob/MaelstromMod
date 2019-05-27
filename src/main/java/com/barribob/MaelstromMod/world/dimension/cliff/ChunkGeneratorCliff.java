package com.barribob.MaelstromMod.world.dimension.cliff;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.world.dimension.WorldChunkGenerator;
import com.barribob.MaelstromMod.world.dimension.azure_dimension.MapGenAzureRavine;
import com.barribob.MaelstromMod.world.gen.WorldGenMaelstrom;
import com.barribob.MaelstromMod.world.gen.maelstrom_fortress.MapGenMaelstromFortress;
import com.barribob.MaelstromMod.world.gen.maelstrom_stronghold.MapGenMaelstromStronghold;
import com.barribob.MaelstromMod.world.gen.mineshaft.MapGenAzureMineshaft;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.structure.MapGenStructure;

public class ChunkGeneratorCliff extends WorldChunkGenerator
{
    private static final MapGenStructure[] structures = {};

    public ChunkGeneratorCliff(World worldIn, long seed, boolean mapFeaturesEnabledIn, String generatorOptions)
    {
	super(worldIn, seed, mapFeaturesEnabledIn, generatorOptions, ModBlocks.CLIFF_STONE, Blocks.WATER, structures);
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