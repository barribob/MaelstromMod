package com.barribob.MaelstromMod.blocks;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModCreativeTabs;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAzureGrass extends BlockBase implements IGrowable
{

    public BlockAzureGrass(String name, Material material, float hardness, float resistance, SoundType soundType)
    {
	super(name, material, hardness, resistance, soundType);
	this.setTickRandomly(true);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
	return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
	return true;
    }

    /**
     * Grow azure foliage when bonemeall is used
     */
    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
	BlockPos blockpos = pos.up();

	for (int i = 0; i < 128; ++i)
	{
	    BlockPos blockpos1 = blockpos;
	    int j = 0;

	    while (true)
	    {
		if (j >= i / 16)
		{
		    if (worldIn.isAirBlock(blockpos1))
		    {
			if (rand.nextInt(8) == 0)
			{
			    worldIn.getBiome(blockpos1).plantFlower(worldIn, rand, blockpos1);
			}
			else
			{
			    if (ModBlocks.BROWNED_GRASS.canPlaceBlockAt(worldIn, blockpos1))
			    {
				worldIn.setBlockState(blockpos1, ModBlocks.BROWNED_GRASS.getDefaultState(), 3);
			    }
			}
		    }

		    break;
		}

		blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

		if (worldIn.getBlockState(blockpos1.down()).getBlock() != ModBlocks.AZURE_GRASS || worldIn.getBlockState(blockpos1).isNormalCube())
		{
		    break;
		}

		++j;
	    }
	}
    }

    /*
     * Makes grass spread to other blocks
     */
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
	if (!worldIn.isRemote)
	{
	    if (!worldIn.isAreaLoaded(pos, 3))
		return; // Forge: prevent loading unloaded chunks when checking neighbor's light and
			// spreading
	    if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2)
	    {
		worldIn.setBlockState(pos, ModBlocks.DARK_AZURE_STONE.getDefaultState());
	    }
	    else
	    {
		if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
		{
		    for (int i = 0; i < 4; ++i)
		    {
			BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

			if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos))
			{
			    return;
			}

			IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
			IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

			if (iblockstate1.getBlock() == ModBlocks.DARK_AZURE_STONE && worldIn.getLightFromNeighbors(blockpos.up()) >= 4
				&& iblockstate.getLightOpacity(worldIn, pos.up()) <= 2)
			{
			    worldIn.setBlockState(blockpos, ModBlocks.AZURE_GRASS.getDefaultState());
			}
		    }
		}
	    }
	}
    }

}
