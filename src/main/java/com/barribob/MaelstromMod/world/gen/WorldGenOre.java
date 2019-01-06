package com.barribob.MaelstromMod.world.gen;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModDimensions;
import com.barribob.MaelstromMod.util.ModRandom;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

/**
 * 
 * Handles all of the world generation for the mod
 *
 */
public class WorldGenOre implements IWorldGenerator
{
    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
	int overworld = 0;
	if (world.provider.getDimension() == overworld)
	{
	    generateOverworld(rand, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
	}
	else if(world.provider.getDimension() == ModDimensions.DIMENSION_AZURE_ID)
	{
	    this.generateAzure(rand, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
	}
    }

    private void generateOverworld(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunckProvider)
    {
    }

    private void generateOverworldOre(IBlockState ore, World world, Random rand, int x, int z, int minY, int maxY, int size, int chances)
    {
	int deltaY = maxY - minY;
	for (int i = 0; i < chances; i++)
	{
	    BlockPos pos = new BlockPos(x + rand.nextInt(16), minY + rand.nextInt(deltaY), z + rand.nextInt(16));

	    WorldGenMinable generator = new WorldGenMinable(ore, size);
	    generator.generate(world, rand, pos);
	}
    }
    
    private void generateAzure(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
	int chunkSize = 16;
	generateAzureOre(ModBlocks.AZURE_COAL_ORE.getDefaultState(), world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(4, 20), 20);
	generateAzureOre(ModBlocks.AZURE_IRON_ORE.getDefaultState(), world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 64, ModRandom.range(3, 9), 20);
	generateAzureOre(ModBlocks.AZURE_GOLD_ORE.getDefaultState(), world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 32, ModRandom.range(3, 9), 3);
	generateAzureOre(ModBlocks.AZURE_REDSTONE_ORE.getDefaultState(), world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 16, ModRandom.range(3, 9), 8);
	generateAzureOre(ModBlocks.AZURE_DIAMOND_ORE.getDefaultState(), world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 16, ModRandom.range(3, 9), 1);
	generateAzureOre(ModBlocks.AZURE_LAPIS_ORE.getDefaultState(), world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 16, ModRandom.range(3, 9), 1);
	generateAzureOre(ModBlocks.AZURE_EMERALD_ORE.getDefaultState(), world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 16, ModRandom.range(2, 6), 1);
    }
    
    private void generateAzureOre(IBlockState ore, World world, Random rand, int x, int z, int minY, int maxY, int size, int chances)
    {
	int deltaY = maxY - minY;
	for (int i = 0; i < chances; i++)
	{
	    BlockPos pos = new BlockPos(x + rand.nextInt(16), minY + rand.nextInt(deltaY), z + rand.nextInt(16));

	    WorldGenAzureMinable generator = new WorldGenAzureMinable(ore, size);
	    generator.generate(world, rand, pos);
	}
    }
}
