package com.barribob.MaelstromMod.enchantments;

import com.barribob.MaelstromMod.event_handlers.ItemToManaSystem;
import com.barribob.MaelstromMod.items.gun.ItemGun;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Increases gun reload
 */
public class EnchantmentReload extends Enchantment {
    public EnchantmentReload(String registryName, Rarity rarityIn, EntityEquipmentSlot[] slots) {
        // The enum enchantment type doesn't matter here
        super(rarityIn, EnumEnchantmentType.ALL, slots);
        this.setRegistryName(registryName);
        this.setName(registryName);
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 1 + (enchantmentLevel - 1) * 10;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof ItemGun || ItemToManaSystem.getManaConfig(stack) != null;
    }
}
