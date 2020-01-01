package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileShadeAttack extends Projectile
{
    private static final int PARTICLE_AMOUNT = 10;

    public ProjectileShadeAttack(World worldIn, EntityLivingBase throwerIn, float damage)
    {
	super(worldIn, throwerIn, damage);
	this.setNoGravity(true);
	this.setSize(0.4f, 0.4f);
	this.setTravelRange(2.0f);
    }

    public ProjectileShadeAttack(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileShadeAttack(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }

    /**
     * Called every update to spawn particles
     * 
     * @param world
     */
    @Override
    protected void spawnParticles()
    {
	for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	{
	    float particleSpread = 0.3f;
	    Vec3d vec1 = new Vec3d(this.posX + rand.nextFloat() * particleSpread, this.posY + rand.nextFloat() * particleSpread,
		    this.posZ + rand.nextFloat() * particleSpread);
	    ParticleManager.spawnMaelstromParticle(world, rand, vec1.add(new Vec3d(this.motionX, this.motionY, this.motionZ)));
	}
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	ModUtils.handleBulletImpact(result.entityHit, this, this.getDamage(), ModDamageSource.causeElementalMeleeDamage(this.shootingEntity, this.getElement()));
	super.onHit(result);

    }
}
