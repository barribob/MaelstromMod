package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.animation.Animation;
import com.barribob.MaelstromMod.entity.animation.StreamAnimation;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromWarrior;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.init.ModAnimations;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * Represent the attibutes and logic of the shade monster
 *
 */
public class EntityShade extends EntityMaelstromMob implements IAttack
{
    public static final float PROJECTILE_INACCURACY = 0;
    public static final float PROJECTILE_VELOCITY = 1.0f;

    public EntityShade(World worldIn)
    {
	super(worldIn);
	this.setSize(0.9f, 1.8f);
    }

    @Override
    protected void initAnimation()
    {
	this.currentAnimation = createAnimation(ModAnimations.SCOUT_SLASH);
    }

    @Override
    protected Animation createAnimation(int animationId)
    {
	return new StreamAnimation<ModelMaelstromWarrior>(animationId)
	{
	    @Override
	    public void setModelRotations(ModelMaelstromWarrior model, float limbSwing, float limbSwingAmount, float partialTicks)
	    {
		model.leftArm.offsetY = (float) Math.cos(Math.toRadians(ticksExisted * 4)) * 0.05f;
		model.rightArm.offsetY = (float) Math.cos(Math.toRadians(ticksExisted * 4)) * 0.05f;
		super.setModelRotations(model, limbSwing, limbSwingAmount, partialTicks);
	    }
	};
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6f);
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25);
	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26f);
	this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3f);
    }

    @Override
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAITimedAttack<EntityShade>(this, 1.0f, 20, 3f, 0.5f));
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
	return SoundsHandler.ENTITY_SHADE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundsHandler.ENTITY_SHADE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
	return SoundsHandler.ENTITY_SHADE_DEATH;
    }

    @Override
    public void onEntityUpdate()
    {
	super.onEntityUpdate();

	if (rand.nextInt(20) == 0)
	{
	    world.setEntityState(this, ModUtils.PARTICLE_BYTE);
	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == ModUtils.PARTICLE_BYTE)
	{
	    if (this.getElement().equals(Element.NONE))
	    {
		ParticleManager.spawnMaelstromPotionParticle(world, rand, this.getPositionVector().add(ModRandom.randVec()).add(ModUtils.yVec(1)), false);
	    }

	    ParticleManager.spawnEffect(world, this.getPositionVector().add(ModRandom.randVec()).add(ModUtils.yVec(1)), getElement().particleColor);
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceFactor, boolean strafingBackwards)
    {
	if (!world.isRemote)
	{
	    this.startAnimation(ModAnimations.SCOUT_SLASH);
	    Vec3d dir = getAttackTarget().getPositionVector().subtract(getPositionVector()).normalize();
	    Vec3d leap = new Vec3d(dir.x, 0, dir.z).normalize().scale(0.4f).add(ModUtils.yVec(0.3f));
	    this.motionX += leap.x;
	    if (this.motionY < 0.1)
	    {
		this.motionY += leap.y;
	    }
	    this.motionZ += leap.z;

	    addEvent(() -> {
		Vec3d pos = this.getPositionVector().add(ModUtils.yVec(this.getEyeHeight())).add(this.getLookVec());
		this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 0.8F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		ModUtils.handleAreaImpact(0.6f, (e) -> this.getAttack(), this, pos, ModDamageSource.causeElementalMeleeDamage(this, getElement()), 0.20f, 0, false);
	    }, 10);
	}
	return 20;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
    }
}