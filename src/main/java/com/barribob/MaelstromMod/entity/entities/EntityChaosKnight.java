package com.barribob.MaelstromMod.entity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.action.Action;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttackNoReset;
import com.barribob.MaelstromMod.entity.animation.AnimationClip;
import com.barribob.MaelstromMod.entity.animation.StreamAnimation;
import com.barribob.MaelstromMod.entity.model.ModelChaosKnight;
import com.barribob.MaelstromMod.entity.util.ComboAttack;
import com.barribob.MaelstromMod.entity.util.LeapingEntity;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.packets.MessageMonolithLazer;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityChaosKnight extends EntityMaelstromMob implements LeapingEntity
{
    private ComboAttack attackHandler = new ComboAttack();
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_6));
    public static final byte sideSwipe = 4;
    public static final byte leapSlam = 5;
    public static final byte thunderCharge = 6;
    public static final byte spinSlash = 7;
    private boolean leaping = false;
    private Vec3d chargeDir;
    private static final float dashRadius = 2;

    public EntityChaosKnight(World worldIn)
    {
	super(worldIn);
	this.setSize(1.5f, 3.0f);
	this.healthScaledAttackFactor = 0.2;
	this.experienceValue = ModEntities.BOSS_EXPERIENCE;
	if (!world.isRemote)
	{
	    attackHandler.setAttack(sideSwipe, (EntityLeveledMob actor, EntityLivingBase target) -> {
		Vec3d offset = actor.getPositionVector().add(ModUtils.getRelativeOffset(actor, new Vec3d(0.5, 1, -1)));
		ModUtils.handleAreaImpact(2, (e) -> actor.getAttack(), actor, offset, ModDamageSource.causeElementalMeleeDamage(actor, actor.getElement()), 0.5f, 0, false);
		actor.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F / (actor.getRNG().nextFloat() * 0.4F + 0.8F));
		Vec3d jump = actor.getLookVec().rotateYaw(180).scale(0.6f);
		actor.motionX = jump.x;
		actor.motionY = 0.4;
		actor.motionZ = jump.z;
		addEvent(() -> EntityChaosKnight.super.setSwingingArms(false), 15);
	    });
	    attackHandler.setAttack(leapSlam, (EntityLeveledMob actor, EntityLivingBase target) -> {
		ModUtils.leapTowards(actor, target.getPositionVector(), (float) (0.4f * Math.sqrt(actor.getDistance(target))), 1.0f);
		actor.fallDistance = -3;
		leaping = true;
		addEvent(() -> EntityChaosKnight.super.setSwingingArms(false), 36);
	    });
	    attackHandler.setAttack(thunderCharge, (EntityLeveledMob actor, EntityLivingBase target) -> {
		world.createExplosion(actor, actor.posX, actor.posY, actor.posZ, 2, false);
		ModUtils.lineCallback(actor.getPositionVector(), chargeDir, (int) Math.sqrt(chargeDir.subtract(actor.getPositionVector()).lengthSquared()), (vec, i) -> {
		    ModUtils.handleAreaImpact(dashRadius, (e) -> actor.getAttack(), actor, vec, ModDamageSource.causeElementalMeleeDamage(actor, actor.getElement()), 0.3f, 5);
		    world.playSound(vec.x, vec.y, vec.z, SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.HOSTILE, 1.0f, 1.0f + ModRandom.getFloat(0.1f), false);
		});
		actor.attemptTeleport(chargeDir.x, chargeDir.y, chargeDir.z);
		world.setEntityState(actor, ModUtils.SECOND_PARTICLE_BYTE);
		addEvent(() -> EntityChaosKnight.super.setSwingingArms(false), 25);
		actor.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 1.0f, 1.0f + ModRandom.getFloat(0.1f));
	    });
	    attackHandler.setAttack(spinSlash, (EntityLeveledMob actor, EntityLivingBase target) -> {
		Runnable meleeAttack = () -> {
		    ModUtils.handleAreaImpact(2.8f, (e) -> actor.getAttack(), actor, actor.getPositionVector().add(ModUtils.yVec(1)), ModDamageSource.causeElementalMeleeDamage(actor, actor.getElement()), 0.5f, 0, false);
		    actor.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F / (actor.getRNG().nextFloat() * 0.4F + 0.8F));
		};
		Runnable leap = () -> ModUtils.leapTowards(actor, target.getPositionVector(), (float) (0.4f * Math.sqrt(actor.getDistance(target))), 0.4f);

		ModUtils.leapTowards(actor, target.getPositionVector(), (float) (0.4f * Math.sqrt(actor.getDistance(target))), 0.5f);
		addEvent(meleeAttack, 10);
		addEvent(leap, 13);
		addEvent(meleeAttack, 23);
		addEvent(leap, 26);
		addEvent(meleeAttack, 36);
		addEvent(() -> EntityChaosKnight.super.setSwingingArms(false), 50);
	    });
	}
    }

    @Override
    protected void initAnimation()
    {
	BiConsumer<ModelChaosKnight, Float> leftArmX = (model, f) -> model.leftShoulder.rotateAngleX = -f;
	BiConsumer<ModelChaosKnight, Float> elbowZ = (model, f) -> model.leftArm2.rotateAngleZ = f;
	BiConsumer<ModelChaosKnight, Float> none = (model, f) -> {
	};
	BiConsumer<ModelChaosKnight, Float> bodyY = (model, f) -> model.Chest1.rotateAngleY = -f;
	BiConsumer<ModelChaosKnight, Float> bodyX = (model, f) -> model.Chest1.rotateAngleX = -f;
	BiConsumer<ModelChaosKnight, Float> rightArmX = (model, f) -> model.rightShoulder.rotateAngleX = -f;
	BiConsumer<ModelChaosKnight, Float> rightArmZ = (model, f) -> model.rightShoulder.rotateAngleZ = f;
	BiConsumer<ModelChaosKnight, Float> rightArmY = (model, f) -> model.rightShoulder.rotateAngleY = -f;
	BiConsumer<ModelChaosKnight, Float> axeX = (model, f) -> model.axe0.rotateAngleX = -f;
	BiConsumer<ModelChaosKnight, Float> rootX = (model, f) -> model.root.rotateAngleX = -f;
	BiConsumer<ModelChaosKnight, Float> rootY = (model, f) -> model.root.rotateAngleY = -f;

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

	bodyYStream.add(new AnimationClip<ModelChaosKnight>(4, 0, 0, none));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(10, 180, 195, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(4, 195, 195, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(4, 195, 145, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(4, 145, 145, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(6, 145, 180, bodyY));

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

	List<List<AnimationClip<ModelChaosKnight>>> animationLeapSlam = new ArrayList<List<AnimationClip<ModelChaosKnight>>>();
	List<AnimationClip<ModelChaosKnight>> rootXStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	bodyXStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	leftArmXStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	rightArmXStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	List<AnimationClip<ModelChaosKnight>> rightArmYStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	axeStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	elbowZStream = new ArrayList<AnimationClip<ModelChaosKnight>>();

	bodyXStream.add(new AnimationClip<ModelChaosKnight>(10, 0, 0, none));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(5, 0, -50, bodyX));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(5, -50, -50, bodyX));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(3, -50, 0, bodyX));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(5, 0, 50, bodyX));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(25, 50, 50, bodyX));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(5, 50, 0, bodyX));

	rootXStream.add(new AnimationClip<ModelChaosKnight>(23, 0, 0, rootX));
	rootXStream.add(new AnimationClip<ModelChaosKnight>(20, 0, -720, rootX));

	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(5, 0, 0, none));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(7, 0, -180, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(5, -180, -180, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(5, -180, 0, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(10, 0, -180, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(10, -180, -180, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(5, -180, -75, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(10, -75, -75, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(5, -75, 0, rightArmX));

	rightArmYStream.add(new AnimationClip<ModelChaosKnight>(30, 0, 0, rightArmY));
	rightArmYStream.add(new AnimationClip<ModelChaosKnight>(15, 0, 15, rightArmY));
	rightArmYStream.add(new AnimationClip<ModelChaosKnight>(5, 15, 15, rightArmY));
	rightArmYStream.add(new AnimationClip<ModelChaosKnight>(5, 15, 0, rightArmY));

	axeStream.add(new AnimationClip<ModelChaosKnight>(40, 0, 0, none));
	axeStream.add(new AnimationClip<ModelChaosKnight>(5, 0, 45, axeX));
	axeStream.add(new AnimationClip<ModelChaosKnight>(5, 45, 45, axeX));
	axeStream.add(new AnimationClip<ModelChaosKnight>(5, 45, 0, axeX));

	leftArmXStream.add(shieldDownLeftArmX);
	leftArmXStream.add(new AnimationClip<ModelChaosKnight>(7, 0, -180, leftArmX));
	leftArmXStream.add(new AnimationClip<ModelChaosKnight>(5, -180, -180, leftArmX));
	leftArmXStream.add(new AnimationClip<ModelChaosKnight>(5, -180, 0, leftArmX));
	leftArmXStream.add(new AnimationClip<ModelChaosKnight>(30, 0, 0, leftArmX));
	leftArmXStream.add(shieldUpLeftArmX);

	elbowZStream.add(shieldDownElbowZ);
	elbowZStream.add(new AnimationClip<ModelChaosKnight>(50, 0, 0, elbowZ));
	elbowZStream.add(shieldUpElbowZ);

	animationLeapSlam.add(rootXStream);
	animationLeapSlam.add(bodyXStream);
	animationLeapSlam.add(leftArmXStream);
	animationLeapSlam.add(elbowZStream);
	animationLeapSlam.add(rightArmYStream);
	animationLeapSlam.add(rightArmXStream);
	animationLeapSlam.add(axeStream);

	List<List<AnimationClip<ModelChaosKnight>>> animationCharge = new ArrayList<List<AnimationClip<ModelChaosKnight>>>();
	bodyXStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	bodyYStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	rightArmXStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	rightArmZStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	leftArmXStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	axeStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	elbowZStream = new ArrayList<AnimationClip<ModelChaosKnight>>();

	bodyXStream.add(new AnimationClip<ModelChaosKnight>(10, 0, 30, bodyX));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(25, 30, 30, bodyX));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(10, 30, 0, bodyX));

	bodyYStream.add(new AnimationClip<ModelChaosKnight>(10, 180, 160, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(10, 160, 160, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(5, 160, 210, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(10, 210, 210, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(10, 210, 180, bodyY));

	rightArmZStream.add(new AnimationClip<ModelChaosKnight>(10, 0, -70, rightArmZ));
	rightArmZStream.add(new AnimationClip<ModelChaosKnight>(25, -70, -70, rightArmZ));
	rightArmZStream.add(new AnimationClip<ModelChaosKnight>(10, -70, 0, rightArmZ));

	axeStream.add(new AnimationClip<ModelChaosKnight>(15, 0, 45, axeX));
	axeStream.add(new AnimationClip<ModelChaosKnight>(20, 45, 45, axeX));
	axeStream.add(new AnimationClip<ModelChaosKnight>(10, 45, 0, axeX));

	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(20, 0, 0, none));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(5, 0, -130, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(10, -130, -130, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(10, -130, 0, rightArmX));

	leftArmXStream.add(shieldDownLeftArmX);
	leftArmXStream.add(new AnimationClip<ModelChaosKnight>(37, 0, 0, leftArmX));
	leftArmXStream.add(shieldUpLeftArmX);

	elbowZStream.add(shieldDownElbowZ);
	elbowZStream.add(new AnimationClip<ModelChaosKnight>(37, 0, 0, elbowZ));
	elbowZStream.add(shieldUpElbowZ);

	animationCharge.add(bodyXStream);
	animationCharge.add(bodyYStream);
	animationCharge.add(leftArmXStream);
	animationCharge.add(elbowZStream);
	animationCharge.add(rightArmXStream);
	animationCharge.add(axeStream);
	animationCharge.add(rightArmZStream);
	
	List<List<AnimationClip<ModelChaosKnight>>> animationSpinSlash = new ArrayList<List<AnimationClip<ModelChaosKnight>>>();
	List<AnimationClip<ModelChaosKnight>> rootYStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	bodyXStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	bodyYStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	axeStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	leftArmXStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	elbowZStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	rightArmZStream = new ArrayList<AnimationClip<ModelChaosKnight>>();
	rightArmXStream = new ArrayList<AnimationClip<ModelChaosKnight>>();

	rootYStream.add(new AnimationClip<ModelChaosKnight>(20, 0, 0, none));
	rootYStream.add(new AnimationClip<ModelChaosKnight>(36, 0, 360 * 3 + 60, rootY));
	rootYStream.add(new AnimationClip<ModelChaosKnight>(10, 360 * 3 + 60, 360 * 3, rootY));

	bodyXStream.add(new AnimationClip<ModelChaosKnight>(10, 0, -10, bodyX));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(5, -10, -10, bodyX));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(5, -10, 20, bodyX));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(30, 20, 20, bodyX));
	bodyXStream.add(new AnimationClip<ModelChaosKnight>(10, 20, 0, bodyX));

	bodyYStream.add(new AnimationClip<ModelChaosKnight>(10, 180, 145, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(18, 145, 145, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(5, 145, 210, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(5, 210, 145, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(3, 145, 145, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(5, 145, 210, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(5, 210, 145, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(3, 145, 145, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(5, 145, 210, bodyY));
	bodyYStream.add(new AnimationClip<ModelChaosKnight>(10, 210, 180, bodyY));

	rightArmZStream.add(new AnimationClip<ModelChaosKnight>(10, 0, -70, rightArmZ));
	rightArmZStream.add(new AnimationClip<ModelChaosKnight>(50, -70, -70, rightArmZ));
	rightArmZStream.add(new AnimationClip<ModelChaosKnight>(10, -70, 0, rightArmZ));
	
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(28, 0, 0, none));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(5, 0, -100, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(5, -100, 0, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(3, 0, 0, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(5, 0, -100, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(5, -100, 0, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(3, 0, 0, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(5, 0, -100, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(5, -100, -100, rightArmX));
	rightArmXStream.add(new AnimationClip<ModelChaosKnight>(10, -100, 0, rightArmX));
	
	axeStream.add(new AnimationClip<ModelChaosKnight>(15, 0, 45, axeX));
	axeStream.add(new AnimationClip<ModelChaosKnight>(50, 45, 45, axeX));
	axeStream.add(new AnimationClip<ModelChaosKnight>(10, 45, 0, axeX));
	
	leftArmXStream.add(shieldDownLeftArmX);
	leftArmXStream.add(new AnimationClip<ModelChaosKnight>(60, 0, 0, leftArmX));
	leftArmXStream.add(shieldUpLeftArmX);

	elbowZStream.add(shieldDownElbowZ);
	elbowZStream.add(new AnimationClip<ModelChaosKnight>(60, 0, 0, elbowZ));
	elbowZStream.add(shieldUpElbowZ);
	
	animationSpinSlash.add(bodyXStream);
	animationSpinSlash.add(bodyYStream);
	animationSpinSlash.add(rightArmZStream);
	animationSpinSlash.add(rightArmXStream);
	animationSpinSlash.add(leftArmXStream);
	animationSpinSlash.add(elbowZStream);
	animationSpinSlash.add(axeStream);
	animationSpinSlash.add(rootYStream);

	attackHandler.setAttack(sideSwipe, Action.NONE, () -> new StreamAnimation<ModelChaosKnight>(animationSlash));
	attackHandler.setAttack(leapSlam, Action.NONE, () -> new StreamAnimation<ModelChaosKnight>(animationLeapSlam));
	attackHandler.setAttack(thunderCharge, Action.NONE, () -> new StreamAnimation<ModelChaosKnight>(animationCharge));
	attackHandler.setAttack(spinSlash, Action.NONE, () -> new StreamAnimation<ModelChaosKnight>(animationSpinSlash));
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
    public float getEyeHeight()
    {
	return this.height * 0.8f;
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

	    if (this.getAttackTarget() == null)
	    {
		return;
	    }

	    Byte[] attack = { sideSwipe, leapSlam, thunderCharge, spinSlash };
	    double[] weights = { 3.0 / getDistance(getAttackTarget()), 0.5, 0.5, 0.5 };
	    attackHandler.setCurrentAttack(ModRandom.choice(attack, rand, weights).next());
	    world.setEntityState(this, attackHandler.getCurrentAttack());

	    if (attackHandler.getCurrentAttack() == sideSwipe)
	    {
		this.addEvent(() -> {
		    if (this.getAttackTarget() != null)
		    {
			float distance = this.getDistance(this.getAttackTarget());
			if (distance > 2)
			{
			    ModUtils.leapTowards(this, this.getAttackTarget().getPositionVector(), (float) (0.4 * Math.sqrt(distance)), 0.5f);
			}
		    }
		}, 5);
	    }

	    if (attackHandler.getCurrentAttack() == thunderCharge)
	    {
		Vec3d dir = getAttackTarget().getPositionVector().subtract(getPositionVector()).normalize();
		Vec3d teleportPos = getAttackTarget().getPositionVector();
		int maxDistance = 10;
		for (int i = 0; i < maxDistance; i++)
		{
		    Vec3d proposedPos = teleportPos.add(dir);
		    IBlockState state = world.getBlockState(new BlockPos(proposedPos).down());
		    if (state.canEntitySpawn(this) && state.isTopSolid())
		    {
			teleportPos = proposedPos;
		    }
		}

		this.chargeDir = teleportPos;

		// Send the aimed position to the client side
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("entityId", this.getEntityId());
		data.setFloat("posX", (float) this.chargeDir.x);
		data.setFloat("posY", (float) this.chargeDir.y);
		data.setFloat("posZ", (float) this.chargeDir.z);
		Main.network.sendToAllTracking(new MessageMonolithLazer(data), this);
	    }
	}
    }

    @Override
    public void handleStatusUpdate(byte id)
    {
	if (id >= sideSwipe && id <= spinSlash)
	{
	    currentAnimation = attackHandler.getAnimation(id);
	    getCurrentAnimation().startAnimation();
	}
	else if (id == ModUtils.PARTICLE_BYTE)
	{
	    for (int r = 1; r < 5; r++)
	    {
		ModUtils.circleCallback(r, r * 20, (pos) -> {
		    pos = new Vec3d(pos.x, 0, pos.y);
		    ParticleManager.spawnSplit(world, pos.add(getPositionVector().add(ModUtils.yVec(-1.5f))), ModColors.RED, pos.scale(0.1f).add(ModUtils.yVec(0.05f)));
		});
	    }
	}
	else if (id == ModUtils.SECOND_PARTICLE_BYTE)
	{
	    if (chargeDir != null)
	    {
		Vec3d particleVel = chargeDir.subtract(getPositionVector()).normalize().scale(0.5);
		ModUtils.lineCallback(getPositionVector(), chargeDir, 20, (vec, i) -> {
		    ModUtils.performNTimes(10, (j) -> {
			ParticleManager.spawnSplit(world, vec.add(ModRandom.randVec().scale(dashRadius * 2)), ModColors.RED, particleVel.add(ModRandom.randVec().scale(0.2f)));
			ParticleManager.spawnCustomSmoke(world, vec.add(ModRandom.randVec().scale(dashRadius * 2)), ModColors.GREY, particleVel.add(ModRandom.randVec().scale(0.2f)));
			Vec3d flamePos = vec.add(ModRandom.randVec().scale(dashRadius * 2));
			Vec3d flameVel = particleVel.add(ModRandom.randVec().scale(0.2f));
			world.spawnParticle(EnumParticleTypes.FLAME, flamePos.x, flamePos.y, flamePos.z, flameVel.x, flameVel.y, flameVel.z);
		    });
		});
		this.chargeDir = null; // So that the lazer doesn't render anymore
	    }
	}
	super.handleStatusUpdate(id);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	this.attackHandler.getCurrentAttackAction().performAction(this, target);
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
    public void onStopLeaping()
    {
	ModUtils.handleAreaImpact(5, (e) -> this.getAttack(), this, this.getPositionVector(), ModDamageSource.causeElementalExplosionDamage(this, getElement()));
	this.playSound(SoundEvents.EVOCATION_ILLAGER_CAST_SPELL, 1.0f, 1.0f + ModRandom.getFloat(0.1f));
	this.world.setEntityState(this, ModUtils.PARTICLE_BYTE);
    }

    // For rendering the lazer
    @SideOnly(Side.CLIENT)
    public Vec3d getLazerPosition()
    {
	return this.chargeDir;
    }

    @SideOnly(Side.CLIENT)
    public void setLazerDir(Vec3d lazerDir)
    {
	this.chargeDir = lazerDir;
    }

}
