package com.barribob.MaelstromMod.world.gen;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenMaelstrom extends WorldGenerator
{
    private final Block maelstromBlock;
    private final Block maelstromCore;
    private final Consumer<TileEntityMobSpawner> dataSetter;
    
    public WorldGenMaelstrom(Block block, Block core, Consumer<TileEntityMobSpawner> dataSetter)
    {
	this.maelstromBlock = block;
	this.maelstromCore = core;
	this.dataSetter = dataSetter;
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
	    
	    // Add in all directions
	    addDirection(worldIn, randPos.add(1, 0, 0), queue);
	    addDirection(worldIn, randPos.add(-1, 0, 0), queue);
	    addDirection(worldIn, randPos.add(0, 0, 1), queue);
	    addDirection(worldIn, randPos.add(0, 0, -1), queue);
	    addDirection(worldIn, randPos.up(), queue);
	    addDirection(worldIn, randPos.down(), queue);
	}
	
	// Add the core in the approximate center, and initialize the tile entity
	worldIn.setBlockState(pos.down(), this.maelstromCore.getDefaultState(), 2);
        TileEntity tileentity = worldIn.getTileEntity(pos.down());

        if (tileentity instanceof TileEntityMobSpawner)
        {
	    this.dataSetter.accept((TileEntityMobSpawner) tileentity);
        }
	
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
