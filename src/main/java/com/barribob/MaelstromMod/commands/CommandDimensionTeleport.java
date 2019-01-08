package com.barribob.MaelstromMod.commands;

import java.util.List;

import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.Teleport;
import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;

public class CommandDimensionTeleport extends CommandBase
{
    private final List<String> aliases = Lists.newArrayList(Reference.MOD_ID, "tpdim", "teleport", "dimension");

    @Override
    public String getName()
    {
	return "tpdimension";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
	return "tpdimension <dimension id>";
    }

    @Override
    public List<String> getAliases()
    {
	return aliases;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
	return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
	if (args.length < 1)
	    return;

	String s = args[0];

	int dimensionId;

	try
	{
	    dimensionId = Integer.parseInt(s);
	}
	catch (NumberFormatException e)
	{
	    sender.sendMessage(new TextComponentString(TextFormatting.RED + "Dimension Id Invalid"));
	    return;
	}

	try
	{
	    if (sender instanceof EntityPlayerMP && ((EntityPlayer) sender).dimension != dimensionId)
	    {
		WorldServer worldServer = server.getWorld(dimensionId);
		
		if (worldServer == null || server == null)
		{
		    throw new IllegalArgumentException("Dimension: " + dimensionId + " doesn't exist");
		}
		
		Teleport.teleportToDimension((EntityPlayerMP) sender, dimensionId, new Teleport(worldServer, sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ()));
	    }
	}
	catch (IllegalArgumentException e)
	{
	    sender.sendMessage(new TextComponentString(TextFormatting.RED + e.getMessage()));
	    return;
	}

    }
}
