package com.barribob.MaelstromMod.entity.projectile;

import java.util.List;

import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * 
 * Launched from the will o the wisp staff
 *
 */
public class ProjectileWillOTheWisp extends ProjectileGun
{
    private static final int PARTICLE_AMOUNT = 6;
    private static final int AREA_FACTOR = 2;

    public ProjectileWillOTheWisp(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage, stack);
	this.setNoGravity(true);
    }

    public ProjectileWillOTheWisp(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileWillOTheWisp(World worldIn, double x, double y, double z)
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
	float f1 = 2.0f;
	float f2 = 0.3f;
	for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	{
	    ParticleManager.spawnMaelstromSmoke(world, rand,
		    new Vec3d(this.posX + ModRandom.getFloat(f1), this.posY + ModRandom.getFloat(f1), this.posZ + ModRandom.getFloat(f1)), true);
	    world.spawnParticle(EnumParticleTypes.FLAME, this.posX + ModRandom.getFloat(f2), this.posY + ModRandom.getFloat(f2), this.posZ + ModRandom.getFloat(f2), 0, 0, 0);
	}
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();

	/*
	 * Find all entities in a certain area and deal damage to them
	 */
	List list = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(AREA_FACTOR));
	if (list != null)
	{
	    for (Object entity : list)
	    {
		if (entity instanceof EntityLivingBase && this.shootingEntity != null && entity != this.shootingEntity)
		{
		    int burnTime = this.isBurning() ? 10 : 5;
		    ((EntityLivingBase) entity).setFire(burnTime);

		    ((EntityLivingBase) entity).attackEntityFrom(ModDamageSource.causeMalestromThrownDamage(this, this.shootingEntity),
			    this.getDamage(((EntityLivingBase) entity)));
		    ((EntityLivingBase) entity).addVelocity(0, 0.1D, 0);

		    float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

		    if (f1 > 0.0F)
		    {
			((EntityLivingBase) entity).addVelocity(this.motionX * (double) this.getKnockback() * 0.6000000238418579D / (double) f1, 0.0D,
				this.motionZ * (double) this.getKnockback() * 0.6000000238418579D / (double) f1);
		    }
		}
	    }
	}
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	// Only destroy if the collision is a block
	if (result.entityHit != null)
	{
	    return;
	}

	super.onHit(result);
    }
}
