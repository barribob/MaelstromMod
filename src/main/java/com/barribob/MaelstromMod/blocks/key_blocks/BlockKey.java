package com.barribob.MaelstromMod.blocks.key_blocks;

import com.barribob.MaelstromMod.blocks.BlockBase;
import com.barribob.MaelstromMod.entity.util.EntityAzurePortalSpawn;
import com.barribob.MaelstromMod.entity.util.EntityPortalSpawn;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class BlockKey extends BlockBase
{
    private Item activationItem;
    public BlockKey(String name, Item item)
    {
	super(name, Material.ROCK, 1000, 1000, SoundType.STONE);
	this.setBlockUnbreakable();
	this.activationItem = item;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY,
	    float hitZ)
    {
	if(playerIn.getHeldItemMainhand() != null && playerIn.getHeldItemMainhand().getItem() == this.activationItem)
	{    
	    worldIn.spawnEntity(this.getPortalSpawn(worldIn, pos));
	    worldIn.setBlockToAir(pos);
	}
	return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
    
    protected abstract EntityPortalSpawn getPortalSpawn(World world, BlockPos pos);
}
