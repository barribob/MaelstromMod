package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.animation.Animation;
import com.barribob.MaelstromMod.entity.animation.AnimationNone;
import com.barribob.MaelstromMod.util.IAnimatedMob;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * Base class that serves as the flying version of EntityLeveledMob
 */
public abstract class EntityLeveledFlyingMob extends EntityFlying implements IMob, IRangedAttackMob, IAnimatedMob
{
    @SideOnly(Side.CLIENT)
    protected Animation currentAnimation;
    private float level;

    public EntityLeveledFlyingMob(World worldIn)
    {
	super(worldIn);
	this.level = 2;
    }

    @Override
    public void onLivingUpdate()
    {
	super.onLivingUpdate();

	if (world.isRemote && currentAnimation != null)
	{
	    currentAnimation.update();
	}
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();

	if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
	{
	    this.setDead();
	}

	/**
	 * Periodically check if the animations need to be reinitialized
	 */
	if (this.ticksExisted % 20 == 0)
	{
	    world.setEntityState(this, animationByte);
	}
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
	world.setEntityState(this, animationByte);

	super.readFromNBT(compound);
    }

    public float getAttack()
    {
	return ModUtils.getMobDamage(this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue(), this.level);
    }

    @Override
    public Animation getCurrentAnimation()
    {
	return this.currentAnimation == null ? new AnimationNone() : this.currentAnimation;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
	if (!source.isUnblockable())
	{
	    amount = amount * LevelHandler.getArmorFromLevel(level - 1);
	}
	return super.attackEntityFrom(source, amount);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == animationByte && currentAnimation == null)
	{
	    initAnimation();
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }

    @SideOnly(Side.CLIENT)
    protected void initAnimation()
    {
    }
}
