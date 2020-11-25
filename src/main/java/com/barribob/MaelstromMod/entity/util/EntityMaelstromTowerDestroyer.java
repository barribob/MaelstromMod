package com.barribob.MaelstromMod.entity.util;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.world.gen.WorldGenCustomStructures;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EntityMaelstromTowerDestroyer extends Entity {
    List<Vec3d> blocksToDestroy = new ArrayList<>();

    public EntityMaelstromTowerDestroyer(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
    }

    public EntityMaelstromTowerDestroyer(World worldIn, Vec3d pos) {
        this(worldIn);
        ModUtils.setEntityPosition(this, pos);
        BlockPos towerSize = WorldGenCustomStructures.invasionTower.getSize(world);
        BlockPos max = towerSize.add(new BlockPos(pos));
        BlockPos min = new BlockPos(pos);
        blocksToDestroy = ModUtils.cubePoints(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ()).stream()
                .filter(vec3d -> worldIn.getBlockState(new BlockPos(vec3d)).getBlock() == Blocks.OBSIDIAN ||
                        worldIn.getBlockState(new BlockPos(vec3d)).getBlock() == ModBlocks.AZURE_MAELSTROM ||
                        worldIn.getBlockState(new BlockPos(vec3d)).getBlock() == ModBlocks.MAELSTROM_BRICKS ||
                        worldIn.getBlockState(new BlockPos(vec3d)).getBlock() == ModBlocks.MAELSTROM_BRICK_STAIRS ||
                        worldIn.getBlockState(new BlockPos(vec3d)).getBlock() == ModBlocks.MAELSTROM_STONEBRICK ||
                        worldIn.getBlockState(new BlockPos(vec3d)).getBlock() == ModBlocks.MAELSTROM_STONEBRICK_STAIRS ||
                        worldIn.getBlockState(new BlockPos(vec3d)).getBlock() == ModBlocks.MAELSTROM_STONEBRICK_FENCE ||
                        worldIn.getBlockState(new BlockPos(vec3d)).getBlock() == Blocks.NETHERRACK ||
                        worldIn.getBlockState(new BlockPos(vec3d)).getBlock() == ModBlocks.CRIMSON_MAELSTROM_BRICKS ||
                        worldIn.getBlockState(new BlockPos(vec3d)).getBlock() == ModBlocks.BOSS_SPAWNER)
                .collect(Collectors.toList());
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (world.isRemote) {
            return;
        }

        int towerDisintegrationSpeed = Math.max(1, Main.invasionsConfig.getInt("tower_disintegration_speed"));
        for (int i = 0; i < towerDisintegrationSpeed; i++) {
            if (this.blocksToDestroy.size() == 0) {
                this.setDead();
                return;
            }

            this.world.setEntityState(this, ModUtils.PARTICLE_BYTE);

            int rand = this.rand.nextInt(blocksToDestroy.size());
            BlockPos pos = new BlockPos(blocksToDestroy.get(rand));
            if (world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 10, false) != null) {
                world.destroyBlock(pos, false);
            } else {
                world.setBlockToAir(pos);
            }
            blocksToDestroy.remove(rand);
        }
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
    }
}
