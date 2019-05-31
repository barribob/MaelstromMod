package com.barribob.MaelstromMod.blocks;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * 
 * Represents a single-block-tall grass block
 *
 */
public class BlockAzureTallGrass extends BlockModBush
{
    public BlockAzureTallGrass(String name, Material material, float hardness, float resistance, SoundType soundType)
    {
	super(name, material, ModBlocks.AZURE_GRASS, hardness, resistance, soundType);
    }

    protected static final AxisAlignedBB TALL_GRASS_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D,
	    0.8999999761581421D);

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
	return TALL_GRASS_AABB;
    }

    /**
     * Whether this Block can be replaced directly by other blocks (true for e.g.
     * tall grass)
     */
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
	return true;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
	return null;
    }
}
