package com.barribob.MaelstromMod.blocks;

import com.barribob.MaelstromMod.entity.tileentity.TileEntityDisappearingSpawner;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockNexusHerobrineSpawner extends BlockDisappearingSpawner
{
    public BlockNexusHerobrineSpawner(String name)
    {
	super(name, Material.ROCK);
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
	return new TileEntityDisappearingSpawner();
    }
}
