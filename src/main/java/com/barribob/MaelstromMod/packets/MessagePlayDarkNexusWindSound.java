package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.sounds.DarkNexusWindSound;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePlayDarkNexusWindSound implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<MessagePlayDarkNexusWindSound, IMessage> {
	@Override
	public IMessage onMessage(MessagePlayDarkNexusWindSound message, MessageContext ctx) {
	    if (PacketUtils.getPlayer() != null) {
		EntityPlayer player = PacketUtils.getPlayer();
		Minecraft.getMinecraft().getSoundHandler().playSound(new DarkNexusWindSound((EntityPlayerSP) player));
	    }
	    return null;
	}
    }
}
