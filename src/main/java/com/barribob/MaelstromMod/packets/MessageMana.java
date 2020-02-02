package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.gui.InGameGui;
import com.barribob.MaelstromMod.mana.IMana;
import com.barribob.MaelstromMod.mana.ManaProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
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
	    if (PacketUtils.getPlayer() != null)
	    {
		EntityPlayer player = PacketUtils.getPlayer();
		IMana mana = player.getCapability(ManaProvider.MANA, null);

		// Handle flash animation
		if (message.mana - mana.getMana() >= 0.5)
		{
		    InGameGui.setManaFlashCounter(InGameGui.MAX_FLASH_COUNTER);
		}
		else if (message.mana - mana.getMana() <= -0.5)
		{
		    InGameGui.setManaFlashCounter(-InGameGui.MAX_FLASH_COUNTER);
		}

		mana.set(message.mana);

		if (mana.isLocked())
		{
		    mana.setLocked(false);
		}
	    }
	    return null;
	}
    }
}
