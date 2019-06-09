package com.barribob.MaelstromMod.world.gen.foliage;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

/*
 * Generates vines of shorter length
 */
public class WorldGenSwampVines extends WorldGenerator
{
    private static final int maxVineLength = 20;

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
	int y = 0;
	boolean placed = true;
	while (y < this.maxVineLength && worldIn.isAirBlock(position.add(new BlockPos(0, y, 0))) && placed)
	{
	    placed = false;
	    for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.facings())
	    {
		if (Blocks.VINE.canPlaceBlockOnSide(worldIn, position.add(new BlockPos(0, y, 0)), enumfacing))
		{
		    IBlockState iblockstate = Blocks.VINE.getDefaultState()
			    .withProperty(BlockVine.SOUTH, Boolean.valueOf(enumfacing == EnumFacing.NORTH))
			    .withProperty(BlockVine.WEST, Boolean.valueOf(enumfacing == EnumFacing.EAST))
			    .withProperty(BlockVine.NORTH, Boolean.valueOf(enumfacing == EnumFacing.SOUTH))
			    .withProperty(BlockVine.EAST, Boolean.valueOf(enumfacing == EnumFacing.WEST));
		    worldIn.setBlockState(position.add(new BlockPos(0, y, 0)), iblockstate, 2);
		    placed = true;
		    break;
		}
	    }
	    y++;
	}

	return true;
    }
}