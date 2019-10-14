package com.barribob.MaelstromMod.event_handlers;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromBeast;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.init.SoundEvents;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber()
public class EntityEventHandler
{
    @SubscribeEvent
    public static void onLivingFallEvent(LivingFallEvent event)
    {
	if (event.getEntityLiving() instanceof EntityMaelstromBeast && ((EntityMaelstromBeast) event.getEntityLiving()).isLeaping())
	{
	    EntityMaelstromBeast beast = (EntityMaelstromBeast) event.getEntityLiving();
	    ModUtils.handleAreaImpact(5, (e) -> beast.getAttack(), beast, beast.getPositionVector(), ModDamageSource.causeMaelstromExplosionDamage(beast));
	    beast.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f + ModRandom.getFloat(0.1f));
	    beast.world.setEntityState(beast, beast.explosionParticles);
	    ((EntityMaelstromBeast) event.getEntityLiving()).setLeaping(false);
	}
    }
}
