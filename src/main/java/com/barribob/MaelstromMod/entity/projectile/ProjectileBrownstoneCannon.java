package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileBrownstoneCannon extends ProjectileGun
{
    private static final int PARTICLE_AMOUNT = 1;
    private static final int IMPACT_PARTICLE_AMOUNT = 20;
    private static final int EXPOSION_AREA_FACTOR = 2;

    public ProjectileBrownstoneCannon(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage, stack);
    }

    public ProjectileBrownstoneCannon(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileBrownstoneCannon(World worldIn, double x, double y, double z)
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
	    ParticleManager.spawnEffect(world, getPositionVector(), ModColors.BROWNSTONE);
	}
    }

    @Override
    protected void spawnImpactParticles()
    {
	ParticleManager.spawnParticlesInCircle(EXPOSION_AREA_FACTOR + 0.25f, 10, (pos) -> {
	    ParticleManager.spawnParticlesInCircle((float) (pos.x), 32, (pos2) -> {
		ParticleManager.spawnEffect(world, new Vec3d(pos2.x, pos.y, pos2.y).add(getPositionVector()), ModColors.BROWNSTONE);
	    });
	});
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	float knockbackFactor = 1 + this.getKnockback() * 0.4f;
	int fireFactor = this.isBurning() ? 5 : 0;
	ModUtils.handleAreaImpact(EXPOSION_AREA_FACTOR, (e) -> this.getGunDamage(e), this.shootingEntity, this.getPositionVector(),
		DamageSource.causeExplosionDamage(this.shootingEntity), knockbackFactor, fireFactor);
	this.playSound(SoundEvents.ENTITY_ILLAGER_CAST_SPELL, 1.0F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
	super.onHit(result);
    }
}
