package com.barribob.MaelstromMod.entity.entities;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

/**
 * 
 * The base mob that most mobs in this mod will extend A lot of these methods
 * are from the EntityMob class to make it behave similarly
 *
 */
public abstract class EntityMaelstromMob extends EntityCreature implements IRangedAttackMob
{
    // Swinging arms is the animation for the attack
    private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(EntityMaelstromMob.class, DataSerializers.BOOLEAN);
    private float level;

    public EntityMaelstromMob(World worldIn)
    {
	super(worldIn);
	this.setLevel(1);
    }

    protected void initEntityAI()
    {
	this.tasks.addTask(1, new EntityAISwimming(this));
	this.tasks.addTask(2, new EntityAIRestrictSun(this));
	this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
	this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
	this.tasks.addTask(6, new EntityAILookIdle(this));
	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));

	// This makes it so that the entity attack every entity except others of its
	// kind
	this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 1, true, true, new Predicate<Entity>()
	{
	    public boolean apply(@Nullable Entity entity)
	    {
		return !(entity instanceof EntityMaelstromMob);
	    }
	}));
    }
    
    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
    }

    public SoundCategory getSoundCategory()
    {
	return SoundCategory.HOSTILE;
    }

    protected SoundEvent getSwimSound()
    {
	return SoundEvents.ENTITY_HOSTILE_SWIM;
    }

    protected SoundEvent getSplashSound()
    {
	return SoundEvents.ENTITY_HOSTILE_SPLASH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundEvents.ENTITY_HOSTILE_HURT;
    }

    protected SoundEvent getDeathSound()
    {
	return SoundEvents.ENTITY_HOSTILE_DEATH;
    }

    protected SoundEvent getFallSound(int heightIn)
    {
	return heightIn > 4 ? SoundEvents.ENTITY_HOSTILE_BIG_FALL : SoundEvents.ENTITY_HOSTILE_SMALL_FALL;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this
     * entity.
     */
    public boolean getCanSpawnHere()
    {
	return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && super.getCanSpawnHere();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
	super.onUpdate();

	if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
	{
	    this.setDead();
	}
    }

    /**
     * Entity won't drop items or experience points if this returns false
     */
    protected boolean canDropLoot()
    {
	return true;
    }

    protected void entityInit()
    {
	super.entityInit();
	this.dataManager.register(SWINGING_ARMS, Boolean.valueOf(false));
    }

    public boolean isSwingingArms()
    {
	return ((Boolean) this.dataManager.get(SWINGING_ARMS)).booleanValue();
    }

    public void setSwingingArms(boolean swingingArms)
    {
	this.dataManager.set(SWINGING_ARMS, Boolean.valueOf(swingingArms));
    }

    /**
     * Changes the default "white smoke" spawning from a mob spawner to a purple
     * smoke
     */
    @Override
    public void spawnExplosionParticle()
    {
	if (this.world.isRemote)
	{
	    for (int i = 0; i < 20; ++i)
	    {
		double d0 = this.rand.nextGaussian() * 0.02D;
		double d1 = this.rand.nextGaussian() * 0.02D;
		double d2 = this.rand.nextGaussian() * 0.02D;
		ParticleManager.spawnMaelstromLargeSmoke(world, rand,
			new Vec3d(this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width - d0 * 10.0D,
				this.posY + (double) (this.rand.nextFloat() * this.height) - d1 * 10.0D,
				this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width - d2 * 10.0D));
	    }
	}
	else
	{
	    this.world.setEntityState(this, (byte) 20);
	}
    }

    public float getLevel()
    {
	return this.level;
    }
    
    /**
     * Sets the level, updates attributes, and set health to the updated max health
     */
    public void setLevel(float level)
    {
	this.level = level;
	this.updateAttributes();
	this.setHealth(this.getMaxHealth());
    }
    
    protected abstract void updateAttributes();
    
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
	compound.setFloat("level", level);
	super.writeEntityToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
	if (compound.hasKey("level"))
	{
	    this.setLevel(compound.getFloat("level"));
	}
	super.readEntityFromNBT(compound);
    }

    /**
     * Get the progression multiplier based on the level of the entity
     */
    public float getProgressionMultiplier()
    {
	return LevelHandler.getMultiplierFromLevel(this.getLevel());
    }
}
