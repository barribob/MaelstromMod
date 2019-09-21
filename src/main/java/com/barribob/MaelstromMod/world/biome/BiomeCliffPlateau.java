package com.barribob.MaelstromMod.world.biome;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;

/**
 * 
 * The biome for the tall part of the cliff dimension
 *
 */
public class BiomeCliffPlateau extends BiomeDifferentStone
{
    private static final WorldGenSavannaTree SAVANNA_TREE = new WorldGenSavannaTree(false);

    public BiomeCliffPlateau()
    {
	super(new BiomeProperties("Cliff Plateau").setBaseHeight(11F).setHeightVariation(0.15F).setTemperature(1.2F).setRainfall(0.0F).setRainDisabled(), Blocks.GRASS,
		ModBlocks.CLIFF_STONE);

	this.decorator.treesPerChunk = 1;
	this.decorator.grassPerChunk = 3;
	this.decorator.flowersPerChunk = 1;
	this.decorator.deadBushPerChunk = 1;
	this.decorator.sandPatchesPerChunk = 0;
	this.decorator.gravelPatchesPerChunk = 0;

	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntitySheep.class, 12, 4, 4));
	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityPig.class, 10, 4, 4));
	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityChicken.class, 10, 4, 4));
	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCow.class, 8, 4, 4));
	this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityBat.class, 10, 8, 8));
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
	return rand.nextInt(5) > 0 ? SAVANNA_TREE : TREE_FEATURE;
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
	DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);

	for (int i = 0; i < 7; ++i)
	{
	    int j = rand.nextInt(16) + 8;
	    int k = rand.nextInt(16) + 8;
	    int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
	    DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
	}
	super.decorate(worldIn, rand, pos);
    }

    @Override
    public void generateTopBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal, Block stoneBlock)
    {
	int i = worldIn.getSeaLevel();
	IBlockState iblockstate = this.topBlock;
	IBlockState iblockstate1 = this.fillerBlock;
	int j = -1;
	int k = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
	int l = x & 15;
	int i1 = z & 15;
	BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

	for (int j1 = 255; j1 >= 0; --j1)
	{
	    if (j1 <= rand.nextInt(5))
	    {
		chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
	    }
	    else
	    {
		IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

		if (iblockstate2.getMaterial() == Material.AIR)
		{
		    j = -1;
		}
		/**
		 * The line below is the block that needs to be match to the custom stone in
		 * order to make the block replace correctly
		 */
		else if (iblockstate2.getBlock() == stoneBlock)
		{
		    if (j1 < 243)
		    {
			iblockstate = AIR;
		    }
		    if (j == -1)
		    {
			if (k <= 0)
			{
			    iblockstate = AIR;
			    iblockstate1 = stoneBlock.getDefaultState();
			}
			else if (j1 >= i - 4 && j1 <= i + 1)
			{
			    iblockstate = this.topBlock;
			    iblockstate1 = this.fillerBlock;
			}

			if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR))
			{
			    if (this.getTemperature(blockpos$mutableblockpos.setPos(x, j1, z)) < 0.15F)
			    {
				iblockstate = ICE;
			    }
			    else
			    {
				iblockstate = WATER;
			    }
			}

			j = k;

			if (j1 >= i - 1)
			{
			    chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
			}
			else if (j1 < i - 7 - k)
			{
			    iblockstate = AIR;
			    iblockstate1 = stoneBlock.getDefaultState();
			}
			else
			{
			    chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
			}
		    }
		    else if (j > 0)
		    {
			--j;
			chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
		    }
		}
	    }
	}
    }
}
