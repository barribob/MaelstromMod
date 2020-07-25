package com.barribob.MaelstromMod.test;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModUtils;
import com.typesafe.config.Config;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;

public class InGameTests {
    public static void runAllTests(MinecraftServer server, ICommandSender sender) throws Exception {
        spawnAlgorithm(server.getEntityWorld(), sender.getPosition());
    }

    public static void runSingleTest(MinecraftServer server, ICommandSender sender, String testName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InGameTests.class.getMethod(testName, World.class, BlockPos.class).invoke(null, server.getEntityWorld(), sender.getPosition());
    }

    public static void spawnAlgorithm(World world, BlockPos pos) throws Exception {
        Config config = TestUtils.testingConfig().getConfig("spawning_algorithm");
        EntityLeveledMob entity = ModUtils.spawnMob(world, pos, 0, config);
        assert entity != null;
        NBTTagCompound compound = new NBTTagCompound();
        entity.writeToNBT(compound);

        TestUtils.AssertTrue(entity.getDisplayName().getFormattedText().contains("Maelstrom Scout"), "Mob display names do not match");
        TestUtils.AssertEquals(Element.AZURE, entity.getElement());
        TestUtils.AssertEquals(1000, compound.getInteger("experienceValue"));
        TestUtils.AssertEquals(1f, entity.getHealth());
        TestUtils.AssertEquals(1f, entity.getMaxHealth());
        TestUtils.AssertEquals(12.0, entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue());
        TestUtils.AssertEquals(64.0, entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue());
        TestUtils.AssertEquals(0.4, entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getBaseValue());
        TestUtils.AssertEquals(0.27, entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue());
    }
}
