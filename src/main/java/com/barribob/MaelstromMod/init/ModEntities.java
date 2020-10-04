package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.EntityCrimsonPortalSpawn;
import com.barribob.MaelstromMod.entity.entities.*;
import com.barribob.MaelstromMod.entity.entities.gauntlet.EntityAlternativeMaelstromGauntlet;
import com.barribob.MaelstromMod.entity.entities.gauntlet.EntityMaelstromGauntlet;
import com.barribob.MaelstromMod.entity.entities.npc.*;
import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerExplosion;
import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerRainbow;
import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerSwordSwing;
import com.barribob.MaelstromMod.entity.projectile.*;
import com.barribob.MaelstromMod.entity.tileentity.*;
import com.barribob.MaelstromMod.entity.util.*;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * Lists all of the entities in the mod
 */
public class ModEntities {
    public static final int SHADE_ID = 100;
    public static final int HORROR_ID = 101;
    public static final int DREAM_ELK_ID = 102;
    public static final int BEAST_ID = 103;
    public static final int MAELSTROM_ILLAGER_ID = 104;
    public static final int AZURE_VILLAGER_ID = 105;
    public static final int MAELSTROM_MAGE_ID = 106;
    public static final int AZURE_GOLEM_ID = 107;
    public static final int FLOATING_SKULL_ID = 108;
    public static final int HEROBRINE_1_ID = 109;
    public static final int HEROBRINE_CONTROLLLER = 110;
    public static final int NEXUS_GUNSMITH = 111;
    public static final int NEXUS_MAGE = 112;
    public static final int NEXUS_ARMORER = 113;
    public static final int NEXUS_SAIYAN = 114;
    public static final int NEXUS_BLADESMITH = 115;
    public static final int GOLDEN_PILLAR = 116;
    public static final int GOLDEN_BOSS = 119;
    public static final int MAELSTROM_WITCH = 121;
    public static final int CLIFF_GOLEM = 122;

    private static int ENTITY_START_ID = 123;

    public static final int HORROR_ATTACK_ID = 202;
    public static final int BEAST_ATTACK_ID = 203;
    public static final int BULLET_ID = 204;
    public static final int MAELSTROM_CANNON_ID = 205;
    public static final int WILL_O_THE_WISP_ID = 206;
    public static final int QUAKE_ID = 207;
    public static final int SKULL_ATTACK_ID = 208;
    public static final int AZURE_PORTAL_SPAWN_ID = 209;
    public static final int PUMPKIN_ID = 210;
    public static final int REPEATER_ID = 211;
    public static final int FIREBALL_ID = 212;
    public static final int HEROBRINE_SLASH_ID = 213;
    public static final int BLACK_FIREBALL_ID = 214;
    public static final int PILLAR_FLAMES_ID = 217;
    public static final int GOLDEN_RUNE_ID = 218;
    public static final int GOLDEN_MAGE_ATTACK_ID = 220;
    public static final int GOLDEN_FIREBALL_ID = 222;
    public static final int MAELSTROM_QUAKE_ID = 223;
    public static final int WOOD_ID = 224;
    public static final int GEYSER_ID = 225;
    public static final int BROWNSTONE_CANNON_ID = 226;
    public static final int CLIFF_PORTAL_SPAWN = 227;
    public static final int EXPLOSIVE_DRILL = 228;
    public static final int AZURE_BULLET = 229;

    private static int PROJECTILE_START_ID = 230;

    private static int PARTICLE_START_ID = 500;

    public static Vec3i maelstrom = new Vec3i(6433126, 3221816, 0);
    public static Vec3i azure = new Vec3i(7248383, 7236306, 0);
    public static Vec3i nexus = new Vec3i(15724287, 12501453, 0);
    public static Vec3i cliff = new Vec3i(0x999966, 0xe6e600, 0);
    public static Vec3i cliff_maelstrom = new Vec3i(6433126, 0xe6e600, 0);

    private static final Map<Class<? extends Entity>, String> ID_MAP = new HashMap<Class<? extends Entity>, String>();

