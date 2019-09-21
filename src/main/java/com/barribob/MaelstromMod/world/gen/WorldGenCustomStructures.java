package com.barribob.MaelstromMod.world.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityTeleporter;
import com.barribob.MaelstromMod.init.BiomeInit;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.world.biome.BiomeCliffSwamp;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenCliffMushroom;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenCliffShrub;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenSwampVines;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenWaterfall;
import com.barribob.MaelstromMod.world.gen.maelstrom_castle.WorldGenMaelstromCastle;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

/**
 * 
 * Keeps track of all the custom structures
 *
 */
public class WorldGenCustomStructures implements IWorldGenerator
{
    public static final WorldGenStructure MAELSTROM_CASTLE = new WorldGenMaelstromCastle("maelstrom_castle/maelstrom_castle");
    public static final WorldGenStructure WITCH_HUT = new WorldGenStructure("cliff/maelstrom_witch_hut")
    {
	@Override
	protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand)
	{
	    if (function.startsWith("witch"))
	    {
		worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityMobSpawner)
		{
		    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setEntities(new ResourceLocation(Reference.MOD_ID + ":maelstrom_witch"), 1);
		}
	    }
	};
    };
    public static final WorldGenStructure CLIFF_TEMPLE = new WorldGenStructure("cliff/swamp_temple")
    {
	@Override
	protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand)
	{
	    if (function.startsWith("enemy"))
	    {
		worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityMobSpawner)
		{
		    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setEntities(new ResourceLocation(Reference.MOD_ID + ":cliff_golem"), 1);
		}
	    }
	};
    };
    private static final WorldGenStructure MAELSTROM_RUINS = new WorldGenStructure("cliff/maelstrom_ruins")
    {
	@Override
	protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand)
	{
	    if (function.startsWith("enemy"))
	    {
		worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityMobSpawner)
		{
		    String[] enemies = { "golden_mage", "golden_shade" };
		    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setEntities(new ResourceLocation(Reference.MOD_ID + ":" + ModRandom.choice(enemies)), 5);
		}
	    }
	    if (function.startsWith("chest"))
	    {
		worldIn.setBlockState(pos, Blocks.CHEST.getDefaultState());
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityChest)
		{
		    ((TileEntityChest) tileentity).setLootTable(LootTableHandler.MAELSTROM_RUINS, rand.nextLong());
		}
	    }
	};
    };
    public static final WorldGenStructure MAELSTROM_PIT = new WorldGenStructure("azure/maelstrom_pit")
    {
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
	    if (position.getY() < 12)
	    {
		return false;
	    }
	    return super.generate(worldIn, rand, position.down(4));
	};

	@Override
	protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand)
	{
	    if (function.startsWith("boss"))
	    {
		worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityMobSpawner)
		{
		    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setEntities(new ResourceLocation(Reference.MOD_ID + ":iron_shade"), 1);
		}
	    }
	};
    };
    public static final WorldGenStructure SMALL_LEDGE = new WorldGenStructure("cliff/small_ledge")
    {
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
	    BlockPos center = position.add(new BlockPos(-2, -5, -2));
	    if (canLedgeGenerate(worldIn, position))
	    {
		return super.generate(worldIn, rand, center);
	    }
	    return false;
	};
    };

    public static final WorldGenStructure MEDIUM_LEDGE = new WorldGenStructure("cliff/medium_ledge")
    {
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
	    BlockPos center = position.add(new BlockPos(-5, -10, -5));
	    if (canLedgeGenerate(worldIn, position))
	    {
		return super.generate(worldIn, rand, center);
	    }
	    return false;
	};
    };

    public static final WorldGenStructure MAELSTROM_LEDGE = new WorldGenStructure("cliff/maelstrom_ledge")
    {
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
	    BlockPos center = position.add(new BlockPos(-6, -15, -6));
	    if (canLedgeGenerate(worldIn, position))
	    {
		return super.generate(worldIn, rand, center);
	    }
	    return false;
	};

	@Override
	protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand)
	{
	    if (function.startsWith("enemy"))
	    {
		worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityMobSpawner)
		{
		    String[] enemies = { "golden_pillar" };
		    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setEntities(new ResourceLocation(Reference.MOD_ID + ":" + ModRandom.choice(enemies)), 2);
		}
	    }
	    if (function.startsWith("chest"))
	    {
		worldIn.setBlockToAir(pos);
		pos = pos.down();
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityChest)
		{
		    ((TileEntityChest) tileentity).setLootTable(LootTableHandler.MAELSTROM_RUINS, rand.nextLong());
		}
	    }
	};
    };

    public static final WorldGenStructure MAELSTROM_CAVE = new WorldGenStructure("cliff/maelstrom_cave")
    {
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
	    BlockPos center = position.add(new BlockPos(-6, -6, -6));
	    if (position.getY() > 120 && position.getY() < 220 && worldIn.getBlockState(position).getBlock() == ModBlocks.CLIFF_STONE
		    && worldIn.getBlockState(position.down(6)).getBlock() == ModBlocks.CLIFF_STONE)
	    {
		for (int y = 1; y < 15; y++)
		{
		    if (!worldIn.isAirBlock(position.up(y)))
		    {
			return false;
		    }
		}
		return super.generate(worldIn, rand, center);
	    }
	    return false;
	};

	@Override
	protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand)
	{
	    if (function.startsWith("enemy"))
	    {
		worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityMobSpawner)
		{
		    String[] enemies = { "golden_mage" };
		    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setEntities(new ResourceLocation(Reference.MOD_ID + ":" + ModRandom.choice(enemies)), 2);
		}
	    }
	    else if (function.startsWith("chest"))
	    {
		worldIn.setBlockToAir(pos);
		pos = pos.down();
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityChest)
		{
		    ((TileEntityChest) tileentity).setLootTable(LootTableHandler.MAELSTROM_RUINS, rand.nextLong());
		}
	    }
	    else
	    {
		if (isBlockNearby(worldIn, pos))
		{
		    worldIn.setBlockState(pos, ModBlocks.AZURE_MAELSTROM.getDefaultState());
		}
		else
		{
		    worldIn.setBlockToAir(pos);
		}
	    }
	};

	private boolean isBlockNearby(World world, BlockPos pos)
	{
	    for (BlockPos dir : Arrays.asList(pos.up(), pos.east(), pos.west(), pos.north(), pos.south(), pos.down()))
	    {
		if (world.getBlockState(dir).getBlock() == ModBlocks.CLIFF_STONE)
		{
		    return true;
		}
	    }
	    return false;
	}
    };

    private static final boolean canLedgeGenerate(World worldIn, BlockPos center)
    {
	if (center.getY() > 90 && center.getY() < 220
		&& (worldIn.getBlockState(center).getBlock() == ModBlocks.CLIFF_STONE || worldIn.getBlockState(center).getBlock() == Blocks.GRASS)
		&& worldIn.isAirBlock(center.up(5)) && worldIn.isAirBlock(center.up(40)))
	{
	    return true;
	}
	return false;
    }

    public static final WorldGenStructure NEXUS = new WorldGenStructure("nexus/nexus_islands")
    {
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
	    this.generateStructure(worldIn, position, false);
	    return true;
	};

	@Override
	protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand)
	{
	    worldIn.setBlockToAir(pos);
	    if (function.startsWith("teleport"))
	    {
		worldIn.setBlockState(pos, ModBlocks.NEXUS_TELEPORTER.getDefaultState());
		String[] params = function.split(" ");
		Vec3d relTeleport = new Vec3d(Integer.parseInt(params[1]) + 0.5, Integer.parseInt(params[2]), Integer.parseInt(params[3]) + 0.5);
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityTeleporter)
		{
		    ((TileEntityTeleporter) tileentity).setRelTeleportPos(relTeleport);
		}
	    }
	    else if (function.startsWith("herobrine"))
	    {
		worldIn.setBlockState(pos, ModBlocks.NEXUS_HEROBRINE_SPAWNER.getDefaultState(), 2);
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityMobSpawner)
		{
		    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setEntities(new ResourceLocation(Reference.MOD_ID + ":herobrine_controller"), 1);
		}
	    }
	    else if (function.startsWith("mage"))
	    {
		ItemMonsterPlacer.spawnCreature(worldIn, new ResourceLocation(Reference.MOD_ID + ":nexus_mage"), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
	    }
	    else if (function.startsWith("saiyan"))
	    {
		ItemMonsterPlacer.spawnCreature(worldIn, new ResourceLocation(Reference.MOD_ID + ":nexus_saiyan"), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
	    }
	    else if (function.startsWith("bladesmith"))
	    {
		ItemMonsterPlacer.spawnCreature(worldIn, new ResourceLocation(Reference.MOD_ID + ":nexus_bladesmith"), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
	    }
	    else if (function.startsWith("armorer"))
	    {
		ItemMonsterPlacer.spawnCreature(worldIn, new ResourceLocation(Reference.MOD_ID + ":nexus_armorer"), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
	    }
	    else if (function.startsWith("gunsmith"))
	    {
		ItemMonsterPlacer.spawnCreature(worldIn, new ResourceLocation(Reference.MOD_ID + ":nexus_gunsmith"), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
	    }
	};
    };

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
	int i = 15;
	if (world.provider.getDimension() == ModConfig.cliff_dimension_id)
	{
	    if (chunkX % i == 0 && chunkZ % i == 0)
	    {
		generateBiomeSpecificStructure(WITCH_HUT, world, random, chunkX, chunkZ, 2, ModBlocks.CLIFF_STONE, BiomeInit.CLIFF_SWAMP.getClass());
	    }
	    else if ((chunkX + 5) % i == 0 && (chunkZ + 5) % i == 0)
	    {
		generateBiomeSpecificStructure(CLIFF_TEMPLE, world, random, chunkX, chunkZ, 2, ModBlocks.CLIFF_STONE, BiomeInit.CLIFF_SWAMP.getClass());
	    }
	    else if ((chunkX + 10) % i == 0 && (chunkZ + 10) % i == 0)
	    {
		generateBiomeSpecificStructure(MAELSTROM_RUINS, world, random, chunkX, chunkZ, 2, ModBlocks.CLIFF_STONE, BiomeInit.CLIFF_SWAMP.getClass());
	    }

	    int x = chunkX * 16;
	    int z = chunkZ * 16;
	    BlockPos pos = new BlockPos(x, 0, z);
	    ModUtils.generateN(world, random, pos, 4, 70, 1, new WorldGenCliffMushroom(ModBlocks.CLIFF_STONE));
	    ModUtils.generateN(world, random, pos, 35, 65, 1, new WorldGenCliffShrub(BiomeCliffSwamp.log, BiomeCliffSwamp.leaf));

	    generateBiomeSpecificStructure(
		    ModRandom.choice(new WorldGenerator[] { MEDIUM_LEDGE, MEDIUM_LEDGE, MEDIUM_LEDGE, MAELSTROM_LEDGE, SMALL_LEDGE, SMALL_LEDGE, SMALL_LEDGE, SMALL_LEDGE,
			    MAELSTROM_CAVE }, random),
		    world, random, chunkX, chunkZ, 1, ModBlocks.CLIFF_STONE, BiomeInit.HIGH_CLIFF.getClass(), BiomeInit.CLIFF_SWAMP.getClass());

	    ModUtils.generateN(world, random, pos, 400, 60, 40, new WorldGenSwampVines());
	    ModUtils.generateN(world, random, pos, 200, 100, 40, new WorldGenSwampVines());
	    if (random.nextInt(15) == 0)
	    {
		ModUtils.generateN(world, random, pos, 1, 200, 50, new WorldGenWaterfall(ModBlocks.CLIFF_STONE));
	    }
	}
	if (world.provider.getDimension() == ModConfig.fracture_dimension_id)
	{
	    if (chunkX % i == 0 && chunkZ % i == 0)
	    {
		generateBiomeSpecificStructure(MAELSTROM_PIT, world, random, chunkX, chunkZ, 2, ModBlocks.DARK_AZURE_STONE, BiomeInit.AZURE.getClass());
	    }
	}
    }

    /**
     * Generates a structure in the world, calculating floor height and only placing
     * on the top block. Only places it in specified biome classes
     * 
     * @param generator
     * @param world
     * @param rand
     * @param chunkX
     * @param chunkZ
     * @param chance
     * @param topBlock
     * @param classes
     */
    private void generateBiomeSpecificStructure(WorldGenerator generator, World world, Random rand, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes)
    {
	ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));

	int x = chunkX * 16 + 8;
	int z = chunkZ * 16 + 8;
	int y = calculateGenerationHeight(world, x, z, topBlock);
	BlockPos pos = new BlockPos(x, y, z);

	Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

	if (world.getWorldType() != WorldType.FLAT || world.provider.getDimension() != 0)
	{
	    if (classesList.contains(biome))
	    {
		if (rand.nextInt(chance) == 0)
		{
		    generator.generate(world, rand, pos);
		}
	    }
	}
    }

    /**
     * Generates a structure in the world, calculating floor height and only placing
     * on the top block
     * 
     * @param generator
     * @param world
     * @param rand
     * @param chunkX
     * @param chunkZ
     * @param chance
     * @param topBlock
     * @param classes
     */
    private void generateStructure(WorldGenerator generator, World world, Random rand, int chunkX, int chunkZ, int chance, Block topBlock)
    {
	if (rand.nextInt(chance) == 0)
	{
	    int x = chunkX * 16 + 8;
	    int z = chunkZ * 16 + 8;
	    int y = calculateGenerationHeight(world, x, z, topBlock);

	    if (world.getWorldType() != WorldType.FLAT || world.provider.getDimension() != 0)
	    {
		generator.generate(world, rand, new BlockPos(x, y, z));
	    }
	}
    }

    /**
     * Calculates what the best height to put a structure is i.e. the top block
     * 
     * @param world
     * @param x
     * @param z
     * @param topBlock
     * @return
     */
    private static int calculateGenerationHeight(World world, int x, int z, Block topBlock)
    {
	int y = world.getHeight();
	boolean foundGround = false;

	while (!foundGround && y-- >= 0)
	{
	    Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
	    foundGround = block == topBlock;
	}

	return y;
    }
}
