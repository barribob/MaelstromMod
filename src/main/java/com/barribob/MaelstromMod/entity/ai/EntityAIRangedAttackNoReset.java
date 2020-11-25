package com.barribob.MaelstromMod.entity.ai;

import net.minecraft.entity.EntityCreature;

/**
 * Makes the task unresettable
 */
public class EntityAIRangedAttackNoReset<T extends EntityCreature> extends EntityAIRangedAttack {
    public EntityAIRangedAttackNoReset(T entity, double moveSpeedAmp, int attackCooldown, float armsRaisedTime, float maxAttackDistance, float strafeAmount) {
        super(entity, moveSpeedAmp, attackCooldown, armsRaisedTime, maxAttackDistance, strafeAmount);
    }

    @Override
    public void resetTask() {
    }
}
