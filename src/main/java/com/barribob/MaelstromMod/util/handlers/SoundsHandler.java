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
    public static SoundEvent ENTITY_HORROR_AMBIENT;
    public static SoundEvent ENTITY_HORROR_HURT;
    public static SoundEvent ENTITY_HORROR_DEATH;
    public static SoundEvent ENTITY_BEAST_AMBIENT;
    public static SoundEvent ENTITY_BEAST_HURT;
    public static SoundEvent ENTTIY_CRAWLER_AMBIENT;
    public static SoundEvent ENTTIY_CRAWLER_HURT;
    public static SoundEvent ENTITY_MONOLITH_AMBIENT;
    public static SoundEvent ENTITY_CHAOS_KNIGHT_BLOCK;

    public static void registerSounds()
    {
	ENTITY_HORROR_AMBIENT = registerSound("entity.horror.ambient");
	ENTITY_HORROR_HURT = registerSound("entity.horror.hurt");
	ENTITY_HORROR_DEATH = registerSound("entity.horror.death");
	ENTITY_SHADE_AMBIENT = registerSound("entity.shade.ambient");
	ENTITY_SHADE_HURT = registerSound("entity.shade.hurt");
	ENTITY_SHADE_DEATH = registerSound("entity.shade.death");
	ENTITY_BEAST_AMBIENT = registerSound("entity.beast.ambient");
	ENTITY_BEAST_HURT = registerSound("entity.beast.hurt");
	ENTTIY_CRAWLER_AMBIENT = registerSound("entity.swamp_crawler.ambient");
	ENTTIY_CRAWLER_HURT = registerSound("entity.swamp_crawler.hurt");
	ENTITY_MONOLITH_AMBIENT = registerSound("entity.monolith.ambient");
	ENTITY_CHAOS_KNIGHT_BLOCK = registerSound("entity.chaos_knight.block");
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
