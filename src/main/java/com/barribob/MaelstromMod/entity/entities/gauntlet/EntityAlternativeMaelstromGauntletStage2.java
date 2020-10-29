package com.barribob.MaelstromMod.entity.entities.gauntlet;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileCrimsonWanderer;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMegaFireball;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class EntityAlternativeMaelstromGauntletStage2 extends EntityAbstractMaelstromGauntlet {
    private final IGauntletAction summonAttack;
    private final List<IGauntletAction> attacks;
    private final double maxLaserDistance = getMobConfig().getDouble("max_laser_distance");
    private final double maxFireballDistance = getMobConfig().getDouble("max_fireball_distance");

    public EntityAlternativeMaelstromGauntletStage2(World worldIn) {
        super(worldIn);
        Supplier<Vec3d> position = () -> getAttackTarget() == null ? null : getAttackTarget().getPositionVector();
        IGauntletAction swirlPunchAttack = new PunchAction("gauntlet.swirl_punch", position, this::summonWanderersAndSmoke, this, fist);
        summonAttack = new SummonMobsAction(this::spawnMob, this, fist);
        IGauntletAction laserAttack = new LaserAction(this, stopLazerByte, (vec3d) -> {});
        IGauntletAction fireballAttack = new FireballThrowAction<>((target) -> target.getPositionEyes(1), this::generateFireball, this);

        IGauntletAction punchAttack = new PunchAction("gauntlet.punch", position, () -> {}, this, fist);
        ModRandom.RandomCollection<IGauntletAction> punchAttacks =
                ModRandom.choice(new IGauntletAction[]{ punchAttack, swirlPunchAttack }, rand, new double[] { 2, 1 });
        IGauntletAction comboPunchAttack = new ComboPunchAction(punchAttacks, this);
        attacks = new ArrayList<>(Arrays.asList(swirlPunchAttack, laserAttack, summonAttack, fireballAttack, comboPunchAttack));
    }

    private void spawnMob() {
        if(!trySpawnMob(false)) trySpawnMob(true);
    }

    private boolean trySpawnMob(boolean findGround) {
        EntityLeveledMob mob = ModUtils.spawnMob(world, this.getPosition(), this.getLevel(), getMobConfig().getConfig(findGround ? "summoning_algorithm" : "aerial_summoning_algorithm"), findGround);
        return mob != null;
    }

    private Projectile generateFireball() {
        ProjectileMegaFireball fireball = new ProjectileMegaFireball(world, this, this.getAttack() * 2f, null, false);
        fireball.setTravelRange((float) maxFireballDistance);
        return fireball;
    }

    private void summonWanderersAndSmoke() {
        world.setEntityState(this, ModUtils.THIRD_PARTICLE_BYTE);
        if(rand.nextInt(2) == 0) {
            summonCrimsonWanderer();
        }
    }

    @Override
    protected IGauntletAction getNextAttack(EntityLivingBase target, float distanceSq, IGauntletAction previousAction) {
        int numMinions = (int) ModUtils.getEntitiesInBox(this, getEntityBoundingBox().grow(50)).stream()
                .filter((e) -> e instanceof EntityMaelstromMob).count();

        double summonWeight = previousAction == summonAttack || numMinions > 3 ? 0 : 0.8;
        double fireballWeight = distanceSq < Math.pow(maxFireballDistance, 2) ? 1 : 0;
        double laserWeight = distanceSq < Math.pow(maxLaserDistance, 2) ? 1 : 0;
        double punchWeight = ModUtils.canEntityBeSeen(this, target) ? Math.sqrt(distanceSq) / 25 : 3;
        double comboPunchWeight = 0.8;

        double[] weights = {punchWeight, laserWeight, summonWeight, fireballWeight, comboPunchWeight};
        return ModRandom.choice(attacks, rand, weights).next();
    }

    private void summonCrimsonWanderer() {
        ProjectileCrimsonWanderer shrapnel = new ProjectileCrimsonWanderer(world, this, getAttack() * 0.5f);
        Vec3d lookVec = ModUtils.getLookVec(getPitch(), rotationYaw);
        Vec3d shrapnelPos = this.getPositionVector()
                .add(ModRandom.randVec().scale(3))
                .subtract(lookVec.scale(6));
        ModUtils.setEntityPosition(shrapnel, shrapnelPos);
        shrapnel.setNoGravity(false);
        shrapnel.setTravelRange(50);
        world.spawnEntity(shrapnel);
        ModUtils.setEntityVelocity(shrapnel, lookVec.scale(0.35));
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == ModUtils.THIRD_PARTICLE_BYTE) {
            for (int i = 0; i < 10; i++) {
                Vec3d lookVec = ModUtils.getLookVec(getPitch(), rotationYaw);
                Vec3d pos = ModRandom.randVec().scale(3).add(getPositionVector()).subtract(lookVec.scale(3));
                ParticleManager.spawnFluff(world, pos, Vec3d.ZERO, lookVec.scale(0.1));
            }
        }
        super.handleStatusUpdate(id);
    }
}
