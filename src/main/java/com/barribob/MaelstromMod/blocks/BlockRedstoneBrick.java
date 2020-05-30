package com.barribob.MaelstromMod.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRedstoneBrick extends BlockBase
{
    public BlockRedstoneBrick(String name, Material material, float hardness, float resistance, SoundType soundType)
    {
	super(name, material, hardness, resistance, soundType);
    }

    @Override
    public boolean canProvidePower(net.minecraft.block.state.IBlockState state)
    {
	return true;
    };

    @Override
    public int getWeakPower(net.minecraft.block.state.IBlockState blockState, net.minecraft.world.IBlockAccess blockAccess, net.minecraft.util.math.BlockPos pos, net.minecraft.util.EnumFacing side)
    {
	return 15;
    };

}
