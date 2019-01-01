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
import net.minecraft.entity.IEntityLivingData;
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
import net.minecraft.entity.monster.EntitySpellcasterIllager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

/**
 * 
 * The illager summoner boss
 *
 */
public class EntityMaelstromIllager extends EntityMaelstromMob
{
    private boolean summonedBeast;

    // Responsible for the boss bar
    private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_20));

    public EntityMaelstromIllager(World worldIn)
    {
	super(worldIn);
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
	this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(14);
	this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(8);
    }

    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttack<EntityMaelstromMob>(this, 1.0f, 180, 15.0f));
    }

    protected SoundEvent getAmbientSound()
    {
	return SoundEvents.ENTITY_EVOCATION_ILLAGER_AMBIENT;
    }

    protected SoundEvent getDeathSound()
    {
	return SoundEvents.EVOCATION_ILLAGER_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundEvents.ENTITY_EVOCATION_ILLAGER_HURT;
    }
    
    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return false;
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
	if (!summonedBeast && this.getHealth() < 30 && this.spawnMinion(new EntityBeast(this.world)))
	{
	    summonedBeast = true;
	}
	else if (rand.nextInt(2) == 0)
	{
	    this.spawnMinion(new EntityShade(this.world));
	}
	else
	{
	    this.spawnMinion(new EntityHorror(this.world));
	}
    }

    /**
     * Spawn summoming particles
     */
    public void onUpdate()
    {
	super.onUpdate();

	if (this.world.isRemote && this.isSwingingArms())
	{
	    float f = this.renderYawOffset * 0.017453292F + MathHelper.cos((float) this.ticksExisted * 0.6662F) * 0.25F;
	    float f1 = MathHelper.cos(f);
	    float f2 = MathHelper.sin(f);
	    ParticleManager.spawnMaelstromPotionParticle(world, rand, new Vec3d(this.posX + (double) f1 * 0.6D, this.posY + 1.8D, this.posZ + (double) f2 * 0.6D));
	    ParticleManager.spawnMaelstromPotionParticle(world, rand, new Vec3d(this.posX - (double) f1 * 0.6D, this.posY + 1.8D, this.posZ - (double) f2 * 0.6D));
	}
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
	compound.setBoolean("SummonedBeast", summonedBeast);
	super.writeEntityToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
	if (compound.hasKey("SummonedBeast"))
	{
	    summonedBeast = compound.getBoolean("SummonedBeast");
	}

	if (this.hasCustomName())
	{
	    this.bossInfo.setName(this.getDisplayName());
	}

	super.readEntityFromNBT(compound);
    }

    /**
     * Try to spawn a minion to aid in battle. Taken from the zombie spawn
     * reinforcements code
     */
    private boolean spawnMinion(EntityMaelstromMob mob)
    {
	int tries = 50;
	for (int i = 0; i < tries; i++)
	{
	    // Find a random position to spawn the enemy
	    int i1 = (int) this.posX + MathHelper.getInt(this.rand, 2, 4) * MathHelper.getInt(this.rand, -1, 1);
	    int j1 = (int) this.posY + MathHelper.getInt(this.rand, 0, 4) * MathHelper.getInt(this.rand, -1, 1);
	    int k1 = (int) this.posZ + MathHelper.getInt(this.rand, 2, 4) * MathHelper.getInt(this.rand, -1, 1);

	    if (this.world.getBlockState(new BlockPos(i1, j1 - 1, k1)).isSideSolid(this.world, new BlockPos(i1, j1 - 1, k1), net.minecraft.util.EnumFacing.UP))
	    {
		mob.setPosition((double) i1, (double) j1, (double) k1);

		// Make sure that the position is a proper spawning position
		if (!this.world.isAnyPlayerWithinRangeAt((double) i1, (double) j1, (double) k1, 5.0D) && this.world.checkNoEntityCollision(mob.getEntityBoundingBox(), mob)
			&& this.world.getCollisionBoxes(mob, mob.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(mob.getEntityBoundingBox()))
		{
		    // Spawn the entity
		    this.world.spawnEntity(mob);
		    if (this.getAttackTarget() != null)
		    {
			mob.setAttackTarget(this.getAttackTarget());
		    }
		    mob.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(mob)), (IEntityLivingData) null);
		    mob.spawnExplosionParticle();
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * Sets the custom name tag for this entity
     */
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

    /**
     * Add the given player to the list of players tracking this entity. For
     * instance, a player may track a boss in order to view its associated boss bar.
     */
    public void addTrackingPlayer(EntityPlayerMP player)
    {
	super.addTrackingPlayer(player);
	this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See
     * {@link Entity#addTrackingPlayer} for more information on tracking.
     */
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
	super.removeTrackingPlayer(player);
	this.bossInfo.removePlayer(player);
    }
}