package com.barribob.MaelstromMod.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * 
 * represents a double-block-tall grass block
 *
 */
public class BlockDoubleBrownedGrass extends BlockModBush
{
    public static final PropertyEnum<BlockDoublePlant.EnumBlockHalf> HALF = PropertyEnum.<BlockDoublePlant.EnumBlockHalf>create("half", BlockDoublePlant.EnumBlockHalf.class);

    public BlockDoubleBrownedGrass(String name, Material material, float hardness, float resistance, SoundType soundType)
    {
	super(name, material, Blocks.GRASS, hardness, resistance, soundType);
	this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.LOWER));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
	return FULL_BLOCK_AABB;
    }

    /**
     * Checks if this block can be placed exactly at the given position.
     */
    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
	return super.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos.up());
    }

    @Override
    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {
	if (!this.canBlockStay(worldIn, pos, state))
	{
	    boolean flag = state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER;
	    BlockPos blockpos = flag ? pos : pos.up();
	    BlockPos blockpos1 = flag ? pos.down() : pos;
	    Block block = flag ? this : worldIn.getBlockState(blockpos).getBlock();
	    Block block1 = flag ? worldIn.getBlockState(blockpos1).getBlock() : this;

	    if (!flag)
		this.dropBlockAsItem(worldIn, pos, state, 0); // Forge move above the setting to air.

	    if (block == this)
	    {
		worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
	    }

	    if (block1 == this)
	    {
		worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
	    }
	}
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
	if (state.getBlock() != this)
	    return super.canBlockStay(worldIn, pos, state); // Forge: This function is called during world gen and placement, before this
							    // block is set, so if we are not 'here' then assume it's the pre-check.
	if (state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER)
	{
	    return worldIn.getBlockState(pos.down()).getBlock() == this;
	}
	else
	{
	    IBlockState iblockstate = worldIn.getBlockState(pos.up());
	    return iblockstate.getBlock() == this && super.canBlockStay(worldIn, pos, iblockstate);
	}
    }

    public void placeAt(World worldIn, BlockPos lowerPos, int flags)
    {
	worldIn.setBlockState(lowerPos, this.getDefaultState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.LOWER), flags);
	worldIn.setBlockState(lowerPos.up(), this.getDefaultState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.UPPER), flags);
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place
     * logic
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
	worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.UPPER), 2);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
	return (meta & 8) > 0 ? this.getDefaultState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.UPPER)
		: this.getDefaultState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.LOWER);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
	return state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER ? 8 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
	return new BlockStateContainer(this, new IProperty[] { HALF });
    }

    /**
     * Get the OffsetType for this Block. Determines if the model is rendered
     * slightly offset.
     */
    @Override
    public Block.EnumOffsetType getOffsetType()
    {
	return Block.EnumOffsetType.XZ;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
	return null;
    }
}
