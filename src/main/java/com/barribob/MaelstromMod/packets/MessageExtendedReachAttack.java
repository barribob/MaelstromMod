package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.items.IExtendedReach;
import com.barribob.MaelstromMod.items.tools.ToolLongsword;
import com.barribob.MaelstromMod.renderer.InputOverrides;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
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

		    if (player.getHeldItemMainhand() == null)
		    {
			return;
		    }

		    if (entity == null) // Miss
		    {
			// On a miss, reset cooldown anyways
			player.resetCooldown();
			net.minecraftforge.common.ForgeHooks.onEmptyLeftClick(player);
		    }
		    else // Hit
		    {
			Item sword = player.getHeldItemMainhand().getItem();

			if (sword instanceof IExtendedReach)
			{
			    if (entity.getDistance(player) < ((IExtendedReach) sword).getReach())
			    {
				player.attackTargetEntityWithCurrentItem(entity);
			    }
			}
		    }

		    player.swingArm(EnumHand.MAIN_HAND);
		}
	    });

	    // No response message
	    return null;
	}

    }
}