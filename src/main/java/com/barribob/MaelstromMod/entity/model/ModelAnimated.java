package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.util.IAnimatedMob;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;

public abstract class ModelAnimated extends ModelBase {
    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime) {
        if (entity instanceof IAnimatedMob) {
            // The partial tick time conditional prevent twitching when the animation is stopped after death
            ((IAnimatedMob) entity).getCurrentAnimation().setModelRotations(this, limbSwing, limbSwingAmount, entity.getHealth() > 0 ? partialTickTime : 0.99f);
        } else {
            throw new IllegalArgumentException("The entity class " + entity.getClass().getName() + " was not an instance of EntityLeveledMob");
        }
    }
}
