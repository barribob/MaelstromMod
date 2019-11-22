package com.barribob.MaelstromMod.entity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.barribob.MaelstromMod.entity.action.Action;
import com.barribob.MaelstromMod.entity.action.ActionGroundSlash;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttackNoReset;
import com.barribob.MaelstromMod.entity.animation.AnimationClip;
import com.barribob.MaelstromMod.entity.animation.AnimationMaelstromBeast;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromBeast;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBeastQuake;
import com.barribob.MaelstromMod.entity.util.ComboAttack;
import com.barribob.MaelstromMod.entity.util.LeapingEntity;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityMaelstromBeast extends EntityMaelstromMob implements LeapingEntity
{
    private ComboAttack attackHandler = new ComboAttack();
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_20));
    private byte hammerSwing = 4;
    private byte battleShout = 5;
    private byte groundSlash = 6;
    private byte leap = 7;
    private boolean leaping = false;
    public static final byte explosionParticles = 8;
    private static final DataParameter<Boolean> RAGED = EntityDataManager.<Boolean>createKey(EntityMaelstromBeast.class, DataSerializers.BOOLEAN);
    private byte rageParticles = 9;
    private int ragedAttackDamage = 14;

    public EntityMaelstromBeast(World worldIn)
    {
	super(worldIn);
	this.setSize(1.4f, 2.5f);
	this.experienceValue = ModEntities.BOSS_EXPERIENCE;
	this.setLevel(1.5f);
	if (!world.isRemote)
	{
	    attackHandler.addAttack(hammerSwing, new Action()
	    {
		@Override
		public void performAction(EntityLeveledMob actor, EntityLivingBase target)
		{
		    ModUtils.handleAreaImpact(3, (e) -> actor.getAttack(), actor, actor.getPositionVector().add(ModUtils.getRelativeOffset(actor, new Vec3d(2, 0, 0))),
			    ModDamageSource.causeMaelstromMeleeDamage(actor), 1, 0, false);
		    actor.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F / (actor.getRNG().nextFloat() * 0.4F + 0.8F));
		}
	    });
	    attackHandler.addAttack(battleShout, new Action()
	    {
		@Override
		public void performAction(EntityLeveledMob actor, EntityLivingBase target)
		{
		    ModUtils.handleAreaImpact(20, (e) -> {
			e.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 1));
			return actor.getAttack() * 0.5f;
		    }, actor, actor.getPositionVector(), ModDamageSource.causeMaelstromMeleeDamage(actor), 0, 0, false);
		    actor.playSound(SoundEvents.ENTITY_ENDERDRAGON_GROWL, 1.0F, 0.9F / (actor.getRNG().nextFloat() * 0.4F + 0.8F));
		}
	    });
	    attackHandler.addAttack(groundSlash, new ActionGroundSlash(() -> new ProjectileBeastQuake(worldIn, this, this.getAttack())));
	    attackHandler.addAttack(leap, new Action()
	    {
		@Override
		public void performAction(EntityLeveledMob actor, EntityLivingBase target)
		{
		    Vec3d dir = target.getPositionVector().subtract(actor.getPositionVector()).normalize();
		    Vec3d leap = new Vec3d(dir.x, 0, dir.z).normalize().scale(1.4f).add(ModUtils.yVec(0.7f));
		    actor.motionX = leap.x;
		    actor.motionY = leap.y;
		    actor.motionZ = leap.z;
		}
	    });
	}
    }

    @Override
    protected void initAnimation()
    {
	List<List<AnimationClip<ModelMaelstromBeast>>> animationHammer = new ArrayList<List<AnimationClip<ModelMaelstromBeast>>>();
	List<AnimationClip<ModelMaelstromBeast>> body = new ArrayList<AnimationClip<ModelMaelstromBeast>>();
	List<AnimationClip<ModelMaelstromBeast>> rightArmXStream = new ArrayList<AnimationClip<ModelMaelstromBeast>>();
	List<AnimationClip<ModelMaelstromBeast>> rightArmZStream = new ArrayList<AnimationClip<ModelMaelstromBeast>>();
	List<AnimationClip<ModelMaelstromBeast>> hammerStream = new ArrayList<AnimationClip<ModelMaelstromBeast>>();

	BiConsumer<ModelMaelstromBeast, Float> bodyY = (model, f) -> {
	    model.body.rotateAngleY = f;
	};

	BiConsumer<ModelMaelstromBeast, Float> rightArmX = (model, f) -> {
	    model.rightArm.rotateAngleX = f;
	};

	BiConsumer<ModelMaelstromBeast, Float> rightArmZ = (model, f) -> {
	    model.rightArm.rotateAngleZ = f;
	};

	BiConsumer<ModelMaelstromBeast, Float> hammerX = (model, f) -> {
	    model.hammer_handle.rotateAngleX = f;
	};

	body.add(new AnimationClip(12, 0, -50, bodyY));
	body.add(new AnimationClip(8, -50, -50, bodyY));
	body.add(new AnimationClip(6, -50, 50, bodyY));
	body.add(new AnimationClip(12, 50, 0, bodyY));

	rightArmXStream.add(new AnimationClip(12, 0, -75, rightArmX));
	rightArmXStream.add(new AnimationClip(8, -75, -75, rightArmX));
	rightArmXStream.add(new AnimationClip(6, -75, 75, rightArmX));
	rightArmXStream.add(new AnimationClip(8, 75, 0, rightArmX));

	rightArmZStream.add(new AnimationClip(12, 0, 60, rightArmZ));
	rightArmZStream.add(new AnimationClip(8, 60, 60, rightArmZ));
	rightArmZStream.add(new AnimationClip(6, 60, 60, rightArmZ));
	rightArmZStream.add(new AnimationClip(8, 60, 0, rightArmZ));

	hammerStream.add(new AnimationClip(12, 0, 0, hammerX));
	hammerStream.add(new AnimationClip(8, 0, 0, hammerX));
	hammerStream.add(new AnimationClip(6, 0, 90, hammerX));
	hammerStream.add(new AnimationClip(8, 90, 0, hammerX));

	animationHammer.add(body);
	animationHammer.add(rightArmXStream);
	animationHammer.add(rightArmZStream);
	animationHammer.add(hammerStream);

	List<List<AnimationClip<ModelMaelstromBeast>>> animationShout = new ArrayList<List<AnimationClip<ModelMaelstromBeast>>>();
	List<AnimationClip<ModelMaelstromBeast>> shoutStream = new ArrayList<AnimationClip<ModelMaelstromBeast>>();

	BiConsumer<ModelMaelstromBeast, Float> shout = (model, f) -> {
	    model.body.rotateAngleX = model.defaultBodyRotation + f / 3;
	    model.leftArm.rotateAngleX = f;
	    model.rightArm.rotateAngleX = f;
	    model.lowerJaw.rotateAngleX = -f / 3;
	};

	shoutStream.add(new AnimationClip(20, 0, -130, shout));
	shoutStream.add(new AnimationClip(5, -130, -130, shout));
	shoutStream.add(new AnimationClip(12, -130, 0, shout));

	animationShout.add(shoutStream);

	List<List<AnimationClip<ModelMaelstromBeast>>> animationGroundSlash = new ArrayList<List<AnimationClip<ModelMaelstromBeast>>>();
	rightArmXStream = new ArrayList<AnimationClip<ModelMaelstromBeast>>();
	rightArmZStream = new ArrayList<AnimationClip<ModelMaelstromBeast>>();
	List<AnimationClip<ModelMaelstromBeast>> bodyXStream = new ArrayList<AnimationClip<ModelMaelstromBeast>>();
	hammerStream = new ArrayList<AnimationClip<ModelMaelstromBeast>>();

	BiConsumer<ModelMaelstromBeast, Float> bodyX = (model, f) -> {
	    model.body.rotateAngleX = model.defaultBodyRotation + f;
	};

	rightArmXStream.add(new AnimationClip(18, 0, -130, rightArmX));
	rightArmXStream.add(new AnimationClip(6, -130, -80, rightArmX));
	rightArmXStream.add(new AnimationClip(8, -80, -80, rightArmX));
	rightArmXStream.add(new AnimationClip(8, -80, 0, rightArmX));

	rightArmZStream.add(new AnimationClip(18, 0, 0, rightArmZ));
	rightArmZStream.add(new AnimationClip(6, 0, 20, rightArmZ));
	rightArmZStream.add(new AnimationClip(8, 20, 20, rightArmZ));
	rightArmZStream.add(new AnimationClip(8, 20, 0, rightArmZ));

	bodyXStream.add(new AnimationClip(18, 0, -40, bodyX));
	bodyXStream.add(new AnimationClip(6, -40, 47, bodyX));
	bodyXStream.add(new AnimationClip(8, 47, 47, bodyX));
	bodyXStream.add(new AnimationClip(8, 47, 0, bodyX));

	hammerStream.add(new AnimationClip(18, 0, 0, hammerX));
	hammerStream.add(new AnimationClip(6, 0, 81, hammerX));
	hammerStream.add(new AnimationClip(8, 81, 81, hammerX));
	hammerStream.add(new AnimationClip(8, 81, 0, hammerX));

	animationGroundSlash.add(hammerStream);
	animationGroundSlash.add(rightArmXStream);
	animationGroundSlash.add(rightArmZStream);
	animationGroundSlash.add(bodyXStream);

	List<List<AnimationClip<ModelMaelstromBeast>>> animationLeap = new ArrayList<List<AnimationClip<ModelMaelstromBeast>>>();
	rightArmXStream = new ArrayList<AnimationClip<ModelMaelstromBeast>>();
	rightArmZStream = new ArrayList<AnimationClip<ModelMaelstromBeast>>();
	bodyXStream = new ArrayList<AnimationClip<ModelMaelstromBeast>>();
	hammerStream = new ArrayList<AnimationClip<ModelMaelstromBeast>>();

	rightArmXStream.add(new AnimationClip(24, 0, 0, rightArmX));
	rightArmXStream.add(new AnimationClip(8, 0, -130, rightArmX));
	rightArmXStream.add(new AnimationClip(6, -130, -80, rightArmX));
	rightArmXStream.add(new AnimationClip(8, -80, -80, rightArmX));
	rightArmXStream.add(new AnimationClip(8, -80, 0, rightArmX));

	rightArmZStream.add(new AnimationClip(24, 0, 0, rightArmZ));
	rightArmZStream.add(new AnimationClip(8, 0, 0, rightArmZ));
	rightArmZStream.add(new AnimationClip(6, 0, 20, rightArmZ));
	rightArmZStream.add(new AnimationClip(8, 20, 20, rightArmZ));
	rightArmZStream.add(new AnimationClip(8, 20, 0, rightArmZ));

	bodyXStream.add(new AnimationClip(16, 0, 50, bodyX));
	bodyXStream.add(new AnimationClip(8, 50, 50, bodyX));
	bodyXStream.add(new AnimationClip(8, 50, -40, bodyX));
	bodyXStream.add(new AnimationClip(6, -40, 47, bodyX));
	bodyXStream.add(new AnimationClip(8, 47, 47, bodyX));
	bodyXStream.add(new AnimationClip(8, 47, 0, bodyX));

	hammerStream.add(new AnimationClip(24, 0, 0, hammerX));
	hammerStream.add(new AnimationClip(8, 0, 0, hammerX));
	hammerStream.add(new AnimationClip(6, 0, 81, hammerX));
	hammerStream.add(new AnimationClip(8, 81, 81, hammerX));
	hammerStream.add(new AnimationClip(8, 81, 0, hammerX));

	animationLeap.add(hammerStream);
	animationLeap.add(rightArmXStream);
	animationLeap.add(rightArmZStream);
	animationLeap.add(bodyXStream);

	attackHandler.addAttack(hammerSwing, Action.NONE, () -> new AnimationMaelstromBeast(animationHammer));
	attackHandler.addAttack(battleShout, Action.NONE, () -> new AnimationMaelstromBeast(animationShout));
	attackHandler.addAttack(groundSlash, Action.NONE, () -> new AnimationMaelstromBeast(animationGroundSlash));
	attackHandler.addAttack(leap, Action.NONE, () -> new AnimationMaelstromBeast(animationLeap));

	currentAnimation = new AnimationMaelstromBeast(new ArrayList<List<AnimationClip<ModelMaelstromBeast>>>());
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(9);
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(400);

    }

    @Override
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttackNoReset<EntityMaelstromMob>(this, 1.25f, 50, 20, 9.0f, 0.5f));
    }

    @Override
    protected boolean canDespawn()
    {
	return false;
    }

    @Override
    public boolean isLeaping()
    {
	return leaping;
    }

    @Override
    public void setLeaping(boolean leaping)
    {
	this.leaping = leaping;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	this.attackHandler.getCurrentAttackAction().performAction(this, target);
	if (attackHandler.getCurrentAttack() == leap)
	{
	    leaping = true;
	}
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {
	super.setSwingingArms(swingingArms);
	if (this.isSwingingArms())
	{
	    Byte[] attack = { groundSlash, groundSlash, battleShout };
	    attackHandler.setCurrentAttack(ModRandom.choice(attack));
	    if (this.getAttackTarget() != null && this.getDistance(this.getAttackTarget()) > 8)
	    {
		attackHandler.setCurrentAttack(leap);
	    }
	    if (this.getAttackTarget() != null && this.getDistance(this.getAttackTarget()) < 4)
	    {
		attackHandler.setCurrentAttack(hammerSwing);
	    }
	    world.setEntityState(this, attackHandler.getCurrentAttack());
	}
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	if (this.isRaged())
	{
	    world.setEntityState(this, rageParticles);
	}
    }

    @Override
    public void handleStatusUpdate(byte id)
    {
	if (id >= 4 && id <= 7)
	{
	    currentAnimation = attackHandler.getAnimation(id);
	    getCurrentAnimation().startAnimation();
	}
	else if (id == explosionParticles)
	{
	    ModUtils.performNTimes(100, (i) -> {
		ParticleManager.spawnMaelstromExplosion(world, rand, ModRandom.randVec().scale(5).add(getPositionVector()));
	    });
	}
	else if (id == rageParticles)
	{
	    ParticleManager.spawnEffect(world, getPositionVector().add(ModRandom.randVec().scale(2)).add(ModUtils.yVec(this.getEyeHeight())), ModColors.RED);
	}
	super.handleStatusUpdate(id);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
	float prevHealth = this.getHealth();
	boolean flag = super.attackEntityFrom(source, amount);
	if (prevHealth > this.getMaxHealth() * 0.25f && this.getHealth() <= this.getMaxHealth() * 0.25f)
	{
	    this.dataManager.set(RAGED, true);
	    this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.ragedAttackDamage);
	}
	return flag;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
	if (this.hasCustomName())
	{
	    this.bossInfo.setName(this.getDisplayName());
	}

	if (compound.hasKey("rage"))
	{
	    this.dataManager.set(RAGED, Boolean.valueOf(compound.getBoolean("rage")));
	    if (this.isRaged())
	    {
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.ragedAttackDamage);
	    }
	}

	super.readEntityFromNBT(compound);

    }

    public boolean isRaged()
    {
	return this.dataManager.get(RAGED).booleanValue();
    }

    @Override
    protected void entityInit()
    {
	super.entityInit();
	this.dataManager.register(RAGED, Boolean.valueOf(false));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
	compound.setBoolean("rage", this.isRaged());

	super.writeEntityToNBT(compound);
    }

    @Override
    public void setCustomNameTag(String name)
    {
	super.setCustomNameTag(name);
	this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    protected void updateAITasks()
    {
	this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	super.updateAITasks();
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player)
    {
	super.addTrackingPlayer(player);
	this.bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
	super.removeTrackingPlayer(player);
	this.bossInfo.removePlayer(player);
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
	return SoundEvents.ENTITY_POLAR_BEAR_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundEvents.ENTITY_POLAR_BEAR_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
	return SoundEvents.ENTITY_POLAR_BEAR_DEATH;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
	return LootTableHandler.BEAST;
    }

    @Override
    protected float getSoundPitch()
    {
	return super.getSoundPitch() * 0.8f;
    }

    @Override
    protected float getSoundVolume()
    {
	return 0.6F;
    }

    @Override
    public void onStopLeaping()
    {
	ModUtils.handleAreaImpact(5, (e) -> this.getAttack(), this, this.getPositionVector(), ModDamageSource.causeMaelstromExplosionDamage(this));
	this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f + ModRandom.getFloat(0.1f));
	this.world.setEntityState(this, this.explosionParticles);
    }
}
