package com.barribob.MaelstromMod.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAttackMelee;

/**
 * 
 * Makes the task unresettable
 *
 */
public class EntityAIRangedAttackNoReset <T extends EntityCreature> extends EntityAIRangedAttack
{
    public EntityAIRangedAttackNoReset(T entity, double moveSpeedAmp, int attackCooldown, float maxAttackDistance)
    {
	super(entity, moveSpeedAmp, attackCooldown, maxAttackDistance);
	this.ARMS_RAISED_TIME_RATIO = 0.15f;
    }

    @Override
    public void resetTask()
    {
    }
}
