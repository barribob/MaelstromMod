package com.barribob.MaelstromMod.world.dimension.azure_dimension;

import com.barribob.MaelstromMod.init.BiomeInit;
import com.barribob.MaelstromMod.init.ModDimensions;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;

/**
 * 
 * The Azure dimension attributes are defined here
 *
 */
public class DimensionAzure extends WorldProvider
{
    // Overridden to change the biome provider
    @Override
    protected void init()
    {
	this.biomeProvider = new BiomeProviderSingle(BiomeInit.AZURE);
	this.hasSkyLight = true;
    }
        
    @Override
    public DimensionType getDimensionType()
    {
	return ModDimensions.AZURE;
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
	return new ChunkGeneratorAzure(world, world.getSeed(), true, "");
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
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_)
    {
        float f = MathHelper.cos(p_76562_1_ * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        float f1 = 0.7529412F;
        float f2 = 0.84705883F;
        float f3 = 1.0F;
        f1 = f1 * (f * 0.70F + 0.06F);
        f2 = f2 * (f * 0.84F + 0.06F);
        f3 = f3 * (f * 0.70F + 0.09F);
        return new Vec3d((double)f1, (double)f2, (double)f3);
    }
}
