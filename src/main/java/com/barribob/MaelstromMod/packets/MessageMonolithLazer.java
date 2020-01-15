package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.entity.entities.EntityMonolith;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMonolithLazer implements IMessage
{
    private NBTTagCompound data;

    public MessageMonolithLazer()
    {
    }

    public MessageMonolithLazer(NBTTagCompound data)
    {
	this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
	data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
	ByteBufUtils.writeTag(buf, data);
    }

    public static class Handler implements IMessageHandler<MessageMonolithLazer, IMessage>
    {
	@Override
	public IMessage onMessage(MessageMonolithLazer message, MessageContext ctx)
	{
	    if (PacketUtils.getPlayer() != null)
	    {
		EntityPlayer player = PacketUtils.getPlayer();
		if (message.data.hasKey("entityId") && message.data.hasKey("posX") && message.data.hasKey("posY") && message.data.hasKey("posZ"))
		{
		    Entity entity = player.world.getEntityByID(message.data.getInteger("entityId"));
		    if (entity instanceof EntityMonolith)
		    {
			((EntityMonolith) entity).setLazerDir(new Vec3d(message.data.getFloat("posX"), message.data.getFloat("posY"), message.data.getFloat("posZ")));
		    }
		}
	    }

	    // No response message
	    return null;
	}

    }
}