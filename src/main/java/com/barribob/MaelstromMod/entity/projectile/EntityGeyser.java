package com.barribob.MaelstromMod.entity.projectile;

import java.util.List;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent;

public class EntityGeyser extends Projectile
{
    private int tickDelay = 30;
    private int blastRadius = 3;

    public EntityGeyser(World worldIn, EntityLivingBase throwerIn, float damage)
    {
	super(worldIn, throwerIn, damage);
	this.setNoGravity(true);
    }

    public EntityGeyser(World worldIn)
    {
	super(worldIn);
	this.setNoGravity(true);
    }

    public EntityGeyser(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
	this.setNoGravity(true);
    }

    /**
     * Set the delay before "going off" in ticks
     */
    public void setDelay(int delay)
    {
	this.tickDelay = delay;
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();

	if (this.ticksExisted >= this.tickDelay)
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
	List<EntityLivingBase> entities = ModUtils.getEntitiesInBox(this, this.getEntityBoundingBox().grow(this.blastRadius));
	for (EntityLivingBase entity : entities)
	{
	    if (this.getDistanceSq(entity) < Math.pow(blastRadius, 2))
	    {
		if (this.shootingEntity != entity && this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase)
		{
		    entity.attackEntityFrom(DamageSource.causeExplosionDamage((EntityLivingBase)this.shootingEntity), this.getDamage());
		}
	    }
	}
	this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
	this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
	super.onHit(result);
    }

    @Override
    protected void spawnImpactParticles()
    {
	ModUtils.performNTimes(1000, (i) -> {
	    Vec3d offset = new Vec3d(ModRandom.getFloat(1), 0, ModRandom.getFloat(1)).normalize().scale(ModRandom.getFloat(blastRadius));
	    ParticleManager.spawnFirework(world, ModUtils.entityPos(this).add(offset), ModColors.CLIFF_STONE, ModUtils.yVec(rand.nextFloat() + 1).scale(0.5));
	});
    }

    @Override
    protected void spawnParticles()
    {
	ParticleManager.spawnParticlesInCircle(this.blastRadius, 30,
		(offset) -> ParticleManager.spawnEffect(world, ModUtils.entityPos(this).add(new Vec3d(offset.x, 0.5f, offset.y)), ModColors.CLIFF_STONE));
    }
}
