package com.barribob.MaelstromMod.entity.ai;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.entity.entities.gauntlet.EntityMaelstromGauntlet;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/**
 * AI made specifically for the gauntlet to wander using its punch attack
 * 
 * @author Barribob
 *
 */
public class AiFistWander extends EntityAIBase {
    protected final EntityMaelstromGauntlet entity;
    protected Vec3d direction;
    protected int executionChance;
    protected float heightAboveGround;

    public AiFistWander(EntityMaelstromGauntlet entity, int executionChance, float heightAboveGround) {
	this.entity = entity;
	this.executionChance = executionChance;
	this.heightAboveGround = heightAboveGround;
    }

    @Nullable
    protected Vec3d getPosition() {
	Vec3d groupCenter = ModUtils.findEntityGroupCenter(this.entity, 20);

	for (int i = 0; i < 10; i++) {
	    int minRange = 5;
	    int maxRange = 15;
	    Vec3d pos = groupCenter.add(new Vec3d(ModRandom.range(minRange, maxRange) * ModRandom.randSign(), 0, ModRandom.range(minRange, maxRange) * ModRandom.randSign()));
	    pos = new Vec3d(ModUtils.findGroundBelow(entity.world, new BlockPos(pos)));
	    pos = pos.add(ModUtils.yVec(heightAboveGround));

	    RayTraceResult result = entity.world.rayTraceBlocks(entity.getPositionEyes(1), pos, false, true, false);
	    if (result == null || result.typeOfHit != RayTraceResult.Type.BLOCK) {
		return pos;
	    }
	}

	return null;

    }

    @Override
    public void startExecuting() {
	if (entity.getAttackTarget() == null) {
	    entity.punchAtPos.accept(direction);
	}
    }

    @Override
    public boolean shouldExecute() {
	if (this.entity.getIdleTime() >= 100) {
	    return false;
	}

	if (this.entity.ticksExisted % 100 != this.executionChance) {
	    return false;
	}

	Vec3d vec3d = this.getPosition();

	if (vec3d == null) {
	    return false;
	}
	else {
	    direction = vec3d;
	    return true;
	}
    }
}