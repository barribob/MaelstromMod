package com.barribob.MaelstromMod.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * A base class for mod foliage
 *
 */
public class BlockModBush extends BlockBase implements IPlantable
{
    protected static final AxisAlignedBB BUSH_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.6000000238418579D,
	    0.699999988079071D);
    private Block grassBlock;

    public BlockModBush(String name, Material material, Block grassBlock, float hardness, float resistance, SoundType soundType)
    {
	super(name, material, hardness, resistance, soundType);
	this.setTickRandomly(true);
	this.grassBlock = grassBlock;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
	return null;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
	IBlockState state = world.getBlockState(pos);
	if (state.getBlock() != this)
	    return getDefaultState();
	return state;
    }

    /**
     * Checks if this block can be placed exactly at the given position.
     */
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
	IBlockState soil = worldIn.getBlockState(pos.down());
	return super.canPlaceBlockAt(worldIn, pos) && soil.getBlock() == grassBlock;
    }

    /**
     * Called when a neighboring block was changed and marks that this state should
     * perform any checks during a neighbor change. Cases may include when redstone
     * power is updated, cactus blocks popping off due to a neighboring solid block,
     * etc.
     */
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
	super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
	this.checkAndDropBlock(worldIn, pos, state);
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
	this.checkAndDropBlock(worldIn, pos, state);
    }

    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {
	if (!this.canBlockStay(worldIn, pos, state))
	{
	    this.dropBlockAsItem(worldIn, pos, state, 0);
	    worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
	}
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
	if (state.getBlock() == this) // Forge: This function is called during world gen and placement, before this
				      // block is set, so if we are not 'here' then assume it's the pre-check.
	{
	    IBlockState soil = worldIn.getBlockState(pos.down());
	    return soil.getBlock() == grassBlock;
	}
	return this.canSustainBush(worldIn.getBlockState(pos.down()));
    }

    /**
     * Return true if the block can sustain a Bush
     */
    protected boolean canSustainBush(IBlockState state)
    {
	return state.getBlock() == grassBlock;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
	return NULL_AABB;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
	return BUSH_AABB;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for
     * render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
	return false;
    }

    public boolean isFullCube(IBlockState state)
    {
	return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
	return BlockRenderLayer.CUTOUT;
    }

    /**
     * Get the geometry of the queried face at the given position and state. This is
     * used to decide whether things like buttons are allowed to be placed on the
     * face, or how glass panes connect to the face, among other things.
     * <p>
     * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED},
     * which represents something that does not fit the other descriptions and will
     * generally cause other things not to connect to the face.
     * 
     * @return an approximation of the form of the given face
     */
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
	return BlockFaceShape.UNDEFINED;
    }
}
