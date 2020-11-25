package com.barribob.MaelstromMod.entity.ai;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromHealer;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class AIFlyingSupport extends EntityAIBase {
    private final EntityMaelstromHealer supporter;
    private final double movementSpeed;
    private final double heightAboveGround;
    private final double supportDistance;
    private final double supportCooldown;
    private double cooldown;

    public AIFlyingSupport(EntityMaelstromHealer creature, double movementSpeed, double heightAboveGround, double supportDistance, double supportCooldown) {
        this.supportCooldown = supportCooldown;
        this.supportDistance = supportDistance;
        this.supporter = creature;
        this.movementSpeed = movementSpeed;
        this.heightAboveGround = heightAboveGround;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        return supporter.isFlying();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.shouldExecute();
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.cooldown = this.supportCooldown;
    }

    @Override
    public void updateTask() {
        super.updateTask();

        Vec3d groupCenter = ModUtils.findEntityGroupCenter(this.supporter, supporter.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue());
        boolean hasGroup = groupCenter.squareDistanceTo(this.supporter.getPositionVector()) != 0;

        /**
         * Provide support to the nearest mobs
         */
        EntityLivingBase optimalMob = null;
        double health = 2;
        for (EntityLivingBase entity : ModUtils.getEntitiesInBox(supporter, new AxisAlignedBB(supporter.getPosition()).grow(supporter.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue()))) {
            if (!EntityMaelstromMob.CAN_TARGET.apply(entity) && entity.getHealth() / entity.getMaxHealth() < health && this.supporter.getDistanceSq(entity) < Math.pow(supportDistance, 2)) {
                optimalMob = entity;
                health = entity.getHealth() / entity.getMaxHealth();
            }
        }

        if (optimalMob != null && hasGroup) {
            cooldown--;
            /**
             * Face the closest mob
             */
            this.supporter.faceEntity(optimalMob, 25, 25);
            this.supporter.getLookHelper().setLookPositionWithEntity(optimalMob, 25, 25);

            /**
             * Provide support if close enough
             */
            if (this.cooldown <= 0) {
                this.supporter.attackEntityWithRangedAttack(optimalMob, (float) this.supporter.getDistanceSq(optimalMob));
                this.cooldown = supportCooldown;
            }

            Vec3d pos = groupCenter.add(ModUtils.yVec((float) (this.heightAboveGround + ModRandom.getFloat(0.5f) * this.heightAboveGround)));
            this.supporter.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, this.movementSpeed);
        } else {
            /**
             * Move towards the target, which is the center of the group
             */
            Vec3d pos;

            if (hasGroup) {
                pos = groupCenter.add(ModUtils.yVec((float) (this.heightAboveGround + ModRandom.getFloat(0.5f) * this.heightAboveGround)));
            }
            /**
             * Run away from the attack target if there are no mobs to support nearby
             */
            else if (this.supporter.getAttackTarget() != null) {
                Vec3d away = this.supporter.getPositionVector().subtract(this.supporter.getAttackTarget().getPositionVector()).normalize();
                pos = this.supporter.getPositionVector().add(away.scale(4)).add(ModRandom.randVec().scale(4));
            }
            /**
             * There is no target and no mobs to support, slowly float down
             */
            else {
                pos = this.supporter.getPositionVector().add(ModUtils.yVec(0.01));
            }

            this.supporter.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, this.movementSpeed);
            this.supporter.getLookHelper().setLookPosition(pos.x, pos.y, pos.z, 3, 3);
            ModUtils.facePosition(pos, this.supporter, 10, 10);
        }
    }
}
