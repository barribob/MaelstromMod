package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileMeteor extends ProjectileGun
{
    private static final int PARTICLE_AMOUNT = 15;
    private static final int EXPOSION_AREA_FACTOR = 6;

    public ProjectileMeteor(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage, stack);
	this.setNoGravity(true);
    }

    public ProjectileMeteor(World worldIn)
    {
	super(worldIn);
	this.setNoGravity(true);
    }

    public ProjectileMeteor(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
	this.setNoGravity(true);
    }

    /**
     * Called every update to spawn particles
     * 
     * @param world
     */
    @Override
    protected void spawnParticles()
    {
	float size = 0.25f;
	for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	{
	    ParticleManager.spawnEffect(this.world, getPositionVector().add(ModRandom.randVec().scale(size)), ModColors.PURPLE);
	}
    }

    @Override
    protected void spawnImpactParticles()
    {
	for (int i = 0; i < 1000; i++)
	{
	    Vec3d unit = new Vec3d(0, 1, 0);
	    unit = unit.rotatePitch((float) (Math.PI * ModRandom.getFloat(1)));
	    unit = unit.rotateYaw((float) (Math.PI * ModRandom.getFloat(1)));
	    unit = unit.normalize().scale(this.EXPOSION_AREA_FACTOR);
	    ParticleManager.spawnEffect(world, unit.add(getPositionVector()), ModColors.PURPLE);
	}
	for (int i = 0; i < 100; i++)
	{
	    ParticleManager.spawnMaelstromExplosion(world, rand, getPositionVector().add(ModRandom.randVec().scale(EXPOSION_AREA_FACTOR)));
	}
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	float knockbackFactor = 1.3f + this.getKnockback() * 0.4f;
	int fireFactor = this.isBurning() ? 5 : 0;
	ModUtils.handleAreaImpact(EXPOSION_AREA_FACTOR, (e) -> this.getGunDamage((e)), this.shootingEntity, this.getPositionVector(),
		DamageSource.causeExplosionDamage(this.shootingEntity), knockbackFactor, fireFactor);
	this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
	super.onHit(result);
    }
}
