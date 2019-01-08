package com.barribob.MaelstromMod.items.tools;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.init.ModCreativeTabs;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class ToolSword extends ItemSword implements IHasModel
{
    public ToolSword(String name, ToolMaterial material)
    {
	super(material);
	setUnlocalizedName(name);
	setRegistryName(name);
	setCreativeTab(ModCreativeTabs.ALL);

	ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
	Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
