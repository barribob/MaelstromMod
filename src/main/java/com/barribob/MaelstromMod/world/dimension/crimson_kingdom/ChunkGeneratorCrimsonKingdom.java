package com.barribob.MaelstromMod.world.dimension.crimson_kingdom;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class ChunkGeneratorCrimsonKingdom implements IChunkGenerator
{
    private final Random rand;
    private final World world;
    private final float[] biomeWeights;
    private ChunkGeneratorSettings settings;
    private Biome[] biomesForGeneration;

    public ChunkGeneratorCrimsonKingdom(World worldIn, long seed, boolean mapFeaturesEnabledIn, String generatorOptions)
    {
	this.world = worldIn;
	this.rand = new Random(seed);
	this.biomeWeights = new float[25];

	/**
	 * Does something to the biome weights, but I'm not sure what
	 */
	for (int i = -2; i <= 2; ++i)
	{
	    for (int j = -2; j <= 2; ++j)
	    {
		float f = 10.0F / MathHelper.sqrt(i * i + j * j + 0.2F);
		this.biomeWeights[i + 2 + (j + 2) * 5] = f;
	    }
	}

	/**
	 * Some options involving the oceans and lava oceans?
	 */
	if (generatorOptions != null)
	{
	    this.settings = ChunkGeneratorSettings.Factory.jsonToFactory(generatorOptions).build();
	    worldIn.setSeaLevel(this.settings.seaLevel);
	}
    }

    /**
     * Generates the chunk at the specified position, from scratch
     */
    @Override
    public Chunk generateChunk(int x, int z)
    {
	this.rand.setSeed(x * 341873128712L + z * 132897987541L);
	ChunkPrimer chunkprimer = new ChunkPrimer();
	this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);

	Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
	byte[] abyte = chunk.getBiomeArray();

	for (int i = 0; i < abyte.length; ++i)
	{
	    abyte[i] = (byte) Biome.getIdForBiome(this.biomesForGeneration[i]);
	}

	chunk.generateSkylightMap();
	return chunk;
    }

    /**
     * Generate initial structures in this chunk, e.g. mineshafts, temples, lakes, and dungeons
     */
    @Override
    public void populate(int x, int z)
    {
	BlockFalling.fallInstantly = true;
	this.rand.setSeed(this.world.getSeed());
	long k = this.rand.nextLong() / 2L * 2L + 1L;
	long l = this.rand.nextLong() / 2L * 2L + 1L;
	this.rand.setSeed(x * k + z * l ^ this.world.getSeed());
	BlockFalling.fallInstantly = false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
	Biome biome = this.world.getBiome(pos);
	return biome.getSpawnableList(creatureType);
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z)
    {
	return false;
    }

    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
    {
	return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z)
    {
    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
    {
	return false;
    }
}