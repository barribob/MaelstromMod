package com.barribob.MaelstromMod.init;

import java.util.Random;

import com.barribob.MaelstromMod.trades.AzureTrades;
import com.barribob.MaelstromMod.trades.NexusTrades;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

/**
 * 
 * Based on Jabelar's villager profession tutorial
 * https://jabelarminecraft.blogspot.com/p/minecraft-forge-modding-villagers.html
 *
 */
@ObjectHolder(Reference.MOD_ID)
public class ModProfessions
{
    private static VillagerProfession AZURE_VILLAGER = null;
    private static VillagerProfession NEXUS_VILLAGER = null;

    public static VillagerCareer AZURE_WEAPONSMITH;
    public static VillagerCareer NEXUS_GUNSMITH;
    public static VillagerCareer NEXUS_MAGE;
    public static VillagerCareer NEXUS_ARMORER;
    public static VillagerCareer NEXUS_SPECIAL_TRADER;
    public static VillagerCareer NEXUS_BLADESMITH;
    public static VillagerCareer HEROBRINE_ENDER_PEARLS;
    public static VillagerCareer HEROBRINE_CLIFF_KEY;
    public static VillagerCareer HEROBRINE_CRIMSON_KEY;

    public static void associateCareersAndTrades()
    {
	AZURE_VILLAGER = new VillagerProfession(Reference.MOD_ID + ":azure_villager", Reference.MOD_ID + ":textures/entity/azure_villager.png",
		"minecraft:textures/entity/zombie_villager/zombie_farmer.png");

	NEXUS_VILLAGER = new VillagerProfession(Reference.MOD_ID + ":nexus_villager", Reference.MOD_ID + ":textures/entity/nexus_villager.png",
		"minecraft:textures/entity/zombie_villager/zombie_farmer.png");

	AZURE_WEAPONSMITH = new VillagerCareer(AZURE_VILLAGER, "azure_weaponsmith");
	NEXUS_GUNSMITH = new VillagerCareer(NEXUS_VILLAGER, "nexus_weaponsmith");
	NEXUS_MAGE = new VillagerCareer(NEXUS_VILLAGER, "nexus_mage");
	NEXUS_ARMORER = new VillagerCareer(NEXUS_VILLAGER, "nexus_armorer");
	NEXUS_SPECIAL_TRADER = new VillagerCareer(NEXUS_VILLAGER, "nexus_saiyan");
	NEXUS_BLADESMITH = new VillagerCareer(NEXUS_VILLAGER, "nexus_bladesmith");
	
	ItemStack sharpness3Book = new ItemStack(Items.ENCHANTED_BOOK);
	ItemStack sharpness5Book = new ItemStack(Items.ENCHANTED_BOOK);
	ItemEnchantedBook.addEnchantment(sharpness3Book, new EnchantmentData(Enchantments.SHARPNESS, 3));
	ItemEnchantedBook.addEnchantment(sharpness5Book, new EnchantmentData(Enchantments.SHARPNESS, 5));

	/**
	 * Level 0 (Pre - Azure) trades
	 */

	NEXUS_GUNSMITH.addTrade(1, new GeneralTrade(ModItems.MAELSTROM_CORE, 1, null, 0, ModItems.AMMO_CASE, 1));
	NEXUS_GUNSMITH.addTrade(1, new GeneralTrade(ModItems.MAELSTROM_CORE, 1, Items.IRON_INGOT, 6, ModItems.FLINTLOCK, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(Item.getItemFromBlock(Blocks.HAY_BLOCK), 1, ModItems.MAELSTROM_CORE, 1, ModItems.STRAW_HAT, 1));
	NEXUS_BLADESMITH.addTrade(1, new GeneralTrade(ModItems.MAELSTROM_CORE, 1, Items.SPIDER_EYE, 16, ModItems.VENOM_DAGGER, 1));
	NEXUS_BLADESMITH.addTrade(1, new StackTrade(new ItemStack(ModItems.MAELSTROM_CORE, 2), sharpness3Book));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.MAELSTROM_CORE, 1, Items.DIAMOND, 5, ModItems.NEXUS_HELMET, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.MAELSTROM_CORE, 1, Items.DIAMOND, 8, ModItems.NEXUS_CHESTPLATE, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.MAELSTROM_CORE, 1, Items.DIAMOND, 7, ModItems.NEXUS_LEGGINGS, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.MAELSTROM_CORE, 1, Items.DIAMOND, 4, ModItems.NEXUS_BOOTS, 1));

	/**
	 * Level 1 (Azure overworld) trades
	 */

	AZURE_WEAPONSMITH.addTrade(1, new AzureTrades.ConstructElkArmor());
	AZURE_WEAPONSMITH.addTrade(1, new AzureTrades.ConstructChasmiumArmor());
	AZURE_WEAPONSMITH.addTrade(1, new AzureTrades.RangedWeapons());
	AZURE_WEAPONSMITH.addTrade(1, new GeneralTrade(ModItems.CHASMIUM_INGOT, 2, null, 0, ModItems.CHASMIUM_AMMO_CASE, 1));

