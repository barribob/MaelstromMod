package com.barribob.MaelstromMod.blocks.portal;

import java.util.Random;

import com.barribob.MaelstromMod.blocks.BlockBase;
import com.barribob.MaelstromMod.util.teleporter.Teleport;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * The base portal block class
 *
 */
public abstract class BlockPortal extends BlockBase
{
    private int entranceDimension;
    private int exitDimension;

    protected static final AxisAlignedBB QUARTER_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

    public BlockPortal(String name, int entranceDimension, int exitDimension)
    {
	super(name, Material.ROCK, 1000, 1000, SoundType.STONE);
	this.setBlockUnbreakable();
	this.setLightLevel(0.5f);
	this.setLightOpacity(0);
	this.entranceDimension = entranceDimension;
	this.exitDimension = exitDimension;
    }

    /**
     * Teleport the player to the correct dimension on collision
     */
    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
	if (entityIn instanceof EntityPlayerMP && !entityIn.isRiding() && !entityIn.isBeingRidden())
	{
	    /**
	     * Find the corner of the portal, so that the entire portal is treated as one
	     * position.
	     * 
	     * If this isn't done, then different parts of the same portal could potentially
	     * send someone to different areas. (Assumes a simple 3x3 x-z portal layout)
	     */
	    BlockPos portalCorner = pos;
	    for (int x = 0; x >= -2; x--)
	    {
		for (int z = 0; z >= -2; z--)
		{
		    if (worldIn.getBlockState(pos.add(new BlockPos(x, 0, z))).getBlock() == this)
		    {
			portalCorner = pos.add(new BlockPos(x, 0, z));
		    }
		}
	    }

	    EntityPlayerMP player = (EntityPlayerMP) entityIn;
	    player.connection.setPlayerLocation(portalCorner.getX(), portalCorner.getY(), portalCorner.getZ(), player.rotationYaw, player.rotationPitch);

	    if (player.dimension == entranceDimension)
	    {
		Teleport.teleportToDimension(player, exitDimension, getExitTeleporter(worldIn));
	    }
	    else // Entering the portal from any other dimension will teleport the player to that dimension
	    {
		Teleport.teleportToDimension(player, entranceDimension, getEntranceTeleporter(worldIn));
	    }
	}
    }

    protected abstract Teleporter getEntranceTeleporter(World world);

    protected abstract Teleporter getExitTeleporter(World world);

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
	return NULL_AABB;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
	return QUARTER_AABB;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
	return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
	return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
	return BlockRenderLayer.TRANSLUCENT;
    }

    /**
     * If the block is connected with itself, don't render the sides
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
	if (side == EnumFacing.NORTH && blockAccess.getBlockState(pos.north()).getBlock() == this)
	{
	    return false;
	}

	if (side == EnumFacing.SOUTH && blockAccess.getBlockState(pos.south()).getBlock() == this)
	{
	    return false;
	}

	if (side == EnumFacing.WEST && blockAccess.getBlockState(pos.west()).getBlock() == this)
	{
	    return false;
	}

	if (side == EnumFacing.EAST && blockAccess.getBlockState(pos.east()).getBlock() == this)
	{
	    return false;
	}

	return true;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random p_149745_1_)
    {
	return 0;
    }
}