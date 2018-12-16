package com.barribob.MaelstromMod.world;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;

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
public class ModWorldGen implements IWorldGenerator
{
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		int overworld = 0;
		if(world.provider.getDimension() == overworld) {
			generateOverworld(rand, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}
	
	private void generateOverworld(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunckProvider) 
	{
		int chunkSize = 16;
		generateOre(ModBlocks.ORE.getDefaultState(), world, rand, chunkX * chunkSize, chunkZ * chunkSize, 16, 64, rand.nextInt(4) + 4, 20);
	}
	
	private void generateOre(IBlockState ore, World world, Random rand, int x, int z, int minY, int maxY, int size, int chances) 
	{
		int deltaY = maxY - minY;
		for(int i = 0; i < chances; i++) 
		{
			BlockPos pos = new BlockPos(x + rand.nextInt(16), minY + rand.nextInt(deltaY), z + rand.nextInt(16));
			
			WorldGenMinable generator = new WorldGenMinable(ore, size);
			generator.generate(world, rand, pos);
		}
	}
}
