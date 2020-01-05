package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic.MobSpawnData;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.world.gen.WorldGenMaelstrom;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ProjectileMaelstromMeteor extends Projectile
{
    public ProjectileMaelstromMeteor(World worldIn, EntityLivingBase throwerIn, float damage)
    {
	super(worldIn, throwerIn, damage);
	this.setNoGravity(true);
    }

    public ProjectileMaelstromMeteor(World worldIn)
    {
	super(worldIn);
	this.setNoGravity(true);
    }

    public ProjectileMaelstromMeteor(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
	this.setNoGravity(true);
    }

    @Override
    public void onUpdate()
    {
	this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.5f, ModRandom.getFloat(0.2f) + 1.0f);
	if (this.ticksExisted > 400)
	{
	    this.setDead();
	}
	super.onUpdate();
    }

    @Override
    protected void spawnParticles()
    {
	float size = 0.25f;
	ModUtils.performNTimes(10, (i) -> {
	    ParticleManager.spawnMaelstromLargeSmoke(world, rand, getPositionVector().add(ModRandom.randVec().scale(size)));
	});
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
	// Go through entities
	if (result.entityHit != null)
	{
	    return;
	}

	new WorldGenMaelstrom(ModBlocks.DECAYING_AZURE_MAELSTROM, ModBlocks.AZURE_MAELSTROM_CORE, (tileEntity) -> tileEntity.getSpawnerBaseLogic().setData(
		new MobSpawnData(Reference.MOD_ID + ":" + EntityShade.ID, Element.NONE),
		2,
		1.0f,
		16))
			.generate(world, rand, this.getPosition());
	super.onHit(result);
    }
}
