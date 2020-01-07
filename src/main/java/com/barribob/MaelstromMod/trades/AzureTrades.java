package com.barribob.MaelstromMod.trades;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModItems;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
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
    public static class ConstructElkArmor implements ITradeList
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

	    recipeList.add(new MerchantRecipe(new ItemStack(ModItems.MAELSTROM_CORE), material, sellStack));
	}
    }
    
    public static class ConstructChasmiumArmor implements ITradeList
    {
	@Override
	public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
	{
	    ItemStack sellStack;
	    int requiredMaterial;
	    int armor = random.nextInt(5);

	    switch (armor)
	    {
	    case 0:
		sellStack = new ItemStack(ModItems.CHASMIUM_HELMET);
		requiredMaterial = 5;
		break;
	    case 1:
		sellStack = new ItemStack(ModItems.CHASMIUM_CHESTPLATE);
		requiredMaterial = 8;
		break;
	    case 2:
		sellStack = new ItemStack(ModItems.CHASMIUM_LEGGINGS);
		requiredMaterial = 7;
		break;
	    case 3:
		sellStack = new ItemStack(ModItems.CHASMIUM_SWORD);
		requiredMaterial = 2;
		break;
	    default:
		sellStack = new ItemStack(ModItems.CHASMIUM_BOOTS);
		requiredMaterial = 4;
		break;
	    }

	    ItemStack material = new ItemStack(ModItems.CHASMIUM_INGOT, requiredMaterial);

	    recipeList.add(new MerchantRecipe(new ItemStack(ModItems.MAELSTROM_CORE), material, sellStack));
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
}
