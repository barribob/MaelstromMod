package com.barribob.MaelstromMod.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSingleDescription extends ItemBase
{
    private String desc;

    public ItemSingleDescription(String name, String desc, CreativeTabs tab)
    {
	super(name, tab);
	this.desc = desc;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add(desc);
	super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
