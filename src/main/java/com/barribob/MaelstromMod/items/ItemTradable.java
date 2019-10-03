package com.barribob.MaelstromMod.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTradable extends ItemBase
{
    public ItemTradable(String name, CreativeTabs tab)
    {
	super(name, tab);
    }

    public ItemTradable(String name)
    {
	super(name);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add("Used For Trading");
	super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
