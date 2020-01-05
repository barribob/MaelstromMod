package com.barribob.MaelstromMod.entity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.action.Action;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttackNoReset;
import com.barribob.MaelstromMod.entity.animation.AnimationClip;
import com.barribob.MaelstromMod.entity.animation.AnimationNone;
import com.barribob.MaelstromMod.entity.animation.StreamAnimation;
import com.barribob.MaelstromMod.entity.model.ModelMonolith;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMaelstromMeteor;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMonolithFireball;
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

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMonolith extends EntityMaelstromMob implements LeapingEntity
{
    // Maelstrom minion AI stuff
    private static final int enemySpawnTicks = 200;
    private static final int maxShades = 5;
    private List<CommandMob> commandMobs = new ArrayList<CommandMob>();
    private Vec3d[] movementsDownTower = { new Vec3d(-11, -3, 0), new Vec3d(-5, -7, 0), new Vec3d(-11, -11, 0), new Vec3d(-5, -15, 0), new Vec3d(-11, -19, 0),
	    new Vec3d(-5, -23, 0), new Vec3d(-11, -27, 0), new Vec3d(-5, -31, 0), new Vec3d(-11, -35, 0), new Vec3d(-5, -39, 0), new Vec3d(-21, -42, 0) };

    private ComboAttack attackHandler = new ComboAttack();
    public static final byte noAttack = 0;
    public static final byte blueAttack = 4;
    public static final byte redAttack = 5;
    public static final byte yellowAttack = 6;
    private byte stageTransform = 7;
    private boolean leaping = false;
    private static final DataParameter<Boolean> TRANSFORMED = EntityDataManager.<Boolean>createKey(EntityMonolith.class, DataSerializers.BOOLEAN);
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_6));

    // Field to handle the anvil attack teleporting
    private int teleportBackTime = 0;

    // Field to store the lazer's aimed direction
    private Vec3d lazerDir;
    private float lazerRadius = 2.5f;

    // Datamanager to keep track of which attack the mob is doing
    private static final DataParameter<Byte> ATTACK = EntityDataManager.<Byte>createKey(EntityMonolith.class, DataSerializers.BYTE);

    public EntityMonolith(World worldIn)
    {
	super(worldIn);
	this.setImmovable(true);
	this.setNoGravity(true);
	this.setSize(2.2f, 4.5f);
	this.healthScaledAttackFactor = 0.2;
	this.isImmuneToFire = true;
	this.experienceValue = ModEntities.BOSS_EXPERIENCE;
	if (!world.isRemote)
	{
	    attackHandler.setAttack(blueAttack, new Action()
	    {
		@Override
		public void performAction(EntityLeveledMob actor, EntityLivingBase target)
		{
		    int numMobs = EntityMonolith.this.isTransformed() ? 3 : 2;
		    for (int i = 0; i < numMobs; i++)
		    {
			EntityMaelstromMob mob = new EntityShade(world);
			mob.setLevel(getLevel());
			mob.copyLocationAndAnglesFrom(EntityMonolith.this);
			mob.posX -= 1;
			world.spawnEntity(mob);
		    }
		}
	    });
	    attackHandler.setAttack(redAttack, new Action()
	    {
		@Override
		public void performAction(EntityLeveledMob actor, EntityLivingBase target)
		{
		    ModUtils.performNTimes(3, (i) -> {
			ProjectileMonolithFireball meteor = new ProjectileMonolithFireball(world, actor, actor.getAttack(), null);
			Vec3d pos = ModRandom.randVec().scale(10).add(target.getPositionVector()).add(ModUtils.yVec(30));
			meteor.setPosition(pos.x, pos.y, pos.z);
			meteor.shoot(actor, 90, 0, 0.0F, 1.0f, 0);
			meteor.motionX -= actor.motionX;
			meteor.motionZ -= actor.motionZ;
			meteor.setTravelRange(100f);
			world.spawnEntity(meteor);
		    });
		}
	    });
	    attackHandler.setAttack(yellowAttack, new Action()
	    {
		@Override
		public void performAction(EntityLeveledMob actor, EntityLivingBase target)
		{
		    ModUtils.handleAreaImpact(7, (e) -> getAttack(), actor, getPositionVector(), ModDamageSource.causeMaelstromMeleeDamage(actor), 2.0f, 0, true);
		    actor.playSound(SoundEvents.EVOCATION_ILLAGER_CAST_SPELL, 1.0f, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
		    actor.world.setEntityState(actor, ModUtils.SECOND_PARTICLE_BYTE);
		}
	    });
	    attackHandler.setAttack(stageTransform, new Action()
	    {
		// Change the yellow and blue attacks to new attacks
		@Override
		public void performAction(EntityLeveledMob actor, EntityLivingBase target)
		{
		    actor.getDataManager().set(TRANSFORMED, Boolean.valueOf(true));
		    attackHandler.setAttack(yellowAttack, new Action()
		    {
			@Override
			public void performAction(EntityLeveledMob actor, EntityLivingBase target)
			{
			    actor.setImmovable(false);
			    actor.setNoGravity(false);
			    Vec3d pos = target.getPositionVector().add(target.getForward()).add(ModUtils.yVec(24))
				    .add(new Vec3d(ModRandom.getFloat(1), 0, ModRandom.getFloat(1)));
			    actor.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
			    actor.setPosition(pos.x, pos.y, pos.z);
			    EntityMonolith.this.leaping = true;
			}
		    });
		    attackHandler.setAttack(redAttack, new Action()
		    {
			@Override
			public void performAction(EntityLeveledMob actor, EntityLivingBase target)
			{
			    actor.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.5F, 0.4F / (actor.world.rand.nextFloat() * 0.4F + 0.8F));

			    float numParticles = 10;
			    Vec3d dir = lazerDir.subtract(actor.getPositionVector().add(ModUtils.yVec(actor.getEyeHeight()))).scale(1 / numParticles);
			    Vec3d currentPos = actor.getPositionVector().add(ModUtils.yVec(actor.getEyeHeight()));
			    for (int i = 0; i < numParticles; i++)
			    {
				ModUtils.handleAreaImpact(lazerRadius, (e) -> actor.getAttack(), actor, currentPos, ModDamageSource.causeMaelstromExplosionDamage(actor), 0.5f,
					5, false);
				currentPos = currentPos.add(dir);
				for (int j = 0; j < 20; j++)
				{
				    Vec3d pos = currentPos.add(ModRandom.randVec().scale(lazerRadius));
				    if (world.isBlockFullCube(new BlockPos(pos).down()) && world.isAirBlock(new BlockPos(pos)))
				    {
					world.setBlockState(new BlockPos(pos), Blocks.FIRE.getDefaultState());
				    }
				}
			    }
			    world.setEntityState(actor, ModUtils.FOURTH_PARTICLE_BYTE);
			}
		    });
		}
	    });
	}
    }

    @Override
    public float getEyeHeight()
    {
	return this.height * 0.8f;
    }

    @Override
    protected void initAnimation()
    {
	List<List<AnimationClip<ModelMonolith>>> animationStage2 = new ArrayList<List<AnimationClip<ModelMonolith>>>();
	List<AnimationClip<ModelMonolith>> middle = new ArrayList<AnimationClip<ModelMonolith>>();

	BiConsumer<ModelMonolith, Float> resize = (model, f) -> {
	    f *= 35f;

	    model.shell3.rotateAngleX = 0.1f;
	    model.body1.rotateAngleX = 0.1f;
	    model.body2.rotateAngleX = 0.1f;

	    model.shell3 = new ModelRenderer(model);
	    model.shell3.setRotationPoint(0.0F, 24.0F, 0.0F);
	    model.shell3.cubeList.add(new ModelBox(model.shell3, 116, 116, -4.0F, -71.0F + f.intValue(), -11.0F, 8, 61 - f.intValue(), 22, 0.0F, false));

	    model.body1 = new ModelRenderer(model);
	    model.body1.setRotationPoint(-6.0F, 24.0F, 0.0F);
	    model.body1.cubeList.add(new ModelBox(model.body1, 94, 0, -5.0F, -69.0F + f.intValue(), -8.0F, 7, 62 - f.intValue(), 16, 0.0F, false));

	    model.body2 = new ModelRenderer(model);
	    model.body2.setRotationPoint(7.0F, 24.0F, 0.0F);
	    model.body2.cubeList.add(new ModelBox(model.body2, 0, 95, -3.0F, -65.0F + f.intValue(), -8.0F, 6, 56 - f.intValue(), 16, 0.0F, false));
	};

	middle.add(new AnimationClip(40, 0, (float) Math.toDegrees(Math.PI / 3), resize));

	animationStage2.add(middle);
	attackHandler.setAttack(stageTransform, Action.NONE, () -> new StreamAnimation(animationStage2));
	attackHandler.setAttack(blueAttack, Action.NONE, () -> new AnimationNone());
	attackHandler.setAttack(redAttack, Action.NONE, () -> new AnimationNone());
	attackHandler.setAttack(yellowAttack, Action.NONE, () -> new AnimationNone());
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
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttackNoReset<EntityMonolith>(this, 0, 90, 40, 30, 0.0f));
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
	if (this.isSwingingArms())
	{
	    this.playSound(SoundsHandler.ENTITY_MONOLITH_AMBIENT, 0.7f, 1.0f * ModRandom.getFloat(0.2f));

	    int numMinions = (int) ModUtils.getEntitiesInBox(this, getEntityBoundingBox().grow(10, 2, 10)).stream().filter((e) -> e instanceof EntityMaelstromMob).count();

	    double yellowWeight = 0.0;
	    if (this.getAttackTarget() != null && this.getDistance(this.getAttackTarget()) < 6)
	    {
		yellowWeight = 1.0; // Likely to use yellow attack if the player is near
	    }
	    else if (isTransformed())
	    {
		yellowWeight = 0.3;
	    }

	    Byte[] attack = { blueAttack, redAttack, yellowAttack };
	    double[] weights = { numMinions == 0 ? 0.5 : 0.1, 0.5, yellowWeight };
	    attackHandler.setCurrentAttack(ModRandom.choice(attack, rand, weights).next());

	    if (!isTransformed() && this.getHealth() < this.getMaxHealth() * 0.5)
	    {
		attackHandler.setCurrentAttack(stageTransform);
	    }

	    // Initialize the lazer
	    if (isTransformed() && attackHandler.getCurrentAttack() == redAttack && this.getAttackTarget() != null)
	    {
		this.lazerDir = getAttackTarget().getPositionVector().add(ModUtils.yVec(this.getEyeHeight()))
			.subtract(getPositionVector().add(ModUtils.yVec(this.getEyeHeight()))).normalize().scale(20).add(getPositionVector());

		// Send the aimed position to the client side
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("entityId", this.getEntityId());
		data.setFloat("posX", (float) this.lazerDir.x);
		data.setFloat("posY", (float) this.lazerDir.y);
		data.setFloat("posZ", (float) this.lazerDir.z);
		Main.network.sendToAllTracking(new MessageMonolithLazer(data), this);
	    }

	    world.setEntityState(this, attackHandler.getCurrentAttack());
	    this.dataManager.set(ATTACK, Byte.valueOf(attackHandler.getCurrentAttack()));
	}
	else
	{
	    this.dataManager.set(ATTACK, Byte.valueOf(noAttack));
	}
    }

    // For rendering the lazer
    @SideOnly(Side.CLIENT)
    public Vec3d getLazerPosition()
    {
	if (isTransformed() && getAttackColor() == redAttack && this.lazerDir != null)
	{
	    return this.lazerDir;
	}
	return null;
    }

    public boolean isTransformed()
    {
	return this.dataManager.get(TRANSFORMED).booleanValue();
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	this.setRotation(0, 0);
	this.setRotationYawHead(0);

	if (!world.isRemote && this.getAttackTarget() == null)
	{
	    this.dataManager.set(ATTACK, Byte.valueOf(noAttack));
	}

	// Teleport back after a certain amount of time
	if (this.teleportBackTime > 0)
	{
	    teleportBackTime--;
	    if (!this.isImmovable() && teleportBackTime == 0)
	    {
		this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
		this.setImmovable(true);
		this.setNoGravity(false);
	    }
	}

	// When is is "moving" make sure it still feels immovable
	if (!this.isImmovable())
	{
	    this.setVelocity(0, this.motionY, 0);
	}

	// Spawn a maelstrom splotch nearby
	int maelstromMeteorTime = 1200;
	int maxMeteors = 20;
	if (!world.isRemote && this.getAttackTarget() == null && this.ticksExisted % maelstromMeteorTime == 0 && this.ticksExisted < maelstromMeteorTime * maxMeteors)
	{
	    ProjectileMaelstromMeteor meteor = new ProjectileMaelstromMeteor(world, this, this.getAttack());
	    Vec3d pos = new Vec3d(ModRandom.getFloat(1.0f), 0, ModRandom.getFloat(1.0f)).normalize().scale(ModRandom.range(20, 40)).add(this.getPositionVector());
	    meteor.setPosition(pos.x, pos.y, pos.z);
	    meteor.shoot(this, 90, 0, 0.0F, 0.7f, 0);
	    meteor.motionX -= this.motionX;
	    meteor.motionZ -= this.motionZ;
	    meteor.setTravelRange(100f);
	    world.spawnEntity(meteor);
	}

	// Spawn a new mob and add it to the list of mobs to command
	int mobSpawnTime = 200;
	if (!world.isRemote && commandMobs.size() < maxShades && this.getAttackTarget() == null && this.ticksExisted % mobSpawnTime == 0)
	{
	    EntityMaelstromMob mob = new EntityShade(world);
	    mob.setLevel(getLevel());
	    mob.copyLocationAndAnglesFrom(this);
	    world.spawnEntity(mob);
	    commandMobs.add(new CommandMob(mob));
	}

	// Command all summoned mobs to walk down the stairs of the tower
	if (!world.isRemote)
	{
	    List<CommandMob> mobsToRemove = new ArrayList<CommandMob>();
	    for (CommandMob commandMob : commandMobs)
	    {
		if (commandMob.mob.getAttackTarget() != null || commandMob.mob.isDead || commandMob.mob.ticksExisted > 1500
			|| commandMob.towerProgress >= movementsDownTower.length)
		{
		    mobsToRemove.add(commandMob);
		}
		else
		{
		    Vec3d pos = movementsDownTower[commandMob.towerProgress].add(getPositionVector());
		    commandMob.mob.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, 1.0);

		    // When the mob reaches the nearest waypoint
		    if (commandMob.mob.getDistanceSq(pos.x, pos.y, pos.z) < 2)
		    {
			commandMob.towerProgress++;
		    }
		}
	    }
	    commandMobs.removeAll(mobsToRemove);
	}

	world.setEntityState(this, ModUtils.PARTICLE_BYTE);
    }

    @Override
    public void handleStatusUpdate(byte id)
    {
	if (id >= 4 && id <= 7)
	{
	    currentAnimation = attackHandler.getAnimation(id);
	    getCurrentAnimation().startAnimation();
	}
	else if (id == ModUtils.PARTICLE_BYTE)
	{
	    Vec3d particlePos = getPositionVector().add(ModUtils.yVec(2)).add(ModRandom.randVec().scale(4));
	    switch (this.getAttackColor())
	    {
	    case noAttack:
		ParticleManager.spawnMaelstromSmoke(world, rand, particlePos, false);
		break;
	    case blueAttack:
		ParticleManager.spawnEffect(world, particlePos, ModColors.BLUE);
		break;
	    case redAttack:
		ParticleManager.spawnEffect(world, particlePos, ModColors.RED);
		break;
	    case yellowAttack:
		ParticleManager.spawnEffect(world, particlePos, ModColors.YELLOW);
	    }
	}
	else if (id == ModUtils.SECOND_PARTICLE_BYTE)
	{
	    ModUtils.performNTimes(4, (i) -> {
		ParticleManager.spawnParticlesInCircle(2, 60, (pos) -> {
		    pos = new Vec3d(pos.x, 0, pos.y);
		    ParticleManager.spawnFirework(world, pos.add(getPositionVector()).add(ModUtils.yVec(i + 1)), ModColors.YELLOW, pos.scale(0.5f));
		});
	    });
	}
	else if (id == ModUtils.THIRD_PARTICLE_BYTE)
	{
	    ModUtils.performNTimes(100, (i) -> {
		this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + ModRandom.getFloat(5), this.posY + ModRandom.getFloat(5),
			this.posZ + ModRandom.getFloat(5), 0, 0, 0);
	    });
	}
	else if (id == ModUtils.FOURTH_PARTICLE_BYTE)
	{
	    if (lazerDir != null)
	    {
		float numParticles = 10;
		Vec3d dir = lazerDir.subtract(this.getPositionVector().add(ModUtils.yVec(this.getEyeHeight()))).scale(1 / numParticles);
		Vec3d currentPos = this.getPositionVector().add(ModUtils.yVec(this.getEyeHeight()));
		for (int i = 0; i < numParticles; i++)
		{
		    for (int j = 0; j < 100; j++)
		    {
			ParticleManager.spawnEffect(world, currentPos.add(ModRandom.randVec().scale(lazerRadius * 2)), ModColors.RED);
			Vec3d pos = currentPos.add(ModRandom.randVec().scale(lazerRadius * 2));
			world.spawnParticle(EnumParticleTypes.FLAME, pos.x, pos.y, pos.z, 0, 0, 0);
			pos = currentPos.add(ModRandom.randVec().scale(lazerRadius * 2));
			world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, pos.x, pos.y, pos.z, 0, 0, 0);
		    }
		    currentPos = currentPos.add(dir);
		}
	    }
	}
	super.handleStatusUpdate(id);
    }

    private static class CommandMob
    {
	public EntityMaelstromMob mob;
	public int towerProgress = 0;

	public CommandMob(EntityMaelstromMob mob)
	{
	    this.mob = mob;
	}
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
	ModUtils.handleAreaImpact(5, (e) -> this.getAttack(), this, this.getPositionVector().add(ModUtils.yVec(1)), ModDamageSource.causeMaelstromExplosionDamage(this));
	this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f + ModRandom.getFloat(0.1f));
	this.world.setEntityState(this, ModUtils.THIRD_PARTICLE_BYTE);
	this.teleportBackTime = 20;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
	if (source == DamageSource.FALL)
	{
	    return false;
	}
	return super.attackEntityFrom(source, amount);
    }

    @Override
    protected boolean canDespawn()
    {
	return false;
    }

    @SideOnly(Side.CLIENT)
    public void setLazerDir(Vec3d lazerDir)
    {
	this.lazerDir = lazerDir;
    }

    @Override
    protected void entityInit()
    {
	super.entityInit();
	this.dataManager.register(ATTACK, Byte.valueOf(noAttack));
	this.dataManager.register(TRANSFORMED, Boolean.valueOf(false));
    }

    public byte getAttackColor()
    {
	return this.dataManager.get(ATTACK).byteValue();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundEvents.BLOCK_METAL_PLACE;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
	return SoundEvents.ENTITY_GENERIC_EXPLODE;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
	// Make sure we save as immovable to avoid some weird states
	if (!this.isImmovable())
	{
	    this.setImmovable(true);
	    this.setPosition(0, 0, 0);// Setting any position teleports it back to the initial position
	}
	super.writeEntityToNBT(compound);
    }

    @Override
    public void onDeath(DamageSource cause)
    {
	world.setEntityState(this, ModUtils.THIRD_PARTICLE_BYTE); // Explode on death

	// Spawn the second half of the boss
	EntityWhiteMonolith boss = new EntityWhiteMonolith(world);
	boss.copyLocationAndAnglesFrom(this);
	boss.setRotationYawHead(this.rotationYawHead);
	if (!world.isRemote)
	{
	    world.spawnEntity(boss);
	}

	// Teleport away so that the player doens't see the death animation
	this.setImmovable(false);
	this.setPosition(0, 0, 0);
	super.onDeath(cause);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
	if (this.hasCustomName())
	{
	    this.bossInfo.setName(this.getDisplayName());
	}
	super.readEntityFromNBT(compound);
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
}
