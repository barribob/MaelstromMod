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

public class EntityAlternativeMaelstromGauntlet extends EntityAbstractMaelstromGauntlet {
    private final IGauntletAction punchAttack;
    private final IGauntletAction swirlPunchAttack;
    private final IGauntletAction summonAttack;
    private final IGauntletAction laserAttack;
    private final IGauntletAction fireballAttack;
    private final List<IGauntletAction> attacks;
    private final double fireballHealth = getMobConfig().getDouble("use_fireball_at_health");
    private final double lazerHealth = getMobConfig().getDouble("use_lazer_at_health");
    private final double spawnHealth = getMobConfig().getDouble("use_spawning_at_health");

    public EntityAlternativeMaelstromGauntlet(World worldIn) {
        super(worldIn);
        punchAttack = new PunchAction("gauntlet.punch", () -> getAttackTarget().getPositionVector(), () -> {}, this, fist);
        swirlPunchAttack = new PunchAction("gauntlet.swirl_punch", () -> getAttackTarget().getPositionVector(), this::summonWanderersAndSmoke, this, fist);
        summonAttack = new SummonMobsAction(this::spawnMob, this, fist);
        laserAttack = new LaserAction(this, stopLazerByte, this::onLaserImpact);
        fireballAttack = new FireballThrowAction<>(() -> getAttackTarget().getPositionEyes(1), this::generateFireball, this);
        attacks = new ArrayList<>(Arrays.asList(punchAttack, laserAttack, summonAttack, fireballAttack));
    }

    private void spawnMob() {
        if(!trySpawnMob(false)) trySpawnMob(true);
    }

    private boolean trySpawnMob(boolean findGround) {
        EntityLeveledMob mob = ModUtils.spawnMob(world, this.getPosition(), this.getLevel(), getMobConfig().getConfig(findGround ? "summoning_algorithm" : "aerial_summoning_algorithm"), findGround);
        return mob != null;
    }

    private Projectile generateFireball() {
        return new ProjectileMegaFireball(world, this, this.getAttack() * 2f, null, true);
    }

    private void summonWanderersAndSmoke() {
        world.setEntityState(this, ModUtils.THIRD_PARTICLE_BYTE);
        if(this.ticksExisted % 2 == 0) {
            summonCrimsonWanderer();
        }
    }

    @Override
    protected IGauntletAction getNextAttack(EntityLivingBase target, float distanceSq, IGauntletAction previousAction) {
        int numMinions = (int) ModUtils.getEntitiesInBox(this, getEntityBoundingBox().grow(20, 10, 20)).stream()
                .filter((e) -> e instanceof EntityMaelstromMob).count();

        double defendWeight = previousAction == this.summonAttack || numMinions > 3 || this.getHealth() > spawnHealth ? 0 : 0.8;
        double fireballWeight = distanceSq < Math.pow(25, 2) && this.getHealth() < fireballHealth ? 1 : 0;
        double lazerWeight = distanceSq < Math.pow(35, 2) && this.getHealth() < lazerHealth ? 1 : 0;
        double punchWeight = ModUtils.canEntityBeSeen(this, target) ? Math.sqrt(distanceSq) / 25 : 3;

        double[] weights = {punchWeight, lazerWeight, defendWeight, fireballWeight};
        return ModRandom.choice(attacks, rand, weights).next();
    }

    private void onLaserImpact(Vec3d lazerPos) {
        Projectile projectile = new ProjectileCrimsonWanderer(world, this, getAttack() * 0.5f);
        projectile.setTravelRange((float) (getMobConfig().getDouble("max_laser_distance") + 20));
        ModUtils.throwProjectile(this, lazerPos.add(ModUtils.Y_AXIS), projectile, 10f, 0.1f, lazerPos.subtract(getPositionEyes(1)).add(ModUtils.Y_AXIS));
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
