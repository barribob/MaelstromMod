package com.barribob.MaelstromMod.util.teleporter;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.world.dimension.nexus.DimensionNexus;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;

public class ToStructuralDimensionTeleporter extends Teleporter
{
    private BlockPos portalOffset;
    private int spacing;
    private WorldGenerator structure;

    public ToStructuralDimensionTeleporter(WorldServer worldIn, BlockPos portalOffset, @Nullable WorldGenerator structure)
    {
	super(worldIn);
	this.portalOffset = portalOffset;
	spacing = DimensionNexus.NexusStructureSpacing * 16;
	this.structure = structure;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
	this.placeInExistingPortal(entityIn, rotationYaw);
    }

    /**
     * Places the entity in the portal (of which we know the location beforehand)
     */
    @Override
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
    {
	int x = MathHelper.floor(entityIn.posX / spacing) * spacing + portalOffset.getX();
	int z = MathHelper.floor(entityIn.posZ / spacing) * spacing + portalOffset.getZ();
	int y = portalOffset.getY();
	Vec3d entityOffset = new Vec3d(0.5, 1, -1.5);

	if (entityIn instanceof EntityPlayerMP)
	{
	    if (structure != null && !this.world.isChunkGeneratedAt(x >> 4, z >> 4))
	    {
		// Round the position to the nearest 64th chunk square
		int chunkX = Math.floorDiv((x >> 4), DimensionNexus.NexusStructureSpacing) * DimensionNexus.NexusStructureSpacing;
		int chunkZ = Math.floorDiv((z >> 4), DimensionNexus.NexusStructureSpacing) * DimensionNexus.NexusStructureSpacing;
		structure.generate(world, random, new BlockPos(chunkX * 16 + 8, 50, chunkZ * 16 + 8));
	    }
	    ((EntityPlayerMP) entityIn).connection.setPlayerLocation(x + entityOffset.x, y + entityOffset.y, z + entityOffset.z, entityIn.rotationYaw, entityIn.rotationPitch);
	}
	else
	{
	    entityIn.setLocationAndAngles(x + entityOffset.x, y + entityOffset.y, z + entityOffset.z, entityIn.rotationYaw, entityIn.rotationPitch);
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
