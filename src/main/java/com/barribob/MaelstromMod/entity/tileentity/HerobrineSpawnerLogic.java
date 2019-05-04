package com.barribob.MaelstromMod.entity.tileentity;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

/**
 * 
 * Spawns herobrine in a flash of lightning
 *
 */
public class HerobrineSpawnerLogic extends DisappearingSpawnerLogic
{
    public HerobrineSpawnerLogic(Supplier<World> world, Supplier<BlockPos> pos, Block block)
    {
	super(world, pos, block);
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
	    ItemMonsterPlacer.spawnCreature(world.get(), this.getEntityId(), pos.get().getX() + 0.5, pos.get().getY(), pos.get().getZ() + 0.5);
	}

	this.onSpawn(world.get(), pos.get());
    }

    @Override
    protected void onSpawn(World world, BlockPos blockpos)
    {
	world.addWeatherEffect(new EntityLightningBolt(world, blockpos.getX(), blockpos.getY() + 2, blockpos.getZ(), false));
	super.onSpawn(world, blockpos);
    }
}