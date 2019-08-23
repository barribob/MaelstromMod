package com.barribob.MaelstromMod.entity.projectile;

import java.util.List;

import com.barribob.MaelstromMod.util.ModRandom;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * 
 * Projectile from the quake staff
 *
 */
public class ProjectileQuake extends ProjectileGun
{
    private static final int PARTICLE_AMOUNT = 10;
    protected static final float AREA_FACTOR = 0.5f;

    public ProjectileQuake(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage, stack);
	this.setNoGravity(true);
    }

    public ProjectileQuake(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileQuake(World worldIn, double x, double y, double z)
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
	IBlockState block = world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ));
	if (block.isFullCube())
	{
	    for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	    {
		world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + ModRandom.getFloat(1.0f), this.posY + ModRandom.getFloat(0.5f) + 0.5f,
			this.posZ + ModRandom.getFloat(1.0f), ModRandom.getFloat(1.0F), ModRandom.getFloat(1.0F), ModRandom.getFloat(1.0F), Block.getStateId(block));
	    }
	}
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();

	// Keeps the projectile on the surface of the ground
	int updates = 5;
	for (int i = 0; i < updates; i++)
	{
	    if (!world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ)).isFullCube())
	    {
		this.setPosition(this.posX, this.posY - 0.25f, this.posZ);
	    }
	    else if (world.getBlockState(new BlockPos(this.posX, this.posY + 1, this.posZ)).isFullCube())
	    {
		this.setPosition(this.posX, this.posY + 0.25f, this.posZ);
	    }
	}

	// Play the block break sound
	BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
	IBlockState state = world.getBlockState(pos);
	if (state.isFullCube())
	{
	    world.playSound(this.posX, this.posY, this.posZ, state.getBlock().getSoundType(state, world, pos, this).getStepSound(), SoundCategory.BLOCKS, 1.0F, 1.0F, false);
	}

	/*
	 * Find all entities in a certain area and deal damage to them
	 */
	List list = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(AREA_FACTOR).expand(0, 0.25f, 0));
	if (list != null)
	{
	    for (Object entity : list)
	    {
		if (entity instanceof EntityLivingBase && this.shootingEntity != null && entity != this.shootingEntity)
		{
		    int burnTime = this.isBurning() ? 5 : 0;
		    ((EntityLivingBase) entity).setFire(burnTime);

		    ((EntityLivingBase) entity).attackEntityFrom(DamageSource.causeThrownDamage(this, this.shootingEntity), this.getGunDamage(((EntityLivingBase) entity)));

		    // Apply knockback enchantment
		    if (this.getKnockback() > 0)
		    {
			float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

			if (f1 > 0.0F)
			{
			    ((EntityLivingBase) entity).addVelocity(this.motionX * this.getKnockback() * 0.6000000238418579D / f1, 0.1D,
				    this.motionZ * this.getKnockback() * 0.6000000238418579D / f1);
			}
		    }
		}
	    }
	}
	
	// If the projectile hits water and looses all of its velocity, despawn
	if(!world.isRemote && Math.abs(this.motionX) + Math.abs(this.motionZ) < 0.01f)
	{
	    this.setDead();
	}
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
    }
}
