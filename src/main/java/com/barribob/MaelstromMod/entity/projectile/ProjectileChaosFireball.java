package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileChaosFireball extends ProjectileGun {
    private static final int IMPACT_PARTICLE_AMOUNT = 20;
    private static final int EXPOSION_AREA_FACTOR = 2;
    public static final Vec3d FIREBALL_COLOR = new Vec3d(1.0, 0.6, 0.5);

    public ProjectileChaosFireball(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack) {
        super(worldIn, throwerIn, baseDamage, stack);
        this.setNoGravity(true);
    }

    public ProjectileChaosFireball(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
    }

    public ProjectileChaosFireball(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.setNoGravity(true);
    }

    @Override
    public void onUpdate() {
        if ((this.ticksExisted / 5.0f) % 5 == 0) {
            this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.2f, ModRandom.getFloat(0.2f) + 1.0f);
        }

        if (this.ticksExisted == 2) {
            world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        }

        Vec3d vel = new Vec3d(this.motionX, this.motionY, this.motionZ);

        super.onUpdate();

        // Maintain the velocity the entity has
        ModUtils.setEntityVelocity(this, vel);
    }

    @Override
    protected void spawnParticles() {
        ParticleManager.spawnSmoke2(world, this.getPositionVector().add(ModUtils.yVec(0.3f)), ModColors.FADED_RED, ModUtils.yVec(0.1));
    }

    @Override
    protected void spawnImpactParticles() {
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 0, 0, 0);
        for (int i = 0; i < IMPACT_PARTICLE_AMOUNT; i++) {
            ParticleManager.spawnEffect(world, getPositionVector().add(ModRandom.randVec().scale(EXPOSION_AREA_FACTOR * 2)), ModColors.RED);
            ParticleManager.spawnFluff(world, getPositionVector().add(ModRandom.randVec().scale(EXPOSION_AREA_FACTOR * 2)), FIREBALL_COLOR, ModRandom.randVec().scale(0.1));
        }
    }

    @Override
    protected void onHit(RayTraceResult result) {
        float knockbackFactor = 1.1f + this.getKnockback() * 0.4f;
        int fireFactor = this.isBurning() ? 8 : 3;
        ModUtils.handleAreaImpact(EXPOSION_AREA_FACTOR, (e) -> this.getGunDamage((e)), this.shootingEntity, this.getPositionVector(),
                DamageSource.causeExplosionDamage(this.shootingEntity), knockbackFactor, fireFactor);
        this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
        super.onHit(result);
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == ModUtils.PARTICLE_BYTE) {
            ParticleManager.spawnSwirl2(world, getPositionVector(), ModColors.RED, Vec3d.ZERO);
        }
        super.handleStatusUpdate(id);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        this.onHit(null);
        return super.attackEntityFrom(source, amount);
    }
}
