package com.barribob.MaelstromMod.entity.tileentity;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.util.ITickable;

/**
 * 
 * The tile entity for spawning maelstrom mobs, a one time spawner that sets itself to air
 * 
 * NOTE: Because mincraft uses .newInstance() to instantiate the tile entities, contructors
 * with arguments don't work :(
 *
 */
public class TileEntityDisappearingSpawner extends TileEntityMobSpawner implements ITickable
{
    @Override
    protected MobSpawnerLogic getSpawnerLogic()
    {
	return new DisappearingSpawnerLogic(() -> world, () -> pos, ModBlocks.DISAPPEARING_SPAWNER);
    }
}