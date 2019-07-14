package com.barribob.MaelstromMod.world.gen;

import java.util.ArrayList;
import java.util.Random;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.BiomeInit;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModDimensions;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.world.gen.maelstrom_castle.WorldGenMaelstromCastle;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import scala.actors.threadpool.Arrays;

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

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
	if (world.provider.getDimension() == ModConfig.cliff_dimension_id)
	{
	    if (chunkX % 10 == 0 && chunkZ % 10 == 0)
	    {
		generateBiomeSpecificStructure(WITCH_HUT, world, random, chunkX, chunkZ, 3, ModBlocks.CLIFF_STONE, BiomeInit.CLIFF_SWAMP.getClass());
	    }
	    else if ((chunkX + 5) % 10 == 0 && (chunkZ + 5) % 10 == 0)
	    {
		generateBiomeSpecificStructure(CLIFF_TEMPLE, world, random, chunkX, chunkZ, 3, ModBlocks.CLIFF_STONE, BiomeInit.CLIFF_SWAMP.getClass());
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
