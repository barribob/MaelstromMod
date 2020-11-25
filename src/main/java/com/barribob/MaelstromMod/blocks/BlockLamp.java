package com.barribob.MaelstromMod.blocks;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.ModRandom;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockLamp extends BlockBase {
    private boolean doManualLightUpdates;

    public BlockLamp(String name, Material material, float hardness, float resistance, SoundType soundType) {
        this(name, material, hardness, resistance, soundType, false);
    }

    public BlockLamp(String name, Material material, float hardness, float resistance, SoundType soundType, Boolean doManualLightUpdates) {
        super(name, material, hardness, resistance, soundType);
        this.doManualLightUpdates = doManualLightUpdates;
    }

    // Make not opaque to remove common lighting errors
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        if (doManualLightUpdates) {
            worldIn.scheduleBlockUpdate(pos, this, 100 + ModRandom.range(0, 100), 0); // All blocks in initial range get updated
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

        if (doManualLightUpdates) {
            BlockPos[] positions = new BlockPos[]{pos.down(), pos.up(), pos.west(), pos.east(), pos.north(), pos.south()};
            boolean wasAir = false;

            for (BlockPos nPos : positions) {
                // Remove all surrounding lighting updater blocks after the seconds pass
                if (worldIn.getBlockState(nPos).getBlock().equals(ModBlocks.LIGHTING_UPDATER)) {
                    worldIn.setBlockToAir(nPos);
                } else if (worldIn.isAirBlock(nPos)) // Set all surrounding air blocks to lighting updater blocks
                {
                    wasAir = true;
                    worldIn.setBlockState(nPos, ModBlocks.LIGHTING_UPDATER.getDefaultState());
                }
            }

            if (wasAir) {
                worldIn.scheduleBlockUpdate(pos, this, 1, 0); // Next update will remove the set lighting updater blocks
            }
        }
    }
}
