package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.items.armor.ModArmorBase;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

/**
 * 
 * Get the maelstrom armor from a player
 *
 */
public class ArmorHandler
{
    /**
     * Get the total maelstrom armor of an entity
     */
    public static int getMaelstromArmor(Entity entity)
    {
	int totalArmor = 0;

	for (ItemStack equipment : entity.getArmorInventoryList())
	{
	    if (equipment.getItem() instanceof ModArmorBase)
	    {
		totalArmor += ((ModArmorBase) equipment.getItem()).getMaelstromArmor();
	    }
	}

	return totalArmor;
    }
}
