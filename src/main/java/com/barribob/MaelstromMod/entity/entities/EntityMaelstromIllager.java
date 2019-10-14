package com.barribob.MaelstromMod.entity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.barribob.MaelstromMod.entity.action.Action;
import com.barribob.MaelstromMod.entity.action.ActionSpawnEnemy;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttackNoReset;
import com.barribob.MaelstromMod.entity.animation.AnimationClip;
import com.barribob.MaelstromMod.entity.animation.AnimationOscillateArms;
import com.barribob.MaelstromMod.entity.animation.StreamAnimation;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromIllager;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHorrorAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMaelstromWisp;
import com.barribob.MaelstromMod.entity.util.ComboAttack;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

/**
 * 
 * The illager summoner boss
 *
 */
public class EntityMaelstromIllager extends EntityMaelstromMob
{
    private int[] easy_minion_spawning = { 2 };
    private int[] hard_minion_spawning = { 1, 3, 2, 3 };
    private int counter;
    private byte summonMob = 4;
    private byte magicMissile = 5;
    private byte wisp = 6;
    private byte shield = 7;
    private final float shieldSize = 4;
    private EntityAIRangedAttack phase1AttackAI;
    private EntityAIRangedAttack phase2AttackAI;
    private boolean phase2Attack = false;
    private ComboAttack attackHandler = new ComboAttack();

    // For rendering purposes
    private boolean blockedBlow;

