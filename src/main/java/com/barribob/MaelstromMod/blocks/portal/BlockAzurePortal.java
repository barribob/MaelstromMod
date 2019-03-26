package com.barribob.MaelstromMod.blocks.portal;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.teleporter.AzureTeleporter;

import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;

/**
 * 
 * The portal block for the azure dimension
 *
 */
public class BlockAzurePortal extends BlockPortal
{
    public BlockAzurePortal(String name)
    {
	super(name, ModBlocks.LIGHT_AZURE_STONE, ModBlocks.AZURE_PORTAL, ModConfig.nexus_dimension_id, ModConfig.fracture_dimension_id);
	this.setBlockUnbreakable();
	this.setLightLevel(0.5f);
	this.setLightOpacity(0);
    }
    
    @Override
    protected Teleporter getTeleporter1(World world)
    {
        return new AzureTeleporter(world.getMinecraftServer().getWorld(ModConfig.nexus_dimension_id));
    }
    
    @Override
    protected Teleporter getTeleporter2(World world)
    {
	return new AzureTeleporter(world.getMinecraftServer().getWorld(ModConfig.fracture_dimension_id));
    }
}