package com.barribob.MaelstromMod.blocks;

import com.barribob.MaelstromMod.util.ModRandom;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockChain extends BlockBase {
    private AxisAlignedBB PILLAR_AABB;

    public BlockChain(String name, Material material, float hardness, float resistance, SoundType soundType, AxisAlignedBB collision) {
        super(name, material, hardness, resistance, soundType);
        PILLAR_AABB = collision;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        // Some hacky checks to make climbing sounds
        if (entityIn instanceof EntityLivingBase && ForgeHooks.isLivingOnLadder(state, worldIn, pos, (EntityLivingBase) entityIn) && !entityIn.onGround && Math.abs(entityIn.motionY) > 0.1 && entityIn.ticksExisted % 15 == 0) {
            entityIn.playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.4f, 1.0f + ModRandom.getFloat(0.2f));
        }
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return PILLAR_AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return PILLAR_AABB.grow(-0.15, 0, -0.15);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
        return true;
    }
}
