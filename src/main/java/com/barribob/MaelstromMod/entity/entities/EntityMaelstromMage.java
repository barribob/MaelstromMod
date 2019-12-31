package com.barribob.MaelstromMod.entity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.animation.AnimationClip;
import com.barribob.MaelstromMod.entity.animation.StreamAnimation;
import com.barribob.MaelstromMod.entity.model.ModelGoldenShade;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * The maelstrom shade monster
 *
 */
public class EntityMaelstromMage extends EntityMaelstromMob
{
    public static final float PROJECTILE_INACCURACY = 6.0f;
    public static final float PROJECTILE_SPEED = 1.2f;

    public EntityMaelstromMage(World worldIn)
    {
	super(worldIn);
	this.setLevel(1.5f);
	this.setSize(0.9f, 1.8f);
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.5);
    }

    @Override
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttack<EntityMaelstromMob>(this, 1.0f, 50, 20, 15.0f, 0.5f));
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
    protected ResourceLocation getLootTable()
    {
	return LootTableHandler.AZURE_MAELSTROM;
    }

    /**
     * Spawn summoning particles
     */
    @Override
    public void onUpdate()
    {
	super.onUpdate();

	if (this.world.isRemote && this.isSwingingArms())
	{
	    this.prepareShoot();
	}
    }

    protected void prepareShoot()
    {
	float f = ModRandom.getFloat(0.25f);
	ParticleManager.spawnMaelstromPotionParticle(world, rand, new Vec3d(this.posX + f, this.posY + this.getEyeHeight() + 1.0f, this.posZ + f), true);
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {
	super.setSwingingArms(swingingArms);
	if (swingingArms)
	{
	    this.world.setEntityState(this, (byte) 4);
	}
    };

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == 4)
	{
	    getCurrentAnimation().startAnimation();
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }

    /**
     * Shoots a projectile in a similar fashion to the snow golem (see
     * EntitySnowman)
     */
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	if (!world.isRemote)
	{
	    ProjectileHorrorAttack projectile = new ProjectileHorrorAttack(this.world, this, this.getAttack());
	    projectile.posY = this.posY + this.getEyeHeight() + 1.0f; // Raise pos y to summon the projectile above the head
	    double d0 = target.posY + target.getEyeHeight() - 0.9f;
	    double d1 = target.posX - this.posX;
	    double d2 = d0 - projectile.posY;
	    double d3 = target.posZ - this.posZ;
	    float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
	    projectile.shoot(d1, d2 + f, d3, this.PROJECTILE_SPEED, this.PROJECTILE_INACCURACY);
	    this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	    this.world.spawnEntity(projectile);
	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void initAnimation()
    {
	List<List<AnimationClip<ModelGoldenShade>>> animations = new ArrayList<List<AnimationClip<ModelGoldenShade>>>();
	List<AnimationClip<ModelGoldenShade>> leftArm = new ArrayList<AnimationClip<ModelGoldenShade>>();
	List<AnimationClip<ModelGoldenShade>> rightArm = new ArrayList<AnimationClip<ModelGoldenShade>>();
	List<AnimationClip<ModelGoldenShade>> body = new ArrayList<AnimationClip<ModelGoldenShade>>();

	BiConsumer<ModelGoldenShade, Float> rightArmMover = (model, f) -> {
	    model.leftArm.rotateAngleX = f;
	    model.leftArm.rotateAngleZ = f.floatValue() / -6;
	};
	rightArm.add(new AnimationClip(12, 0, -180, rightArmMover));
	rightArm.add(new AnimationClip(12, -180, -180, rightArmMover));
	rightArm.add(new AnimationClip(4, -180, 0, rightArmMover));

	BiConsumer<ModelGoldenShade, Float> leftArmMover = (model, f) -> {
	    model.rightArm.rotateAngleX = f;
	    model.rightArm.rotateAngleZ = f.floatValue() / 6;
	};
	leftArm.add(new AnimationClip(12, 0, -180, leftArmMover));
	leftArm.add(new AnimationClip(12, -180, -180, leftArmMover));
	leftArm.add(new AnimationClip(4, -180, 0, leftArmMover));

	BiConsumer<ModelGoldenShade, Float> bodyMover = (model, f) -> {
	    model.body.rotateAngleX = f;
	};
	body.add(new AnimationClip(12, 0, 0, bodyMover));
	body.add(new AnimationClip(8, 0, -10, bodyMover));
	body.add(new AnimationClip(4, -10, 10, bodyMover));
	body.add(new AnimationClip(8, 10, 0, bodyMover));

	animations.add(leftArm);
	animations.add(rightArm);
	animations.add(body);
	this.currentAnimation = new StreamAnimation<ModelGoldenShade>(animations);
    }
}