package com.barribob.MaelstromMod.init;

import java.util.Random;

import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.IForgeRegistry;

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

    public static VillagerCareer AZURE_WEAPONSMITH;

    public static void associateCareersAndTrades()
    {
	AZURE_VILLAGER = new VillagerProfession(Reference.MOD_ID + ":azure_villager", Reference.MOD_ID + ":textures/entity/azure_villager.png",
		"minecraft:textures/entity/zombie_villager/zombie_farmer.png");

	AZURE_WEAPONSMITH = (new VillagerCareer(AZURE_VILLAGER, "azure_weaponsmith")).addTrade(1, new ConstructArmor()).addTrade(1, new RangedWeapons())
		.addTrade(1, new CrystalsForEmeralds()).addTrade(1, new EntityVillager.ListEnchantedBookForEmeralds()).addTrade(1, new EnchantedIronForEmeralds());
    }

    /**
     * 
     * A class that allows for easy instantiation of varied drops It's a little
     * overkill for my purposes, but it works nicely still
     *
     */
    public static class ConstructArmor implements ITradeList
    {
	@Override
	public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
	{
	    ItemStack sellStack;
	    int requiredHide;
	    int armor = random.nextInt(4);

	    switch (armor)
	    {
	    case 0:
		sellStack = new ItemStack(ModItems.MAELSTROM_HELMET);
		requiredHide = 5;
		break;
	    case 1:
		sellStack = new ItemStack(ModItems.MAELSTROM_CHESTPLATE);
		requiredHide = 8;
		break;
	    case 2:
		sellStack = new ItemStack(ModItems.MAELSTROM_LEGGINGS);
		requiredHide = 7;
		break;
	    default:
		sellStack = new ItemStack(ModItems.MAELSTROM_BOOTS);
		requiredHide = 4;
		break;
	    }

	    ItemStack material1 = new ItemStack(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 1);
	    ItemStack material2 = new ItemStack(ModItems.ELK_HIDE, requiredHide);

	    recipeList.add(new MerchantRecipe(material1, material2, sellStack));
	}
    }

    public static class RangedWeapons implements ITradeList
    {
	public PriceInfo priceInfo;

	public RangedWeapons()
	{
	    priceInfo = new PriceInfo(5, 7);
	}

	@Override
	public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
	{
	    int price = 1;
	    ItemStack sellStack;
	    switch (random.nextInt(3))
	    {
	    case 0:
		sellStack = new ItemStack(ModItems.BOOMSTICK);
		break;
	    case 1:
		sellStack = new ItemStack(ModItems.MUSKET);
		break;
	    default:
		sellStack = new ItemStack(ModItems.QUAKE_STAFF);
	    }

	    if (priceInfo != null)
	    {
		price = priceInfo.getPrice(random);
	    }

	    recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, price), sellStack));
	}
    }

    public static class CrystalsForEmeralds implements ITradeList
    {
	@Override
	public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
	{
	    recipeList.add(new MerchantRecipe(new ItemStack(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL), new ItemStack(Items.EMERALD, 2)));
	}
    }

    public static class EnchantedIronForEmeralds implements EntityVillager.ITradeList
    {
	public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
	{
	    ItemStack sellStack;
	    PriceInfo priceInfo;
	    int item = random.nextInt(6);

	    switch (item)
	    {
	    case 0:
		sellStack = new ItemStack(Items.IRON_PICKAXE);
		priceInfo = new PriceInfo(6, 7);
		break;
	    case 1:
		sellStack = new ItemStack(Items.IRON_SWORD);
		priceInfo = new PriceInfo(5, 6);
		break;
	    case 2:
		sellStack = new ItemStack(Items.IRON_AXE);
		priceInfo = new PriceInfo(4, 5);
		break;
	    case 3:
		sellStack = new ItemStack(Items.DIAMOND_PICKAXE);
		priceInfo = new PriceInfo(12, 15);
		break;
	    case 4:
		sellStack = new ItemStack(Items.DIAMOND_SWORD);
		priceInfo = new PriceInfo(10, 13);
		break;
	    default:
		sellStack = new ItemStack(Items.IRON_SHOVEL);
		priceInfo = new PriceInfo(4, 5);
		break;
	    }

	    ItemStack itemstack = new ItemStack(Items.EMERALD, priceInfo.getPrice(random), 0);
	    ItemStack itemstack1 = EnchantmentHelper.addRandomEnchantment(random, new ItemStack(sellStack.getItem(), 1, sellStack.getMetadata()), 10 + random.nextInt(10),
		    false);
	    recipeList.add(new MerchantRecipe(itemstack, itemstack1));
	}
    }
}
