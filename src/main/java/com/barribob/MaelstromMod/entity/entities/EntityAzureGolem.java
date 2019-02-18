package com.barribob.MaelstromMod.entity.entities;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileQuake;
import com.barribob.MaelstromMod.entity.render.RenderAzureGolem;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.AnimationTimer;
import com.barribob.MaelstromMod.util.ModRandom;
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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAzureGolem extends EntityLeveledMob implements IRangedAttackMob
{
    private AnimationTimer animation;
    
    // Animation frames for the pound attack
    private static float[] armFramesDegrees = { 0, -15, -30, -45, -60, -75, -90, -90, -90, -90, -90, -105, -135, -165, -180, -180, -180, -180, -165, -150, -135, -120,
	    -105, -90, -75, -60, -45 };
    private static float[] backFramesDegrees = { 0, 10, 20, 30, 40, 50, 65, 65, 65, 65, 65, 55, 45, 30, 15, 0, -15, -15, -15, -15, -15, -15, -5, 0, 0, 0, 0 };

    public EntityAzureGolem(World worldIn)
    {
	super(worldIn);
	this.setLevel(2);
	animation = new AnimationTimer(armFramesDegrees.length);
        this.setSize(1.4F * RenderAzureGolem.AZURE_GOLEM_SIZE, 2.7F * RenderAzureGolem.AZURE_GOLEM_SIZE);
    }
    
    @Override
    public float getRenderSizeModifier()
    {
        return RenderAzureGolem.AZURE_GOLEM_SIZE;
    }

    @Override
    protected void updateAttributes()
    {
	this.setBaseAttack(9f);
	this.setBaseMaxHealth(125);
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
	this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttack<EntityAzureGolem>(this, 1f, 60, armFramesDegrees.length - 12, 7.0f, 0.1f));
	this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.6D));
	this.tasks.addTask(6, new EntityAILookIdle(this));
	this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
    
    /**
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
        return this.world.getBlockState(blockpos.down()).getBlock() == ModBlocks.AZURE_GRASS && this.world.getLight(blockpos) > 8 && super.getCanSpawnHere();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundEvents.ENTITY_IRONGOLEM_HURT;
    }

    protected SoundEvent getDeathSound()
    {
	return SoundEvents.ENTITY_IRONGOLEM_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
	this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
    }
    
    @Override
    protected float getSoundPitch()
    {
        return 0.9f + ModRandom.getFloat(0.2f);
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
	return LootTableHandler.AZURE_GOLEM;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	float inaccuracy = 0.0f;
	float speed = 2f;
	float pitch = 0; // Projectiles aim straight ahead always

	// Shoots projectiles in a small arc
	for (int i = 0; i < 5; i++)
	{
	    ProjectileQuake projectile = new ProjectileQuake(world, this, this.getAttack(), (ItemStack) null);
	    projectile.setPosition(this.posX, this.posY, this.posZ);
	    projectile.shoot(this, pitch, this.rotationYaw - 20 + (i * 10), 0.0F, speed, inaccuracy);
	    projectile.setTravelRange(8f);
	    world.spawnEntity(projectile);
	}
    }

    public void onLivingUpdate()
    {
	super.onLivingUpdate();

	animation.nextFrame();
    }

    @SideOnly(Side.CLIENT)
    public float getWaistRotation()
    {
	float degree = animation.getFrame(backFramesDegrees);
	return (float) Math.toRadians(degree);
    }

    @SideOnly(Side.CLIENT)
    public float getArmsRotation()
    {
	float degree = animation.getFrame(armFramesDegrees);
	return (float) Math.toRadians(degree);
    }

    @Override
    public void swingArm(EnumHand hand)
    {
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {
	if (swingingArms)
	{
	    this.world.setEntityState(this, (byte) 4);
	    this.motionY = 0.63f;
	}
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == 4)
	{
	    animation.startAnimation();
	    this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }
}
