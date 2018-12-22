package com.barribob.MaelstromMod.world.biome;

import java.util.Random;

import com.barribob.MaelstromMod.blocks.BlockAzureBush;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureDoublePlant;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureFoliage;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureVineBridge;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureVines;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenBigPlumTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenPlumTree;

import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * 
 * The biome for the azure dimension.
 *
 */
public class BiomeAzure extends Biome
{
    protected static final WorldGenAzureTree AZURE_TREE = new WorldGenAzureTree(false);
    protected static final WorldGenPlumTree SMALL_PLUM_TREE = new WorldGenPlumTree(false, true);
    protected static final WorldGenBigPlumTree LARGE_PLUM_TREE = new WorldGenBigPlumTree(false);

    private final BlockAzureBush[] FLOWER_LIST = { (BlockAzureBush) ModBlocks.BLUE_DAISY,
	    (BlockAzureBush) ModBlocks.RUBY_ORCHID };
    protected final WorldGenAzureFoliage FLOWERS = new WorldGenAzureFoliage(FLOWER_LIST, 64);

    private final BlockAzureBush[] TALL_GRASS_LIST = { (BlockAzureBush) ModBlocks.BROWNED_GRASS };

    public BiomeAzure()
    {
	super(new BiomeProperties("azure").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F)
		.setRainDisabled().setWaterColor(10252253));

	/**
	 * Sets what in standard biomes is the grass (top block) and dirt (filler block)
	 */
	topBlock = ModBlocks.AZURE_GRASS.getDefaultState();
	fillerBlock = ModBlocks.DARK_AZURE_STONE.getDefaultState();

	// Clear all existing creatures
	this.spawnableCaveCreatureList.clear();
	this.spawnableCreatureList.clear();
	this.spawnableMonsterList.clear();
	this.spawnableWaterCreatureList.clear();

	this.decorator.treesPerChunk = 2;
	this.decorator.grassPerChunk = 3;

	// Registers flowers when using bonemeal
	this.addFlower(ModBlocks.BLUE_DAISY.getDefaultState(), 1);
	this.addFlower(ModBlocks.RUBY_ORCHID.getDefaultState(), 1);

	// Add our mobs to spawn in the biome
	this.spawnableMonsterList.add(new SpawnListEntry(EntityShade.class, 10, 1, 5));
	this.spawnableMonsterList.add(new SpawnListEntry(EntityWitherSkeleton.class, 10, 1, 5));
    }

    /**
     * Determines which trees to generate
     */
    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
	int plumTreeOdds = 10;
	int largePlumTreeOdds = 12;
	if (rand.nextInt(plumTreeOdds) == 0)
	{
	    return SMALL_PLUM_TREE;
	} else if (rand.nextInt(largePlumTreeOdds) == 0)
	{
	    return LARGE_PLUM_TREE;
	}

	return AZURE_TREE;
    }

    /**
     * Override so that we can have custom stone and have the biome fill in the top
     * and filler blocks correctly Since we can't override directly, we have to hack
     * around a bit in here and in the azure chunk generator
     */
    public void generateAzureTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z,
	    double noiseVal)
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
	    } else
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
		else if (iblockstate2.getBlock() == ModBlocks.DARK_AZURE_STONE)
		{
		    if (j == -1)
		    {
			if (k <= 0)
			{
			    iblockstate = AIR;
			    iblockstate1 = STONE;
			} else if (j1 >= i - 4 && j1 <= i + 1)
			{
			    iblockstate = this.topBlock;
			    iblockstate1 = this.fillerBlock;
			}

			if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR))
			{
			    if (this.getTemperature(blockpos$mutableblockpos.setPos(x, j1, z)) < 0.15F)
			    {
				iblockstate = ICE;
			    } else
			    {
				iblockstate = WATER;
			    }
			}

			j = k;

			if (j1 >= i - 1)
			{
			    chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
			} else if (j1 < i - 7 - k)
			{
			    iblockstate = AIR;
			    iblockstate1 = STONE;
			    chunkPrimerIn.setBlockState(i1, j1, l, GRAVEL);
			} else
			{
			    chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
			}
		    } else if (j > 0)
		    {
			--j;
			chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);

			if (j == 0 && iblockstate1.getBlock() == Blocks.SAND && k > 1)
			{
			    j = rand.nextInt(4) + Math.max(0, j1 - 63);
			    iblockstate1 = iblockstate1.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND
				    ? RED_SANDSTONE
				    : SANDSTONE;
			}
		    }
		}
	    }
	}
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
	super.decorate(worldIn, rand, pos);

	WorldGenAzureVineBridge AZURE_VINES = new WorldGenAzureVineBridge();
	int vineAttempts = 7;
	for (int l = 0; l < vineAttempts; l++)
	{
	    // Generate the vines starting at y=70, and randomly throughout the chunk
	    // the +8 offsets are to avoid cascading chunk generation, which lags the game
	    int i = rand.nextInt(16) + 8;
	    int j = 70;
	    int k = rand.nextInt(16) + 8;
	    AZURE_VINES.generate(worldIn, rand, pos.add(i, j, k));
	}

	WorldGenAzureDoublePlant DOUBLE_PLANT = new WorldGenAzureDoublePlant();
	int doublePlants = rand.nextInt(3);
	for (int l = 0; l < doublePlants; l++)
	{
	    int i = rand.nextInt(16) + 8;
	    int j = 70;
	    int k = rand.nextInt(16) + 8;
	    DOUBLE_PLANT.generate(worldIn, rand, pos.add(i, j, k));
	}

	int flowers = rand.nextInt(2);
	for (int l = 0; l < flowers; l++)
	{
	    int i = rand.nextInt(16) + 8;
	    int j = 70;
	    int k = rand.nextInt(16) + 8;
	    FLOWERS.generate(worldIn, rand, pos.add(i, j, k));
	}

	WorldGenAzureVines worldgenvines = new WorldGenAzureVines();
	int vines = 10;
	for (int j1 = 0; j1 < vines; ++j1)
	{
	    int x = rand.nextInt(16) + 8;
	    int y = rand.nextInt(45) + 15;
	    int z = rand.nextInt(16) + 8;
	    worldgenvines.generate(worldIn, rand, pos.add(x, y, z));
	}
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenerator getRandomWorldGenForGrass(Random rand)
    {
	return new WorldGenAzureFoliage(TALL_GRASS_LIST, 128);
    }
}
