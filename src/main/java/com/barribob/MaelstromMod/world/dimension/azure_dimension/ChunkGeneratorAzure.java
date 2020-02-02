package com.barribob.MaelstromMod.world.dimension.azure_dimension;

import com.barribob.MaelstromMod.entity.entities.EntityHorror;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic.MobSpawnData;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.world.dimension.WorldChunkGenerator;
import com.barribob.MaelstromMod.world.gen.WorldGenMaelstrom;
import com.barribob.MaelstromMod.world.gen.maelstrom_fortress.MapGenMaelstromFortress;
import com.barribob.MaelstromMod.world.gen.maelstrom_stronghold.MapGenMaelstromStronghold;
import com.barribob.MaelstromMod.world.gen.mineshaft.MapGenAzureMineshaft;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;

public class ChunkGeneratorAzure extends WorldChunkGenerator
{
    private static final int STRUCTURE_SPACING_CHUNKS = 40;
    private static final int MINESHAFT_STRUCTURE_NUMBER = 0;
    private static final int FORTRESS_STRUCTURE_NUMBER = 13;
    private static final int STRONGHOLD_STRUCTURE_NUMBER = 26;
    private static final MapGenStructure[] structures = {
	    new MapGenMaelstromFortress(STRUCTURE_SPACING_CHUNKS, FORTRESS_STRUCTURE_NUMBER, 2),
	    new MapGenAzureMineshaft(STRUCTURE_SPACING_CHUNKS, MINESHAFT_STRUCTURE_NUMBER, 1),
	    new MapGenMaelstromStronghold(STRUCTURE_SPACING_CHUNKS, STRONGHOLD_STRUCTURE_NUMBER, 1) };

    private MapGenBase caveGenerator = new MapGenAzureCaves();
    private MapGenBase ravineGenerator = new MapGenAzureRavine();

    public ChunkGeneratorAzure(World worldIn, long seed, boolean mapFeaturesEnabledIn, String generatorOptions)
    {
	super(worldIn, seed, mapFeaturesEnabledIn, generatorOptions, ModBlocks.DARK_AZURE_STONE, Blocks.WATER, structures);
    }

    @Override
    protected void generateCaves(int x, int z, ChunkPrimer cp)
    {
	if (this.settings.useCaves)
	{
	    this.caveGenerator.generate(this.world, x, z, cp);
	}

	if (this.settings.useRavines)
	{
	    this.ravineGenerator.generate(this.world, x, z, cp);
	}
    }

    @Override
    protected void generateFeatures(BlockPos pos, Biome biome)
    {
	WorldGenMaelstrom worldgenmaelstrom = new WorldGenMaelstrom(ModBlocks.DECAYING_MAELSTROM, ModBlocks.AZURE_MAELSTROM_CORE,
		(tileEntity) -> tileEntity.getSpawnerBaseLogic().setData(
			new MobSpawnData[] {
				new MobSpawnData(ModEntities.getID(EntityShade.class), new Element[] { Element.AZURE, Element.NONE }, new int[] { 1, 4 }, 1),
				new MobSpawnData(ModEntities.getID(EntityHorror.class), Element.NONE)
			},
			new int[] { 1, 1 },
			3,
			LevelHandler.AZURE_OVERWORLD,
			16));
	if (rand.nextInt(15) == 0)
	{
	    int x1 = rand.nextInt(8) + 16;
	    int y = 70;
	    int z1 = rand.nextInt(8) + 16;
	    worldgenmaelstrom.generate(this.world, rand, pos.add(x1, y, z1));
	}
    }
}