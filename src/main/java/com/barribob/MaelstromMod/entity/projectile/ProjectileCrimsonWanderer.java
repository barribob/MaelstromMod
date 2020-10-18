package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;

public class ProjectileCrimsonWanderer extends Projectile {
    private static final int AGE = 20 * 4;
    private EntityLivingBase target;

    public ProjectileCrimsonWanderer(World worldIn, EntityLivingBase throwerIn, float baseDamage) {
        super(worldIn, throwerIn, baseDamage);
    }

    public ProjectileCrimsonWanderer(World worldIn) {
        super(worldIn);
    }

    public ProjectileCrimsonWanderer(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    public void onUpdate() {
        Vec3d prevVel = ModUtils.getEntityVelocity(this);
        super.onUpdate();
        ModUtils.setEntityVelocity(this, prevVel);

        if(!world.isRemote) {
            if((target == null || target.isDead) && this.ticksExisted % 20 == 0) {
                Optional<EntityLivingBase> optionalTarget = ModUtils.getEntitiesInBox(this, ModUtils.makeBox(this.getPositionVector(), this.getPositionVector()).grow(20))
                        .stream().filter(EntityMaelstromMob.maelstromTargetFilter).findAny();
                optionalTarget.ifPresent(entityLivingBase -> target = entityLivingBase);
            }

            if(target != null && this.ticksExisted % 20 == 0) {
                ModUtils.homeToPosition(this, 0.15, target.getPositionVector());
            }
            ModUtils.avoidOtherEntities(this, 0.03, 3, e -> e instanceof ProjectileCrimsonWanderer || e == this.shootingEntity);
        }

        if(!this.world.isRemote && this.ticksExisted > AGE) {

            if(shootingEntity != null) {
                onImpact();
            }
            this.setDead();
        }
    }

    private void onImpact() {
        DamageSource source = ModDamageSource.builder()
                .type(ModDamageSource.EXPLOSION)
                .directEntity(this)
                .indirectEntity(shootingEntity)
                .element(getElement())
                .stoppedByArmorNotShields().build();

        ModUtils.handleAreaImpact(2, e -> getDamage(), this.shootingEntity, getPositionVector(), source);
        world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 0.5f, 1.0f + ModRandom.getFloat(0.2f));
        boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, this);
        world.newExplosion(shootingEntity, posX, posY, posZ, 2, false, flag);
    }

    @Override
    protected void onHit(@Nullable RayTraceResult result) {
        if(!world.isRemote && result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
            onImpact();
            super.onHit(result);
        }
    }

    @Override
    protected void spawnParticles() {
        for (int i = 0; i < 4; i++) {
            float colorAge = ModUtils.clamp(Math.pow(ticksExisted / (float)AGE, 2), 0.1, 1);

            ParticleManager.spawnSplit(world,
                    getPositionVector().add(ModRandom.randVec().scale(0.25)),
                    new Vec3d(1, colorAge, colorAge), Vec3d.ZERO);
        }
    }
}
