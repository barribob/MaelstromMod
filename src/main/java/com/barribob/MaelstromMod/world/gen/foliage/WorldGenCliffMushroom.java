package com.barribob.MaelstromMod.world.gen.foliage;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Arrays;
import java.util.Random;

/*
 * Generates mushroom on the side of the cliff of the cliff dimension
 */
public class WorldGenCliffMushroom extends WorldGenerator {
    private Block cliffBlock;

    public WorldGenCliffMushroom(Block cliffBlock) {
        super(false);
        this.cliffBlock = cliffBlock;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {

        Block block = rand.nextInt(4) == 0 ? Blocks.SLIME_BLOCK : Blocks.BROWN_MUSHROOM_BLOCK;

        // Move the generation until it is at the correct y position
        while (worldIn.getBlockState(position).getBlock() != Blocks.AIR) {
            position = position.up();
        }

        if (position.getY() > 240) {
            return false;
        }

        if (position.getY() > 200 && rand.nextInt(2) == 0) {
            return false;
        }

        if (!worldIn.isAirBlock(position.up(40))) {
            return false;
        }

        if (worldIn.getBlockState(position).getBlock() == Blocks.AIR && this.isBlockNearby(worldIn, position)) {
            int k2 = position.getY();

            for (int l2 = k2; l2 <= position.getY(); ++l2) {
                int j3 = 1;

                if (l2 < position.getY()) {
                    ++j3;
                }

                if (block == Blocks.BROWN_MUSHROOM_BLOCK || block.equals(Blocks.SLIME_BLOCK)) {
                    j3 = 3;
                }

                int k3 = position.getX() - j3;
                int l3 = position.getX() + j3;
                int j1 = position.getZ() - j3;
                int k1 = position.getZ() + j3;

                for (int l1 = k3; l1 <= l3; ++l1) {
                    for (int i2 = j1; i2 <= k1; ++i2) {
                        int j2 = 5;

                        if (l1 == k3) {
                            --j2;
                        } else if (l1 == l3) {
                            ++j2;
                        }

                        if (i2 == j1) {
                            j2 -= 3;
                        } else if (i2 == k1) {
                            j2 += 3;
                        }

                        BlockHugeMushroom.EnumType blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.byMetadata(j2);

                        if (block == Blocks.BROWN_MUSHROOM_BLOCK || block.equals(Blocks.SLIME_BLOCK) || l2 < position.getY()) {
                            if ((l1 == k3 || l1 == l3) && (i2 == j1 || i2 == k1)) {
                                continue;
                            }

                            if (l1 == position.getX() - (j3 - 1) && i2 == j1) {
                                blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_WEST;
                            }

                            if (l1 == k3 && i2 == position.getZ() - (j3 - 1)) {
                                blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_WEST;
                            }

                            if (l1 == position.getX() + (j3 - 1) && i2 == j1) {
                                blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_EAST;
                            }

                            if (l1 == l3 && i2 == position.getZ() - (j3 - 1)) {
                                blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_EAST;
                            }

                            if (l1 == position.getX() - (j3 - 1) && i2 == k1) {
                                blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_WEST;
                            }

                            if (l1 == k3 && i2 == position.getZ() + (j3 - 1)) {
                                blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_WEST;
                            }

                            if (l1 == position.getX() + (j3 - 1) && i2 == k1) {
                                blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_EAST;
                            }

                            if (l1 == l3 && i2 == position.getZ() + (j3 - 1)) {
                                blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_EAST;
                            }
                        }

                        if (blockhugemushroom$enumtype == BlockHugeMushroom.EnumType.CENTER && l2 < position.getY()) {
                            blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.ALL_INSIDE;
                        }

                        if (position.getY() >= position.getY() - 1 || blockhugemushroom$enumtype != BlockHugeMushroom.EnumType.ALL_INSIDE) {
                            BlockPos blockpos = new BlockPos(l1, l2, i2);
                            IBlockState state = worldIn.getBlockState(blockpos);

                            if (state.getBlock().canBeReplacedByLeaves(state, worldIn, blockpos)) {
                                state = block.equals(Blocks.SLIME_BLOCK) ? block.getDefaultState()
                                        : block.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, blockhugemushroom$enumtype);
                                this.setBlockAndNotifyAdequately(worldIn, blockpos, state);
                            }
                        }
                    }
                }
            }

            return true;
        }
        return false;
    }

    private boolean isBlockNearby(World world, BlockPos pos) {
        for (BlockPos dir : Arrays.asList(pos.down(), pos.east(), pos.west(), pos.north(), pos.south())) {
            if (world.getBlockState(dir).getBlock() == cliffBlock) {
                return true;
            }
        }
        return false;
    }
}