package com.barribob.MaelstromMod.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntitySenses;

/**
 * Sames as {@code EntitySenses} except that the entity can see through glass
 */
public class GauntletEntitySenses extends EntitySenses {

    public GauntletEntitySenses(EntityLiving entityIn) {
	super(entityIn);
    }

    @Override
    public void clearSensingCache() {
    }

    @Override
    public boolean canSee(Entity entityIn) {
	return true;
    }
}
