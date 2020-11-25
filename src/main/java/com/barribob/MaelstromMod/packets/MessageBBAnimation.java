package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.entity.animation.AnimationManager;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.logging.log4j.LogManager;

/**
 * Sends an entity animation to play to the client side
 */
public class MessageBBAnimation implements IMessage {
    private int animationId;
    private int entityId;
    private boolean remove;

    public MessageBBAnimation() {
    }

    public MessageBBAnimation(int animationId, int id, boolean remove) {
        this.animationId = animationId;
        this.entityId = id;
        this.remove = remove;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.animationId = buf.readInt();
        this.entityId = buf.readInt();
        this.remove = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.animationId);
        buf.writeInt(this.entityId);
        buf.writeBoolean(this.remove);
    }

    public static class Handler implements IMessageHandler<MessageBBAnimation, IMessage> {
        @Override
        public IMessage onMessage(MessageBBAnimation message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {

                /**
                 * Will spin for a second just in case the packet updating the entityID doesn't come before. That's why I put this in a separate thread so the client doesn't block
                 */
                Entity entity = PacketUtils.getWorld().getEntityByID(message.entityId);
                long time = System.currentTimeMillis();
                while (entity == null) {
                    if (System.currentTimeMillis() - time > 1000) {
                        LogManager.getLogger().warn("Failed to recieve entity id for animation.");
                        break;
                    }
                    entity = PacketUtils.getWorld().getEntityByID(message.entityId);
                }

                if (entity != null && entity instanceof EntityLivingBase) {
                    AnimationManager.updateAnimation((EntityLivingBase) entity, ModBBAnimations.getAnimationName(message.animationId), message.remove);
                }
            });
            return null;
        }
    }
}
