package com.barribob.MaelstromMod.entity.ai;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class AIPassiveCircle<T extends EntityLiving> extends EntityAIBase {

    private final T entity;
    private @Nullable Vec3d planeVectorPath = getNewPlaneVector();
    private final float circleRadius;

    public AIPassiveCircle(T entity, float circleRadius) {
        this.entity = entity;
        this.circleRadius = circleRadius;
        setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        return entity.getAttackTarget() != null;
    }

    @Override
    public void updateTask() {
        if(entity.getAttackTarget() != null) {
            Vec3d target = entity.getAttackTarget().getPositionVector();
            Vec3d nextPointToFollow = getNextPoint(target);
            Vec3d direction = nextPointToFollow.subtract(entity.getPositionVector()).normalize();
            double speed = entity.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue();
            ModUtils.addEntityVelocity(entity, direction.scale(0.05f * speed));

            if (!hasClearPath(nextPointToFollow) || lineBlocked(nextPointToFollow, target)) {
                planeVectorPath = getNewPlaneVector();
            }

            ModUtils.facePosition(nextPointToFollow, entity, 10, 10);
            entity.getLookHelper().setLookPosition(nextPointToFollow.x, nextPointToFollow.y, nextPointToFollow.z, 3, 3);
        }

        super.updateTask();
    }

    private Vec3d getNewPlaneVector() {
        return ModUtils.Y_AXIS.add(ModRandom.randVec().scale(2)).normalize();
    }

    private boolean hasClearPath(Vec3d nextPointToFollow) {
        return ModUtils.getBoundingBoxCorners(entity.getEntityBoundingBox()).stream().noneMatch(vec3d -> lineBlocked(vec3d, nextPointToFollow));
    }

    private boolean lineBlocked(Vec3d start, Vec3d nextPointToFollow) {
        RayTraceResult rayTraceResult = entity.world.rayTraceBlocks(start, nextPointToFollow, false, true, false);
        return rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK;
    }

    private Vec3d getNextPoint(Vec3d center) {
        Vec3d circlePointInWorld = entity.getPositionVector().add(ModRandom.randVec());
        for(int i = 0; i < circleRadius; i++) {
            for(int sign : new int[]{1, -1}) {
                Vec3d entityVelocity = ModUtils.getEntityVelocity(entity);
                Vec3d entityDirection = entity.getPositionVector().subtract(center);
                Vec3d projectedEntityDirection = ModUtils.planeProject(entityDirection, planeVectorPath).normalize().scale(circleRadius + (i * sign));
                Vec3d nextPointOnCircle = ModUtils.rotateVector2(projectedEntityDirection, planeVectorPath, 15 * entityVelocity.lengthVector());
                circlePointInWorld = nextPointOnCircle.add(center);
                if (hasClearPath(circlePointInWorld)) {
                    return circlePointInWorld;
                } else {
                    planeVectorPath = getNewPlaneVector();
                }
            }
        }
        return circlePointInWorld;
    }
}
