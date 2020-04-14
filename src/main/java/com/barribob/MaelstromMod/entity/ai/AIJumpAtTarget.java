package com.barribob.MaelstromMod.entity.ai;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class AIJumpAtTarget extends EntityAIBase
{
    private final EntityLiving entity;
    private final float horzVel;
    private final float yVel;
    private int ticksAirborne = 0;
    private int jumpCooldown = 20;
    private int ticksSinceJump;

    public AIJumpAtTarget(EntityLiving entity, float horzVel, float yVel)
    {
	this.entity = entity;
	this.horzVel = horzVel;
	this.yVel = yVel;
	this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
	if (this.entity.onGround)
	{
	    this.ticksAirborne = 0;
	}
	else
	{
	    this.ticksAirborne++;
	}

	// Our goal is to capture the time right when the entity slips of the edge
	if (this.ticksAirborne == 1 && ModUtils.isAirBelow(entity.world, entity.getPosition(), 7) && entity.getAttackTarget() != null)
	{
	    ModUtils.leapTowards(entity, entity.getAttackTarget().getPositionVector(), horzVel, yVel);
	    this.jumpCooldown = 20;
	    return true;
	}
	else
	{
	    if (entity.getAttackTarget() != null && this.entity.getNavigator() != null && this.entity.getNavigator().noPath() && this.entity.onGround)
	    {


		BlockPos nearbyBlock = null;

		Vec3d jumpDirection = entity.getAttackTarget().getPositionVector().subtract(entity.getPositionVector()).normalize();
		Vec3d jumpPos = entity.getPositionVector().add(jumpDirection.scale(2));
		
		for (int i = 0; i < 2; i++)
		{
		    if (this.entity.getNavigator().getNodeProcessor().getPathNodeType(this.entity.world, (int) Math.round(jumpPos.x), (int) Math.round(jumpPos.y), (int) Math.round(jumpPos.z)) == PathNodeType.WALKABLE ||
			    this.entity.getNavigator().getNodeProcessor().getPathNodeType(this.entity.world, (int) Math.round(jumpPos.x), (int) Math.round(jumpPos.y) - 1, (int) Math.round(jumpPos.z)) == PathNodeType.WALKABLE)
		    {
			if (jumpPos.subtract(entity.getPositionVector()).y < 1.2)
			{
			    System.out.println(i);
			    ModUtils.leapTowards(entity, entity.getAttackTarget().getPositionVector(), horzVel * (i * 0.5f + ModRandom.getFloat(0.3f) + 1), yVel);
			    return true;
			}
		    }
		    jumpPos = jumpPos.add(jumpDirection);
		}
	    }
	}

	return false;
    }

    @Override
    public void startExecuting()
    {
    }
}
