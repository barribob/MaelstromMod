package com.barribob.MaelstromMod.entity.entities;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.entity.ai.AIMeleeAndRange;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBeastAttack;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
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

    private boolean isRanged; // Used for animation
    
    // Responsible for the boss bar
    private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_20));

    public EntityBeast(World worldIn)
    {
	super(worldIn);
	this.setSize(1.8f, 1.8f);
    }

    // Init the melee and range ai
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new AIMeleeAndRange<EntityMaelstromMob>(this, SPEED, true, SPEED_AMP, RANGED_COOLDOWN, RANGED_DISTANCE, AI_SWITCH_TIME, RANGED_SWITCH_CHANCE));
    }
    
    @Override
    protected ResourceLocation getLootTable()
    {
	return LootTableHandler.BEAST;
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
	return false;
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
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
	this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(14);
	this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(8);
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	if (!world.isRemote)
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
    
    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
	if (this.hasCustomName())
	{
	    this.bossInfo.setName(this.getDisplayName());
	}

	super.readEntityFromNBT(compound);
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
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundsHandler.ENTITY_BEAST_AMBIENT;
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundsHandler.ENTITY_BEAST_HURT;
    }
    
    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundsHandler.ENTITY_BEAST_HURT;
    }
    
    @Override
    protected float getSoundVolume()
    {
	return 0.5f;
    }
}
