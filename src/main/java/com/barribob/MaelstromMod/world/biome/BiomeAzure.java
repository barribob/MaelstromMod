package com.barribob.MaelstromMod.world.biome;

import java.util.Random;

import com.barribob.MaelstromMod.blocks.BlockModBush;
import com.barribob.MaelstromMod.entity.entities.EntityAzureGolem;
import com.barribob.MaelstromMod.entity.entities.EntityDreamElk;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureDoublePlant;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureFoliage;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureVineBridge;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureVines;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenBigPlumTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenPlumTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenWaterfall;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * 
 * The biome for the azure dimension.
 *
 */
public class BiomeAzure extends BiomeDifferentStone
{
    protected static final WorldGenAzureTree AZURE_TREE = new WorldGenAzureTree(false);
    protected static final WorldGenPlumTree SMALL_PLUM_TREE = new WorldGenPlumTree(false, true);
    protected static final WorldGenBigPlumTree LARGE_PLUM_TREE = new WorldGenBigPlumTree(false);

    private final BlockModBush[] FLOWER_LIST = { (BlockModBush) ModBlocks.BLUE_DAISY, (BlockModBush) ModBlocks.RUBY_ORCHID };
    protected final WorldGenAzureFoliage FLOWERS = new WorldGenAzureFoliage(FLOWER_LIST, 64);

    private final BlockModBush[] TALL_GRASS_LIST = { (BlockModBush) ModBlocks.BROWNED_GRASS };

    public BiomeAzure()
    {
	super(new BiomeProperties("Fracture").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F).setRainDisabled().setWaterColor(10252253),
		ModBlocks.AZURE_GRASS, ModBlocks.DARK_AZURE_STONE);

	this.decorator.treesPerChunk = 2;
	this.decorator.grassPerChunk = 3;

	// Registers flowers when using bonemeal
	this.addFlower(ModBlocks.BLUE_DAISY.getDefaultState(), 1);
	this.addFlower(ModBlocks.RUBY_ORCHID.getDefaultState(), 1);

	// Add our mobs to spawn in the biome
	this.spawnableCreatureList.add(new SpawnListEntry(EntityDreamElk.class, 10, 1, 5));
	this.spawnableCreatureList.add(new SpawnListEntry(EntityAzureGolem.class, 1, 1, 1));
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
	}
	else if (rand.nextInt(largePlumTreeOdds) == 0)
	{
	    return LARGE_PLUM_TREE;
	}

	return AZURE_TREE;
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

	if (rand.nextInt(2) == 0)
	{
	    ModUtils.generateN(worldIn, rand, pos, 1, 30, 70, new WorldGenWaterfall(ModBlocks.DARK_AZURE_STONE));
	}
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand)
    {
	return new WorldGenAzureFoliage(TALL_GRASS_LIST, 128);
    }
}
