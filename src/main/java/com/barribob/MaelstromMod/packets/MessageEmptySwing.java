package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.items.tools.ToolSword;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * This packet sends info to the server that the player missed
 *
 * @author Barribob
 */
public class MessageEmptySwing implements IMessage {
    public MessageEmptySwing() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<MessageEmptySwing, IMessage> {
        @Override
        public IMessage onMessage(MessageEmptySwing message, MessageContext ctx) {
            final EntityPlayerMP player = ctx.getServerHandler().player;

            player.getServer().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    if (player.getHeldItemMainhand() == null) {
                        return;
                    }
                    Item sword = player.getHeldItemMainhand().getItem();

                    if (sword instanceof ToolSword) {
                        float atkCooldown = player.getCooledAttackStrength(0.5F);
                        if (atkCooldown > 0.9F) {
                            ((ToolSword) sword).doSweepAttack(player, null);
                        }
                    }
                }
            });

            // No response message
            return null;
        }

    }
}