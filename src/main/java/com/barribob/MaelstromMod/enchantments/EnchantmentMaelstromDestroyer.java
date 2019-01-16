package com.barribob.MaelstromMod.enchantments;

import com.barribob.MaelstromMod.items.gun.ItemGun;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * 
 * An enchantment on guns that deals increased damage to maelstrom mobs
 *
 */
public class EnchantmentMaelstromDestroyer extends Enchantment
{
    public EnchantmentMaelstromDestroyer(String registryName, Rarity rarityIn, EntityEquipmentSlot[] slots)
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
        return 5 + (enchantmentLevel - 1) * 8;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 20;
    }
    
    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean canApplyTogether(Enchantment ench)
    {
        return !(ench instanceof EnchantmentPower) && super.canApplyTogether(ench);
    }
    
    @Override
    public int calcModifierDamage(int level, DamageSource source)
    {
        return super.calcModifierDamage(level, source);
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 5;
    }
    
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
	return stack.getItem() instanceof ItemGun;
    }
}
