package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * 
 * Does not actually unlock mana (that is done automatically in MessageMana)
 * This just spawn the particles when the catalyst is activated
 *
 */
public class MessageManaUnlock implements IMessage
{
    @Override
    public void fromBytes(ByteBuf buf)
    {
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
    }

    public static class MessageHandler implements IMessageHandler<MessageManaUnlock, IMessage>
    {
	@Override
	public IMessage onMessage(MessageManaUnlock message, MessageContext ctx)
	{
	    if (PacketUtils.getPlayer() != null)
	    {
		EntityPlayer player = PacketUtils.getPlayer();
		int numCircles = 7;
		float dy = (player.height * 1.5f) / numCircles;
		ModUtils.performNTimes(numCircles, (i) -> {
		    ParticleManager.spawnParticlesInCircle(1, 30, (pos) -> {
			pos = new Vec3d(pos.x, 0, pos.y);
			Vec3d worldPos = pos.add(player.getPositionVector()).add(ModUtils.yVec(i * dy));
			player.world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, worldPos.x, worldPos.y, worldPos.z, 0, 0.1, 0);
			ParticleManager.spawnMaelstromParticle(player.world, player.world.rand, worldPos);
		    });
		});
	    }
	    return null;
	}
    }
}
