package com.barribob.MaelstromMod.entity.ai;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAIFollowAttackers extends EntityAIBase {
    private final EntityCreature creature;
    private EntityLivingBase targetEntity;
    private final double movementSpeed;

    public EntityAIFollowAttackers(EntityCreature theCreatureIn, double movementSpeedIn) {
        this.creature = theCreatureIn;
        this.movementSpeed = movementSpeedIn;
        this.setMutexBits(1);
    }

    /**
     * Executes if there are nearby entities to avoid overcrowding
     */
    @Override
    public boolean shouldExecute() {
        EntityLivingBase closestMob = null;
        double distanceSq = Math.pow(creature.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue(), 2);
        for (EntityLivingBase entity : ModUtils.getEntitiesInBox(creature, new AxisAlignedBB(creature.getPosition()).grow(creature.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue()))) {
            if (!EntityMaelstromMob.CAN_TARGET.apply(entity) &&
                    creature.getAttackTarget() == null &&
                    entity instanceof EntityLiving &&
                    ((EntityLiving)entity).getAttackTarget() != null) {
                if (entity.getDistanceSq(creature) < distanceSq) {
                    closestMob = entity;
                    distanceSq = entity.getDistanceSq(creature);
                }
            }
        }

        if (closestMob != null) {
            this.targetEntity = closestMob;
            return true;
        }

        this.targetEntity = null;
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.creature.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        this.creature.getNavigator().tryMoveToEntityLiving(this.targetEntity, this.movementSpeed);
    }
}