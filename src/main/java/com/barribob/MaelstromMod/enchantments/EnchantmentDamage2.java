package com.barribob.MaelstromMod.enchantments;

import com.barribob.MaelstromMod.config.ModConfig;
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
	return level * ModConfig.progression_scale;
    }

    public boolean canApply(ItemStack stack)
    {
	return canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
	int minLevel = 2;
	int maxLevel = 3;
	if (stack.getItem() instanceof ToolSword)
	{
	    float level = ((ToolSword) stack.getItem()).getLevel();
	    return level >= minLevel && level < maxLevel;
	}
	return false;
    }

    public int getMinEnchantability(int enchantmentLevel)
    {
	return 1 + (enchantmentLevel - 1) * 10;
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
	return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    public int getMaxLevel()
    {
	return 5;
    }

    public boolean canApplyTogether(Enchantment ench)
    {
	return !(ench instanceof EnchantmentDamage) && super.canApplyTogether(ench);
    }
}
