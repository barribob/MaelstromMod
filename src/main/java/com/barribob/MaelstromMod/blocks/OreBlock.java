package com.barribob.MaelstromMod.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

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
}
