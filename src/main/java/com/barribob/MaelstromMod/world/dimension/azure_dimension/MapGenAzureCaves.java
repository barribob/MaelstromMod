package com.barribob.MaelstromMod.world.dimension.azure_dimension;

import com.barribob.MaelstromMod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.MapGenCaves;

public class MapGenAzureCaves extends MapGenCaves {
    @Override
    protected boolean canReplaceBlock(IBlockState state, IBlockState above) {
        if (above.getMaterial().equals(Material.WATER)) {
            return false;
        }
        Block block = state.getBlock();
        if (block.equals(ModBlocks.DARK_AZURE_STONE) || block.equals(ModBlocks.DARK_AZURE_STONE_1) || block.equals(ModBlocks.DARK_AZURE_STONE_2)
                || block.equals(ModBlocks.DARK_AZURE_STONE_3) || block.equals(ModBlocks.DARK_AZURE_STONE_4) || block.equals(ModBlocks.DARK_AZURE_STONE_5)
                || block.equals(Blocks.PRISMARINE)) {
            return true;
        }

        return super.canReplaceBlock(state, above);
    }
}
