package com.barribob.MaelstromMod.trades;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModItems;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class AzureTrades
{
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
	    int armor = random.nextInt(5);

	    switch (armor)
	    {
	    case 0:
		sellStack = new ItemStack(Items.DIAMOND_HELMET);
		requiredHide = 5;
		break;
	    case 1:
		sellStack = new ItemStack(Items.DIAMOND_CHESTPLATE);
		requiredHide = 8;
		break;
	    case 2:
		sellStack = new ItemStack(Items.DIAMOND_LEGGINGS);
		requiredHide = 7;
		break;
	    case 3:
		sellStack = new ItemStack(Items.DIAMOND_SWORD);
		requiredHide = 2;
		break;
	    default:
		sellStack = new ItemStack(Items.DIAMOND_BOOTS);
		requiredHide = 4;
		break;
	    }

	    ItemStack material1 = new ItemStack(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, 2);
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
