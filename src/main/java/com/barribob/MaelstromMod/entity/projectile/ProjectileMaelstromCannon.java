package com.barribob.MaelstromMod.entity.projectile;

import java.util.List;

import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * 
 * The projectile for the maelstrom cannon item
 *
 */
public class ProjectileMaelstromCannon extends ProjectileGun
{
    private static final int PARTICLE_AMOUNT = 1;
    private static final int IMPACT_PARTICLE_AMOUNT = 20;
    private static final int EXPOSION_AREA_FACTOR = 2;

    public ProjectileMaelstromCannon(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage, stack);
    }

    public ProjectileMaelstromCannon(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileMaelstromCannon(World worldIn, double x, double y, double z)
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
	for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	{
	    ParticleManager.spawnMaelstromSmoke(world, rand, new Vec3d(this.posX, this.posY, this.posZ), true);
	}
    }

    @Override
    protected void spawnImpactParticles()
    {
	for (int i = 0; i < this.IMPACT_PARTICLE_AMOUNT; i++)
	{
	    Vec3d vec1 = ModRandom.randVec().scale(EXPOSION_AREA_FACTOR * 0.25).add(getPositionVector()); 
	    ParticleManager.spawnMaelstromExplosion(world, rand, vec1);
	}
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	/*
	 * Find all entities in a certain area and deal damage to them
	 */
	List list = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(EXPOSION_AREA_FACTOR, EXPOSION_AREA_FACTOR, EXPOSION_AREA_FACTOR)
		.expand(-EXPOSION_AREA_FACTOR, -EXPOSION_AREA_FACTOR, -EXPOSION_AREA_FACTOR));
	if (list != null)
	{
	    for (Object entity : list)
	    {
		if (entity instanceof EntityLivingBase && this.shootingEntity != null && entity != this.shootingEntity)
		{
		    if (this.isBurning())
		    {
			((EntityLivingBase) entity).setFire(5);
		    }
		    
		    ((EntityLivingBase) entity).attackEntityFrom(ModDamageSource.causeMaelstromExplosionDamage((EntityLivingBase) this.shootingEntity), this.getGunDamage(((EntityLivingBase) entity)));

		    float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		    if (f1 > 0.0F)
		    {
			((EntityLivingBase) entity).addVelocity(this.motionX * (double) this.getKnockback() * 0.6000000238418579D / (double) f1, 0.1D,
				this.motionZ * (double) this.getKnockback() * 0.6000000238418579D / (double) f1);
		    }
		}
	    }
	}

	this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));

	super.onHit(result);
    }
}
