package com.barribob.MaelstromMod.entity.entities.gauntlet;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.OptionalDouble;

public class EntityCrimsonCrystal extends Entity {
    protected static final DataParameter<Float> CLOSEST_TARGET_DISTANCE =
            EntityDataManager.createKey(EntityLeveledMob.class, DataSerializers.FLOAT);
    private EntityLeveledMob shootingEntity = null;
    public static final float explosionDistance = (float) Main.mobsConfig.getDouble("alternative_maelstrom_gauntlet_stage_2.crystal_explosion_radius");
    public static float visualActivationDistance = explosionDistance * 4;
    public static final double VisualActivationDistanceSq = Math.pow(visualActivationDistance, 2);
    private static final double explosionRadiusSq = Math.pow(explosionDistance, 2);
    private static final float crystalLifespan = (float) Main.mobsConfig.getDouble("alternative_maelstrom_gauntlet_stage_2.crystal_lifespan");

    public EntityCrimsonCrystal(World world) {
        super(world);
    }

    public EntityCrimsonCrystal(World world, EntityLeveledMob shootingEntity) {
        super(world);
        this.shootingEntity = shootingEntity;
        this.setSize(0.25f, 0.25f);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!world.isRemote) {

            if (shootingEntity == null) {
                this.setDead();
                return;
            }

            Vec3d pos = this.getPositionVector();
            OptionalDouble optionalDistance = ModUtils.getEntitiesInBox(this, ModUtils.makeBox(pos, pos).grow(20))
                    .stream()
                    .filter(EntityMaelstromMob.maelstromTargetFilter)
                    .mapToDouble((e) -> e.getDistanceSq(this))
                    .min();

            if (optionalDistance.isPresent()) {
                dataManager.set(CLOSEST_TARGET_DISTANCE, ((float) optionalDistance.getAsDouble()));

                if (optionalDistance.getAsDouble() < explosionRadiusSq) {
                    explodeAndDespawn();
                }
            } else {
                dataManager.set(CLOSEST_TARGET_DISTANCE, Float.POSITIVE_INFINITY);
            }

            boolean randomExplodeCondition = ticksExisted > crystalLifespan && rand.nextInt(50) == 0;
            if (randomExplodeCondition && !isDead) explodeAndDespawn();

        } else if (getTargetDistanceSq() < VisualActivationDistanceSq) {
            double distance = Math.sqrt(getTargetDistanceSq());
            double distanceToExplosion = (visualActivationDistance - distance) /
                    (visualActivationDistance - explosionDistance);
            int numParticles = (int) (distanceToExplosion * VisualActivationDistanceSq * 0.02);
            for (int i = 0; i < numParticles; i++) {
                Vec3d vec = ModRandom.randVec().normalize().scale(explosionDistance);
                ParticleManager.spawnEffect(world, getPositionVector().add(vec), getParticleColor());
            }
        }
    }

    public void explodeAndDespawn() {
        DamageSource source = ModDamageSource.builder()
                .type(ModDamageSource.EXPLOSION)
                .directEntity(this)
                .indirectEntity(shootingEntity)
                .element(shootingEntity.getElement())
                .stoppedByArmorNotShields().build();

        ModUtils.handleAreaImpact(explosionDistance, e -> shootingEntity.getAttack(), this.shootingEntity, getPositionVector(), source, 1, 0, false);
        world.newExplosion(shootingEntity, posX, posY, posZ, 1, false, false);
        playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f + ModRandom.getFloat(0.2f));
        world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        this.setDead();
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == ModUtils.PARTICLE_BYTE) {
            for (int i = 0; i < 20; i++) {
                Vec3d randPos = radialRandVec();
                ParticleManager.spawnColoredExplosion(world, randPos, ModColors.RED);
            }

            for (int i = 0; i < 20; i++) {
                Vec3d randPos = radialRandVec();
                Vec3d outVelocity = ModUtils.direction(getPositionVector(), randPos).scale(0.2f);
                ParticleManager.spawnFluff(world, randPos, Vec3d.ZERO, outVelocity);
            }
        }
        super.handleStatusUpdate(id);
    }

    public Vec3d radialRandVec() {
        return getPositionVector()
                .add(ModRandom.randVec().normalize()
                        .scale(rand.nextDouble() * explosionDistance));
    }

    private float getTargetDistanceSq() {
        return dataManager.get(CLOSEST_TARGET_DISTANCE);
    }

    public Vec3d getParticleColor() {
        double distance = Math.sqrt(Math.min(EntityCrimsonCrystal.VisualActivationDistanceSq, getTargetDistanceSq()));
        float distanceToExplosion = (float) ((EntityCrimsonCrystal.visualActivationDistance - distance) /
                (EntityCrimsonCrystal.visualActivationDistance - EntityCrimsonCrystal.explosionDistance));
        return new Vec3d(1.0f, 1 - distanceToExplosion, 1 - distanceToExplosion);
    }

    @Override
    protected void entityInit() {
        dataManager.register(CLOSEST_TARGET_DISTANCE, Float.POSITIVE_INFINITY);
    }

    @Override
    protected void readEntityFromNBT(@Nonnull NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {

    }
}
