package com.barribob.MaelstromMod.event_handlers;

import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerRainbow;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber()
public class ItemEventHandler
{
    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
    {
	if (event.getEntity() instanceof EntityPlayer)
	{
	    EntityPlayer player = (EntityPlayer) event.getEntity();
	    Item heldItem = player.getHeldItem(EnumHand.MAIN_HAND).getItem();
	    Item offhandItem = player.getHeldItem(EnumHand.OFF_HAND).getItem();

	    Item helmet = player.inventory.armorInventory.get(3).getItem();
	    Item chestplate = player.inventory.armorInventory.get(2).getItem();
	    Item leggings = player.inventory.armorInventory.get(1).getItem();
	    Item boots = player.inventory.armorInventory.get(0).getItem();
	    
	    if ((heldItem.equals(ModItems.BAKUYA) && offhandItem.equals(ModItems.KANSHOU)) || (heldItem.equals(ModItems.KANSHOU) && offhandItem.equals(ModItems.BAKUYA)))
	    {
		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20, 2));
	    }

	    if (heldItem.equals(ModItems.CROSS_OF_AQUA) || offhandItem.equals(ModItems.CROSS_OF_AQUA))
	    {
		ModUtils.walkOnWater(player, player.world);
	    }

	    if (helmet.equals(ModItems.NYAN_HELMET) && chestplate.equals(ModItems.NYAN_CHESTPLATE) && leggings.equals(ModItems.NYAN_LEGGINGS)
		    && boots.equals(ModItems.NYAN_BOOTS) && player.isSprinting())
	    {
		Entity particle = new ParticleSpawnerRainbow(player.world);
		particle.copyLocationAndAnglesFrom(player);
		player.world.spawnEntity(particle);
	    }
	}
    }

}
