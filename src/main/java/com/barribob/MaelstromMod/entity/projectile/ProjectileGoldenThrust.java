package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileGoldenThrust extends ProjectileShadeAttack
{
    public ProjectileGoldenThrust(World worldIn, EntityLivingBase throwerIn, float damage)
    {
	super(worldIn, throwerIn, damage);;
    }

    public ProjectileGoldenThrust(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileGoldenThrust(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }
    
    @Override
    protected void spawnParticles()
    {
        ParticleManager.spawnEffect(world, this.getPositionVector().add(ModRandom.randVec().scale(0.25)), ModColors.YELLOW);
    }
}
