package com.barribob.MaelstromMod.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockLamp extends BlockBase
{
    public BlockLamp(String name, Material material, float hardness, float resistance, SoundType soundType)
    {
	super(name, material, hardness, resistance, soundType);
    }

    // Make not opaque to remove common lighting errors
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
	return false;
    }
}
