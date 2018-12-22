package com.barribob.MaelstromMod.world.gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import scala.runtime.RangedProxy;

public class WorldGenMaelstrom extends WorldGenerator
{
    private final Block maelstromBlock;
    private final Block malestromCore;
    
    public WorldGenMaelstrom(Block block, Block core)
    {
	this.maelstromBlock = block;
	this.malestromCore = core;
    }
    
    /**
     * Generate the structure using a form of bfs
     */
    @Override
    public boolean generate(World worldIn, Random rand, BlockPos pos)
    {
	int size = rand.nextInt(20) + 20;
	
	// Move down until we hit land
	while(worldIn.getBlockState(pos).getBlock() == Blocks.AIR)
	{
	    pos = pos.down();
	    if(pos.getY() < 10) return false;
	}
			
	ArrayList<BlockPos> queue = new ArrayList<BlockPos>();
	queue.add(pos);
	
	for(int i = 0; i < size; i++)
	{
	    if(queue.size() == 0) return true;
	    
	    BlockPos randPos = queue.get(rand.nextInt(queue.size()));
	    queue.remove(randPos);
	    worldIn.setBlockState(randPos, this.maelstromBlock.getDefaultState());
	    
	    // Add in all directions except downward
	    addDirection(worldIn, randPos.add(1, 0, 0), queue);
	    addDirection(worldIn, randPos.add(-1, 0, 0), queue);
	    addDirection(worldIn, randPos.add(0, 0, 1), queue);
	    addDirection(worldIn, randPos.add(0, 0, -1), queue);
	    addDirection(worldIn, randPos.up(), queue);
	    addDirection(worldIn, randPos.down(), queue);
	}
	
	// Add the core in the approximate center
	worldIn.setBlockState(pos.down(), this.malestromCore.getDefaultState());
	
	return true;
    }
    
    /**
     * Adds the position to the queue if it is not already maelstrom
     * @param worldIn
     * @param pos
     * @param queue
     */
    private void addDirection(World worldIn, BlockPos pos, ArrayList<BlockPos> queue)
    {
	if (worldIn.getBlockState(pos).getBlock() != this.maelstromBlock && worldIn.getBlockState(pos).getBlock() != Blocks.AIR)
	{
	    queue.add(pos);
	}
    }
}
