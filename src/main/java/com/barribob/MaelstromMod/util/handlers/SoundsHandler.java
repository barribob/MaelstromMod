package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.util.Reference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

/**
 * Keeps track of all the sound resources and registers them
 */
public class SoundsHandler {
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
    public static SoundEvent ENTITY_CHAOS_KNIGHT_AMBIENT;
    public static SoundEvent ENTITY_CHAOS_KNIGHT_HURT;
    public static SoundEvent ENTITY_CHAOS_KNIGHT_DEATH;
    public static SoundEvent ENTITY_GAUNTLET_AMBIENT;
    public static SoundEvent ENTITY_GAUNTLET_HURT;
    public static SoundEvent ENTITY_GAUNTLET_LAZER_CHARGE;

    public static void registerSounds() {
        ENTITY_HORROR_AMBIENT = registerSound("horror.ambient", "entity");
        ENTITY_HORROR_HURT = registerSound("horror.hurt", "entity");
        ENTITY_HORROR_DEATH = registerSound("horror.death", "entity");
        registerSound("horror.attack", "entity");

        ENTITY_SHADE_AMBIENT = registerSound("shade.ambient", "entity");
        ENTITY_SHADE_HURT = registerSound("shade.hurt", "entity");
        ENTITY_SHADE_DEATH = registerSound("shade.death", "entity");
        registerSound("shade.attack", "entity");

        ENTITY_BEAST_AMBIENT = registerSound("beast.ambient", "entity");
        ENTITY_BEAST_HURT = registerSound("beast.hurt", "entity");
        registerSound("beast.death", "entity");

        registerSound("illager.projectile_attack", "entity");
        registerSound("illager.tunnel_attack", "entity");
        registerSound("illager.shield_attack", "entity");
        registerSound("illager.summon_attack", "entity");

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
        registerSound("none", "entity");
    }

    private static final Map<String, SoundEvent> registeredSoundEvents = new HashMap<>();
    private static final Map<String, SoundData> toJson = new HashMap<>();

    public static SoundEvent get(String soundName) {
        if(registeredSoundEvents.containsKey(soundName)) {
            return registeredSoundEvents.get(soundName);
        }

        return registeredSoundEvents.get("none");
    }

    /**
     * Hacky way of autogenerating the sound.json file from the registered sounds up above.
     * It outputs the sound in out/production/MaelstromMod.main/assets/mm/sounds_test.json
     */
    private static void generateSoundsJson() {
        URL sounds = SoundHandler.class.getClassLoader().getResource("assets/mm/sounds_test.json");

        try (JsonWriter writer = new JsonWriter(new FileWriter(sounds.getFile()))){
            writer.setIndent("\t");
            Type type = new TypeToken<Map<String, SoundData>>(){}.getType();
            Gson gson = new Gson();
            gson.toJson(toJson, type, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class SoundData {
        private String category;
        private String subtitle;
        private List<Sound> sounds;

        public SoundData(String category, String subtitle, Sound sound) {
            this.category = category;
            this.subtitle = subtitle;
            this.sounds = new ArrayList<>();
            this.sounds.add(sound);
        }
    }

    private static class Sound {
        private String name;
        private boolean stream;

        public Sound(String name, boolean stream) {
            this.name = name;
            this.stream = stream;
        }
    }

    private static SoundEvent registerSound(String name, String category) {
        String fullName = category + "." + name;
        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, fullName);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(fullName);
        ForgeRegistries.SOUND_EVENTS.register(event);
        registeredSoundEvents.put(name, event);

        String path = Reference.MOD_ID + ":" + category + "/" + String.join("/", name.split("\\."));
        Sound Sound = new Sound(path, true);
        toJson.put(fullName, new SoundData(category, fullName,  Sound));

        return event;
    }
}
