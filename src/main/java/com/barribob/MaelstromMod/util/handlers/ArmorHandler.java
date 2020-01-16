package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.items.armor.ModArmorBase;
import com.barribob.MaelstromMod.util.Element;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

/**
 * 
 * Get the maelstrom armor from a player
 *
 */
public class ArmorHandler
{
    public static float getElementalArmor(Entity entity, Element element)
    {
	float totalArmor = 0;

	for (ItemStack equipment : entity.getArmorInventoryList())
	{
	    if (equipment.getItem() instanceof ModArmorBase)
	    {
		totalArmor += ((ModArmorBase) equipment.getItem()).getElementalArmor(element);
	    }
	}

	return totalArmor;
    }

    /**
     * Get the total maelstrom armor of an entity
     */
    public static float getMaelstromArmor(Entity entity)
    {
	float totalArmor = 0;

	for (ItemStack equipment : entity.getArmorInventoryList())
	{
	    if (equipment.getItem() instanceof ModArmorBase)
	    {
		totalArmor += ((ModArmorBase) equipment.getItem()).getMaelstromArmor(equipment);
	    }
	}

	return totalArmor;
    }
    
    /*
     * Returns the exponential armor factor, for example 0.5 would be 2, and 0.75 would be 4
     * For gui display
     */
    public static int getMaelstromArmorBars(Entity entity)
    {
	double totalArmor = 0;

	for (ItemStack equipment : entity.getArmorInventoryList())
	{
	    if (equipment.getItem() instanceof ModArmorBase)
	    {
		totalArmor += ((ModArmorBase) equipment.getItem()).getMaelstromArmorBars();
	    }
	}
	
	return (int) Math.round(totalArmor * 2);
    }
}
