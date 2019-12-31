package com.barribob.MaelstromMod.entity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.barribob.MaelstromMod.entity.action.Action;
import com.barribob.MaelstromMod.entity.action.ActionGoldenFireball;
import com.barribob.MaelstromMod.entity.action.ActionMultiGoldenRunes;
import com.barribob.MaelstromMod.entity.action.ActionOctoMissiles;
import com.barribob.MaelstromMod.entity.action.ActionSpawnEnemy;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.animation.Animation;
import com.barribob.MaelstromMod.entity.animation.AnimationClip;
import com.barribob.MaelstromMod.entity.animation.AnimationMegaMissile;
import com.barribob.MaelstromMod.entity.animation.AnimationOctoMissiles;
import com.barribob.MaelstromMod.entity.animation.AnimationRuneSummon;
import com.barribob.MaelstromMod.entity.animation.StreamAnimation;
import com.barribob.MaelstromMod.entity.model.ModelGoldenBoss;
import com.barribob.MaelstromMod.entity.util.ComboAttack;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGoldenBoss extends EntityMaelstromMob
{
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.NOTCHED_6));
    private ComboAttack attackHandler = new ComboAttack();
    private byte octoMissile = 4;
    private byte megaMissile = 5;
    private byte runes = 6;
    private byte spawnPillar = 7;

    public EntityGoldenBoss(World worldIn)
    {
	super(worldIn);
	this.setLevel(2.5f);
	this.setSize(1.6f, 3.6f);
	this.healthScaledAttackFactor = 0.2;
	if (!worldIn.isRemote)
	{
	    this.attackHandler.setAttack(octoMissile, new ActionOctoMissiles());
	    this.attackHandler.setAttack(megaMissile, new ActionGoldenFireball());
	    this.attackHandler.setAttack(runes, new ActionMultiGoldenRunes());
	    this.attackHandler.setAttack(spawnPillar, new ActionSpawnEnemy(() -> new EntityGoldenPillar(world)));
	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void initAnimation()
    {
	this.attackHandler.setAttack(octoMissile, new ActionOctoMissiles(), () -> new AnimationOctoMissiles());
	this.attackHandler.setAttack(megaMissile, new ActionGoldenFireball(), () -> new AnimationMegaMissile());
	this.attackHandler.setAttack(runes, new ActionMultiGoldenRunes(), () -> new AnimationRuneSummon());
	this.attackHandler.setAttack(spawnPillar, Action.NONE, this.getSpawnPillarAnimation());
	this.currentAnimation = new AnimationOctoMissiles();
    }

    public static Supplier<Animation> getSpawnPillarAnimation()
    {
	List<List<AnimationClip<ModelGoldenBoss>>> animationPillar = new ArrayList<List<AnimationClip<ModelGoldenBoss>>>();
	List<AnimationClip<ModelGoldenBoss>> arm = new ArrayList<AnimationClip<ModelGoldenBoss>>();

	BiConsumer<ModelGoldenBoss, Float> armMover = (model, f) -> {
	    model.leftArm1.rotateAngleX = 0;
	    model.leftArm2.rotateAngleX = f * 0.11f;
	    model.leftArm3.rotateAngleX = f * 0.27f;
	    model.leftArm4.rotateAngleX = f * 0.44f;
	    model.leftArm1.rotateAngleZ = -f * 1.5f;
	    model.leftArm2.rotateAngleZ = -f * 1.33f;
	    model.leftArm3.rotateAngleZ = -f * 1.16f;
	    model.leftArm4.rotateAngleZ = -f;
	    model.leftForearm1.rotateAngleZ = 0;
	    model.leftForearm2.rotateAngleZ = 0;
	    model.leftForearm3.rotateAngleZ = 0;
	    model.leftForearm4.rotateAngleZ = 0;

	    model.rightArm1.rotateAngleX = 0;
	    model.rightArm2.rotateAngleX = f * 0.11f;
	    model.rightArm3.rotateAngleX = f * 0.27f;
	    model.rightArm4.rotateAngleX = f * 0.44f;
	    model.rightArm1.rotateAngleZ = f * 1.5f;
	    model.rightArm2.rotateAngleZ = f * 1.33f;
	    model.rightArm3.rotateAngleZ = f * 1.16f;
	    model.rightArm4.rotateAngleZ = f;
	    model.rightForearm1.rotateAngleZ = 0;
	    model.rightForearm2.rotateAngleZ = 0;
	    model.rightForearm3.rotateAngleZ = 0;
	    model.rightForearm4.rotateAngleZ = 0;
	};

	arm.add(new AnimationClip(12, 0, 90, armMover));
	arm.add(new AnimationClip(8, 90, 90, armMover));
	arm.add(new AnimationClip(12, 90, 0, armMover));

	animationPillar.add(arm);
	return () -> new StreamAnimation(animationPillar);
    }

    @Override
    protected boolean canDespawn()
    {
	return false;
    }

    @Override
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttack<EntityGoldenBoss>(this, 1.0f, 40, 20.0f, 0.4f));
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.5);
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(250);
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	this.bossInfo.setPercent(getHealth() / getMaxHealth());
	if (!world.isRemote)
	{
	    world.setEntityState(this, ModUtils.PARTICLE_BYTE);
	    if (this.ticksExisted % 20 == 0)
	    {
		world.setEntityState(this, ModUtils.THIRD_PARTICLE_BYTE);
		for (EntityLivingBase e : ModUtils.getEntitiesInBox(this, this.getEntityBoundingBox().grow(20)))
		{
		    if (e instanceof EntityGoldenPillar)
		    {
			this.heal(1);
			break;
		    }
		}
	    }
	}
    }

    @Override
    public void onDeath(DamageSource cause)
    {
	world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);

	// Spawn the second half of the boss
	EntityMaelstromGoldenBoss boss = new EntityMaelstromGoldenBoss(world);
	boss.copyLocationAndAnglesFrom(this);
	boss.setRotationYawHead(this.rotationYawHead);
	if (!world.isRemote)
	{
	    boss.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(this)), null);
	    world.spawnEntity(boss);
	    boss.setAttackTarget(this.getAttackTarget());
	}
	this.setPosition(0, 0, 0);
	super.onDeath(cause);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	this.attackHandler.getCurrentAttackAction().performAction(this, target);
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {
	super.setSwingingArms(swingingArms);
	if (swingingArms && !world.isRemote)
	{
	    Byte[] attack = { octoMissile, megaMissile, runes, spawnPillar };
	    double[] weights = { 0.3, 0.3, 0.3, 0.1 };
	    attackHandler.setCurrentAttack(ModRandom.choice(attack, rand, weights).next());
	    world.setEntityState(this, attackHandler.getCurrentAttack());
	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id >= 4 && id <= 7)
	{
	    currentAnimation = attackHandler.getAnimation(id);
	    getCurrentAnimation().startAnimation();
	}
	else if (id == ModUtils.PARTICLE_BYTE)
	{
	    ParticleManager.spawnEffect(world, ModRandom.randVec().add(new Vec3d(0, 2, 0).scale(2)).add(this.getPositionVector()), ModColors.YELLOW);
	}
	else if (id == ModUtils.SECOND_PARTICLE_BYTE)
	{
	    ParticleManager.spawnParticlesInCircle(2, 30, (pos) -> {
		ModUtils.performNTimes(10, (y) -> {
		    ParticleManager.spawnDarkFlames(world, rand, pos.add(ModUtils.yVec(y * 0.5f).add(getPositionVector())));
		});
	    });
	}
	else if (id == ModUtils.THIRD_PARTICLE_BYTE)
	{
	    for (EntityLivingBase e : ModUtils.getEntitiesInBox(this, this.getEntityBoundingBox().grow(20)))
	    {
		if (e instanceof EntityGoldenPillar)
		{
		    float numParticles = 20;
		    Vec3d dir = e.getPositionVector().add(ModUtils.yVec(2)).subtract(this.getPositionVector().add(ModUtils.yVec(2))).scale(1 / numParticles);
		    Vec3d currentPos = this.getPositionVector().add(ModUtils.yVec(2));
		    for (int i = 0; i < numParticles; i++)
		    {
			ParticleManager.spawnEffect(world, currentPos, ModColors.YELLOW);
			currentPos = currentPos.add(dir);
		    }
		}
	    }
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }

    @Override
    public void setCustomNameTag(String name)
    {
	super.setCustomNameTag(name);
	this.bossInfo.setName(this.getDisplayName());
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
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundEvents.BLOCK_METAL_PLACE;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
	return SoundEvents.BLOCK_METAL_BREAK;
    }

    @Override
    protected float getManaExp()
    {
	return 0;
    }
}
