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

import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	super(new BiomeProperties("Fracture").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F).setRainDisabled().setWaterColor(10252253), Blocks.GRASS,
		ModBlocks.DARK_AZURE_STONE);

	this.decorator.treesPerChunk = 8;
	this.decorator.grassPerChunk = 4;
	this.decorator.flowersPerChunk = 1;
	this.decorator.mushroomsPerChunk = 1;

	// Registers flowers when using bonemeal
	this.addFlower(ModBlocks.BLUE_DAISY.getDefaultState(), 1);
	this.addFlower(ModBlocks.RUBY_ORCHID.getDefaultState(), 1);

	// Add our mobs to spawn in the biome
	this.spawnableCreatureList.add(new SpawnListEntry(EntityDreamElk.class, 10, 1, 5));
	this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 10, 1, 5));
	this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 3, 1, 5));
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
	int azureTreeOdds = 5;
	if (rand.nextInt(plumTreeOdds) == 0)
	{
	    return SMALL_PLUM_TREE;
	}
	else if (rand.nextInt(largePlumTreeOdds) == 0)
	{
	    return LARGE_PLUM_TREE;
	}
	else if (rand.nextInt(azureTreeOdds) == 0)
	{
	    return AZURE_TREE;
	}

	return new WorldGenBigTree(false);
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

    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand)
    {
	if (rand.nextInt(2) == 0)
	{
	    return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
	}
	return new WorldGenAzureFoliage(TALL_GRASS_LIST, 128);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
	double d0 = GRASS_COLOR_NOISE.getValue(pos.getX() * 0.0225D, pos.getZ() * 0.0225D);
	return d0 < -0.1D ? 3109474 : 2837034;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos)
    {
	return 2837034;
    }

    @Override
    public void generateTopBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal, Block stoneBlock)
    {
	int seaLevel = worldIn.getSeaLevel();
	IBlockState topBlock = this.topBlock;
	IBlockState fillerBlock = this.fillerBlock;
	int j = -1;
	int noise = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
	int xPos = x & 15;
	int zPos = z & 15;
	int blockHeight = -1;
	BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

	for (int y = 255; y >= 0; --y)
	{
	    if (y <= rand.nextInt(5))
	    {
		chunkPrimerIn.setBlockState(zPos, y, xPos, BEDROCK);
	    }
	    else
	    {
		IBlockState currentState = chunkPrimerIn.getBlockState(zPos, y, xPos);

		if (currentState.getMaterial() == Material.AIR)
		{
		    j = -1;
		}
		/**
		 * The line below is the block that needs to be match to the custom stone in
		 * order to make the block replace correctly
		 */
		else if (currentState.getBlock() == stoneBlock)
		{
		    // Find the block height
		    if (blockHeight == -1)
		    {
			blockHeight = y;
		    }

		    if (j == -1) // Only gets set intermittently
		    {
			if (noise <= 0)
			{
			    topBlock = AIR;
			    fillerBlock = stoneBlock.getDefaultState();
			}
			else if (y >= seaLevel - 4 && y <= seaLevel + 1)
			{
			    topBlock = this.topBlock;
			    fillerBlock = this.fillerBlock;
			}

			// Adding water
			if (y < seaLevel && (topBlock == null || topBlock.getMaterial() == Material.AIR))
			{
			    if (this.getTemperature(blockpos$mutableblockpos.setPos(x, y, z)) < 0.15F)
			    {
				topBlock = ICE;
			    }
			    else
			    {
				topBlock = WATER;
			    }
			}

			j = y;

			// Only set the top block if we are above sea level
			if (y >= seaLevel - 1)
			{
			    chunkPrimerIn.setBlockState(zPos, y, xPos, topBlock);
			}
			else if (y < seaLevel - 7 - noise) // When lower than a certain threshold, the top block is stone
			{
			    topBlock = AIR;
			    fillerBlock = stoneBlock.getDefaultState();
			}
			else
			{
			    chunkPrimerIn.setBlockState(zPos, y, xPos, this.getStoneBlock(y, blockHeight).getDefaultState());
			}
		    }
		    else if (j > 0)
		    {
			--j;
			chunkPrimerIn.setBlockState(zPos, y, xPos, this.getStoneBlock(y, blockHeight).getDefaultState()); // Set the filler block
		    }
		}
	    }
	}
    }

    private Block getStoneBlock(int y, int blockHeight)
    {
	int heightBelow = blockHeight - y;
	if (ModUtils.isBetween(0, 4, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE;
	}
	if (ModUtils.isBetween(4, 7, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE_1;
	}
	if (ModUtils.isBetween(7, 9, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE;
	}
	if (ModUtils.isBetween(9, 12, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE_1;
	}
	if (ModUtils.isBetween(12, 17, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE_2;
	}
	if (ModUtils.isBetween(17, 20, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE_3;
	}
	if (ModUtils.isBetween(20, 22, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE_4;
	}
	if (ModUtils.isBetween(22, 25, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE_2;
	}
	if (ModUtils.isBetween(25, 27, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE_5;
	}
	if (ModUtils.isBetween(27, 31, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE_2;
	}
	if (ModUtils.isBetween(31, 35, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE;
	}
	if (ModUtils.isBetween(35, 37, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE_1;
	}
	if (ModUtils.isBetween(37, 41, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE_3;
	}
	if (ModUtils.isBetween(41, 44, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE_5;
	}
	if (ModUtils.isBetween(44, 48, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE_4;
	}
	if (ModUtils.isBetween(48, 50, heightBelow))
	{
	    return Blocks.PRISMARINE;
	}
	if (ModUtils.isBetween(50, 53, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE_2;
	}
	if (ModUtils.isBetween(53, 58, heightBelow))
	{
	    return ModBlocks.DARK_AZURE_STONE_3;
	}
	return Blocks.PRISMARINE;
    }
}
