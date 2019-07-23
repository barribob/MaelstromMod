package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHorrorAttack;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
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
	this.setSize(1.2F, 1.2F);
	this.setLevel(1.5f);
    }
    
    
    
    @Override
    protected void updateAttributes()
    {
	this.setBaseMaxHealth(25);
	this.setBaseAttack(7f);
    }

    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttack<EntityMaelstromMob>(this, 1.0f, 20, 5.0f, 0.5f));
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
		ParticleManager.spawnMaelstromSmoke(world, rand, new Vec3d(this.posX + ModRandom.getFloat(0.4f), this.posY + 1, this.posZ + ModRandom.getFloat(0.4f)), true);
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
	    ProjectileHorrorAttack projectile = new ProjectileHorrorAttack(this.world, this, this.getAttack());
	    double xDir = (rand.nextFloat() - rand.nextFloat()) * PROJECTILE_VARIATION_FACTOR;
	    double yDir = 1;
	    double zDir = (rand.nextFloat() - rand.nextFloat()) * PROJECTILE_VARIATION_FACTOR;
	    projectile.shoot(xDir, yDir, zDir, PROJECTILE_VELOCITY, PROJECTILE_INACCURACY);
	    this.playSound(SoundEvents.BLOCK_ANVIL_BREAK, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	    this.world.spawnEntity(projectile);
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
    protected ResourceLocation getLootTable()
    {
	return LootTableHandler.HORROR;
    }

    @Override
    protected float getSoundVolume()
    {
	return 0.25f;
    }
}
