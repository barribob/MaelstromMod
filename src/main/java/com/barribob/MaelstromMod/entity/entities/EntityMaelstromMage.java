package com.barribob.MaelstromMod.entity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.animation.AnimationClip;
import com.barribob.MaelstromMod.entity.animation.StreamAnimation;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromMage;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHorrorAttack;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
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

public class EntityMaelstromMage extends EntityMaelstromMob
{
    public static final String ID = "maelstrom_mage";
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

	if (getElement() != Element.NONE)
	{
	    ParticleManager.spawnEffect(world, new Vec3d(this.posX + f, this.posY + this.getEyeHeight() + 1.0f, this.posZ + f), getElement().particleColor);
	}
	else
	{
	    ParticleManager.spawnMaelstromPotionParticle(world, rand, new Vec3d(this.posX + f, this.posY + this.getEyeHeight() + 1.0f, this.posZ + f), true);
	}
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
	else if (id == ModUtils.PARTICLE_BYTE)
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
	List<List<AnimationClip<ModelMaelstromMage>>> animation = new ArrayList<List<AnimationClip<ModelMaelstromMage>>>();
	List<AnimationClip<ModelMaelstromMage>> leftArmXStream = new ArrayList<AnimationClip<ModelMaelstromMage>>();
	List<AnimationClip<ModelMaelstromMage>> leftArmZStream = new ArrayList<AnimationClip<ModelMaelstromMage>>();
	List<AnimationClip<ModelMaelstromMage>> leftForearmXStream = new ArrayList<AnimationClip<ModelMaelstromMage>>();
	List<AnimationClip<ModelMaelstromMage>> bodyXStream = new ArrayList<AnimationClip<ModelMaelstromMage>>();
	List<AnimationClip<ModelMaelstromMage>> rightArmXStream = new ArrayList<AnimationClip<ModelMaelstromMage>>();

	BiConsumer<ModelMaelstromMage, Float> leftArmX = (model, f) -> model.leftArm.rotateAngleX = f;
	BiConsumer<ModelMaelstromMage, Float> leftArmZ = (model, f) -> model.leftArm.rotateAngleZ = f;
	BiConsumer<ModelMaelstromMage, Float> leftForearmX = (model, f) -> model.leftForearm.rotateAngleX = f;
	BiConsumer<ModelMaelstromMage, Float> bodyX = (model, f) -> model.body.rotateAngleX = f;
	BiConsumer<ModelMaelstromMage, Float> rightArmX = (model, f) -> model.rightArm.rotateAngleX = f;

	leftForearmXStream.add(new AnimationClip(10, -40, 0, leftForearmX));
	leftForearmXStream.add(new AnimationClip(12, 0, 0, leftForearmX));
	leftForearmXStream.add(new AnimationClip(6, 0, 0, leftForearmX));
	leftForearmXStream.add(new AnimationClip(6, 0, -40, leftForearmX));

	leftArmXStream.add(new AnimationClip(10, 0, -120, leftArmX));
	leftArmXStream.add(new AnimationClip(12, -120, -120, leftArmX));
	leftArmXStream.add(new AnimationClip(4, -120, 60, leftArmX));
	leftArmXStream.add(new AnimationClip(2, 60, 60, leftArmX));
	leftArmXStream.add(new AnimationClip(6, 60, 0, leftArmX));

	leftArmZStream.add(new AnimationClip(10, 0, -25, leftArmZ));
	leftArmZStream.add(new AnimationClip(12, -25, -25, leftArmZ));
	leftArmZStream.add(new AnimationClip(6, -25, -20, leftArmZ));
	leftArmZStream.add(new AnimationClip(6, -25, 0, leftArmZ));

	bodyXStream.add(new AnimationClip(10, 0, -15, bodyX));
	bodyXStream.add(new AnimationClip(14, -15, -15, bodyX));
	bodyXStream.add(new AnimationClip(6, -15, 15, bodyX));
	bodyXStream.add(new AnimationClip(4, 15, 0, bodyX));

	rightArmXStream.add(new AnimationClip(10, 0, -40, rightArmX));
	rightArmXStream.add(new AnimationClip(12, -40, -40, rightArmX));
	rightArmXStream.add(new AnimationClip(6, -40, 40, rightArmX));
	rightArmXStream.add(new AnimationClip(6, 40, 0, rightArmX));

	animation.add(leftArmXStream);
	animation.add(leftArmZStream);
	animation.add(leftForearmXStream);
	animation.add(bodyXStream);
	animation.add(rightArmXStream);

	this.currentAnimation = new StreamAnimation<ModelMaelstromMage>(animation)
	{
	    @Override
	    public void setModelRotations(ModelMaelstromMage model, float limbSwing, float limbSwingAmount, float partialTicks)
	    {
		model.leftArm.offsetY = (float) Math.cos(Math.toRadians(ticksExisted * 4)) * 0.05f;
		model.rightArm.offsetY = (float) Math.cos(Math.toRadians(ticksExisted * 4)) * 0.05f;
		super.setModelRotations(model, limbSwing, limbSwingAmount, partialTicks);
	    }
	};
    }
}