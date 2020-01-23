package com.barribob.MaelstromMod.init;

import java.util.ArrayList;
import java.util.List;

import com.barribob.MaelstromMod.blocks.BlockAzureFlower;
import com.barribob.MaelstromMod.blocks.BlockAzureGrass;
import com.barribob.MaelstromMod.blocks.BlockAzureLeaves;
import com.barribob.MaelstromMod.blocks.BlockAzureOre;
import com.barribob.MaelstromMod.blocks.BlockAzureRedstoneOre;
import com.barribob.MaelstromMod.blocks.BlockAzureVines;
import com.barribob.MaelstromMod.blocks.BlockAzureVinesBlock;
import com.barribob.MaelstromMod.blocks.BlockBase;
import com.barribob.MaelstromMod.blocks.BlockBossSpawner;
import com.barribob.MaelstromMod.blocks.BlockDecayingMaelstrom;
import com.barribob.MaelstromMod.blocks.BlockDisappearingSpawner;
import com.barribob.MaelstromMod.blocks.BlockDoubleBrownedGrass;
import com.barribob.MaelstromMod.blocks.BlockFan;
import com.barribob.MaelstromMod.blocks.BlockFenceBase;
import com.barribob.MaelstromMod.blocks.BlockFullLog;
import com.barribob.MaelstromMod.blocks.BlockGrate;
import com.barribob.MaelstromMod.blocks.BlockLamp;
import com.barribob.MaelstromMod.blocks.BlockLogBase;
import com.barribob.MaelstromMod.blocks.BlockMaelstrom;
import com.barribob.MaelstromMod.blocks.BlockMaelstromCore;
import com.barribob.MaelstromMod.blocks.BlockMegaStructure;
import com.barribob.MaelstromMod.blocks.BlockModTallGrass;
import com.barribob.MaelstromMod.blocks.BlockNexusTeleporter;
import com.barribob.MaelstromMod.blocks.BlockPillarBase;
import com.barribob.MaelstromMod.blocks.BlockPlumFilledLeaves;
import com.barribob.MaelstromMod.blocks.BlockPlumLeaves;
import com.barribob.MaelstromMod.blocks.BlockSaplingBase;
import com.barribob.MaelstromMod.blocks.BlockStairsBase;
import com.barribob.MaelstromMod.blocks.BlockSwampLeaves;
import com.barribob.MaelstromMod.blocks.key_blocks.BlockAzureKey;
import com.barribob.MaelstromMod.blocks.key_blocks.BlockBrownKey;
import com.barribob.MaelstromMod.blocks.portal.BlockAzurePortal;
import com.barribob.MaelstromMod.blocks.portal.BlockCliffPortal;
import com.barribob.MaelstromMod.blocks.portal.BlockDarkNexusPortal;
import com.barribob.MaelstromMod.blocks.portal.BlockNexusPortal;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenBigPlumTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenPlumTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenSwampTree;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

/**
 * 
 * Holds all of our new blocks
 *
 */
public class ModBlocks
{
    public static final List<Block> BLOCKS = new ArrayList<Block>();
    public static final float STONE_HARDNESS = 1.7f;
    public static final float STONE_RESISTANCE = 10f;
    public static final float BRICK_HARDNESS = 2.0f;
    public static final float WOOD_HARDNESS = 1.5f;
    public static final float WOOD_RESISTANCE = 5.0f;
    public static final float PLANTS_HARDNESS = 0.2f;
    public static final float PLANTS_RESISTANCE = 2.0f;
    public static final float ORE_HARDNESS = 3.0F;

    public static final Block MEGA_STRUCTURE_BLOCK = new BlockMegaStructure("mega_structure_block");
    public static final Block DISAPPEARING_SPAWNER = new BlockDisappearingSpawner("disappearing_spawner", Material.ROCK);
    public static final Block BOSS_SPAWNER = new BlockBossSpawner("nexus_herobrine_spawner");

    public static final Block MAELSTROM_CORE = new BlockMaelstromCore("maelstrom_core_block", Material.ROCK, ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE, ModItems.MAELSTROM_FRAGMENT);
    public static final Block AZURE_MAELSTROM_CORE = new BlockMaelstromCore("azure_maelstrom_core", Material.ROCK, ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE, ModItems.AZURE_MAELSTROM_FRAGMENT);
    public static final Block CLIFF_MAELSTROM_CORE = new BlockMaelstromCore("cliff_maelstrom_core", Material.ROCK, ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE, ModItems.GOLDEN_MAELSTROM_FRAGMENT);
    public static final Block AZURE_MAELSTROM = new BlockMaelstrom("azure_maelstrom", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE, 1).setLightLevel(0.5f).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DECAYING_MAELSTROM = new BlockDecayingMaelstrom("azure_decaying_maelstrom", STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE, 1).setLightLevel(0.5f);

