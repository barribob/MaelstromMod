package com.barribob.MaelstromMod.entity.entities;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.entity.action.Action;
import com.barribob.MaelstromMod.entity.action.ActionFireball;
import com.barribob.MaelstromMod.entity.action.ActionGroundSlash;
import com.barribob.MaelstromMod.entity.action.ActionSpinSlash;
import com.barribob.MaelstromMod.entity.action.ActionThrust;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.animation.Animation;
import com.barribob.MaelstromMod.entity.animation.AnimationNone;
import com.barribob.MaelstromMod.entity.projectile.ProjectileFireball;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * The first herobrine boss
 */
public class EntityHerobrineOne extends EntityLeveledMob implements IRangedAttackMob
{        
    private Attack currentAttack;
    
    public EntityHerobrineOne(World worldIn)
    {
	super(worldIn);
    }
    
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttack<EntityHerobrineOne>(this, 1.0f, 40, 10.0f, 0.2f));
	this.tasks.addTask(1, new EntityAISwimming(this));
	this.tasks.addTask(2, new EntityAIRestrictSun(this));
	this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
	this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
	this.tasks.addTask(6, new EntityAILookIdle(this));
	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	this.currentAttack.action.performAction(this, target);
    }
    
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
	this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.SWORD_OF_SHADES));
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner,
     * natural spawning etc, but not called when entity is reloaded from nbt. Mainly
     * used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
	IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
	this.setEquipmentBasedOnDifficulty(difficulty);
	this.setEnchantmentBasedOnDifficulty(difficulty);
	return ientitylivingdata;
    }
    
    @Override
    public void setSwingingArms(boolean swingingArms)
    {
	super.setSwingingArms(swingingArms);
	if (swingingArms)
	{
	    float distance = (float) this.getDistanceSq(this.getAttackTarget().posX, getAttackTarget().getEntityBoundingBox().minY, getAttackTarget().posZ);
	    
	    if(distance > Math.pow(4, 2))
	    {
		this.currentAttack = rand.nextInt(2) == 0 ? Attack.FIREBALL : Attack.GROUND_SLASH;
	    }
	    else
	    {
		this.currentAttack = rand.nextInt(2) == 0 ? Attack.SPIN_SLASH : Attack.THRUST;
	    }
	    
	    this.world.setEntityState(this, (byte)this.currentAttack.id);
	    
	    if(this.currentAttack == Attack.FIREBALL)
	    {
		this.motionY = 0.7f;
		this.fallDistance = -4;
	    }
	}
    }
    
    @Override
    public void onUpdate()
    {
        super.onUpdate();
        
        int fireballParticles = 5;
        
	if (!this.world.isRemote && this.isSwingingArms() && this.currentAttack == Attack.FIREBALL)
	{
	    for(int i = 0; i < fireballParticles; i++)
	    {
		Vec3d pos = new Vec3d(this.posX + ModRandom.getFloat(0.5f), this.posY + this.getEyeHeight() + 1.0f, this.posZ + ModRandom.getFloat(0.5f));
		ParticleManager.spawnEffect(world, pos, ProjectileFireball.FIREBALL_COLOR);
	    }
	}
    }

    @Override
    protected void updateAttributes()
    {
	this.setBaseAttack(7);
	this.setBaseMaxHealth(100);
	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id >= 4 && id <= 7)
	{
	    for(Attack attack : Attack.values())
	    {
		if(attack.id == id)
		{
		    this.currentAnimation = attack.animation;
		    this.currentAnimation.startAnimation();
		}
	    }
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }
    
    /*
     * Represents the different attacks that herobrine can have
     */
    private enum Attack
    {
	SPIN_SLASH(4, new AnimationNone(), new ActionSpinSlash()),
	THRUST(5, new AnimationNone(), new ActionThrust()),
	GROUND_SLASH(6, new AnimationNone(), new ActionGroundSlash()),
	FIREBALL(7, new AnimationNone(), new ActionFireball());
	
	int id;
	Animation animation;
	Action action;
	
	private Attack(int attack, Animation animation, Action action)
	{
	    this.id = attack;
	    this.animation = animation;
	    this.action = action;
	}
    }
}
