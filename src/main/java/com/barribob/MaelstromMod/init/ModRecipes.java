package com.barribob.MaelstromMod.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Adds all of the smelting recipes for the mod
 */
public class ModRecipes {
    public static void init() {
        GameRegistry.addSmelting(ModItems.ELK_STRIPS, new ItemStack(ModItems.ELK_JERKY), 0.1f);
        GameRegistry.addSmelting(ModBlocks.AZURE_GOLD_ORE, new ItemStack(Items.GOLD_INGOT), 1);
        GameRegistry.addSmelting(ModBlocks.AZURE_IRON_ORE, new ItemStack(Items.IRON_INGOT), 1);
        GameRegistry.addSmelting(ModBlocks.AZURE_DIAMOND_ORE, new ItemStack(Items.DIAMOND), 1);
        GameRegistry.addSmelting(ModBlocks.AZURE_EMERALD_ORE, new ItemStack(Items.EMERALD), 1);
        GameRegistry.addSmelting(ModBlocks.CHASMIUM_ORE, new ItemStack(ModItems.CHASMIUM_INGOT), 1);
    }
}
