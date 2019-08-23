package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityGeyser extends Projectile
{
    private int tickDelay = 30;
    private int blastRadius = 3;

    public EntityGeyser(World worldIn, EntityLivingBase throwerIn, float damage)
    {
	super(worldIn, throwerIn, damage);
	this.setNoGravity(true);
    }

    public EntityGeyser(World worldIn)
    {
	super(worldIn);
	this.setNoGravity(true);
    }

    public EntityGeyser(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
	this.setNoGravity(true);
    }

    /**
     * Set the delay before "going off" in ticks
     */
    public void setDelay(int delay)
    {
	this.tickDelay = delay;
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();

	if (this.ticksExisted >= this.tickDelay)
	{
	    this.onHit(null);
	}
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	if (result != null)
	{
	    return;
	}
	ModUtils.handleAreaImpact(blastRadius, (e) -> this.getDamage(), this.shootingEntity, this.getPositionVector(), DamageSource.causeExplosionDamage(this.shootingEntity));
	this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
	this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
	super.onHit(result);
    }

    @Override
    protected void spawnImpactParticles()
    {
	ModUtils.performNTimes(1000, (i) -> {
	    Vec3d offset = new Vec3d(ModRandom.getFloat(1), 0, ModRandom.getFloat(1)).normalize().scale(ModRandom.getFloat(blastRadius));
	    ParticleManager.spawnFirework(world, ModUtils.entityPos(this).add(offset), ModColors.CLIFF_STONE, ModUtils.yVec(rand.nextFloat() + 1).scale(0.5));
	});
    }

    @Override
    protected void spawnParticles()
    {
	ParticleManager.spawnParticlesInCircle(this.blastRadius, 30,
		(offset) -> ParticleManager.spawnEffect(world, ModUtils.entityPos(this).add(new Vec3d(offset.x, 0.5f, offset.y)), ModColors.CLIFF_STONE));
    }
}
