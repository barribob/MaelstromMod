package com.barribob.MaelstromMod.entity.projectile;

import java.util.List;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * The bullet from the boomstick
 *
 */
public class ProjectileBullet extends Projectile
{
    private float damage;
    private int knockbackStrength;
    private static final int PARTICLE_AMOUNT = 3;

    public ProjectileBullet(World worldIn, EntityLivingBase throwerIn, float damage)
    {
	super(worldIn, throwerIn);
	this.damage = damage;
	this.setNoGravity(true);
    }

    public ProjectileBullet(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileBullet(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }
    
    /**
     * Set knockback strength for enchantments
     * @param factor
     */
    public void setKnockback(int factor)
    {
	this.knockbackStrength = factor;
    }
    
    @Override
    protected void onHit(RayTraceResult result)
    {	
	if (result.entityHit != null && this.shootingEntity != null)
	{
	    result.entityHit.hurtResistantTime = 0;
	    result.entityHit.attackEntityFrom(ModDamageSource.causeMalestromThrownDamage(this, this.shootingEntity), this.damage);
	
	    // Factor in knockback strength
	    if (this.knockbackStrength > 0)
            {
                float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                if (f1 > 0.0F)
                {
                    result.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)f1, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)f1);
                }
            }
	    
	    // Factor in fire
	    if (this.isBurning() && !(result.entityHit instanceof EntityEnderman))
            {
		result.entityHit.setFire(5);
            }
	}
	
	super.onHit(result);
    }
}
