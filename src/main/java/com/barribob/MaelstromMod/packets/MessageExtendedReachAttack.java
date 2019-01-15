package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.items.IExtendedReach;
import com.barribob.MaelstromMod.items.tools.ToolExtendedReachSword;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * 
 * Taken from Jabelar's extended reach tutorial
 *
 */
public class MessageExtendedReachAttack implements IMessage
{
    private int entityId;

    public MessageExtendedReachAttack()
    {
    }

    public MessageExtendedReachAttack(int entityId)
    {
	this.entityId = entityId;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
	entityId = ByteBufUtils.readVarInt(buf, 4);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
	ByteBufUtils.writeVarInt(buf, entityId, 4);
    }

    public static class Handler implements IMessageHandler<MessageExtendedReachAttack, IMessage>
    {
	// Double checks from the server that the sword reach is valid (to prevent
	// hacking)
	@Override
	public IMessage onMessage(MessageExtendedReachAttack message, MessageContext ctx)
	{
	    final EntityPlayerMP player = ctx.getServerHandler().player;

	    player.getServer().addScheduledTask(new Runnable()
	    {
		@Override
		public void run()
		{
		    Entity entity = player.world.getEntityByID(message.entityId);

		    if (player.getHeldItemMainhand() == null || entity == null)
		    {
			return;
		    }

		    Item sword = player.getHeldItemMainhand().getItem();

		    if (sword instanceof IExtendedReach)
		    {
			if (((IExtendedReach) sword).getReach() >= player.getDistance(entity))
			{
			    player.attackTargetEntityWithCurrentItem(entity);
			}
		    }
		}
	    });

	    // No response message
	    return null;
	}

    }
}