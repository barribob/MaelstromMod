package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.AIMeleeAndRange;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.animation.AnimationOpenJaws;
import com.barribob.MaelstromMod.entity.projectile.ProjectileSwampSpittle;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySwampCrawler extends EntityLeveledMob implements IRangedAttackMob
{
    private AIMeleeAndRange attackAI;

    public EntitySwampCrawler(World worldIn)
    {
	super(worldIn);
	this.setSize(1.3f, 1.3f);
	this.setLevel(2.0f);
	this.currentAnimation = new AnimationOpenJaws();
    }

    @Override
    protected void updateAttributes()
    {
	this.setBaseMaxHealth(25);
	this.setBaseAttack(5);
    }

    protected void initEntityAI()
    {
	this.tasks.addTask(0, new EntityAISwimming(this));
	this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.5F)
	{
	    @Override
	    public boolean shouldExecute()
	    {
		return super.shouldExecute() && attackAI != null && !attackAI.isRanged();
	    }

	    @Override
	    public void startExecuting()
	    {
		world.setEntityState(EntitySwampCrawler.this, (byte) 4);
		super.startExecuting();
	    }
	});
	this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
	this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
	this.tasks.addTask(8, new EntityAILookIdle(this));
	this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
	this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	attackAI = new AIMeleeAndRange<EntitySwampCrawler>(this, 1.0f, true, 1.0f, 30, 8.0f, 100, 1.0f, 0.1f);
	this.tasks.addTask(4, attackAI);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
	if (entityIn instanceof EntityLivingBase)
	{
	    entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttack());
	    return true;
	}
	return false;
    }

    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
    }

    public float getEyeHeight()
    {
	return 0.65F;
    }

    protected SoundEvent getSwimSound()
    {
	return SoundEvents.ENTITY_HOSTILE_SWIM;
    }

    protected SoundEvent getSplashSound()
    {
	return SoundEvents.ENTITY_HOSTILE_SPLASH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundsHandler.ENTTIY_CRAWLER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
	return SoundsHandler.ENTTIY_CRAWLER_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
	return SoundsHandler.ENTTIY_CRAWLER_AMBIENT;
    }

    @Override
    protected Item getDropItem()
    {
        return ModItems.SWAMP_SLIME;
    }

    public boolean getCanSpawnHere()
    {
	return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && super.getCanSpawnHere();
    }

    public void onUpdate()
    {
	super.onUpdate();

	if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
	{
	    this.setDead();
	}
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	ModUtils.throwProjectile(this, target, new ProjectileSwampSpittle(world, this, this.getAttack()));
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {
	if (swingingArms)
	{
	    this.world.setEntityState(this, (byte) 4);
	}
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == 4)
	{
	    this.currentAnimation.startAnimation();
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }
}
