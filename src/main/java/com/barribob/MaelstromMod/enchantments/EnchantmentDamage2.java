package com.barribob.MaelstromMod.enchantments;

import com.barribob.MaelstromMod.items.tools.ToolSword;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentDamage2 extends Enchantment
{
    public EnchantmentDamage2(String registryName, Rarity rarityIn, EntityEquipmentSlot[] slots)
    {
	super(rarityIn, EnumEnchantmentType.WEAPON, slots);
	this.setRegistryName(registryName);
	this.setName(registryName);
    }

    @Override
    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType)
    {
	return 0;
    }

    @Override
    public boolean canApply(ItemStack stack)
    {
	return canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
	return stack.getItem() instanceof ToolSword;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
	return 1 + (enchantmentLevel - 1) * 10;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
	return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    @Override
    public int getMaxLevel()
    {
	return 5;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench)
    {
	return !(ench instanceof EnchantmentDamage) && super.canApplyTogether(ench);
    }
}
