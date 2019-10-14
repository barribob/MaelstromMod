package com.barribob.MaelstromMod.blocks.key_blocks;

import com.barribob.MaelstromMod.entity.entities.EntityHerobrineCliff;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
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
	if (!world.isRemote)
	{
	    world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY() + 2, pos.getZ(), false));
	    Entity herobrine = new EntityHerobrineCliff(world);
	    herobrine.setPosition(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f);
	    world.spawnEntity(herobrine);
	}
    }
}
