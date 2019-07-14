package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.ActionGoldenMissles;
import com.barribob.MaelstromMod.entity.animation.AnimationBeastSpit;
import com.barribob.MaelstromMod.entity.animation.AnimationDualThrow;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGoldenMage extends EntityMaelstromMage
{
    public EntityGoldenMage(World worldIn)
    {
	super(worldIn);
	this.setLevel(2.5f);
	currentAnimation = new AnimationDualThrow();
    }

    @Override
    protected void updateAttributes()
    {
	this.setBaseAttack(4);
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	if (!world.isRemote && rand.nextBoolean())
	{
	    ParticleManager.spawnEffect(world, ModRandom.randVec().add(new Vec3d(0, 1, 0)).add(this.getPositionVector()), ModColors.YELLOW);
	}
    }

    public void setSwingingArms(boolean swingingArms)
    {
	super.setSwingingArms(swingingArms);
	if (swingingArms)
	{
	    this.world.setEntityState(this, (byte) 4);
	}
    };

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == 4)
	{
	    currentAnimation = new AnimationDualThrow();
	    currentAnimation.startAnimation();
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }

    @Override
    protected void prepareShoot()
    {
	Vec3d look = ModUtils.getVectorForRotation(0, this.renderYawOffset);
	Vec3d right = look.rotateYaw((float) Math.PI * -0.5f).scale(0.5);
	Vec3d left = look.rotateYaw((float) Math.PI * 0.5f).scale(0.5);
	Vec3d yoff = new Vec3d(0, getEyeHeight() - 0.5, 0);
	ParticleManager.spawnEffect(world, this.getPositionVector().add(yoff).add(left), ModColors.YELLOW);
	ParticleManager.spawnEffect(world, this.getPositionVector().add(yoff).add(right), ModColors.YELLOW);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	if (!world.isRemote)
	{
	    new ActionGoldenMissles(0.5f, this.getEyeHeight() - 0.5f).performAction(this, target);
	}
    }
}
