package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.ActionGoldenFireball;
import com.barribob.MaelstromMod.entity.action.ActionMultiGoldenRunes;
import com.barribob.MaelstromMod.entity.action.ActionOctoMissiles;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.animation.AnimationMegaMissile;
import com.barribob.MaelstromMod.entity.animation.AnimationOctoMissiles;
import com.barribob.MaelstromMod.entity.animation.AnimationRuneSummon;
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

    public EntityGoldenBoss(World worldIn)
    {
	super(worldIn);
	this.setLevel(2.5f);
	this.setSize(1.5f, 3.2f);
	if (!worldIn.isRemote)
	{
	    this.attackHandler.addAttack(octoMissile, new ActionOctoMissiles());
	    this.attackHandler.addAttack(megaMissile, new ActionGoldenFireball());
	    this.attackHandler.addAttack(runes, new ActionMultiGoldenRunes());
	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void initAnimation()
    {
	this.attackHandler.addAttack(octoMissile, new ActionOctoMissiles(), () -> new AnimationOctoMissiles());
	this.attackHandler.addAttack(megaMissile, new ActionGoldenFireball(), () -> new AnimationMegaMissile());
	this.attackHandler.addAttack(runes, new ActionMultiGoldenRunes(), () -> new AnimationRuneSummon());
	this.currentAnimation = new AnimationOctoMissiles();
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
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	this.bossInfo.setPercent(getHealth() / getMaxHealth());
	if (!world.isRemote)
	{
	    world.setEntityState(this, ModUtils.PARTICLE_BYTE);
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
	if (swingingArms)
	{
	    Byte[] attack = { octoMissile, megaMissile, runes };
	    attackHandler.setCurrentAttack(ModRandom.choice(attack));
	    world.setEntityState(this, attackHandler.getCurrentAttack());
	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id >= 4 && id <= 6)
	{
	    currentAnimation = attackHandler.getAnimation(id);
	    currentAnimation.startAnimation();
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
	else
	{
	    super.handleStatusUpdate(id);
	}
    }

    @Override
    protected void updateAttributes()
    {
	this.setBaseMaxHealth(200);
	this.setBaseAttack(5);
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
}
