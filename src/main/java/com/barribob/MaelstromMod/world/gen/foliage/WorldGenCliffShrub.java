package com.barribob.MaelstromMod.world.gen.foliage;

import java.util.Arrays;
import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class WorldGenCliffShrub extends WorldGenTrees
{
    private final IBlockState leavesMetadata;
    private final IBlockState woodMetadata;
    private static final float upperLeafChance = 0.9f;

    public WorldGenCliffShrub(IBlockState log, IBlockState leaf)
    {
	super(false);
	this.woodMetadata = log;
	this.leavesMetadata = leaf;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
	// Move the generation until it is at the correct y position
	while (worldIn.getBlockState(position).getBlock() != Blocks.AIR)
	{
	    position = position.up();
	}

	if (position.getY() > 240)
	{
	    return false;
	}

	if (position.getY() > 200 && rand.nextInt(2) == 0)
	{
	    return false;
	}

	if (!worldIn.isAirBlock(position.up(40)))
	{
	    return false;
	}

	IBlockState state = worldIn.getBlockState(position);

	if (state.getBlock() == Blocks.AIR && this.isBlockNearby(worldIn, position))
	{
	    this.setBlockAndNotifyAdequately(worldIn, position, woodMetadata);
	    int maxLeafWidth = 2 + rand.nextInt(2);
	    this.generateLeaves(maxLeafWidth, worldIn, rand, position);
	    this.generateLeaves(maxLeafWidth - 1, worldIn, rand, position.down());
	}
	return true;
    }

    /**
     * Generates leaves in a circular fashion
     * 
     * @param maxLeafWidth
     * @param worldIn
     * @param rand
     * @param position
     */
    private void generateLeaves(int maxLeafWidth, World worldIn, Random rand, BlockPos position)
    {
	for (int x = -maxLeafWidth; x <= maxLeafWidth; ++x)
	{
	    for (int z = -maxLeafWidth; z <= +maxLeafWidth; ++z)
	    {
		int mattDistance = Math.abs(x) + Math.abs(z);
		BlockPos blockpos = new BlockPos(x, 0, z).add(position);

		// Place the main leaf bunch
		if (mattDistance <= maxLeafWidth)
		{
		    placeLeaf(blockpos, worldIn);
		}

		// Place a smaller leaf bunch one block above
		if (mattDistance <= maxLeafWidth - 1 && worldIn.rand.nextFloat() < this.upperLeafChance)
		{
		    placeLeaf(blockpos.up(), worldIn);
		}
	    }
	}
    }

    private void placeLeaf(BlockPos pos, World world)
    {
	IBlockState state = world.getBlockState(pos);

	if (state.getBlock().canBeReplacedByLeaves(state, world, pos))
	{
	    this.setBlockAndNotifyAdequately(world, pos, this.leavesMetadata);
	}
    }

    private boolean isBlockNearby(World world, BlockPos pos)
    {
	for (BlockPos dir : Arrays.asList(pos.down(), pos.east(), pos.west(), pos.north(), pos.south()))
	{
	    if (world.getBlockState(dir).getBlock() == ModBlocks.CLIFF_STONE)
	    {
		return true;
	    }
	}
	return false;
    }
}