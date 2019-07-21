package com.barribob.MaelstromMod.trades;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModItems;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class NexusTrades
{
    public static class MoreComing implements ITradeList
    {
	private ItemStack base = new ItemStack(Item.getItemFromBlock(Blocks.BEDROCK), 1).setStackDisplayName("More Items Coming Soon!");

	@Override
	public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
	{
	    recipeList.add(new MerchantRecipe(base, base));
	}
    }
}
