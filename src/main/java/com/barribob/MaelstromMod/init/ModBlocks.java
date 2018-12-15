package com.barribob.MaelstromMod.init;

import java.util.ArrayList;
import java.util.List;

import com.barribob.MaelstromMod.blocks.BlockBase;
import com.barribob.MaelstromMod.blocks.OreBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * 
 * Holds all of our new blocks
 *
 */
public class ModBlocks 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block ORE = new OreBlock("ore", Material.ROCK);
}
