package com.barribob.MaelstromMod.world.dimension.cliff;

import java.util.Arrays;
import java.util.List;

import com.barribob.MaelstromMod.init.BiomeInit;
import com.barribob.MaelstromMod.init.ModDimensions;
import com.barribob.MaelstromMod.world.biome.BiomeProviderMultiple;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;

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
	this.hasSkyLight = true;
	this.biomeProvider = new BiomeProviderMultiple(this.world.getWorldInfo()) {
	    @Override
	    public List<Biome> getBiomesToSpawnIn()
	    {
		return Arrays.asList(new Biome[] { BiomeInit.HIGH_CLIFF, BiomeInit.CLIFF_SWAMP });
	    }
	};
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

    @Override
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
    public float getCloudHeight()
    {
	return 260;
    }

    @Override
    public Vec3d getCloudColor(float partialTicks)
    {
	return new Vec3d(0.5f, 0.43f, 0.5f);
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
	return new Vec3d(f1, f2, f3);
    }

    @Override
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks)
    {
	float f = cameraEntity.world.getCelestialAngle(partialTicks);
	float f1 = MathHelper.cos(f * ((float) Math.PI * 2F)) * 2.0F + 0.5F;
	f1 = MathHelper.clamp(f1, 0.1F, 1.0F);
	int i = MathHelper.floor(cameraEntity.posX);
	int j = MathHelper.floor(cameraEntity.posY);
	int k = MathHelper.floor(cameraEntity.posZ);
	return new Vec3d(0.60f, 0.53f, 0.7f).scale(f1);
    }
}
