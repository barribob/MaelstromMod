package com.barribob.MaelstromMod.enchantments;

import com.barribob.MaelstromMod.items.gun.ItemGun;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * 
 * The punch enchantment for ItemGun
 *
 */
public class EnchantmentImpact extends Enchantment
{
    public EnchantmentImpact(String registryName, Rarity rarityIn, EntityEquipmentSlot[] slots)
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
        return 12 + (enchantmentLevel - 1) * 20;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 25;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 2;
    }
    
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
	return stack.getItem() instanceof ItemGun;
    }
}
