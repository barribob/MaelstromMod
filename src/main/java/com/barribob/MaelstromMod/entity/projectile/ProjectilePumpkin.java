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
public class ProjectilePumpkin extends ProjectileGun
{
    private static final int PARTICLE_AMOUNT = 1;
    private static final int IMPACT_PARTICLE_AMOUNT = 50;
    private static final int EXPOSION_AREA_FACTOR = 4;
    private int rings;
    private int maxRings = ModRandom.range(4, 7);

    public ProjectilePumpkin(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage, stack);
    }

    public ProjectilePumpkin(World worldIn)
    {
	super(worldIn);
    }

    public ProjectilePumpkin(World worldIn, double x, double y, double z)
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

	float tailWidth = 0.5f;
	for (int i = 0; i < 5; i++)
	{
	    ParticleManager.spawnFirework(world,
		    new Vec3d(this.posX, this.posY, this.posZ).add(new Vec3d(ModRandom.getFloat(tailWidth), ModRandom.getFloat(tailWidth), ModRandom.getFloat(tailWidth))),
		    new Vec3d(0.9, 0.9, 0.5));
	}
	if (this.rings < this.maxRings)
	{
	    Vec3d vel = new Vec3d(this.motionX, this.motionY, this.motionZ).normalize();
	    int sectors = 60;
	    int degreesPerSector = 360 / 60;
	    float circleSize = (float) Math.max(0, ModRandom.getFloat(1.5f) + 1);
	    int completeness = ModRandom.range(40, sectors);
	    float particleVelocity = 0.15f;
	    for (int i = 0; i < completeness; i++)
	    {
		Vec3d circle = new Vec3d(Math.cos(i * degreesPerSector), Math.sin(i * degreesPerSector), 0);
		Vec3d offset = circle.crossProduct(vel).normalize().scale(circleSize);
		ParticleManager.spawnFirework(world, new Vec3d(this.posX, this.posY, this.posZ).add(offset), new Vec3d(0.9, 0.9, 0.5), offset.scale(particleVelocity));
	    }
	    this.rings++;
	}
    }

    @Override
    protected void spawnImpactParticles()
    {
	for (int i = 0; i < this.IMPACT_PARTICLE_AMOUNT; i++)
	{
	    Vec3d vec1 = new Vec3d(this.posX + ModRandom.getFloat(EXPOSION_AREA_FACTOR), this.posY + ModRandom.getFloat(EXPOSION_AREA_FACTOR),
		    this.posZ + ModRandom.getFloat(EXPOSION_AREA_FACTOR));
	    ParticleManager.spawnFirework(world, vec1, new Vec3d(0.9, 0.9, 0.5));
	}

	for (int i = 0; i < 10; i++)
	{
	    Vec3d vec1 = new Vec3d(this.posX + ModRandom.getFloat(EXPOSION_AREA_FACTOR), this.posY + ModRandom.getFloat(EXPOSION_AREA_FACTOR),
		    this.posZ + ModRandom.getFloat(EXPOSION_AREA_FACTOR));
	    world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, vec1.x, vec1.y, vec1.z, 0, 0, 0);
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
	List list = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(EXPOSION_AREA_FACTOR));
	if (list != null)
	{
	    for (Object entity : list)
	    {
		if (entity instanceof EntityLivingBase && this.shootingEntity != null)
		{
		    if (this.isBurning())
		    {
			((EntityLivingBase) entity).setFire(5);
		    }
		    
		    float damage = (float) (this.getDistanceTraveled() * this.getGunDamage((EntityLivingBase) entity));
		    System.out.println(this.getDistanceTraveled() + " " + damage);

		    ((EntityLivingBase) entity).attackEntityFrom(ModDamageSource.causeMaelstromExplosionDamage((EntityLivingBase) this.shootingEntity), damage);

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
