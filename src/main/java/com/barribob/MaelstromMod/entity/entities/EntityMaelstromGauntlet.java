package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.AIAerialTimedAttack;
import com.barribob.MaelstromMod.entity.ai.FlyingMoveHelper;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityMaelstromGauntlet extends EntityMaelstromMob implements IAttack
{
    // We keep track of the look ourselves because minecraft's look is clamped
    protected static final DataParameter<Float> LOOK = EntityDataManager.<Float>createKey(EntityLeveledMob.class, DataSerializers.FLOAT);

    public EntityMaelstromGauntlet(World worldIn)
    {
	super(worldIn);
	this.moveHelper = new FlyingMoveHelper(this);
	this.navigator = new PathNavigateFlying(this, worldIn);
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
}
