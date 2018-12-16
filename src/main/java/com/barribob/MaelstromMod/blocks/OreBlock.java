package com.barribob.MaelstromMod.blocks;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModItems;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

/**
 * 
 * Custom class to set properties of the ore block
 *
 */
public class OreBlock extends BlockBase
{
	public OreBlock(String name, Material material) 
	{
		super(name, material);
		
		// Set the walking sound type of the block
		setSoundType(SoundType.STONE);
		
		// Set the mining hardness
		setHardness(1.5f);
		
		// Explosion resistance
		setResistance(15.0f);
		
		// Set tool harvest level
		setHarvestLevel("pickaxe", 0);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) 
	{
		return ModItems.WHAPA;
	}
	
	@Override
	public int quantityDropped(Random rand) 
	{
		int max = 4;
		int min = 2;
		return rand.nextInt(max - min) + min;
	}
}
