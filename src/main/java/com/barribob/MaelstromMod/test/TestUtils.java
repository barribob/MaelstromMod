package com.barribob.MaelstromMod.test;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModUtils;
import com.typesafe.config.Config;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;

public class TestUtils {
    public static final String getMobString(Entity entity) {
        String mobString = "Name: " + entity.getDisplayName();
        if(entity instanceof EntityLivingBase) {
            NBTTagCompound compound = new NBTTagCompound();
            ((EntityLivingBase)entity).writeEntityToNBT(compound);
            mobString += "\nNBT: " + compound;
        }

        return mobString;
    }

    public static Config testingConfig() {
        return Main.CONFIG_MANAGER.loadDefaultConfig("testing");
    }

    public static void AssertEquals(Object expected, Object actual) throws Exception {
        if (expected == null && actual == null) {
            return;
        }
        if (expected == null || !expected.equals(actual)) {
            throw new Exception("Expected " + expected + " Got " + actual);
        }
    }

    public static void AssertAlmostEquals(Double expected, Double actual, double precision) throws Exception {
        if (Math.abs(expected - actual) < 1.5 * Math.pow(10, -precision)) {
            return;
        }
        throw new Exception("Expected " + expected + " to be almost equal to " + actual);
    }

    public static void AssertTrue(boolean value, String message) throws Exception {
        if(!value) {
            throw new Exception(message);
        }
    }
}
