package com.barribob.MaelstromMod.proxy;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.blocks.BlockLeavesBase;
import com.barribob.MaelstromMod.packets.MessageExtendedReachAttack;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy
{
    public void registerItemRenderer(Item item, int meta, String id)
    {
    }

    public void setFancyGraphics(BlockLeavesBase block, boolean isFancy)
    {
    }

    public void setCustomState(Block block, IStateMapper mapper)
    {
    }

    public void init()
    {
    }
}
