package com.barribob.MaelstromMod.event_handlers;

import com.barribob.MaelstromMod.init.ModItems;

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
	    if ((heldItem.equals(ModItems.BAKUYA) && offhandItem.equals(ModItems.KANSHOU)) || (heldItem.equals(ModItems.KANSHOU) && offhandItem.equals(ModItems.BAKUYA)))
	    {
		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20, 1));
	    }
	}
    }
}
