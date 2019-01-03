package com.barribob.MaelstromMod.init;

import java.util.ArrayList;
import java.util.List;

import com.barribob.MaelstromMod.items.ItemBase;
import com.barribob.MaelstromMod.items.ItemFoodBase;
import com.barribob.MaelstromMod.items.armor.ArmorBase;
import com.barribob.MaelstromMod.items.tools.ToolAxe;
import com.barribob.MaelstromMod.items.tools.ToolPickaxe;
import com.barribob.MaelstromMod.items.tools.ToolSpade;
import com.barribob.MaelstromMod.items.tools.ToolSword;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

/**
 * 
 * Holds all of our new items
 *
 */
public class ModItems 
{
	// Materials
	public static final ToolMaterial MATERIAL_ORE = EnumHelper.addToolMaterial("ore", 2, 200, 5.0f, 2.0f, 5);
	public static final ArmorMaterial ARMOR_MATERIAL_ORE = EnumHelper.addArmorMaterial("ore", Reference.MOD_ID + ":ore", 14, 
			new int[] {2, 5, 4, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f);

	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item INVISIBLE = new ItemBase("invisible", null);
	
	public static final Item ELK_HIDE = new ItemBase("elk_hide", CreativeTabs.MATERIALS);
	public static final Item ELK_STRIPS = new ItemFoodBase("elk_strips", CreativeTabs.FOOD, 3, 0.3F, true);
	public static final Item ELK_JERKY = new ItemFoodBase("elk_jerky", CreativeTabs.FOOD, 8, 1.0F, true);
	
	public static final ItemSword ORE_SWORD = new ToolSword("ore_sword", MATERIAL_ORE);
	public static final ItemSpade ORE_SPADE = new ToolSpade("ore_shovel", MATERIAL_ORE);
	public static final ItemPickaxe ORE_PICKAXE = new ToolPickaxe("ore_pickaxe", MATERIAL_ORE);
	public static final ItemAxe ORE_AXE = new ToolAxe("ore_axe", MATERIAL_ORE);

	public static final Item ORE_HELMET = new ArmorBase("ore_helmet", ARMOR_MATERIAL_ORE, 1, EntityEquipmentSlot.HEAD);
	public static final Item ORE_CHESPLATE = new ArmorBase("ore_chestplate", ARMOR_MATERIAL_ORE, 1, EntityEquipmentSlot.CHEST);
	public static final Item ORE_LEGGINGS = new ArmorBase("ore_leggings", ARMOR_MATERIAL_ORE, 2, EntityEquipmentSlot.LEGS);
	public static final Item ORE_BOOTS = new ArmorBase("ore_boots", ARMOR_MATERIAL_ORE, 1, EntityEquipmentSlot.FEET);
}
