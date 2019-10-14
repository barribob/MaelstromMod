package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileIronShadeAttack extends ProjectileShadeAttack
{
    public ProjectileIronShadeAttack(World worldIn, EntityLivingBase throwerIn, float damage)
    {
	super(worldIn, throwerIn, damage);
	this.setTravelRange(4);
    }

    public ProjectileIronShadeAttack(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileIronShadeAttack(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }

    @Override
    protected void spawnParticles()
    {
	ModUtils.performNTimes(10, (i) -> {
	    ParticleManager.spawnDarkFlames(world, rand,
		    getPositionVector().add(ModRandom.randVec()).add(new Vec3d(this.motionX, this.motionY, this.motionZ).normalize().scale(1)),
		    ModUtils.yVec(-0.3f));
	});
    }
}
