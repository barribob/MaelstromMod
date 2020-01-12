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

/**
 * 
 * Projectile for herobrins slash attack
 *
 */
public class ProjectileHerobrineQuake extends ProjectileQuake
{
    public static final int PARTICLE_AMOUNT = 1;

    public ProjectileHerobrineQuake(World worldIn, EntityLivingBase throwerIn, float baseDamage)
    {
	super(worldIn, throwerIn, baseDamage, null);
	this.setSize(0.25f, 1);
    }

    public ProjectileHerobrineQuake(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileHerobrineQuake(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }

    /**
     * Spawns maelstrom particles in an incomplete column
     * 
     * @param world
     */
    @Override
    protected void spawnParticles()
    {
	IBlockState block = world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ));
	if (block.isFullCube())
	{
	    Vec3d color = new Vec3d(0.5, 0.3, 0.5);
	    Vec3d vel = new Vec3d(0, 0.1, 0);
	    for (int i = 0; i < this.PARTICLE_AMOUNT; i++)
	    {
		float height = 2 + ModRandom.getFloat(0.5f);
		for (float y = 0; y < height; y += 0.2f)
		{
		    Vec3d pos = ModUtils.entityPos(this).add(new Vec3d(this.motionX * ModRandom.getFloat(0.5f), y, this.motionZ * ModRandom.getFloat(0.5f)));
		    ParticleManager.spawnSwirl(world, pos, ModColors.AZURE, Vec3d.ZERO, ModRandom.range(10, 15));
		}
	    }
	}
    }
}
