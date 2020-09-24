package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class ProjectileHomingFlame extends Projectile {
    private static final int AGE = 20 * 8;
    public ProjectileHomingFlame(World worldIn, EntityLivingBase throwerIn, float baseDamage) {
        super(worldIn, throwerIn, baseDamage);
    }

    public ProjectileHomingFlame(World worldIn) {
        super(worldIn);
    }

    public ProjectileHomingFlame(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    public void onUpdate() {
        Vec3d prevVel = ModUtils.getEntityVelocity(this);
        super.onUpdate();
        ModUtils.setEntityVelocity(this, prevVel);

        double speed = 0.04;

        if (!this.world.isRemote &&
                this.shootingEntity != null &&
                this.shootingEntity instanceof EntityLiving &&
                ((EntityLiving) this.shootingEntity).getAttackTarget() != null) {
            Vec3d target = ((EntityLiving) this.shootingEntity).getAttackTarget().getPositionEyes(1);
            Vec3d velocityChange = getVelocityToTarget(target).scale(speed);
            ModUtils.addEntityVelocity(this, velocityChange);
        }

        if(!world.isRemote) {
            int detectionSize = 4;
            double boundingBoxEdgeLength = this.getEntityBoundingBox().getAverageEdgeLength() * 0.5;
            double distanceSq = Math.pow(detectionSize + boundingBoxEdgeLength, 2);

            BiFunction<Vec3d, Entity, Vec3d> accumulator = (vec, e) ->
                    vec.add(getPositionVector().subtract(e.getPositionVector()).normalize())
                            .scale((distanceSq - getDistanceSq(e)) / distanceSq);

            Vec3d avoid = world.getEntitiesInAABBexcluding(this,
                    getEntityBoundingBox().grow(detectionSize),
                    e -> e instanceof ProjectileHomingFlame).parallelStream()
                    .reduce(Vec3d.ZERO, accumulator, Vec3d::add)
                    .scale(speed);

            ModUtils.addEntityVelocity(this, avoid);
        }

        if(!this.world.isRemote && this.ticksExisted > AGE) {
            this.setDead();
        }

        if(this.shootingEntity != null && this.shootingEntity.isDead) {
            this.setDead();
        }

        if (!this.world.isRemote && this.ticksExisted % 3 == 0) {
            this.playSound(SoundEvents.BLOCK_STONE_BREAK, 0.2f, ModRandom.getFloat(0.2f) + 0.3f);
        }
    }

    public Vec3d getVelocityToTarget(Vec3d target) {
        Vec3d velocityDirection = ModUtils.getEntityVelocity(this).normalize();
        Vec3d desiredDirection = target.subtract(getPositionVector()).normalize();
        return desiredDirection.subtract(velocityDirection).normalize();
    }

    @Override
    protected void spawnParticles() {
        for (int i = 0; i < 4; i++) {
            float colorAge = ModUtils.clamp((AGE - ticksExisted) / (float)AGE, 0.1, 1);
            ParticleManager.spawnColoredFire(world, rand,
                    getPositionVector().add(ModRandom.randVec().scale(0.25)),
                    new Vec3d(0.8, 1.0, rand.nextFloat()).scale(colorAge));
        }
    }

    @Override
    protected void onHit(@Nullable RayTraceResult result) {
        if(result != null) {
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.PROJECTILE)
                    .directEntity(this)
                    .indirectEntity(shootingEntity)
                    .element(getElement())
                    .stoppedByArmorNotShields().build();

            ModUtils.handleBulletImpact(result.entityHit, this, this.getDamage(), source);

            playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.5f, 1.0f + ModRandom.getFloat(0.2f));
        }
        super.onHit(result);
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        if (!this.isDead && amount > 0) {
            this.setDead();
            this.onHit(null);
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return true;
    }

    @Override
    public int getBrightnessForRender() {
        return 200;
    }
}
