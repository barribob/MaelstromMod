package com.barribob.MaelstromMod.util.teleporter;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.world.dimension.nexus.DimensionNexus;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * 
 * Finds a portal in the overworld from the nexus, or builds one Uses known
 * offsets to teleport precisely to the portal
 *
 */
public class NexusToOverworldTeleporter extends Teleporter
{
    public static final int yPortalOffset = 139;
    private int spacing;

    public NexusToOverworldTeleporter(WorldServer worldIn)
    {
	super(worldIn);
	if (this.world.provider.getDimensionType().getId() != 0)
	{
	    System.err.println("The nexus to overworld teleporter is being used for the wrong dimension!");
	}
	spacing = DimensionNexus.NexusStructureSpacing * 16;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
	if (!this.placeInExistingPortal(entityIn, rotationYaw))
	{
	    this.makePortal(entityIn);
	    this.placeInExistingPortal(entityIn, rotationYaw);
	}
    }

    /**
     * Finds an existing portal to teleport the player to
     */
    @Override
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
    {
	int startX = MathHelper.floor(entityIn.posX / spacing) * spacing;
	int startZ = MathHelper.floor(entityIn.posZ / spacing) * spacing;
	Vec3d entityOffset = new Vec3d(1.5, 1, -0.5);

	/**
	 * This is an algorithm that depends on the assumption that the create portal
	 * will always be at a certain height, and that the portal will be at least 3 x
	 * 3 wide. 
	 */
	for (int x = startX; x < startX + spacing; x += 3)
	{
	    for (int z = startZ; z < startZ + spacing; z += 3)
	    {
		if (this.world.getBlockState(new BlockPos(x, yPortalOffset, z)).getBlock() == ModBlocks.NEXUS_PORTAL)
		{
		    if (entityIn instanceof EntityPlayerMP)
		    {
			((EntityPlayerMP) entityIn).connection.setPlayerLocation(x + entityOffset.x, yPortalOffset + entityOffset.y, z + entityOffset.z, entityIn.rotationYaw, entityIn.rotationPitch);
		    }
		    else
		    {
			entityIn.setLocationAndAngles(x + entityOffset.x, yPortalOffset + entityOffset.y, z + entityOffset.z, entityIn.rotationYaw, entityIn.rotationPitch);
		    }
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * Creates a simple portal
     */
    @Override
    public boolean makePortal(Entity entity)
    {
	int i = MathHelper.floor(entity.posX / spacing) * spacing;
	int k = MathHelper.floor(entity.posZ / spacing) * spacing;
	int j = yPortalOffset;

	// Clear the area of air blocks
	int size = 5;
	for (int x = i; x < i + size; x++)
	{
	    for (int z = k; z < k + size; z++)
	    {
		for (int y = j; y < j + 2; y++)
		{
		    world.setBlockToAir(new BlockPos(x, y, z));
		}
	    }
	}

	// Add the portal blocks
	for (int x = i; x < i + size; x++)
	{
	    for (int z = k; z < k + size; z++)
	    {
		world.setBlockState(new BlockPos(x, j, z), Blocks.QUARTZ_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(x, j - 1, z), Blocks.QUARTZ_BLOCK.getDefaultState());
	    }
	}

	int size2 = size - 2;
	for (int x = i + 1; x < i + size2 + 1; x++)
	{
	    for (int z = k + 1; z < k + size2 + 1; z++)
	    {
		world.setBlockState(new BlockPos(x, j, z), ModBlocks.NEXUS_PORTAL.getDefaultState());
	    }
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
