package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.ActionDarkMissile;
import com.barribob.MaelstromMod.entity.action.ActionThrowPotion;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.animation.AnimationWitchFlail;
import com.barribob.MaelstromMod.entity.util.ComboAttack;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMaelstromWitch extends EntityMaelstromMob
{
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_12));
    private ComboAttack attackHandler = new ComboAttack();
    private byte lingeringPotions = 4;
    private byte rapidPotions = 5;
    private byte throwWood = 6;
    private EntityAIRangedAttack rangedAttack;
    private EntityAIRangedAttack rageRangedAttack;
    private float threshold;
    private boolean isRaged = false;

    public EntityMaelstromWitch(World worldIn)
    {
	super(worldIn);
	this.setLevel(2);
	threshold = this.getMaxHealth() * 0.3f;
	this.experienceValue = ModEntities.MINIBOSS_EXPERIENCE;
	if (!worldIn.isRemote)
	{
	    attackHandler.addAttack(lingeringPotions, new ActionThrowPotion(Items.LINGERING_POTION));
	    attackHandler.addAttack(rapidPotions, new ActionThrowPotion(Items.SPLASH_POTION));
	    attackHandler.addAttack(throwWood, new ActionDarkMissile());
	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void initAnimation()
    {
	attackHandler.addAttack(lingeringPotions, new ActionThrowPotion(Items.LINGERING_POTION), () -> new AnimationWitchFlail());
	attackHandler.addAttack(rapidPotions, new ActionThrowPotion(Items.SPLASH_POTION), () -> new AnimationWitchFlail());
	attackHandler.addAttack(throwWood, new ActionDarkMissile(), () -> new AnimationWitchFlail());
    }

    @Override
    protected void initEntityAI()
    {
	super.initEntityAI();
	rangedAttack = new EntityAIRangedAttack<EntityMaelstromWitch>(this, 1.0f, 60, 30, 12.0f, 0.4f);
	rageRangedAttack = new EntityAIRangedAttack<EntityMaelstromWitch>(this, 1.0f, 35, 30, 12.0f, 0.4f);
	this.tasks.addTask(4, rangedAttack);
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	if (this.isSwingingArms() && this.ticksExisted % 3 == 0 && this.getAttackTarget() != null)
	{
	    attackHandler.getCurrentAttackAction().performAction(this, this.getAttackTarget());
	}
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
	if (source.isMagicDamage())
	{
	    return false;
	}

	float prevHealth = this.getHealth();
	boolean flag = super.attackEntityFrom(source, amount);
	if (prevHealth > threshold && this.getHealth() < threshold && rangedAttack != null)
	{
	    this.tasks.removeTask(rangedAttack);
	    this.tasks.addTask(4, rageRangedAttack);
	    this.isRaged = true;
	}
	return flag;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
    }

    @Override
    protected void updateAttributes()
    {
	this.setBaseAttack(4);
	this.setBaseMaxHealth(100);
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {
	super.setSwingingArms(swingingArms);
	if (swingingArms)
	{
	    Byte[] attack = { lingeringPotions, rapidPotions, throwWood };
	    attackHandler.setCurrentAttack(ModRandom.choice(attack));
	    world.setEntityState(this, attackHandler.getCurrentAttack());
	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id >= 4 && id <= 6)
	{
	    currentAnimation = attackHandler.getAnimation(id);
	    getCurrentAnimation().startAnimation();
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
	return SoundEvents.ENTITY_WITCH_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundEvents.ENTITY_WITCH_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
	return SoundEvents.ENTITY_WITCH_DEATH;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
	return LootTableHandler.SWAMP_BOSS;
    }

    @Override
    protected float getSoundPitch()
    {
	return 0.6f;
    }

    @Override
    public void setCustomNameTag(String name)
    {
	super.setCustomNameTag(name);
	this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player)
    {
	super.addTrackingPlayer(player);
	this.bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
	super.removeTrackingPlayer(player);
	this.bossInfo.removePlayer(player);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
	super.writeEntityToNBT(compound);
	compound.setBoolean("raged", this.isRaged);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
	super.readEntityFromNBT(compound);
	if (compound.hasKey("raged"))
	{
	    this.isRaged = compound.getBoolean("raged");
	    if (isRaged && rangedAttack != null)
	    {
		this.tasks.removeTask(rangedAttack);
		this.tasks.addTask(4, rageRangedAttack);
	    }
	}
    }
}
