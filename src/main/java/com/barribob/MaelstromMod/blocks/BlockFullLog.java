package com.barribob.MaelstromMod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

/*
 * Log for a log textured only with bark
 */
public class BlockFullLog extends BlockLogBase {
    private Block log;

    public BlockFullLog(String name, Block regularLog) {
        super(name);
        this.log = regularLog;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(log);
    }
}
