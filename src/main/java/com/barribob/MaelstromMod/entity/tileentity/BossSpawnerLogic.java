package com.barribob.MaelstromMod.entity.tileentity;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class BossSpawnerLogic extends DisappearingSpawnerLogic {
    public BossSpawnerLogic(Supplier<World> world, Supplier<BlockPos> pos, Block block) {
        super(world, pos, block);
    }

    @Override
    public void updateSpawner() {
        if (this.world.get().isRemote || !this.isActivated()) {
            return;
        }

        if (this.spawnDelay > 0) {
            --this.spawnDelay;
            return;
        }

        while (this.count < this.maxCount) {
            MobSpawnData data = getEntityData();
            Entity entity = ModUtils.createMobFromSpawnData(data, world.get(), pos.get().getX() + 0.5, pos.get().getY(), pos.get().getZ() + 0.5);

            if (entity != null && entity instanceof EntityLeveledMob) {
                world.get().spawnEntity(entity);
                EntityLeveledMob leveledMob = (EntityLeveledMob) entity;
                leveledMob.setElement(ModRandom.choice(data.possibleElements, this.world.get().rand, data.elementalWeights).next());
                leveledMob.setLevel(level);
            }
            this.count += data.count;
        }

        this.onSpawn(world.get(), pos.get());
    }
}