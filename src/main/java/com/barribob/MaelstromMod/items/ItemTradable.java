package com.barribob.MaelstromMod.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemTradable extends ItemBase {
    public ItemTradable(String name, CreativeTabs tab) {
        super(name, tab);
    }

    public ItemTradable(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Used For Trading");
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
