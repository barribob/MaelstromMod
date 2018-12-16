package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * 
 * Keeps track of all the sound resources and registers them
 *
 */
public class SoundsHandler 
{
	public static SoundEvent ENTITY_SHADE_AMBIENT;
	public static SoundEvent ENTITY_SHADE_HURT;
	public static SoundEvent ENTITY_SHADE_DEATH;
	
	public static void registerSounds() 
	{
		ENTITY_SHADE_AMBIENT = registerSound("entity.shade.ambient");
		ENTITY_SHADE_HURT = registerSound("entity.shade.hurt");
		ENTITY_SHADE_DEATH = registerSound("entity.shade.death");
	}
	
	private static SoundEvent registerSound(String name) 
	{
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}
