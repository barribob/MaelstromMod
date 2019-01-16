package com.barribob.MaelstromMod.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.inventory.EntityEquipmentSlot;

/**
 * 
 * Added the flame enchantment to guns
 *
 */
public class EnchantmentFlame extends Enchantment
{
    public EnchantmentFlame(String registryName, Rarity rarityIn, EntityEquipmentSlot[] slots)
    {
	// The enum enchantment type doesn't matter here
	super(rarityIn, EnumEnchantmentType.ALL, slots);
	this.setRegistryName(registryName);
	this.setName(registryName);
    }
    
    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 20;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 1;
    }
}
