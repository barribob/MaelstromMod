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
 * Finds a portal in the nexus dimension, or builds one
 * Uses known offsets to teleport precisely to the portal
 *
 */
public class NexusTeleporter extends Teleporter
{
    
    private BlockPos portalOffset;
    private int spacing;
    public NexusTeleporter(WorldServer worldIn)
    {
	super(worldIn);
	if(this.world.provider.getDimensionType().getId() == ModConfig.nexus_dimension_id)
	{
	    this.portalOffset = new BlockPos(70, 80, 103);
	}
	else if(this.world.provider.getDimensionType().getId() == 0)
	{
	    this.portalOffset = new BlockPos(21, 139, 26);
	}
	spacing = DimensionNexus.NexusStructureSpacing * 16;
    }

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
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
    {
	int i = spacing / 2;
	int x = MathHelper.floor(entityIn.posX / spacing) * spacing + portalOffset.getX();
	int z = MathHelper.floor(entityIn.posZ / spacing) * spacing + portalOffset.getZ();
	int y = portalOffset.getY();
	BlockPos portalPos = BlockPos.ORIGIN;
	long l = ChunkPos.asLong(x, z);

	if (this.destinationCoordinateCache.containsKey(l))
	{
	    Teleporter.PortalPosition teleporter$portalposition = (Teleporter.PortalPosition) this.destinationCoordinateCache.get(l);
	    portalPos = teleporter$portalposition;
	    teleporter$portalposition.lastUpdateTime = this.world.getTotalWorldTime();
	}
	else
	{
	    BlockPos entityPos = new BlockPos(entityIn);
	    BlockPos pos = new BlockPos(x, y, z);
	    if (this.world.getBlockState(pos).getBlock() == ModBlocks.NEXUS_PORTAL)
	    {
		portalPos = pos;
	    }
	}

	if (portalPos == BlockPos.ORIGIN)
	{
	    return false;
	}
	
	System.out.println(portalPos);
	
	this.destinationCoordinateCache.put(l, new Teleporter.PortalPosition(portalPos, this.world.getTotalWorldTime()));
	
	if (entityIn instanceof EntityPlayerMP)
	{
	    ((EntityPlayerMP) entityIn).connection.setPlayerLocation(portalPos.getX(), portalPos.getY(), portalPos.getZ(), entityIn.rotationYaw, entityIn.rotationPitch);
	}
	else
	{
            entityIn.setLocationAndAngles(portalPos.getX(), portalPos.getY(), portalPos.getZ(), entityIn.rotationYaw, entityIn.rotationPitch);
	}

	return true;
    }

    /**
     * Creates a simple portal
     */
    public boolean makePortal(Entity entity)
    {
	int i = MathHelper.floor(entity.posX / spacing) * spacing + portalOffset.getX();
	int k = MathHelper.floor(entity.posZ / spacing) * spacing + portalOffset.getZ();
        int j = portalOffset.getY();

        // Clear the area of air blocks
        int size = 3;
        for(int x = i - size; x < i + size; x++)
        {
            for(int z = k - size; z < k + size; z++)
            {
        	for(int y = j; y < j + 2; y++)
                {
                    world.setBlockToAir(new BlockPos(x, y, z));            	
                }
            }
        }
        
        // Add the portal blocks
        for(int x = i - size; x < i + size; x++)
        {
            for(int z = k - size; z < k + size; z++)
            {
        	world.setBlockState(new BlockPos(x, j, z), Blocks.QUARTZ_BLOCK.getDefaultState());
        	world.setBlockState(new BlockPos(x, j - 1, z), Blocks.QUARTZ_BLOCK.getDefaultState());
            }   
        }
        
        int size3 = size - 1;
        for(int x = i - size3; x < i + size3; x++)
        {
            for(int z = k - size3; z < k + size3; z++)
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
