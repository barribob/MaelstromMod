package com.barribob.MaelstromMod.init;

import java.util.ArrayList;
import java.util.List;

import com.barribob.MaelstromMod.blocks.BlockLeavesBase;
import com.barribob.MaelstromMod.blocks.BlockBase;
import com.barribob.MaelstromMod.blocks.BlockLogBase;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * 
 * Holds all of our new blocks
 *
 */
public class ModBlocks 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block DARK_AZURE_STONE = new BlockBase("dark_azure_stone", Material.ROCK, 1.7f, 35, SoundType.STONE);
	public static final Block LIGHT_AZURE_STONE = new BlockBase("light_azure_stone", Material.ROCK, 1.7f, 35, SoundType.STONE);
	public static final Block AZURE_GRASS = new BlockBase("azure_grass", Material.GRASS, 0.7f, 1, SoundType.GROUND);
	public static final Block AZURE_LOG = new BlockLogBase("azure_log", 1.0f, 10, SoundType.WOOD);
	public static final Block AZURE_LEAVES = new BlockLeavesBase("azure_leaves", 0.5f, 0.5f, SoundType.PLANT);
}
