package com.barribob.MaelstromMod.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * 
 * The promo item for the next dimension
 *
 */
public class ItemAzureKey extends ItemBase
{
    public ItemAzureKey(String name, CreativeTabs tab)
    {
	super(name, tab);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.GRAY + "Key to the next dimension, which is coming soon!");
    }
}
