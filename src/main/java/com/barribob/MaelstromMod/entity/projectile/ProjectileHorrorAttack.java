package com.barribob.MaelstromMod.entity.projectile;

import java.util.List;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProjectileHorrorAttack extends EntityThrowable
{
    private static final float DAMAGE = 4.0f;
    private static final float TRAVEL_RANGE = 20.0f;
    private static final byte PARTICLE_BYTE = 3;
    private static final int PARTICLE_AMOUNT = 1;
    private static final int IMPACT_PARTICLE_AMOUNT = 20;
    private static final int EXPOSION_AREA_FACTOR = 6;

    public ProjectileHorrorAttack(World worldIn, EntityLivingBase throwerIn)
    {
	super(worldIn, throwerIn);
    }

    public ProjectileHorrorAttack(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileHorrorAttack(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();

	// Despawn if a certain distance away from its thrower
	if (!this.world.isRemote)
	{
	    if (this.getThrower() != null && this.getDistance(this.getThrower()) > TRAVEL_RANGE)
	    {
		this.world.setEntityState(this, PARTICLE_BYTE);
		this.setDead();
	    }
	}
	else
	{
	    spawnParticles(world);
	}
    }

    /**
     * Called every update to spawn particles
     * 
     * @param world
     */
    protected void spawnParticles(World world)
    {
	for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	{
	    ParticleManager.spawnMaelstromSmoke(world, rand, new Vec3d(this.posX, this.posY, this.posZ));
	}
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
	if (result.entityHit != null)
	    return;

	/*
	 * Find all entities in a certain area and deal damage to them
	 */
	List list = world.getEntitiesWithinAABBExcludingEntity(this,
		this.getEntityBoundingBox().expand(EXPOSION_AREA_FACTOR, EXPOSION_AREA_FACTOR, EXPOSION_AREA_FACTOR));
	if (list != null)
	{
	    for (Object entity : list)
	    {
		if (entity instanceof EntityLivingBase && !(entity instanceof EntityMaelstromMob) && this.getThrower() != null)
		{
		    ((EntityLivingBase) entity).attackEntityFrom(ModDamageSource.causeMaelstromExplosionDamage(this.getThrower()), DAMAGE);
		}
	    }
	}

	this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));

	if (!this.world.isRemote)
	{
	    this.world.setEntityState(this, this.PARTICLE_BYTE);
	    this.setDead();
	}
    }

    /**
     * Handler for {@link World#setEntityState}
     * Connected through setEntityState to spawn particles
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == this.PARTICLE_BYTE)
	{
	    for (int i = 0; i < this.IMPACT_PARTICLE_AMOUNT; i++)
	    {
		Vec3d vec1 = new Vec3d(this.posX + ModRandom.getFloat(1), this.posY + ModRandom.getFloat(1), this.posZ + ModRandom.getFloat(1));
		ParticleManager.spawnMaelstromExplosion(world, rand, vec1);
	    }
	}
    }
}
