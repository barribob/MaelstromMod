package com.barribob.MaelstromMod.init;

import java.util.ArrayList;
import java.util.List;

import com.barribob.MaelstromMod.blocks.BlockAzureFlower;
import com.barribob.MaelstromMod.blocks.BlockAzureGrass;
import com.barribob.MaelstromMod.blocks.BlockAzureTallGrass;
import com.barribob.MaelstromMod.blocks.BlockAzureVines;
import com.barribob.MaelstromMod.blocks.BlockAzureVinesBlock;
import com.barribob.MaelstromMod.blocks.BlockBase;
import com.barribob.MaelstromMod.blocks.BlockDoubleBrownedGrass;
import com.barribob.MaelstromMod.blocks.BlockFenceBase;
import com.barribob.MaelstromMod.blocks.BlockLeavesBase;
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
	
	/**
	 * Blocks for the azure dimension
	 */
	public static final Block DARK_AZURE_STONE = new BlockBase("dark_azure_stone", Material.ROCK, 1.7f, 35, SoundType.STONE);
	public static final Block LIGHT_AZURE_STONE = new BlockBase("light_azure_stone", Material.ROCK, 1.7f, 35, SoundType.STONE);
	public static final Block AZURE_GRASS = new BlockAzureGrass("azure_grass", Material.GRASS, 0.7f, 1, SoundType.GROUND);
	public static final Block AZURE_LOG = new BlockLogBase("azure_log", 1.0f, 10, SoundType.WOOD);
	public static final Block AZURE_LEAVES = new BlockLeavesBase("azure_leaves", 0.5f, 0.5f, SoundType.PLANT);
	public static final Block PLUM_LOG = new BlockLogBase("plum_log", 1.0f, 10, SoundType.WOOD);
	public static final Block PLUM_LEAVES = new BlockLeavesBase("plum_leaves", 0.5f, 0.5f, SoundType.PLANT);
	public static final Block AZURE_VINES_BLOCK = new BlockAzureVinesBlock("azure_vines_block", Material.PLANTS, 0.5f, 0.5f, SoundType.PLANT);
	public static final Block AZURE_VINES = new BlockAzureVines("azure_vines", 0.5f, 0.5f, SoundType.PLANT);
	public static final Block BROWNED_GRASS = new BlockAzureTallGrass("browned_grass", Material.PLANTS, 0.5f, 0.5f, SoundType.PLANT);
	public static final Block BLUE_DAISY = new BlockAzureFlower("blue_daisy", Material.PLANTS, 0.5f, 0.5f, SoundType.PLANT);
	public static final Block RUBY_ORCHID = new BlockAzureFlower("ruby_orchid", Material.PLANTS, 0.5f, 0.5f, SoundType.PLANT);
	public static final Block DOUBLE_BROWNED_GRASS = new BlockDoubleBrownedGrass("double_browned_grass", Material.PLANTS, 0.5f, 0.5f, SoundType.PLANT);
	public static final Block AZURE_PLANKS = new BlockBase("azure_planks", Material.WOOD, 1.0f, 10, SoundType.WOOD);
	public static final Block AZURE_FENCE = new BlockFenceBase("azure_fence", Material.WOOD, 1.0f, 10, SoundType.WOOD);
}
