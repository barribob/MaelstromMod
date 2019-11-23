package com.barribob.MaelstromMod.event_handlers;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.util.LeapingEntity;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber()
public class EntityEventHandler
{
    @SubscribeEvent
    public static void onLivingFallEvent(LivingFallEvent event)
    {
	if (event.getEntityLiving() instanceof LeapingEntity && ((LeapingEntity) event.getEntityLiving()).isLeaping())
	{
	    ((LeapingEntity) event.getEntityLiving()).onStopLeaping();
	    ((LeapingEntity) event.getEntityLiving()).setLeaping(false);
	}
    }

    @SubscribeEvent
    public static void onEntityUpdateEvent(LivingUpdateEvent event)
    {
	if (event.getEntity().dimension == ModConfig.world.dark_nexus_dimension_id && event.getEntity() instanceof EntityPlayer)
	{
	    ModUtils.performNTimes(15, (i) -> {
		Vec3d pos = event.getEntity().getPositionVector().add(new Vec3d(ModRandom.getFloat(8), ModRandom.getFloat(4), ModRandom.getFloat(4)));
		event.getEntity().world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.x - 8, pos.y + 2, pos.z, 0.8, 0, 0);
	    });
	    event.getEntity().addVelocity(0.02, 0, 0);
	}
    }

    @SubscribeEvent
    public static void onEntitySpawnEvent(LivingSpawnEvent event)
    {
	if (event.getEntityLiving() instanceof EntitySheep)
	{
	    if (event.getEntityLiving().dimension == ModConfig.world.fracture_dimension_id)
	    {
		((EntitySheep) event.getEntityLiving()).setFleeceColor(EnumDyeColor.CYAN);
	    }
	    if (event.getEntityLiving().dimension == ModConfig.world.cliff_dimension_id)
	    {
		((EntitySheep) event.getEntityLiving()).setFleeceColor(EnumDyeColor.GRAY);
	    }
	}
    }
}
