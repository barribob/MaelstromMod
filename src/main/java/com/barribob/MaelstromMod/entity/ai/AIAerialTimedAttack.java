package com.barribob.MaelstromMod.entity.ai;

import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.entity.util.IPitch;
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
    private final int attackCooldown;
    private final float maxAttackDistSq;
    private int attackTime;
    private final float lookSpeed;
    private final float idealAttackDistanceSq;
    private int unseeTime;
    private final AIPassiveCircle<T> circleAI;

    private static final int MEMORY = 100;

    public AIAerialTimedAttack(T entity, int attackCooldown, float maxAttackDistance, float idealAttackDistance, float lookSpeed) {
        this.entity = entity;
        this.attackCooldown = attackCooldown;
        this.maxAttackDistSq = maxAttackDistance * maxAttackDistance;
        this.attackTime = attackCooldown;
        this.lookSpeed = lookSpeed;
        this.idealAttackDistanceSq = idealAttackDistance * idealAttackDistance;
        circleAI = new AIPassiveCircle<>(entity, idealAttackDistance);
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
                this.attackTime = this.entity.startAttack(target, (float) distSq, false);
            }
        }
    }

    public void move(EntityLivingBase target, double distSq, boolean canSee) {
        if (distSq <= this.idealAttackDistanceSq && canSee) {
            this.entity.getNavigator().clearPath();
            circleAI.updateTask();
        } else {
            this.entity.getNavigator().tryMoveToEntityLiving(target, 1.0f);
        }

        this.entity.getLookHelper().setLookPositionWithEntity(target, this.lookSpeed, this.lookSpeed);
        this.entity.faceEntity(target, this.lookSpeed, this.lookSpeed);
        if (this.entity instanceof IPitch) {
            Vec3d targetPos = target.getPositionEyes(1);
            Vec3d entityPos = this.entity.getPositionEyes(1);
            Vec3d forwardVec = targetPos.subtract(entityPos).normalize();
            ((IPitch) this.entity).setPitch(forwardVec);
        }
    }
}