    public static final Block NEXUS_TELEPORTER = new BlockNexusTeleporter("nexus_teleporter", Material.ROCK, SoundType.STONE).setLightLevel(1.0f);
    public static final Block NEXUS_PORTAL = new BlockNexusPortal("nexus_portal").setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DARK_NEXUS_PORTAL = new BlockDarkNexusPortal("dark_nexus_portal").setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_PORTAL = new BlockAzurePortal("azure_portal").setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CLIFF_PORTAL = new BlockCliffPortal("cliff_portal").setCreativeTab(ModCreativeTabs.BLOCKS);

    /**
     * Stone and ore
     */

    public static final Block DARK_AZURE_STONE = new BlockBase("dark_azure_stone", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DARK_AZURE_STONE_1 = new BlockBase("azure_stone_1", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DARK_AZURE_STONE_2 = new BlockBase("azure_stone_2", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DARK_AZURE_STONE_3 = new BlockBase("azure_stone_3", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DARK_AZURE_STONE_4 = new BlockBase("azure_stone_4", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DARK_AZURE_STONE_5 = new BlockBase("azure_stone_5", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setLightLevel(1.0f).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block LIGHT_AZURE_STONE = new BlockLamp("light_azure_stone", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS).setLightLevel(1.0f);
    public static final Block AZURE_COAL_ORE = new BlockAzureOre("azure_coal_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_DIAMOND_ORE = new BlockAzureOre("azure_diamond_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_EMERALD_ORE = new BlockAzureOre("azure_emerald_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_LAPIS_ORE = new BlockAzureOre("azure_lapis_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_IRON_ORE = new BlockAzureOre("azure_iron_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_GOLD_ORE = new BlockAzureOre("azure_gold_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_REDSTONE_ORE = new BlockAzureRedstoneOre("azure_redstone_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_LIT_REDSTONE_ORE = new BlockAzureRedstoneOre("azure_lit_redstone_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setLightLevel(0.3f);
    public static final Block CHASMIUM_ORE = new BlockAzureOre("chasmium_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CLIFF_STONE = new BlockBase("cliff_stone", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block RED_CLIFF_STONE = new BlockBase("red_cliff_stone", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);

    /**
     * Organic blocks
     */

    public static final Block AZURE_GRASS = new BlockAzureGrass("azure_grass", Material.GRASS, 0.5f, PLANTS_RESISTANCE, SoundType.GROUND).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_VINES_BLOCK = new BlockAzureVinesBlock("azure_vines_block", Material.PLANTS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_VINES = new BlockAzureVines("azure_vines", PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block BLUE_DAISY = new BlockAzureFlower("blue_daisy", Material.PLANTS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block RUBY_ORCHID = new BlockAzureFlower("ruby_orchid", Material.PLANTS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block BROWNED_GRASS = new BlockModTallGrass("browned_grass", Material.PLANTS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DOUBLE_BROWNED_GRASS = new BlockDoubleBrownedGrass("double_browned_grass", Material.PLANTS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block FIRE_GRASS = new BlockModTallGrass("fire_grass", Material.PLANTS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS).setLightLevel(0.5f);

    public static final Block AZURE_LOG = new BlockLogBase("azure_log", WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block PLUM_LOG = new BlockLogBase("plum_log", WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setLightLevel(0.3f).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block SWAMP_LOG = new BlockLogBase("swamp_log", WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block FULL_SWAMP_LOG = new BlockFullLog("full_swamp_log", SWAMP_LOG).setHardness(WOOD_HARDNESS).setResistance(WOOD_RESISTANCE).setCreativeTab(ModCreativeTabs.BLOCKS);

    public static final Block AZURE_LEAVES = new BlockAzureLeaves("azure_leaves", PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block PLUM_LEAVES = new BlockPlumLeaves("plum_leaves", PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block PLUM_FILLED_LEAVES = new BlockPlumFilledLeaves("plum_filled_leaves", PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block SWAMP_LEAVES = new BlockSwampLeaves("swamp_leaves", PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);

    public static final Block AZURE_SAPLING = new BlockSaplingBase("azure_sapling", Blocks.GRASS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT, new WorldGenAzureTree(true)).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block PLUM_SAPLING = new BlockSaplingBase("plum_sapling", Blocks.GRASS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT, new WorldGenPlumTree(true, true)).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block LARGE_PLUM_SAPLING = new BlockSaplingBase("large_plum_sapling", Blocks.GRASS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT, new WorldGenBigPlumTree(true)).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block SWAMP_SAPLING = new BlockSaplingBase("swamp_sapling", Blocks.GRASS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT, new WorldGenSwampTree(true)).setCreativeTab(ModCreativeTabs.BLOCKS);

    public static final Block AZURE_PLANKS = new BlockBase("azure_planks", Material.WOOD, WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_FENCE = new BlockFenceBase("azure_fence", Material.WOOD, WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_PLANK_STAIRS = new BlockStairsBase("azure_plank_stairs", AZURE_PLANKS.getDefaultState(), WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block SWAMP_PLANKS = new BlockBase("swamp_planks", Material.WOOD, WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block SWAMP_PLANK_STAIRS = new BlockStairsBase("swamp_plank_stairs", SWAMP_PLANKS.getDefaultState(), WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block SWAMP_FENCE = new BlockFenceBase("swamp_fence", Material.WOOD, WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);

    /**
     * Blocks for structures
     */

    public static final Block MAELSTROM_BRICKS = new BlockBase("maelstrom_bricks", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block MAELSTROM_BRICK_FENCE = new BlockFenceBase("maelstrom_brick_fence", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block MAELSTROM_BRICK_STAIRS = new BlockStairsBase("maelstrom_brick_stairs", MAELSTROM_BRICKS.getDefaultState(), BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);

    public static final Block MAELSTROM_STONEBRICK = new BlockBase("maelstrom_stonebrick", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block MAELSTROM_STONEBRICK_STAIRS = new BlockStairsBase("maelstrom_stonebrick_stairs", MAELSTROM_STONEBRICK.getDefaultState(), STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block MAELSTROM_STONEBRICK_FENCE = new BlockFenceBase("maelstrom_stonebrick_fence", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);

    public static final Block AZURE_STONEBRICK = new BlockBase("azure_stonebrick", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_STONEBRICK_STAIRS = new BlockStairsBase("azure_stonebrick_stairs", AZURE_STONEBRICK.getDefaultState(), BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_STONEBRICK_CRACKED = new BlockBase("azure_stonebrick_cracked", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_STONEBRICK_CARVED = new BlockBase("azure_stonebrick_carved", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_STONEBRICK_CARVED_2 = new BlockBase("azure_stonebrick_carved_2", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_STONEBRICK_CARVED_3 = new BlockBase("azure_stonebrick_carved_3", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_STONEBRICK_LIT = new BlockLamp("azure_stonebrick_lit", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setLightLevel(1.0f).setCreativeTab(ModCreativeTabs.BLOCKS);

    public static final Block GOLD_STONE = new BlockBase("gold_stone", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CRACKED_GOLD_STONE = new BlockBase("cracked_gold_stone", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block GOLD_STONE_FENCE = new BlockFenceBase("gold_stone_fence", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block GOLD_STONE_STAIRS = new BlockStairsBase("gold_stone_stairs", GOLD_STONE.getDefaultState(), BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block BROWNED_PILLAR = new BlockBase("browned_pillar", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block BROWNED_BLOCK = new BlockBase("browned_block", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block GOLD_STONE_LAMP = new BlockLamp("gold_stone_lamp", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setLightLevel(1.0f).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CHISELED_CLIFF_STONE = new BlockBase("chiseled_cliff_stone", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block SWAMP_BRICK = new BlockBase("swamp_brick", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CRACKED_SWAMP_BRICK = new BlockBase("cracked_swamp_brick", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);

    /*
     * Key blocks and nexus stuff
     */

    public static final Block CRACKED_QUARTZ = new BlockBase("cracked_quartz", Material.ROCK, 0.8f, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_KEY_BLOCK = new BlockAzureKey("azure_key_block", ModItems.AZURE_KEY).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block MAELSTROM_DUNGEON_KEY_BLOCK = new BlockAzureKey("azure_dungeon_key_block", null).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block BROWN_KEY_BLOCK = new BlockBrownKey("brown_key_block", ModItems.BROWN_KEY).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block RED_DUNGEON_KEY_BLOCK = new BlockAzureKey("red_dungeon_key_block", null).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block ICE_KEY_BLOCK = new BlockAzureKey("ice_key_block", null).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block ICE_DUNGEON_KEY_BLOCK = new BlockAzureKey("ice_dungeon_key_block", null).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block BLACK_DUNGEON_KEY_BLOCK = new BlockAzureKey("black_dungeon_key_block", null).setCreativeTab(ModCreativeTabs.BLOCKS);

    /*
     * Crimson dimension
     */

    public static final Block FURNACE_PILLAR = new BlockPillarBase("furnace_pillar", Material.ROCK).setHardness(BRICK_HARDNESS).setResistance(STONE_RESISTANCE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block REDSTONE_BRICK = new BlockBase("redstone_brick", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE)
    {
	@Override
	public boolean canProvidePower(net.minecraft.block.state.IBlockState state)
	{
	    return true;
	};

	@Override
	public int getWeakPower(net.minecraft.block.state.IBlockState blockState, net.minecraft.world.IBlockAccess blockAccess, net.minecraft.util.math.BlockPos pos,
		net.minecraft.util.EnumFacing side)
	{
	    return 15;
	};
    }.setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block IRON_GRATE = new BlockGrate("iron_grate", Material.IRON).setHardness(BRICK_HARDNESS).setResistance(STONE_RESISTANCE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block FAN = new BlockFan("fan", Material.IRON, STONE_HARDNESS, STONE_RESISTANCE, SoundType.METAL);

}
