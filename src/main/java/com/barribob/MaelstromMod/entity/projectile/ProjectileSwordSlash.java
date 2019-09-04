package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ProjectileSwordSlash extends Projectile
{
    private static final int PARTICLE_AMOUNT = 10;

    public ProjectileSwordSlash(World worldIn, EntityLivingBase throwerIn, float damage)
    {
	super(worldIn, throwerIn, damage);
	this.setNoGravity(true);
	this.setSize(0.25f, 0.25f);
    }

    public ProjectileSwordSlash(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileSwordSlash(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	if (!world.isRemote && this.shootingEntity != null)
	{
	    if (this.world instanceof WorldServer)
	    {
		((WorldServer) this.world).spawnParticle(EnumParticleTypes.SWEEP_ATTACK, this.posX, this.posY, this.posZ, 0, 0, 0.0D, 0, 0.0D);
	    }

	    ModUtils.handleAreaImpact(1, (e) -> this.getDamage(), this.shootingEntity, this.getPositionVector(), DamageSource.causeThrownDamage(this, this.shootingEntity),
		    0.2f, 0);
	}
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	if (result.entityHit == null)
	{
	    super.onHit(result);
	    return;
	}
    }
}
