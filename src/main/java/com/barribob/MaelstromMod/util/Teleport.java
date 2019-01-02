package com.barribob.MaelstromMod.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * 
 * Responsible for teleporting the player between dimensions
 *
 */
public class Teleport extends Teleporter
{
    private final WorldServer world;
    private double x, y, z;

    public Teleport(WorldServer world, double x, double y, double z)
    {
	super(world);
	this.world = world;
	this.x = x;
	this.y = y;
	this.z = z;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
	this.world.getBlockState(new BlockPos((int) x, (int) y, (int) z));
	entityIn.setPosition(x, y, z);
	entityIn.setVelocity(0, 0, 0);
    }

    public static void teleportToDimension(EntityPlayer player, int dimension, double x, double y, double z)
    {
	EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
	MinecraftServer server = player.getEntityWorld().getMinecraftServer();
	WorldServer worldServer = server.getWorld(dimension);

	if (worldServer == null || server == null)
	{
	    throw new IllegalArgumentException("Dimension: " + dimension + " doesn't exist");
	}

	worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new Teleport(worldServer, x, y, z));
	player.setPositionAndUpdate(x, y, z);
    }
}
