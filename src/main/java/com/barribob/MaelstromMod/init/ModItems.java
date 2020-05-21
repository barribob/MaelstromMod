package com.barribob.MaelstromMod.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.barribob.MaelstromMod.items.ItemBase;
import com.barribob.MaelstromMod.items.ItemBlockvoid;
import com.barribob.MaelstromMod.items.ItemCatalyst;
import com.barribob.MaelstromMod.items.ItemFoodBase;
import com.barribob.MaelstromMod.items.ItemKey;
import com.barribob.MaelstromMod.items.ItemModElytra;
import com.barribob.MaelstromMod.items.ItemModRecord;
import com.barribob.MaelstromMod.items.ItemSingleDescription;
import com.barribob.MaelstromMod.items.ItemTradable;
import com.barribob.MaelstromMod.items.armor.ArmorNyanHelmet;
import com.barribob.MaelstromMod.items.armor.ArmorStrawHat;
import com.barribob.MaelstromMod.items.armor.ItemSpeedBoots;
import com.barribob.MaelstromMod.items.armor.ModArmorBase;
import com.barribob.MaelstromMod.items.gun.ItemAmmoCase;
import com.barribob.MaelstromMod.items.gun.ItemBoomstick;
import com.barribob.MaelstromMod.items.gun.ItemExplosiveStaff;
import com.barribob.MaelstromMod.items.gun.ItemFireballStaff;
import com.barribob.MaelstromMod.items.gun.ItemFlintlock;
import com.barribob.MaelstromMod.items.gun.ItemLeapStaff;
import com.barribob.MaelstromMod.items.gun.ItemMaelstromCannon;
import com.barribob.MaelstromMod.items.gun.ItemMeteorStaff;
import com.barribob.MaelstromMod.items.gun.ItemMusket;
import com.barribob.MaelstromMod.items.gun.ItemPiercer;
import com.barribob.MaelstromMod.items.gun.ItemPotionEffectStaff;
import com.barribob.MaelstromMod.items.gun.ItemPumpkin;
import com.barribob.MaelstromMod.items.gun.ItemQuakeStaff;
import com.barribob.MaelstromMod.items.gun.ItemRepeater;
import com.barribob.MaelstromMod.items.gun.ItemRifle;
import com.barribob.MaelstromMod.items.gun.ItemRuneStaff;
import com.barribob.MaelstromMod.items.gun.ItemSpeedStaff;
import com.barribob.MaelstromMod.items.gun.ItemTuningFork;
import com.barribob.MaelstromMod.items.gun.ItemWispStaff;
import com.barribob.MaelstromMod.items.gun.bullet.BrownstoneCannon;
import com.barribob.MaelstromMod.items.gun.bullet.GoldenFireball;
import com.barribob.MaelstromMod.items.tools.ItemMagisteelSword;
import com.barribob.MaelstromMod.items.tools.ToolBattleaxe;
import com.barribob.MaelstromMod.items.tools.ToolCrusadeSword;
import com.barribob.MaelstromMod.items.tools.ToolDagger;
import com.barribob.MaelstromMod.items.tools.ToolDragonslayer;
import com.barribob.MaelstromMod.items.tools.ToolExplosiveDagger;
import com.barribob.MaelstromMod.items.tools.ToolFrostSword;
import com.barribob.MaelstromMod.items.tools.ToolLongsword;
import com.barribob.MaelstromMod.items.tools.ToolPickaxe;
import com.barribob.MaelstromMod.items.tools.ToolSword;
import com.barribob.MaelstromMod.items.tools.ToolVenomDagger;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

/**
 * 
 * Holds all of our new items
 *
 */
public class ModItems
{
    public static final float BASE_MELEE_DAMAGE = 6;
    private static final String keyDesc = "Give to herobrine to craft";

