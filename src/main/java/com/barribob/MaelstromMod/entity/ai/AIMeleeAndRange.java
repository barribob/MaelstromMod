package com.barribob.MaelstromMod.entity.ai;

import java.util.Random;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Combines melee and ranged ai to create an ai that does both itermittently
 *
 * @param <T> The entity getting the ai
 */
public class AIMeleeAndRange<T extends EntityCreature & IRangedAttackMob> extends EntityAIBase
{
    private int switchUpdateTime;
    private float chanceForRanged;

    private int switchTimer;

    private EntityAIRangedAttack rangedAttackAI;
    private EntityAIAttackMelee meleeAttackAI;

    private EntityAIBase attackAI;

    private T entity;

    public AIMeleeAndRange(T mob, double speedIn, boolean useLongMemory, double moveSpeedAmp, int attackCooldown, float maxAttackDistance,
	    int switchUpdateTime, float chanceForRanged)
    {
	rangedAttackAI = new EntityAIRangedAttack<T>(mob, moveSpeedAmp, attackCooldown, maxAttackDistance);
	meleeAttackAI = new EntityAIAttackMelee(mob, speedIn, useLongMemory);
	attackAI = meleeAttackAI;
	this.switchUpdateTime = switchUpdateTime;
	this.chanceForRanged = chanceForRanged;
	this.entity = mob;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
	return attackAI.shouldExecute();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
	return attackAI.shouldContinueExecuting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
	attackAI.startExecuting();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by
     * another one
     */
    public void resetTask()
    {
	attackAI.resetTask();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
	this.switchTimer--;
	if (this.switchTimer <= 0)
	{
	    this.switchTimer = this.switchUpdateTime;

	    // Switch ai's
	    if (this.entity.world.rand.nextFloat() < this.chanceForRanged)
	    {
		attackAI = this.rangedAttackAI;
		this.entity.world.setEntityState(this.entity, (byte) 4);
	    }
	    else
	    {
		attackAI = this.meleeAttackAI;
		this.entity.world.setEntityState(this.entity, (byte) 5);
	    }
	}

	this.attackAI.updateTask();
    }
}
