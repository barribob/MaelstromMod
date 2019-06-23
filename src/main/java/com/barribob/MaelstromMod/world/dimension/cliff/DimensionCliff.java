package com.barribob.MaelstromMod.world.dimension.cliff;

import com.barribob.MaelstromMod.init.BiomeInit;
import com.barribob.MaelstromMod.init.ModDimensions;
import com.barribob.MaelstromMod.world.biome.BiomeProviderMultiple;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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
    
    @Override
    public Vec3d getFogColor(float time, float p_76562_2_)
    {
	float f = MathHelper.cos(time * ((float) Math.PI * 2F)) * 2.0F + 0.5F;
	f = MathHelper.clamp(f, 0.0F, 1.0F);
	float f1 = 0.4f;
	float f2 = 0.3f;
	float f3 = 0.2F;
	f1 = f1 * (f * 0.70F + 0.06F);
	f2 = f2 * (f * 0.84F + 0.06F);
	f3 = f3 * (f * 0.70F + 0.09F);
	return new Vec3d((double) f1, (double) f2, (double) f3);
    }
}