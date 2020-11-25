package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
            ModUtils.homeToPosition(this, speed, ((EntityLiving) this.shootingEntity).getAttackTarget().getPositionEyes(1));
        }

        if(!world.isRemote) {
            ModUtils.avoidOtherEntities(this, speed, 4, e -> e instanceof ProjectileHomingFlame);
        }

        if(!this.world.isRemote && this.ticksExisted > AGE) {
            onHit(null);
        }

        if(this.shootingEntity != null && this.shootingEntity.isDead) {
            this.setDead();
        }

        if (!this.world.isRemote && this.ticksExisted % 3 == 0) {
            this.playSound(SoundEvents.BLOCK_STONE_BREAK, 0.2f, ModRandom.getFloat(0.2f) + 0.3f);
        }
    }

    @Override
    protected void spawnImpactParticles() {
        ParticleManager.spawnColoredExplosion(world, getPositionVector(), Vec3d.ZERO);
        super.spawnImpactParticles();
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
        if(result != null && EntityMaelstromMob.isMaelstromMob(result.entityHit)) {
            return;
        }

        DamageSource source = ModDamageSource.builder()
                .type(ModDamageSource.PROJECTILE)
                .directEntity(this)
                .indirectEntity(shootingEntity)
                .element(getElement())
                .stoppedByArmorNotShields().build();

        ModUtils.handleAreaImpact(0.6f, (e) -> getDamage(), shootingEntity, getPositionVector(), source, 0, 0, false);
        playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0f, 1.0f + ModRandom.getFloat(0.2f));
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
        float colorAge = ModUtils.clamp((AGE - ticksExisted) / (float)AGE, 0.0, 1) * 255;
        return (int) colorAge;
    }
}
