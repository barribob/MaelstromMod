package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileGoldenRepeater extends ProjectileGun
{
    public ProjectileGoldenRepeater(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage, stack);
    }

    public ProjectileGoldenRepeater(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileGoldenRepeater(World worldIn, double x, double y, double z)
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
	ParticleManager.spawnEffect(world, ModUtils.entityPos(this), new Vec3d(0.8, 0.8, 0.4));
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	ModUtils.handleBulletImpact(result.entityHit, this, this.getGunDamage(result.entityHit), DamageSource.causeThrownDamage(this, this.shootingEntity),
		this.getKnockback());
	super.onHit(result);
    }
}