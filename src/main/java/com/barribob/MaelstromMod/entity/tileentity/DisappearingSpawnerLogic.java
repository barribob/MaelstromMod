package com.barribob.MaelstromMod.entity.tileentity;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

/**
 * 
 * The tile entity spawner logic for the disappearing spawner. Similar to the
 * vanilla spawner, except it sets itself to air
 *
 */
public class DisappearingSpawnerLogic extends MobSpawnerLogic
{
    public DisappearingSpawnerLogic(Supplier<World> world, Supplier<BlockPos> pos, Block block)
    {
	super(world, pos, block);
    }

    /**
     * Returns true if there's a player close enough to this mob spawner to activate
     * it.
     */
    protected boolean isActivated()
    {
	BlockPos blockpos = this.pos.get();
	return isAnyPlayerWithinRangeAt(this.world.get(), (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.5D, (double) blockpos.getZ() + 0.5D,
		(double) this.activatingRangeFromPlayer);
    }

    /**
     * Checks to see if any players (in survival) are in range for spawning
     */
    private boolean isAnyPlayerWithinRangeAt(World world, double x, double y, double z, double range)
    {
	for (int j2 = 0; j2 < world.playerEntities.size(); ++j2)
	{
	    EntityPlayer entityplayer = world.playerEntities.get(j2);

	    if (EntitySelectors.NOT_SPECTATING.apply(entityplayer) && !entityplayer.capabilities.isCreativeMode)
	    {
		double d0 = entityplayer.getDistanceSq(x, y, z);

		if (range < 0.0D || d0 < range * range)
		{
		    return true;
		}
	    }
	}

	return false;
    }

    public void updateSpawner()
    {	
	// Currently does not deal with any server stuff, although this might be a
	// mistake, so potentially this may have to revert back to the vanilla logic
	if (this.world.get().isRemote || !this.isActivated())
	{
	    return;
	}

	if (this.spawnDelay > 0)
	{
	    --this.spawnDelay;
	    return;
	}
	
	for (int i = 0; i < this.spawnCount; i++)
	{
	    // Try multiple times to spawn the entity in a good spot
	    int tries = 50;
	    for (int t = 0; t < tries; t++)
	    {
		if(this.tryToSpawnEntity())
		{
		    break;
		}
	    }
	}
	
	this.onSpawn(world.get(), pos.get());
    }
    
    protected void onSpawn(World world, BlockPos blockpos)
    {
	world.setBlockToAir(blockpos);
    }
}