package com.barribob.MaelstromMod.entity.util;

import java.util.List;

import com.barribob.MaelstromMod.entity.util.EntityPortalSpawn.BlockPosTuple;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityNexusParticleSpawner extends Entity
{
    public EntityNexusParticleSpawner(World worldIn)
    {
	super(worldIn);
	this.setNoGravity(true);
	this.setSize(0.1f, 0.1f);
    }

    public EntityNexusParticleSpawner(World worldIn, float x, float y, float z)
    {
	this(worldIn);
	this.setPosition(x, y, z);
    }

    public void onUpdate()
    {
	super.onUpdate();
	if (this.ticksExisted % 10 == 0)
	{
	    ModUtils.performNTimes(20, (i) -> {
		ParticleManager.spawnParticlesInCircle(i * 2, 600 - this.ticksExisted, (pos) -> {
		    pos = pos.scale(1.0f + ModRandom.getFloat(0.03f));
		    ParticleManager.spawnEffect(world, new Vec3d(pos.x, i * 5, pos.y).add(getPositionVector()), ModColors.WHITE);
		});
	    });
	}
	if (this.ticksExisted > 600)
	{
	    this.setDead();
	}
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
    }

    @Override
    protected void entityInit()
    {
    }
}
