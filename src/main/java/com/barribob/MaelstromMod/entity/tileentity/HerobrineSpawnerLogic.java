package com.barribob.MaelstromMod.entity.tileentity;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HerobrineSpawnerLogic extends DisappearingSpawnerLogic
{
    public HerobrineSpawnerLogic(Supplier<World> world, Supplier<BlockPos> pos, Block block)
    {
	super(world, pos, block);
    }

    @Override
    public void updateSpawner()
    {
	if (this.world.get().isRemote || !this.isActivated())
	{
	    return;
	}

	if (this.spawnDelay > 0)
	{
	    --this.spawnDelay;
	    return;
	}

	ItemMonsterPlacer.spawnCreature(world.get(), new ResourceLocation(this.getEntityData().mobId), pos.get().getX() + 0.5, pos.get().getY(), pos.get().getZ() + 0.5);

	this.onSpawn(world.get(), pos.get());
    }
}