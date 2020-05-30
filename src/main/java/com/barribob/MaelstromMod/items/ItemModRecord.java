package com.barribob.MaelstromMod.items;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.init.ModCreativeTabs;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.IHasModel;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;

public class ItemModRecord extends ItemRecord implements IHasModel {
    public ItemModRecord(String name, SoundEvent event) {
	super(name, event);
	setUnlocalizedName(name);
	setRegistryName(name);
	setCreativeTab(ModCreativeTabs.ITEMS);

	ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
	Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
