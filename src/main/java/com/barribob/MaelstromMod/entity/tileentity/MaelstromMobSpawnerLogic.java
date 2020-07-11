package com.barribob.MaelstromMod.entity.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

/**
 * The tile entity spawner logic for the maelstrom core. Has similar
 * functionality to the vanilla minecraft mob spawner
 */
public class MaelstromMobSpawnerLogic extends MobSpawnerLogic {
    private int minSpawnDelay = 600;
    private int maxSpawnDelay = 800;

    public MaelstromMobSpawnerLogic(Supplier<World> world, Supplier<BlockPos> pos, Block block) {
        super(world, pos, block);
    }

    /**
     * Returns true if there's a player close enough to this mob spawner to activate
     * it.
     */
    private boolean isActivated() {
        BlockPos blockpos = this.pos.get();
        return this.world.get().isAnyPlayerWithinRangeAt(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D,
                this.activatingRangeFromPlayer);
    }

    @Override
    public void updateSpawner() {
        // Currently does not deal with any server stuff, although this might be a
        // mistake, so potentially this may have to revert back to the vanilla logic
        if (this.world.get().isRemote || !this.isActivated()) {
            return;
        }

        if (this.spawnDelay == -1) {
            this.resetTimer();
        }

        if (this.spawnDelay > 0) {
            --this.spawnDelay;
            return;
        }

        while (this.count < this.maxCount) {
            // Try multiple times to spawn the entity in a good spot
            int tries = 20;
            for (int t = 0; t < tries; t++) {
                if (this.tryToSpawnEntity()) {
                    break;
                } else if (t == tries - 1) {
                    this.count++;
                }
            }
        }

        this.resetTimer();
    }

    /**
     * Resets the timer back to a random number between max and min spawn delay
     */
    private void resetTimer() {
        if (this.maxSpawnDelay <= this.minSpawnDelay) {
            this.spawnDelay = this.minSpawnDelay;
        } else {
            int i = this.maxSpawnDelay - this.minSpawnDelay;
            this.spawnDelay = this.minSpawnDelay + this.world.get().rand.nextInt(i);
        }

        this.count = 0;

        this.broadcastEvent(1);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("MinSpawnDelay", 99)) {
            this.minSpawnDelay = nbt.getShort("MinSpawnDelay");
            this.maxSpawnDelay = nbt.getShort("MaxSpawnDelay");
        }
        super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (this.getEntityData() != null) {
            compound.setShort("MinSpawnDelay", (short) this.minSpawnDelay);
            compound.setShort("MaxSpawnDelay", (short) this.maxSpawnDelay);
        }
        return super.writeToNBT(compound);
    }
}