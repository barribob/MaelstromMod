package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import net.minecraft.entity.EntityLivingBase;

/*
 * Base class for entity actions for example, Shooting, melee attack, and other actions
 */
public abstract class Action {
    public abstract void performAction(EntityLeveledMob actor, EntityLivingBase target);

    public static final Action NONE = new Action() {
        @Override
        public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
        }
    };
}
