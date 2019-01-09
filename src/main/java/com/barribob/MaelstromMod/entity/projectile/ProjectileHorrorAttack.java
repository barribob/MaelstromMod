package com.barribob.MaelstromMod.entity.projectile;

import java.util.List;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProjectileHorrorAttack extends Projectile
{
    private static final float DAMAGE = 4.0f;
    private static final int PARTICLE_AMOUNT = 1;
    private static final int IMPACT_PARTICLE_AMOUNT = 20;
    private static final int EXPOSION_AREA_FACTOR = 2;

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

    /**
     * Called every update to spawn particles
     * 
     * @param world
     */
    protected void spawnParticles()
    {
	for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	{
	    ParticleManager.spawnMaelstromSmoke(world, rand, new Vec3d(this.posX, this.posY, this.posZ));
	}
    }
    
    @Override
    protected void spawnImpactParticles()
    {
	for (int i = 0; i < this.IMPACT_PARTICLE_AMOUNT; i++)
	{
	    Vec3d vec1 = new Vec3d(this.posX + ModRandom.getFloat(1), this.posY + ModRandom.getFloat(1), this.posZ + ModRandom.getFloat(1));
	    ParticleManager.spawnMaelstromExplosion(world, rand, vec1);
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
	List list = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(EXPOSION_AREA_FACTOR, EXPOSION_AREA_FACTOR, EXPOSION_AREA_FACTOR).expand(-EXPOSION_AREA_FACTOR, -EXPOSION_AREA_FACTOR, -EXPOSION_AREA_FACTOR));
	if (list != null)
	{
	    for (Object entity : list)
	    {
		if (entity instanceof EntityLivingBase && !(entity instanceof EntityMaelstromMob) && this.shootingEntity != null)
		{
		    ((EntityLivingBase) entity).attackEntityFrom(ModDamageSource.causeMaelstromExplosionDamage((EntityLivingBase)this.shootingEntity), DAMAGE);
		}
	    }
	}

	this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));

	super.onHit(result);
    }
}
