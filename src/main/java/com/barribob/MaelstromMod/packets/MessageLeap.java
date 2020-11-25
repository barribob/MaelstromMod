package com.barribob.MaelstromMod.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageLeap implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class MessageHandler implements IMessageHandler<MessageLeap, IMessage> {
        @Override
        public IMessage onMessage(MessageLeap message, MessageContext ctx) {
            if (PacketUtils.getPlayer() != null) {
                EntityPlayer player = PacketUtils.getPlayer();
                float maxVelocityIncrease = 0.6f;
                player.addVelocity(Math.min(Math.max(player.motionX, -maxVelocityIncrease), maxVelocityIncrease), 0.8f,
                        Math.min(Math.max(player.motionZ, -maxVelocityIncrease), maxVelocityIncrease));
                player.motionY = Math.min(1.0f, player.motionY);
            }
            return null;
        }
    }
}
