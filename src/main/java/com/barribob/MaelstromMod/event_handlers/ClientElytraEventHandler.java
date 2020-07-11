package com.barribob.MaelstromMod.event_handlers;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.model.LayerModElytra;
import com.barribob.MaelstromMod.items.ItemModElytra;
import com.barribob.MaelstromMod.packets.MessageStartElytraFlying;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Side.CLIENT)
public class ClientElytraEventHandler {
    static boolean prevJumpTick;
    private static Set<EntityPlayer> layeredPlayers = Collections.newSetFromMap(new WeakHashMap<EntityPlayer, Boolean>());

    @SubscribeEvent
    public static void onPressKey(InputUpdateEvent event) {
        if (event.getEntityPlayer() instanceof EntityPlayerSP) {
            EntityPlayerSP player = (EntityPlayerSP) event.getEntityPlayer();
            if (!prevJumpTick && player.movementInput.jump && !player.onGround && player.motionY < 0.0D && !player.isElytraFlying() && !player.capabilities.isFlying) {
                ItemStack itemstack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

                if (itemstack.getItem() instanceof ItemModElytra) {
                    Main.network.sendToServer(new MessageStartElytraFlying());
                }
            }
            prevJumpTick = player.movementInput.jump;
        }
    }

    // Jenky way of registering layers for player
    @SubscribeEvent
    public static void onRenderLiving(RenderLivingEvent.Pre<AbstractClientPlayer> event) {
        if (event.getEntity() instanceof EntityPlayer && !layeredPlayers.contains(event.getEntity())) {
            event.getRenderer().addLayer(new LayerModElytra(event.getRenderer()));
            layeredPlayers.add((EntityPlayer) event.getEntity());
        }
    }
}
