package com.barribob.MaelstromMod.entity.ai;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.Vec3d;

public class AIFlyingSupport extends EntityAIBase
{
    private final EntityCreature creature;
    private Vec3d target;
    private final double movementSpeed;
    private final double heightAboveGround;

    public AIFlyingSupport(EntityCreature creature, double movementSpeed, double heightAboveGround)
    {
	this.creature = creature;
	this.movementSpeed = movementSpeed;
	this.heightAboveGround = heightAboveGround;
	this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
	Vec3d groupCenter = ModUtils.findEntityGroupCenter(this.creature, 20);
	if (groupCenter != this.creature.getPositionVector())
	{
	    this.target = groupCenter.add(ModRandom.randVec().scale(3));
	    return true;
	}

	return false;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
	return !this.creature.getNavigator().noPath();
    }

    @Override
    public void updateTask()
    {
	super.updateTask();
	Vec3d pos = this.target.add(ModUtils.yVec((float) (this.heightAboveGround + ModRandom.getFloat(0.5f) * this.heightAboveGround)));
	if (this.creature.getAttackTarget() != null)
	{
	    this.creature.faceEntity(this.creature.getAttackTarget(), 25, 25);
	    this.creature.getLookHelper().setLookPositionWithEntity(this.creature.getAttackTarget(), 25, 25);
	}
	this.creature.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, this.movementSpeed);
    }
}
