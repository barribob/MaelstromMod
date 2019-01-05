package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHorrorAttack;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityHorror extends EntityMaelstromMob
{
    public static final float PROJECTILE_INACCURACY = 0;
    public static final float PROJECTILE_VELOCITY = 0.5f;
    public static final float PROJECTILE_AMOUNT = 8;
    public static final float PROJECTILE_VARIATION_FACTOR = 0.5f;

    public EntityHorror(World worldIn)
    {
	super(worldIn);
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
	this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(14);
	this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(8);
    }

    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttack<EntityMaelstromMob>(this, 1.0f, 100, 5.0f));
    }

    /**
     * Spawns smoke out of the middle of the entity
     */
    @Override
    public void onUpdate()
    {
	super.onUpdate();
	if (world.isRemote)
	{
	    for (int i = 0; i < 5; i++)
	    {
		ParticleManager.spawnMaelstromSmoke(world, rand, new Vec3d(this.posX + ModRandom.getFloat(0.7f), this.posY + 1, this.posZ + ModRandom.getFloat(0.7f)));
	    }
	}
    }

    /**
     * Launches 8 projectiles upwards randomly
     */
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	if (!world.isRemote)
	{
	    for (int i = 0; i < PROJECTILE_AMOUNT; i++)
	    {
		ProjectileHorrorAttack projectile = new ProjectileHorrorAttack(this.world, this);
		double xDir = (rand.nextFloat() - rand.nextFloat()) * PROJECTILE_VARIATION_FACTOR;
		double yDir = 1;
		double zDir = (rand.nextFloat() - rand.nextFloat()) * PROJECTILE_VARIATION_FACTOR;
		projectile.shoot(xDir, yDir, zDir, PROJECTILE_VELOCITY, PROJECTILE_INACCURACY);
		this.playSound(SoundEvents.BLOCK_ANVIL_BREAK, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.world.spawnEntity(projectile);
	    }
	}
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
	return SoundsHandler.ENTITY_HORROR_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundsHandler.ENTITY_HORROR_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
	return SoundsHandler.ENTITY_HORROR_DEATH;
    }

    @Override
    protected float getSoundVolume()
    {
	return 0.5f;
    }
}
