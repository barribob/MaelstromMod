package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

/**
 * 
 * Keeps track of all the item drop resource locations
 *
 */
public class LootTableHandler
{
    public static final ResourceLocation AZURE_MAELSTROM = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/azure_maelstrom"));
    public static final ResourceLocation ELK = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/elk"));
    public static final ResourceLocation MAELSTROM_ILLAGER = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/maelstrom_illager"));
    public static final ResourceLocation AZURE_GOLEM = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/azure_golem"));
    public static final ResourceLocation BEAST = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/beast"));
    public static final ResourceLocation GOLDEN_BOSS = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/golden_boss"));
    public static final ResourceLocation SWAMP_BOSS = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/swamp_miniboss"));
    public static final ResourceLocation GOLDEN_MAELSTROM = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/golden_maelstrom"));
    public static final ResourceLocation IRON_SHADE = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/iron_shade"));

    public static final ResourceLocation AZURE_FORTRESS = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "chest/azure_fortress"));
    public static final ResourceLocation AZURE_FORTRESS_FORGE = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "chest/azure_fortress_forge"));
    public static final ResourceLocation AZURE_MINESHAFT = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "chest/azure_mineshaft"));
    public static final ResourceLocation STRONGHOLD_KEY_CHEST = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "chest/stronghold_key_chest"));
    public static final ResourceLocation GOLDEN_RUINS = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "chest/golden_ruins"));
    public static final ResourceLocation GOLDEN_RUINS_BOSS = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "chest/golden_ruins_boss"));
    public static final ResourceLocation MAELSTROM_RUINS = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "chest/maelstrom_ruins"));

    // For testing purposes
    public static final ResourceLocation IRON = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "util/iron_level_loot"));
    public static final ResourceLocation DIAMOND = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "util/diamond_level_loot"));
    public static final ResourceLocation MAELSTROM = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "util/maelstrom_level_loot"));
    public static final ResourceLocation TWO = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "util/level_2_loot"));
    public static final ResourceLocation TWOANDHALF = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "util/level_2_half_loot"));
}
