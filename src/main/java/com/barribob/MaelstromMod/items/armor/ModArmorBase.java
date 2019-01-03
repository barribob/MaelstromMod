package com.barribob.MaelstromMod.items.armor;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

/**
 * 
 * The base for armor for the mod
 *
 */
public class ModArmorBase extends ItemArmor implements IHasModel
{
    private int maelstrom_armor;
    
    public ModArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, int maelstrom_armor)
    {
	super(materialIn, renderIndexIn, equipmentSlotIn);
	setUnlocalizedName(name);
	setRegistryName(name);
	setCreativeTab(CreativeTabs.COMBAT);
	this.maelstrom_armor = maelstrom_armor;

	ModItems.ITEMS.add(this);
    }
    
    public int getMaelstromArmor()
    {
	return this.maelstrom_armor;
    }

    @Override
    public void registerModels()
    {
	Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
