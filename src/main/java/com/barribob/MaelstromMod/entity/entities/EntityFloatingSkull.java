package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileSkullAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileWillOTheWisp;
import com.barribob.MaelstromMod.util.AnimationTimer;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * Represent the attibutes and logic of the shade monster
 *
 */
public class EntityFloatingSkull extends EntityMaelstromMob
{
    public static final float PROJECTILE_INACCURACY = 0;
    public static final float PROJECTILE_VELOCITY = 0.4f;

    private float[] jawAnimation = { 0, 15, 25, 35, 35, 35, 35, 35, 35, 35, 35, 25, 15, 0 };

    private AnimationTimer animation;

    public EntityFloatingSkull(World worldIn)
    {
	super(worldIn);
	animation = new AnimationTimer(jawAnimation.length);
    }

    @Override
    protected void updateAttributes()
    {
	this.setBaseAttack(3);
    }

    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttack<EntityMaelstromMob>(this, 1.0f, 60, 5, 7.5f, 0.5f));
    }

    @Override
    public void onLivingUpdate()
    {
	super.onLivingUpdate();
	animation.nextFrame();
    }
    
    @Override
    public void onUpdate()
    {
	super.onUpdate();
	if (world.isRemote)
	{
	    ParticleManager.spawnDarkFlames(world, rand, new Vec3d(this.posX + ModRandom.getFloat(0.5f), this.posY + 0.1f + ModRandom.getFloat(0.1f), this.posZ + ModRandom.getFloat(0.5f)));
	}
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
	return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
	return SoundEvents.ENTITY_SKELETON_DEATH;
    }
    
    @Override
    protected float getSoundPitch()
    {
        return 0.8f + ModRandom.getFloat(0.2f);
    }

    @Override
    protected ResourceLocation getLootTable()
    {
	return LootTableHandler.FLOATING_SKULL;
    }
    
    @SideOnly(Side.CLIENT)
    public float getJawRotation()
    {
	float degree = animation.getFrame(jawAnimation);
	return (float) Math.toRadians(degree);
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {
	if (swingingArms)
	{
	    this.world.setEntityState(this, (byte) 4);
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
	    animation.startAnimation();
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
	    world.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_BLAZE_AMBIENT, SoundCategory.NEUTRAL, 0.5F,
		    0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));

	    float inaccuracy = 0.0f;
	    float speed = 0.5f;

	    ProjectileSkullAttack projectile = new ProjectileSkullAttack(world, this, this.getAttack());
	    projectile.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, speed, inaccuracy);
	    projectile.setTravelRange(9f);

	    world.spawnEntity(projectile);
	}
    }
}