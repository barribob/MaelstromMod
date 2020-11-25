package com.barribob.MaelstromMod.blocks;

import com.barribob.MaelstromMod.init.ModItems;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPlumFilledLeaves extends BlockLeavesBase {
    public BlockPlumFilledLeaves(String name, float hardness, float resistance, SoundType soundType) {
        super(name, hardness, resistance, soundType);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.PLUM;
    }

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : new Random();

        ItemStack drop = new ItemStack(getItemDropped(state, rand, fortune), this.quantityDropped(rand), damageDropped(state));

        drops.add(drop);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1 + random.nextInt(3);
    }
}
