package com.barribob.MaelstromMod.entity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.ai.AIFlyingRangedAttack;
import com.barribob.MaelstromMod.entity.ai.AILookAround;
import com.barribob.MaelstromMod.entity.ai.AIRandomFly;
import com.barribob.MaelstromMod.entity.ai.FlyingMoveHelper;
import com.barribob.MaelstromMod.entity.animation.Animation;
import com.barribob.MaelstromMod.entity.animation.AnimationClip;
import com.barribob.MaelstromMod.entity.animation.AnimationNone;
import com.barribob.MaelstromMod.entity.animation.StreamAnimation;
import com.barribob.MaelstromMod.entity.model.ModelCliffFly;
import com.barribob.MaelstromMod.entity.projectile.ProjectileSwampSpittle;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.IAnimatedMob;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCliffFly extends EntityFlying implements IMob, IRangedAttackMob, IAnimatedMob
{
    @SideOnly(Side.CLIENT)
    protected Animation currentAnimation;
    private float level;
    private byte animationByte = 13;

    public EntityCliffFly(World worldIn)
    {
	super(worldIn);
	this.moveHelper = new FlyingMoveHelper(this);
	this.level = 2;
    }

    @Override
    public void onLivingUpdate()
    {
	super.onLivingUpdate();

	if (world.isRemote && currentAnimation != null)
	{
	    currentAnimation.update();
	}
    }

    @Override
    protected void initEntityAI()
    {
	this.tasks.addTask(5, new AIRandomFly(this));
	this.tasks.addTask(7, new AILookAround(this));
	this.tasks.addTask(7, new AIFlyingRangedAttack(this, 40, 20, 30, 1.0f));
	this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
	this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4);
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();

	if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
	{
	    this.setDead();
	}

	/**
	 * Periodically check if the animations need to be reinitialized
	 */
	if (this.ticksExisted % 20 == 0)
	{
	    world.setEntityState(this, animationByte);
	}
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	for (int i = 0; i < 5; i++)
	{
	    ModUtils.throwProjectile(this, target, new ProjectileSwampSpittle(world, this, this.getAttack()));
	    this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	}
    }

    public float getAttack()
    {
	return (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * LevelHandler.getMultiplierFromLevel(this.level)
		* ModConfig.balance.mob_damage;
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {

    }

    @Override
    protected SoundEvent getAmbientSound()
    {
	return SoundsHandler.ENTITY_BEAST_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundsHandler.ENTITY_BEAST_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
	return SoundsHandler.ENTITY_BEAST_HURT;
    }

    @Override
    protected Item getDropItem()
    {
	return ModItems.FLY_WINGS;
    }

    @Override
    protected float getSoundVolume()
    {
	return 0.3f;
    }

    @Override
    protected float getSoundPitch()
    {
	return super.getSoundPitch() * 1.5f;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == animationByte && currentAnimation == null)
	{
	    List<List<AnimationClip<ModelCliffFly>>> animationWings = new ArrayList<List<AnimationClip<ModelCliffFly>>>();
	    List<AnimationClip<ModelCliffFly>> wings = new ArrayList<AnimationClip<ModelCliffFly>>();
	    BiConsumer<ModelCliffFly, Float> wingsY = (model, f) -> {
		model.leftFrontWing.rotateAngleY = -f;
		model.leftFrontWing1.rotateAngleY = -f;
		model.rightFrontWing.rotateAngleY = f;
		model.rightFrontWing2.rotateAngleY = f;

		model.rightBackWing.rotateAngleY = -f;
		model.rightBackWing2.rotateAngleY = -f;
		model.leftBackWing.rotateAngleY = f;
		model.leftBackWing2.rotateAngleY = f;
	    };

	    wings.add(new AnimationClip(2, 0, 30, wingsY));
	    wings.add(new AnimationClip(4, 30, -30, wingsY));
	    wings.add(new AnimationClip(2, -30, 0, wingsY));

	    animationWings.add(wings);

	    currentAnimation = new StreamAnimation(animationWings).loop(true);
	    this.currentAnimation.startAnimation();
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
	world.setEntityState(this, animationByte);

	super.readFromNBT(compound);
    }

    @Override
    public Animation getCurrentAnimation()
    {
	return this.currentAnimation == null ? new AnimationNone() : this.currentAnimation;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
	if (!source.isUnblockable())
	{
	    amount = amount * LevelHandler.getArmorFromLevel(level - 1);
	}
	return super.attackEntityFrom(source, amount);
    }
}
