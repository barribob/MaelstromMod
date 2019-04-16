package com.barribob.MaelstromMod.entity.util;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.block.Block;
import net.minecraft.client.particle.Particle;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAzurePortalSpawn extends EntityPortalSpawn
{
    public EntityAzurePortalSpawn(World worldIn)
    {
	super(worldIn);
    }

    public EntityAzurePortalSpawn(World worldIn, float x, float y, float z)
    {
	super(worldIn, x, y, z);
    }

    /*
     * Spawns a bunch of particles in fancy order using sin and cos functions
     */
    @Override
    protected void spawnParticles()
    {
	int offset = 0;
	int sectors = 90;
	int degreesPerSector = 360 / sectors;
	double size = 3;
	for (int i = 0; i < sectors; i++)
	{
	    double x = this.posX + 0.5 + Math.cos(i * degreesPerSector) * Math.sin(this.ticksExisted) * size + offset;
	    double y = this.posY + 3.5 + Math.sin(i * degreesPerSector) * Math.cos(this.ticksExisted) * size + offset;
	    double z = this.posZ + 0.5 + Math.cos(i * degreesPerSector) * Math.sin(this.ticksExisted) * size + offset;
	    ParticleManager.spawnEffect(world, new Vec3d(x, y, this.posZ + 0.5), new Vec3d(0.3, 0.4, 1));
	    ParticleManager.spawnEffect(world, new Vec3d(this.posX + 0.5, y, z), new Vec3d(0.3, 0.4, 1));
	}
    }

    @Override
    protected Block getRimBlock()
    {
	return Blocks.QUARTZ_BLOCK;
    }

    @Override
    protected Block getPortalBlock()
    {
	return ModBlocks.AZURE_PORTAL;
    }
}
