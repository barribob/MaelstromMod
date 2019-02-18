package com.barribob.MaelstromMod.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * 
 * Represents azure dimension flowers
 *
 */
public class BlockAzureFlower extends BlockAzureBush
{
    public BlockAzureFlower(String name, Material material, float hardness, float resistance, SoundType soundType)
    {
	super(name, material, hardness, resistance, soundType);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
	return super.getBoundingBox(state, source, pos).offset(state.getOffset(source, pos));
    }
}
