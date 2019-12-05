package com.barribob.MaelstromMod.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileAzureBullet;
import com.barribob.MaelstromMod.items.ItemBase;
import com.barribob.MaelstromMod.items.ItemCatalyst;
import com.barribob.MaelstromMod.items.ItemFoodBase;
import com.barribob.MaelstromMod.items.ItemKey;
import com.barribob.MaelstromMod.items.ItemNexusIslandBuilder;
import com.barribob.MaelstromMod.items.ItemSingleDescription;
import com.barribob.MaelstromMod.items.ItemTBDKey;
import com.barribob.MaelstromMod.items.ItemTradable;
import com.barribob.MaelstromMod.items.armor.ArmorNyanHelmet;
import com.barribob.MaelstromMod.items.armor.ArmorStrawHat;
import com.barribob.MaelstromMod.items.armor.ItemSpeedBoots;
import com.barribob.MaelstromMod.items.armor.ModArmorBase;
import com.barribob.MaelstromMod.items.gun.ItemBoomstick;
import com.barribob.MaelstromMod.items.gun.ItemExplosiveStaff;
import com.barribob.MaelstromMod.items.gun.ItemFireballStaff;
import com.barribob.MaelstromMod.items.gun.ItemFlintlock;
import com.barribob.MaelstromMod.items.gun.ItemLeapStaff;
import com.barribob.MaelstromMod.items.gun.ItemMaelstromCannon;
import com.barribob.MaelstromMod.items.gun.ItemMeteorStaff;
import com.barribob.MaelstromMod.items.gun.ItemMusket;
import com.barribob.MaelstromMod.items.gun.ItemPumpkin;
import com.barribob.MaelstromMod.items.gun.ItemQuakeStaff;
import com.barribob.MaelstromMod.items.gun.ItemRepeater;
import com.barribob.MaelstromMod.items.gun.ItemRifle;
import com.barribob.MaelstromMod.items.gun.ItemSpeedStaff;
import com.barribob.MaelstromMod.items.gun.ItemWispStaff;
import com.barribob.MaelstromMod.items.gun.bullet.BrownstoneCannon;
import com.barribob.MaelstromMod.items.gun.bullet.BulletFactory;
import com.barribob.MaelstromMod.items.gun.bullet.GoldenBullet;
import com.barribob.MaelstromMod.items.gun.bullet.GoldenFireball;
import com.barribob.MaelstromMod.items.gun.bullet.GoldenRepeater;
import com.barribob.MaelstromMod.items.gun.bullet.RedstoneRepeater;
import com.barribob.MaelstromMod.items.tools.ItemMagisteelSword;
import com.barribob.MaelstromMod.items.tools.ToolBattleaxe;
import com.barribob.MaelstromMod.items.tools.ToolBlackGoldSword;
import com.barribob.MaelstromMod.items.tools.ToolCrusadeSword;
import com.barribob.MaelstromMod.items.tools.ToolDagger;
import com.barribob.MaelstromMod.items.tools.ToolDragonslayer;
import com.barribob.MaelstromMod.items.tools.ToolExplosiveDagger;
import com.barribob.MaelstromMod.items.tools.ToolFrostSword;
import com.barribob.MaelstromMod.items.tools.ToolLongsword;
import com.barribob.MaelstromMod.items.tools.ToolSword;
import com.barribob.MaelstromMod.items.tools.ToolVenomDagger;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
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
    private static final float BASE_MELEE_DAMAGE = 6.0f;
    private static final String keyDesc = "Give to herobrine to craft";

    // Tool materials
    private static final ToolMaterial COMMON_DAGGER = EnumHelper.addToolMaterial("common_dagger", 2, 300, 8.0f, BASE_MELEE_DAMAGE, 14);
    private static final ToolMaterial COMMON_BATTLEAXE = EnumHelper.addToolMaterial("common_battleaxe", 2, 200, 8.0f, BASE_MELEE_DAMAGE, 14);
    private static final ToolMaterial COMMON_SWORD = EnumHelper.addToolMaterial("common_sword", 2, 250, 8.0f, BASE_MELEE_DAMAGE, 14);

    private static final ToolMaterial RARE_DAGGER = EnumHelper.addToolMaterial("rare_dagger", 2, 600, 8.0f, BASE_MELEE_DAMAGE, 20);
    private static final ToolMaterial RARE_SWORD = EnumHelper.addToolMaterial("rare_sword", 2, 500, 8.0f, BASE_MELEE_DAMAGE, 20);
    private static final ToolMaterial RARE_BATTLEAXE = EnumHelper.addToolMaterial("rare_battleaxe", 2, 400, 8.0f, BASE_MELEE_DAMAGE, 20);

    // Armor materials
    private static final ArmorMaterial COMMON_ARMOR_MATERIAL = EnumHelper.addArmorMaterial("maelstrom", Reference.MOD_ID + ":maelstrom", 16, new int[] { 3, 6, 8, 3 }, 12,
	    SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.0f);

    private static final ArmorMaterial RARE_ARMOR_MATERIAL = EnumHelper.addArmorMaterial("maelstrom", Reference.MOD_ID + ":maelstrom", 32, new int[] { 3, 6, 8, 3 }, 16,
	    SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.0f);

    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item INVISIBLE = new ItemBase("invisible", null);

    /*
     * Dimensional Items
     */
    public static final Item NEXUS_ISLAND_BUILDER = new ItemNexusIslandBuilder("nexus_island_builder", ModCreativeTabs.ALL);
    public static final Item AZURE_KEY = new ItemKey("azure_key", ModCreativeTabs.ALL);
    public static final Item BROWN_KEY = new ItemKey("brown_key", ModCreativeTabs.ALL);
    public static final Item BEASTS_KEY = new ItemTBDKey("beast_key", ModCreativeTabs.ALL);
    public static final Item RED_KEY = new ItemTBDKey("red_key", ModCreativeTabs.ALL);

    public static final Item CLIFF_KEY_FRAGMENT = new ItemSingleDescription("cliff_key_fragment", keyDesc, ModCreativeTabs.ALL);
    public static final Item RED_KEY_FRAGMENT = new ItemSingleDescription("red_key_fragment", keyDesc, ModCreativeTabs.ALL);

    // The azure dimension's items
    public static final Item ELK_HIDE = new ItemTradable("elk_hide", ModCreativeTabs.ALL);
    public static final Item ELK_STRIPS = new ItemFoodBase("elk_strips", ModCreativeTabs.ALL, 3, 0.3F, true);
    public static final Item ELK_JERKY = new ItemFoodBase("elk_jerky", ModCreativeTabs.ALL, 8, 1.0F, true);
    public static final Item PLUM = new ItemFoodBase("plum", ModCreativeTabs.ALL, 4, 0.3F, true);
    public static final Item IRON_PELLET = new ItemBase("iron_pellet", ModCreativeTabs.ALL);
    public static final Item CHASMIUM_INGOT = new ItemTradable("chasmium_ingot", ModCreativeTabs.ALL);
    public static final Item CATALYST = new ItemCatalyst("catalyst", ModCreativeTabs.ALL);
    public static final Item MINOTAUR_HORN = new ItemTradable("minotaur_horn", ModCreativeTabs.ALL);

    public static final Item AZURE_MAELSTROM_CORE_CRYSTAL = new ItemTradable("azure_maelstrom_core_crystal", ModCreativeTabs.ALL);

    private static final int COMMON_USE_TIME = 6000;
    private static final int RARE_USE_TIME = 12000;
    public static final Item BOOMSTICK = new ItemBoomstick("boomstick", 60, RARE_USE_TIME, IRON_PELLET, 1.5f, ModCreativeTabs.ALL);
    public static final Item MUSKET = new ItemMusket("musket", 40, RARE_USE_TIME, 5.0f, IRON_PELLET, 1.5f, ModCreativeTabs.ALL);
    public static final Item MAELSTROM_CANNON = new ItemMaelstromCannon("maelstrom_cannon", COMMON_USE_TIME, 1.5f, ModCreativeTabs.ALL);
    public static final Item WILLOTHEWISP_STAFF = new ItemWispStaff("will-o-the-wisp_staff", 40, COMMON_USE_TIME, 1.5f, ModCreativeTabs.ALL);
    public static final Item QUAKE_STAFF = new ItemQuakeStaff("quake_staff", 25, COMMON_USE_TIME, 1.5f, ModCreativeTabs.ALL);

    public static final Item SWORD_OF_SHADES = new ToolLongsword("sword_of_shades", COMMON_SWORD, 1.5f);
    public static final Item SHADOW_DAGGER = new ToolDagger("shadow_dagger", COMMON_DAGGER, 1.5f);
    public static final Item MAELSTROM_BATTLEAXE = new ToolBattleaxe("maelstrom_battleaxe", COMMON_BATTLEAXE, 1.5f);
    public static final Item BEAST_BLADE = new ToolSword("beast_blade", RARE_SWORD, 1.5f);

    public static final Item MAELSTROM_HELMET = new ModArmorBase("maelstrom_helmet", COMMON_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD, 1.5f, "maelstrom");
    public static final Item MAELSTROM_CHESTPLATE = new ModArmorBase("maelstrom_chestplate", COMMON_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST, 1.5f, "maelstrom");
    public static final Item MAELSTROM_LEGGINGS = new ModArmorBase("maelstrom_leggings", COMMON_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS, 1.5f, "maelstrom");
    public static final Item MAELSTROM_BOOTS = new ModArmorBase("maelstrom_boots", COMMON_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET, 1.5f, "maelstrom");

    public static final Item ELK_HIDE_HELMET = new ModArmorBase("elk_hide_helmet", RARE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD, 1, "elk_hide");
    public static final Item ELK_HIDE_CHESTPLATE = new ModArmorBase("elk_hide_chestplate", RARE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST, 1, "elk_hide");
    public static final Item ELK_HIDE_LEGGINGS = new ModArmorBase("elk_hide_leggings", RARE_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS, 1, "elk_hide");
    public static final Item ELK_HIDE_BOOTS = new ModArmorBase("elk_hide_boots", RARE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET, 1, "elk_hide");

    public static final Item CHASMIUM_HELMET = new ModArmorBase("chasmium_helmet", RARE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD, 1.5f, "chasmium");
    public static final Item CHASMIUM_CHESTPLATE = new ModArmorBase("chasmium_chestplate", RARE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST, 1.5f, "chasmium");
    public static final Item CHASMIUM_LEGGINGS = new ModArmorBase("chasmium_leggings", RARE_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS, 1.5f, "chasmium");
    public static final Item CHASMIUM_BOOTS = new ModArmorBase("chasmium_boots", RARE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET, 1.5f, "chasmium");
    public static final Item CHASMIUM_SWORD = new ToolSword("chasmium_sword", RARE_SWORD, 1.5f);

    /*
     * Nexus Items
     */

    // Special Items
    public static final Item PUMPKIN = new ItemPumpkin("pumpkin", 80, RARE_USE_TIME, null, 1.5f, ModCreativeTabs.ALL);
    public static final Item ELUCIDATOR = new ToolLongsword("elucidator", RARE_SWORD, 1.5f);
    public static final Item DRAGON_SLAYER = new ToolDragonslayer("dragon_slayer", RARE_BATTLEAXE, 1.5f);

    static Consumer<List<String>> kanshouBakuya = (tooltip) -> {
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("kanshou_bakuya"));
    };
    public static final Item KANSHOU = new ToolDagger("kanshou", RARE_DAGGER, 2.5f).setInformation(kanshouBakuya);
    public static final Item BAKUYA = new ToolDagger("bakuya", RARE_DAGGER, 2.5f).setInformation(kanshouBakuya);;

    // Nexus Guns
    public static final Item FLINTLOCK = new ItemFlintlock("flintlock_pistol", 40, RARE_USE_TIME, 1, ModCreativeTabs.ALL);
    public static final Item REPEATER = new ItemRepeater("repeater", 60, RARE_USE_TIME, 1, ModCreativeTabs.ALL).setBullet(new RedstoneRepeater());
    public static final Item RIFLE = new ItemRifle("rifle", 60, RARE_USE_TIME, 1, ModCreativeTabs.ALL).setInformation((tooltip) -> {
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("rifle"));
    });
    public static final Item ELK_BLASTER = new ItemRifle("elk_blaster", 60, RARE_USE_TIME, 1.5f, ModCreativeTabs.ALL).setInformation((tooltip) -> {
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("elk_rifle"));
    }).setBullet(new BulletFactory()
    {
	@Override
	public Projectile get(World world, EntityPlayer player, ItemStack stack, float damage)
	{
	    return new ProjectileAzureBullet(world, player, damage, stack);
	}
    });

    // Nexus Swords
    public static final Item FROST_SWORD = new ToolFrostSword("frost_sword", RARE_SWORD, 1.5f);
    public static final Item NEXUS_BATTLEAXE = new ToolBattleaxe("nexus_battleaxe", RARE_BATTLEAXE, 1.5f);
    public static final Item VENOM_DAGGER = new ToolVenomDagger("venom_dagger", RARE_DAGGER, 1.5f);
    public static final Item CRUSADE_SWORD = new ToolCrusadeSword("crusade_sword", RARE_SWORD, 2f);
    public static final Item EXPLOSIVE_DAGGER = new ToolExplosiveDagger("explosive_dagger", RARE_DAGGER, 2.5f);
    public static final Item MAGISTEEL_SWORD = new ItemMagisteelSword("magisteel_sword", RARE_SWORD, 2f);

    // Nexus Magic
    public static final Item LEAP_STAFF = new ItemLeapStaff("leap_staff", 20, RARE_USE_TIME, 1f, ModCreativeTabs.ALL);
    public static final Item SPEED_STAFF = new ItemSpeedStaff("speed_staff", RARE_USE_TIME, 1f, ModCreativeTabs.ALL);
    public static final Item FIREBALL_STAFF = new ItemFireballStaff("fireball_staff", RARE_USE_TIME, 1.5f, ModCreativeTabs.ALL);
    public static final Item EXPLOSIVE_STAFF = new ItemExplosiveStaff("explosive_staff", 60, RARE_USE_TIME, 1f, ModCreativeTabs.ALL);

    // Nexus Armors
    public static final Item STRAW_HAT = new ArmorStrawHat("straw_hat", RARE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD, 1.5f, "straw_hat.png");
    public static final Item SPEED_BOOTS = new ItemSpeedBoots("speed_boots", RARE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET, 1.5f, "speed");

    public static final Item NEXUS_HELMET = new ModArmorBase("nexus_helmet", RARE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD, 1.5f, "nexus");
    public static final Item NEXUS_CHESTPLATE = new ModArmorBase("nexus_chestplate", RARE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST, 1.5f, "nexus");
    public static final Item NEXUS_LEGGINGS = new ModArmorBase("nexus_leggings", RARE_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS, 1.5f, "nexus");
    public static final Item NEXUS_BOOTS = new ModArmorBase("nexus_boots", RARE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET, 1.5f, "nexus");
    public static final Item NYAN_HELMET = new ArmorNyanHelmet("nyan_helmet", RARE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD, 2.5f, "nyan_helmet.png");
    public static final Item NYAN_CHESTPLATE = new ModArmorBase("nyan_chestplate", RARE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST, 2.5f, "nyan");
    public static final Item NYAN_LEGGINGS = new ModArmorBase("nyan_leggings", RARE_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS, 2.5f, "nyan");
    public static final Item NYAN_BOOTS = new ModArmorBase("nyan_boots", RARE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET, 2.5f, "nyan");
    /*
     * Cliff Dimension Items
     */
    public static final Item GOLD_PELLET = new ItemBase("gold_pellet", null);
    public static final Item GOLDEN_FLINTLOCK = new ItemFlintlock("golden_pistol", 40, COMMON_USE_TIME, 2.5f, ModCreativeTabs.ALL).setBullet(new GoldenBullet());
    public static final Item GOLDEN_REPEATER = new ItemRepeater("golden_repeater", 60, COMMON_USE_TIME, 2.5f, ModCreativeTabs.ALL).setBullet(new GoldenRepeater());
    public static final Item GOLDEN_SHOTGUN = new ItemBoomstick("golden_shotgun", 60, COMMON_USE_TIME, IRON_PELLET, 2.5f, ModCreativeTabs.ALL).setBullet(new GoldenBullet());
    public static final Item GOLDEN_RIFLE = new ItemRifle("golden_rifle", 60, COMMON_USE_TIME, 2.5f, ModCreativeTabs.ALL).setBullet(new GoldenBullet());

    public static final Item BLACK_GOLD_HELMET = new ModArmorBase("black_gold_helmet", COMMON_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD, 2.5f, "black_gold");
    public static final Item BLACK_GOLD_CHESTPLATE = new ModArmorBase("black_gold_chestplate", COMMON_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST, 2.5f, "black_gold");
    public static final Item BLACK_GOLD_LEGGINGS = new ModArmorBase("black_gold_leggings", COMMON_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS, 2.5f, "black_gold");
    public static final Item BLACK_GOLD_BOOTS = new ModArmorBase("black_gold_boots", COMMON_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET, 2.5f, "black_gold");

    public static final Item SWAMP_HELMET = new ModArmorBase("swamp_helmet", COMMON_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD, 2, "swamp");
    public static final Item SWAMP_CHESTPLATE = new ModArmorBase("swamp_chestplate", COMMON_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST, 2, "swamp");
    public static final Item SWAMP_LEGGINGS = new ModArmorBase("swamp_leggings", COMMON_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS, 2, "swamp");
    public static final Item SWAMP_BOOTS = new ModArmorBase("swamp_boots", COMMON_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET, 2, "swamp");
    public static final Item SWAMP_SLIME = new ItemTradable("swamp_slime", ModCreativeTabs.ALL);
    public static final Item FLY_WINGS = new ItemTradable("fly_wings", ModCreativeTabs.ALL);

    public static final Item BLACK_GOLD_SWORD = new ToolBlackGoldSword("black_gold_sword", COMMON_SWORD, 2.5f);
    public static final Item BROWNSTONE_SWORD = new ToolSword("brownstone_sword", COMMON_SWORD, 2.0f);
    public static final Item BROWNSTONE_CANNON = new ItemMaelstromCannon("brownstone_cannon", COMMON_USE_TIME, 2f, ModCreativeTabs.ALL).setFactory(new BrownstoneCannon());
    public static final Item GOLDEN_FIREBALL_STAFF = new ItemFireballStaff("golden_fireball_staff", RARE_USE_TIME, 2.5f, ModCreativeTabs.ALL).setFactory(new GoldenFireball());
    public static final Item ANCIENT_BATTLEAXE = new ToolDragonslayer("ancient_battleaxe", COMMON_BATTLEAXE, 2f);

    public static final Item GOLDEN_MAELSTROM_CORE = new ItemTradable("golden_maelstrom_core", ModCreativeTabs.ALL);
    public static final Item CROSS_OF_AQUA = new ItemBase("cross_of_aqua", ModCreativeTabs.ALL)
    {
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
	    tooltip.add(TextFormatting.BLUE + "When held, allows the player to walk on water");
	};
    };
    public static final Item GOLD_STONE_LONGSWORD = new ToolLongsword("gold_stone_longsword", COMMON_SWORD, 2.5f);
    public static final Item METEOR_STAFF = new ItemMeteorStaff("meteor_staff", 50, RARE_USE_TIME, 2f, ModCreativeTabs.ALL);
}
