package com.barribob.MaelstromMod.entity.entities;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.entity.ai.AIMeleeAndRange;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBeastAttack;
import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The first boss of the mod
 * 
 * @author micha
 *
 */
public class EntityBeast extends EntityMaelstromMob
{
    private final static double SPEED = 1.5D;
    private final static double SPEED_AMP = 1.0F;
    private final static int RANGED_COOLDOWN = 40;
    private final static float RANGED_DISTANCE = 8.0f;
    private final static int AI_SWITCH_TIME = 100;
    private final static float RANGED_SWITCH_CHANCE = 0.5f;
    private final static float PROJECTILE_SPEED = 1.0f;
    private final static float PROJECTILE_INACCURACY = 6.0f;
    private final static int PROJECTILE_AMOUNT = 5;
    private final static int MINION_AMOUNT = 2;

    private boolean isRanged; // Used for animation

    // Responsible for the boss bar
    private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE,
	    BossInfo.Overlay.NOTCHED_20));

    public EntityBeast(World worldIn)
    {
	super(worldIn);
	this.setSize(1.8f, 1.8f);
    }

    // Init the melee and range ai
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new AIMeleeAndRange<EntityMaelstromMob>(this, SPEED, true, SPEED_AMP, RANGED_COOLDOWN, RANGED_DISTANCE, AI_SWITCH_TIME,
		RANGED_SWITCH_CHANCE));
    }

    // Checks to see if it should spawn a minion
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
	float health = this.getHealth();
	boolean flag = super.attackEntityFrom(source, amount);

	if (flag && healthBelowMilestone(health))
	{
	    for (int i = 0; i < this.MINION_AMOUNT; i++)
	    {
		this.spawnMinion();
	    }
	}

	return flag;
    }

    /**
     * Determines whether the boss's health dropped below a certain benchmark
     * 
     * @param prevHealth
     * @return
     */
    private boolean healthBelowMilestone(float prevHealth)
    {
	return (prevHealth >= 60 && this.getHealth() < 60) || (prevHealth >= 40 && this.getHealth() < 40)
		|| (prevHealth >= 20 && this.getHealth() < 20);
    }

    /**
     * Try to spawn a minion shade to aid in battle Taken from the zombie spawn
     * reinforcements code
     */
    private void spawnMinion()
    {
	int tries = 50;
	for (int i = 0; i < tries; i++)
	{
	    // Find a random position to spawn the enemy
	    int i1 = (int) this.posX + MathHelper.getInt(this.rand, 3, 7) * MathHelper.getInt(this.rand, -1, 1);
	    int j1 = (int) this.posY + MathHelper.getInt(this.rand, 3, 7) * MathHelper.getInt(this.rand, -1, 1);
	    int k1 = (int) this.posZ + MathHelper.getInt(this.rand, 3, 7) * MathHelper.getInt(this.rand, -1, 1);

	    if (this.world.getBlockState(new BlockPos(i1, j1 - 1, k1)).isSideSolid(this.world, new BlockPos(i1, j1 - 1, k1),
		    net.minecraft.util.EnumFacing.UP))
	    {
		EntityShade shade = new EntityShade(this.world);
		shade.setPosition((double) i1, (double) j1, (double) k1);

		// Make sure that the position is a proper spawning position
		if (!this.world.isAnyPlayerWithinRangeAt((double) i1, (double) j1, (double) k1, 5.0D)
			&& this.world.checkNoEntityCollision(shade.getEntityBoundingBox(), shade)
			&& this.world.getCollisionBoxes(shade, shade.getEntityBoundingBox()).isEmpty()
			&& !this.world.containsAnyLiquid(shade.getEntityBoundingBox()))
		{
		    // Spawn the entity
		    this.world.spawnEntity(shade);
		    if (this.getAttackTarget() != null)
		    {
			shade.setAttackTarget(this.getAttackTarget());
		    }
		    shade.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(shade)), (IEntityLivingData) null);
		    shade.spawnExplosionParticle();
		    break;
		}
	    }
	}
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == 4) // Change to ranged mode
	{
	    this.isRanged = true;
	}
	else if (id == 5) // Change to melee mode
	{
	    this.isRanged = false;
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }

    public boolean isRanged()
    {
	return this.isRanged;
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120.0D);
	this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(14);
	this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(8);
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	for (int i = 0; i < this.PROJECTILE_AMOUNT; i++)
	{
	    ProjectileBeastAttack projectile = new ProjectileBeastAttack(this.world, this);
	    double d0 = target.posY + (double) target.getEyeHeight();
	    double d1 = target.posX - this.posX;
	    double d2 = d0 - projectile.posY;
	    double d3 = target.posZ - this.posZ;
	    float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
	    projectile.shoot(d1, d2 + (double) f, d3, this.PROJECTILE_SPEED, this.PROJECTILE_INACCURACY);
	    this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	    this.world.spawnEntity(projectile);
	}
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
	if (entityIn instanceof EntityLivingBase)
	{
	    entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
	    return true;
	}
	return false;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
	super.readEntityFromNBT(compound);

	if (this.hasCustomName())
	{
	    this.bossInfo.setName(this.getDisplayName());
	}
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
