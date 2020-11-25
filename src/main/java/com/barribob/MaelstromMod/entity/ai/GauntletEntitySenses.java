package com.barribob.MaelstromMod.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntitySenses;

import javax.annotation.Nonnull;

public class GauntletEntitySenses extends EntitySenses {

    private final EntityLiving entity;

    public GauntletEntitySenses(EntityLiving entityIn) {
        super(entityIn);
        entity = entityIn;
    }

    @Override
    public void clearSensingCache() {
    }

    @Override
    public boolean canSee(@Nonnull Entity entityIn) {
        return entity.getAttackTarget() != null || super.canSee(entityIn);
    }
}
