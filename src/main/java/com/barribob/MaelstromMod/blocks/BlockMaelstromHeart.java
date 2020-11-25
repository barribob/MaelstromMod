package com.barribob.MaelstromMod.blocks;

import com.barribob.MaelstromMod.entity.util.EntityMaelstromTowerDestroyer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMaelstromHeart extends BlockBase {
    public BlockMaelstromHeart(String name, Material material, float hardness, float resistance, SoundType soundType) {
        super(name, material, hardness, resistance, soundType);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        EntityMaelstromTowerDestroyer entity = new EntityMaelstromTowerDestroyer(worldIn, new Vec3d(pos).subtract(new Vec3d(21, 48, 13)));
        worldIn.spawnEntity(entity);
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }
}
