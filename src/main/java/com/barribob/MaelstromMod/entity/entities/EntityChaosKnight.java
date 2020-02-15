package com.barribob.MaelstromMod.entity.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.util.ComboAttack;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.init.ModAnimations;
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

public class EntityChaosKnight extends EntityMaelstromMob implements IAttack
{
    private ComboAttack attackHandler = new ComboAttack();
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_6));
    private Vec3d chargeDir;
    private static final float dashRadius = 2;
    private Consumer<EntityLivingBase> prevAttack;

    private final Consumer<EntityLivingBase> sideSwipe = (target) -> {
	this.startAnimation(ModAnimations.CHAOS_KNIGHT_SINGLE_SWIPE);
	addEvent(() -> {
	    float distance = getDistance(target);
	    if (distance > 2)
	    {
		ModUtils.leapTowards(this, target.getPositionVector(), (float) (0.4 * Math.sqrt(distance)), 0.5f);
	    }
	}, 5);

	addEvent(() -> {
	    Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0.5, 1, -1)));
	    ModUtils.handleAreaImpact(2, (e) -> getAttack(), this, offset, ModDamageSource.causeElementalMeleeDamage(this, getElement()), 0.5f, 0, false);
	    playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
	    Vec3d jump = getLookVec().rotateYaw(180).scale(0.6f);
	    motionX = jump.x;
	    motionY = 0.4;
	    motionZ = jump.z;
	}, 20);

	addEvent(() -> EntityChaosKnight.super.setSwingingArms(false), 35);
    };

    private final Consumer<EntityLivingBase> leapSlam = (target) -> {
	this.startAnimation(ModAnimations.CHAOS_KNIGHT_LEAP_SLAM);
	addEvent(() -> {
	    ModUtils.leapTowards(this, target.getPositionVector(), (float) (0.4f * Math.sqrt(getDistance(target))), 1.0f);
	    fallDistance = -3;
	    setLeaping(true);
	}, 20);
	addEvent(() -> EntityChaosKnight.super.setSwingingArms(false), 60);
    };

    private final Consumer<EntityLivingBase> dash = (target) -> {
	this.startAnimation(ModAnimations.CHAOS_KNIGHT_DASH);
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

	addEvent(() -> {
	    world.createExplosion(this, posX, posY, posZ, 2, false);
	    ModUtils.lineCallback(getPositionVector(), chargeDir, (int) Math.sqrt(chargeDir.subtract(getPositionVector()).lengthSquared()), (vec, i) -> {
		ModUtils.handleAreaImpact(dashRadius, (e) -> getAttack(), this, vec, ModDamageSource.causeElementalMeleeDamage(this, getElement()), 0.3f, 5);
		world.playSound(vec.x, vec.y, vec.z, SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.HOSTILE, 1.0f, 1.0f + ModRandom.getFloat(0.1f), false);
	    });
	    attemptTeleport(chargeDir.x, chargeDir.y, chargeDir.z);
	    world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
	    playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 1.0f, 1.0f + ModRandom.getFloat(0.1f));
	}, 20);

	addEvent(() -> EntityChaosKnight.super.setSwingingArms(false), 45);
    };

    private final Consumer<EntityLivingBase> spinSlash = (target) -> {
	this.startAnimation(ModAnimations.CHAOS_KNIGHT_SPIN_SLASH);
	Runnable leap = () -> ModUtils.leapTowards(this, target.getPositionVector(), (float) (0.4f * Math.sqrt(getDistance(target))), 0.4f);
	Runnable meleeAttack = () -> {
	    ModUtils.handleAreaImpact(2.7f, (e) -> getAttack(), this, getPositionVector().add(ModUtils.yVec(1)), ModDamageSource.causeElementalMeleeDamage(this, getElement()), 0.5f, 0, false);
	    playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
	};

	addEvent(leap, 20);
	addEvent(meleeAttack, 30);
	addEvent(leap, 33);
	addEvent(meleeAttack, 41);
	addEvent(leap, 44);
	addEvent(meleeAttack, 53);
	addEvent(() -> EntityChaosKnight.super.setSwingingArms(false), 70);
    };

    public EntityChaosKnight(World worldIn)
    {
	super(worldIn);
	this.setSize(1.5f, 3.0f);
	this.healthScaledAttackFactor = 0.2;
	this.experienceValue = ModEntities.BOSS_EXPERIENCE;
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceFactor, boolean strafingBackwards)
    {
	setSwingingArms(true);
	double distance = Math.sqrt(distanceFactor);
	List<Consumer<EntityLivingBase>> attacks = new ArrayList<Consumer<EntityLivingBase>>(Arrays.asList(sideSwipe, leapSlam, dash, spinSlash));
	double[] weights = {
		(1 - (distance / 10)) * (prevAttack != sideSwipe ? 1.5 : 1.0), // More likely at closer range
		0.2 + 0.04 * distance, // Most likely as longer range
		0.2 + 0.04 * distance,
		0.5 - (prevAttack == spinSlash ? 0.3 : 0.0) }; // A powerful move that shouldn't happen too often in a row.

	prevAttack = ModRandom.choice(attacks, rand, weights).next();
	prevAttack.accept(target);
	return prevAttack == sideSwipe ? 50 : 90;
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
	this.tasks.addTask(4, new EntityAITimedAttack<EntityChaosKnight>(this, 1.0f, 90, 15, 0.5f));
    }

    @Override
    public void handleStatusUpdate(byte id)
    {
	if (id == ModUtils.PARTICLE_BYTE)
	{
	    for (int r = 1; r < 3; r++)
	    {
		ModUtils.circleCallback(r, r * 20, (pos) -> {
		    pos = new Vec3d(pos.x, 0, pos.y);
		    ParticleManager.spawnSplit(world, pos.add(this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1, 0, 0))).add(ModUtils.yVec(-1.5f))), ModColors.RED, pos.scale(0.1f).add(ModUtils.yVec(0.05f)));
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
    public void onStopLeaping()
    {
	ModUtils.handleAreaImpact(3, (e) -> this.getAttack(), this, this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1, 0, 0))), ModDamageSource.causeElementalExplosionDamage(this, getElement()));
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

    @Override
    protected void initAnimation()
    {
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
    }
}
