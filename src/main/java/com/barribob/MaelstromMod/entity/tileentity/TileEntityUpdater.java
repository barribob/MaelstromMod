package com.barribob.MaelstromMod.entity.tileentity;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.util.IBlockUpdater;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityUpdater extends TileEntity implements ITickable
{

    @Override
    public void update()
    {
	if (world.isRemote && this.getBlockType() instanceof IBlockUpdater)
	{
	    ((IBlockUpdater) this.getBlockType()).update(world, pos);
	}
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
	return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
	NBTTagCompound nbttagcompound = this.writeToNBT(new NBTTagCompound());
	return nbttagcompound;
    }
}
