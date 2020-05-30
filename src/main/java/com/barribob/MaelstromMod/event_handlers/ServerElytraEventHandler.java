package com.barribob.MaelstromMod.event_handlers;

import java.util.Map;
import java.util.WeakHashMap;

import com.barribob.MaelstromMod.items.ItemModElytra;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Code based heavily on {@link https://github.com/GlassPane/Powered-Elytra} Handles the server side ticking of custom Elytras
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ServerElytraEventHandler {

    /**
     * These two functions from {@link https://github.com/GlassPane/Powered-Elytra}
     */
    public static synchronized void setFlying(EntityPlayerMP playerMP, boolean flying) {
	LAST_TICK_FLIGHT.put(playerMP, flying);
	if (flying) {
	    if (!playerMP.isElytraFlying()) {
		playerMP.setElytraFlying();
	    }
	}
	else if (playerMP.isElytraFlying()) {
	    playerMP.clearElytraFlying();
	}
    }

    public static boolean isFlying(EntityPlayerMP playerMP) {
	return LAST_TICK_FLIGHT.getOrDefault(playerMP, false);
    }

    private static final Map<EntityPlayerMP, Boolean> LAST_TICK_FLIGHT = new WeakHashMap<>();

    @SubscribeEvent
    public static void onPlayerUpdate(TickEvent.PlayerTickEvent event) {

	/**
	 * Requires both pre and post tick to be updated. Post tick required to keep vanilla update from resetting the elytra travel. Pre tick solves some small bugs, such as getting stuck in the ground under
	 * rare circumstances, and exiting flight mode too far above ground.
	 */
	if (event.player instanceof EntityPlayerMP) {
	    EntityPlayerMP mpPlayer = (EntityPlayerMP) event.player;
	    ItemStack stack = mpPlayer.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

	    boolean canContinueFly = stack.getItem() instanceof ItemModElytra && !mpPlayer.onGround && !mpPlayer.isRiding() && !mpPlayer.capabilities.isFlying;
	    if (isFlying(mpPlayer)) {
		if (event.phase == TickEvent.Phase.END) {
		    ModUtils.handleElytraTravel(mpPlayer);
		    mpPlayer.fallDistance = 1.0f;
		}
		setFlying(mpPlayer, canContinueFly);
	    }
	}
    }
}
