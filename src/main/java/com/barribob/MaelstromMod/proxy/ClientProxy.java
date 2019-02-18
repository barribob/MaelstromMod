package com.barribob.MaelstromMod.proxy;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.blocks.BlockLeavesBase;
import com.barribob.MaelstromMod.packets.MessageExtendedReachAttack;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.handlers.RenderHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

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
    
    /**
     * Initializations for client only stuff like rendering
     */
    public void init()
    {
	RenderHandler.registerEntityRenderers();
	super.init();
    }
}
