package com.barribob.MaelstromMod.blocks.portal;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.teleporter.NexusToOverworldTeleporter;
import com.barribob.MaelstromMod.util.teleporter.OverworldToNexusTeleporter;

import net.minecraft.init.Blocks;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;

/**
 * 
 * The portal block for the nexus dimension
 *
 */
public class BlockNexusPortal extends BlockPortal
{
    public BlockNexusPortal(String name)
    {
	super(name, Blocks.QUARTZ_BLOCK, ModBlocks.NEXUS_PORTAL, 0, ModConfig.nexus_dimension_id);
	this.setBlockUnbreakable();
	this.setLightLevel(0.5f);
	this.setLightOpacity(0);
    }
    
    @Override
    protected Teleporter getTeleporter1(World world)
    {
        return new NexusToOverworldTeleporter(world.getMinecraftServer().getWorld(0));
    }
    
    @Override
    protected Teleporter getTeleporter2(World world)
    {
	return new OverworldToNexusTeleporter(world.getMinecraftServer().getWorld(ModConfig.nexus_dimension_id));
    }
}