package com.barribob.MaelstromMod.entity.ai;

import com.barribob.MaelstromMod.entity.entities.gauntlet.EntityMaelstromGauntlet;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.Vec3d;

/**
 * A version of the timed attack that attempts to work for flying mobs a bit better.
 *
 * @param <T>
 * @author micha
 */
public class AIAerialTimedAttack<T extends EntityLiving & IAttack> extends EntityAIBase {
    private final T entity;
    private final double moveSpeedAmp;
    private final int attackCooldown;
    private final float maxAttackDistSq;
    private int attackTime;
    private float strafeDirection;
    private boolean strafingBackwards;
    private int strafingTime = -1;
    private final float strafeAmount;
    private float lookSpeed;
    private final float idealAttackDistanceSq;
    private int unseeTime;

    private static final int MEMORY = 100;
    private static final float STRAFING_STOP_FACTOR = 0.75f;
    private static final float STRAFING_BACKWARDS_FACTOR = 0.25f;
    private static final float STRAFING_DIRECTION_TICK = 20;
    private static final float STRAFING_DIRECTION_CHANGE_CHANCE = 0.3f;
    private static final int MAX_STRAFE_ANGLE = 360;

    public AIAerialTimedAttack(T entity, double moveSpeedAmp, int attackCooldown, float maxAttackDistance, float idealAttackDistance, float strafeAmount, float lookSpeed) {
        this.entity = entity;
        this.moveSpeedAmp = moveSpeedAmp;
        this.attackCooldown = attackCooldown;
        this.maxAttackDistSq = maxAttackDistance * maxAttackDistance;
        this.strafeAmount = strafeAmount;
        this.attackTime = attackCooldown;
        this.lookSpeed = lookSpeed;
        this.idealAttackDistanceSq = idealAttackDistance * idealAttackDistance;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        return this.entity.getAttackTarget() != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return (this.shouldExecute() || !this.entity.getNavigator().noPath());
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.attackTime = Math.max(attackTime, attackCooldown);
    }

    @Override
    public void updateTask() {
        EntityLivingBase target = this.entity.getAttackTarget();

        if (target == null) {
            return;
        }

        double distSq = this.entity.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
        boolean canSee = this.entity.getEntitySenses().canSee(target);

        // Implements some sort of memory mechanism (can still attack a short while after the enemy isn't seen)
        if (canSee) {
            unseeTime = 0;
        } else {
            unseeTime += 1;
        }

        canSee = canSee || unseeTime < MEMORY;

        move(target, distSq, canSee);

        if (distSq <= this.maxAttackDistSq && canSee) {
            this.attackTime--;
            if (this.attackTime <= 0) {
                this.attackTime = this.entity.startAttack(target, (float) distSq, this.strafingBackwards);
            }
        }
    }

    public void move(EntityLivingBase target, double distSq, boolean canSee) {
        if (distSq <= this.idealAttackDistanceSq && canSee) {
            this.entity.getNavigator().clearPath();
            ++this.strafingTime;
        } else {
            this.entity.getNavigator().tryMoveToEntityLiving(target, this.moveSpeedAmp);
            this.strafingTime = -1;
        }

        if (this.strafingTime >= STRAFING_DIRECTION_TICK) {
            if (this.entity.getRNG().nextFloat() < STRAFING_DIRECTION_CHANGE_CHANCE) {
                this.strafeDirection = this.entity.getRNG().nextFloat() * MAX_STRAFE_ANGLE;
            }

            if (this.entity.getRNG().nextFloat() < STRAFING_DIRECTION_CHANGE_CHANCE) {
                this.strafingBackwards = !this.strafingBackwards;
            }

            this.strafingTime = 0;
        }

        if (this.strafingTime > -1) {
            if (distSq > this.idealAttackDistanceSq * STRAFING_STOP_FACTOR) {
                this.strafingBackwards = false;
            } else if (distSq < this.idealAttackDistanceSq * STRAFING_BACKWARDS_FACTOR) {
                this.strafingBackwards = true;
            }

            boolean successful = this.aerialStrafe((this.strafingBackwards ? -1 : 1) * this.strafeAmount, this.strafeDirection);

            if (!successful) {
                this.strafeDirection = this.entity.getRNG().nextFloat() * MAX_STRAFE_ANGLE;
            }
        }

        this.entity.getLookHelper().setLookPositionWithEntity(target, this.lookSpeed, this.lookSpeed);
        this.entity.faceEntity(target, this.lookSpeed, this.lookSpeed);
        if (this.entity instanceof EntityMaelstromGauntlet) {
            Vec3d targetPos = target.getPositionEyes(1);
            Vec3d entityPos = this.entity.getPositionEyes(1);
            Vec3d forwardVec = targetPos.subtract(entityPos).normalize();
            ((EntityMaelstromGauntlet) this.entity).setLook(forwardVec);
        }
    }

    private boolean aerialStrafe(float forward, float sidewaysRotation) {
        Vec3d forwardVec = this.entity.getAttackTarget().getPositionVector().subtract(this.entity.getPositionVector()).normalize();
        Vec3d sidewaysVec = ModUtils.rotateVector(forwardVec, forwardVec.crossProduct(new Vec3d(0, 1, 0)), 90);
        Vec3d strafeVec = ModUtils.rotateVector(sidewaysVec, forwardVec, sidewaysRotation);
        Vec3d move = forwardVec.scale(forward).add(strafeVec).scale(4);
        Vec3d pos = this.entity.getPositionVector().add(move);
        return this.entity.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, this.moveSpeedAmp);
    }
}