package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Function;

public class ProjectileBlackHoleFireball extends ProjectileAbstractMegaFireball {
    private static final int radius = 7;

    public ProjectileBlackHoleFireball(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack, boolean canTakeDamage) {
        super(worldIn, throwerIn, baseDamage, stack, canTakeDamage);
    }

    public ProjectileBlackHoleFireball(World worldIn) {
        super(worldIn);
    }

    public ProjectileBlackHoleFireball(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void spawnParticles() {
        for(int i = 0; i < 3; i++) {
            Vec3d randOff = ModRandom.randVec().normalize().scale(2);
            Vec3d velOff = ModUtils.getEntityVelocity(this).scale(5);
            Vec3d off = randOff.add(velOff);
            Vec3d vel = randOff.scale(-0.1);
            if(rand.nextFloat() < 0.5) {
                ParticleManager.spawnFluff(world, off.add(getPositionVector()), Vec3d.ZERO, vel);
            }
            else {
                ParticleManager.spawnSwirl2(world, off.add(getPositionVector()), ModColors.RED, vel);
            }
        }
    }

    @Override
    protected void spawnImpactParticles() {
        for(int i = 0; i < 30; i++) {
            Vec3d pos = getPositionVector().add(ModRandom.randVec().scale(radius));
            ParticleManager.spawnColoredExplosion(world, pos, Vec3d.ZERO);
        }

        for(int i = 0; i < 30; i++) {
            Vec3d pos = getPositionVector().add(ModRandom.randVec().scale(radius * 2));
            ParticleManager.spawnSwirl2(world, pos, ModColors.RED, ModUtils.direction(getPositionVector(), pos).scale(0.5));
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        int fireFactor = this.isBurning() ? 5 : 0;

        DamageSource source = ModDamageSource.builder()
                .type(ModDamageSource.EXPLOSION)
                .directEntity(this)
                .indirectEntity(shootingEntity)
                .element(this.getElement())
                .stoppedByArmorNotShields().build();

        Function<Entity, Float> damageAndEffect = (entity) -> {
            if (entity instanceof EntityLivingBase) {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 0));
            }
            return getGunDamage(this);
        };

        ModUtils.handleAreaImpact(radius, damageAndEffect, shootingEntity, getPositionVector(), source, 0, fireFactor);
        boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, shootingEntity);
        if(!world.isRemote) {
            this.world.newExplosion(shootingEntity, posX, posY, posZ, 4, false, flag);

            for (int i = 0; i < 10; i++) {
                ProjectileCrimsonWanderer shrapnel = new ProjectileCrimsonWanderer(world, shootingEntity, this.getDamage() * 0.25f);
                ModUtils.setEntityPosition(shrapnel, this.getPositionVector().add(ModRandom.randVec().scale(radius)));
                shrapnel.setNoGravity(false);
                shrapnel.setTravelRange(50);
                world.spawnEntity(shrapnel);
                ModUtils.setEntityVelocity(shrapnel, ModUtils.direction(getPositionVector(), shrapnel.getPositionVector()).scale(0.35));
            }
        }
    }

    @Override
    public void onUpdate() {
        if (this.ticksExisted % 3 == 0) {
            this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.2f, ModRandom.getFloat(0.2f) + 1.0f);
        }

        int attractionRadius = 20;
        AxisAlignedBB box = ModUtils.makeBox(getPositionVector(), getPositionVector()).grow(attractionRadius);
        List<Entity> list = world.getEntitiesInAABBexcluding(this, box, EntityMaelstromMob.maelstromTargetFilter);
        for (Entity entity : list) {
            double distance = entity.getPositionVector().distanceTo(getPositionVector());
            if (distance < attractionRadius) {
                Vec3d direction = getPositionVector().subtract(entity.getPositionVector()).normalize();
                double gravityFactor = entity.onGround ? 0.07 : 0.18;
                Vec3d vel = direction.scale((attractionRadius - distance) / attractionRadius).scale(gravityFactor);
                ModUtils.addEntityVelocity(entity, vel);
            }
        }

        super.onUpdate();
    }
}
