package com.barribob.MaelstromMod.entity.tileentity;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.Vec3d;

public class TileEntityTeleporter extends TileEntity implements ITickable
{
    private Vec3d relTeleportPos;

    public void setRelTeleportPos(Vec3d pos)
    {
	this.relTeleportPos = pos;
    }
    
    public void update()
    {
	float activationDistance = 0.7f;
	float particleDistance = 6f;
	EntityPlayer player = this.world.getClosestPlayer(this.pos.getX() + 0.5f, this.pos.getY() + 0.5f, this.pos.getZ() + 0.5f, activationDistance, false);
	if(player != null && this.relTeleportPos != null)
	{
	    player.attemptTeleport(player.posX + this.relTeleportPos.x, player.posY + this.relTeleportPos.y, player.posZ + this.relTeleportPos.z);
	}
	
	// Spawn a line of particles indicating what direction the teleport is
	player = this.world.getClosestPlayer(this.pos.getX() + 0.5f, this.pos.getY() + 0.5f, this.pos.getZ() + 0.5f, particleDistance, false);
	if(this.world.isRemote && player != null && this.relTeleportPos != null)
	{
	    double spacing = 4;
	    Vec3d pos = new Vec3d(this.pos.getX() + 0.5, this.pos.getY() + 3, this.pos.getZ() + 0.5);
	    double particles = this.relTeleportPos.lengthVector() / spacing;
	    double granularity = 20;
	    Vec3d timeOffset = this.relTeleportPos.normalize().scale((this.world.getTotalWorldTime() % granularity) / (granularity / spacing));
	    for(int i = 0; i < particles; i++)
	    {
		Vec3d offset = this.relTeleportPos.normalize().scale(i * spacing);
		float noiseFactor = 0.25f;
		Vec3d noise = new Vec3d(ModRandom.getFloat(noiseFactor), ModRandom.getFloat(noiseFactor), ModRandom.getFloat(noiseFactor));
		ParticleManager.spawnEffect(this.world, pos.add(offset).add(timeOffset).add(noise), new Vec3d(1, 1, 1));
	    }
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
	if (this.relTeleportPos != null)
	{
	    compound.setDouble("teleport_pos_x", this.relTeleportPos.x);
	    compound.setDouble("teleport_pos_y", this.relTeleportPos.y);
	    compound.setDouble("teleport_pos_z", this.relTeleportPos.z);
	}
	return compound;
    }
    
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
	return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    public NBTTagCompound getUpdateTag()
    {
	NBTTagCompound nbttagcompound = this.writeToNBT(new NBTTagCompound());
	return nbttagcompound;
    }
}
