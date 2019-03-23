package com.barribob.MaelstromMod.blocks.key_blocks;

import com.barribob.MaelstromMod.entity.util.EntityAzurePortalSpawn;
import com.barribob.MaelstromMod.entity.util.EntityPortalSpawn;

import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBrownKey extends BlockKey
{
    public BlockBrownKey(String name, Item item)
    {
	super(name, item);
    }

    @Override
    protected void spawnPortalEntity(World world, BlockPos pos)
    {
	// Not yet implemented
    }
}
