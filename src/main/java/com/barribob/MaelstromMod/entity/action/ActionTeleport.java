package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.util.ModRandom;
import net.minecraft.entity.EntityLivingBase;

/*
 * Attempt to teleport around the target
 */
public class ActionTeleport extends Action {
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
        int attempts = 100;
        int minRange = 5;
        int maxRange = 11;
        int yOffset = 2;

        while (attempts > 0) {
            double x = ModRandom.range(minRange, maxRange) * ModRandom.randSign() + target.posX;
            double z = ModRandom.range(minRange, maxRange) * ModRandom.randSign() + target.posZ;
            double y = target.posY - yOffset;

            for (; y <= yOffset + target.posY; y++) {
                if (actor.attemptTeleport(x, y, z)) {
                    return;
                }
            }

            attempts++;
        }
    }
}
