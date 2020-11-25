package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * Keeps track of all the sound resources and registers them
 */
public class SoundsHandler {
    public static SoundEvent ENTITY_SHADE_AMBIENT;
    public static SoundEvent ENTITY_SHADE_HURT;
//    public static SoundEvent ENTITY_SHADE_DEATH;
    public static SoundEvent ENTITY_HORROR_AMBIENT;
    public static SoundEvent ENTITY_HORROR_HURT;
//    public static SoundEvent ENTITY_HORROR_DEATH;
    public static SoundEvent ENTITY_BEAST_AMBIENT;
    public static SoundEvent ENTITY_BEAST_HURT;
    public static SoundEvent ENTTIY_CRAWLER_AMBIENT;
    public static SoundEvent ENTTIY_CRAWLER_HURT;
    public static SoundEvent ENTITY_MONOLITH_AMBIENT;
    public static SoundEvent ENTITY_CHAOS_KNIGHT_BLOCK;
    public static SoundEvent ENTITY_CHAOS_KNIGHT_AMBIENT;
    public static SoundEvent ENTITY_CHAOS_KNIGHT_HURT;
    public static SoundEvent ENTITY_CHAOS_KNIGHT_DEATH;
    public static SoundEvent ENTITY_GAUNTLET_AMBIENT;
    public static SoundEvent ENTITY_GAUNTLET_HURT;
    public static SoundEvent ENTITY_GAUNTLET_LAZER_CHARGE;
    public static SoundEvent NONE;

    // Sound hooks
    public static class Hooks{
        public static SoundEvent ENTITY_ILLAGER_SPELL_CHARGE;
        public static SoundEvent ENTITY_ILLAGER_DOME_CHARGE;
        public static SoundEvent ENTITY_ILLAGER_VORTEX;
        public static SoundEvent ENTITY_ILLAGER_DOME;
    }

    public static void registerSounds() {
        ENTITY_HORROR_AMBIENT = registerSound("horror.ambient", "entity");
        ENTITY_HORROR_HURT = registerSound("horror.hurt", "entity");

        ENTITY_SHADE_AMBIENT = registerSound("shade.ambient", "entity");
        ENTITY_SHADE_HURT = registerSound("shade.hurt", "entity");

        ENTITY_BEAST_AMBIENT = registerSound("beast.ambient", "entity");
        ENTITY_BEAST_HURT = registerSound("beast.hurt", "entity");

        ENTTIY_CRAWLER_AMBIENT = registerSound("swamp_crawler.ambient", "entity");
        ENTTIY_CRAWLER_HURT = registerSound("swamp_crawler.hurt", "entity");
        ENTITY_MONOLITH_AMBIENT = registerSound("monolith.ambient", "entity");
        ENTITY_CHAOS_KNIGHT_BLOCK = registerSound("chaos_knight.block", "entity");
        ENTITY_CHAOS_KNIGHT_AMBIENT = registerSound("chaos_knight.ambient", "entity");
        ENTITY_CHAOS_KNIGHT_HURT = registerSound("chaos_knight.hurt", "entity");
        ENTITY_CHAOS_KNIGHT_DEATH = registerSound("chaos_knight.death", "entity");
        ENTITY_GAUNTLET_AMBIENT = registerSound("gauntlet.ambient", "entity");
        ENTITY_GAUNTLET_HURT = registerSound("gauntlet.hurt", "entity");
        ENTITY_GAUNTLET_LAZER_CHARGE = registerSound("gauntlet.lazer_charge", "entity");
        NONE = registerSound("none", "entity");

        Hooks.ENTITY_ILLAGER_SPELL_CHARGE = registerSound("illager.spell_charge", "entity");
        Hooks.ENTITY_ILLAGER_DOME_CHARGE = registerSound("illager.dome_charge", "entity");
        Hooks.ENTITY_ILLAGER_VORTEX = registerSound("illager.vortex", "entity");
        Hooks.ENTITY_ILLAGER_DOME = registerSound("illager.dome", "entity");
    }

    private static SoundEvent registerSound(String name, String category) {
        String fullName = category + "." + name;
        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, fullName);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(fullName);
        ForgeRegistries.SOUND_EVENTS.register(event);

        return event;
    }
}
