package com.barribob.MaelstromMod.world.gen.cliff;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenSmallLedge extends WorldGenCliffLedge
{
    public WorldGenSmallLedge(String name, int yOffset)
    {
	super(name, yOffset);
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand)
    {
	if (rand.nextInt(5) == 0)
	{
	    worldIn.setBlockToAir(pos);
	}
	else if (rand.nextInt(5) == 0)
	{
	    worldIn.setBlockState(pos, ModBlocks.CRACKED_SWAMP_BRICK.getDefaultState());
	}
	else
	{
	    worldIn.setBlockState(pos, ModBlocks.SWAMP_BRICK.getDefaultState());
	}
    }
}
