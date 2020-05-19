package com.barribob.MaelstromMod.entity.ai;

import java.util.List;

import com.barribob.MaelstromMod.util.ModUtils;
import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntitySenses;

/**
 * Sames as {@code EntitySenses} except that the entity can see through glass
 */
public class ModEntitySenses extends EntitySenses {

    EntityLiving entity;
    /** Cache of entities which we can see */
    List<Entity> seenEntities = Lists.<Entity>newArrayList();
    /** Cache of entities which we cannot see */
    List<Entity> unseenEntities = Lists.<Entity>newArrayList();

    public ModEntitySenses(EntityLiving entityIn) {
	super(entityIn);
	this.entity = entityIn;
    }

    /**
     * Clears canSeeCachePositive and canSeeCacheNegative.
     */
    @Override
    public void clearSensingCache() {
	this.seenEntities.clear();
	this.unseenEntities.clear();
    }

    @Override
    public boolean canSee(Entity entityIn) {
	if (this.seenEntities.contains(entityIn)) {
	    return true;
	}
	else if (this.unseenEntities.contains(entityIn)) {
	    return false;
	}
	else {
	    this.entity.world.profiler.startSection("canSee");
	    boolean flag = ModUtils.canEntityBeSeen(this.entity, entityIn);
	    this.entity.world.profiler.endSection();

	    if (flag) {
		this.seenEntities.add(entityIn);
	    }
	    else {
		this.unseenEntities.add(entityIn);
	    }

	    return flag;
	}
    }
}
