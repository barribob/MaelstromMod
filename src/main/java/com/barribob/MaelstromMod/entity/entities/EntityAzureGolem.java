package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.ActionGolemSlam;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.animation.AnimationAzureGolem;
import com.barribob.MaelstromMod.entity.render.RenderAzureGolem;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityAzureGolem extends EntityLeveledMob implements IRangedAttackMob {
    public EntityAzureGolem(World worldIn) {
        super(worldIn);
        this.setSize(1.4F * RenderAzureGolem.AZURE_GOLEM_SIZE, 2.7F * RenderAzureGolem.AZURE_GOLEM_SIZE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void initAnimation() {
        this.currentAnimation = new AnimationAzureGolem();
    }

    @Override
    public float getRenderSizeModifier() {
        return RenderAzureGolem.AZURE_GOLEM_SIZE;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAIRangedAttack<EntityAzureGolem>(this, 1f, 60, 15, 7.0f, 0.1f));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this
     * entity.
     */
    @Override
    public boolean getCanSpawnHere() {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
        return this.world.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS && this.world.getLight(blockpos) > 8 && super.getCanSpawnHere();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_IRONGOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRONGOLEM_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
    }

    @Override
    protected float getSoundPitch() {
        return 0.9f + ModRandom.getFloat(0.1f);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.AZURE_GOLEM;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        new ActionGolemSlam().performAction(this, target);
    }

    @Override
    public void swingArm(EnumHand hand) {
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
        if (swingingArms) {
            this.world.setEntityState(this, (byte) 4);
            this.motionY = 0.63f;
        }
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            this.currentAnimation = new AnimationAzureGolem();
            getCurrentAnimation().startAnimation();
            this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
        } else {
            super.handleStatusUpdate(id);
        }
    }
}
