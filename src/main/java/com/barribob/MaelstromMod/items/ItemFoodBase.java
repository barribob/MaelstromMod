package com.barribob.MaelstromMod.items;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemFoodBase extends ItemFood implements IHasModel
{
    public ItemFoodBase(String name, CreativeTabs tab, int amount, float saturation, boolean isWolfFood)
    {
	super(amount, saturation, isWolfFood);
	setUnlocalizedName(name);
	setRegistryName(name);
	if (tab != null)
	{
	    setCreativeTab(tab);
	}

	ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
	Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
