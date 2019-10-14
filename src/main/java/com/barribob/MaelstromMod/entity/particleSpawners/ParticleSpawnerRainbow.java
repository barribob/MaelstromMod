package com.barribob.MaelstromMod.entity.particleSpawners;

import com.barribob.MaelstromMod.entity.util.EntityParticleSpawner;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.world.World;

public class ParticleSpawnerRainbow extends EntityParticleSpawner
{
    public ParticleSpawnerRainbow(World worldIn)
    {
	super(worldIn);
    }

    @Override
    protected void spawnParticles()
    {
	ParticleManager.spawnEffect(world, getPositionVector().add(ModUtils.yVec(2.0f)), ModColors.RED);
	ParticleManager.spawnEffect(world, getPositionVector().add(ModUtils.yVec(1.7f)), ModColors.ORANGE);
	ParticleManager.spawnEffect(world, getPositionVector().add(ModUtils.yVec(1.4f)), ModColors.YELLOW);
	ParticleManager.spawnEffect(world, getPositionVector().add(ModUtils.yVec(1.1f)), ModColors.GREEN);
	ParticleManager.spawnEffect(world, getPositionVector().add(ModUtils.yVec(0.8f)), ModColors.BLUE);
	ParticleManager.spawnEffect(world, getPositionVector().add(ModUtils.yVec(0.5f)), ModColors.PURPLE);
    }
}