	NEXUS_GUNSMITH.addTrade(1, new GeneralTrade(ModItems.MAELSTROM_CORE, 3, Items.REDSTONE, 16, ModItems.REPEATER, 1));
	NEXUS_GUNSMITH.addTrade(1, new GeneralTrade(ModItems.MAELSTROM_CORE, 3, Items.IRON_INGOT, 8, ModItems.RIFLE, 1));
	NEXUS_GUNSMITH.addTrade(1, new GeneralTrade(ModItems.CHASMIUM_INGOT, 2, null, 0, ModItems.CHASMIUM_AMMO_CASE, 1));

	NEXUS_ARMORER.addTrade(1, new GeneralTrade(Items.DIAMOND_BOOTS, 1, ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 2, ModItems.SPEED_BOOTS, 1));

	NEXUS_BLADESMITH.addTrade(1, new GeneralTrade(ModItems.MAELSTROM_CORE, 3, null, 0, ModItems.NEXUS_BATTLEAXE, 1));
	NEXUS_BLADESMITH.addTrade(1, new StackTrade(new ItemStack(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 2), sharpness5Book));

	/**
	 * Level 2 (Azure endgame)
	 */

	AZURE_WEAPONSMITH.addTrade(1, new GeneralTrade(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 3, ModItems.ELK_HIDE, 16, ModItems.ELK_BLASTER, 1));
	AZURE_WEAPONSMITH.addTrade(1, new GeneralTrade(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 4, ModItems.ELK_HIDE, 16, ModItems.FROST_SWORD, 1));

