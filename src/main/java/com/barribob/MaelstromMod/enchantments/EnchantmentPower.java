package com.barribob.MaelstromMod.enchantments;

import com.barribob.MaelstromMod.items.gun.ItemGun;
import com.barribob.MaelstromMod.items.gun.ItemStaff;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * 
 * Increases gun damage
 *
 */
public class EnchantmentPower extends Enchantment
{
    public EnchantmentPower(String registryName, Rarity rarityIn, EntityEquipmentSlot[] slots)
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
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }
    
    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    @Override
    public boolean canApplyTogether(Enchantment ench)
    {
        return !(ench instanceof EnchantmentMaelstromDestroyer) && super.canApplyTogether(ench);
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    @Override
    public int getMaxLevel()
    {
        return 5;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
	return stack.getItem() instanceof ItemGun || (stack.getItem() instanceof ItemStaff && ((ItemStaff) stack.getItem()).doesDamage());
    }
}
