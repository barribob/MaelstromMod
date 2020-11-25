package com.barribob.MaelstromMod.world.gen;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.ModRandom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Handles all of the world generation for the mod
 */
public class WorldGenOre implements IWorldGenerator {
    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int overworld = 0;
        if (world.provider.getDimension() == overworld) {
            generateOverworld(rand, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        } else if (world.provider.getDimension() == ModConfig.world.fracture_dimension_id) {
            this.generateAzure(rand, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        } else if (world.provider.getDimension() == ModConfig.world.cliff_dimension_id) {
            this.generateCliff(rand, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
    }

    private void generateOverworld(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunckProvider) {
    }

    private void generateOverworldOre(IBlockState ore, World world, Random rand, int x, int z, int minY, int maxY, int size, int chances) {
        int deltaY = maxY - minY;
        for (int i = 0; i < chances; i++) {
            BlockPos pos = new BlockPos(x + rand.nextInt(16), minY + rand.nextInt(deltaY), z + rand.nextInt(16));

            WorldGenMinable generator = new WorldGenMinable(ore, size);
            generator.generate(world, rand, pos);
        }
    }

    private void generateCliff(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int chunkSize = 16;
        IBlockState stone = Blocks.STONE.getDefaultState();
        IBlockState[] stoneBlocks = new IBlockState[]{stone, ModBlocks.RED_CLIFF_STONE.getDefaultState(),
                stone.withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), stone.withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE),
                stone.withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE)};
        for (IBlockState stoneBlock : stoneBlocks) {
            generateOre(stoneBlock, ModBlocks.CLIFF_STONE, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 256, ModRandom.range(8, 20), 10);
        }
    }

    private void generateAzure(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int chunkSize = 16;
        generateOre(ModBlocks.DARK_AZURE_STONE, ModBlocks.DARK_AZURE_STONE_1, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(8, 20), 40);
        generateOre(ModBlocks.DARK_AZURE_STONE_1, ModBlocks.DARK_AZURE_STONE, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(8, 20), 40);
        generateOre(Blocks.PRISMARINE, ModBlocks.DARK_AZURE_STONE_2, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(8, 20), 40);
        generateOre(ModBlocks.DARK_AZURE_STONE_2, Blocks.PRISMARINE, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(8, 20), 40);
        generateOre(ModBlocks.DARK_AZURE_STONE_4, ModBlocks.DARK_AZURE_STONE_3, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(8, 20), 40);
        generateOre(ModBlocks.DARK_AZURE_STONE_3, ModBlocks.DARK_AZURE_STONE_4, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(8, 20), 40);

        generateOre(ModBlocks.AZURE_COAL_ORE, ModBlocks.DARK_AZURE_STONE_1, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(8, 20), 40);
        generateOre(ModBlocks.AZURE_IRON_ORE, ModBlocks.DARK_AZURE_STONE, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(6, 12), 40);
        generateOre(ModBlocks.AZURE_GOLD_ORE, ModBlocks.DARK_AZURE_STONE_2, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(6, 12), 12);
        generateOre(ModBlocks.AZURE_REDSTONE_ORE, ModBlocks.DARK_AZURE_STONE_2, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(6, 12), 24);
        generateOre(ModBlocks.AZURE_DIAMOND_ORE, Blocks.PRISMARINE, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(6, 12), 5);
        generateOre(ModBlocks.AZURE_LAPIS_ORE, ModBlocks.DARK_AZURE_STONE_3, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(6, 12), 5);
        generateOre(ModBlocks.AZURE_EMERALD_ORE, ModBlocks.DARK_AZURE_STONE_4, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(4, 8), 5);
        generateOre(ModBlocks.CHASMIUM_ORE, Blocks.PRISMARINE, world, rand, chunkX * chunkSize, chunkZ * chunkSize, 1, 70, ModRandom.range(6, 12), 4);

    }

    private void generateOre(Block ore, Block stone, World world, Random rand, int x, int z, int minY, int maxY, int size, int chances) {
        generateOre(ore.getDefaultState(), stone, world, rand, x, z, minY, maxY, size, chances);
    }

    private void generateOre(IBlockState ore, Block stone, World world, Random rand, int x, int z, int minY, int maxY, int size, int chances) {
        int deltaY = maxY - minY;
        for (int i = 0; i < chances; i++) {
            BlockPos pos = new BlockPos(x + rand.nextInt(16), minY + rand.nextInt(deltaY), z + rand.nextInt(16));

            WorldGenModMinable generator = new WorldGenModMinable(ore, stone, size);
            generator.generate(world, rand, pos);
        }
    }
}