	NEXUS_MAGE.addTrade(1, new GeneralTrade(ModItems.MINOTAUR_HORN, 1, null, 0, ModItems.CATALYST, 1));
	NEXUS_MAGE.addTrade(1, new GeneralTrade(ModItems.MINOTAUR_HORN, 1, null, 0, ModItems.FIREBALL_STAFF, 1));
	NEXUS_MAGE.addTrade(1, new GeneralTrade(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 4, null, 0, ModItems.LEAP_STAFF, 1));
	NEXUS_MAGE.addTrade(1, new GeneralTrade(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 4, null, 0, ModItems.SPEED_STAFF, 1));

	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.MAELSTROM_CORE, 5, Item.getItemFromBlock(Blocks.OBSIDIAN), 16, ModItems.ELUCIDATOR, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 4, Item.getItemFromBlock(Blocks.PUMPKIN), 16, ModItems.PUMPKIN, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.MAELSTROM_CORE, 6, null, 0, ModItems.DRAGON_SLAYER, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 5, null, 0, ModItems.NEW_WORLD_RECORD, 1));

	/**
	 * Level 3 (Cliff Overworld)
	 */
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.SWAMP_SLIME, 5, ModItems.FLY_WINGS, 2, ModItems.SWAMP_HELMET, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.SWAMP_SLIME, 8, ModItems.FLY_WINGS, 3, ModItems.SWAMP_CHESTPLATE, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.SWAMP_SLIME, 7, ModItems.FLY_WINGS, 2, ModItems.SWAMP_LEGGINGS, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.SWAMP_SLIME, 4, ModItems.FLY_WINGS, 1, ModItems.SWAMP_BOOTS, 1));

	NEXUS_BLADESMITH.addTrade(1, new GeneralTrade(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 2, ModItems.GOLDEN_MAELSTROM_CORE, 2, ModItems.CRUSADE_SWORD, 1));
	NEXUS_BLADESMITH.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 2, Items.IRON_INGOT, 64, ModItems.MAGISTEEL_SWORD, 1));

	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.FLY_WINGS, 32, null, 0, Items.ELYTRA, 1));

	NEXUS_MAGE.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 2, null, 0, ModItems.GOLDEN_QUAKE_STAFF, 1));

	/**
	 * Level 4 (Cliff Endgame)
	 */

	NEXUS_GUNSMITH.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 1, null, 0, ModItems.BLACK_GOLD_AMMO_CASE, 1));

	NEXUS_MAGE.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 2, ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 2, ModItems.CROSS_OF_AQUA, 1));
	NEXUS_MAGE.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 6, Items.GUNPOWDER, 64, ModItems.EXPLOSIVE_STAFF, 1));


	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 4, ModItems.MAELSTROM_CORE, 5, ModItems.BAKUYA, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 4, ModItems.MAELSTROM_CORE, 5, ModItems.KANSHOU, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 3, ModItems.MAELSTROM_CORE, 4, ModItems.NYAN_HELMET, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 5, ModItems.MAELSTROM_CORE, 6, ModItems.NYAN_CHESTPLATE, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 4, ModItems.MAELSTROM_CORE, 6, ModItems.NYAN_LEGGINGS, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 2, ModItems.MAELSTROM_CORE, 4, ModItems.NYAN_BOOTS, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 5, null, 0, ModItems.WANDERING_RECORD, 1));

	NEXUS_BLADESMITH.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 6, Items.GUNPOWDER, 64, ModItems.EXPLOSIVE_DAGGER, 1));

	/**
	 * Level 5 (Crimson Start)
	 */

	NEXUS_GUNSMITH.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 1, null, 0, ModItems.CRIMSON_AMMO_CASE, 1));
	NEXUS_GUNSMITH.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 2, ModItems.MAELSTROM_CORE, 3, ModItems.ENERGIZED_PISTOL, 1));
	NEXUS_GUNSMITH.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 2, ModItems.MAELSTROM_CORE, 3, ModItems.ENERGIZED_SHOTGUN, 1));
	NEXUS_GUNSMITH.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 2, ModItems.MAELSTROM_CORE, 3, ModItems.ENERGIZED_REPEATER, 1));

	NEXUS_MAGE.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 3, ModItems.MAELSTROM_CORE, 3, ModItems.CRIMSON_RUNE_STAFF, 1));
	NEXUS_MAGE.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 1, ModItems.MAELSTROM_CORE, 3, ModBlocks.STONEBRICK_BLOCKVOID, 1));

	NEXUS_BLADESMITH.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 1, ModItems.MAELSTROM_CORE, 4, ModItems.FADESTEEL_SWORD, 1));
	NEXUS_BLADESMITH.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 3, ModItems.MAELSTROM_CORE, 5, ModItems.ENERGETIC_STEEL_CLEAVER, 1));

	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 1, ModItems.MAELSTROM_CORE, 5, ModItems.FADESTEEL_HELMET, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 1, ModItems.MAELSTROM_CORE, 8, ModItems.FADESTEEL_CHESTPLATE, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 1, ModItems.MAELSTROM_CORE, 7, ModItems.FADESTEEL_LEGGINGS, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 1, ModItems.MAELSTROM_CORE, 4, ModItems.FADESTEEL_BOOTS, 1));

	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 4, ModItems.MAELSTROM_CORE, 5, ModItems.ELYSIUM_HELMET, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 7, ModItems.MAELSTROM_CORE, 8, ModItems.ELYSIUM_CHESTPLATE, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 6, ModItems.MAELSTROM_CORE, 7, ModItems.ELYSIUM_LEGGINGS, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.CRIMSON_MAELSTROM_CORE, 3, ModItems.MAELSTROM_CORE, 4, ModItems.ELYSIUM_BOOTS, 1));

	NEXUS_BLADESMITH.addTrade(1, new NexusTrades.MoreComing());
	NEXUS_SPECIAL_TRADER.addTrade(1, new NexusTrades.MoreComing());
	NEXUS_ARMORER.addTrade(1, new NexusTrades.MoreComing());
	NEXUS_MAGE.addTrade(1, new NexusTrades.MoreComing());
	NEXUS_GUNSMITH.addTrade(1, new NexusTrades.MoreComing());

	HEROBRINE_ENDER_PEARLS = new VillagerCareer(NEXUS_VILLAGER, "herobrine_pearls");
	HEROBRINE_ENDER_PEARLS.addTrade(1, new GeneralTrade(Items.ENDER_PEARL, 8, null, 0, ModItems.AZURE_KEY, 1));

	HEROBRINE_CLIFF_KEY = new VillagerCareer(NEXUS_VILLAGER, "herobrine_cliff");
	HEROBRINE_CLIFF_KEY.addTrade(1, new GeneralTrade(ModItems.CLIFF_KEY_FRAGMENT, 2, null, 0, ModItems.BROWN_KEY, 1));

	HEROBRINE_CRIMSON_KEY = new VillagerCareer(NEXUS_VILLAGER, "herobrine_crimson");
	HEROBRINE_CRIMSON_KEY.addTrade(1, new GeneralTrade(ModItems.RED_KEY_FRAGMENT, 1, null, 0, ModItems.RED_KEY, 1));
    }

    public static class GeneralTrade implements ITradeList
    {
	private final ItemStack base;
	private final ItemStack cost;
	private final ItemStack reward;

	public GeneralTrade(Item cost, int amount, Item cost2, int amount2, Item reward, int amount3)
	{
	    base = new ItemStack(cost, amount);
	    if (cost2 != null)
	    {
		this.cost = new ItemStack(cost2, amount2);
	    }
	    else
	    {
		this.cost = null;
	    }
	    this.reward = new ItemStack(reward, amount3);
	}

	@Override
	public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
	{
	    if (cost != null)
	    {
		recipeList.add(new MerchantRecipe(base, cost, reward));
	    }
	    else
	    {
		recipeList.add(new MerchantRecipe(base, reward));
	    }
	}
    }

    public static class StackTrade implements ITradeList
    {
	private final ItemStack base;
	private final ItemStack cost;
	private final ItemStack reward;

	public StackTrade(ItemStack base, ItemStack cost, ItemStack reward)
	{
	    this.base = base;
	    this.cost = cost;
	    this.reward = reward;
	}
	
	public StackTrade(ItemStack base, ItemStack reward)
	{
	    this(base, null, reward);
	}


	@Override
	public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
	{
	    if (cost != null)
	    {
		recipeList.add(new MerchantRecipe(base, cost, reward));
	    }
	    else
	    {
		recipeList.add(new MerchantRecipe(base, reward));
	    }
	}
    }
}
