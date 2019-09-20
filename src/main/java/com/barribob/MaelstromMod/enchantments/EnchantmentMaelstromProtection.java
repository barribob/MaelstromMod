package com.barribob.MaelstromMod.enchantments;

import com.barribob.MaelstromMod.items.armor.ModArmorBase;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * 
 * The maelstrom armor enchantment. Actual functionality implemented in ModEventHandler and Armor Handler
 *
 */
public class EnchantmentMaelstromProtection extends Enchantment
{
    public EnchantmentMaelstromProtection(String registryName, Rarity rarityIn, EntityEquipmentSlot[] slots)
    {
	// The enum enchantment type doesn't matter here
	super(rarityIn, EnumEnchantmentType.ALL, slots);
	this.setRegistryName(registryName);
	this.setName(registryName);
    }
    
    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 1 + (enchantmentLevel - 1) * 10;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 10;
    }
    
    /**
     * Returns the maximum level that the enchantment can have.
     */
    @Override
    public int getMaxLevel()
    {
	return 4;
    }
    
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
	return stack.getItem() instanceof ModArmorBase;
    }
}
