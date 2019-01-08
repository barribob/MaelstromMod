package com.barribob.MaelstromMod.blocks;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAzureVines extends BlockVine implements IHasModel
{
    public BlockAzureVines(String name)
    {
	super();
	setUnlocalizedName(name);
	setRegistryName(name);

	// Add both an item as a block and the block itself
	ModBlocks.BLOCKS.add(this);
	ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public BlockAzureVines(String name, float hardness, float resistance, SoundType soundType)
    {
	this(name);
	setHardness(hardness);
	setResistance(resistance);
	setSoundType(soundType);
    }

    @Override
    public void registerModels()
    {
	Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
    
    public boolean canAttachTo(World p_193395_1_, BlockPos p_193395_2_, EnumFacing p_193395_3_)
    {
        Block block = p_193395_1_.getBlockState(p_193395_2_.up()).getBlock();
        return this.isAcceptableNeighbor(p_193395_1_, p_193395_2_.offset(p_193395_3_.getOpposite()), p_193395_3_) && (block == Blocks.AIR || block == ModBlocks.AZURE_VINES || this.isAcceptableNeighbor(p_193395_1_, p_193395_2_.up(), EnumFacing.UP));
    }
    
    private boolean isAcceptableNeighbor(World p_193396_1_, BlockPos p_193396_2_, EnumFacing p_193396_3_)
    {
        IBlockState iblockstate = p_193396_1_.getBlockState(p_193396_2_);
        return iblockstate.getBlockFaceShape(p_193396_1_, p_193396_2_, p_193396_3_) == BlockFaceShape.SOLID && !isExceptBlockForAttaching(iblockstate.getBlock());
    }


}
