package com.barribob.MaelstromMod.entity.projectile;

import java.util.List;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent;

public class EntityGoldenRune extends Projectile
{
    private static final byte PARTICLE_BYTE = 3;
    private int tickDelay = 30;
    private int blastRadius = 2;

    public EntityGoldenRune(World worldIn, EntityLivingBase throwerIn, float damage)
    {
	super(worldIn, throwerIn, damage);
	this.setNoGravity(true);
    }

    public EntityGoldenRune(World worldIn)
    {
	super(worldIn);
	this.setNoGravity(true);
    }

    public EntityGoldenRune(World worldIn, double x, double y, double z)
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
		if (this.shootingEntity != entity && this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase && !(entity instanceof EntityMaelstromMob))
		{
		    entity.attackEntityFrom(ModDamageSource.causeMaelstromExplosionDamage((EntityLivingBase) this.shootingEntity), this.getDamage());
		    entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80, 0));
		}
	    }
	}
	this.playSound(SoundEvents.ENTITY_ILLAGER_CAST_SPELL, 1.0F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
	super.onHit(result);
    }

    @Override
    protected void spawnImpactParticles()
    {
	ModUtils.performNTimes(10, (i) -> {
	    ParticleManager.spawnParticlesInCircle(blastRadius, 30,
		    (offset) -> ParticleManager.spawnEffect(world, ModUtils.entityPos(this).add(new Vec3d(offset.x, i * 0.5, offset.y)), new Vec3d(0.8, 0.8, 0.4)));
	});
    }

    @Override
    protected void spawnParticles()
    {
	ParticleManager.spawnParticlesInCircle(this.blastRadius, 30,
		(offset) -> ParticleManager.spawnEffect(world, ModUtils.entityPos(this).add(new Vec3d(offset.x, 0.5f, offset.y)), new Vec3d(0.8, 0.8, 0.4)));
    }
}
