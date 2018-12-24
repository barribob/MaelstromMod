package com.barribob.MaelstromMod.items;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * 
 * The base class for new mod items
 *
 */
public class ItemBase extends Item implements IHasModel
{
    public ItemBase(String name, CreativeTabs tab)
    {
	setUnlocalizedName(name);
	setRegistryName(name);
	if (tab != null)
	{
	    setCreativeTab(CreativeTabs.MATERIALS);
	}

	ModItems.ITEMS.add(this);
    }

    public ItemBase(String name)
    {
	this(name, CreativeTabs.MATERIALS);
    }

    @Override
    public void registerModels()
    {
	Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
