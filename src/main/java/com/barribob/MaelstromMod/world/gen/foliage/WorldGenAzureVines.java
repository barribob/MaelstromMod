package com.barribob.MaelstromMod.world.gen.foliage;

import com.barribob.MaelstromMod.init.ModBlocks;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Generates azure vines in a row until the conditions are broken, then generates a new row.
 */
public class WorldGenAzureVines extends WorldGenerator {
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        int maxY = position.getY() + rand.nextInt(25) + 5;
        BlockPos newPos = position;
        for (; newPos.getY() < maxY; newPos = newPos.up()) {
            if (worldIn.isAirBlock(newPos)) {
                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.facings()) {
                    if (ModBlocks.AZURE_VINES.canPlaceBlockOnSide(worldIn, newPos, enumfacing)) {
                        IBlockState iblockstate = ModBlocks.AZURE_VINES.getDefaultState().withProperty(BlockVine.SOUTH, Boolean.valueOf(enumfacing == EnumFacing.NORTH)).withProperty(BlockVine.WEST, Boolean.valueOf(enumfacing == EnumFacing.EAST)).withProperty(BlockVine.NORTH, Boolean.valueOf(enumfacing == EnumFacing.SOUTH)).withProperty(BlockVine.EAST, Boolean.valueOf(enumfacing == EnumFacing.WEST));
                        worldIn.setBlockState(newPos, iblockstate, 2);
                        break;
                    }
                }
            } else {
                // Find a new position to place the vine row
                newPos = new BlockPos(rand.nextInt(4) - rand.nextInt(4) + position.getX(), newPos.getY(), rand.nextInt(4) - rand.nextInt(4) + position.getZ());
            }
        }

        return true;
    }
}