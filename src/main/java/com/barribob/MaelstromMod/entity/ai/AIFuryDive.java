package com.barribob.MaelstromMod.entity.ai;

import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/**
 * Has a cooldown that prevents it from running immediately
 * Requires the player not be looking at the entity
 */
public class AIFuryDive extends EntityAIBase {

    private int cooldown;
    private final int maxCooldown;
    private final EntityLiving entity;
    private final Runnable onDiveStart;
    private final Runnable onDiveEnd;
    private final Runnable whileDiving;
    private final int maxDiveTime;

    public AIFuryDive(int maxCooldown, int maxDiveTime, EntityLiving entity, Runnable onDiveStart, Runnable onDiveEnd, Runnable whileDiving) {
        this.maxCooldown = maxCooldown;
        this.entity = entity;
        this.onDiveEnd = onDiveEnd;
        this.onDiveStart = onDiveStart;
        this.whileDiving = whileDiving;
        this.maxDiveTime = maxDiveTime;
        setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        cooldown++;
        return shouldContinueExecuting() && entity.getAttackTarget().getLookVec().dotProduct(entity.getPositionVector().subtract(entity.getAttackTarget().getPositionEyes(1))) < 0;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return cooldown >= maxCooldown && entity.getAttackTarget() != null && hasClearPath(entity.getAttackTarget().getPositionEyes(1));
    }

    @Override
    public void startExecuting() {
        onDiveStart.run();
        cooldown = maxCooldown;
    }

    private boolean hasClearPath(Vec3d nextPointToFollow) {
        return ModUtils.getBoundingBoxCorners(entity.getEntityBoundingBox()).stream().noneMatch(vec3d -> {
            RayTraceResult rayTraceResult = entity.world.rayTraceBlocks(vec3d, nextPointToFollow, true, true, false);
            return rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK;
        });
    }

    @Override
    public void resetTask() {
        cooldown = 0;
        onDiveEnd.run();
    }

    @Override
    public void updateTask() {
        dive();
    }

    public void dive() {
        if (entity.getAttackTarget() != null) {
            cooldown++;
            Vec3d target = entity.getAttackTarget().getPositionEyes(1);
            Vec3d pos = entity.getPositionVector();
            Vec3d directionToTarget = target.subtract(pos).normalize();
            double speed = entity.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue();
            ModUtils.addEntityVelocity(entity, directionToTarget.scale(0.055 * speed));
            Vec3d lookTarget = pos.add(directionToTarget);

            ModUtils.facePosition(lookTarget, entity, 10, 10);
            entity.getLookHelper().setLookPosition(lookTarget.x, lookTarget.y, lookTarget.z, 3, 3);
            whileDiving.run();

            boolean diveInCriteria = target.squareDistanceTo(pos) < Math.pow(ModUtils.getEntityVelocity(entity).lengthVector() * 4, 2) || entity.collided;

            if (diveInCriteria || !hasClearPath(target) || cooldown - maxCooldown > maxDiveTime) {
                resetTask();
            }
        }
    }
}
