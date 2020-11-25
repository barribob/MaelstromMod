package com.barribob.MaelstromMod.world.dimension.dark_nexus;

import com.barribob.MaelstromMod.init.BiomeInit;
import com.barribob.MaelstromMod.init.ModDimensions;
import com.barribob.MaelstromMod.world.dimension.nexus.ChunkGeneratorNexus;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class DimensionDarkNexus extends WorldProvider {
    @Override
    protected void init() {
        this.biomeProvider = new BiomeProviderSingle(BiomeInit.NEXUS);
    }

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.DARK_NEXUS;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorNexus(world, world.getSeed(), true, "");
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public WorldSleepResult canSleepAt(net.minecraft.entity.player.EntityPlayer player, BlockPos pos) {
        return WorldSleepResult.DENY;
    }

    @Override
    public Vec3d getFogColor(float time, float p_76562_2_) {
        float f1 = 100 / 255f;
        float f2 = 100 / 255F;
        float f3 = 100 / 255f;
        return new Vec3d(0, 0, 0);
    }

    @Override
    @Nullable
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
        return null;
    }

    @Override
    public boolean isSkyColored() {
        return false;
    }

    @Override
    public IRenderHandler getSkyRenderer() {
        return null;
    }
}
