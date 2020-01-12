package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileBlackFireball extends Projectile
{
    private static final int PARTICLE_AMOUNT = 15;
    private static final int IMPACT_PARTICLE_AMOUNT = 150;
    private static final int EXPOSION_AREA_FACTOR = 3;

    public ProjectileBlackFireball(World worldIn, EntityLivingBase throwerIn, float baseDamage)
    {
	super(worldIn, throwerIn, baseDamage);
	this.setNoGravity(true);
    }

    public ProjectileBlackFireball(World worldIn)
    {
	super(worldIn);
	this.setNoGravity(true);
    }

    public ProjectileBlackFireball(World worldIn, double x, double y, double z)
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
	    ParticleManager.spawnDarkFlames(this.world, rand, ModUtils.entityPos(this).add(ModRandom.randVec().scale(size)));
	}
    }

    @Override
    protected void spawnImpactParticles()
    {
	float size = (float) (EXPOSION_AREA_FACTOR * this.getEntityBoundingBox().grow(EXPOSION_AREA_FACTOR).getAverageEdgeLength() * 0.5f);
	for (int i = 0; i < this.IMPACT_PARTICLE_AMOUNT; i++)
	{
	    Vec3d pos = ModUtils.entityPos(this).add(ModRandom.randVec().scale(size));
	    if (rand.nextInt(2) == 0)
	    {
		ParticleManager.spawnDarkFlames(this.world, rand, pos, ModRandom.randVec().scale(0.5f));
	    }
	    else
	    {
		this.world.spawnParticle(EnumParticleTypes.FLAME, pos.x, pos.y, pos.z, ModRandom.getFloat(0.25f), ModRandom.getFloat(0.25f), ModRandom.getFloat(0.25f));
	    }
	}
	for (int i = 0; i < 10; i++)
	{
	    Vec3d pos = ModUtils.entityPos(this).add(ModRandom.randVec().scale(size));
	    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, pos.x, pos.y, pos.z, ModRandom.getFloat(0.25f), ModRandom.getFloat(0.25f), ModRandom.getFloat(0.25f));
	}
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	ModUtils.handleAreaImpact(EXPOSION_AREA_FACTOR, (e) -> {
	    e.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 80, 0));
	    return this.getDamage();
	}, this.shootingEntity, this.getPositionVector(),
		ModDamageSource.causeElementalExplosionDamage(this.shootingEntity, getElement()), 1, 0);
	this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
	super.onHit(result);
    }
}
