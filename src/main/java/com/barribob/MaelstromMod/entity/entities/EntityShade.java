package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.ActionThrust;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.animation.AnimationShadeThrust;
import com.barribob.MaelstromMod.entity.projectile.ProjectileShadeAttack;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * Represent the attibutes and logic of the shade monster
 *
 */
public class EntityShade extends EntityMaelstromMob
{
    public static final float PROJECTILE_INACCURACY = 0;
    public static final float PROJECTILE_VELOCITY = 1.0f;

    public EntityShade(World worldIn)
    {
	super(worldIn);
	this.setLevel(1.5f);
    }

    @Override
    protected void initAnimation()
    {
	this.currentAnimation = new AnimationShadeThrust();
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4);
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25);
    }

    @Override
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttack<EntityMaelstromMob>(this, 1.0f, 20, 3.0f, 0.5f));
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
	return LootTableHandler.SHADE;
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

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	if (!world.isRemote)
	{
	    new ActionThrust(() -> new ProjectileShadeAttack(world, this, this.getAttack())).performAction(this, target);
	}
    }
}