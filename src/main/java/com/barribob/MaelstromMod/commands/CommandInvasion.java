package com.barribob.MaelstromMod.commands;

import com.barribob.MaelstromMod.invasion.InvasionUtils;
import com.barribob.MaelstromMod.invasion.MultiInvasionWorldSavedData;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.Optional;

public class CommandInvasion extends CommandBase {
    @Override
    public String getName() {
        return "invade";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "invade";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = getCommandSenderAsPlayer(sender);

        if (sender.getCommandSenderEntity() != null && sender.getCommandSenderEntity().dimension != 0) {
            player.sendMessage(new TextComponentString("You can only spawn an invasion in the overworld"));
            return;
        }

        MultiInvasionWorldSavedData data = InvasionUtils.getInvasionData(server.getWorld(0));
        Optional<BlockPos> invasionPos = InvasionUtils.trySpawnInvasionTower(player.getPosition(), player.world, data.getSpawnedInvasionPositions());
        if (invasionPos.isPresent()) {
            data.addSpawnedInvasionPosition(invasionPos.get());
            player.sendMessage(new TextComponentString(
                    "" + TextFormatting.DARK_PURPLE + new TextComponentTranslation(Reference.MOD_ID + ".invasion_2").getFormattedText()));
        } else {
            player.sendMessage(new TextComponentString("Failed to generate invasion tower (maybe it's not a great place)"));
        }
    }
}
