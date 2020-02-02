package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModRandom;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ProjectileExplosiveDrill extends ProjectileGun
{
    private static final int PARTICLE_AMOUNT = 15;

    public ProjectileExplosiveDrill(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage, stack);
	this.setNoGravity(true);
    }

    public ProjectileExplosiveDrill(World worldIn)
    {
	super(worldIn);
	this.setNoGravity(true);
    }

    public ProjectileExplosiveDrill(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
	this.setNoGravity(true);
    }

    @Override
    protected void spawnParticles()
    {
	for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	{
	    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + ModRandom.getFloat(0.5f), this.posY + ModRandom.getFloat(0.5f),
		    this.posZ + ModRandom.getFloat(0.5f), 0, 0, 0);
	}
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	if (!world.isRemote)
	{
	    world.createExplosion(this.shootingEntity, this.posX, this.posY, this.posZ, 3, true);
	}
    }
}
