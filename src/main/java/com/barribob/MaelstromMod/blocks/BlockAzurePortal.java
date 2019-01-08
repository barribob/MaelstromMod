package com.barribob.MaelstromMod.blocks;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModDimensions;
import com.barribob.MaelstromMod.util.AzureTeleporter;
import com.barribob.MaelstromMod.util.Teleport;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * The portal block for the azure dimension
 *
 */
public class BlockAzurePortal extends BlockBase
{
    private final Block rimBlock = ModBlocks.LIGHT_AZURE_STONE;
    private final Block portalBlock = ModBlocks.AZURE_PORTAL;

    protected static final AxisAlignedBB QUARTER_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

    public BlockAzurePortal(String name, Material material, SoundType soundType)
    {
	super(name, material, 1000, 1000, soundType);
	this.setBlockUnbreakable();
	this.setLightLevel(0.5f);
	this.setLightOpacity(0);
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
		return;
	    }

	    if (player.dimension == 0)
	    {
		Teleport.teleportToDimension(player, ModDimensions.DIMENSION_AZURE_ID, new AzureTeleporter(server.getWorld(ModDimensions.DIMENSION_AZURE_ID)));
	    }
	    else if (player.dimension == ModDimensions.DIMENSION_AZURE_ID)
	    {
		Teleport.teleportToDimension(player, 0, new AzureTeleporter(server.getWorld(0)));
	    }

	    player.timeUntilPortal = 100;
	}
    }

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

    public boolean isFullCube(IBlockState state)
    {
	return false;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
	return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
	return BlockRenderLayer.TRANSLUCENT;
    }

    /**
     * If the block is connected with itself, don't render the sides
     */
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
    public int quantityDropped(Random p_149745_1_)
    {
	return 0;
    }
}