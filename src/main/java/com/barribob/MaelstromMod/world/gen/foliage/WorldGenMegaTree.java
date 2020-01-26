package com.barribob.MaelstromMod.world.gen.foliage;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;

public class WorldGenMegaTree extends WorldGenHugeTrees
{
    IBlockState fruitBlock;

    public WorldGenMegaTree(boolean notify, int baseHeightIn, int extraRandomHeightIn, IBlockState woodMetadataIn, IBlockState leavesBlock, IBlockState fruitBlock)
    {
	super(notify, baseHeightIn, extraRandomHeightIn, woodMetadataIn, leavesBlock);
	this.fruitBlock = fruitBlock;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
	int i = this.getHeight(rand);

	if (!this.ensureGrowable(worldIn, rand, position, i))
	{
	    return false;
	}
	else
	{
	    this.createCrown(worldIn, position.up(i), 2);
	    this.setBlockAndNotifyAdequately(worldIn, position.up(i - 2).west(), this.woodMetadata);
	    this.setBlockAndNotifyAdequately(worldIn, position.up(i - 2).north().east(), this.woodMetadata);
	    this.setBlockAndNotifyAdequately(worldIn, position.up(i - 2).east().east().south(), this.woodMetadata);
	    this.setBlockAndNotifyAdequately(worldIn, position.up(i - 2).south().south(), this.woodMetadata);
	    this.setBlockAndNotifyAdequately(worldIn, position.up(i - 2).west().west(), this.woodMetadata);
	    this.setBlockAndNotifyAdequately(worldIn, position.up(i - 2).north().east().north(), this.woodMetadata);
	    this.setBlockAndNotifyAdequately(worldIn, position.up(i - 2).east().east().east().south(), this.woodMetadata);
	    this.setBlockAndNotifyAdequately(worldIn, position.up(i - 2).south().south().south(), this.woodMetadata);

	    for (int j = position.getY() + i - 2 - rand.nextInt(4); j > position.getY() + i / 3; j -= 3 + rand.nextInt(3))
	    {
		float f = rand.nextFloat() * ((float) Math.PI * 2F);
		int k = position.getX() + (int) (0.5F + MathHelper.cos(f) * 4.0F);
		int l = position.getZ() + (int) (0.5F + MathHelper.sin(f) * 4.0F);

		for (int i1 = 0; i1 < 5; ++i1)
		{
		    k = position.getX() + (int) (1.5F + MathHelper.cos(f) * i1);
		    l = position.getZ() + (int) (1.5F + MathHelper.sin(f) * i1);
		    this.setBlockAndNotifyAdequately(worldIn, new BlockPos(k, j - 3 + i1 / 2, l), this.woodMetadata);
		}

		int j2 = 2 + rand.nextInt(1);
		int j1 = j;

		for (int k1 = j - j2; k1 <= j1; ++k1)
		{
		    int l1 = k1 - j1;
		    this.growLeavesLayer(worldIn, new BlockPos(k, k1, l), 1 - l1);
		}
	    }

	    for (int i2 = 0; i2 < i; ++i2)
	    {
		BlockPos blockpos = position.up(i2);

		if (this.isAirLeaves(worldIn, blockpos))
		{
		    this.setBlockAndNotifyAdequately(worldIn, blockpos, this.woodMetadata);
		}

		if (i2 < i - 1)
		{
		    BlockPos blockpos1 = blockpos.east();

		    if (this.isAirLeaves(worldIn, blockpos1))
		    {
			this.setBlockAndNotifyAdequately(worldIn, blockpos1, this.woodMetadata);
		    }
		}

		BlockPos blockpos2 = blockpos.south().east();

		if (this.isAirLeaves(worldIn, blockpos2))
		{
		    this.setBlockAndNotifyAdequately(worldIn, blockpos2, this.woodMetadata);
		}

		BlockPos blockpos3 = blockpos.south();

		if (this.isAirLeaves(worldIn, blockpos3))
		{
		    this.setBlockAndNotifyAdequately(worldIn, blockpos3, this.woodMetadata);
		}
	    }
	}

	return true;
    }

    @Override
    protected void growLeavesLayer(World worldIn, BlockPos layerCenter, int width)
    {
	int i = width * width;

	for (int j = -width; j <= width; ++j)
	{
	    for (int k = -width; k <= width; ++k)
	    {
		if (j * j + k * k <= i)
		{
		    BlockPos blockpos = layerCenter.add(j, 0, k);
		    IBlockState state = worldIn.getBlockState(blockpos);

		    if (state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos))
		    {
			this.setBlockAndNotifyAdequately(worldIn, blockpos, worldIn.rand.nextInt(20) == 0 ? this.fruitBlock : this.leavesMetadata);
		    }
		}
	    }
	}
    }

    private void createCrown(World worldIn, BlockPos p_175930_2_, int p_175930_3_)
    {
	for (int j = -3; j <= 0; ++j)
	{
	    this.growLeavesLayerStrict(worldIn, p_175930_2_.up(j), p_175930_3_ - j);
	}
    }

    // Helper macro
    private boolean isAirLeaves(World world, BlockPos pos)
    {
	IBlockState state = world.getBlockState(pos);
	return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos);
    }
}