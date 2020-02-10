package com.barribob.MaelstromMod.entity.util;

import net.minecraft.entity.EntityLivingBase;

/**
 * Used by {@link #EntityAITimedAttack}
 */
public interface IAttack
{
    /**
     * Called when the entity is ready to begin an attack sequence
     * 
     * @param target
     * @param distanceSq
     * @param strafingBackwards
     * @return The number of seconds before launching another attack (calling this
     *         method again)
     */
    int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards);
}
