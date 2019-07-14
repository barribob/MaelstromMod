package com.barribob.MaelstromMod.items;

import java.util.List;

import com.barribob.MaelstromMod.entity.util.EntityNexusParticleSpawner;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.world.gen.nexus.MapGenNexusEntrance;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ItemNexusIslandBuilder extends ItemBase
{
    public ItemNexusIslandBuilder(String name, CreativeTabs tab)
    {
	super(name, tab);
	this.setMaxStackSize(1);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
	EntityNexusParticleSpawner entity = new EntityNexusParticleSpawner(worldIn);
	entity.copyLocationAndAnglesFrom(playerIn);
	worldIn.spawnEntity(entity);
	WorldServer worldServer = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
	PlacementSettings settings = new PlacementSettings().setChunk(null).setIgnoreEntities(false).setIgnoreStructureBlock(false).setMirror(Mirror.NONE).setRotation(Rotation.NONE);
	MinecraftServer mcServer = worldIn.getMinecraftServer();
	TemplateManager manager = worldServer.getStructureTemplateManager();
	ResourceLocation location = new ResourceLocation(Reference.MOD_ID, "nexus/nexus_entrance_island");
	Template template = manager.get(mcServer, location);
	BlockPos pos = new BlockPos(playerIn.posX - 15, MapGenNexusEntrance.NEXUS_ISLAND_SPAWN_HEIGHT, playerIn.posZ - 15);
	if (template != null)
	{
	    template.addBlocksToWorld(worldIn, pos, settings, 18);
	}
	else
	{
	    System.out.println("The template, " + location + " could not be loaded");
	}
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!playerIn.capabilities.isCreativeMode)
        {
            itemstack.shrink(1);
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.GRAY + "Right-clicking builds a large island at y=100 that has a portal to the nexus dimension.");
        tooltip.add(TextFormatting.RED + "Lag warning!");
    }
}
