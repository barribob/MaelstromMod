package com.barribob.MaelstromMod.entity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.barribob.MaelstromMod.entity.action.Action;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttackNoReset;
import com.barribob.MaelstromMod.entity.animation.AnimationClip;
import com.barribob.MaelstromMod.entity.animation.StreamAnimation;
import com.barribob.MaelstromMod.entity.model.ModelChaosKnight;
import com.barribob.MaelstromMod.entity.util.ComboAttack;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityChaosKnight extends EntityMaelstromMob
{
    private ComboAttack attackHandler = new ComboAttack();
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_6));
    public static final byte sideSwipe = 4;

    public EntityChaosKnight(World worldIn)
    {
	super(worldIn);
	this.setSize(1.5f, 3.0f);
	this.healthScaledAttackFactor = 0.2;
	this.experienceValue = ModEntities.BOSS_EXPERIENCE;
	if (!world.isRemote)
	{
	    attackHandler.setAttack(sideSwipe, new Action()
	    {
		@Override
		public void performAction(EntityLeveledMob actor, EntityLivingBase target)
		{
		    Vec3d offset = actor.getPositionVector().add(ModUtils.getRelativeOffset(actor, new Vec3d(0.5, 1, -1)));
		    ModUtils.handleAreaImpact(2, (e) -> actor.getAttack(), actor, offset, ModDamageSource.causeElementalMeleeDamage(actor, actor.getElement()), 0.5f, 0, false);
		    actor.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F / (actor.getRNG().nextFloat() * 0.4F + 0.8F));
		    Vec3d jump = actor.getLookVec().rotateYaw(180).scale(0.6f);
		    actor.motionX = jump.x;
		    actor.motionY = 0.4;
		    actor.motionZ = jump.z;
		    addEvent(() -> EntityChaosKnight.super.setSwingingArms(false), 15);
		}
	    });
	}
    }

    @Override
    protected void initAnimation()
    {
	BiConsumer<ModelChaosKnight, Float> leftArmX = (model, f) -> model.leftShoulder.rotateAngleX = -f;
	BiConsumer<ModelChaosKnight, Float> elbowZ = (model, f) -> model.leftArm2.rotateAngleZ = f;

	AnimationClip<ModelChaosKnight> shieldUpLeftArmX = new AnimationClip<ModelChaosKnight>(5, 0, -90, leftArmX);
	AnimationClip<ModelChaosKnight> shieldUpElbowZ = new AnimationClip<ModelChaosKnight>(5, 0, -90, elbowZ);

	AnimationClip<ModelChaosKnight> shieldDownLeftArmX = new AnimationClip<ModelChaosKnight>(5, -90, 0, leftArmX);
	AnimationClip<ModelChaosKnight> shieldDownElbowZ = new AnimationClip<ModelChaosKnight>(5, -90, 0, elbowZ);

	List<List<AnimationClip<ModelChaosKnight>>> animationSlash = new ArrayList<List<AnimationClip<ModelChaosKnight>>>();
	List<AnimationClip<ModelChaosKnight>> bodyYStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	List<AnimationClip<ModelChaosKnight>> bodyXStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	List<AnimationClip<ModelChaosKnight>> rightArmXStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	List<AnimationClip<ModelChaosKnight>> rightArmZStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	List<AnimationClip<ModelChaosKnight>> axeStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	List<AnimationClip<ModelChaosKnight>> leftArmXStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	List<AnimationClip<ModelChaosKnight>> elbowZStream = new ArrayList<AnimationClip<ModelChaosKnight>>();

	BiConsumer<ModelChaosKnight, Float> none = (model, f) -> {
	};
	BiConsumer<ModelChaosKnight, Float> bodyY = (model, f) -> model.Chest1.rotateAngleY = f;
	BiConsumer<ModelChaosKnight, Float> bodyX = (model, f) -> model.Chest1.rotateAngleX = -f;
	BiConsumer<ModelChaosKnight, Float> rightArmX = (model, f) -> model.rightShoulder.rotateAngleX = -f;
	BiConsumer<ModelChaosKnight, Float> rightArmZ = (model, f) -> model.rightShoulder.rotateAngleZ = f;
	BiConsumer<ModelChaosKnight, Float> axeX = (model, f) -> model.axe0.rotateAngleX = -f;

	bodyYStream.add(new AnimationClip<ModelChaosKnight>(4, 0, 0, none));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(10, -180, -195, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(4, -195, -195, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(4, -195, -145, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(4, -145, -145, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(6, -145, -180, bodyY));

	bodyXStream.add(new AnimationClip<ModelChaosKnight>(4, 0, 0, none));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(10, 0, 20, bodyX));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(12, 20, 20, bodyX));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(6, 20, 0, bodyX));

	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(4, 0, 0, none));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(10, 0, -100, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(4, -100, -100, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(4, -100, 0, rightArmX));

	rightArmZStream.add(new AnimationClip<ModelChaosKnight>(4, 0, 0, none));
	rightArmZStream.add(new AnimationClip<ModelChaosKnight>(10, 0, -80, rightArmZ));
	rightArmZStream.add(new AnimationClip<ModelChaosKnight>(12, -80, -80, rightArmZ));
	rightArmZStream.add(new AnimationClip<ModelChaosKnight>(6, -80, 0, rightArmZ));

	axeStream.add(new AnimationClip<ModelChaosKnight>(18, 0, 0, none));
	axeStream.add(new AnimationClip<ModelChaosKnight>(4, 0, 45, axeX));
	axeStream.add(new AnimationClip<ModelChaosKnight>(4, 45, 45, axeX));
	axeStream.add(new AnimationClip<ModelChaosKnight>(6, 45, 0, axeX));

	leftArmXStream.add(shieldDownLeftArmX);
	leftArmXStream.add(new AnimationClip<ModelChaosKnight>(27, 0, 0, leftArmX));
	leftArmXStream.add(shieldUpLeftArmX);

	elbowZStream.add(shieldDownElbowZ);
	elbowZStream.add(new AnimationClip<ModelChaosKnight>(27, 0, 0, elbowZ));
	elbowZStream.add(shieldUpElbowZ);

	animationSlash.add(axeStream);
	animationSlash.add(bodyYStream);
	animationSlash.add(bodyXStream);
	animationSlash.add(rightArmXStream);
	animationSlash.add(rightArmZStream);
	animationSlash.add(leftArmXStream);
	animationSlash.add(elbowZStream);

	attackHandler.setAttack(sideSwipe, Action.NONE, () -> new StreamAnimation(animationSlash));
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(9f);
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300);
	this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
	if (amount > 0.0F && this.canBlockDamageSource(source))
	{
	    this.damageShield(amount);
	    amount = 0.0F;

	    if (!source.isProjectile())
	    {
		Entity entity = source.getImmediateSource();

		if (entity instanceof EntityLivingBase)
		{
		    this.blockUsingShield((EntityLivingBase) entity);
		}
	    }
	    this.playSound(SoundsHandler.ENTITY_CHAOS_KNIGHT_BLOCK, 1.0f, 0.9f + ModRandom.getFloat(0.2f));

	    return false;
	}
	return super.attackEntityFrom(source, amount);
    }

    /**
     * Determines whether the entity can block the damage source based on the damage
     * source's location, whether the damage source is blockable, and whether the
     * entity is blocking.
     */
    private boolean canBlockDamageSource(DamageSource damageSourceIn)
    {
	if (!damageSourceIn.isUnblockable() && !this.isSwingingArms())
	{
	    Vec3d vec3d = damageSourceIn.getDamageLocation();

	    if (vec3d != null)
	    {
		Vec3d vec3d1 = this.getLook(1.0F);
		Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(this.posX, this.posY, this.posZ)).normalize();
		vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);

		if (vec3d2.dotProduct(vec3d1) < 0.0D)
		{
		    return true;
		}
	    }
	}

	return false;
    }

    @Override
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttackNoReset<EntityChaosKnight>(this, 1.0f, 90, 20, 15, 0.5f));
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {
	if (swingingArms)
	{
	    super.setSwingingArms(true);
	    Byte[] attack = { sideSwipe };
	    attackHandler.setCurrentAttack(ModRandom.choice(attack));
	    world.setEntityState(this, attackHandler.getCurrentAttack());
	    this.addEvent(() -> {
		if (this.getAttackTarget() != null)
		{
		    float distance = this.getDistance(this.getAttackTarget());
		    if (distance > 3)
		    {
			ModUtils.leapTowards(this, this.getAttackTarget().getPositionVector(), (float) (0.4 * Math.sqrt(distance)), 0.5f);
		    }
		}
	    }, 5);
	}
    }

    @Override
    public void handleStatusUpdate(byte id)
    {
	if (id == sideSwipe)
	{
	    currentAnimation = attackHandler.getAnimation(id);
	    getCurrentAnimation().startAnimation();
	}
	super.handleStatusUpdate(id);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	this.attackHandler.getCurrentAttackAction().performAction(this, target);
    }

}
