package com.barribob.MaelstromMod.world.gen.foliage;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenWaterfall extends WorldGenerator
{
    private static Block stoneBlock;

    public WorldGenWaterfall(Block stoneBlock)
    {
	super(false);
	this.stoneBlock = stoneBlock;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
	while (!this.isAirNearby(worldIn, position))
	{
	    position = position.up();
	}

	if (worldIn.getBlockState(position.up()).getBlock().equals(stoneBlock) && worldIn.getBlockState(position.down()).getBlock().equals(stoneBlock))
	{
	    this.setBlockAndNotifyAdequately(worldIn, position, Blocks.WATER.getDefaultState());
	    worldIn.neighborChanged(position, Blocks.WATER, position);
	}
	return true;
    }

    private boolean isAirNearby(World world, BlockPos pos)
    {
	for (BlockPos dir : Arrays.asList(pos.east(), pos.west(), pos.north(), pos.south()))
	{
	    if (world.getBlockState(dir).getBlock() == Blocks.AIR)
	    {
		return true;
	    }
	}
	return false;
    }
}