package com.barribob.MaelstromMod.entity.entities.gauntlet;

import com.barribob.MaelstromMod.entity.ai.AIAerialTimedAttack;
import com.barribob.MaelstromMod.entity.ai.FlyingMoveHelper;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityMaelstromGauntlet extends EntityMaelstromMob implements IAttack, IEntityMultiPart
{
    // We keep track of the look ourselves because minecraft's look is clamped
    protected static final DataParameter<Float> LOOK = EntityDataManager.<Float>createKey(EntityLeveledMob.class, DataSerializers.FLOAT);
    private MultiPartEntityPart[] hitboxParts;
    private float boxSize = 0.8f;
    private MultiPartEntityPart eye = new MultiPartEntityPart(this, "eye", boxSize, boxSize);
    private MultiPartEntityPart bottomPalm = new MultiPartEntityPart(this, "bottomPalm", 1, 1);
    private MultiPartEntityPart upLeftPalm = new MultiPartEntityPart(this, "upLeftPalm", boxSize, boxSize);
    private MultiPartEntityPart upRightPalm = new MultiPartEntityPart(this, "upRightPalm", boxSize, boxSize);
    private MultiPartEntityPart rightPalm = new MultiPartEntityPart(this, "rightPalm", boxSize, boxSize);
    private MultiPartEntityPart leftPalm = new MultiPartEntityPart(this, "leftPalm", boxSize, boxSize);
    private MultiPartEntityPart fingers = new MultiPartEntityPart(this, "fingers", 1, 1);

    public EntityMaelstromGauntlet(World worldIn)
    {
	super(worldIn);
	this.moveHelper = new FlyingMoveHelper(this);
	this.navigator = new PathNavigateFlying(this, worldIn);
	this.hitboxParts = new MultiPartEntityPart[] { eye, bottomPalm, upLeftPalm, upRightPalm, rightPalm, leftPalm, fingers };
	this.setSize(3, 5);
	this.noClip = true;
	this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(250);
	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26f);
	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(9f);
    }

    @Override
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new AIAerialTimedAttack<EntityMaelstromGauntlet>(this, 1.0f, 60, 20, 0.8f));
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards)
    {
	return 0;
    }

    @Override
    public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage)
    {
	if (part == this.eye)
	{
	    return this.attackEntityFrom(source, damage);
	}

	return false;
    }

    @Override
    public void onLivingUpdate()
    {
	super.onLivingUpdate();
	Vec3d[] avec3d = new Vec3d[this.hitboxParts.length];
	for (int j = 0; j < this.hitboxParts.length; ++j)
	{
	    avec3d[j] = new Vec3d(this.hitboxParts[j].posX, this.hitboxParts[j].posY, this.hitboxParts[j].posZ);
	}

	/**
	 * Set the hitbox pieces based on the entity's rotation so that even large pitch rotations don't mess up the hitboxes
	 */
	Vec3d yawVec = ModUtils.rotateVector(new Vec3d(0, 0, -1), new Vec3d(0, 1, 0), -this.rotationYaw);
	Vec3d lookVec = ModUtils.rotateVector(yawVec, yawVec.crossProduct(new Vec3d(0, 1, 0)), this.getLook());
	Vec3d rotationVector = ModUtils.rotateVector(lookVec, lookVec.crossProduct(new Vec3d(0, 1, 0)), 90);

	Vec3d eyePos = this.getPositionEyes(1).add(rotationVector.scale(-0.5)).add(ModUtils.getAxisOffset(lookVec, new Vec3d(-0.2, 0, 0)));
	this.eye.setLocationAndAngles(eyePos.x, eyePos.y, eyePos.z, this.rotationYaw, this.rotationPitch);

	Vec3d palmPos = this.getPositionEyes(1).add(rotationVector.scale(0.5)).add(ModUtils.getAxisOffset(lookVec, new Vec3d(0, 0, 0.5)));
	this.upLeftPalm.setLocationAndAngles(palmPos.x, palmPos.y, palmPos.z, this.rotationYaw, this.rotationPitch);

	palmPos = this.getPositionEyes(1).add(rotationVector.scale(0.5)).add(ModUtils.getAxisOffset(lookVec, new Vec3d(0, 0, -0.5)));
	this.upRightPalm.setLocationAndAngles(palmPos.x, palmPos.y, palmPos.z, this.rotationYaw, this.rotationPitch);

	palmPos = this.getPositionEyes(1).add(rotationVector.scale(-1.7));
	this.bottomPalm.setLocationAndAngles(palmPos.x, palmPos.y, palmPos.z, this.rotationYaw, this.rotationPitch);
	this.bottomPalm.width = 1.2f;
	this.bottomPalm.height = 1.2f;

	palmPos = this.getPositionEyes(1).add(rotationVector.scale(-0.4)).add(ModUtils.getAxisOffset(lookVec, new Vec3d(0, 0, 0.7)));
	this.leftPalm.setLocationAndAngles(palmPos.x, palmPos.y, palmPos.z, this.rotationYaw, this.rotationPitch);

	palmPos = this.getPositionEyes(1).add(rotationVector.scale(-0.4)).add(ModUtils.getAxisOffset(lookVec, new Vec3d(0, 0, -0.7)));
	this.rightPalm.setLocationAndAngles(palmPos.x, palmPos.y, palmPos.z, this.rotationYaw, this.rotationPitch);

	palmPos = this.getPositionEyes(1).add(rotationVector.scale(1.3));
	this.fingers.setLocationAndAngles(palmPos.x, palmPos.y, palmPos.z, this.rotationYaw, this.rotationPitch);
	this.fingers.width = 1.2f;
	this.fingers.height = 1.2f;

	for (int l = 0; l < this.hitboxParts.length; ++l)
	{
	    this.hitboxParts[l].prevPosX = avec3d[l].x;
	    this.hitboxParts[l].prevPosY = avec3d[l].y;
	    this.hitboxParts[l].prevPosZ = avec3d[l].z;
	}
    }

    @Override
    public float getEyeHeight()
    {
	return 1.6f;
    }

    public EntityLeveledMob setLook(Vec3d look)
    {
	float prevLook = this.getLook();
	float newLook = (float) ModUtils.toPitch(look);
	float deltaLook = 1;
	float clampedLook = MathHelper.clamp(newLook, prevLook - deltaLook, prevLook + deltaLook);
	this.dataManager.set(LOOK, clampedLook);
	return this;
    }

    public float getLook()
    {
	return this.dataManager == null ? 0 : this.dataManager.get(LOOK);
    }

    @Override
    public void travel(float strafe, float vertical, float forward)
    {
	if (this.isInWater())
	{
	    this.moveRelative(strafe, vertical, forward, 0.02F);
	    this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	    this.motionX *= 0.800000011920929D;
	    this.motionY *= 0.800000011920929D;
	    this.motionZ *= 0.800000011920929D;
	}
	else if (this.isInLava())
	{
	    this.moveRelative(strafe, vertical, forward, 0.02F);
	    this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	    this.motionX *= 0.5D;
	    this.motionY *= 0.5D;
	    this.motionZ *= 0.5D;
	}
	else
	{
	    float f = 0.91F;

	    if (this.onGround)
	    {
		BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
		IBlockState underState = this.world.getBlockState(underPos);
		f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
	    }

	    float f1 = 0.16277136F / (f * f * f);
	    this.moveRelative(strafe, vertical, forward, this.onGround ? 0.1F * f1 : 0.02F);
	    f = 0.91F;

	    if (this.onGround)
	    {
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

	if (f2 > 1.0F)
	{
	    f2 = 1.0F;
	}

	this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
	this.limbSwing += this.limbSwingAmount;
    }

    @Override
    protected void entityInit()
    {
	this.dataManager.register(LOOK, Float.valueOf(0));
	super.entityInit();
    }

    @Override
    public World getWorld()
    {
	return world;
    }

    @Override
    public Entity[] getParts()
    {
	return this.hitboxParts;
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {
    }

    @Override
    public boolean isOnLadder()
    {
	return false;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
    }

    @Override
    public boolean canBeCollidedWith()
    {
	return false;
    }
}
