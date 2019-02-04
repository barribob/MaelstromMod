package com.barribob.MaelstromMod.entity.projectile;

import java.util.List;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * The simple attacks that the beast outputs during its ranged mode
 *
 */
public class ProjectileBeastAttack extends Projectile
{
    private static final int PARTICLE_AMOUNT = 3;
    private static final int IMPACT_PARTICLE_AMOUNT = 20;

    public ProjectileBeastAttack(World worldIn, EntityLivingBase throwerIn, float damage)
    {
	super(worldIn, throwerIn, damage);
    }

    public ProjectileBeastAttack(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileBeastAttack(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }

    /**
     * Called every update to spawn particles
     * 
     * @param world
     */
    protected void spawnParticles()
    {
	for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	{
	    ParticleManager.spawnMaelstromSmoke(world, rand,
		    new Vec3d(this.posX + ModRandom.getFloat(0.5f), this.posY + ModRandom.getFloat(0.5f), this.posZ + ModRandom.getFloat(0.5f)), true);
	}
    }
    
    @Override
    protected void spawnImpactParticles()
    {
	for (int i = 0; i < this.IMPACT_PARTICLE_AMOUNT; i++)
	{
	    Vec3d vec1 = new Vec3d(this.posX + ModRandom.getFloat(1), this.posY + ModRandom.getFloat(1), this.posZ + ModRandom.getFloat(1));
	    ParticleManager.spawnMaelstromSmoke(world, rand, vec1, true);
	}
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	if (result.entityHit instanceof EntityMaelstromMob)
	{
	    return;
	}

	if (result.entityHit != null && this.shootingEntity != null)
	{
	    result.entityHit.hurtResistantTime = 0;
	    result.entityHit.attackEntityFrom(ModDamageSource.causeMalestromThrownDamage(this, this.shootingEntity), this.getDamage());
	}

	super.onHit(result);
    }
}
