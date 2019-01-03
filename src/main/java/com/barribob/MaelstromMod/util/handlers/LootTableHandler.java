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
	public static final ResourceLocation SHADE = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "shade"));
	public static final ResourceLocation ELK = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "elk"));
}
