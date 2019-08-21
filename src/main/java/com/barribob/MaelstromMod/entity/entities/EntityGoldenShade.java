package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.ActionThrust;
import com.barribob.MaelstromMod.entity.animation.AnimationShadeThrust;
import com.barribob.MaelstromMod.entity.projectile.ProjectileGoldenThrust;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGoldenShade extends EntityShade
{
    public EntityGoldenShade(World worldIn)
    {
	super(worldIn);
	this.setLevel(2.5f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void initAnimation()
    {
	this.currentAnimation = new AnimationShadeThrust();
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	if (!world.isRemote && rand.nextBoolean())
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
	    currentAnimation.startAnimation();
	}
	else if (id == ModUtils.PARTICLE_BYTE)
	{
	    ParticleManager.spawnEffect(world, ModRandom.randVec().add(new Vec3d(0, 1, 0)).add(this.getPositionVector()), ModColors.YELLOW);
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
	    new ActionThrust(new ProjectileGoldenThrust(world, this, this.getAttack())).performAction(this, target);
	}
    }

    @Override
    protected ResourceLocation getLootTable()
    {
	return LootTableHandler.GOLDEN_MAELSTROM;
    }
}
