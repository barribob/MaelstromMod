package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ProjectileAzureBullet extends ProjectileBullet
{
    public ProjectileAzureBullet(World worldIn, EntityLivingBase throwerIn, float damage, ItemStack stack)
    {
	super(worldIn, throwerIn, damage, stack, Element.AZURE);
    }

    public ProjectileAzureBullet(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileAzureBullet(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }

    @Override
    protected void spawnParticles()
    {
	ParticleManager.spawnEffect(world, ModUtils.entityPos(this), ModColors.AZURE);
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	ModUtils.handleBulletImpact(result.entityHit, this, this.getGunDamage(result.entityHit),
		ModDamageSource.causeMaelstromThrownDamage(this, this.shootingEntity, this.getElement()), this.getKnockback());

	if (result.entityHit == null)
	{
	    super.onHit(result);
	}
    }
}
