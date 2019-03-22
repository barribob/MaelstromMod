package com.barribob.MaelstromMod.blocks.key_blocks;

import com.barribob.MaelstromMod.entity.util.EntityAzurePortalSpawn;
import com.barribob.MaelstromMod.entity.util.EntityPortalSpawn;

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
    protected EntityPortalSpawn getPortalSpawn(World world, BlockPos pos)
    {
	return new EntityAzurePortalSpawn(world, pos.getX(), pos.getY(), pos.getZ());
    }
}
