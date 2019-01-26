package com.barribob.MaelstromMod.items.armor;

import java.util.List;
import java.util.UUID;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.init.ModCreativeTabs;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.IHasModel;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * 
 * The base for armor for the mod
 *
 */
public class ModArmorBase extends ItemArmor implements IHasModel
{
    private static final UUID[] ARMOR_MODIFIERS = new UUID[] { UUID.fromString("a3578781-e4a8-4d70-9d32-cd952aeae1df"),
	    UUID.fromString("e2d1f056-f539-48c7-b353-30d7a367ebd0"), UUID.fromString("db13047a-bb47-4621-a025-65ed22ce461a"),
	    UUID.fromString("abb5df20-361d-420a-8ec7-4bdba33378eb") };

    private float maelstrom_armor_factor;
    private static final int[] armor_fractions = {4, 7, 8, 5};
    private static final int armor_total = 24;

    public ModArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, float maelstrom_armor)
    {
	super(materialIn, renderIndexIn, equipmentSlotIn);
	setUnlocalizedName(name);
	setRegistryName(name);
	setCreativeTab(ModCreativeTabs.ALL);
	this.maelstrom_armor_factor = maelstrom_armor;

	ModItems.ITEMS.add(this);
    }

    /**
     * Get the calculated reduction in armor based on the armor factor
     */
    public float getMaelstromArmor(ItemStack stack)
    {
	float total_armor_reduction = 1 - LevelHandler.getArmorFromLevel(this.maelstrom_armor_factor);
	float armor_type_fraction = this.armor_fractions[this.getEquipmentSlot().getIndex()] / (float)armor_total;
	return total_armor_reduction * armor_type_fraction;
    }
    
    /**
     * Gets the armor bars for display
     * Directly calculates from the fraction of the maelstrom_armor_factor, which
     * represents the total armor bars of any given armor set
     */
    public float getMaelstromArmorBars()
    {
	float armor_type_fraction = this.armor_fractions[this.getEquipmentSlot().getIndex()] / (float)armor_total;
	return this.maelstrom_armor_factor * armor_type_fraction * 2;
    }

    @Override
    public void registerModels()
    {
	Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit
     * damage.
     */
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
	Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

	if (equipmentSlot == this.armorType)
	{
	    multimap.put("maelstrom_armor", new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Maelstrom Armor modifier", Math.round(this.getMaelstromArmorBars() * 100) / 100.0f, 0));
	}

	return multimap;
    }
}
