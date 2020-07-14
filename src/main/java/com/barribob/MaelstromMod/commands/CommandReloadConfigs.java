package com.barribob.MaelstromMod.commands;

import com.barribob.MaelstromMod.Main;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

public class CommandReloadConfigs extends CommandBase {
    @Override
    public String getName() { return "reloadConfig"; }

    @Override
    public String getUsage(ICommandSender sender) { return "reloadConfig"; }

    @Override
    public int getRequiredPermissionLevel() { return 2; }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList("reloadConf", "reloadConfigs");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        try {
            Main.loadConfigs();
            getCommandSenderAsPlayer(sender).sendMessage(new TextComponentTranslation("mm.config_load_success"));
        } catch (Exception e) {
            Main.log.error(e);
            String message = "" + new TextComponentTranslation("mm.config_load_failed").getFormattedText();
            throw new CommandException(message);
        }
    }
}
