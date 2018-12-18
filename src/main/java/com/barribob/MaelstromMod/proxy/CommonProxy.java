package com.barribob.MaelstromMod.proxy;

import com.barribob.MaelstromMod.blocks.BlockLeavesBase;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;

public class CommonProxy 
{
	public void registerItemRenderer(Item item, int meta, String id) {}
	public void setFancyGraphics(BlockLeavesBase block, boolean isFancy) {}
	public void setCustomState(Block block, IStateMapper mapper) {}
}
