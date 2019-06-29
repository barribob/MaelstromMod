package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.ActionGoldenFireball;
import com.barribob.MaelstromMod.entity.action.ActionMultiGoldenRunes;
import com.barribob.MaelstromMod.entity.action.ActionOctoMissiles;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.animation.AnimationMegaMissile;
import com.barribob.MaelstromMod.entity.animation.AnimationOctoMissiles;
import com.barribob.MaelstromMod.entity.animation.AnimationRuneSummon;
import com.barribob.MaelstromMod.entity.util.ComboAttack;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGoldenBoss extends EntityMaelstromMob
{
    private ComboAttack attackHandler = new ComboAttack();
    private byte octoMissile = 4;
    private byte megaMissile = 5;
    private byte runes = 6;
    public static final byte octoMissileParticles = 7;

    public EntityGoldenBoss(World worldIn)
    {
	super(worldIn);
	this.setLevel(2);
	this.attackHandler.addAttack(octoMissile, new ActionOctoMissiles(), () -> new AnimationOctoMissiles());
	this.attackHandler.addAttack(megaMissile, new ActionGoldenFireball(), () -> new AnimationMegaMissile());
	this.attackHandler.addAttack(runes, new ActionMultiGoldenRunes(), () -> new AnimationRuneSummon());
	this.setSize(1.5f, 3.2f);
    }

    @Override
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttack<EntityGoldenBoss>(this, 1.0f, 40, 20.0f, 0.4f));
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	if (!world.isRemote)
	{
	    ParticleManager.spawnEffect(world, ModRandom.randVec().add(new Vec3d(0, 2, 0).scale(2)).add(this.getPositionVector()), ModColors.YELLOW);
	    if (this.isSwingingArms() && attackHandler.getCurrentAttack() == megaMissile)
	    {
		Vec3d look = this.getVectorForRotation(0, this.rotationYaw);
		ParticleManager.spawnEffect(world, this.getPositionVector().add(ModRandom.randVec().scale(0.5)).add(ModUtils.yVec(this.getEyeHeight())).add(look),
			ModColors.YELLOW);
	    }
	}
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	this.attackHandler.getCurrentAttackAction().performAction(this, target);
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {
	super.setSwingingArms(swingingArms);
	if (swingingArms)
	{
	    Byte[] attack = { octoMissile, megaMissile, runes };
	    attackHandler.setCurrentAttack(ModRandom.choice(attack));
	    world.setEntityState(this, attackHandler.getCurrentAttack());
	}
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id >= 4 && id <= 6)
	{
	    currentAnimation = attackHandler.getAnimation(id);
	    currentAnimation.startAnimation();
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }

    @Override
    protected void updateAttributes()
    {
	this.setBaseMaxHealth(200);
	this.setBaseAttack(5);
    }
}
