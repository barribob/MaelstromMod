package com.barribob.MaelstromMod.init;

import java.util.ArrayList;
import java.util.List;

import com.barribob.MaelstromMod.blocks.BlockAzureFlower;
import com.barribob.MaelstromMod.blocks.BlockAzureGrass;
import com.barribob.MaelstromMod.blocks.BlockAzureLeaves;
import com.barribob.MaelstromMod.blocks.BlockAzureOre;
import com.barribob.MaelstromMod.blocks.BlockAzurePortal;
import com.barribob.MaelstromMod.blocks.BlockAzureRedstoneOre;
import com.barribob.MaelstromMod.blocks.BlockAzureTallGrass;
import com.barribob.MaelstromMod.blocks.BlockAzureVines;
import com.barribob.MaelstromMod.blocks.BlockAzureVinesBlock;
import com.barribob.MaelstromMod.blocks.BlockBase;
import com.barribob.MaelstromMod.blocks.BlockDecayingMaelstrom;
import com.barribob.MaelstromMod.blocks.BlockDisappearingSpawner;
import com.barribob.MaelstromMod.blocks.BlockDoubleBrownedGrass;
import com.barribob.MaelstromMod.blocks.BlockFenceBase;
import com.barribob.MaelstromMod.blocks.BlockLogBase;
import com.barribob.MaelstromMod.blocks.BlockMaelstrom;
import com.barribob.MaelstromMod.blocks.BlockMaelstromCore;
import com.barribob.MaelstromMod.blocks.BlockMegaStructure;
import com.barribob.MaelstromMod.blocks.BlockNexusTeleporter;
import com.barribob.MaelstromMod.blocks.BlockPlumFilledLeaves;
import com.barribob.MaelstromMod.blocks.BlockPlumLeaves;
import com.barribob.MaelstromMod.blocks.BlockSaplingBase;
import com.barribob.MaelstromMod.blocks.BlockStairsBase;
import com.barribob.MaelstromMod.blocks.key_blocks.BlockAzureKey;
import com.barribob.MaelstromMod.blocks.key_blocks.BlockKey;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenBigPlumTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenPlumTree;

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
	public static final Block DARK_AZURE_STONE = new BlockBase("dark_azure_stone", Material.ROCK, 1.7f, 35, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block LIGHT_AZURE_STONE = new BlockBase("light_azure_stone", Material.ROCK, 1.7f, 35, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL).setLightLevel(1.0f);
	
	// Plants
	public static final Block AZURE_GRASS = new BlockAzureGrass("azure_grass", Material.GRASS, 0.7f, 1, SoundType.GROUND).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_LOG = new BlockLogBase("azure_log", 1.0f, 10, SoundType.WOOD).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_LEAVES = new BlockAzureLeaves("azure_leaves", 0.5f, 0.5f, SoundType.PLANT).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_SAPLING = new BlockSaplingBase("azure_sapling", 0.5f, 0.5f, SoundType.PLANT, new WorldGenAzureTree(true));
	public static final Block PLUM_LOG = new BlockLogBase("plum_log", 1.0f, 10, SoundType.WOOD).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block PLUM_LEAVES = new BlockPlumLeaves("plum_leaves", 0.5f, 0.5f, SoundType.PLANT).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block PLUM_FILLED_LEAVES = new BlockPlumFilledLeaves("plum_filled_leaves", 0.5f, 0.5f, SoundType.PLANT).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block PLUM_SAPLING = new BlockSaplingBase("plum_sapling", 0.5f, 0.5f, SoundType.PLANT, new WorldGenPlumTree(true, true));
	public static final Block LARGE_PLUM_SAPLING = new BlockSaplingBase("large_plum_sapling", 0.5f, 0.5f, SoundType.PLANT, new WorldGenBigPlumTree(true));
	public static final Block AZURE_VINES_BLOCK = new BlockAzureVinesBlock("azure_vines_block", Material.PLANTS, 0.5f, 0.5f, SoundType.PLANT).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_VINES = new BlockAzureVines("azure_vines", 0.5f, 0.5f, SoundType.PLANT).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block BROWNED_GRASS = new BlockAzureTallGrass("browned_grass", Material.PLANTS, 0.5f, 0.5f, SoundType.PLANT).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block BLUE_DAISY = new BlockAzureFlower("blue_daisy", Material.PLANTS, 0.5f, 0.5f, SoundType.PLANT).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block RUBY_ORCHID = new BlockAzureFlower("ruby_orchid", Material.PLANTS, 0.5f, 0.5f, SoundType.PLANT).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block DOUBLE_BROWNED_GRASS = new BlockDoubleBrownedGrass("double_browned_grass", Material.PLANTS, 0.5f, 0.5f, SoundType.PLANT).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_PLANKS = new BlockBase("azure_planks", Material.WOOD, 1.0f, 10, SoundType.WOOD).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_FENCE = new BlockFenceBase("azure_fence", Material.WOOD, 1.0f, 10, SoundType.WOOD).setCreativeTab(ModCreativeTabs.ALL);
	
	// Maelstrom
	public static final Block AZURE_MAELSTROM = new BlockMaelstrom("azure_maelstrom", Material.ROCK, 1.7f, 35, SoundType.STONE, 1).setLightLevel(0.5f).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block DECAYING_AZURE_MAELSTROM = new BlockDecayingMaelstrom("azure_decaying_maelstrom", 1.7f, 35, SoundType.STONE, 1).setLightLevel(0.5f);
	public static final Block AZURE_MAELSTROM_CORE = new BlockMaelstromCore("azure_maelstrom_core", Material.ROCK, 2.7f, 35, SoundType.STONE);
	public static final Block MAELSTROM_BRICKS = new BlockBase("maelstrom_bricks", Material.ROCK, 2.0f, 40, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block MAELSTROM_BRICK_FENCE = new BlockFenceBase("maelstrom_brick_fence", Material.ROCK, 2.0f, 40, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block MAELSTROM_BRICK_STAIRS = new BlockStairsBase("maelstrom_brick_stairs", MAELSTROM_BRICKS.getDefaultState(), 2.0f, 40, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	
	// Ore
	public static final Block AZURE_COAL_ORE = new BlockAzureOre("azure_coal_ore", 3.0f, 40, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_DIAMOND_ORE = new BlockAzureOre("azure_diamond_ore", 3.0f, 40, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_EMERALD_ORE = new BlockAzureOre("azure_emerald_ore", 3.0f, 40, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_LAPIS_ORE = new BlockAzureOre("azure_lapis_ore", 3.0f, 40, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_IRON_ORE = new BlockAzureOre("azure_iron_ore", 3.0f, 40, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_GOLD_ORE = new BlockAzureOre("azure_gold_ore", 3.0f, 40, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_REDSTONE_ORE = new BlockAzureRedstoneOre("azure_redstone_ore", 3.0f, 40, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_LIT_REDSTONE_ORE = new BlockAzureRedstoneOre("azure_lit_redstone_ore", 3.0f, 40, SoundType.STONE).setLightLevel(0.3f);
	
	// Overworld
	public static final Block MAELSTROM_STONEBRICK = new BlockBase("maelstrom_stonebrick", Material.ROCK, 1.7f, 35, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block MAELSTROM_STONEBRICK_STAIRS = new BlockStairsBase("maelstrom_stonebrick_stairs", MAELSTROM_STONEBRICK.getDefaultState(), 1.7f, 35, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block MAELSTROM_STONEBRICK_FENCE = new BlockFenceBase("maelstrom_stonebrick_fence", Material.ROCK, 1.7f, 35, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_PORTAL = new BlockAzurePortal("azure_portal", Material.ROCK, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	
	// Maelstrom Fortress
	public static final Block AZURE_STONEBRICK = new BlockBase("azure_stonebrick", Material.ROCK, 0f, 10000f, SoundType.STONE).setBlockUnbreakable().setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_STONEBRICK_STAIRS = new BlockStairsBase("azure_stonebrick_stairs", AZURE_STONEBRICK.getDefaultState(), 0f, 10000f, SoundType.STONE).setBlockUnbreakable().setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_STONEBRICK_CRACKED = new BlockBase("azure_stonebrick_cracked", Material.ROCK, 0f, 10000f, SoundType.STONE).setBlockUnbreakable().setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_STONEBRICK_CARVED = new BlockBase("azure_stonebrick_carved", Material.ROCK, 0f, 10000f, SoundType.STONE).setBlockUnbreakable().setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_STONEBRICK_CARVED_2 = new BlockBase("azure_stonebrick_carved_2", Material.ROCK, 0f, 10000f, SoundType.STONE).setBlockUnbreakable().setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_STONEBRICK_CARVED_3 = new BlockBase("azure_stonebrick_carved_3", Material.ROCK, 0f, 10000f, SoundType.STONE).setBlockUnbreakable().setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_STONEBRICK_LIT = new BlockBase("azure_stonebrick_lit", Material.ROCK, 0f, 10000f, SoundType.STONE).setBlockUnbreakable().setLightLevel(1.0f).setCreativeTab(ModCreativeTabs.ALL);
	
	// Util
	public static final Block MEGA_STRUCTURE_BLOCK = new BlockMegaStructure("mega_structure_block");
	public static final Block DISAPPEARING_SPAWNER = new BlockDisappearingSpawner("disappearing_spawner", Material.ROCK);
	
	// Nexus
	public static final Block NEXUS_TELEPORTER = new BlockNexusTeleporter("nexus_teleporter", Material.ROCK, SoundType.STONE).setLightLevel(1.0f).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block CRACKED_QUARTZ = new BlockBase("cracked_quartz", Material.ROCK, 0.8f, 5, SoundType.STONE).setCreativeTab(ModCreativeTabs.ALL);
	public static final Block AZURE_KEY_BLOCK = new BlockAzureKey("azure_key_block", ModItems.AZURE_KEY).setCreativeTab(ModCreativeTabs.ALL);
//	public static final Block MAELSTROM_DUNGEON_KEY_BLOCK
//	public static final Block BROWN_KEY_BLOCK
//	public static final Block RED_DUNGEON_KEY_BLOCK
//	public static final Block ICE_KEY_BLOCK
//	public static final Block ICE_DUNGEON_KEY_BLOCK
//	public static final Block BLACK_DUNGEON_KEY_BLOCK
}
