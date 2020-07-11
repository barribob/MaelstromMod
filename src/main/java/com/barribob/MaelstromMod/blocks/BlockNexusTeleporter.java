package com.barribob.MaelstromMod.blocks;

import com.barribob.MaelstromMod.entity.tileentity.TileEntityTeleporter;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.ModRandom;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNexusTeleporter extends BlockLamp implements ITileEntityProvider {
    public BlockNexusTeleporter(String name, Material material, SoundType soundType) {
        super(name, material, 50, 2000, soundType);
        this.hasTileEntity = true;
        this.setTickRandomly(true);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTeleporter();
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updatedmod
     */
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        worldIn.scheduleBlockUpdate(pos, this, 100 + ModRandom.range(0, 100), 0); // All blocks in initial range get updated
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

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
