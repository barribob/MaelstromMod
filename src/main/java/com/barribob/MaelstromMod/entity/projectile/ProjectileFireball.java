package com.barribob.MaelstromMod.entity.projectile;

import java.util.List;

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

/**
 * 
 * The projectile for the maelstrom cannon item
 *
 */
public class ProjectileFireball extends ProjectileGun
{
    private static final int PARTICLE_AMOUNT = 15;
    private static final int IMPACT_PARTICLE_AMOUNT = 10;
    private static final int EXPOSION_AREA_FACTOR = 3;
    public static final Vec3d FIREBALL_COLOR = new Vec3d(1.0, 0.6, 0.5);

    public ProjectileFireball(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage, stack);
	this.setNoGravity(true);
    }

    public ProjectileFireball(World worldIn)
    {
	super(worldIn);
	this.setNoGravity(true);
    }

    public ProjectileFireball(World worldIn, double x, double y, double z)
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
	    ParticleManager.spawnEffect(this.world, new Vec3d(this.posX, this.posY, this.posZ).add(new Vec3d(ModRandom.getFloat(size), ModRandom.getFloat(size), ModRandom.getFloat(size))), FIREBALL_COLOR);
	}
    }

    @Override
    protected void spawnImpactParticles()
    {
	int sectors = 60;
	int degreesPerSector = 360 / sectors;
	Vec3d pos = new Vec3d(this.posX, this.posY, this.posZ);
	float particleVel = 0.30f;
	for(int i = 0; i < sectors; i++)
	{
	    Vec3d circleX = new Vec3d(Math.cos(i * degreesPerSector), Math.sin(i * degreesPerSector), 0);
	    Vec3d circleY = new Vec3d(0, Math.sin(i * degreesPerSector), Math.cos(i * degreesPerSector));
	    Vec3d circleZ = new Vec3d(Math.cos(i * degreesPerSector), 0, Math.sin(i * degreesPerSector));
	    ParticleManager.spawnFirework(this.world, pos.add(circleX), FIREBALL_COLOR, circleX.normalize().scale(particleVel));
	    ParticleManager.spawnFirework(this.world, pos.add(circleY), FIREBALL_COLOR, circleY.normalize().scale(particleVel));
	    ParticleManager.spawnFirework(this.world, pos.add(circleZ), FIREBALL_COLOR, circleZ.normalize().scale(particleVel));
	}
	
	for(int i = 0; i < this.IMPACT_PARTICLE_AMOUNT; i++)
	{
	    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + ModRandom.getFloat(EXPOSION_AREA_FACTOR), this.posY + ModRandom.getFloat(EXPOSION_AREA_FACTOR), this.posZ + ModRandom.getFloat(EXPOSION_AREA_FACTOR), 0, 0, 0);
	    this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + ModRandom.getFloat(EXPOSION_AREA_FACTOR), this.posY + ModRandom.getFloat(EXPOSION_AREA_FACTOR), this.posZ + ModRandom.getFloat(EXPOSION_AREA_FACTOR), 0, 0, 0);
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
		if (entity instanceof EntityLivingBase && this.shootingEntity != null)
		{
		    if (this.isBurning())
		    {
			((EntityLivingBase) entity).setFire(10);
		    }
		    else 
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
