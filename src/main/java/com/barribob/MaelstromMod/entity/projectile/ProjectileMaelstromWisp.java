package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileMaelstromWisp extends Projectile
{
    private static final int PARTICLE_AMOUNT = 6;
    private static final int AREA_FACTOR = 2;

    public ProjectileMaelstromWisp(World worldIn, EntityLivingBase throwerIn, float baseDamage)
    {
	super(worldIn, throwerIn, baseDamage);
	this.setNoGravity(true);
    }

    public ProjectileMaelstromWisp(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileMaelstromWisp(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }

    @Override
    protected void spawnParticles()
    {
	for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	{
	    ParticleManager.spawnParticlesInCircle(AREA_FACTOR, 30, (pos) -> {
		Vec3d vel = new Vec3d(this.motionX, this.motionY, this.motionZ).normalize();

		// Conversion code taken from projectile shoot method
		float f1 = MathHelper.sqrt(vel.x * vel.x + vel.z * vel.z);
		Vec3d outer = pos.rotatePitch((float) (MathHelper.atan2(vel.y, f1))).rotateYaw((float) (MathHelper.atan2(vel.x, vel.z))).add(getPositionVector());
		Vec3d inner = pos.scale(0.85f).rotatePitch((float) (MathHelper.atan2(vel.y, f1))).rotateYaw((float) (MathHelper.atan2(vel.x, vel.z))).add(getPositionVector());
		ParticleManager.spawnMaelstromSmoke(world, rand, outer, true);
		ParticleManager.spawnMaelstromPotionParticle(world, rand, inner, false);
	    });
	}
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	ModUtils.handleAreaImpact(AREA_FACTOR, (e) -> this.getDamage(), this.shootingEntity, getPositionVector(),
		ModDamageSource.causeMalestromThrownDamage(this, this.shootingEntity));
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	// Only destroy if the collision is a block
	if (result.entityHit != null)
	{
	    return;
	}

	super.onHit(result);
    }
}
