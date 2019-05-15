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
    public static final ResourceLocation SHADE = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/shade"));
    public static final ResourceLocation ELK = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/elk"));
    public static final ResourceLocation HORROR = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/horror"));
    public static final ResourceLocation MAELSTROM_ILLAGER = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/maelstrom_illager"));
    public static final ResourceLocation AZURE_GOLEM = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/azure_golem"));
    public static final ResourceLocation FLOATING_SKULL = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/floating_skull"));
    public static final ResourceLocation BEAST = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entity/beast"));

    public static final ResourceLocation AZURE_FORTRESS = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "chest/azure_fortress"));
    public static final ResourceLocation AZURE_MINESHAFT = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "chest/azure_mineshaft"));
    public static final ResourceLocation STRONGHOLD_KEY_CHEST = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "chest/stronghold_key_chest"));
}
