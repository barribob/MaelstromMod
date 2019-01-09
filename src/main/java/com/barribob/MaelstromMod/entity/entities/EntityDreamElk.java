package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.AIEatAzureGrass;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDreamElk extends EntityCreature
{
    /**
     * Timers for animation
     */
    private int attackTimer;
    private int eatGrassTimer;
    private AIEatAzureGrass grassAI;

    public EntityDreamElk(World worldIn)
    {
	super(worldIn);
	this.setSize(1.3964844F, 1.6F);
    }

    protected void initEntityAI()
    {
	grassAI = new AIEatAzureGrass(this);
	this.tasks.addTask(0, new EntityAISwimming(this));
	this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
	this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
	this.tasks.addTask(5, grassAI);
	this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
	this.tasks.addTask(8, new EntityAILookIdle(this));
	this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }

    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
	this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    protected void updateAITasks()
    {
	this.eatGrassTimer = this.grassAI.getEatingGrassTimer();
	super.updateAITasks();
    }
    
    @Override
    protected ResourceLocation getLootTable()
    {
	return LootTableHandler.ELK;
    }

    @Override
    public void swingArm(EnumHand hand)
    {
    }

    /**
     * Called frequently so the entity can update its state every tick as required.
     * For example, zombies and skeletons use this to react to sunlight and start to
     * burn.
     */
    public void onLivingUpdate()
    {
	super.onLivingUpdate();

	if (this.attackTimer > 0)
	{
	    --this.attackTimer;
	}

	if (this.world.isRemote)
	{
	    this.eatGrassTimer = Math.max(0, this.eatGrassTimer - 1);
	}

    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == 4)
	{
	    this.attackTimer = 10;
	    this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
	}
	if (id == 10)
	{
	    this.eatGrassTimer = 40;
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }

    /**
     * Handles the animations of the neck (bucking and eating grass)
     * @param partialTickTime
     * @return
     */
    @SideOnly(Side.CLIENT)
    public float getHeadRotationAngleX(float partialTickTime)
    {
	if (this.attackTimer > 0)
	{
	    return 0.6f * this.triangleWave((float)this.attackTimer - partialTickTime, 10.0F);
	}
	else if (this.eatGrassTimer > 4 && this.eatGrassTimer <= 36)
	{
	    float f = ((float) (this.eatGrassTimer - 4) - partialTickTime) / 32.0F;
	    return ((float) Math.PI / 5F) + ((float) Math.PI * 7F / 100F) * MathHelper.sin(f * 28.7F);
	}
	else
	{
	    return 0;
	}
    }
    
    /**
     * Taken from the iron golem animation to make the bucking animation
     * @param x
     * @param f
     * @return
     */
    private float triangleWave(float x, float f)
    {
        return (Math.abs(x % f - f * 0.5F) - f * 0.25F) / (f * 0.25F);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
	this.world.setEntityState(this, (byte) 4);
	boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 6);

	if (flag)
	{
	    entityIn.motionY += 0.35D;
	    this.applyEnchantments(this, entityIn);
	}

	this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
	return flag;
    }
    
    protected SoundEvent getAmbientSound()
    {
        super.getAmbientSound();
        return SoundEvents.ENTITY_HORSE_AMBIENT;
    }

    protected SoundEvent getDeathSound()
    {
        super.getDeathSound();
        return SoundEvents.ENTITY_HORSE_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        super.getHurtSound(damageSourceIn);
        return SoundEvents.ENTITY_HORSE_HURT;
    }
    
    @Override
    protected float getSoundVolume()
    {
	return 0.5f;
    }
}
