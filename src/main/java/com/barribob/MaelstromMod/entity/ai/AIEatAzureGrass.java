package com.barribob.MaelstromMod.entity.ai;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 
 * Same eating grass ai that vanilla animals use except for the azure dimension
 *
 */
public class AIEatAzureGrass extends EntityAIBase
{
    /** The entity owner of this AITask */
    private final EntityLiving grassEaterEntity;
    /** The world the grass eater entity is eating from */
    private final World entityWorld;
    /** Number of ticks since the entity started to eat grass */
    int eatingGrassTimer;

    public AIEatAzureGrass(EntityLiving grassEaterEntityIn)
    {
        this.grassEaterEntity = grassEaterEntityIn;
        this.entityWorld = grassEaterEntityIn.world;
        this.setMutexBits(7);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.grassEaterEntity.getRNG().nextInt(this.grassEaterEntity.isChild() ? 50 : 1000) != 0)
        {
            return false;
        }
        
	BlockPos blockpos = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);

	if (this.entityWorld.getBlockState(blockpos.down()).getBlock() == ModBlocks.AZURE_GRASS || this.entityWorld.getBlockState(blockpos).getBlock() == ModBlocks.BROWNED_GRASS)
	{
	    return true;
	}
	
	return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.eatingGrassTimer = 40;
        this.entityWorld.setEntityState(this.grassEaterEntity, (byte)10);
        this.grassEaterEntity.getNavigator().clearPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        this.eatingGrassTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.eatingGrassTimer > 0;
    }

    /**
     * Number of ticks since the entity started to eat grass
     */
    public int getEatingGrassTimer()
    {
        return this.eatingGrassTimer;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);

        if (this.eatingGrassTimer == 4)
        {
            BlockPos blockpos = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);

            if (this.entityWorld.getBlockState(blockpos).getBlock() == ModBlocks.BROWNED_GRASS)
            {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity))
                {
                    this.entityWorld.destroyBlock(blockpos, false);
                }

                this.grassEaterEntity.eatGrassBonus();
            }
            else
            {
                BlockPos blockpos1 = blockpos.down();

                if (this.entityWorld.getBlockState(blockpos1).getBlock() == ModBlocks.AZURE_GRASS)
                {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity))
                    {
                        this.entityWorld.playEvent(2001, blockpos1, Block.getIdFromBlock(ModBlocks.AZURE_GRASS));
                        this.entityWorld.setBlockState(blockpos1, ModBlocks.DARK_AZURE_STONE.getDefaultState(), 2);
                    }

                    this.grassEaterEntity.eatGrassBonus();
                }
            }
        }
    }
}