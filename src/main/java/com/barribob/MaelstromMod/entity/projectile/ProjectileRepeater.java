package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ProjectileRepeater extends ProjectileGun
{
    public ProjectileRepeater(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage, stack);
    }

    public ProjectileRepeater(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileRepeater(World worldIn, double x, double y, double z)
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
	world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, 0, 0, 0);
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	ModUtils.handleBulletImpact(result.entityHit, this, this.getGunDamage(result.entityHit), ModDamageSource.causeElementalThrownDamage(this, this.shootingEntity, getElement()),
		this.getKnockback());
	super.onHit(result);
    }
}