package com.barribob.MaelstromMod.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.EnumHand;

/**
 * 
 * Uses a similar ai to the skeleton, just slightly altered to fit the mod's
 * needs better
 *
 * @param <T>
 *            The entity to aquire the ai
 */
public class EntityAIRangedAttack<T extends EntityCreature & IRangedAttackMob> extends EntityAIBase
{
    private final T entity;
    private final double moveSpeedAmp;
    private int attackCooldown;
    private final float maxAttackDistance;
    private int attackTime = -1;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;
    private boolean active;

    private static final float STRAFING_STOP_FACTOR = 0.75f;
    private static final float STRAFING_BACKWARDS_FACTOR = 0.25f;
    private static final float STRAFING_DIRECTION_TICK = 20;
    private static final float STRAFING_DIRECTION_CHANGE_CHANCE = 0.3f;
    private static final int SEE_TIME = 20;
    private static final int LOSE_SIGHT_TIME = 60;
    private static final float ARMS_RAISED_TIME_RATIO = 0.3f;

    public EntityAIRangedAttack(T entity, double moveSpeedAmp, int attackCooldown, float maxAttackDistance)
    {
	this.entity = entity;
	this.moveSpeedAmp = moveSpeedAmp;
	this.attackCooldown = attackCooldown;
	this.maxAttackDistance = maxAttackDistance * maxAttackDistance;
	this.setMutexBits(3);
    }

    public void setAttackCooldown(int attackCooldown)
    {
	this.attackCooldown = attackCooldown;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
	return this.entity.getAttackTarget() != null;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
	return (this.shouldExecute() || !this.entity.getNavigator().noPath());
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by
     * another one
     */
    public void resetTask()
    {
	super.resetTask();
	((IRangedAttackMob) this.entity).setSwingingArms(false);
	this.attackTime = -1;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
	EntityLivingBase target = this.entity.getAttackTarget();

	if (target != null)
	{
	    double d0 = this.entity.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
	    boolean inRange = this.entity.getEntitySenses().canSee(target) && d0 <= (double) this.maxAttackDistance;

	    if (d0 <= (double) this.maxAttackDistance)
	    {
		this.entity.getNavigator().clearPath();
		++this.strafingTime;
	    }
	    else
	    {
		this.entity.getNavigator().tryMoveToEntityLiving(target, this.moveSpeedAmp);
		this.strafingTime = -1;
	    }

	    // Handle strafing direction changes
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

	    // Strafe
	    if (this.strafingTime > -1)
	    {
		if (d0 > (double) (this.maxAttackDistance * STRAFING_STOP_FACTOR))
		{
		    this.strafingBackwards = false;
		}
		else if (d0 < (double) (this.maxAttackDistance * STRAFING_BACKWARDS_FACTOR))
		{
		    this.strafingBackwards = true;
		}

		this.entity.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
		this.entity.faceEntity(target, 30.0F, 30.0F);
	    }
	    else
	    {
		this.entity.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
	    }

	    // Handle the attacking
	    if (this.active)
	    {
		this.attackTime--;
		if (this.attackTime <= 0)
		{
		    this.active = false;
		    ((IRangedAttackMob) this.entity).attackEntityWithRangedAttack(target, 0);
		    ((IRangedAttackMob) this.entity).setSwingingArms(false);
		}
		// Right before the attack, raise arms
		else if (this.attackTime <= this.attackCooldown * this.ARMS_RAISED_TIME_RATIO)
		{
		    ((IRangedAttackMob) this.entity).setSwingingArms(true);
		}
	    }
	    else if (inRange)
	    {
		this.active = true;
		this.attackTime = this.attackCooldown;
	    }
	}
    }
}