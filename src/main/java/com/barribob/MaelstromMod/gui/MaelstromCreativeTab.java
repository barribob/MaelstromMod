package com.barribob.MaelstromMod.gui;

import com.barribob.MaelstromMod.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class MaelstromCreativeTab extends CreativeTabs
{
    public MaelstromCreativeTab(int index, String label)
    {
	super(index, label);
    }

    @Override
    public ItemStack getTabIconItem()
    {
	return new ItemStack(ModItems.AZURE_KEY);
    }
}
