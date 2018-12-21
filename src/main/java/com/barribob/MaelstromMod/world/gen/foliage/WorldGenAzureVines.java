package com.barribob.MaelstromMod.world.gen.foliage;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenAzureVines extends WorldGenerator
{
	protected Block vine = ModBlocks.AZURE_VINES;
	
	// Represents the eight directions to check for the vine to travel
	protected BlockPos[] directions = {new BlockPos(1, 0, 0), new BlockPos(1, 0, 1), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 1), 
			new BlockPos(-1, 0, 0), new BlockPos(-1, 0, -1), new BlockPos(0, 0, -1), new BlockPos(1, 0, -1)};
	
	private int minVineLength = 4;
	private int maxVineLength = 6;
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) 
	{
		// Can only generate if the position is air, and there is an anchor
		while(worldIn.getBlockState(position).getBlock() != Blocks.AIR || !canGenerateVines(worldIn, position))
		{
			position = position.down();
			if(position.getY() < 10)
			{
				return false;
			}
		}
		// Try to find a valid direction for the vines to generate in
		for(BlockPos direction : directions)
		{
			int vineLength = getVineLength(worldIn, position, direction);
			if(vineLength >= minVineLength && vineLength < maxVineLength)
			{
				// Generate the vines in a straight line
				for(int i = 0; i < vineLength; i++)
				{
					if(i == 0 || i == vineLength - 1)
					{
						worldIn.setBlockState(position, ModBlocks.AZURE_VINES.getDefaultState());
					}
					else
					{
						// Add some sag to the vines
						worldIn.setBlockState(position.down(), ModBlocks.AZURE_VINES.getDefaultState());
					}
					
					position = position.add(direction);
				}

				return true;
			}
		}
		return false;
	}
	
	// Checks to see if there is air space between to generate the vines
	public int getVineLength(World worldIn, BlockPos position, BlockPos direction)
	{
		int emptySpace = 0;
		BlockPos newPos = position;
		while(worldIn.getBlockState(newPos).getBlock() == Blocks.AIR && worldIn.getBlockState(newPos.down()).getBlock() == Blocks.AIR)
		{
			emptySpace += 1;
			
			// If the space is too far apart, return failure
			if(emptySpace >= maxVineLength)
			{
				return -1;
			}
			
			newPos = newPos.add(direction);
		}
		
		return emptySpace;
	}
	
	/**
	 * If there is an anchor (i.e. a surrounding block is stone) then return true
	 * @param worldIn
	 * @param position
	 * @return
	 */
	public boolean canGenerateVines(World worldIn, BlockPos position)
	{
		boolean foundAnchor = false;
		for(BlockPos direction : directions)
		{
			if(worldIn.getBlockState(direction.add(position)).getBlock() == ModBlocks.DARK_AZURE_STONE)
			{
				foundAnchor = true;
			}
		}
		
		return foundAnchor;
	}
}
