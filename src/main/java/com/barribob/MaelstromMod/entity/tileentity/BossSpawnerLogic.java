package com.barribob.MaelstromMod.entity.tileentity;

import java.util.function.Supplier;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.util.ModRandom;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BossSpawnerLogic extends DisappearingSpawnerLogic
{
    public BossSpawnerLogic(Supplier<World> world, Supplier<BlockPos> pos, Block block)
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

	Entity entity = ItemMonsterPlacer.spawnCreature(world.get(), new ResourceLocation(this.getEntityData().mobId), pos.get().getX() + 0.5, pos.get().getY(), pos.get().getZ() + 0.5);

	if (entity != null && entity instanceof EntityLeveledMob)
	{
	    EntityLeveledMob leveledMob = (EntityLeveledMob) entity;
	    leveledMob.setElement(ModRandom.choice(getEntityData().possibleElements, this.world.get().rand, getEntityData().elementalWeights).next());
	    leveledMob.setLevel(level);
	}

	this.onSpawn(world.get(), pos.get());
    }
}