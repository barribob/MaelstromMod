package com.barribob.MaelstromMod.init;

import java.util.ArrayList;
import java.util.List;

import com.barribob.MaelstromMod.blocks.BlockAzureFlower;
import com.barribob.MaelstromMod.blocks.BlockAzureGrass;
import com.barribob.MaelstromMod.blocks.BlockAzureRedstoneOre;
import com.barribob.MaelstromMod.blocks.BlockAzureTallGrass;
import com.barribob.MaelstromMod.blocks.BlockAzureVines;
import com.barribob.MaelstromMod.blocks.BlockAzureVinesBlock;
import com.barribob.MaelstromMod.blocks.BlockBase;
import com.barribob.MaelstromMod.blocks.BlockDoubleBrownedGrass;
import com.barribob.MaelstromMod.blocks.BlockFenceBase;
import com.barribob.MaelstromMod.blocks.BlockLeavesBase;
import com.barribob.MaelstromMod.blocks.BlockLogBase;
import com.barribob.MaelstromMod.blocks.BlockMaelstrom;
import com.barribob.MaelstromMod.blocks.BlockMaelstromCore;
import com.barribob.MaelstromMod.blocks.BlockAzureOre;
import com.barribob.MaelstromMod.blocks.BlockStairsBase;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

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
	public static final Block LIGHT_AZURE_STONE = new BlockBase("light_azure_stone", Material.ROCK, 1.7f, 35, SoundType.STONE).setLightLevel(1.0f);
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
	public static final Block AZURE_MAELSTROM = new BlockMaelstrom("azure_maelstrom", Material.ROCK, 1.7f, 35, SoundType.STONE, 1).setLightLevel(0.5f);
	public static final Block AZURE_MAELSTROM_CORE = new BlockMaelstromCore("azure_maelstrom_core", Material.ROCK, 2.7f, 35, SoundType.STONE);
	public static final Block MAELSTROM_BRICKS = new BlockBase("maelstrom_bricks", Material.ROCK, 2.0f, 40, SoundType.STONE);
	public static final Block MAELSTROM_BRICK_FENCE = new BlockFenceBase("maelstrom_brick_fence", Material.ROCK, 2.0f, 40, SoundType.STONE);
	public static final Block MAELSTROM_BRICK_STAIRS = new BlockStairsBase("maelstrom_brick_stairs", MAELSTROM_BRICKS.getDefaultState(), 2.0f, 40, SoundType.STONE);
	public static final Block AZURE_COAL_ORE = new BlockAzureOre("azure_coal_ore", 3.0f, 40, SoundType.STONE);
	public static final Block AZURE_DIAMOND_ORE = new BlockAzureOre("azure_diamond_ore", 3.0f, 40, SoundType.STONE);
	public static final Block AZURE_EMERALD_ORE = new BlockAzureOre("azure_emerald_ore", 3.0f, 40, SoundType.STONE);
	public static final Block AZURE_LAPIS_ORE = new BlockAzureOre("azure_lapis_ore", 3.0f, 40, SoundType.STONE);
	public static final Block AZURE_IRON_ORE = new BlockAzureOre("azure_iron_ore", 3.0f, 40, SoundType.STONE);
	public static final Block AZURE_GOLD_ORE = new BlockAzureOre("azure_gold_ore", 3.0f, 40, SoundType.STONE);
	public static final Block AZURE_REDSTONE_ORE = new BlockAzureRedstoneOre("azure_redstone_ore", 3.0f, 40, SoundType.STONE).setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	public static final Block AZURE_LIT_REDSTONE_ORE = new BlockAzureRedstoneOre("azure_lit_redstone_ore", 3.0f, 40, SoundType.STONE).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setLightLevel(0.3f);
}
