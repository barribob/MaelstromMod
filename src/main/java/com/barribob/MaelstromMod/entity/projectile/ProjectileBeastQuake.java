package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileBeastQuake extends ProjectileQuake
{
    public static final int PARTICLE_AMOUNT = 5;

    public ProjectileBeastQuake(World worldIn, EntityLivingBase throwerIn, float baseDamage)
    {
	super(worldIn, throwerIn, baseDamage, null);
	this.setSize(0.25f, 1);
    }

    public ProjectileBeastQuake(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileBeastQuake(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }

    @Override
    protected void spawnParticles()
    {
	IBlockState block = world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ));
	if (block.isFullCube())
	{
	    for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	    {
		ParticleManager.spawnFirework(world, getPositionVector().add(new Vec3d(ModRandom.getFloat(0.125f), 0, ModRandom.getFloat(0.125f))),
			ModColors.PURPLE, ModUtils.yVec(0.1f + ModRandom.getFloat(0.1f)));
	    }
	}
    }
}
