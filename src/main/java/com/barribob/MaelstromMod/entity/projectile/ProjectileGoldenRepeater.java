package com.barribob.MaelstromMod.entity.projectile;

import java.util.List;

import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
    protected void spawnParticles()
    {
	ParticleManager.spawnEffect(world, ModUtils.entityPos(this), new Vec3d(0.8, 0.8, 0.4));
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	// Only destroy if the collision is a block
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

	super.onHit(result);
    }
}