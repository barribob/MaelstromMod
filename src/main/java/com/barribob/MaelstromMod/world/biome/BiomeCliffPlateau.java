package com.barribob.MaelstromMod.world.biome;

import java.util.Random;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMage;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic.MobSpawnData;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.world.gen.WorldGenMaelstrom;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	this.decorator.flowersPerChunk = -999;
	this.decorator.deadBushPerChunk = 4;
	this.decorator.sandPatchesPerChunk = 0;
	this.decorator.gravelPatchesPerChunk = 0;

	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntitySheep.class, 12, 4, 4));
	this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityBat.class, 10, 8, 8));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
	double d0 = GRASS_COLOR_NOISE.getValue(pos.getX() * 0.0225D, pos.getZ() * 0.0225D);
	return d0 < -0.1D ? 4671303 : 4665927;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos)
    {
	return 4671303;
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
	return rand.nextInt(5) > 0 ? SAVANNA_TREE : TREE_FEATURE;
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
	WorldGenMaelstrom worldgenmaelstrom = new WorldGenMaelstrom(ModBlocks.DECAYING_AZURE_MAELSTROM, ModBlocks.CLIFF_MAELSTROM_CORE,
		(tileEntity) -> tileEntity.getSpawnerBaseLogic().setData(
			new MobSpawnData[] {
				new MobSpawnData(Reference.MOD_ID + ":" + EntityMaelstromMage.ID, new Element[] { Element.NONE, Element.GOLDEN }, new int[] { 1, 4 }, 1),
				new MobSpawnData(Reference.MOD_ID + ":" + EntityShade.ID, new Element[] { Element.NONE, Element.GOLDEN }, new int[] { 1, 4 }, 1),
			},
			new int[] { 1, 1 },
			3,
			2.0f,
			16));
	if (rand.nextInt(5) == 0)
	{
	    int x1 = rand.nextInt(8) + 16;
	    int y = 256;
	    int z1 = rand.nextInt(8) + 16;
	    worldgenmaelstrom.generate(world, rand, pos.add(x1, y, z1));
	}
	super.decorate(world, rand, pos);
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
