package com.barribob.MaelstromMod.blocks.key_blocks;

import com.barribob.MaelstromMod.entity.util.EntityAzurePortalSpawn;

import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAzureKey extends BlockKey
{
    public BlockAzureKey(String name, Item item)
    {
	super(name, item);
    }

    @Override
    protected void spawnPortalEntity(World world, BlockPos pos)
    {
	world.spawnEntity(new EntityAzurePortalSpawn(world, pos.getX(), pos.getY(), pos.getZ()));
    }
}
