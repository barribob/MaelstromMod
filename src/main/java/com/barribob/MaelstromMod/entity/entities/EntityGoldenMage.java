package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.ActionGoldenMissles;
import com.barribob.MaelstromMod.entity.animation.AnimationDualThrow;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
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
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void initAnimation()
    {
	currentAnimation = new AnimationDualThrow();
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5);
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	if (rand.nextBoolean())
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
	    ParticleManager.spawnEffect(world, ModRandom.randVec().add(new Vec3d(0, 1, 0)).add(this.getPositionVector()), ModColors.YELLOW);
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

    @Override
    protected ResourceLocation getLootTable()
    {
	return LootTableHandler.GOLDEN_MAELSTROM;
    }
}