    public static void registerEntities() {
        registerEntityWithID("shade", EntityShade.class, SHADE_ID, 50, maelstrom);
        registerEntityWithID("horror", EntityHorror.class, HORROR_ID, 50, maelstrom);
        registerEntity("dream_elk", EntityDreamElk.class, DREAM_ELK_ID, 50, azure);
        registerEntityWithID("beast", EntityBeast.class, BEAST_ID, 100, maelstrom);
        registerEntity("maelstrom_illager", EntityMaelstromIllager.class, MAELSTROM_ILLAGER_ID, 50, maelstrom);
        registerEntity("azure_villager", EntityAzureVillager.class, AZURE_VILLAGER_ID, 100, azure);
        registerEntityWithID("maelstrom_mage", EntityMaelstromMage.class, MAELSTROM_MAGE_ID, 50, maelstrom);
        registerEntity("azure_golem", EntityAzureGolem.class, AZURE_GOLEM_ID, 70, azure);
        registerEntityWithID("floating_skull", EntityFloatingSkull.class, FLOATING_SKULL_ID, 50, maelstrom);
        registerEntity("herobrine_1", EntityHerobrineOne.class, HEROBRINE_1_ID, 50);
        registerEntityWithID("herobrine_controller", Herobrine.class, HEROBRINE_CONTROLLLER, 50, maelstrom);
        registerEntity("nexus_gunsmith", NexusGunTrader.class, NEXUS_GUNSMITH, 50, nexus);
        registerEntity("nexus_mage", NexusMageTrader.class, NEXUS_MAGE, 50, nexus);
        registerEntity("nexus_armorer", NexusArmorer.class, NEXUS_ARMORER, 50, nexus);
        registerEntity("nexus_saiyan", NexusSpecialTrader.class, NEXUS_SAIYAN, 50, nexus);
        registerEntity("nexus_bladesmith", NexusBladesmith.class, NEXUS_BLADESMITH, 50, nexus);
        registerEntityWithID("golden_pillar", EntityGoldenPillar.class, GOLDEN_PILLAR, 50, cliff_maelstrom);
        registerEntityWithID("golden_boss", EntityGoldenBoss.class, GOLDEN_BOSS, 70, cliff_maelstrom);
        registerEntityWithID("maelstrom_witch", EntityMaelstromWitch.class, MAELSTROM_WITCH, 70, cliff_maelstrom);
        registerEntityWithID("cliff_golem", EntityCliffGolem.class, CLIFF_GOLEM, 70, cliff);
        registerEntity("swamp_crawler", EntitySwampCrawler.class, ENTITY_START_ID++, 50, cliff);
        registerEntity("cliff_fly", EntityCliffFly.class, ENTITY_START_ID++, 70, cliff);
        registerEntityWithID("iron_shade", EntityIronShade.class, ENTITY_START_ID++, 70, maelstrom);
        registerEntityWithID("maelstrom_beast", EntityMaelstromBeast.class, ENTITY_START_ID++, 70, maelstrom);
        registerEntity("monolith", EntityMonolith.class, ENTITY_START_ID++, 70, maelstrom);
        registerEntity("white_monolith", EntityWhiteMonolith.class, ENTITY_START_ID++, 70);
        registerEntityWithID("maelstrom_lancer", EntityMaelstromLancer.class, ENTITY_START_ID++, 50, maelstrom);
        registerEntityWithID("chaos_knight", EntityChaosKnight.class, ENTITY_START_ID++, 70, maelstrom);
        registerEntityWithID("maelstrom_healer", EntityMaelstromHealer.class, ENTITY_START_ID++, 50, maelstrom);
        registerEntityWithID("maelstrom_gauntlet", EntityMaelstromGauntlet.class, ENTITY_START_ID++, 70, maelstrom);
        registerEntityWithID("maelstrom_statue_of_nirvana", EntityMaelstromStatueOfNirvana.class, ENTITY_START_ID++, 70, cliff_maelstrom);
        registerEntityWithID("alternative_maelstrom_gauntlet", EntityAlternativeMaelstromGauntlet.class, ENTITY_START_ID++, 70, maelstrom);

        registerEntity("horror_attack", ProjectileHorrorAttack.class, HORROR_ATTACK_ID, 30);
        registerEntity("beast_attack", ProjectileBeastAttack.class, BEAST_ATTACK_ID, 100);
        registerFastProjectile("bullet", ProjectileBullet.class, BULLET_ID, 100);
        registerEntity("maelstrom_cannon", ProjectileMaelstromCannon.class, MAELSTROM_CANNON_ID, 30);
        registerEntity("will-o-the-wisp", ProjectileWillOTheWisp.class, WILL_O_THE_WISP_ID, 30);
        registerEntity("quake", ProjectileQuake.class, QUAKE_ID, 30);
        registerEntity("skull_attack", ProjectileSkullAttack.class, SKULL_ATTACK_ID, 30);
        registerEntity("azure_portal_spawn", EntityAzurePortalSpawn.class, AZURE_PORTAL_SPAWN_ID, 100);
        registerFastProjectile("pumpkin", ProjectilePumpkin.class, PUMPKIN_ID, 1000);
        registerEntity("repeater", ProjectileRepeater.class, REPEATER_ID, 30);
        registerEntity("fireball", ProjectileFireball.class, FIREBALL_ID, 30);
        registerEntity("herobrine_slash", ProjectileHerobrineQuake.class, HEROBRINE_SLASH_ID, 30);
        registerEntity("black_fireball", ProjectileBlackFireball.class, BLACK_FIREBALL_ID, 30);
        registerEntity("pillar_flames", ProjectilePillarFlames.class, PILLAR_FLAMES_ID, 30);
        registerEntity("golden_rune", EntityGoldenRune.class, GOLDEN_RUNE_ID, 30);
        registerEntity("golden_mage_attack", ProjectileGoldenMissile.class, GOLDEN_MAGE_ATTACK_ID, 30);
        registerEntity("golden_fireball", ProjectileGoldenFireball.class, GOLDEN_FIREBALL_ID, 30);
        registerEntity("maelstrom_quake", ProjectileMaelstromQuake.class, MAELSTROM_QUAKE_ID, 30);
        registerEntity("maelstrom_missile", ProjectileMaelstromMissile.class, WOOD_ID, 30);
        registerEntity("geyser", EntityGeyser.class, GEYSER_ID, 30);
        registerEntity("brownstone_cannon", ProjectileBrownstoneCannon.class, BROWNSTONE_CANNON_ID, 30);
        registerEntity("cliff_portal_spawn", EntityCliffPortalSpawn.class, CLIFF_PORTAL_SPAWN, 30);
        registerEntity("explosive_drill", ProjectileExplosiveDrill.class, EXPLOSIVE_DRILL, 30);
        registerFastProjectile("azure_bullet", ProjectilePiercingBullet.class, AZURE_BULLET, 100);
        registerEntity("swamp_spittle", ProjectileSwampSpittle.class, PROJECTILE_START_ID++, 30);
        registerEntity("nexus_particle", EntityNexusParticleSpawner.class, PROJECTILE_START_ID++, 50);
        registerEntity("maelstrom_wisp", ProjectileMaelstromWisp.class, PROJECTILE_START_ID++, 50);
        registerEntity("meteor", ProjectileMeteor.class, PROJECTILE_START_ID++, 100);
        registerEntity("meteor_spawner", ProjectileMeteorSpawner.class, PROJECTILE_START_ID++, 50);
        registerEntity("sword_slash", ProjectileSwordSlash.class, PROJECTILE_START_ID++, 50);
        registerEntity("beast_quake", ProjectileBeastQuake.class, PROJECTILE_START_ID++, 50);
        registerEntity("bone", ProjectileBone.class, PROJECTILE_START_ID++, 30);
        registerEntity("bone_quake", ProjectileBoneQuake.class, PROJECTILE_START_ID++, 50);
        registerEntity("monolith_fireball", ProjectileMonolithFireball.class, PROJECTILE_START_ID++, 50);
        registerEntity("maelstrom_meteor", ProjectileMaelstromMeteor.class, PROJECTILE_START_ID++, 50);
        registerEntity("large_golden_rune", EntityLargeGoldenRune.class, PROJECTILE_START_ID++, 40);
        registerEntity("crimson_tower_spawner", EntityCrimsonTowerSpawner.class, PROJECTILE_START_ID++, 40);
        registerEntity("healer_orb", EntityHealerOrb.class, PROJECTILE_START_ID++, 40);
        registerEntity("chaos_fireball", ProjectileChaosFireball.class, PROJECTILE_START_ID++, 40);
        registerEntity("rune_wisp", ProjectileRuneWisp.class, PROJECTILE_START_ID++, 40);
        registerEntity("crimson_portal_spawn", EntityCrimsonPortalSpawn.class, PROJECTILE_START_ID++, 40);
        registerEntity("tuning_fork_lazer_renderer", EntityTuningForkLazer.class, PROJECTILE_START_ID++, 60);
        registerEntity("mega_fireball", ProjectileMegaFireball.class, PROJECTILE_START_ID++, 40);
        registerEntity("statue_maelstrom_missile", ProjectileStatueMaelstromMissile.class, PROJECTILE_START_ID++, 30);
        registerEntity("maelstrom_rune", ProjectileMaelstromRune.class, PROJECTILE_START_ID++, 40);
        registerEntity("beast_fireball", ProjectileBeastFireball.class, PROJECTILE_START_ID++, 40);
        registerEntity("homing_flame", ProjectileHomingFlame.class, PROJECTILE_START_ID++, 50);
        registerEntity("crimson_wanderer", ProjectileCrimsonWanderer.class, PROJECTILE_START_ID++, 60);

        registerEntity("explosion_particle", ParticleSpawnerExplosion.class, PARTICLE_START_ID++, 20);
        registerEntity("black_gold_sword_particle", ParticleSpawnerSwordSwing.class, PARTICLE_START_ID++, 20);
        registerEntity("rainbow_particle", ParticleSpawnerRainbow.class, PARTICLE_START_ID++, 20);
        registerEntity("maelstrom_tower_destroyer", EntityMaelstromTowerDestroyer.class, PARTICLE_START_ID++, 20);

        registerTileEntity(TileEntityMalestromSpawner.class, "spawner");
        registerTileEntity(TileEntityDisappearingSpawner.class, "maelstrom_spawner");
        registerTileEntity(TileEntityMegaStructure.class, "mega_structure");
        registerTileEntity(TileEntityTeleporter.class, "nexus_teleporter");
        registerTileEntity(TileEntityBossSpawner.class, "nexus_spawner");
        registerTileEntity(TileEntityUpdater.class, "updater");
        registerTileEntity(TileEntityFan.class, "fan");
    }

    public static String getID(Class<? extends Entity> entity) {
        if (ID_MAP.containsKey(entity)) {
            return Reference.MOD_ID + ":" + ID_MAP.get(entity);
        }
        throw new IllegalArgumentException("Mapping of an entity has not be registered for the maelstrom mod spawner system.");
    }

    private static void registerEntityWithID(String name, Class<? extends Entity> entity, int id, int range, Vec3i eggColor) {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, true, eggColor.getX(), eggColor.getY());
        ID_MAP.put(entity, name);
    }

    private static void registerEntityWithID(String name, Class<? extends Entity> entity, int id, int range) {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, true);
        ID_MAP.put(entity, name);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, Vec3i eggColor) {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, true, eggColor.getX(), eggColor.getY());
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range) {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, true);
    }

    private static void registerFastProjectile(String name, Class<? extends Entity> entity, int id, int range) {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, false);
    }

    private static void registerTileEntity(Class<? extends TileEntity> entity, String name) {
        GameRegistry.registerTileEntity(entity, new ResourceLocation(Reference.MOD_ID + ":" + name));
    }
}
