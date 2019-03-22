package com.barribob.MaelstromMod.entity.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.Vec3d;

public class TileEntityTeleporter extends TileEntity implements ITickable
{
    private Vec3d relTeleportPos = new Vec3d(0, 3, 0);

    public void setRelTeleportPos(Vec3d pos)
    {
	this.relTeleportPos = pos;
    }
    
    public void update()
    {
	EntityPlayer player = this.world.getClosestPlayer(this.pos.getX() + 0.5f, this.pos.getY() + 0.5f, this.pos.getZ() + 0.5f, 1, false);
	if(player != null)
	{
	    player.setPosition(player.posX + this.relTeleportPos.x, player.posY + this.relTeleportPos.y, player.posZ + this.relTeleportPos.z);
	}
    }

    public void readFromNBT(NBTTagCompound compound)
    {
	super.readFromNBT(compound);
	if (compound.hasKey("teleport_pos_x") && compound.hasKey("teleport_pos_y") && compound.hasKey("teleport_pos_z"))
	{
	    double x = compound.getDouble("teleport_pos_x");
	    double y = compound.getDouble("teleport_pos_y");
	    double z = compound.getDouble("teleport_pos_z");
	    this.relTeleportPos = new Vec3d(x, y, z);
	}
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
	super.writeToNBT(compound);
	compound.setDouble("teleport_pos_x", this.relTeleportPos.x);
	compound.setDouble("teleport_pos_y", this.relTeleportPos.y);
	compound.setDouble("teleport_pos_y", this.relTeleportPos.z);
	return compound;
    }
    

    public boolean onlyOpsCanSetNbt()
    {
	return true;
    }
}
