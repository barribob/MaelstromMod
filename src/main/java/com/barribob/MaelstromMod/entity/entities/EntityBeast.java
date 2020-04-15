package com.barribob.MaelstromMod.entity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.barribob.MaelstromMod.entity.action.Action;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttackNoReset;
import com.barribob.MaelstromMod.entity.animation.AnimationClip;
import com.barribob.MaelstromMod.entity.animation.StreamAnimation;
import com.barribob.MaelstromMod.entity.model.ModelBeast;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBeastAttack;
import com.barribob.MaelstromMod.entity.util.ComboAttack;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBeast extends EntityMaelstromMob
{
    private ComboAttack attackHandler = new ComboAttack();
    private byte leap = 4;
    private byte spit = 5;

    // Responsible for the boss bar
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_20));

    public EntityBeast(World worldIn)
    {
	super(worldIn);
	this.setSize(2.8f, 2.2f);
	this.experienceValue = ModEntities.BOSS_EXPERIENCE;
	this.healthScaledAttackFactor = 0.2;
	if (!worldIn.isRemote)
	{
	    attackHandler.setAttack(leap, new Action()
	    {
		@Override
		public void performAction(EntityLeveledMob actor, EntityLivingBase target)
		{
		    ModUtils.leapTowards(actor, target.getPositionVector(), 1.0f, 0.5f);
		}
	    });
	    attackHandler.setAttack(spit, new Action()
	    {
		@Override
		public void performAction(EntityLeveledMob actor, EntityLivingBase target)
		{
		    for (int i = 0; i < 5; i++)
		    {
			ProjectileBeastAttack projectile = new ProjectileBeastAttack(actor.world, actor, actor.getAttack() * 0.5f);
			double d0 = target.posY + target.getEyeHeight();
			double d1 = target.posX - actor.posX;
			double d2 = d0 - projectile.posY;
			double d3 = target.posZ - actor.posZ;
			float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
			projectile.shoot(d1, d2 + f, d3, 1, 8);
			actor.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F / (actor.getRNG().nextFloat() * 0.4F + 0.8F));
			actor.world.spawnEntity(projectile);
		    }
		}
	    });
	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void initAnimation()
    {
	List<List<AnimationClip<ModelBeast>>> animationLeap = new ArrayList<List<AnimationClip<ModelBeast>>>();
	List<AnimationClip<ModelBeast>> head = new ArrayList<AnimationClip<ModelBeast>>();

	BiConsumer<ModelBeast, Float> headZ = (model, f) -> {
	    model.head.rotateAngleZ = f;
	    model.jaw.rotateAngleX = f / 2;
	};

	head.add(new AnimationClip(20, 0, 40, headZ));
	head.add(new AnimationClip(8, 40, 40, headZ));
	head.add(new AnimationClip(12, 40, 0, headZ));

	animationLeap.add(head);

	List<List<AnimationClip<ModelBeast>>> animationSpit = new ArrayList<List<AnimationClip<ModelBeast>>>();
	List<AnimationClip<ModelBeast>> jaw = new ArrayList<AnimationClip<ModelBeast>>();

	BiConsumer<ModelBeast, Float> jawX = (model, f) -> {
	    model.head.rotateAngleZ = 0;
	    model.jaw.rotateAngleX = f;
	};

	jaw.add(new AnimationClip(20, 0, 20, jawX));
	jaw.add(new AnimationClip(8, 20, 20, jawX));
	jaw.add(new AnimationClip(12, 20, 0, jawX));

	animationSpit.add(jaw);

	attackHandler.setAttack(leap, Action.NONE, () -> new StreamAnimation(animationLeap));
	attackHandler.setAttack(spit, Action.NONE, () -> new StreamAnimation(animationSpit));

	this.currentAnimation = new StreamAnimation(animationSpit);
    }

    @Override
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttackNoReset<EntityMaelstromMob>(this, 1.0f, 40, 24, 8.0f, 0.5f));
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    @Override
    protected boolean canDespawn()
    {
	return false;
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8);
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(250);
	this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5);
	this.getEntityAttribute(SWIM_SPEED).setBaseValue(1.0D);
    }

    @Override
    protected float getWaterSlowDown()
    {
	return 0.95f;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	this.attackHandler.getCurrentAttackAction().performAction(this, target);
	if (attackHandler.getCurrentAttack() == leap)
	{
	    setLeaping(true);
	}
    }

    @Override
    public void onLivingUpdate()
    {
	if (!world.isRemote && this.isLeaping())
	{
	    ModUtils.handleAreaImpact(2.5f, (e) -> this.getAttack(), this, this.getPositionVector(), ModDamageSource.causeElementalMeleeDamage(this, getElement()), 0.3f, 0, false);
	}
	super.onLivingUpdate();
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

    @Override
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
    protected ResourceLocation getLootTable()
    {
	return LootTableHandler.SWAMP_BOSS;
    }

    @Override
    protected float getSoundVolume()
    {
	return 0.5f;
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {
	super.setSwingingArms(swingingArms);
	if (this.isSwingingArms())
	{
	    attackHandler.setCurrentAttack(ModRandom.choice(new Byte[] { leap, spit }));
	    world.setEntityState(this, attackHandler.getCurrentAttack());
	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == 4 || id == 5)
	{
	    currentAnimation = attackHandler.getAnimation(id);
	    getCurrentAnimation().startAnimation();
	}
	super.handleStatusUpdate(id);
    }

    @Override
    public void onStopLeaping()
    {
    }
}
