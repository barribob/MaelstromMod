package com.barribob.MaelstromMod.invasion;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.Reference;
import com.typesafe.config.Config;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MultiInvasionWorldSavedData extends WorldSavedData {
    public static final String DATA_NAME = Reference.MOD_ID + "_MultiInvasionData";
    private int ticks;
    private int invasionIndex;
    public static final int INVASION_RESET_TIME = ModUtils.secondsToTicks(10);
    private final Set<BlockPos> spawnedInvasionPositions = new HashSet<>();

    @SuppressWarnings("unused")
    public MultiInvasionWorldSavedData(String s) {
        super(s);
    }

    public MultiInvasionWorldSavedData() {
        super(DATA_NAME);
    }

    public void addSpawnedInvasionPosition(BlockPos pos) {
        if (spawnedInvasionPositions.add(pos)) {
            this.markDirty();
        }
    }

    public Set<BlockPos> getSpawnedInvasionPositions() {
        return spawnedInvasionPositions;
    }

    public Config getCurrentInvasion() {
        List<? extends Config> invasions = Main.invasionsConfig.getConfigList("invasions");

        if (invasions.size() > invasionIndex) {
            return invasions.get(invasionIndex);
        }

        return null;
    }

    public void tick(World world) {
        Config invasion = getCurrentInvasion();

        if (invasion == null) {
            return;
        }

        this.markDirty();

        int invasionTime = ModUtils.minutesToTicks(invasion.getInt("invasion_time"));
        int warningTime = ModUtils.minutesToTicks(invasion.getInt("warning_time"));

        if (ticks == warningTime) {
            InvasionUtils.sendInvasionMessage(world, Reference.MOD_ID + ".invasion_1");
        }

        if (ticks == invasionTime) {
            if (world.playerEntities.size() > 0) {
                EntityPlayer player = InvasionUtils.getPlayerClosestToOrigin(world);

                Optional<BlockPos> spawnedPos = InvasionUtils.trySpawnInvasionTower(player.getPosition(), player.world, spawnedInvasionPositions);

                if (spawnedPos.isPresent()) {
                    spawnedInvasionPositions.add(spawnedPos.get());
                    InvasionUtils.sendInvasionMessage(world, Reference.MOD_ID + ".invasion_2");
                    invasionIndex++;
                    ticks = 0;
                    return;
                }
            }

            ticks = Math.max(0, ticks - INVASION_RESET_TIME);
        }

        ticks++;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.ticks = nbt.getInteger("ticks");
        this.invasionIndex = nbt.getInteger("integerIndex");

        spawnedInvasionPositions.clear();
        NBTTagList nbtList = nbt.getTagList("spawnedInvasionPositions", new NBTTagCompound().getId());
        for (NBTBase nbtBase : nbtList) {
            NBTTagCompound posNbt = (NBTTagCompound) nbtBase;
            int[] pos = posNbt.getIntArray("pos");
            spawnedInvasionPositions.add(new BlockPos(pos[0], pos[1], pos[2]));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("ticks", ticks);
        compound.setInteger("integerIndex", invasionIndex);

        NBTTagList nbtList = new NBTTagList();
        for (BlockPos pos : spawnedInvasionPositions) {
            NBTTagCompound posCompound = new NBTTagCompound();
            posCompound.setIntArray("pos", new int[]{pos.getX(), pos.getY(), pos.getZ()});
            nbtList.appendTag(posCompound);
        }

        compound.setTag("spawnedInvasionPositions", nbtList);

        return compound;
    }
}
