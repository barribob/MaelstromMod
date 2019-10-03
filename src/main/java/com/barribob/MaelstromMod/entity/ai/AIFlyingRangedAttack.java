package com.barribob.MaelstromMod.entity.ai;

import com.barribob.MaelstromMod.util.ModRandom;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AIFlyingRangedAttack<T extends EntityLiving & IRangedAttackMob> extends EntityAIBase
{
    private final T parentEntity;
    public int attackTimer;
    private final int attackCooldown;
    private final float maxAttackDistance;
    private final float idealAttackDistance;
    protected float armsRaisedTime;
    private float moveSpeed;

    public AIFlyingRangedAttack(T e, int attackCooldown, float maxAttackDistance, float raiseArmsTime, float moveSpeed)
    {
	this.parentEntity = e;
	this.attackCooldown = attackCooldown;
	this.maxAttackDistance = maxAttackDistance * maxAttackDistance;
	this.idealAttackDistance = this.maxAttackDistance * 0.25f;
	this.armsRaisedTime = raiseArmsTime;
	this.moveSpeed = moveSpeed;
    }

    @Override
    public boolean shouldExecute()
    {
	return this.parentEntity.getAttackTarget() != null;
    }

    @Override
    public void startExecuting()
    {
	this.attackTimer = 0;
    }

    @Override
    public void resetTask()
    {
	this.parentEntity.setSwingingArms(false);
    }

    @Override
    public void updateTask()
    {
	EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
	double distance = entitylivingbase.getDistanceSq(this.parentEntity);

	if (distance < maxAttackDistance && this.parentEntity.canEntityBeSeen(entitylivingbase))
	{
	    World world = this.parentEntity.world;
	    ++this.attackTimer;

	    if (this.attackTimer == armsRaisedTime)
	    {
		this.parentEntity.setSwingingArms(true);
	    }

	    if (this.attackTimer == attackCooldown)
	    {
		if (!this.parentEntity.world.isRemote)
		{
		    this.parentEntity.attackEntityWithRangedAttack(this.parentEntity.getAttackTarget(), (float) distance);
		}
		this.attackTimer = -attackCooldown;
	    }
	}
	else if (this.attackTimer > 0)
	{
	    --this.attackTimer;
	}

	if (distance > idealAttackDistance)
	{
	    Vec3d moveVec = this.parentEntity.getAttackTarget().getPositionVector().subtract(this.parentEntity.getPositionVector()).normalize().scale(16);
	    Vec3d pos = this.parentEntity.getPositionVector().add(moveVec);
	    pos = pos.add(ModRandom.randVec().scale(16));
	    this.parentEntity.getMoveHelper().setMoveTo(pos.x, pos.y, pos.z, moveSpeed);
	}

    }
}