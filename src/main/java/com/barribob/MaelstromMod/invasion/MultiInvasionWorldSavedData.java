package com.barribob.MaelstromMod.invasion;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.Reference;
import com.typesafe.config.Config;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import java.util.List;

public class MultiInvasionWorldSavedData extends WorldSavedData {
    public static final String DATA_NAME = Reference.MOD_ID + "_MultiInvasionData";
    private int ticks;
    private int invasionIndex;
    public static final int INVASION_RESET_TIME = ModUtils.secondsToTicks(10);

    @SuppressWarnings("unused")
    public MultiInvasionWorldSavedData(String s) {
        super(s);
    }

    public MultiInvasionWorldSavedData() {
        super(DATA_NAME);
    }

    public Config getCurrentInvasion() {
        List<? extends Config> invasions = Main.invasionsConfig.getConfigList("invasions");

        if(invasions.size() > invasionIndex) {
            return invasions.get(invasionIndex);
        }

        return null;
    }

    public void tick(World world) {
        Config invasion = getCurrentInvasion();

        if(invasion == null) {
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

                if (InvasionUtils.trySpawnInvasionTower(player.getPosition(), player.world)) {
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
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("ticks", ticks);
        compound.setInteger("integerIndex", invasionIndex);
        return compound;
    }
}
