package com.barribob.MaelstromMod.world.biome;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenCliffMushroom;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenCliffShrub;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenSwampTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenSwampVines;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * Biome for the lower section of the cliff dimension
 *
 */
public class BiomeCliffSwamp extends BiomeDifferentStone
{
    private final static IBlockState log = ModBlocks.SWAMP_LOG.getDefaultState();
    private final static IBlockState leaf = ModBlocks.SWAMP_LEAVES.getDefaultState();
    public BiomeCliffSwamp()
    {
	super(new BiomeProperties("cliff_swamp").setBaseHeight(-0.2F).setHeightVariation(0.1F).setTemperature(0.8F).setRainfall(0.9F).setWaterColor(4864285), Blocks.GRASS,
		Blocks.DIRT);

        this.decorator.treesPerChunk = 8;
        this.decorator.flowersPerChunk = 1;
        this.decorator.deadBushPerChunk = 1;
        this.decorator.mushroomsPerChunk = 8;
        this.decorator.bigMushroomsPerChunk = 1;
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
	WorldGenAbstractTree jungleTree = new WorldGenTrees(false, 4 + rand.nextInt(7), log, leaf, true);
	WorldGenAbstractTree bigJungleTree = new WorldGenMegaJungle(false, 8, 18, log, leaf);
	WorldGenAbstractTree swampTree = new WorldGenSwampTree(true);
        WorldGenAbstractTree shrub = new WorldGenShrub(log, leaf);
        if(rand.nextFloat() > 0.96)
        {
            return bigJungleTree;
        }
        else if(rand.nextFloat() > 0.96)
        {
            return jungleTree;
        }
        else if(rand.nextFloat() > 0.8)
        {
            return shrub;
        }
        return swampTree;
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
    
    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
	super.decorate(worldIn, rand, pos);
	// Generate vines, with less near the top
	ModUtils.generateN(worldIn, rand, pos, 4, 70, 1, new WorldGenCliffMushroom(ModBlocks.CLIFF_STONE));
	ModUtils.generateN(worldIn, rand, pos, 35, 65, 1, new WorldGenCliffShrub(log, leaf));
	ModUtils.generateN(worldIn, rand, pos, 400, 60, 40, new WorldGenSwampVines());
	ModUtils.generateN(worldIn, rand, pos, 200, 100, 40, new WorldGenSwampVines());
    }
    
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
        double d0 = GRASS_COLOR_NOISE.getValue((double)pos.getX() * 0.0225D, (double)pos.getZ() * 0.0225D);
        return d0 < -0.1D ? 4605755 : 5325610;
    }

    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos)
    {
        return 6975545;
    }
}
