package com.barribob.MaelstromMod.blocks.key_blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.blocks.BlockBase;
import com.barribob.MaelstromMod.util.ModUtils;
import com.google.common.base.Predicate;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockKey extends BlockBase
{
    private Item activationItem;

    public BlockKey(String name, Item item)
    {
	super(name, Material.ROCK, 1000, 10000, SoundType.STONE);
	this.setBlockUnbreakable();
	this.activationItem = item;
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
	List<EntityPlayerSP> list = worldIn.<EntityPlayerSP>getPlayers(EntityPlayerSP.class, new Predicate<EntityPlayerSP>()
	{
	    @Override
	    public boolean apply(@Nullable EntityPlayerSP player)
	    {
		return player.getHeldItem(EnumHand.MAIN_HAND).getItem() == activationItem;
	    }
	});

	if (list.size() > 0)
	{
	    ModUtils.performNTimes(50, (i) -> {
		worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5, pos.getY() + 1 + i, pos.getZ() + 0.5, 0, -0.1, 0);
	    });
	}
	super.randomDisplayTick(stateIn, worldIn, pos, rand);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY,
	    float hitZ)
    {
	if (playerIn.getHeldItemMainhand() != null && playerIn.getHeldItemMainhand().getItem() == this.activationItem)
	{
	    this.spawnPortalEntity(worldIn, pos);
	    worldIn.setBlockToAir(pos);
	}
	return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    protected abstract void spawnPortalEntity(World world, BlockPos pos);
}
