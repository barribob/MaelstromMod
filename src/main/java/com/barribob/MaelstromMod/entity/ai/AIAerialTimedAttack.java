package com.barribob.MaelstromMod.entity.ai;

import com.barribob.MaelstromMod.entity.util.IAttackInitiator;
import com.barribob.MaelstromMod.entity.util.IPitch;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.Vec3d;

/**
 * A version of the timed attack that attempts to work for flying mobs a bit better.
 *
 * @author micha
 */
public class AIAerialTimedAttack extends EntityAIBase {
    private final EntityLiving entity;
    private final float maxAttackDistSq;
    private final float lookSpeed;
    private final IAttackInitiator attackInitiator;
    private int unseeTime;
    private final AIPassiveCircle<EntityLiving> circleAI;

    private static final int MEMORY = 100;

    public AIAerialTimedAttack(EntityLiving entity, float maxAttackDistance, float idealAttackDistance, float lookSpeed, IAttackInitiator attackInitiator) {
        this.entity = entity;
        this.maxAttackDistSq = maxAttackDistance * maxAttackDistance;
        this.lookSpeed = lookSpeed;
        this.attackInitiator = attackInitiator;
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
        attackInitiator.resetTask();
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
            attackInitiator.update(target);
        }
    }

    public void move(EntityLivingBase target, double distSq, boolean canSee) {
        circleAI.updateTask();

        this.entity.getLookHelper().setLookPositionWithEntity(target, this.lookSpeed, this.lookSpeed);
        this.entity.faceEntity(target, this.lookSpeed, this.lookSpeed);
        if (this.entity instanceof IPitch) {
            Vec3d targetPos = target.getPositionEyes(1);
            Vec3d entityPos = this.entity.getPositionEyes(1);
            Vec3d forwardVec = ModUtils.direction(entityPos, targetPos);
            ((IPitch) this.entity).setPitch(forwardVec);
        }
    }
}