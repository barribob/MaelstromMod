package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.enchantments.EnchantmentReload;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * 
 * Based on Jabelar's enchantment tutorial
 * https://jabelarminecraft.blogspot.com/p/minecraft-modding_6.html
 *
 */
@ObjectHolder(Reference.MOD_ID)
public class ModEnchantments
{
    public static final Enchantment reload = null;
    
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class RegistrationHandler
    {
	
	@SubscribeEvent
	public static void onRegisterEnchantments(final RegistryEvent.Register<Enchantment> event)
	{
	    System.out.println("Registering Enchantments");
	    final IForgeRegistry<Enchantment> registry = event.getRegistry();
	    
	    registry.register(new EnchantmentReload("reload", Enchantment.Rarity.COMMON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND}));
	}
    }
}
