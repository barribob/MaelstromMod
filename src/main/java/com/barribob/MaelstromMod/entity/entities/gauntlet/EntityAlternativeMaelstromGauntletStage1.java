package com.barribob.MaelstromMod.entity.entities.gauntlet;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileCrimsonWanderer;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMegaFireball;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class EntityAlternativeMaelstromGauntletStage1 extends EntityAbstractMaelstromGauntlet {
    private final IGauntletAction summonAttack = new SummonMobsAction(this::spawnMob, this, fist);
    private final double fireballHealth = getMobConfig().getDouble("use_fireball_at_health");
    private final double lazerHealth = getMobConfig().getDouble("use_lazer_at_health");
    private final double spawnHealth = getMobConfig().getDouble("use_spawning_at_health");
    private final double maxLaserDistance = getMobConfig().getDouble("max_laser_distance");
    private final double maxFireballDistance = getMobConfig().getDouble("max_fireball_distance");

    List<IGauntletAction> attacks;

    public EntityAlternativeMaelstromGauntletStage1(World worldIn) {
        super(worldIn);
        Supplier<Vec3d> position = () -> getAttackTarget() == null ? null : getAttackTarget().getPositionVector();
        IGauntletAction punchAttack = new PunchAction("gauntlet.punch", position, () -> {
        }, this, fist);
        IGauntletAction laserAttack = new LaserAction(this, stopLazerByte, (vec3d) -> {
        });
        IGauntletAction fireballAttack = new FireballThrowAction<>((target) -> target.getPositionEyes(1), this::generateFireball, this);
        attacks = new ArrayList<>(Arrays.asList(punchAttack, laserAttack, summonAttack, fireballAttack));
    }

    private void spawnMob() {
        if(!trySpawnMob(true)) trySpawnMob(false);
    }

    private boolean trySpawnMob(boolean findGround) {
        EntityLeveledMob mob = ModUtils.spawnMob(world, this.getPosition(), this.getLevel(), getMobConfig().getConfig(findGround ? "summoning_algorithm" : "aerial_summoning_algorithm"), findGround);
        return mob != null;
    }

    private Projectile generateFireball() {
        ProjectileMegaFireball fireball = new ProjectileMegaFireball(world, this, this.getAttack() * getConfigFloat("fireball_damage"), null, false);
        fireball.setTravelRange((float) maxFireballDistance);
        return fireball;
    }

    @Override
    protected IGauntletAction getNextAttack(EntityLivingBase target, float distanceSq, IGauntletAction previousAction) {
        int numMinions = (int) ModUtils.getEntitiesInBox(this, getEntityBoundingBox().grow(50))
                .stream().filter(EntityMaelstromMob::isMaelstromMob).count();

        double defendWeight = previousAction == this.summonAttack || numMinions > 3 || this.getHealth() > spawnHealth ? 0 : 0.8;
        double fireballWeight = distanceSq < Math.pow(maxFireballDistance, 2) && this.getHealth() < fireballHealth ? 1 : 0;
        double laserWeight = distanceSq < Math.pow(maxLaserDistance, 2) && this.getHealth() < lazerHealth ? 1 : 0;
        double punchWeight = ModUtils.canEntityBeSeen(this, target) ? Math.sqrt(distanceSq) / 25 : 3;

        double[] weights = {punchWeight, laserWeight, defendWeight, fireballWeight};
        return ModRandom.choice(attacks, rand, weights).next();
    }

    @Override
    public void onDeath(DamageSource cause) {
        EntityAlternativeMaelstromGauntletStage2 secondStage = new EntityAlternativeMaelstromGauntletStage2(world);
        secondStage.copyLocationAndAnglesFrom(this);
        secondStage.setRotationYawHead(this.rotationYawHead);
        if(!world.isRemote) {
            secondStage.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(this)), null);
            secondStage.setLevel(getLevel());
            secondStage.setElement(getElement());
            secondStage.setAttackTarget(getAttackTarget());
            world.spawnEntity(secondStage);
            for(int i = 0; i < 30; i++) {
                summonCrimsonWanderer();
            }
        }
        this.setPosition(0, 0, 0);
        super.onDeath(cause);
    }

    private void summonCrimsonWanderer() {
        ProjectileCrimsonWanderer shrapnel = new ProjectileCrimsonWanderer(world, this, getAttack() * 0.5f);
        Vec3d shrapnelPos = this.getPositionVector()
                .add(ModRandom.randVec().normalize().scale(4));
        ModUtils.setEntityPosition(shrapnel, shrapnelPos);
        shrapnel.setNoGravity(false);
        shrapnel.setTravelRange(30);
        world.spawnEntity(shrapnel);
        Vec3d vel = ModUtils.direction(getPositionEyes(1), shrapnelPos).scale(0.1);
        ModUtils.setEntityVelocity(shrapnel, vel);
    }
}
