package com.barribob.MaelstromMod.entity.projectile;

import java.util.List;

import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileGoldenFireball extends ProjectileGun
{
    private static final int PARTICLE_AMOUNT = 15;
    private static final int IMPACT_PARTICLE_AMOUNT = 10;
    private static final int EXPOSION_AREA_FACTOR = 4;

    public ProjectileGoldenFireball(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage, stack);
	this.setNoGravity(true);
    }

    public ProjectileGoldenFireball(World worldIn)
    {
	super(worldIn);
	this.setNoGravity(true);
    }

    public ProjectileGoldenFireball(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
	this.setNoGravity(true);
    }

    /**
     * Called every update to spawn particles
     * 
     * @param world
     */
    protected void spawnParticles()
    {
	float size = 0.5f;
	for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	{
	    ParticleManager.spawnEffect(world, getPositionVector().add(ModRandom.randVec().scale(size)), ModColors.YELLOW);
	}
    }

    @Override
    protected void spawnImpactParticles()
    {
	for (int i = 0; i < 1000; i++)
	{
	    Vec3d unit = new Vec3d(1, 1, 1).scale(ModRandom.randSign());
	    unit = unit.rotatePitch((float) (Math.PI * ModRandom.getFloat(0.5f)));
	    unit = unit.rotateYaw((float) (Math.PI * ModRandom.getFloat(0.5f)));
	    unit = unit.normalize().scale(EXPOSION_AREA_FACTOR);
	    ParticleManager.spawnEffect(world, unit.add(getPositionVector()), ModColors.YELLOW);
	}
	for (int i = 0; i < this.IMPACT_PARTICLE_AMOUNT; i++)
	{
	    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + ModRandom.getFloat(EXPOSION_AREA_FACTOR),
		    this.posY + ModRandom.getFloat(EXPOSION_AREA_FACTOR), this.posZ + ModRandom.getFloat(EXPOSION_AREA_FACTOR), 0, 0, 0);
	    this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + ModRandom.getFloat(EXPOSION_AREA_FACTOR), this.posY + ModRandom.getFloat(EXPOSION_AREA_FACTOR),
		    this.posZ + ModRandom.getFloat(EXPOSION_AREA_FACTOR), 0, 0, 0);
	}
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	if (result.entityHit == this.shootingEntity)
	    return;

	/*
	 * Find all entities in a certain area and deal damage to them
	 */
	List list = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(EXPOSION_AREA_FACTOR, EXPOSION_AREA_FACTOR, EXPOSION_AREA_FACTOR)
		.expand(-EXPOSION_AREA_FACTOR, -EXPOSION_AREA_FACTOR, -EXPOSION_AREA_FACTOR));
	if (list != null)
	{
	    for (Object entity : list)
	    {
		if (entity instanceof EntityLivingBase && this.shootingEntity != null && !this.shootingEntity.equals(entity))
		{
		    if (this.isBurning())
		    {
			((EntityLivingBase) entity).setFire(10);
		    }
		    else
		    {
			((EntityLivingBase) entity).setFire(5);
		    }

		    ((EntityLivingBase) entity).attackEntityFrom(ModDamageSource.causeMaelstromExplosionDamage((EntityLivingBase) this.shootingEntity),
			    this.getGunDamage(((EntityLivingBase) entity)));

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