    private static final ToolMaterial DAGGER = EnumHelper.addToolMaterial("rare_dagger", 2, 600, 8.0f, BASE_MELEE_DAMAGE, 20);
    private static final ToolMaterial SWORD = EnumHelper.addToolMaterial("rare_sword", 2, 500, 8.0f, BASE_MELEE_DAMAGE, 20);
    private static final ToolMaterial BATTLEAXE = EnumHelper.addToolMaterial("rare_battleaxe", 2, 400, 8.0f, BASE_MELEE_DAMAGE, 20);
    public static final int GUN_USE_TIME = 12000;
    public static final int STAFF_USE_TIME = 9000;
    private static final ArmorMaterial ARMOR = EnumHelper.addArmorMaterial("maelstrom", Reference.MOD_ID + ":maelstrom", 32, new int[] { 3, 6, 8, 3 }, 16, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0);
    private static final ToolMaterial ENERGETIC_PICKAXE = EnumHelper.addToolMaterial("energetic_pickaxe", 5, 8000, 100, 6, 15);

    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item INVISIBLE = new ItemBase("invisible", null);

    static Consumer<List<String>> kanshouBakuya = (tooltip) -> {
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("kanshou_bakuya"));
    };

    /*
     * Dimensional Items
     */
    public static final Item AZURE_KEY = new ItemKey("azure_key", "dimensional_key", ModCreativeTabs.ITEMS);
    public static final Item BROWN_KEY = new ItemKey("brown_key", "dimensional_key", ModCreativeTabs.ITEMS);
    public static final Item MAELSTROM_KEY = new ItemKey("maelstrom_key", "maelstrom_key", null);
    public static final Item RED_KEY = new ItemKey("red_key", "dimensional_key", ModCreativeTabs.ITEMS);

    public static final Item CLIFF_KEY_FRAGMENT = new ItemSingleDescription("cliff_key_fragment", keyDesc, ModCreativeTabs.ITEMS);
    public static final Item RED_KEY_FRAGMENT = new ItemSingleDescription("red_key_fragment", keyDesc, ModCreativeTabs.ITEMS);

    public static final Item MAELSTROM_CORE = new ItemTradable("maelstrom_core", ModCreativeTabs.ITEMS);
    public static final Item AZURE_MAELSTROM_CORE_CRYSTAL = new ItemTradable("azure_maelstrom_core_crystal", ModCreativeTabs.ITEMS);
    public static final Item GOLDEN_MAELSTROM_CORE = new ItemTradable("golden_maelstrom_core", ModCreativeTabs.ITEMS);
    public static final Item CRIMSON_MAELSTROM_CORE = new ItemTradable("crimson_maelstrom_core", ModCreativeTabs.ITEMS);
    public static final Item MAELSTROM_FRAGMENT = new ItemBase("maelstrom_fragment", ModCreativeTabs.ITEMS);
    public static final Item AZURE_MAELSTROM_FRAGMENT = new ItemBase("azure_maelstrom_fragment", ModCreativeTabs.ITEMS);
    public static final Item GOLDEN_MAELSTROM_FRAGMENT = new ItemTradable("golden_maelstrom_fragment", ModCreativeTabs.ITEMS);
    public static final Item CRISMON_MAELSTROM_FRAGMENT = new ItemTradable("crimson_maelstrom_fragment", ModCreativeTabs.ITEMS);

    // The azure dimension's items
    public static final Item ELK_HIDE = new ItemTradable("elk_hide", ModCreativeTabs.ITEMS);
    public static final Item ELK_STRIPS = new ItemFoodBase("elk_strips", ModCreativeTabs.ITEMS, 3, 0.3F, true);
    public static final Item ELK_JERKY = new ItemFoodBase("elk_jerky", ModCreativeTabs.ITEMS, 8, 1.0F, true);
    public static final Item PLUM = new ItemFoodBase("plum", ModCreativeTabs.ITEMS, 4, 0.3F, true);
    public static final Item IRON_PELLET = new ItemBase("iron_pellet", null);
    public static final Item MAELSTROM_PELLET = new ItemBase("maelstrom_pellet", null);
    public static final Item CHASMIUM_INGOT = new ItemTradable("chasmium_ingot", ModCreativeTabs.ITEMS);
    public static final Item CATALYST = new ItemCatalyst("catalyst", ModCreativeTabs.ITEMS);
    public static final Item MINOTAUR_HORN = new ItemTradable("minotaur_horn", ModCreativeTabs.ITEMS);

    /**
     * Guns
     */

    public static final Item FLINTLOCK = new ItemFlintlock("flintlock_pistol", LevelHandler.INVASION);
    public static final Item BOOMSTICK = new ItemBoomstick("boomstick", LevelHandler.AZURE_OVERWORLD);
    public static final Item MUSKET = new ItemMusket("musket", LevelHandler.AZURE_OVERWORLD);
    public static final Item REPEATER = new ItemRepeater("repeater", LevelHandler.AZURE_OVERWORLD);
    public static final Item RIFLE = new ItemRifle("rifle", LevelHandler.AZURE_OVERWORLD).setInformation((tooltip) -> {
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("rifle"));
    });
    public static final Item ELK_BLASTER = new ItemPiercer("elk_blaster", LevelHandler.AZURE_ENDGAME).setElement(Element.AZURE);
    public static final Item PUMPKIN = new ItemPumpkin("pumpkin", 80, null, LevelHandler.AZURE_ENDGAME);
    public static final Item GOLDEN_FLINTLOCK = new ItemFlintlock("golden_pistol", LevelHandler.CLIFF_ENDGAME).setElement(Element.GOLDEN);
    public static final Item GOLDEN_REPEATER = new ItemRepeater("golden_repeater", LevelHandler.CLIFF_ENDGAME).setElement(Element.GOLDEN);
    public static final Item GOLDEN_SHOTGUN = new ItemBoomstick("golden_shotgun", LevelHandler.CLIFF_ENDGAME).setElement(Element.GOLDEN);
    public static final Item GOLDEN_RIFLE = new ItemRifle("golden_rifle", LevelHandler.CLIFF_ENDGAME).setElement(Element.GOLDEN);

    public static final Item ENERGIZED_PISTOL = new ItemFlintlock("energized_pistol", LevelHandler.CRIMSON_START).setElement(Element.CRIMSON);
    public static final Item ENERGIZED_REPEATER = new ItemRepeater("energized_repeater", LevelHandler.CRIMSON_START).setElement(Element.CRIMSON);
    public static final Item ENERGIZED_SHOTGUN = new ItemBoomstick("energized_shotgun", LevelHandler.CRIMSON_START).setElement(Element.CRIMSON);
    public static final Item ENERGIZED_PIERCER = new ItemPiercer("energized_piercer", LevelHandler.CRIMSON_START).setElement(Element.CRIMSON);
    public static final Item ENERGIZED_MUSKET = new ItemMusket("energized_musket", LevelHandler.CRIMSON_START).setElement(Element.CRIMSON);

    /**
     * Staves
     */

    public static final Item MAELSTROM_CANNON = new ItemMaelstromCannon("maelstrom_cannon", STAFF_USE_TIME, LevelHandler.AZURE_ENDGAME, ModCreativeTabs.ITEMS);
    public static final Item WILLOTHEWISP_STAFF = new ItemWispStaff("will-o-the-wisp_staff", 40, STAFF_USE_TIME, LevelHandler.AZURE_ENDGAME, ModCreativeTabs.ITEMS);
    public static final Item QUAKE_STAFF = new ItemQuakeStaff("quake_staff", 25, STAFF_USE_TIME, LevelHandler.AZURE_OVERWORLD, ModCreativeTabs.ITEMS);
    public static final Item LEAP_STAFF = new ItemLeapStaff("leap_staff", 20, STAFF_USE_TIME, LevelHandler.AZURE_ENDGAME, ModCreativeTabs.ITEMS);
    public static final Item SPEED_STAFF = new ItemSpeedStaff("speed_staff", STAFF_USE_TIME, LevelHandler.AZURE_ENDGAME, ModCreativeTabs.ITEMS);
    public static final Item FIREBALL_STAFF = new ItemFireballStaff("fireball_staff", STAFF_USE_TIME, LevelHandler.AZURE_ENDGAME, ModCreativeTabs.ITEMS);
    public static final Item CROSS_OF_AQUA = (new ItemBase("cross_of_aqua", ModCreativeTabs.ITEMS)
    {
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
	    tooltip.add(TextFormatting.BLUE + "When held, allows the player to walk on water");
	    tooltip.add(TextFormatting.GRAY + "Mana cost: " + TextFormatting.DARK_PURPLE + "0.5" + TextFormatting.GRAY + " per second");
	};
    }).setMaxStackSize(1);
    public static final Item BROWNSTONE_CANNON = new ItemMaelstromCannon("brownstone_cannon", STAFF_USE_TIME, LevelHandler.CLIFF_OVERWORLD, ModCreativeTabs.ITEMS).setFactory(new BrownstoneCannon());
    public static final Item METEOR_STAFF = new ItemMeteorStaff("meteor_staff", 50, STAFF_USE_TIME, LevelHandler.CLIFF_OVERWORLD, ModCreativeTabs.ITEMS);
    public static final Item GOLDEN_QUAKE_STAFF = new ItemQuakeStaff("golden_quake_staff", 25, STAFF_USE_TIME, LevelHandler.CLIFF_OVERWORLD, ModCreativeTabs.ITEMS).setElement(Element.GOLDEN);
    public static final Item EXPLOSIVE_STAFF = new ItemExplosiveStaff("explosive_staff", 60, STAFF_USE_TIME, LevelHandler.CLIFF_ENDGAME, ModCreativeTabs.ITEMS);
    public static final Item GOLDEN_FIREBALL_STAFF = new ItemFireballStaff("golden_fireball_staff", STAFF_USE_TIME, LevelHandler.CLIFF_ENDGAME, ModCreativeTabs.ITEMS).setFactory(new GoldenFireball()).setElement(Element.GOLDEN);
    public static final Item CRIMSON_RUNE_STAFF = new ItemRuneStaff("crimson_rune_staff", LevelHandler.CRIMSON_START).setElement(Element.CRIMSON);
    public static final Item ENERGIZED_CADUCEUS = new ItemPotionEffectStaff("energized_caduceus", LevelHandler.CRIMSON_END, 15, 150,
	    () -> new PotionEffect[] { new PotionEffect(MobEffects.REGENERATION, 150, 3), new PotionEffect(MobEffects.ABSORPTION, 2400, 4) }, "energized_caduceus");
    public static final Item TUNING_FORK = new ItemTuningFork("tuning_fork", LevelHandler.CRIMSON_END).setElement(Element.CRIMSON);

    /**
     * Melee
     */

    public static final Item VENOM_DAGGER = new ToolVenomDagger("venom_dagger", DAGGER, LevelHandler.INVASION);
    public static final Item NEXUS_BATTLEAXE = new ToolBattleaxe("nexus_battleaxe", BATTLEAXE, LevelHandler.AZURE_OVERWORLD);
    public static final Item CHASMIUM_SWORD = new ToolSword("chasmium_sword", SWORD, LevelHandler.AZURE_OVERWORLD);
    public static final Item SWORD_OF_SHADES = new ToolLongsword("sword_of_shades", SWORD, LevelHandler.AZURE_ENDGAME);
    public static final Item SHADOW_DAGGER = new ToolDagger("shadow_dagger", DAGGER, LevelHandler.AZURE_ENDGAME);
    public static final Item MAELSTROM_BATTLEAXE = new ToolBattleaxe("maelstrom_battleaxe", BATTLEAXE, LevelHandler.AZURE_ENDGAME);
    public static final Item FROST_SWORD = new ToolFrostSword("frost_sword", SWORD, LevelHandler.AZURE_ENDGAME);
    public static final Item BEAST_BLADE = new ToolSword("beast_blade", SWORD, LevelHandler.AZURE_ENDGAME);
    public static final Item ELUCIDATOR = new ToolLongsword("elucidator", SWORD, LevelHandler.AZURE_ENDGAME);
    public static final Item DRAGON_SLAYER = new ToolDragonslayer("dragon_slayer", BATTLEAXE, LevelHandler.AZURE_ENDGAME);
    public static final Item ANCIENT_BATTLEAXE = new ToolDragonslayer("ancient_battleaxe", BATTLEAXE, LevelHandler.CLIFF_OVERWORLD);
    public static final Item BROWNSTONE_SWORD = new ToolSword("brownstone_sword", SWORD, LevelHandler.CLIFF_OVERWORLD);
    public static final Item CRUSADE_SWORD = new ToolCrusadeSword("crusade_sword", SWORD, LevelHandler.CLIFF_OVERWORLD);
    public static final Item MAGISTEEL_SWORD = new ItemMagisteelSword("magisteel_sword", SWORD, LevelHandler.CLIFF_OVERWORLD, Element.NONE);
    public static final Item GOLD_STONE_LONGSWORD = new ToolLongsword("gold_stone_longsword", SWORD, LevelHandler.CLIFF_ENDGAME);
    public static final Item BLACK_GOLD_SWORD = new ToolSword("black_gold_sword", SWORD, LevelHandler.CLIFF_ENDGAME, Element.GOLDEN);
    public static final Item KANSHOU = new ToolDagger("kanshou", DAGGER, LevelHandler.CLIFF_ENDGAME).setInformation(kanshouBakuya);
    public static final Item BAKUYA = new ToolDagger("bakuya", DAGGER, LevelHandler.CLIFF_ENDGAME).setInformation(kanshouBakuya);
    public static final Item EXPLOSIVE_DAGGER = new ToolExplosiveDagger("explosive_dagger", DAGGER, LevelHandler.CLIFF_ENDGAME);
    public static final Item ENERGETIC_STEEL_SWORD = new ItemMagisteelSword("energetic_steel_sword", SWORD, LevelHandler.CRIMSON_START, Element.CRIMSON);
    public static final Item ENERGETIC_STEEL_CLEAVER = new ToolDragonslayer("energetic_steel_cleaver", BATTLEAXE, LevelHandler.CRIMSON_START).setElement(Element.CRIMSON);
    public static final Item FADESTEEL_SWORD = new ToolSword("fadesteel_sword", SWORD, LevelHandler.CRIMSON_START);

    /*
     * Armors
     */

    public static final Item STRAW_HAT = new ArmorStrawHat("straw_hat", ARMOR, 1, EntityEquipmentSlot.HEAD, LevelHandler.INVASION, "straw_hat.png");
    public static final Item SPEED_BOOTS = new ItemSpeedBoots("speed_boots", ARMOR, 1, EntityEquipmentSlot.FEET, LevelHandler.AZURE_OVERWORLD, "speed");

    public static final Item NEXUS_HELMET = new ModArmorBase("nexus_helmet", ARMOR, 1, EntityEquipmentSlot.HEAD, LevelHandler.INVASION, "nexus");
    public static final Item NEXUS_CHESTPLATE = new ModArmorBase("nexus_chestplate", ARMOR, 1, EntityEquipmentSlot.CHEST, LevelHandler.INVASION, "nexus");
    public static final Item NEXUS_LEGGINGS = new ModArmorBase("nexus_leggings", ARMOR, 2, EntityEquipmentSlot.LEGS, LevelHandler.INVASION, "nexus");
    public static final Item NEXUS_BOOTS = new ModArmorBase("nexus_boots", ARMOR, 1, EntityEquipmentSlot.FEET, LevelHandler.INVASION, "nexus");

    public static final Item ELK_HIDE_HELMET = new ModArmorBase("elk_hide_helmet", ARMOR, 1, EntityEquipmentSlot.HEAD, LevelHandler.AZURE_OVERWORLD, "elk_hide").setElement(Element.AZURE);
    public static final Item ELK_HIDE_CHESTPLATE = new ModArmorBase("elk_hide_chestplate", ARMOR, 1, EntityEquipmentSlot.CHEST, LevelHandler.AZURE_OVERWORLD, "elk_hide").setElement(Element.AZURE);
    public static final Item ELK_HIDE_LEGGINGS = new ModArmorBase("elk_hide_leggings", ARMOR, 2, EntityEquipmentSlot.LEGS, LevelHandler.AZURE_OVERWORLD, "elk_hide").setElement(Element.AZURE);
    public static final Item ELK_HIDE_BOOTS = new ModArmorBase("elk_hide_boots", ARMOR, 1, EntityEquipmentSlot.FEET, LevelHandler.AZURE_OVERWORLD, "elk_hide").setElement(Element.AZURE);

    public static final Item CHASMIUM_HELMET = new ModArmorBase("chasmium_helmet", ARMOR, 1, EntityEquipmentSlot.HEAD, LevelHandler.AZURE_OVERWORLD, "chasmium");
    public static final Item CHASMIUM_CHESTPLATE = new ModArmorBase("chasmium_chestplate", ARMOR, 1, EntityEquipmentSlot.CHEST, LevelHandler.AZURE_OVERWORLD, "chasmium");
    public static final Item CHASMIUM_LEGGINGS = new ModArmorBase("chasmium_leggings", ARMOR, 2, EntityEquipmentSlot.LEGS, LevelHandler.AZURE_OVERWORLD, "chasmium");
    public static final Item CHASMIUM_BOOTS = new ModArmorBase("chasmium_boots", ARMOR, 1, EntityEquipmentSlot.FEET, LevelHandler.AZURE_OVERWORLD, "chasmium");

    public static final Item MAELSTROM_HELMET = new ModArmorBase("maelstrom_helmet", ARMOR, 1, EntityEquipmentSlot.HEAD, LevelHandler.AZURE_ENDGAME, "maelstrom").setArmorBonusDesc("maelstrom_full_set");
    public static final Item MAELSTROM_CHESTPLATE = new ModArmorBase("maelstrom_chestplate", ARMOR, 1, EntityEquipmentSlot.CHEST, LevelHandler.AZURE_ENDGAME, "maelstrom").setArmorBonusDesc("maelstrom_full_set");
    public static final Item MAELSTROM_LEGGINGS = new ModArmorBase("maelstrom_leggings", ARMOR, 2, EntityEquipmentSlot.LEGS, LevelHandler.AZURE_ENDGAME, "maelstrom").setArmorBonusDesc("maelstrom_full_set");
    public static final Item MAELSTROM_BOOTS = new ModArmorBase("maelstrom_boots", ARMOR, 1, EntityEquipmentSlot.FEET, LevelHandler.AZURE_ENDGAME, "maelstrom").setArmorBonusDesc("maelstrom_full_set");

    public static final Item SWAMP_HELMET = new ModArmorBase("swamp_helmet", ARMOR, 1, EntityEquipmentSlot.HEAD, LevelHandler.CLIFF_OVERWORLD, "swamp").setArmorBonusDesc("swamp_full_set");
    public static final Item SWAMP_CHESTPLATE = new ModArmorBase("swamp_chestplate", ARMOR, 1, EntityEquipmentSlot.CHEST, LevelHandler.CLIFF_OVERWORLD, "swamp").setArmorBonusDesc("swamp_full_set");
    public static final Item SWAMP_LEGGINGS = new ModArmorBase("swamp_leggings", ARMOR, 2, EntityEquipmentSlot.LEGS, LevelHandler.CLIFF_OVERWORLD, "swamp").setArmorBonusDesc("swamp_full_set");
    public static final Item SWAMP_BOOTS = new ModArmorBase("swamp_boots", ARMOR, 1, EntityEquipmentSlot.FEET, LevelHandler.CLIFF_OVERWORLD, "swamp").setArmorBonusDesc("swamp_full_set");

    public static final Item GOLTOX_HELMET = new ModArmorBase("goltox_helmet", ARMOR, 1, EntityEquipmentSlot.HEAD, LevelHandler.CLIFF_OVERWORLD, "goltox").setElement(Element.GOLDEN).setArmorBonusDesc("goltox_full_set");
    public static final Item GOLTOX_CHESTPLATE = new ModArmorBase("goltox_chestplate", ARMOR, 1, EntityEquipmentSlot.CHEST, LevelHandler.CLIFF_OVERWORLD, "goltox").setElement(Element.GOLDEN).setArmorBonusDesc("goltox_full_set");
    public static final Item GOLTOX_LEGGINGS = new ModArmorBase("goltox_leggings", ARMOR, 2, EntityEquipmentSlot.LEGS, LevelHandler.CLIFF_OVERWORLD, "goltox").setElement(Element.GOLDEN).setArmorBonusDesc("goltox_full_set");
    public static final Item GOLTOX_BOOTS = new ModArmorBase("goltox_boots", ARMOR, 1, EntityEquipmentSlot.FEET, LevelHandler.CLIFF_OVERWORLD, "goltox").setElement(Element.GOLDEN).setArmorBonusDesc("goltox_full_set");

    public static final Item NYAN_HELMET = new ArmorNyanHelmet("nyan_helmet", ARMOR, 1, EntityEquipmentSlot.HEAD, LevelHandler.CLIFF_ENDGAME, "nyan_helmet.png").setArmorBonusDesc("nyan_full_set");
    public static final Item NYAN_CHESTPLATE = new ModArmorBase("nyan_chestplate", ARMOR, 1, EntityEquipmentSlot.CHEST, LevelHandler.CLIFF_ENDGAME, "nyan").setArmorBonusDesc("nyan_full_set");
    public static final Item NYAN_LEGGINGS = new ModArmorBase("nyan_leggings", ARMOR, 2, EntityEquipmentSlot.LEGS, LevelHandler.CLIFF_ENDGAME, "nyan").setArmorBonusDesc("nyan_full_set");
    public static final Item NYAN_BOOTS = new ModArmorBase("nyan_boots", ARMOR, 1, EntityEquipmentSlot.FEET, LevelHandler.CLIFF_ENDGAME, "nyan").setArmorBonusDesc("nyan_full_set");

    public static final Item BLACK_GOLD_HELMET = new ModArmorBase("black_gold_helmet", ARMOR, 1, EntityEquipmentSlot.HEAD, LevelHandler.CLIFF_ENDGAME, "black_gold").setElement(Element.GOLDEN).setArmorBonusDesc("black_gold_full_set");
    public static final Item BLACK_GOLD_CHESTPLATE = new ModArmorBase("black_gold_chestplate", ARMOR, 1, EntityEquipmentSlot.CHEST, LevelHandler.CLIFF_ENDGAME, "black_gold").setElement(Element.GOLDEN).setArmorBonusDesc("black_gold_full_set");
    public static final Item BLACK_GOLD_LEGGINGS = new ModArmorBase("black_gold_leggings", ARMOR, 2, EntityEquipmentSlot.LEGS, LevelHandler.CLIFF_ENDGAME, "black_gold").setElement(Element.GOLDEN).setArmorBonusDesc("black_gold_full_set");
    public static final Item BLACK_GOLD_BOOTS = new ModArmorBase("black_gold_boots", ARMOR, 1, EntityEquipmentSlot.FEET, LevelHandler.CLIFF_ENDGAME, "black_gold").setElement(Element.GOLDEN).setArmorBonusDesc("black_gold_full_set");

    public static final Item ENERGETIC_STEEL_HELMET =
	    new ModArmorBase("energetic_steel_helmet", ARMOR, 1, EntityEquipmentSlot.HEAD, LevelHandler.CRIMSON_START, "energetic_steel").setElement(Element.CRIMSON).setArmorBonusDesc("energetic_steel_full_set");
    public static final Item ENERGETIC_STEEL_CHESTPLATE =
	    new ModArmorBase("energetic_steel_chestplate", ARMOR, 1, EntityEquipmentSlot.CHEST, LevelHandler.CRIMSON_START, "energetic_steel").setElement(Element.CRIMSON).setArmorBonusDesc("energetic_steel_full_set");
    public static final Item ENERGETIC_STEEL_LEGGINGS =
	    new ModArmorBase("energetic_steel_leggings", ARMOR, 2, EntityEquipmentSlot.LEGS, LevelHandler.CRIMSON_START, "energetic_steel").setElement(Element.CRIMSON).setArmorBonusDesc("energetic_steel_full_set");
    public static final Item ENERGETIC_STEEL_BOOTS =
	    new ModArmorBase("energetic_steel_boots", ARMOR, 1, EntityEquipmentSlot.FEET, LevelHandler.CRIMSON_START, "energetic_steel").setElement(Element.CRIMSON).setArmorBonusDesc("energetic_steel_full_set");

    public static final Item FADESTEEL_HELMET = new ModArmorBase("fadesteel_helmet", ARMOR, 1, EntityEquipmentSlot.HEAD, LevelHandler.CRIMSON_START, "fadesteel").setArmorBonusDesc("fadesteel_full_set");
    public static final Item FADESTEEL_CHESTPLATE = new ModArmorBase("fadesteel_chestplate", ARMOR, 1, EntityEquipmentSlot.CHEST, LevelHandler.CRIMSON_START, "fadesteel").setArmorBonusDesc("fadesteel_full_set");
    public static final Item FADESTEEL_LEGGINGS = new ModArmorBase("fadesteel_leggings", ARMOR, 2, EntityEquipmentSlot.LEGS, LevelHandler.CRIMSON_START, "fadesteel").setArmorBonusDesc("fadesteel_full_set");
    public static final Item FADESTEEL_BOOTS = new ModArmorBase("fadesteel_boots", ARMOR, 1, EntityEquipmentSlot.FEET, LevelHandler.CRIMSON_START, "fadesteel").setArmorBonusDesc("fadesteel_full_set");

    public static final Item ELYSIUM_HELMET = new ModArmorBase("elysium_helmet", ARMOR, 1, EntityEquipmentSlot.HEAD, LevelHandler.CRIMSON_END, "elysium").setArmorBonusDesc("elysium_full_set");
    public static final Item ELYSIUM_CHESTPLATE = new ModArmorBase("elysium_chestplate", ARMOR, 1, EntityEquipmentSlot.CHEST, LevelHandler.CRIMSON_END, "elysium").setArmorBonusDesc("elysium_full_set");
    public static final Item ELYSIUM_LEGGINGS = new ModArmorBase("elysium_leggings", ARMOR, 2, EntityEquipmentSlot.LEGS, LevelHandler.CRIMSON_END, "elysium").setArmorBonusDesc("elysium_full_set");
    public static final Item ELYSIUM_BOOTS = new ModArmorBase("elysium_boots", ARMOR, 1, EntityEquipmentSlot.FEET, LevelHandler.CRIMSON_END, "elysium").setArmorBonusDesc("elysium_full_set");

    public static final Item AMMO_CASE = new ItemAmmoCase("ammo_case", LevelHandler.INVASION);
    public static final Item CHASMIUM_AMMO_CASE = new ItemAmmoCase("chasmium_ammo_case", LevelHandler.AZURE_OVERWORLD);
    public static final Item BLACK_GOLD_AMMO_CASE = new ItemAmmoCase("black_gold_ammo_case", LevelHandler.CLIFF_ENDGAME);
    public static final Item CRIMSON_AMMO_CASE = new ItemAmmoCase("crimson_ammo_case", LevelHandler.CRIMSON_START);

    /*
     * Cliff Dimension Items
     */
    public static final Item GOLD_PELLET = new ItemBase("gold_pellet", null);
    public static final Item SWAMP_SLIME = new ItemTradable("swamp_slime", ModCreativeTabs.ITEMS);
    public static final Item FLY_WINGS = new ItemTradable("fly_wings", ModCreativeTabs.ITEMS);

    /**
     * Crimson Dimension Items
     */

    public static final Item ENERGETIC_STEEL_PICKAXE = new ToolPickaxe("energetic_steel_pickaxe", ENERGETIC_PICKAXE);
    public static final Item STONEBRICK_BLOCKVOID = new ItemBlockvoid("stonebrick_blockvoid", Blocks.STONEBRICK, 30);
    public static final Item OBSIDIAN_BLOCKVOID = new ItemBlockvoid("obsidian_blockvoid", Blocks.OBSIDIAN, 1000);
    public static final Item FURNACE_BRICKS_BLOCKVOID = new ItemBlockvoid("furnace_bricks_blockvoid", ModBlocks.FURNACE_BRICKS, 30);
    public static final Item REDSTONE_BRICK_BLOCKVOID = new ItemBlockvoid("redstone_brick_blockvoid", ModBlocks.REDSTONE_BRICK, 30);
    public static final Item CRIMSON_PELLET = new ItemBase("crimson_pellet", null);
    public static final Item ELYSIUM_WINGS = new ItemModElytra("elysium_wings", ARMOR);

    /**
     * Random
     */

    // The sound events are unregistered because they are null at the point in the loading procedure
    public static final Item NEW_WORLD_RECORD = new ItemModRecord("music_disc_new_world", new SoundEvent(new ResourceLocation(Reference.MOD_ID, "music.new_world")));
    public static final Item WANDERING_RECORD = new ItemModRecord("music_disc_wandering", new SoundEvent(new ResourceLocation(Reference.MOD_ID, "music.wandering")));
}
