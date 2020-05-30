package com.barribob.MaelstromMod.world.dimension.crimson_kingdom;

import com.barribob.MaelstromMod.init.BiomeInit;
import com.barribob.MaelstromMod.init.ModDimensions;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class DimensionCrimsonKingdom extends WorldProvider
{
    // Overridden to change the biome provider
    @Override
    protected void init()
    {
	this.biomeProvider = new BiomeProviderSingle(BiomeInit.CRIMSON_KINGDOM);
	this.hasSkyLight = false;
	this.world.setAllowedSpawnTypes(false, false);
    }

    @Override
    public DimensionType getDimensionType()
    {
	return ModDimensions.CRIMSON_KINGDOM;
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
	return new ChunkGeneratorCrimsonKingdom(world, world.getSeed(), true, "");
    }

    @Override
    public boolean canRespawnHere()
    {
	return false;
    }

    @Override
    public boolean isSurfaceWorld()
    {
	return false;
    }

    @Override
    public WorldSleepResult canSleepAt(net.minecraft.entity.player.EntityPlayer player, BlockPos pos)
    {
	return WorldSleepResult.DENY;
    }

    @Override
    public Vec3d getFogColor(float time, float p_76562_2_)
    {
	float f = MathHelper.cos(time * ((float) Math.PI * 2F)) * 2.0F + 0.5F;
	f = MathHelper.clamp(f, 0.0F, 1.0F);
	float f1 = 150 / 255f;
	float f2 = 150 / 255F;
	float f3 = 200 / 255f;
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
	return new Vec3d(150 / 255f, 150 / 255f, 200 / 255f).scale(f1);
    }
    
    @Override
    protected void generateLightBrightnessTable()
    {
	// For flat lighting, add a little bit of ambient light
	float f = 0.02f;
	float maxLight = 15;

	for (int i = 0; i <= maxLight; ++i)
	{
	    float f1 = 1 - i / maxLight;
	    this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) + f;
	}
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks)
    {
	return 1.2874f; // This was just guessed to get a sunset time
    }
}
