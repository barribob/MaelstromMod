package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ProjectileAzureBullet extends ProjectileBullet
{
    public ProjectileAzureBullet(World worldIn, EntityLivingBase throwerIn, float damage, ItemStack stack)
    {
	super(worldIn, throwerIn, damage, stack);
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
	if (result.entityHit != null && this.shootingEntity != null)
	{
	    // Factor in fire
	    if (this.isBurning() && !(result.entityHit instanceof EntityEnderman))
	    {
		result.entityHit.setFire(5);
	    }

	    result.entityHit.hurtResistantTime = 0;
	    result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.shootingEntity), this.getGunDamage(result.entityHit));

	    // Factor in knockback strength
	    if (this.getKnockback() > 0)
	    {
		float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

		if (f1 > 0.0F)
		{
		    result.entityHit.addVelocity(this.motionX * (double) this.getKnockback() * 0.6000000238418579D / (double) f1, 0.1D,
			    this.motionZ * (double) this.getKnockback() * 0.6000000238418579D / (double) f1);
		}
	    }
	}
	else
	{
	    super.onHit(result);
	}
    }
}
