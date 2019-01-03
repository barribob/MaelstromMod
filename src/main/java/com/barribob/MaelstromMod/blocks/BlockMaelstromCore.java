package com.barribob.MaelstromMod.blocks;

import java.util.Random;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.entities.TileEntityMalestromSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.IHasModel;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

/**
 * 
 * The maelstrom mob spawner. Also prevent decay of the maelstrom block
 *
 */
public class BlockMaelstromCore extends BlockContainer implements IHasModel
{
    public BlockMaelstromCore(String name, Material material)
    {
	super(material);
	setUnlocalizedName(name);
	setRegistryName(name);
	setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

	// Add both an item as a block and the block itself
	ModBlocks.BLOCKS.add(this);
	ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	this.hasTileEntity = true;
    }

    public BlockMaelstromCore(String name, Material material, float hardness, float resistance, SoundType soundType)
    {
	this(name, material);
	setHardness(hardness);
	setResistance(resistance);
	setSoundType(soundType);
    }

    @Override
    public void registerModels()
    {
	Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    /**
     * Tile Entity methods to make the tile entity spawner work
     */
    @Override
    public boolean hasTileEntity()
    {
	return false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
	return new TileEntityMalestromSpawner();
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model,
     * MODELBLOCK_ANIMATED for TESR-only, LIQUID for vanilla liquids, INVISIBLE to
     * skip all rendering
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
	return EnumBlockRenderType.MODEL;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
	return ModItems.AZURE_MAELSTROM_CORE_CRYSTAL;
    }
}
