package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * 
 * The projectile for the maelstrom cannon item
 *
 */
public class ProjectilePumpkin extends ProjectileGun
{
    private static final int PARTICLE_AMOUNT = 1;
    private static final int IMPACT_PARTICLE_AMOUNT = 50;
    private static final int EXPOSION_AREA_FACTOR = 4;
    private int rings;
    private int maxRings = ModRandom.range(4, 7);

    public ProjectilePumpkin(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage, stack);
    }

    public ProjectilePumpkin(World worldIn)
    {
	super(worldIn);
    }

    public ProjectilePumpkin(World worldIn, double x, double y, double z)
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

	float tailWidth = 0.25f;
	for (int i = 0; i < 5; i++)
	{
	    ParticleManager.spawnFirework(world,
		    new Vec3d(this.posX, this.posY, this.posZ).add(new Vec3d(ModRandom.getFloat(tailWidth), ModRandom.getFloat(tailWidth), ModRandom.getFloat(tailWidth))),
		    new Vec3d(0.9, 0.9, 0.5));
	}
	if (this.rings < this.maxRings)
	{
	    Vec3d vel = new Vec3d(this.motionX, this.motionY, this.motionZ).normalize();
	    int sectors = 60;
	    int degreesPerSector = 360 / 60;
	    float circleSize = Math.max(0, ModRandom.getFloat(1.5f) + 1);
	    int completeness = ModRandom.range(40, sectors);
	    float particleVelocity = 0.15f;
	    for (int i = 0; i < completeness; i++)
	    {
		Vec3d circle = new Vec3d(Math.cos(i * degreesPerSector), Math.sin(i * degreesPerSector), 0);
		Vec3d offset = circle.crossProduct(vel).normalize().scale(circleSize);
		ParticleManager.spawnFirework(world, new Vec3d(this.posX, this.posY, this.posZ).add(offset), new Vec3d(0.9, 0.9, 0.5), offset.scale(particleVelocity));
	    }
	    this.rings++;
	}
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	ModUtils.handleBulletImpact(result.entityHit, this, (float) (this.getGunDamage(result.entityHit) * this.getDistanceTraveled()),
		DamageSource.causeThrownDamage(this, this.shootingEntity), this.getKnockback());
	super.onHit(result);
    }
}
