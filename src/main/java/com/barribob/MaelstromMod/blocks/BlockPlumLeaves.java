package com.barribob.MaelstromMod.blocks;

import com.barribob.MaelstromMod.init.ModBlocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockPlumLeaves extends BlockLeavesBase {
    public BlockPlumLeaves(String name, float hardness, float resistance, SoundType soundType) {
        super(name, hardness, resistance, soundType);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.PLUM_SAPLING);
    }
}
