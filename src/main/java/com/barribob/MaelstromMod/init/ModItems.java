package com.barribob.MaelstromMod.init;

import java.util.ArrayList;
import java.util.List;

import com.barribob.MaelstromMod.items.ItemAzureKey;
import com.barribob.MaelstromMod.items.ItemBase;
import com.barribob.MaelstromMod.items.ItemBoomstick;
import com.barribob.MaelstromMod.items.ItemFoodBase;
import com.barribob.MaelstromMod.items.armor.ModArmorBase;
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
    // Tool materials
    public static final ToolMaterial BEAST = EnumHelper.addToolMaterial("beast", 2, 2000, 8.0f, 4.0f, 10);
    
    // Armor materials
    public static final ArmorMaterial ARMOR_MATERIAL_MAELSTROM = EnumHelper.addArmorMaterial("maelstrom", Reference.MOD_ID + ":maelstrom", 14, new int[] { 1, 3, 3, 1 }, 10,
	    SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0f);

    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item INVISIBLE = new ItemBase("invisible", null);

    // The azure dimension's items
    public static final Item ELK_HIDE = new ItemBase("elk_hide", CreativeTabs.MATERIALS);
    public static final Item ELK_STRIPS = new ItemFoodBase("elk_strips", CreativeTabs.FOOD, 3, 0.3F, true);
    public static final Item ELK_JERKY = new ItemFoodBase("elk_jerky", CreativeTabs.FOOD, 8, 1.0F, true);

    public static final Item AZURE_MAELSTROM_CORE_CRYSTAL = new ItemBase("azure_maelstrom_core_crystal", CreativeTabs.MATERIALS);
    public static final Item AZURE_KEY = new ItemAzureKey("azure_key", CreativeTabs.MATERIALS);

    public static final Item IRON_PELLET = new ItemBase("iron_pellet", CreativeTabs.MATERIALS);
    public static final Item BOOMSTICK = new ItemBoomstick("boomstick", 60, 250, IRON_PELLET);
    
    public static final Item BEAST_BLADE = new ToolSword("beast_blade", BEAST);

    public static final Item MAELSTROM_HELMET = new ModArmorBase("maelstrom_helmet", ARMOR_MATERIAL_MAELSTROM, 1, EntityEquipmentSlot.HEAD, 2);
    public static final Item MAELSTROM_CHESTPLATE = new ModArmorBase("maelstrom_chestplate", ARMOR_MATERIAL_MAELSTROM, 1, EntityEquipmentSlot.CHEST, 5);
    public static final Item MAELSTROM_LEGGINGS = new ModArmorBase("maelstrom_leggings", ARMOR_MATERIAL_MAELSTROM, 2, EntityEquipmentSlot.LEGS, 4);
    public static final Item MAELSTROM_BOOTS = new ModArmorBase("maelstrom_boots", ARMOR_MATERIAL_MAELSTROM, 1, EntityEquipmentSlot.FEET, 2);
}
