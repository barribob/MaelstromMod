package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.event_handlers.ServerElytraEventHandler;
import com.barribob.MaelstromMod.init.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Message from the client to indicate that the player has attempted to start elytra flying
 */
public class MessageStartElytraFlying implements IMessage {

    public MessageStartElytraFlying() {
    }

    public MessageStartElytraFlying(int entityId) {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<MessageStartElytraFlying, IMessage> {
        @Override
        public IMessage onMessage(MessageStartElytraFlying message, MessageContext ctx) {
            final EntityPlayerMP player = ctx.getServerHandler().player;

            player.getServer().addScheduledTask(() -> {
                boolean canFly = false;
                if (!player.onGround && player.motionY < 0.0D && !player.isElytraFlying() && !player.isInWater()) {
                    ItemStack itemstack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
                    // Hardcoded for security reasons. If an instanceof check is used, someone could extend and add a new elytra item to hack on the client
                    canFly = itemstack.getItem() == ModItems.ELYSIUM_WINGS;
                }
                ServerElytraEventHandler.setFlying(player, canFly);
            });

            // No response message
            return null;
        }

    }
}