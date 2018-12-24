package com.barribob.MaelstromMod.proxy;

import com.barribob.MaelstromMod.blocks.BlockLeavesBase;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @Override
    public void setFancyGraphics(BlockLeavesBase block, boolean isFancy)
    {
	block.setFancyGraphics(isFancy);
    }

    @Override
    public void setCustomState(Block block, IStateMapper mapper)
    {
	ModelLoader.setCustomStateMapper(block, mapper);
    }
}
