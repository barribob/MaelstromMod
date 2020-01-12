package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileGoldenFireball extends ProjectileGun
{
    private static final int PARTICLE_AMOUNT = 15;
    private static final int IMPACT_PARTICLE_AMOUNT = 10;
    private static final int EXPOSION_AREA_FACTOR = 4;

    public ProjectileGoldenFireball(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage, stack);
	this.setNoGravity(true);
    }

    public ProjectileGoldenFireball(World worldIn)
    {
	super(worldIn);
	this.setNoGravity(true);
    }

    public ProjectileGoldenFireball(World worldIn, double x, double y, double z)
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
	float size = 0.5f;
	for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	{
	    ParticleManager.spawnColoredCustomSmoke(world, getPositionVector().add(ModRandom.randVec().scale(size)), ModColors.YELLOW);
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
	    unit = unit.normalize().scale(EXPOSION_AREA_FACTOR);
	    ParticleManager.spawnEffect(world, unit.add(getPositionVector()), ModColors.YELLOW);
	}
	for (int i = 0; i < this.IMPACT_PARTICLE_AMOUNT; i++)
	{
	    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + ModRandom.getFloat(EXPOSION_AREA_FACTOR),
		    this.posY + ModRandom.getFloat(EXPOSION_AREA_FACTOR), this.posZ + ModRandom.getFloat(EXPOSION_AREA_FACTOR), 0, 0, 0);
	    this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + ModRandom.getFloat(EXPOSION_AREA_FACTOR), this.posY + ModRandom.getFloat(EXPOSION_AREA_FACTOR),
		    this.posZ + ModRandom.getFloat(EXPOSION_AREA_FACTOR), 0, 0, 0);
	}
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	float knockbackFactor = 1.1f + this.getKnockback() * 0.4f;
	int fireFactor = this.isBurning() ? 10 : 5;
	ModUtils.handleAreaImpact(EXPOSION_AREA_FACTOR, (e) -> this.getGunDamage((e)), this.shootingEntity, this.getPositionVector(),
		ModDamageSource.causeElementalExplosionDamage(shootingEntity, getElement()), knockbackFactor, fireFactor);
	this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));

	super.onHit(result);
    }
}
