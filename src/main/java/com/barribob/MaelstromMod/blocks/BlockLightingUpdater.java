package com.barribob.MaelstromMod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * A block desined to update lighting in a seamless manner
 */
public class BlockLightingUpdater extends BlockBase
{
    public BlockLightingUpdater(String name, Material material)
    {
	super(name, material);
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
    }
    
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
    
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid)
    {
        return false;
    }
    
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }
    
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
