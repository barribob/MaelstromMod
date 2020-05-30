package com.barribob.MaelstromMod.entity.ai;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class AIJumpAtTarget extends EntityAIBase
{
    private final EntityLiving entity;
    private final float horzVel;
    private final float yVel;
    private int ticksAirborne = 0;

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

	if (entity.getAttackTarget() == null)
	{
	    return false;
	}

	// Our goal is to capture the time right when the entity slips of the edge
	if (this.ticksAirborne == 1 && ModUtils.isAirBelow(entity.world, entity.getPosition(), 7))
	{
	    ModUtils.leapTowards(entity, entity.getAttackTarget().getPositionVector(), horzVel, yVel);
	    return true;
	}
	else
	{
	    if (this.entity.getNavigator() != null && this.entity.getNavigator().noPath() && this.entity.onGround)
	    {
		Vec3d jumpDirection = entity.getAttackTarget().getPositionVector().subtract(entity.getPositionVector()).normalize();
		Vec3d jumpPos = entity.getPositionVector().add(jumpDirection);

		if (!ModUtils.isAirBelow(entity.world, new BlockPos(jumpPos), 3))
		{
		    return false;
		}

		for (int i = 0; i < 2; i++)
		{
		    jumpPos = jumpPos.add(jumpDirection);
		    if (jumpPos.subtract(entity.getPositionVector()).y < 1.2)
		    {
			BlockPos pos = new BlockPos(jumpPos);
			NodeProcessor processor = this.entity.getNavigator().getNodeProcessor();

			for (int y = -1; y <= 1; y++)
			{
			    if (processor.getPathNodeType(this.entity.world, pos.getX(), pos.getY() + y, pos.getZ()) == PathNodeType.WALKABLE)
			    {
				System.out.println(i);
				ModUtils.leapTowards(entity, entity.getAttackTarget().getPositionVector(), horzVel * (i * 0.3f + ModRandom.getFloat(0.3f) + 1), yVel);
				return true;
			    }
			}
		    }
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
