package com.barribob.MaelstromMod.blocks.portal;

import java.util.List;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.teleporter.ToDarkNexusTeleporter;
import com.barribob.MaelstromMod.util.teleporter.ToNexusTeleporter;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;

public class BlockDarkNexusPortal extends BlockPortal
{
    public BlockDarkNexusPortal(String name)
    {
	super(name, ModConfig.world.dark_nexus_dimension_id, ModConfig.world.nexus_dimension_id);
	this.setBlockUnbreakable();
	this.setLightLevel(0.5f);
	this.setLightOpacity(0);
    }
    
    @Override
    protected Teleporter getEntranceTeleporter(World world)
    {
	return new ToDarkNexusTeleporter(world.getMinecraftServer().getWorld(ModConfig.world.dark_nexus_dimension_id), new BlockPos(24, 64, 24));
    }
    
    @Override
    protected Teleporter getExitTeleporter(World world)
    {
	return new ToNexusTeleporter(world.getMinecraftServer().getWorld(ModConfig.world.nexus_dimension_id), new BlockPos(189, 103, 40));
    }
    
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
	tooltip.add(ModUtils.translateDesc("nexus_only_portal"));
	super.addInformation(stack, player, tooltip, advanced);
    }
}