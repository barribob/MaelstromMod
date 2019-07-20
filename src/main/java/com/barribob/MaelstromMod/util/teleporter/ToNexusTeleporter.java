package com.barribob.MaelstromMod.util.teleporter;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.world.dimension.nexus.DimensionNexus;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * 
 * Finds a portal in the nexus dimension, or builds one Uses known offsets to
 * teleport precisely to the portal
 *
 */
public class ToNexusTeleporter extends Teleporter
{
    private BlockPos portalOffset;
    private int spacing;

    public ToNexusTeleporter(WorldServer worldIn, BlockPos portalOffset)
    {
	super(worldIn);
	if (this.world.provider.getDimensionType().getId() != ModConfig.nexus_dimension_id)
	{
	    System.err.println("The overworld to nexus teleporter is being used for the wrong dimension!");
	    return;
	}
	this.portalOffset = portalOffset;
	spacing = DimensionNexus.NexusStructureSpacing * 16;
    }

    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
	this.placeInExistingPortal(entityIn, rotationYaw);
    }

    /**
     * Places the entity in the portal (of which we know the location beforehand)
     */
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
    {
	int x = MathHelper.floor(entityIn.posX / spacing) * spacing + portalOffset.getX();
	int z = MathHelper.floor(entityIn.posZ / spacing) * spacing + portalOffset.getZ();
	int y = portalOffset.getY();
	if (entityIn instanceof EntityPlayerMP)
	{
	    ((EntityPlayerMP) entityIn).connection.setPlayerLocation(x, y, z, entityIn.rotationYaw, entityIn.rotationPitch);
	}
	else
	{
	    entityIn.setLocationAndAngles(x, y, z, entityIn.rotationYaw, entityIn.rotationPitch);
	}
	return true;
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw)
    {
	if (entity instanceof EntityPlayerMP)
	    placeInPortal(entity, yaw);
	else
	    placeInExistingPortal(entity, yaw);
    }
}
