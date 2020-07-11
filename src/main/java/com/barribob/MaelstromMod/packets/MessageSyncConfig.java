package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.util.Reference;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncConfig implements IMessage {
    float progression_scale;
    float weapon_damage;
    float armor_toughness;
    float elemental_factor;

    public MessageSyncConfig() {

    }

    public MessageSyncConfig(float progression_scale, float weapon_damage, float armor_toughness, float elemental_factor) {
        this.progression_scale = progression_scale;
        this.weapon_damage = weapon_damage;
        this.armor_toughness = armor_toughness;
        this.elemental_factor = elemental_factor;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.progression_scale = buf.readFloat();
        this.weapon_damage = buf.readFloat();
        this.armor_toughness = buf.readFloat();
        this.elemental_factor = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(this.progression_scale);
        buf.writeFloat(this.weapon_damage);
        buf.writeFloat(this.armor_toughness);
        buf.writeFloat(this.elemental_factor);
    }

    public static class Handler implements IMessageHandler<MessageSyncConfig, IMessage> {
        @Override
        public IMessage onMessage(MessageSyncConfig message, MessageContext ctx) {
            ModConfig.balance.progression_scale = message.progression_scale;
            ModConfig.balance.weapon_damage = message.weapon_damage;
            ModConfig.balance.armor_toughness = message.armor_toughness;
            ModConfig.balance.elemental_factor = message.elemental_factor;
            ConfigManager.sync(Reference.MOD_ID, Type.INSTANCE);
            return null;
        }
    }
}
