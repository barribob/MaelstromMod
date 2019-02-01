package com.barribob.MaelstromMod.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * The base projectile class for most projectiles in the mod
 * 
 * The other two constructors immediately cause the projectile to despawn (e.g. if the projectile is saved in midair, just despawn it)
 * 
 * This is intended for offensive projectiles, as there is damage included
 *
 */
public class Projectile extends EntityModThrowable
{
    private float travelRange;
    private final Vec3d startPos;
    private static final byte IMPACT_PARTICLE_BYTE = 3;
    private static final byte PARTICLE_BYTE = 4;
    private float damage = 0;

    public Projectile(World worldIn, EntityLivingBase throwerIn, float damage)
    {
	super(worldIn, throwerIn);
	this.travelRange = 20.0f;
	this.setDamage(damage);
	this.startPos = new Vec3d(this.posX, this.posY, this.posZ);
    }

    public Projectile(World worldIn)
    {
	super(worldIn);
	this.startPos = new Vec3d(this.posX, this.posY, this.posZ);
    }

    public Projectile(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
	this.startPos = new Vec3d(this.posX, this.posY, this.posZ);
    }

    /**
     * Set how far the projectile will be from its shooting entity before despawning
     * 
     * @param distance
     */
    public void setTravelRange(float distance)
    {
	this.travelRange = distance;
    }
    
    /**
     * Sets the damage for use by inherited projectiles
     */
    protected void setDamage(float damage)
    {
	this.damage = damage;
    }
    
    /*
     * For use of inhereted projectiles
     */
    protected float getDamage()
    {
	return this.damage;
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();

	this.world.setEntityState(this, this.PARTICLE_BYTE);

	// Despawn if a certain distance away from its origin
	if (this.shootingEntity != null && this.getDistance(startPos.x, startPos.y, startPos.z) > this.travelRange)
	{
	    this.world.setEntityState(this, this.IMPACT_PARTICLE_BYTE);
	    this.setDead();
	}
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	if (!world.isRemote)
	{
	    this.world.setEntityState(this, this.IMPACT_PARTICLE_BYTE);
	    this.setDead();
	}
    }

    /**
     * Handler for {@link World#setEntityState} Connected through setEntityState to
     * spawn particles
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == this.IMPACT_PARTICLE_BYTE)
	{
	    spawnImpactParticles();
	}
	if (id == this.PARTICLE_BYTE)
	{
	    spawnParticles();
	}
    }

    /**
     * Called every update to spawn particles
     * 
     * @param world
     */
    protected void spawnParticles()
    {
    }

    /**
     * Called on impact to spawn impact particles
     */
    protected void spawnImpactParticles()
    {
    }
}
