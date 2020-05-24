package com.barribob.MaelstromMod.packets;

import io.netty.buffer.ByteBuf;
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
		PacketUtils.playDarkNexusWindSound();
	    }
	    return null;
	}
    }
}
