package com.barribob.MaelstromMod.event_handlers;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.util.LeapingEntity;
import com.barribob.MaelstromMod.gui.InGameGui;
import com.barribob.MaelstromMod.init.ModDimensions;
import com.barribob.MaelstromMod.init.ModPotions;
import com.barribob.MaelstromMod.mana.IMana;
import com.barribob.MaelstromMod.mana.ManaProvider;
import com.barribob.MaelstromMod.packets.MessageMana;
import com.barribob.MaelstromMod.packets.MessagePlayDarkNexusWindSound;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Mod.EventBusSubscriber()
public class EntityEventHandler {
    // Queues players to receive the wind sound packet
    private static final Set<EntityPlayerMP> DARK_NEXUS_PLAYERS = Collections.newSetFromMap(new WeakHashMap<EntityPlayerMP, Boolean>());

    @SubscribeEvent
    public static void onLivingFallEvent(LivingFallEvent event) {
	if (event.getEntityLiving() instanceof LeapingEntity && ((LeapingEntity) event.getEntityLiving()).isLeaping()) {
	    ((LeapingEntity) event.getEntityLiving()).onStopLeaping();
	    ((LeapingEntity) event.getEntityLiving()).setLeaping(false);
	}
    }

    // Play wind sound when traveling to the dark nexus dimension
    @SubscribeEvent
    public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
	if (event.getEntity() instanceof EntityPlayerMP && event.getDimension() == ModDimensions.DARK_NEXUS.getId()) {
	    DARK_NEXUS_PLAYERS.add((EntityPlayerMP) event.getEntity());
	}
    }

    // Play wind sound when logging in and in dark nexus dimension
    @SubscribeEvent
    public static void onWorldLoad(PlayerLoggedInEvent event) {
	if (!event.player.world.isRemote) {
	    if (event.player.dimension == ModDimensions.DARK_NEXUS.getId()) {
		DARK_NEXUS_PLAYERS.add((EntityPlayerMP) event.player);
	    }
	}
    }

    @SubscribeEvent
    public static void onEntityUpdateEvent(LivingUpdateEvent event) {

	// Play wind sound for players in dark nexus
	if (DARK_NEXUS_PLAYERS.contains(event.getEntity()) && event.getEntity().dimension == ModDimensions.DARK_NEXUS.getId()) {
	    Main.network.sendTo(new MessagePlayDarkNexusWindSound(), (EntityPlayerMP) event.getEntity());
	    DARK_NEXUS_PLAYERS.remove(event.getEntity());
	}

	if (event.getEntityLiving() != null && event.getEntityLiving().isPotionActive(ModPotions.water_strider)) {
	    ModUtils.walkOnWater(event.getEntityLiving(), event.getEntityLiving().world);
	}

	if (event.getEntity().dimension == ModConfig.world.dark_nexus_dimension_id && event.getEntity() instanceof EntityPlayer) {
	    EntityPlayer player = (EntityPlayer) event.getEntity();
	    if (player.world.isRemote) {
		ModUtils.performNTimes(15, (i) -> {
		    Vec3d pos = player.getPositionVector().add(new Vec3d(ModRandom.getFloat(8), ModRandom.getFloat(4), ModRandom.getFloat(4)));
		    ParticleManager.spawnColoredSmoke(player.world, pos, ModColors.DARK_GREY, new Vec3d(0.8, 0, 0));
		});
	    }

	    int[] blockage = { 0, 0 }; // Represents the two y values the wind could be blowing at the player

	    // Find any blocks that block the path of the wind
	    for (int x = 0; x < 4; x++) {
		for (int y = 0; y < 2; y++) {
		    BlockPos pos = new BlockPos(player.getPositionVector()).add(new BlockPos(x - 4, y, 0));
		    IBlockState block = player.world.getBlockState(pos);
		    if (block.isFullBlock() || block.isFullCube() || block.isBlockNormalCube()) {
			blockage[y] = 1;
		    }
		}
	    }

	    // With 1 blockage, velocity is 0.01. With no blockage, velocity is 0.02, and
	    // with all blockage, velocity is 0
	    float windStrength = (2 - (blockage[0] + blockage[1])) * 0.5f * 0.02f;
	    player.addVelocity(windStrength, 0, 0);

	}

	if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer) {
	    EntityPlayer player = ((EntityPlayer) event.getEntity());
	    if (event.getEntity().ticksExisted % 35 == 0) {
		IMana currentMana = player.getCapability(ManaProvider.MANA, null);
		if (!currentMana.isLocked()) {
		    if (currentMana.isRecentlyConsumed()) {
			currentMana.setRecentlyConsumed(false);
		    }
		    else {
			currentMana.replenish(1f);
			Main.network.sendTo(new MessageMana(currentMana.getMana()), (EntityPlayerMP) player);
		    }
		}
	    }
	}
	else if (event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer) {
	    InGameGui.updateCounter();
	}
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event) {
	// Persist whether mana is locked across player respawning
	IMana newMana = event.getEntityPlayer().getCapability(ManaProvider.MANA, null);
	IMana oldMana = event.getOriginal().getCapability(ManaProvider.MANA, null);
	newMana.setLocked(oldMana.isLocked());
    }

    @SubscribeEvent
    public static void onEntitySpawnEvent(LivingSpawnEvent event) {
	if (event.getEntityLiving() instanceof EntitySheep) {
	    if (event.getEntityLiving().dimension == ModConfig.world.fracture_dimension_id) {
		((EntitySheep) event.getEntityLiving()).setFleeceColor(EnumDyeColor.CYAN);
	    }
	    if (event.getEntityLiving().dimension == ModConfig.world.cliff_dimension_id) {
		((EntitySheep) event.getEntityLiving()).setFleeceColor(EnumDyeColor.GRAY);
	    }
	}
    }
}
