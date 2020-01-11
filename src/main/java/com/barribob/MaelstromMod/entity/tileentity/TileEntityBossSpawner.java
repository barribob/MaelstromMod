package com.barribob.MaelstromMod.entity.tileentity;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.util.ITickable;

/**
 * 
 * The tile entity for spawning maelstrom mobs
 *
 */
public class TileEntityBossSpawner extends TileEntityMobSpawner implements ITickable
{
    @Override
    protected MobSpawnerLogic getSpawnerLogic()
    {
	return new BossSpawnerLogic(() -> world, () -> pos, ModBlocks.BOSS_SPAWNER);
    }
}