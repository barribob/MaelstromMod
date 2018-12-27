package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProjectileShadeAttack extends EntityThrowable
{
    private static final float DAMAGE = 3.0f;
    private static final float TRAVEL_RANGE = 2.0f;
    private static final byte PARTICLE_BYTE = 3;
    private static final int PARTICLE_AMOUNT = 10;

    public ProjectileShadeAttack(World worldIn, EntityLivingBase throwerIn)
    {
	super(worldIn, throwerIn);
	this.setNoGravity(true);
	this.setSize(0.4f, 0.4f);
    }

    public ProjectileShadeAttack(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileShadeAttack(World worldIn, double x, double y, double z)
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
     * @param world
     */
    protected void spawnParticles(World world)
    {
	for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	{
	    float particleSpread = 0.3f;
	    Vec3d vec1 = new Vec3d(this.posX + rand.nextFloat() * particleSpread,
		    this.posY + rand.nextFloat() * particleSpread, this.posZ + rand.nextFloat() * particleSpread);
	    ParticleManager.spawnMaelstromParticle(world, rand, vec1.add(new Vec3d(this.motionX, this.motionY, this.motionZ)));
	}
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
	if (result.entityHit != null && !(result.entityHit instanceof EntityMaelstromMob) && this.getThrower() != null)
	{
	    result.entityHit.attackEntityFrom(ModDamageSource.causeMaelstromMeleeDamage(this.getThrower()), DAMAGE);
	}
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
    }
}
