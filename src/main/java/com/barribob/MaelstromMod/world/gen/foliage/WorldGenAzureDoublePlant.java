package com.barribob.MaelstromMod.world.gen.foliage;

import com.barribob.MaelstromMod.blocks.BlockDoubleBrownedGrass;
import com.barribob.MaelstromMod.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenAzureDoublePlant extends WorldGenerator {
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        boolean flag = false;
        for (IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getBlock().isAir(iblockstate, worldIn, position) || iblockstate.getBlock().isLeaves(iblockstate, worldIn, position)) && position.getY() > 0; iblockstate = worldIn.getBlockState(position)) {
            position = position.down();
        }

        for (int i = 0; i < 64; ++i) {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.isNether() || blockpos.getY() < 254) && ModBlocks.DOUBLE_BROWNED_GRASS.canPlaceBlockAt(worldIn, blockpos)) {
                ((BlockDoubleBrownedGrass) ModBlocks.DOUBLE_BROWNED_GRASS).placeAt(worldIn, blockpos, 2);
                flag = true;
            }
        }

        return flag;
    }
}