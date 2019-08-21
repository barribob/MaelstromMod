package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.action.ActionGoldenMissles;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityOctoMissileLauncher extends Projectile
{
    private int endTime = 40;
    private MissileWrapper[] missiles = { new MissileWrapper(0.5f, 4.5f, 20), new MissileWrapper(1, 4, 25), new MissileWrapper(2, 3, 30), new MissileWrapper(2, 2, 35) };
    EntityLivingBase target;

    public EntityOctoMissileLauncher(World worldIn, EntityLivingBase throwerIn, float damage, EntityLivingBase target)
    {
	super(worldIn, throwerIn, damage);
	this.setNoGravity(true);
	this.target = target;
    }

    public EntityOctoMissileLauncher(World worldIn)
    {
	super(worldIn);
	this.setNoGravity(true);
    }

    public EntityOctoMissileLauncher(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
	this.setNoGravity(true);
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	for (MissileWrapper m : missiles)
	{
	    m.maybeSpawnMissile();
	}
	world.setEntityState(this, ModUtils.PARTICLE_BYTE);

	if (this.ticksExisted >= this.endTime)
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
	super.onHit(result);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == ModUtils.PARTICLE_BYTE)
	{
	    for (MissileWrapper m : missiles)
	    {
		m.maybeSpawnParticles();
	    }
	}
	super.handleStatusUpdate(id);
    }

    private class MissileWrapper
    {
	float side;
	float height;
	float ticks;
	EntityOctoMissileLauncher parent;

	public MissileWrapper(float side, float height, float ticks)
	{
	    this.side = side;
	    this.height = height;
	    this.ticks = ticks;
	    this.parent = EntityOctoMissileLauncher.this;
	}

	@SideOnly(Side.CLIENT)
	public void maybeSpawnParticles()
	{
	    if (this.parent.ticksExisted < ticks && parent.shootingEntity != null)
	    {
		Vec3d look = ModUtils.getVectorForRotation(0, parent.shootingEntity.rotationYaw);
		Vec3d right = look.rotateYaw((float) Math.PI * -0.5f).scale(side);
		Vec3d left = look.rotateYaw((float) Math.PI * 0.5f).scale(side);
		ParticleManager.spawnEffect(parent.world, parent.shootingEntity.getPositionVector().add(ModUtils.yVec(height)).add(left), ModColors.YELLOW);
		ParticleManager.spawnEffect(parent.world, parent.shootingEntity.getPositionVector().add(ModUtils.yVec(height)).add(right), ModColors.YELLOW);
	    }
	}

	public void maybeSpawnMissile()
	{
	    if (this.parent.ticksExisted == ticks && parent.target != null && parent.shootingEntity != null && parent.shootingEntity instanceof EntityLeveledMob)
	    {
		new ActionGoldenMissles(side, height).performAction((EntityLeveledMob) parent.shootingEntity, parent.target);
	    }
	}
    }
}
