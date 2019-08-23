package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ProjectileSwampSpittle extends Projectile
{
    public ProjectileSwampSpittle(World worldIn, EntityLivingBase throwerIn, float damage)
    {
	super(worldIn, throwerIn, damage);
    }

    public ProjectileSwampSpittle(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileSwampSpittle(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }

    /**
     * Called every update to spawn particles
     * 
     * @param world
     */
    @Override
    protected void spawnParticles()
    {
	ParticleManager.spawnEffect(world, this.getPositionVector(), ModColors.CLIFF_STONE);
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	ModUtils.handleBulletImpact(result.entityHit, this, this.getDamage(), DamageSource.causeThrownDamage(this, this.shootingEntity));
	super.onHit(result);
    }
}
