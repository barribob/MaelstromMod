package com.barribob.MaelstromMod.commands;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.test.InGameTests;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.util.List;

public class CommandRunUnitTests extends CommandBase {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "test <testName>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList("unitTest", "tests", "unitTests");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        try {
            if (args.length == 0) {
                InGameTests.runAllTests(server, sender);
            } else {
                InGameTests.runSingleTest(server, sender, args[0]);
            }
        } catch (Exception e) {
            Main.log.error("Test case failed: ");
            e.printStackTrace();
            throw new CommandException("mm.unit_test_failed");
        }
    }
}