package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Sends an entity animation to play to the client side
 *
 */
public class MessageAnimation implements IMessage
{
    private int animationId;
    private int entityId;

    public MessageAnimation()
    {
    }

    public MessageAnimation(int animationId, int id)
    {
	this.animationId = animationId;
	this.entityId = id;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
	this.animationId = buf.readInt();
	this.entityId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
	buf.writeInt(this.animationId);
	buf.writeInt(this.entityId);
    }

    public static class Handler implements IMessageHandler<MessageAnimation, IMessage>
    {
	@Override
	public IMessage onMessage(MessageAnimation message, MessageContext ctx)
	{
	    Entity entity = PacketUtils.getWorld().getEntityByID(message.entityId);
	    if (entity instanceof EntityLeveledMob)
	    {
		((EntityLeveledMob) entity).startAnimation(message.animationId);
	    }
	    return null;
	}
    }
}
