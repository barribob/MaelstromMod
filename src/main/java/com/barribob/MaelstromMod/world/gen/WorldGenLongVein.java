package com.barribob.MaelstromMod.world.gen;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.ModRandom;

import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLongVein extends WorldGenerator
{
    private final IBlockState block;

    public WorldGenLongVein()
    {
	IBlockState stone = Blocks.STONE.getDefaultState();
	this.block = ModRandom.choice(new IBlockState[] { 
		stone, 
		ModBlocks.RED_CLIFF_STONE.getDefaultState(), 
		stone.withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE),
		stone.withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE),
		stone.withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE)});
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos pos)
    {
	int size = 200;
	for (int y = 0; y < size; y++)
	{
	    for (int x = -1; x <= 1; x++)
	    {
		for (int z = -1; z <= 1; z++)
		{
		    BlockPos newPos = new BlockPos(x, -y, z).add(pos);
		    if (worldIn.rand.nextFloat() > 0.25 && worldIn.getBlockState(newPos).getBlock() == ModBlocks.CLIFF_STONE)
		    {
			worldIn.setBlockState(newPos, block);
		    }
		}
	    }
	    if (worldIn.rand.nextFloat() > 0.975)
	    {
		pos = pos.add(new BlockPos(ModRandom.randSign(), 0, 0));
	    }
	    if (worldIn.rand.nextFloat() > 0.975)
	    {
		pos = pos.add(new BlockPos(0, 0, ModRandom.randSign()));
	    }
	}
	return true;
    }
}
