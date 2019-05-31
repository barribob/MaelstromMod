package com.barribob.MaelstromMod.world.biome;

import java.util.Random;

import com.barribob.MaelstromMod.blocks.BlockModBush;
import com.barribob.MaelstromMod.entity.entities.EntityAzureGolem;
import com.barribob.MaelstromMod.entity.entities.EntityDreamElk;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureDoublePlant;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureFoliage;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureVineBridge;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureVines;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenBigPlumTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenPlumTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenSwampTree;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * Biome for the lower section of the cliff dimension
 *
 */
public class BiomeCliffSwamp extends BiomeDifferentStone
{
    public BiomeCliffSwamp()
    {
	super(new BiomeProperties("cliff_swamp").setBaseHeight(-0.2F).setHeightVariation(0.1F).setTemperature(0.8F).setRainfall(0.9F).setWaterColor(4864285), Blocks.GRASS,
		Blocks.DIRT);

        this.decorator.treesPerChunk = 6;
        this.decorator.flowersPerChunk = 1;
        this.decorator.deadBushPerChunk = 1;
        this.decorator.mushroomsPerChunk = 8;
        this.decorator.reedsPerChunk = 10;
        this.decorator.clayPerChunk = 1;
        this.decorator.waterlilyPerChunk = 4;
        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
        this.decorator.grassPerChunk = 5;
    }
    
    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return new WorldGenSwampTree(true);
    }
    
    @Override
    public void generateTopBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal, Block stoneBlock)
    {
        double d0 = GRASS_COLOR_NOISE.getValue((double)x * 0.25D, (double)z * 0.25D);

        if (d0 > 0.0D)
        {
            int i = x & 15;
            int j = z & 15;

            for (int k = 255; k >= 0; --k)
            {
                if (chunkPrimerIn.getBlockState(j, k, i).getMaterial() != Material.AIR)
                {
                    if (k == 62 && chunkPrimerIn.getBlockState(j, k, i).getBlock() != Blocks.WATER)
                    {
                        chunkPrimerIn.setBlockState(j, k, i, WATER);

                        if (d0 < 0.12D)
                        {
                            chunkPrimerIn.setBlockState(j, k + 1, i, Blocks.WATERLILY.getDefaultState());
                        }
                    }

                    break;
                }
            }
        }
	
        super.generateTopBlocks(worldIn, rand, chunkPrimerIn, x, z, noiseVal, stoneBlock);
    }
    
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
        double d0 = GRASS_COLOR_NOISE.getValue((double)pos.getX() * 0.0225D, (double)pos.getZ() * 0.0225D);
        return d0 < -0.1D ? 5011004 : 6975545;
    }

    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos)
    {
        return 6975545;
    }
}
