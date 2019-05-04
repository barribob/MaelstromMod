package com.barribob.MaelstromMod.entity.tileentity;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
 * The tile entity spawner logic for the maelstrom core. Has similar
 * functionality to the vanilla minecraft mob spawner
 *
 */
public class MaelstromMobSpawnerLogic extends MobSpawnerLogic
{
    private int minSpawnDelay = 600;
    private int maxSpawnDelay = 800;

    public MaelstromMobSpawnerLogic(Supplier<World> world, Supplier<BlockPos> pos, Block block)
    {
	super(world, pos, block);
    }

    /**
     * Returns true if there's a player close enough to this mob spawner to activate
     * it.
     */
    private boolean isActivated()
    {
	BlockPos blockpos = this.pos.get();
	return this.world.get().isAnyPlayerWithinRangeAt((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.5D, (double) blockpos.getZ() + 0.5D,
		(double) this.activatingRangeFromPlayer);
    }

    public void updateSpawner()
    {
	// Currently does not deal with any server stuff, although this might be a
	// mistake, so potentially this may have to revert back to the vanilla logic
	if (this.world.get().isRemote || !this.isActivated())
	{
	    return;
	}

	if (this.spawnDelay == -1)
	{
	    this.resetTimer();
	}

	if (this.spawnDelay > 0)
	{
	    --this.spawnDelay;
	    return;
	}

	for (int i = 0; i < this.spawnCount; i++)
	{
	    // Try multiple times to spawn the entity in a good spot
	    int tries = 20;
	    for (int t = 0; t < tries; t++)
	    {
		if(this.tryToSpawnEntity())
		{
		    break;
		}
	    }
	}

	this.resetTimer();
    }

    /**
     * Resets the timer back to a random number between max and min spawn delay
     */
    private void resetTimer()
    {
	if (this.maxSpawnDelay <= this.minSpawnDelay)
	{
	    this.spawnDelay = this.minSpawnDelay;
	}
	else
	{
	    int i = this.maxSpawnDelay - this.minSpawnDelay;
	    this.spawnDelay = this.minSpawnDelay + this.world.get().rand.nextInt(i);
	}

	if (!this.potentialSpawns.isEmpty())
	{
	    this.setNextSpawnData((WeightedSpawnerEntity) WeightedRandom.getRandomItem(this.world.get().rand, this.potentialSpawns));
	}

	this.broadcastEvent(1);
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
	if (nbt.hasKey("MinSpawnDelay", 99))
	{
	    this.minSpawnDelay = nbt.getShort("MinSpawnDelay");
	    this.maxSpawnDelay = nbt.getShort("MaxSpawnDelay");
	}
	super.readFromNBT(nbt);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
	if(this.getEntityId() != null)
	{
	    compound.setShort("MinSpawnDelay", (short) this.minSpawnDelay);
	    compound.setShort("MaxSpawnDelay", (short) this.maxSpawnDelay);
	}
	return super.writeToNBT(compound);
    }
}