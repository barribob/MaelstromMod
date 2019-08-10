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
 * Combines melee and ranged ai to create an ai that does both intermittently
 *
 * @param <T>
 *            The entity getting the ai
 */
public class AIMeleeAndRange<T extends EntityCreature & IRangedAttackMob> extends EntityAIBase
{
    private int switchUpdateTime;
    private float switchChance;

    private int switchTimer;

    private EntityAIRangedAttack rangedAttackAI;
    private EntityAIAttackMelee meleeAttackAI;

    private EntityAIBase attackAI;

    private T entity;

    public AIMeleeAndRange(T mob, double speedIn, boolean useLongMemory, double moveSpeedAmp, int attackCooldown, float maxAttackDistance, int switchUpdateTime,
	    float switchChance, float strafeAmount)
    {
	rangedAttackAI = new EntityAIRangedAttack<T>(mob, moveSpeedAmp, attackCooldown, maxAttackDistance, strafeAmount);
	meleeAttackAI = new EntityAIAttackMelee(mob, speedIn, useLongMemory);
	attackAI = meleeAttackAI;
	this.switchUpdateTime = switchUpdateTime;
	this.switchChance = switchChance;
	this.entity = mob;
    }
    
    public boolean isRanged() {
	return attackAI.equals(rangedAttackAI);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
	return attackAI.shouldExecute() && meleeAttackAI.shouldExecute();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
	return attackAI.shouldContinueExecuting() && meleeAttackAI.shouldContinueExecuting();
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
	this.rangedAttackAI.resetTask();
	this.meleeAttackAI.resetTask();
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

	    if (this.entity.world.rand.nextFloat() < this.switchChance)
	    {
		// Switch ai's
		if (attackAI == this.meleeAttackAI)
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
	}

	this.attackAI.updateTask();
    }
}
