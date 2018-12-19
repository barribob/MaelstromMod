package com.barribob.MaelstromMod.world.gen;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;

/**
 * 
 * Tree structure based on the Canopy Tree
 *
 */
public class WorldGenAzureTree extends WorldGenAbstractTree
{
	private static final IBlockState AZURE_LOG = ModBlocks.AZURE_LOG.getDefaultState();
    private static final IBlockState AZURE_LEAVES = ModBlocks.AZURE_LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

	public WorldGenAzureTree(boolean notify) 
	{
		super(notify);
	}

    private void placeLogAt(World worldIn, BlockPos pos)
    {
        if (this.canGrowInto(worldIn.getBlockState(pos).getBlock()))
        {
            this.setBlockAndNotifyAdequately(worldIn, pos, AZURE_LOG);
        }
    }

    private void placeLeafAt(World worldIn, int x, int y, int z)
    {
        BlockPos blockpos = new BlockPos(x, y, z);
        IBlockState state = worldIn.getBlockState(blockpos);

        if (state.getBlock().isAir(state, worldIn, blockpos))
        {
            this.setBlockAndNotifyAdequately(worldIn, blockpos, AZURE_LEAVES);
        }
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int relativeHeight = rand.nextInt(5) + 7;
        int leafHeight =  rand.nextInt(2) + 2;
        int maxLeafWidth = 1 + rand.nextInt(leafHeight + 1);
        int additionalHeight = rand.nextInt(3) + 1;
        int additionalTrees = rand.nextInt(4) + 1;
        int maxLeafDecayChance = 3;

        // Check to make sure the tree position is valid
        if (position.getY() >= 1 && position.getY() + relativeHeight + 1 <= 256)
        {
            boolean flag = true;

            for (int y = position.getY(); y <= position.getY() + 1 + relativeHeight + additionalHeight * additionalTrees && flag; ++y)
            {
                int j1 = 1;

                if (y - position.getY() < leafHeight + relativeHeight)
                {
                    j1 = 0;
                }
                else
                {
                    j1 = maxLeafWidth;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int x = position.getX() - j1; x <= position.getX() + j1 && flag; ++x)
                {
                    for (int z = position.getZ() - j1; z <= position.getZ() + j1 && flag; ++z)
                    {
                        if (y >= 0 && y < 256)
                        {
                            if (!this.isReplaceable(worldIn,blockpos$mutableblockpos.setPos(x, y, z)))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                BlockPos down = position.down();
                IBlockState state = worldIn.getBlockState(down);
                boolean isSoil = state.getBlock() == ModBlocks.AZURE_GRASS;

                // Make sure there is soil
                if (isSoil && position.getY() < 256 - relativeHeight - 1)
                {
                	state.getBlock().onPlantGrow(state, worldIn, down, position);
                	
                	// Generate the first tree
                	generateTreeSegment(relativeHeight, leafHeight, maxLeafWidth, worldIn, rand, position, state);
                	
            		// Increase the position to align with the next segment of the tree
                	BlockPos newPos = new BlockPos(position.getX(), position.getY() + relativeHeight - 1, position.getZ());
                	
                	// Generate the additional trees on top of the first tree
                	for(int i = 0; i < additionalTrees; i++)
                	{
                		// Sometimes make the leaves at the top shorter
                		if(rand.nextInt(maxLeafDecayChance) == 0)
                		{
                    		maxLeafWidth = maxLeafWidth > 1 ? maxLeafWidth - 1 : maxLeafWidth;                			
                		}
                		
                		generateTreeSegment(additionalHeight, leafHeight, maxLeafWidth, worldIn, rand, newPos, state);
                		
                		// Increase the position to align with the next segment of the tree
                		newPos = new BlockPos(position.getX(), newPos.getY() + additionalHeight - 1, position.getZ());
                	}
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * 
     * @param relativeHeight The height of the tree segment to generate
     * @param leafHeight The height of the leaves to generate (e.g. a leaf height of 3 will generate leaves from relative height down to relative height - 3)
     * @param maxLeafWidth
     * @param worldIn
     * @param rand
     * @param position
     * @param state
     */
    private void generateTreeSegment(int relativeHeight, int leafHeight, int maxLeafWidth, World worldIn, Random rand, BlockPos position, IBlockState state) 
    {
        int leafWidth = 0;

        // Generate the leaves
        for (int y = position.getY() + relativeHeight; y >= position.getY() + relativeHeight - leafHeight; --y)
        {
            for (int x = position.getX() - leafWidth; x <= position.getX() + leafWidth; ++x)
            {
                int widthX = x - position.getX();

                for (int z = position.getZ() - leafWidth; z <= position.getZ() + leafWidth; ++z)
                {
                    int widthZ = z - position.getZ();

                    if (Math.abs(widthX) != leafWidth || Math.abs(widthZ) != leafWidth || leafWidth <= 0)
                    {
                        BlockPos blockpos = new BlockPos(x, y, z);
                        state = worldIn.getBlockState(blockpos);

                        if (state.getBlock().canBeReplacedByLeaves(state, worldIn, blockpos))
                        {
                            this.setBlockAndNotifyAdequately(worldIn, blockpos, AZURE_LEAVES);
                        }
                    }
                }
            }

            // Change the bushiness of the layer
            if (leafWidth >= 1 && y == position.getY() + leafHeight + 1)
            {
                --leafWidth;
            }
            else if (leafWidth < maxLeafWidth)
            {
                ++leafWidth;
            }
        }

        // Generate the trunk
        for (int y = 0; y < relativeHeight - 1; ++y)
        {
            BlockPos upN = position.up(y);
            state = worldIn.getBlockState(upN);

            if (state.getBlock().isAir(state, worldIn, upN) || state.getBlock().isLeaves(state, worldIn, upN))
            {
                this.setBlockAndNotifyAdequately(worldIn, position.up(y), AZURE_LOG);
            }
        }
    }
}
