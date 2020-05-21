package com.barribob.MaelstromMod.entity.entities.gauntlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.ai.AIAerialTimedAttack;
import com.barribob.MaelstromMod.entity.ai.AiFistWander;
import com.barribob.MaelstromMod.entity.ai.EntityAIWanderWithGroup;
import com.barribob.MaelstromMod.entity.ai.FlyingMoveHelper;
import com.barribob.MaelstromMod.entity.ai.ModEntitySenses;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromHealer;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromLancer;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMage;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMegaFireball;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic.MobSpawnData;
import com.barribob.MaelstromMod.entity.util.DirectionalRender;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.packets.MessageDirectionForRender;
import com.barribob.MaelstromMod.packets.MessageModParticles;
import com.barribob.MaelstromMod.particle.EnumModParticles;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.RenderUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.world.dimension.crimson_kingdom.WorldGenGauntletSpike;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityMaelstromGauntlet extends EntityMaelstromMob implements IAttack, IEntityMultiPart, DirectionalRender {
    // We keep track of the look ourselves because minecraft's look is clamped
    protected static final DataParameter<Float> LOOK = EntityDataManager.<Float>createKey(EntityLeveledMob.class, DataSerializers.FLOAT);
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_6));
    private MultiPartEntityPart[] hitboxParts;
    private float boxSize = 0.8f;
    private MultiPartEntityPart eye = new MultiPartEntityPart(this, "eye", 1.0f, 1.0f);
    private MultiPartEntityPart behindEye = new MultiPartEntityPart(this, "behindEye", 1.0f, 1.0f);
    private MultiPartEntityPart bottomPalm = new MultiPartEntityPart(this, "bottomPalm", 1.2f, 1.2f);
    private MultiPartEntityPart upLeftPalm = new MultiPartEntityPart(this, "upLeftPalm", boxSize, boxSize);
    private MultiPartEntityPart upRightPalm = new MultiPartEntityPart(this, "upRightPalm", boxSize, boxSize);
    private MultiPartEntityPart rightPalm = new MultiPartEntityPart(this, "rightPalm", boxSize, boxSize);
    private MultiPartEntityPart leftPalm = new MultiPartEntityPart(this, "leftPalm", boxSize, boxSize);
    private MultiPartEntityPart fingers = new MultiPartEntityPart(this, "fingers", 1.2f, 1.2f);
    private MultiPartEntityPart fist = new MultiPartEntityPart(this, "fist", 0, 0);
    private Consumer<EntityLivingBase> prevAttack;
    private boolean isPunching;

    // Lazer state variables
    private boolean isShootingLazer;
    private Vec3d targetPos;
    private Vec3d renderLazerPos;
    private Vec3d prevRenderLazerPos;
    private byte stopLazerByte = 39;
    private int beamLag = 7;

    private boolean isDefending;

    // Used to filter damage from parts
    private boolean damageFromEye;

    // Custom entity see ai
    private EntitySenses senses = new ModEntitySenses(this);

    public final Consumer<Vec3d> punchAtPos = (target) -> {
	ModBBAnimations.animation(this, "gauntlet.punch", false);
	this.addVelocity(0, 0.5, 0);
	this.addEvent(() -> {
	    this.targetPos = target;
	    this.isPunching = true;
	    this.fist.width = 2.5f;
	    this.fist.height = 4.5f;
	    this.height = 2;
	    for (int i = 0; i < 10; i++) {
		this.addEvent(() -> {
		    Vec3d dir = this.targetPos.subtract(this.getPositionVector()).normalize().scale(0.32);
		    ModUtils.addEntityVelocity(this, dir);
		}, i);
	    }
	}, 20);
	for (int i = 0; i < 12; i++) {
	    this.addEvent(() -> {
		ModUtils.facePosition(target, this, 15, 15);
		this.getLookHelper().setLookPosition(target.x, target.y, target.z, 15, 15);
		this.setLook(target.subtract(this.getPositionEyes(1)));
	    }, i);
	}
	this.addEvent(() -> {
	    this.isPunching = false;
	}, 40);
	this.addEvent(() -> {
	    this.fist.width = 0;
	    this.fist.height = 0;
	    this.height = 4;
	}, 50);
    };

    private final Consumer<EntityLivingBase> punch = (target) -> {
	punchAtPos.accept(target.getPositionVector());
    };

    private final Consumer<EntityLivingBase> lazer = (target) -> {
	ModBBAnimations.animation(this, "gauntlet.lazer_eye", false);
	for (int i = 0; i < 25; i++) {
	    this.addEvent(() -> {
		world.setEntityState(this, ModUtils.PARTICLE_BYTE);
	    }, i);
	}
	this.addEvent(() -> {
	    this.isShootingLazer = true;
	}, 25);
	this.addEvent(() -> {
	    this.isShootingLazer = false;
	    // Have to add delay because there will be 5 more ticks of lazers
	    this.addEvent(() -> {
		world.setEntityState(this, stopLazerByte);
	    }, beamLag + 1);
	}, 60);
    };

    private final Consumer<EntityLivingBase> defend = (target) -> {
	ModBBAnimations.animation(this, "gauntlet.defend", false);
	this.addEvent(() -> {
	    this.isDefending = true;
	    this.fist.width = 2.5f;
	    this.fist.height = 2f;
	}, 10);
	this.addEvent(() -> {
	    this.isDefending = false;
	    this.fist.width = 0;
	    this.fist.height = 0;
	}, 210);

	// Schedule spawning a bunch of enemies
	for (int i = 1; i < 200; i += 30) {
	    this.addEvent(() -> {
		EntityLeveledMob mob = ModUtils.spawnMob(world, this.getPosition(), this.getLevel(),
			new MobSpawnData[] {
				new MobSpawnData(ModEntities.getID(EntityShade.class), new Element[] { Element.CRIMSON, Element.NONE }, new int[] { 1, 4 }, 1),
				new MobSpawnData(ModEntities.getID(EntityMaelstromLancer.class), new Element[] { Element.CRIMSON, Element.NONE }, new int[] { 1, 4 }, 1),
				new MobSpawnData(ModEntities.getID(EntityMaelstromMage.class), new Element[] { Element.CRIMSON, Element.NONE }, new int[] { 1, 4 }, 1),
				new MobSpawnData(ModEntities.getID(EntityMaelstromHealer.class), Element.NONE),
			},
			new int[] { 3, 2, 1, 1 },
			new BlockPos(8, 6, 8));
		if (mob != null) {
		    ModUtils.lineCallback(this.getPositionEyes(1), mob.getPositionVector(), 20, (pos, j) -> Main.network.sendToAllTracking(new MessageModParticles(EnumModParticles.EFFECT, pos, Vec3d.ZERO, mob.getElement().particleColor), this));
		}
	    }, i);
	}
    };

    private final Consumer<EntityLivingBase> fireball = (target) -> {
	ModBBAnimations.animation(this, "gauntlet.fireball", false);
	Projectile proj = new ProjectileMegaFireball(world, this, this.getAttack() * 2f, null);
	proj.setTravelRange(30);

	this.addEvent(() -> {
	    world.spawnEntity(proj);
	}, 10);

	// Hold the fireball in place
	for (int i = 10; i < 27; i++) {
	    this.addEvent(() -> {
		Vec3d fireballPos = this.getPositionEyes(1).add(ModUtils.getAxisOffset(ModUtils.getLookVec(this.getLook(), this.renderYawOffset), new Vec3d(-1, 0, 0)));
		ModUtils.setEntityPosition(proj, fireballPos);
	    }, i);
	}

	// Throw the fireball
	this.addEvent(() -> {
	    Vec3d vel = target.getPositionEyes(1).subtract(ModUtils.yVec(1)).subtract(proj.getPositionVector());
	    proj.shoot(vel.x, vel.y, vel.z, 0.8f, 0.3f);
	}, 27);
    };

    public EntityMaelstromGauntlet(World worldIn) {
	super(worldIn);
	this.moveHelper = new FlyingMoveHelper(this);
	this.navigator = new PathNavigateFlying(this, worldIn);
	this.hitboxParts = new MultiPartEntityPart[] { eye, behindEye, bottomPalm, upLeftPalm, upRightPalm, rightPalm, leftPalm, fingers, fist };
	this.setSize(2, 4);
	this.noClip = true;
	this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes() {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(400);
	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26f);
	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12f);
	this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
    }

    @Override
    protected void initEntityAI() {
	super.initEntityAI();
	float attackDistance = (float) this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
	this.tasks.addTask(4, new AIAerialTimedAttack<EntityMaelstromGauntlet>(this, 1.0f, 60, attackDistance + 20, 20, 0.8f, 20));
	ModUtils.removeTaskOfType(this.tasks, EntityAIWanderWithGroup.class);
	this.tasks.addTask(7, new AiFistWander(this, 80, 8));
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
	List<Consumer<EntityLivingBase>> attacks = new ArrayList<Consumer<EntityLivingBase>>(Arrays.asList(punch, lazer, defend, fireball));
	int numMinions = (int) ModUtils.getEntitiesInBox(this, getEntityBoundingBox().grow(20, 10, 20)).stream().filter((e) -> e instanceof EntityMaelstromMob).count();
	float healthRatio = this.getHealth() / this.getMaxHealth();
	double defendWeight = this.prevAttack == this.defend || numMinions > 3 || healthRatio > 0.55 ? 0 : 0.8;
	double fireballWeight = distanceSq < Math.pow(25, 2) && healthRatio < 0.85 ? 1 : 0;
	double lazerWeight = distanceSq < Math.pow(35, 2) && healthRatio < 0.7 ? 1 : 0;

	double[] weights = {
		Math.sqrt(distanceSq) / 25,
		lazerWeight,
		defendWeight,
		fireballWeight
	};
	this.prevAttack = ModRandom.choice(attacks, rand, weights).next();
	this.prevAttack.accept(target);
	return this.prevAttack == this.defend ? 240 : 100;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
	if (!this.damageFromEye && !source.isUnblockable()) {
	    return false;
	}
	this.damageFromEye = false;
	return super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage) {
	if (part == this.eye && !this.isPunching && !this.isDefending) {
	    this.damageFromEye = true;
	    return this.attackEntityFrom(source, damage);
	}

	if (damage > 0.0F && !source.isUnblockable()) {
	    if (!source.isProjectile()) {
		Entity entity = source.getImmediateSource();

		if (entity instanceof EntityLivingBase) {
		    this.blockUsingShield((EntityLivingBase) entity);
		}
	    }
	    this.playSound(SoundEvents.ENTITY_BLAZE_HURT, 1.0f, 0.6f + ModRandom.getFloat(0.2f));

	    return false;
	}

	return false;
    }

    @Override
    public void onLivingUpdate() {
	bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

	super.onLivingUpdate();
	Vec3d[] avec3d = new Vec3d[this.hitboxParts.length];
	for (int j = 0; j < this.hitboxParts.length; ++j) {
	    avec3d[j] = new Vec3d(this.hitboxParts[j].posX, this.hitboxParts[j].posY, this.hitboxParts[j].posZ);
	}

	/**
	 * Set the hitbox pieces based on the entity's rotation so that even large pitch rotations don't mess up the hitboxes
	 */
	Vec3d lookVec = ModUtils.getLookVec(this.getLook(), this.renderYawOffset);
	Vec3d rotationVector = ModUtils.rotateVector(lookVec, lookVec.crossProduct(new Vec3d(0, 1, 0)), 90);

	Vec3d eyePos = this.getPositionEyes(1).add(rotationVector.scale(-0.5)).add(ModUtils.getAxisOffset(lookVec, new Vec3d(-0.2, 0, 0)));
	this.eye.setLocationAndAngles(eyePos.x, eyePos.y, eyePos.z, this.rotationYaw, this.rotationPitch);

	Vec3d behindEyePos = this.getPositionEyes(1).add(rotationVector.scale(-0.5)).add(ModUtils.getAxisOffset(lookVec, new Vec3d(0.5, -0.1, 0)));
	this.behindEye.setLocationAndAngles(behindEyePos.x, behindEyePos.y, behindEyePos.z, this.rotationYaw, this.rotationPitch);

	Vec3d palmPos = this.getPositionEyes(1).add(rotationVector.scale(0.5)).add(ModUtils.getAxisOffset(lookVec, new Vec3d(0, 0, 0.5)));
	this.upLeftPalm.setLocationAndAngles(palmPos.x, palmPos.y, palmPos.z, this.rotationYaw, this.rotationPitch);

	palmPos = this.getPositionEyes(1).add(rotationVector.scale(0.5)).add(ModUtils.getAxisOffset(lookVec, new Vec3d(0, 0, -0.5)));
	this.upRightPalm.setLocationAndAngles(palmPos.x, palmPos.y, palmPos.z, this.rotationYaw, this.rotationPitch);

	palmPos = this.getPositionEyes(1).add(rotationVector.scale(-1.7));
	this.bottomPalm.setLocationAndAngles(palmPos.x, palmPos.y, palmPos.z, this.rotationYaw, this.rotationPitch);

	palmPos = this.getPositionEyes(1).add(rotationVector.scale(-0.4)).add(ModUtils.getAxisOffset(lookVec, new Vec3d(0, 0, 0.7)));
	this.leftPalm.setLocationAndAngles(palmPos.x, palmPos.y, palmPos.z, this.rotationYaw, this.rotationPitch);

	palmPos = this.getPositionEyes(1).add(rotationVector.scale(-0.4)).add(ModUtils.getAxisOffset(lookVec, new Vec3d(0, 0, -0.7)));
	this.rightPalm.setLocationAndAngles(palmPos.x, palmPos.y, palmPos.z, this.rotationYaw, this.rotationPitch);

	palmPos = this.getPositionEyes(1).add(rotationVector.scale(1.3));
	this.fingers.setLocationAndAngles(palmPos.x, palmPos.y, palmPos.z, this.rotationYaw, this.rotationPitch);

	Vec3d fistPos = this.getPositionVector().subtract(ModUtils.yVec(0.5));
	this.fist.setLocationAndAngles(fistPos.x, fistPos.y, fistPos.z, this.rotationYaw, this.rotationPitch);

	for (int l = 0; l < this.hitboxParts.length; ++l) {
	    this.hitboxParts[l].prevPosX = avec3d[l].x;
	    this.hitboxParts[l].prevPosY = avec3d[l].y;
	    this.hitboxParts[l].prevPosZ = avec3d[l].z;
	}

	if (world.isRemote) {
	    return;
	}

	if (this.isPunching) {
	    ModUtils.destroyBlocksInAABB(this.fist.getEntityBoundingBox(), world, this);
	    ModUtils.handleAreaImpact(1.3f, (e) -> this.getAttack(), this, this.getPositionEyes(1), ModDamageSource.causeElementalMeleeDamage(this, this.getElement()), 1.5f, 0, false);
	}

	if (this.isShootingLazer) {
	    if (this.getAttackTarget() != null) {
		Vec3d lazerShootPos = this.getAttackTarget().getPositionEyes(1).subtract(ModUtils.yVec(1));
		this.addEvent(() -> {

		    // Extend shooting beyond the target position up to 40 blocks
		    Vec3d lazerPos = lazerShootPos.add(lazerShootPos.subtract(this.getPositionEyes(1)).normalize().scale(40));
		    // Ray trace both blocks and entities
		    RayTraceResult raytraceresult = this.world.rayTraceBlocks(this.getPositionEyes(1), lazerPos, false, true, false);
		    if (raytraceresult != null) {
			world.createExplosion(this, raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z, 1, true);

			// If we hit a block, make sure that any collisions with entities are detected up to the hit block
			lazerPos = raytraceresult.hitVec;
		    }

		    for (Entity entity : ModUtils.findEntitiesInLine(this.getPositionEyes(1), lazerPos, world, this)) {
			entity.attackEntityFrom(ModDamageSource.causeElementalMagicDamage(this, null, this.getElement()), 4);
		    }

		    Main.network.sendToAllTracking(new MessageDirectionForRender(this, lazerPos), this);
		}, beamLag);
	    }
	    else {
		// Prevent the gauntlet from instantly locking onto other targets with the lazer.
		this.isShootingLazer = false;
		this.addEvent(() -> {
		    world.setEntityState(this, stopLazerByte);
		}, beamLag + 1);
	    }
	}

	if (this.isDefending) {
	    world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
	}
    }

    @Override
    public void doRender(RenderManager renderManager, double x, double y, double z, float entityYaw, float partialTicks) {
	if (this.renderLazerPos != null) {
	    // This sort of jenky way of binding the wrong texture to the original guardian beam creates quite a nice particle beam visual
	    renderManager.renderEngine.bindTexture(RenderDragon.ENDERCRYSTAL_BEAM_TEXTURES);
	    // We must interpolate between positions to make the move smoothly
	    Vec3d interpolatedPos = renderLazerPos.subtract(this.prevRenderLazerPos).scale(partialTicks).add(prevRenderLazerPos);
	    RenderUtils.drawBeam(renderManager, this.getPositionEyes(1), interpolatedPos, new Vec3d(x, y, z), ModColors.RED, this, partialTicks);
	}
	super.doRender(renderManager, x, y, z, entityYaw, partialTicks);
    }

    @Override
    public float getEyeHeight() {
	return 1.6f;
    }

    public EntityLeveledMob setLook(Vec3d look) {
	float prevLook = this.getLook();
	float newLook = (float) ModUtils.toPitch(look);
	float deltaLook = 5;
	float clampedLook = MathHelper.clamp(newLook, prevLook - deltaLook, prevLook + deltaLook);
	this.dataManager.set(LOOK, clampedLook);
	return this;
    }

    public float getLook() {
	return this.dataManager == null ? 0 : this.dataManager.get(LOOK);
    }

    @Override
    public void handleStatusUpdate(byte id) {
	if (id == stopLazerByte) {
	    this.renderLazerPos = null;
	}
	else if (id == ModUtils.PARTICLE_BYTE) {
	    // Render particles in a sucking in motion
	    for (int i = 0; i < 5; i++) {
		Vec3d lookVec = ModUtils.getLookVec(this.getLook(), this.renderYawOffset);
		Vec3d randOffset = ModUtils.rotateVector(lookVec, lookVec.crossProduct(new Vec3d(0, 1, 0)), ModRandom.range(-70, 70));
		randOffset = ModUtils.rotateVector(randOffset, lookVec, ModRandom.range(0, 360)).scale(1.5f);
		Vec3d velocity = Vec3d.ZERO.subtract(randOffset).normalize().scale(0.15f).add(new Vec3d(this.motionX, this.motionY, this.motionZ));
		Vec3d particlePos = this.getPositionEyes(1).add(ModUtils.getAxisOffset(lookVec, new Vec3d(-2, 0, 0))).add(randOffset);
		ParticleManager.spawnDust(world, particlePos, ModColors.RED, velocity, ModRandom.range(5, 7));
	    }
	}
	else if (id == ModUtils.SECOND_PARTICLE_BYTE) {
	    // Render particles in some weird circular trig fashion
	    ModUtils.circleCallback(2, 16, (pos) -> {
		pos = new Vec3d(pos.x, 0, pos.y).add(this.getPositionVector());
		double y = Math.cos(pos.x + pos.z);
		ParticleManager.spawnSplit(world, pos.add(ModUtils.yVec(y)), ModColors.PURPLE, ModUtils.yVec(-y * 0.1));
	    });
	}
	else if (id == ModUtils.THIRD_PARTICLE_BYTE) {
	    for (int i = 0; i < 2; i++) {
		Vec3d pos = this.getPositionVector().add(ModRandom.gaussVec());
		world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, pos.x, pos.y, pos.z, 0, 0, 0);
	    }
	}
	super.handleStatusUpdate(id);
    }

    @Override
    public void onDeath(DamageSource cause) {
	if (!world.isRemote) {

	    for (int i = 0; i < 15; i++) {
		final int i_final = i;
		world.newExplosion(this, this.posX, this.posY + i_final * 2, this.posZ, 2, false, false);
	    }

	    new WorldGenGauntletSpike().generate(world, this.getRNG(), this.getPosition().add(new BlockPos(-3, 0, -3)));
	    super.onDeath(cause);
	}
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
	if (this.isInWater()) {
	    this.moveRelative(strafe, vertical, forward, 0.02F);
	    this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	    this.motionX *= 0.800000011920929D;
	    this.motionY *= 0.800000011920929D;
	    this.motionZ *= 0.800000011920929D;
	}
	else if (this.isInLava()) {
	    this.moveRelative(strafe, vertical, forward, 0.02F);
	    this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	    this.motionX *= 0.5D;
	    this.motionY *= 0.5D;
	    this.motionZ *= 0.5D;
	}
	else {
	    float f = 0.91F;

	    if (this.onGround) {
		BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
		IBlockState underState = this.world.getBlockState(underPos);
		f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
	    }

	    float f1 = 0.16277136F / (f * f * f);
	    this.moveRelative(strafe, vertical, forward, this.onGround ? 0.1F * f1 : 0.02F);
	    f = 0.91F;

	    if (this.onGround) {
		BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
		IBlockState underState = this.world.getBlockState(underPos);
		f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
	    }

	    this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	    this.motionX *= f;
	    this.motionY *= f;
	    this.motionZ *= f;
	}

	this.prevLimbSwingAmount = this.limbSwingAmount;
	double d1 = this.posX - this.prevPosX;
	double d0 = this.posZ - this.prevPosZ;
	float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

	if (f2 > 1.0F) {
	    f2 = 1.0F;
	}

	this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
	this.limbSwing += this.limbSwingAmount;
    }

    /**
     * Add a bit of brightness to the entity, because otherwise it looks pretty black
     */
    @Override
    public int getBrightnessForRender() {
	return Math.min(super.getBrightnessForRender() + 60, 200);
    }

    @Override
    protected void entityInit() {
	this.dataManager.register(LOOK, Float.valueOf(0));
	super.entityInit();
    }

    @Override
    public EntitySenses getEntitySenses() {
	return this.senses;
    }

    @Override
    public World getWorld() {
	return world;
    }

    @Override
    public Entity[] getParts() {
	return this.hitboxParts;
    }

    @Override
    protected boolean canDespawn() {
	return false;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }

    @Override
    public boolean isOnLadder() {
	return false;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    }

    @Override
    public boolean canBeCollidedWith() {
	return false;
    }

    /**
     * This is overriden because we do want the main hitbox to clip with blocks while still not clipping with anything else
     */
    @Override
    public void move(MoverType type, double x, double y, double z) {
	this.world.profiler.startSection("move");

	if (this.isInWeb) {
	    this.isInWeb = false;
	    x *= 0.25D;
	    y *= 0.05000000074505806D;
	    z *= 0.25D;
	    this.motionX = 0.0D;
	    this.motionY = 0.0D;
	    this.motionZ = 0.0D;
	}

	double d2 = x;
	double d3 = y;
	double d4 = z;

	List<AxisAlignedBB> list1 = this.world.getCollisionBoxes(this, this.getEntityBoundingBox().expand(x, y, z));
	AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();

	if (y != 0.0D) {
	    int k = 0;

	    for (int l = list1.size(); k < l; ++k) {
		y = list1.get(k).calculateYOffset(this.getEntityBoundingBox(), y);
	    }

	    this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, y, 0.0D));
	}

	if (x != 0.0D) {
	    int j5 = 0;

	    for (int l5 = list1.size(); j5 < l5; ++j5) {
		x = list1.get(j5).calculateXOffset(this.getEntityBoundingBox(), x);
	    }

	    if (x != 0.0D) {
		this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, 0.0D, 0.0D));
	    }
	}

	if (z != 0.0D) {
	    int k5 = 0;

	    for (int i6 = list1.size(); k5 < i6; ++k5) {
		z = list1.get(k5).calculateZOffset(this.getEntityBoundingBox(), z);
	    }

	    if (z != 0.0D) {
		this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, 0.0D, z));
	    }
	}

	boolean flag = this.onGround || d3 != y && d3 < 0.0D;

	if (this.stepHeight > 0.0F && flag && (d2 != x || d4 != z)) {
	    double d14 = x;
	    double d6 = y;
	    double d7 = z;
	    AxisAlignedBB axisalignedbb1 = this.getEntityBoundingBox();
	    this.setEntityBoundingBox(axisalignedbb);
	    y = this.stepHeight;
	    List<AxisAlignedBB> list = this.world.getCollisionBoxes(this, this.getEntityBoundingBox().expand(d2, y, d4));
	    AxisAlignedBB axisalignedbb2 = this.getEntityBoundingBox();
	    AxisAlignedBB axisalignedbb3 = axisalignedbb2.expand(d2, 0.0D, d4);
	    double d8 = y;
	    int j1 = 0;

	    for (int k1 = list.size(); j1 < k1; ++j1) {
		d8 = list.get(j1).calculateYOffset(axisalignedbb3, d8);
	    }

	    axisalignedbb2 = axisalignedbb2.offset(0.0D, d8, 0.0D);
	    double d18 = d2;
	    int l1 = 0;

	    for (int i2 = list.size(); l1 < i2; ++l1) {
		d18 = list.get(l1).calculateXOffset(axisalignedbb2, d18);
	    }

	    axisalignedbb2 = axisalignedbb2.offset(d18, 0.0D, 0.0D);
	    double d19 = d4;
	    int j2 = 0;

	    for (int k2 = list.size(); j2 < k2; ++j2) {
		d19 = list.get(j2).calculateZOffset(axisalignedbb2, d19);
	    }

	    axisalignedbb2 = axisalignedbb2.offset(0.0D, 0.0D, d19);
	    AxisAlignedBB axisalignedbb4 = this.getEntityBoundingBox();
	    double d20 = y;
	    int l2 = 0;

	    for (int i3 = list.size(); l2 < i3; ++l2) {
		d20 = list.get(l2).calculateYOffset(axisalignedbb4, d20);
	    }

	    axisalignedbb4 = axisalignedbb4.offset(0.0D, d20, 0.0D);
	    double d21 = d2;
	    int j3 = 0;

	    for (int k3 = list.size(); j3 < k3; ++j3) {
		d21 = list.get(j3).calculateXOffset(axisalignedbb4, d21);
	    }

	    axisalignedbb4 = axisalignedbb4.offset(d21, 0.0D, 0.0D);
	    double d22 = d4;
	    int l3 = 0;

	    for (int i4 = list.size(); l3 < i4; ++l3) {
		d22 = list.get(l3).calculateZOffset(axisalignedbb4, d22);
	    }

	    axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d22);
	    double d23 = d18 * d18 + d19 * d19;
	    double d9 = d21 * d21 + d22 * d22;

	    if (d23 > d9) {
		x = d18;
		z = d19;
		y = -d8;
		this.setEntityBoundingBox(axisalignedbb2);
	    }
	    else {
		x = d21;
		z = d22;
		y = -d20;
		this.setEntityBoundingBox(axisalignedbb4);
	    }

	    int j4 = 0;

	    for (int k4 = list.size(); j4 < k4; ++j4) {
		y = list.get(j4).calculateYOffset(this.getEntityBoundingBox(), y);
	    }

	    this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, y, 0.0D));

	    if (d14 * d14 + d7 * d7 >= x * x + z * z) {
		x = d14;
		y = d6;
		z = d7;
		this.setEntityBoundingBox(axisalignedbb1);
	    }
	}

	this.world.profiler.endSection();
	this.world.profiler.startSection("rest");
	this.resetPositionToBB();
	this.collidedHorizontally = d2 != x || d4 != z;
	this.collidedVertically = d3 != y;
	this.onGround = this.collidedVertically && d3 < 0.0D;
	this.collided = this.collidedHorizontally || this.collidedVertically;
	int j6 = MathHelper.floor(this.posX);
	int i1 = MathHelper.floor(this.posY - 0.20000000298023224D);
	int k6 = MathHelper.floor(this.posZ);
	BlockPos blockpos = new BlockPos(j6, i1, k6);
	IBlockState iblockstate = this.world.getBlockState(blockpos);

	if (iblockstate.getMaterial() == Material.AIR) {
	    BlockPos blockpos1 = blockpos.down();
	    IBlockState iblockstate1 = this.world.getBlockState(blockpos1);
	    Block block1 = iblockstate1.getBlock();

	    if (block1 instanceof BlockFence || block1 instanceof BlockWall || block1 instanceof BlockFenceGate) {
		iblockstate = iblockstate1;
		blockpos = blockpos1;
	    }
	}

	this.updateFallState(y, this.onGround, iblockstate, blockpos);

	if (d2 != x) {
	    this.motionX = 0.0D;
	}

	if (d4 != z) {
	    this.motionZ = 0.0D;
	}

	Block block = iblockstate.getBlock();

	if (d3 != y) {
	    block.onLanded(this.world, this);
	}

	try {
	    this.doBlockCollisions();
	}
	catch (Throwable throwable) {
	    CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
	    CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
	    this.addEntityCrashInfo(crashreportcategory);
	    throw new ReportedException(crashreport);
	}

	this.world.profiler.endSection();
    }

    @Override
    public void setRenderDirection(Vec3d dir) {
	if (this.renderLazerPos != null) {
	    this.prevRenderLazerPos = this.renderLazerPos;
	}
	else {
	    this.prevRenderLazerPos = dir;
	}
	this.renderLazerPos = dir;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
	if (this.hasCustomName()) {
	    this.bossInfo.setName(this.getDisplayName());
	}

	super.readEntityFromNBT(compound);
    }

    @Override
    public void setCustomNameTag(String name) {
	super.setCustomNameTag(name);
	this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player) {
	super.addTrackingPlayer(player);
	this.bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
	super.removeTrackingPlayer(player);
	this.bossInfo.removePlayer(player);
    }
}
