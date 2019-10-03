package com.barribob.MaelstromMod.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * 
 * Uses a similar ai to the skeleton, just slightly altered to fit the mod's
 * needs better
 *
 * @param <T>
 *            The entity to aquire the ai
 */
public class EntityAIRangedAttack<T extends EntityLiving & IRangedAttackMob> extends EntityAIBase
{
    private final T entity;
    private final double moveSpeedAmp;
    private final int attackCooldown;
    private final float maxAttackDistance;
    private int attackTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;
    private boolean active;
    private final float strafeAmount;
    private int seeTime;
    private boolean attackStarted = false;

    private static final float STRAFING_STOP_FACTOR = 0.75f;
    private static final float STRAFING_BACKWARDS_FACTOR = 0.25f;
    private static final float STRAFING_DIRECTION_TICK = 20;
    private static final float STRAFING_DIRECTION_CHANGE_CHANCE = 0.3f;
    private static final int SEE_TIME = 20;
    private static final int LOSE_SIGHT_TIME = 60;
    protected float arms_raised_time;

    public EntityAIRangedAttack(T entity, double moveSpeedAmp, int attackCooldown, float maxAttackDistance, float strafeAmount)
    {
	this.entity = entity;
	this.moveSpeedAmp = moveSpeedAmp;
	this.attackCooldown = attackCooldown;
	this.maxAttackDistance = maxAttackDistance * maxAttackDistance;
	this.strafeAmount = strafeAmount;
	this.arms_raised_time = this.attackCooldown * 0.3f;
	this.attackTime = attackCooldown;
	this.setMutexBits(3);
    }

    public EntityAIRangedAttack(T entity, double moveSpeedAmp, int attackCooldown, float raiseArmsTime, float maxAttackDistance, float strafeAmount)
    {
	this(entity, moveSpeedAmp, attackCooldown, maxAttackDistance, strafeAmount);
	this.arms_raised_time = raiseArmsTime;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute()
    {
	return this.entity.getAttackTarget() != null;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting()
    {
	return (this.shouldExecute() || !this.entity.getNavigator().noPath());
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by
     * another one
     */
    @Override
    public void resetTask()
    {
	super.resetTask();
	entity.setSwingingArms(false);
	this.seeTime = 0;
	this.attackTime = attackCooldown;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void updateTask()
    {
	EntityLivingBase entitylivingbase = this.entity.getAttackTarget();

	if (entitylivingbase != null)
	{
	    double d0 = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
	    boolean flag = this.entity.getEntitySenses().canSee(entitylivingbase);
	    boolean flag1 = this.seeTime > 0;

	    if (flag != flag1)
	    {
		this.seeTime = 0;
	    }

	    if (flag)
	    {
		++this.seeTime;
	    }
	    else
	    {
		--this.seeTime;
	    }

	    if (d0 <= this.maxAttackDistance && this.seeTime >= SEE_TIME)
	    {
		this.entity.getNavigator().clearPath();
		++this.strafingTime;
	    }
	    else
	    {
		this.entity.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.moveSpeedAmp);
		this.strafingTime = -1;
	    }

	    if (this.strafingTime >= STRAFING_DIRECTION_TICK)
	    {
		if ((double) this.entity.getRNG().nextFloat() < STRAFING_DIRECTION_CHANGE_CHANCE)
		{
		    this.strafingClockwise = !this.strafingClockwise;
		}

		if ((double) this.entity.getRNG().nextFloat() < STRAFING_DIRECTION_CHANGE_CHANCE)
		{
		    this.strafingBackwards = !this.strafingBackwards;
		}

		this.strafingTime = 0;
	    }

	    if (this.strafingTime > -1)
	    {
		if (d0 > this.maxAttackDistance * STRAFING_STOP_FACTOR)
		{
		    this.strafingBackwards = false;
		}
		else if (d0 < this.maxAttackDistance * STRAFING_BACKWARDS_FACTOR)
		{
		    this.strafingBackwards = true;
		}

		this.entity.getMoveHelper().strafe((this.strafingBackwards ? -1 : 1) * this.strafeAmount, (this.strafingClockwise ? 1 : -1) * this.strafeAmount);
		this.entity.faceEntity(entitylivingbase, 30.0F, 30.0F);
	    }
	    else
	    {
		this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
	    }

	    if (active)
	    {
		active = false;
		this.entity.attackEntityWithRangedAttack(entitylivingbase, (float) d0);
		this.attackTime = this.attackCooldown;
		entity.setSwingingArms(false);
		this.attackStarted = false;
	    }
	    else if (attackStarted && --this.attackTime <= 0 && this.seeTime >= -LOSE_SIGHT_TIME)
	    {
		active = true;
	    }
	    else if (this.attackTime == this.arms_raised_time)
	    {
		this.entity.setSwingingArms(true);
	    }

	    if (!attackStarted && d0 <= this.maxAttackDistance && flag)
	    {
		this.attackStarted = true;
	    }
	}
    }
}