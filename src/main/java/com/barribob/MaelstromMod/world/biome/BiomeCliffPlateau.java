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

/**
 * 
 * The biome for the tall part of the cliff dimension
 *
 */
public class BiomeCliffPlateau extends BiomeDifferentStone
{
    public BiomeCliffPlateau()
    {
	super(new BiomeProperties("cliff_plateau").setBaseHeight(11F).setHeightVariation(0.05F).setTemperature(0.6F).setRainfall(0.3F), ModBlocks.CLIFF_STONE, ModBlocks.CLIFF_STONE);
	
	this.decorator.treesPerChunk = 0;
	this.decorator.grassPerChunk = 3;
    }

}
