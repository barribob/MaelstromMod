package com.barribob.MaelstromMod.init;

import java.util.Random;

import com.barribob.MaelstromMod.trades.AzureTrades;
import com.barribob.MaelstromMod.trades.NexusTrades;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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

    public static void associateCareersAndTrades()
    {
	AZURE_VILLAGER = new VillagerProfession(Reference.MOD_ID + ":azure_villager", Reference.MOD_ID + ":textures/entity/azure_villager.png",
		"minecraft:textures/entity/zombie_villager/zombie_farmer.png");

	NEXUS_VILLAGER = new VillagerProfession(Reference.MOD_ID + ":nexus_villager", Reference.MOD_ID + ":textures/entity/nexus_villager.png",
		"minecraft:textures/entity/zombie_villager/zombie_farmer.png");

	AZURE_WEAPONSMITH = new VillagerCareer(AZURE_VILLAGER, "azure_weaponsmith");
	AZURE_WEAPONSMITH.addTrade(1, new AzureTrades.ConstructArmor());
	AZURE_WEAPONSMITH.addTrade(1, new AzureTrades.RangedWeapons());
	AZURE_WEAPONSMITH.addTrade(1, new AzureTrades.CrystalsForEmeralds());
	AZURE_WEAPONSMITH.addTrade(1, new EntityVillager.ListEnchantedBookForEmeralds());
	AZURE_WEAPONSMITH.addTrade(1, new AzureTrades.EnchantedIronForEmeralds());

	NEXUS_GUNSMITH = new VillagerCareer(NEXUS_VILLAGER, "nexus_weaponsmith");
	NEXUS_GUNSMITH.addTrade(1, new GeneralTrade(Items.GOLD_INGOT, 5, Items.IRON_INGOT, 6, ModItems.FLINTLOCK, 1));
	NEXUS_GUNSMITH.addTrade(1, new GeneralTrade(Items.REDSTONE, 16, Items.IRON_INGOT, 6, ModItems.REPEATER, 1));
	NEXUS_GUNSMITH.addTrade(1, new GeneralTrade(Items.IRON_INGOT, 6, ModItems.ELK_JERKY, 4, ModItems.RIFLE, 1));
	NEXUS_GUNSMITH.addTrade(1, new GeneralTrade(ModItems.ELK_HIDE, 32, null, 0, ModItems.ELK_BLASTER, 1));
	NEXUS_GUNSMITH.addTrade(1, new NexusTrades.MoreComing());

	NEXUS_MAGE = new VillagerCareer(NEXUS_VILLAGER, "nexus_mage");
	NEXUS_MAGE.addTrade(1, new GeneralTrade(Items.RABBIT_FOOT, 1, Items.DIAMOND, 1, ModItems.LEAP_STAFF, 1));
	NEXUS_MAGE.addTrade(1, new GeneralTrade(Item.getItemFromBlock(Blocks.LAPIS_BLOCK), 1, null, 0, ModItems.SPEED_STAFF, 1));
	NEXUS_MAGE.addTrade(1, new GeneralTrade(Items.BLAZE_ROD, 16, null, 0, ModItems.FIREBALL_STAFF, 1));
	NEXUS_MAGE.addTrade(1, new GeneralTrade(ModItems.SWAMP_SLIME, 32, null, 0, ModItems.CROSS_OF_AQUA, 1));
	NEXUS_MAGE.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 6, Items.GUNPOWDER, 64, ModItems.EXPLOSIVE_STAFF, 1));
	NEXUS_MAGE.addTrade(1, new NexusTrades.MoreComing());

	NEXUS_ARMORER = new VillagerCareer(NEXUS_VILLAGER, "nexus_armorer");
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(Item.getItemFromBlock(Blocks.HAY_BLOCK), 2, Items.DIAMOND, 4, ModItems.STRAW_HAT, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(Items.DIAMOND_BOOTS, 1, ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 2, ModItems.SPEED_BOOTS, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 3, null, 0, ModItems.NEXUS_HELMET, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 5, null, 0, ModItems.NEXUS_CHESTPLATE, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 4, null, 0, ModItems.NEXUS_LEGGINGS, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 2, null, 0, ModItems.NEXUS_BOOTS, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.FLY_WINGS, 32, null, 0, Items.ELYTRA, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.SWAMP_SLIME, 3, ModItems.FLY_WINGS, 2, ModItems.SWAMP_HELMET, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.SWAMP_SLIME, 5, ModItems.FLY_WINGS, 3, ModItems.SWAMP_CHESTPLATE, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.SWAMP_SLIME, 4, ModItems.FLY_WINGS, 2, ModItems.SWAMP_LEGGINGS, 1));
	NEXUS_ARMORER.addTrade(1, new GeneralTrade(ModItems.SWAMP_SLIME, 2, ModItems.FLY_WINGS, 1, ModItems.SWAMP_BOOTS, 1));
	NEXUS_ARMORER.addTrade(1, new NexusTrades.MoreComing());

	NEXUS_SPECIAL_TRADER = new VillagerCareer(NEXUS_VILLAGER, "nexus_saiyan");
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(Items.IRON_INGOT, 16, Item.getItemFromBlock(Blocks.OBSIDIAN), 16, ModItems.ELUCIDATOR, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 12, Item.getItemFromBlock(Blocks.PUMPKIN), 16, ModItems.PUMPKIN, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(Items.IRON_INGOT, 32, null, 0, ModItems.DRAGON_SLAYER, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 5, null, 0, ModItems.BAKUYA, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 5, null, 0, ModItems.KANSHOU, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 3, null, 0, ModItems.NYAN_HELMET, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 5, null, 0, ModItems.NYAN_CHESTPLATE, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 4, null, 0, ModItems.NYAN_LEGGINGS, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 2, null, 0, ModItems.NYAN_BOOTS, 1));
	NEXUS_SPECIAL_TRADER.addTrade(1, new NexusTrades.MoreComing());

	NEXUS_BLADESMITH = new VillagerCareer(NEXUS_VILLAGER, "nexus_bladesmith");
	NEXUS_BLADESMITH.addTrade(1, new GeneralTrade(Items.DIAMOND_SWORD, 1, ModItems.ELK_HIDE, 32, ModItems.FROST_SWORD, 1));
	NEXUS_BLADESMITH.addTrade(1, new GeneralTrade(Items.DIAMOND_SWORD, 1, Items.FERMENTED_SPIDER_EYE, 32, ModItems.VENOM_DAGGER, 1));
	NEXUS_BLADESMITH.addTrade(1, new GeneralTrade(Items.EMERALD, 4, ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 5, ModItems.NEXUS_BATTLEAXE, 1));
	NEXUS_BLADESMITH.addTrade(1, new GeneralTrade(ModItems.SWAMP_SLIME, 16, Items.DIAMOND, 5, ModItems.CRUSADE_SWORD, 1));
	NEXUS_BLADESMITH.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 2, Items.IRON_INGOT, 64, ModItems.MAGISTEEL_SWORD, 1));
	NEXUS_BLADESMITH.addTrade(1, new GeneralTrade(ModItems.GOLDEN_MAELSTROM_CORE, 6, Items.GUNPOWDER, 64, ModItems.EXPLOSIVE_DAGGER, 1));
	NEXUS_BLADESMITH.addTrade(1, new NexusTrades.MoreComing());
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
}
