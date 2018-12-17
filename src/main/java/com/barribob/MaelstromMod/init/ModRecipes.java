package com.barribob.MaelstromMod.init;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Adds all of the smelting recipes for the mod
 *
 */
public class ModRecipes 
{
	public static void init() {
		GameRegistry.addSmelting(ModBlocks.DARK_AZURE_STONE, new ItemStack(ModBlocks.LIGHT_AZURE_STONE), 0.0f);
	}
}
