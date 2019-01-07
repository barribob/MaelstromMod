package com.barribob.MaelstromMod.blocks;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModDimensions;
import com.barribob.MaelstromMod.util.Teleport;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * 
 * The portal block for the azure dimension
 *
 */
public class BlockAzurePortal extends BlockBase
{
    private final Block rimBlock = ModBlocks.LIGHT_AZURE_STONE;
    private final Block portalBlock = ModBlocks.AZURE_PORTAL;

    public BlockAzurePortal(String name, Material material, SoundType soundType)
    {
	super(name, material, 1000, 1000, soundType);
	this.setBlockUnbreakable();
	this.setLightLevel(0.5f);
    }

    /**
     * Teleport the player to the correct dimension on collision
     */
    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
	if (entityIn instanceof EntityPlayerMP && !entityIn.isRiding() && !entityIn.isBeingRidden())
	{
	    EntityPlayerMP player = (EntityPlayerMP) entityIn;

	    MinecraftServer server = worldIn.getMinecraftServer();

	    if (player.timeUntilPortal > 0)
	    {
		player.timeUntilPortal = 3;
	    }
	    else if (player.dimension == 0)
	    {
		player.timeUntilPortal = 3;
		Teleport.teleportToDimension(player, ModDimensions.DIMENSION_AZURE_ID, player.posX, player.posY, player.posZ);
	    }
	    else if (player.dimension == ModDimensions.DIMENSION_AZURE_ID)
	    {
		player.timeUntilPortal = -3;
		Teleport.teleportToDimension(player, 0, player.posX, player.posY, player.posZ);
	    }
	}
    }

    /**
     * Will destroy itself if the blocks next to it aren't appropriate
     */
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {	
	if(fromPos.getY() != pos.getY())
	{
	    return;
	}
	
	if(world.getBlockState(fromPos).getBlock() != this.rimBlock && world.getBlockState(fromPos).getBlock() != this.portalBlock)
	{
	    world.setBlockToAir(pos);
	}
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube? This determines whether or
     * not to render the shared face of two adjacent blocks and also whether the
     * player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
	return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
	return NULL_AABB;
    }

    public boolean isFullCube(IBlockState state)
    {
	return false;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random p_149745_1_)
    {
	return 0;
    }
}