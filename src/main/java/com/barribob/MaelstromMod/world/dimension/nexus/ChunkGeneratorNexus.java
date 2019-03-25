package com.barribob.MaelstromMod.world.dimension.nexus;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.world.gen.maelstrom_fortress.MapGenMaelstromFortress;
import com.barribob.MaelstromMod.world.gen.nexus.MapGenNexus;

import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.IChunkGenerator;

/*
 * The main job here is to generate the nexus structure every 32 chunks or so
 */
public class ChunkGeneratorNexus implements IChunkGenerator
{
    private static final int STRUCTURE_SPACING_CHUNKS = 32;

    private final Random rand;
    private final World world;
    private final boolean mapFeaturesEnabled;
    private final WorldType terrainType;
    private final float[] biomeWeights;
    private ChunkGeneratorSettings settings;
    private double[] depthBuffer = new double[256];

    private MapGenNexus nexus = new MapGenNexus(STRUCTURE_SPACING_CHUNKS, 0, 1);
    
    private Biome[] biomesForGeneration;

    public ChunkGeneratorNexus(World worldIn, long seed, boolean mapFeaturesEnabledIn, String generatorOptions)
    {
	{
	    nexus = (MapGenNexus) net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(nexus,
		    net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_BRIDGE);
	}

	this.world = worldIn;
	this.mapFeaturesEnabled = mapFeaturesEnabledIn;
	this.terrainType = worldIn.getWorldInfo().getTerrainType();
	this.rand = new Random(seed);
	this.biomeWeights = new float[25];

	/**
	 * Does something to the biome weights, but I'm not sure what
	 */
	for (int i = -2; i <= 2; ++i)
	{
	    for (int j = -2; j <= 2; ++j)
	    {
		float f = 10.0F / MathHelper.sqrt((float) (i * i + j * j) + 0.2F);
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
    public Chunk generateChunk(int x, int z)
    {
	this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
	ChunkPrimer chunkprimer = new ChunkPrimer();
	this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);

	this.nexus.generate(this.world, x, z, chunkprimer);

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
     * Generate initial structures in this chunk, e.g. mineshafts, temples, lakes,
     * and dungeons
     */
    public void populate(int x, int z)
    {
	BlockFalling.fallInstantly = true;
	int i = x * 16;
	int j = z * 16;
	BlockPos blockpos = new BlockPos(i, 0, j);
	Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
	this.rand.setSeed(this.world.getSeed());
	long k = this.rand.nextLong() / 2L * 2L + 1L;
	long l = this.rand.nextLong() / 2L * 2L + 1L;
	this.rand.setSeed((long) x * k + (long) z * l ^ this.world.getSeed());
	boolean flag = false;
	ChunkPos chunkpos = new ChunkPos(x, z);

	net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, flag);

	this.nexus.generateStructure(this.world, this.rand, chunkpos);

	biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));

	net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, flag);

	BlockFalling.fallInstantly = false;
    }

    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
	Biome biome = this.world.getBiome(pos);
	return biome.getSpawnableList(creatureType);
    }

    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
    {
	if (!this.mapFeaturesEnabled)
	{
	    return false;
	}
	else if ("Nexus".equals(structureName) && this.nexus != null)
	{
	    return this.nexus.isInsideStructure(pos);
	}
	return false;
    }

    @Nullable
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
    {
	if (!this.mapFeaturesEnabled)
	{
	    return null;
	}
	else if ("Nexus".equals(structureName) && this.nexus != null)
	{
	    return this.nexus.getNearestStructurePos(worldIn, position, findUnexplored);
	}
	return null;
    }

    /**
     * Recreates data about structures intersecting given chunk (used for example by
     * getPossibleCreatures), without placing any blocks. When called for the first
     * time before any chunk is generated - also initializes the internal state
     * needed by getPossibleCreatures.
     */
    public void recreateStructures(Chunk chunkIn, int x, int z)
    {
	this.nexus.generate(this.world, x, z, (ChunkPrimer) null);
    }

    /**
     * Called to generate additional structures after initial worldgen, used by
     * ocean monuments
     */
    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z)
    {
	return false;
    }
}