package com.barribob.MaelstromMod.init;

import java.util.Random;

import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.init.Items;
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
	
	AZURE_WEAPONSMITH = (new VillagerCareer(AZURE_VILLAGER, "azure_weaponsmith")).addTrade(1, new TradeEmeraldsForArmor());
    }

    /**
     * 
     * A class that allows for easy instantiation of varied drops
     * It's a little overkill for my purposes, but it works nicely still
     *
     */
    public static class TradeEmeraldsForArmor implements ITradeList
    {
	public ItemStack sellStack;
	public EntityVillager.PriceInfo priceInfo;

	public TradeEmeraldsForArmor()
	{
	    sellStack = new ItemStack(ModItems.ORE_CHESPLATE);
	    priceInfo = new PriceInfo(5, 5);
	}

	@Override
	public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
	{
	    int price = 1;

	    if (priceInfo != null)
	    {
		price = priceInfo.getPrice(random);
	    }

	    ItemStack payStack = new ItemStack(Items.EMERALD, price);
	    recipeList.add(new MerchantRecipe(payStack, sellStack));
	}
    }
}
