package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Fireball for the Gauntlet. Main things are that it can be collided with (shot down) and it spawns more projectiles on impact
 * 
 * @author Barribob
 *
 */
public class ProjectileMegaFireball extends ProjectileGun {
    private static final int PARTICLE_AMOUNT = 15;
    private static final int IMPACT_PARTICLE_AMOUNT = 30;
    private static final int EXPOSION_AREA_FACTOR = 4;

    public ProjectileMegaFireball(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack) {
	super(worldIn, throwerIn, baseDamage, stack);
	this.setNoGravity(true);
	this.setSize(1, 1);
    }

    public ProjectileMegaFireball(World worldIn) {
	super(worldIn);
	this.setNoGravity(true);
	this.setSize(1, 1);
    }

    public ProjectileMegaFireball(World worldIn, double x, double y, double z) {
	super(worldIn, x, y, z);
	this.setNoGravity(true);
	this.setSize(1, 1);
    }

    @Override
    protected void spawnParticles() {
	for (int i = 0; i < PARTICLE_AMOUNT; i++) {
	    Vec3d origin = this.getPositionVector().add(ModUtils.getAxisOffset(ModUtils.getEntityVelocity(this).normalize(), new Vec3d(1, 0, 0)));
	    Vec3d smokePos = origin.add(ModRandom.randVec());
	    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, smokePos.x, smokePos.y, smokePos.z, 0, 0, 0);
	    ParticleManager.spawnEffect(world, origin.add(ModRandom.randVec()), ModColors.FIREBALL_ORANGE);
	}
    }

    @Override
    protected void spawnImpactParticles() {
	for (int i = 0; i < IMPACT_PARTICLE_AMOUNT; i++) {
	    Vec3d pos = this.getPositionVector().add(ModRandom.randVec().scale(EXPOSION_AREA_FACTOR));
	    Vec3d vel = pos.subtract(this.getPositionVector()).normalize().scale(world.rand.nextFloat() * 0.3f);
	    this.world.spawnParticle(EnumParticleTypes.FLAME, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);
	}
    }

    @Override
    protected void onHit(RayTraceResult result) {

	boolean isShootingEntity = result != null && result.entityHit != null && result.entityHit == this.shootingEntity;
	boolean isPartOfShootingEntity = result != null && result.entityHit != null && (result.entityHit instanceof MultiPartEntityPart && ((MultiPartEntityPart) result.entityHit).parent == this.shootingEntity);
	if (isShootingEntity || isPartOfShootingEntity || world.isRemote) {
	    return;
	}

	int fireFactor = this.isBurning() ? 10 : 5;
	ModUtils.handleAreaImpact(7, (e) -> this.getGunDamage((e)), this.shootingEntity, this.getPositionVector(), DamageSource.causeExplosionDamage(this.shootingEntity), 0, fireFactor);
	boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.shootingEntity);
	super.onHit(result);
	this.world.newExplosion((Entity) null, this.posX, this.posY, this.posZ, 3, true, flag);
	for (int i = 0; i < 10; i++) {
	    Vec3d vel = ModRandom.randVec().normalize().scale(0.5f).add(ModUtils.yVec(1));
	    ProjectileFireball shrapenel = new ProjectileFireball(world, shootingEntity, this.getDamage() * 0.5f, null);
	    ModUtils.setEntityPosition(shrapenel, this.getPositionVector().add(ModUtils.yVec(1)).add(ModRandom.randVec()));
	    shrapenel.setNoGravity(false);
	    shrapenel.setTravelRange(50);
	    world.spawnEntity(shrapenel);
	    ModUtils.setEntityVelocity(shrapenel, vel);
	}
    }

    @Override
    public void onUpdate() {
	if ((this.ticksExisted / 5.0f) % 5 == 0) {
	    this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.2f, ModRandom.getFloat(0.2f) + 1.0f);
	}

	Vec3d vel = new Vec3d(this.motionX, this.motionY, this.motionZ);

	super.onUpdate();

	// Maintain the velocity the entity has
	ModUtils.setEntityVelocity(this, vel);

	if (this.shootingEntity != null && getDistanceTraveled() > this.travelRange) {
	    this.world.setEntityState(this, IMPACT_PARTICLE_BYTE);
	    this.onHit(null);
	}
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
	if (!this.isDead) {
	    this.onHit(null);
	}
	return super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean canBeCollidedWith() {
	return true;
    }

    @Override
    public int getBrightnessForRender() {
	return 200;
    }
}
