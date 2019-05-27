package com.barribob.MaelstromMod.world.dimension.cliff;

import com.barribob.MaelstromMod.init.BiomeInit;
import com.barribob.MaelstromMod.init.ModDimensions;
import com.barribob.MaelstromMod.world.biome.BiomeProviderMultiple;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import scala.actors.threadpool.Arrays;

/**
 * 
 * The Cliff dimension attributes are defined here
 *
 */
public class DimensionCliff extends WorldProvider
{
    // Overridden to change the biome provider
    @Override
    protected void init()
    {
	BiomeProvider provider = new BiomeProviderMultiple(this.world.getWorldInfo(), Arrays.asList(new Biome[] { BiomeInit.HIGH_CLIFF, BiomeInit.CLIFF_SWAMP }));
	this.biomeProvider = provider;
	this.hasSkyLight = true;
    }

    @Override
    public DimensionType getDimensionType()
    {
	return ModDimensions.CLIFF;
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
	return new ChunkGeneratorCliff(world, world.getSeed(), true, "");
    }

    @Override
    public boolean canRespawnHere()
    {
	return false;
    }

    @Override
    public boolean isSurfaceWorld()
    {
	return true;
    }

    public WorldSleepResult canSleepAt(net.minecraft.entity.player.EntityPlayer player, BlockPos pos)
    {
	return WorldSleepResult.DENY;
    }

    @Override
    public double getVoidFogYFactor()
    {
	return 8.0f / 256f;
    }
}
