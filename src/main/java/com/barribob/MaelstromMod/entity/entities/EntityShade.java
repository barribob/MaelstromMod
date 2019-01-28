package com.barribob.MaelstromMod.entity.entities;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileShadeAttack;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * 
 * Represent the attibutes and logic of the shade monster
 *
 */
public class EntityShade extends EntityMaelstromMob
{
    public static final float PROJECTILE_INACCURACY = 0;
    public static final float PROJECTILE_VELOCITY = 1.0f;

    public EntityShade(World worldIn)
    {
	super(worldIn);
    }

    @Override
    protected void updateAttributes()
    {
	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D * this.getProgressionMultiplier());
    }
    
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttack<EntityMaelstromMob>(this, 1.0f, 20, 3.0f));
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
	return SoundsHandler.ENTITY_SHADE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundsHandler.ENTITY_SHADE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
	return SoundsHandler.ENTITY_SHADE_DEATH;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
	return LootTableHandler.SHADE;
    }

    /**
     * Shoots a projectile in a similar fashion to the snow golem (see
     * EntitySnowman)
     */
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	if (!world.isRemote)
	{
	    ProjectileShadeAttack projectile = new ProjectileShadeAttack(this.world, this, 3 * this.getProgressionMultiplier());
	    double d0 = target.posY + (double) target.getEyeHeight() - 1.100000023841858D;
	    double xDir = target.posX - this.posX;
	    double yDir = d0 - projectile.posY;
	    double zDir = target.posZ - this.posZ;
	    float f = MathHelper.sqrt(xDir * xDir + zDir * zDir) * 0.2F;
	    yDir = Math.min(yDir + f, 0); // Keep the entity from aiming upward
	    projectile.shoot(xDir, yDir, zDir, PROJECTILE_VELOCITY, PROJECTILE_INACCURACY);
	    this.playSound(SoundEvents.BLOCK_ANVIL_BREAK, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	    this.world.spawnEntity(projectile);
	}
    }
}