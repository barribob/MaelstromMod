package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.mana.ManaProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMana implements IMessage
{
    public MessageMana()
    {
    }

    public MessageMana(float mana)
    {
	super();
	this.mana = mana;
    }

    private float mana;

    @Override
    public void fromBytes(ByteBuf buf)
    {
	mana = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
	buf.writeFloat(mana);
    }

    public static class MessageHandler implements IMessageHandler<MessageMana, IMessage>
    {
	@Override
	public IMessage onMessage(MessageMana message, MessageContext ctx)
	{
	    if (Minecraft.getMinecraft().player != null)
	    {
		Minecraft.getMinecraft().player.getCapability(ManaProvider.MANA, null).set(message.mana);
	    }
	    return null;
	}
    }
}
