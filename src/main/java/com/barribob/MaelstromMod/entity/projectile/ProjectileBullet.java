package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * 
 * The bullet from the boomstick
 *
 */
public class ProjectileBullet extends ProjectileGun
{
    private static final int PARTICLE_AMOUNT = 3;

    public ProjectileBullet(World worldIn, EntityLivingBase throwerIn, float damage, ItemStack stack)
    {
	super(worldIn, throwerIn, damage, stack);
    }

    public ProjectileBullet(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileBullet(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	ModUtils.handleBulletImpact(result.entityHit, this, this.getGunDamage(result.entityHit), DamageSource.causeThrownDamage(this, this.shootingEntity),
		this.getKnockback());
	super.onHit(result);
    }
}
