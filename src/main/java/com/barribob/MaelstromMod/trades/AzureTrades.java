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
		sellStack = new ItemStack(ModItems.ELK_HIDE_HELMET);
		requiredHide = 4;
		break;
	    case 1:
		sellStack = new ItemStack(ModItems.ELK_HIDE_CHESTPLATE);
		requiredHide = 7;
		break;
	    case 2:
		sellStack = new ItemStack(ModItems.ELK_HIDE_LEGGINGS);
		requiredHide = 6;
		break;
	    default:
		sellStack = new ItemStack(ModItems.ELK_HIDE_BOOTS);
		requiredHide = 3;
		break;
	    }

	    ItemStack material = new ItemStack(ModItems.ELK_HIDE, requiredHide);

	    recipeList.add(new MerchantRecipe(material, sellStack));
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
	    switch (random.nextInt(5))
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

	    recipeList.add(new MerchantRecipe(new ItemStack(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, price), sellStack));
	}
    }

    public static class CrystalsForEmeralds implements ITradeList
    {
	@Override
	public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
	{
	    recipeList.add(new MerchantRecipe(new ItemStack(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL), new ItemStack(Items.EMERALD, 3)));
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
		sellStack = new ItemStack(Items.DIAMOND_BOOTS);
		priceInfo = new PriceInfo(4, 5);
		break;
	    case 1:
		sellStack = new ItemStack(Items.DIAMOND_HELMET);
		priceInfo = new PriceInfo(4, 5);
		break;
	    case 2:
		sellStack = new ItemStack(Items.DIAMOND_AXE);
		priceInfo = new PriceInfo(2, 3);
		break;
	    case 3:
		sellStack = new ItemStack(Items.DIAMOND_PICKAXE);
		priceInfo = new PriceInfo(2, 3);
		break;
	    case 4:
		sellStack = new ItemStack(Items.DIAMOND_SWORD);
		priceInfo = new PriceInfo(2, 3);
		break;
	    default:
		sellStack = new ItemStack(Items.DIAMOND_SHOVEL);
		priceInfo = new PriceInfo(1, 2);
		break;
	    }

	    ItemStack itemstack = new ItemStack(ModItems.AZURE_MAELSTROM_CORE_CRYSTAL, priceInfo.getPrice(random), 0);
	    ItemStack itemstack1 = EnchantmentHelper.addRandomEnchantment(random, new ItemStack(sellStack.getItem(), 1, sellStack.getMetadata()), 10 + random.nextInt(10),
		    false);
	    recipeList.add(new MerchantRecipe(itemstack, itemstack1));
	}
    }
}
