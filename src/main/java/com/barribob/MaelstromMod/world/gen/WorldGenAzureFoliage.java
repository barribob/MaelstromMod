package com.barribob.MaelstromMod.world.gen;

import java.util.Random;

import com.barribob.MaelstromMod.blocks.BlockAzureBush;
import com.barribob.MaelstromMod.blocks.BlockAzureTallGrass;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * 
 * generates foliage of type BlockAzureBush into the azure dimension
 *
 */
public class WorldGenAzureFoliage extends WorldGenerator
{
    private final BlockAzureBush[] foliage;
    private final int amount;

    public WorldGenAzureFoliage(BlockAzureBush[] tallGrass, int amount)
    {
    	this.foliage = tallGrass;
    	this.amount = amount;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
    	if(foliage.length <= 0) return false;
        for (IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getBlock().isAir(iblockstate, worldIn, position) || iblockstate.getBlock().isLeaves(iblockstate, worldIn, position)) && position.getY() > 0; iblockstate = worldIn.getBlockState(position))
        {
            position = position.down();
        }

        for (int i = 0; i < this.amount; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            BlockAzureBush randomFoliage = foliage[rand.nextInt(foliage.length)];
            
            if (worldIn.isAirBlock(blockpos) && randomFoliage.canBlockStay(worldIn, blockpos, randomFoliage.getDefaultState()))
            {
                worldIn.setBlockState(blockpos, randomFoliage.getDefaultState(), 2);
            }
        }

        return true;
    }
}