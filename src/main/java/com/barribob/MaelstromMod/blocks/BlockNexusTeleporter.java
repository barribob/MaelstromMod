package com.barribob.MaelstromMod.blocks;

import java.util.Random;

import com.barribob.MaelstromMod.entity.tileentity.TileEntityTeleporter;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNexusTeleporter extends BlockBase implements ITileEntityProvider
{
    public BlockNexusTeleporter(String name, Material material, SoundType soundType)
    {
	super(name, material, 1000, 1000, soundType);
	this.setBlockUnbreakable();
	this.hasTileEntity = true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
	return new TileEntityTeleporter();
    }
    
    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }
}
