package com.barribob.MaelstromMod.gui;

import java.util.function.Supplier;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MaelstromCreativeTab extends CreativeTabs
{
    Supplier<Item> icon;

    public MaelstromCreativeTab(int index, String label, Supplier<Item> icon)
    {
	super(index, label);
	this.icon = icon;
    }

    @Override
    public ItemStack getTabIconItem()
    {
	return new ItemStack(icon.get());
    }
}
