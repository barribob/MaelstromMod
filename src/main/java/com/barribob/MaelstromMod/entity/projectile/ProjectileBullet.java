package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
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

    public ProjectileBullet(World worldIn, EntityLivingBase throwerIn, float damage, ItemStack stack, Element element)
    {
	super(worldIn, throwerIn, damage, stack, element);
    }

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
	ModUtils.handleBulletImpact(result.entityHit, this, this.getGunDamage(result.entityHit), ModDamageSource.causeElementalThrownDamage(this, this.shootingEntity, this.getElement()),
		this.getKnockback());
	super.onHit(result);
    }
}
