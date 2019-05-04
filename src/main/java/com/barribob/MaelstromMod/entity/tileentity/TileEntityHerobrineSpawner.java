package com.barribob.MaelstromMod.entity.tileentity;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.util.ITickable;

/**
 * 
 * The tile entity for spawning maelstrom mobs
 *
 */
public class TileEntityHerobrineSpawner extends TileEntityMobSpawner implements ITickable
{
    @Override
    protected MobSpawnerLogic getSpawnerLogic()
    {
	return new HerobrineSpawnerLogic(() -> world, () -> pos, ModBlocks.NEXUS_HEROBRINE_SPAWNER);
    }
}