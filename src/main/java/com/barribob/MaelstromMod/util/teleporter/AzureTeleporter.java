package com.barribob.MaelstromMod.util.teleporter;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModDimensions;

import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * 
 * Finds a portal in the azure dimension, or builds one
 *
 */
public class AzureTeleporter extends Teleporter
{
    public AzureTeleporter(WorldServer worldIn)
    {
	super(worldIn);
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
	int i = 64;
	int j = MathHelper.floor(entityIn.posX);
	int k = MathHelper.floor(entityIn.posZ);
	BlockPos portalPos = BlockPos.ORIGIN;
	long l = ChunkPos.asLong(j, k);

	if (this.destinationCoordinateCache.containsKey(l))
	{
	    Teleporter.PortalPosition teleporter$portalposition = (Teleporter.PortalPosition) this.destinationCoordinateCache.get(l);
	    portalPos = teleporter$portalposition;
	    teleporter$portalposition.lastUpdateTime = this.world.getTotalWorldTime();
	}
	else
	{
	    BlockPos entityPos = new BlockPos(entityIn);

	    for (int i1 = -i; i1 <= i; ++i1)
	    {
		BlockPos blockpos2;

		for (int j1 = -i; j1 <= i; ++j1)
		{
		    for (BlockPos blockpos1 = entityPos.add(i1, this.world.getActualHeight() - 1 - entityPos.getY(), j1); blockpos1.getY() >= 0; blockpos1 = blockpos2)
		    {
			blockpos2 = blockpos1.down();

			if (this.world.getBlockState(blockpos1).getBlock() == ModBlocks.AZURE_PORTAL)
			{
			    portalPos = blockpos1;
			}
		    }
		}
	    }
	}

	if (portalPos == BlockPos.ORIGIN)
	{
	    return false;
	}
		
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
        int i = MathHelper.floor(entity.posX);
        int k = MathHelper.floor(entity.posZ);

        int j = world.getActualHeight() - 1;
        
        while(!world.getBlockState(new BlockPos(i, j, k)).isFullBlock() && j > 0)
        {
            j--;
        }
                
        // Clear the area of air blocks
        int size = 4;
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
        	world.setBlockState(new BlockPos(x, j, z), ModBlocks.LIGHT_AZURE_STONE.getDefaultState());
            }   
        }
        
        int size2 = size - 1;
        for(int x = i - size2; x < i + size2; x++)
        {
            for(int z = k - size2; z < k + size2; z++)
            {
        	world.setBlockState(new BlockPos(x, j + 1, z), ModBlocks.LIGHT_AZURE_STONE.getDefaultState());
        	world.setBlockState(new BlockPos(x, j - 1, z), ModBlocks.LIGHT_AZURE_STONE.getDefaultState());
            }   
        }
        
        int size3 = size2 - 1;
        for(int x = i - size3; x < i + size3; x++)
        {
            for(int z = k - size3; z < k + size3; z++)
            {
        	world.setBlockState(new BlockPos(x, j + 1, z), ModBlocks.AZURE_PORTAL.getDefaultState());
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
