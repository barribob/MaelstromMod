package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ProjectilePiercingBullet extends ProjectileBullet
{
    public ProjectilePiercingBullet(World worldIn, EntityLivingBase throwerIn, float damage, ItemStack stack)
    {
	super(worldIn, throwerIn, damage, stack);
    }

    public ProjectilePiercingBullet(World worldIn)
    {
	super(worldIn);
    }

    public ProjectilePiercingBullet(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }
    
    @Override
    protected void onHit(RayTraceResult result)
    {
	ModUtils.handleBulletImpact(result.entityHit, this, this.getGunDamage(result.entityHit),
		ModDamageSource.causeElementalThrownDamage(this, this.shootingEntity, this.getElement()), this.getKnockback());

	if (result.entityHit == null)
	{
	    super.onHit(result);
	}
    }
}
