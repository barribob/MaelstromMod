package com.barribob.MaelstromMod.items;

import java.util.List;

import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemKey extends ItemBase
{
    public ItemKey(String name, CreativeTabs tab)
    {
	super(name, tab);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("dimensional_key"));
    }
}