    // Responsible for the boss bar
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_20));

    public EntityMaelstromIllager(World worldIn)
    {
	super(worldIn);
	this.setSize(0.7f, 2.2f);
	this.experienceValue = ModEntities.BOSS_EXPERIENCE;
	this.setLevel(1.5f);
	if (!world.isRemote)
	{
	    attackHandler.addAttack(magicMissile, new Action()
	    {
		@Override
		public void performAction(EntityLeveledMob actor, EntityLivingBase target)
		{
		    ModUtils.throwProjectile(actor, target, new ProjectileHorrorAttack(world, actor, getAttack()), 6.0f, 1.2f,
			    ModUtils.getRelativeOffset(actor, new Vec3d(0, 0, 1)));
		    actor.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		}
	    });
	    attackHandler.addAttack(wisp, new Action()
	    {
		@Override
		public void performAction(EntityLeveledMob actor, EntityLivingBase target)
		{
		    Projectile proj = new ProjectileMaelstromWisp(world, actor, getAttack());
		    proj.setTravelRange(15f);
		    ModUtils.throwProjectile(actor, target, proj, 1.0f, 1.0f);
		    actor.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		}
	    });
	    attackHandler.addAttack(shield, new Action()
	    {
		@Override
		public void performAction(EntityLeveledMob actor, EntityLivingBase target)
		{
		    ModUtils.handleAreaImpact(shieldSize, (e) -> getAttack(), actor, getPositionVector(), ModDamageSource.causeMaelstromExplosionDamage(actor));
		    actor.playSound(SoundEvents.ENTITY_ILLAGER_CAST_SPELL, 1.0F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
		    actor.world.setEntityState(actor, ModUtils.THIRD_PARTICLE_BYTE);
		}
	    });
	}
    }

    @Override
    protected void initAnimation()
    {
	List<List<AnimationClip<ModelMaelstromIllager>>> animationMissile = new ArrayList<List<AnimationClip<ModelMaelstromIllager>>>();
	List<AnimationClip<ModelMaelstromIllager>> rightArm = new ArrayList<AnimationClip<ModelMaelstromIllager>>();
	List<AnimationClip<ModelMaelstromIllager>> leftArm = new ArrayList<AnimationClip<ModelMaelstromIllager>>();

	BiConsumer<ModelMaelstromIllager, Float> leftArmMover = (model, f) -> {
	    model.bipedLeftArm.rotateAngleX = f;
	    model.bipedLeftArm.rotateAngleY = 0;
	    model.bipedLeftArm.rotateAngleZ = f.floatValue() / -6;
	};
	BiConsumer<ModelMaelstromIllager, Float> rightArmMover = (model, f) -> {
	    model.bipedRightArm.rotateAngleX = 0;
	    model.bipedRightArm.rotateAngleY = 0;
	    model.bipedRightArm.rotateAngleZ = 0;
	};

	leftArm.add(new AnimationClip(12, 0, -180, leftArmMover));
	leftArm.add(new AnimationClip(8, -180, -180, leftArmMover));
	leftArm.add(new AnimationClip(4, -180, 0, leftArmMover));

	rightArm.add(new AnimationClip(12, 0, -180, rightArmMover));
	rightArm.add(new AnimationClip(8, -180, -180, rightArmMover));
	rightArm.add(new AnimationClip(4, -180, 0, rightArmMover));

	animationMissile.add(rightArm);
	animationMissile.add(leftArm);

	attackHandler.addAttack(magicMissile, Action.NONE, () -> new StreamAnimation(animationMissile));

	List<List<AnimationClip<ModelMaelstromIllager>>> animationWisp = new ArrayList<List<AnimationClip<ModelMaelstromIllager>>>();
	rightArm = new ArrayList<AnimationClip<ModelMaelstromIllager>>();
	leftArm = new ArrayList<AnimationClip<ModelMaelstromIllager>>();

	leftArmMover = (model, f) -> {
	    model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-90);
	    model.bipedLeftArm.rotateAngleY = f;
	    model.bipedLeftArm.rotateAngleZ = 0;
	};
	rightArmMover = (model, f) -> {
	    model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-90);
	    model.bipedRightArm.rotateAngleY = f;
	    model.bipedRightArm.rotateAngleZ = 0;
	};

	leftArm.add(new AnimationClip(10, 0, -90, leftArmMover));
	leftArm.add(new AnimationClip(8, -90, -90, leftArmMover));
	leftArm.add(new AnimationClip(4, -90, 0, leftArmMover));

	rightArm.add(new AnimationClip(10, 0, 90, rightArmMover));
	rightArm.add(new AnimationClip(8, 90, 90, rightArmMover));
	rightArm.add(new AnimationClip(4, 90, 0, rightArmMover));

	animationWisp.add(rightArm);
	animationWisp.add(leftArm);

	attackHandler.addAttack(wisp, Action.NONE, () -> new StreamAnimation(animationWisp));

	List<List<AnimationClip<ModelMaelstromIllager>>> animationShield = new ArrayList<List<AnimationClip<ModelMaelstromIllager>>>();

	rightArm = new ArrayList<AnimationClip<ModelMaelstromIllager>>();
	leftArm = new ArrayList<AnimationClip<ModelMaelstromIllager>>();

	leftArmMover = (model, f) -> {
	    model.bipedLeftArm.rotateAngleX = f;
	    model.bipedLeftArm.rotateAngleY = -f * 0.45f;
	    model.bipedLeftArm.rotateAngleZ = 0;
	};
	rightArmMover = (model, f) -> {
	    model.bipedRightArm.rotateAngleX = f;
	    model.bipedRightArm.rotateAngleY = f * 0.45f;
	    model.bipedRightArm.rotateAngleZ = 0;
	};

	leftArm.add(new AnimationClip(10, 0, -120, leftArmMover));
	leftArm.add(new AnimationClip(8, -120, -120, leftArmMover));
	leftArm.add(new AnimationClip(4, -120, 0, leftArmMover));

	rightArm.add(new AnimationClip(10, 0, -120, rightArmMover));
	rightArm.add(new AnimationClip(8, -120, -120, rightArmMover));
	rightArm.add(new AnimationClip(4, -120, 0, rightArmMover));

	animationShield.add(rightArm);
	animationShield.add(leftArm);

	attackHandler.addAttack(shield, Action.NONE, () -> new StreamAnimation(animationShield));

	this.currentAnimation = new AnimationOscillateArms(60, this);
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(14);
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300);
    }

    @Override
    protected void initEntityAI()
    {
	super.initEntityAI();
	phase1AttackAI = new EntityAIRangedAttackNoReset<EntityMaelstromMob>(this, 1.25f, 360, 60, 15.0f, 0.5f);
	phase2AttackAI = new EntityAIRangedAttackNoReset<EntityMaelstromMob>(this, 1.25f, 50, 20, 15.0f, 0.5f);
	this.tasks.addTask(4, phase1AttackAI);
    }

    public boolean blockedBlow()
    {
	return this.blockedBlow;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
	return SoundEvents.ENTITY_EVOCATION_ILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
	return SoundEvents.EVOCATION_ILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundEvents.ENTITY_EVOCATION_ILLAGER_HURT;
    }

    @Override
    protected boolean canDespawn()
    {
	return false;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
	return LootTableHandler.MAELSTROM_ILLAGER;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
	if (!this.isSwingingArms())
	{
	    amount = 0;
	}

	this.blockedBlow = !this.isSwingingArms();

	float prevHealth = this.getHealth();
	boolean flag = super.attackEntityFrom(source, amount);

	String message = "";
	if (prevHealth > this.getMaxHealth() * 0.95 && this.getHealth() <= this.getMaxHealth() * 0.95)
	{
	    message = "illager_1";
	}

	if (prevHealth > this.getMaxHealth() * 0.85 && this.getHealth() <= this.getMaxHealth() * 0.85)
	{
	    message = "illager_2";
	}

	if (prevHealth > this.getMaxHealth() * 0.5 && this.getHealth() <= this.getMaxHealth() * 0.5)
	{
	    message = "illager_3";
	    this.tasks.removeTask(phase1AttackAI);
	    this.tasks.addTask(4, phase2AttackAI);
	    phase2Attack = true;
	}

	if (message != "")
	{
	    for (EntityPlayer player : this.bossInfo.getPlayers())
	    {
		player.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "Maelstrom Illager: " + TextFormatting.WHITE)
			.appendSibling(new TextComponentTranslation(ModUtils.LANG_CHAT + message)));
	    }
	}

	return flag;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	if (phase2Attack)
	{
	    attackHandler.getCurrentAttackAction().performAction(this, target);
	}
	else
	{
	    phase1Attack();
	}
    }

    private void phase1Attack()
    {
	int spawnAmount;
	if (this.getHealth() < this.getMaxHealth() * 0.75f)
	{
	    spawnAmount = hard_minion_spawning[counter % this.hard_minion_spawning.length];
	}
	else
	{
	    spawnAmount = easy_minion_spawning[counter % this.easy_minion_spawning.length];
	}

	counter++;

	for (int i = 0; i < spawnAmount; i++)
	{
	    Supplier<EntityLeveledMob> mobSupplier = () -> {
		int r = rand.nextInt(3);
		if (r == 0)
		{
		    return new EntityShade(this.world);
		}
		else if (r == 1)
		{
		    return new EntityMaelstromMage(this.world);
		}
		else
		{
		    return new EntityHorror(this.world);
		}
	    };
	    new ActionSpawnEnemy(mobSupplier).performAction(this, null);
	}
	this.playSound(SoundEvents.ENTITY_ILLAGER_CAST_SPELL, 1.0F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();

	if (!phase2Attack)
	{
	    world.setEntityState(this, ModUtils.PARTICLE_BYTE);
	}
	else if (this.attackHandler != null && this.isSwingingArms())
	{
	    if (this.attackHandler.getCurrentAttack() == magicMissile)
	    {
		world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
	    }
	}
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {
	super.setSwingingArms(swingingArms);
	if (this.isSwingingArms())
	{
	    if (phase2Attack)
	    {
		Byte[] attack = { wisp, magicMissile };
		attackHandler.setCurrentAttack(ModRandom.choice(attack));
		if (this.getAttackTarget() != null && this.getDistance(this.getAttackTarget()) < 4)
		{
		    attackHandler.setCurrentAttack(shield);
		}
		world.setEntityState(this, attackHandler.getCurrentAttack());
	    }
	    else
	    {
		world.setEntityState(this, summonMob);
	    }
	}
    }

    @Override
    public void handleStatusUpdate(byte id)
    {
	if (id == summonMob)
	{
	    currentAnimation.startAnimation();
	}
	else if (id >= 5 && id <= 7)
	{
	    currentAnimation = attackHandler.getAnimation(id);
	    getCurrentAnimation().startAnimation();
	}
	else if (id == ModUtils.THIRD_PARTICLE_BYTE)
	{
	    for (int i = 0; i < 1000; i++)
	    {
		Vec3d unit = new Vec3d(0, 1, 0);
		unit = unit.rotatePitch((float) (Math.PI * ModRandom.getFloat(1)));
		unit = unit.rotateYaw((float) (Math.PI * ModRandom.getFloat(1)));
		unit = unit.normalize().scale(shieldSize);
		ParticleManager.spawnEffect(world, unit.add(getPositionVector()), ModColors.PURPLE);
	    }
	}
	else if (id == ModUtils.SECOND_PARTICLE_BYTE)
	{
	    ParticleManager.spawnMaelstromPotionParticle(world, rand, ModUtils.getRelativeOffset(this, new Vec3d(0, this.getEyeHeight(), 1)).add(getPositionVector()), true);
	}
	else if (id == ModUtils.PARTICLE_BYTE)
	{
	    if (this.isSwingingArms())
	    {
		float f = this.renderYawOffset * 0.017453292F + MathHelper.cos(this.ticksExisted * 0.6662F) * 0.25F;
		float f1 = MathHelper.cos(f);
		float f2 = MathHelper.sin(f);
		ParticleManager.spawnMaelstromPotionParticle(world, rand, new Vec3d(this.posX + f1 * 0.6D, this.posY + 1.8D, this.posZ + f2 * 0.6D), true);
		ParticleManager.spawnMaelstromPotionParticle(world, rand, new Vec3d(this.posX - f1 * 0.6D, this.posY + 1.8D, this.posZ - f2 * 0.6D), true);
	    }
	}
	super.handleStatusUpdate(id);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
	if (this.hasCustomName())
	{
	    this.bossInfo.setName(this.getDisplayName());
	}

	if (compound.hasKey("phase2"))
	{
	    phase2Attack = compound.getBoolean("phase2");
	    if (phase2Attack)
	    {
		this.tasks.removeTask(phase1AttackAI);
		this.tasks.addTask(4, phase2AttackAI);
	    }
	}

	super.readEntityFromNBT(compound);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
	compound.setBoolean("phase2", phase2Attack);

	super.writeEntityToNBT(compound);
    }

    /**
     * Sets the custom name tag for this entity
     */
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

    /**
     * Add the given player to the list of players tracking this entity. For
     * instance, a player may track a boss in order to view its associated boss bar.
     */
    @Override
    public void addTrackingPlayer(EntityPlayerMP player)
    {
	super.addTrackingPlayer(player);
	this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See
     * {@link Entity#addTrackingPlayer} for more information on tracking.
     */
    @Override
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
	super.removeTrackingPlayer(player);
	this.bossInfo.removePlayer(player);
    }
}