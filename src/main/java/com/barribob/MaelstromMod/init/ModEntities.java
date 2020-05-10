package com.barribob.MaelstromMod.init;

import java.util.HashMap;
import java.util.Map;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.EntityCrimsonPortalSpawn;
import com.barribob.MaelstromMod.entity.entities.EntityAzureGolem;
import com.barribob.MaelstromMod.entity.entities.EntityAzureVillager;
import com.barribob.MaelstromMod.entity.entities.EntityBeast;
import com.barribob.MaelstromMod.entity.entities.EntityChaosKnight;
import com.barribob.MaelstromMod.entity.entities.EntityCliffFly;
import com.barribob.MaelstromMod.entity.entities.EntityCliffGolem;
import com.barribob.MaelstromMod.entity.entities.EntityDreamElk;
import com.barribob.MaelstromMod.entity.entities.EntityFloatingSkull;
import com.barribob.MaelstromMod.entity.entities.EntityGoldenBoss;
import com.barribob.MaelstromMod.entity.entities.EntityGoldenPillar;
import com.barribob.MaelstromMod.entity.entities.EntityHerobrineOne;
import com.barribob.MaelstromMod.entity.entities.EntityHorror;
import com.barribob.MaelstromMod.entity.entities.EntityIronShade;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromBeast;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromGoldenBoss;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromHealer;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromIllager;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromLancer;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMage;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromWitch;
import com.barribob.MaelstromMod.entity.entities.EntityMonolith;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.entities.EntitySwampCrawler;
import com.barribob.MaelstromMod.entity.entities.EntityWhiteMonolith;
import com.barribob.MaelstromMod.entity.entities.Herobrine;
import com.barribob.MaelstromMod.entity.entities.npc.NexusArmorer;
import com.barribob.MaelstromMod.entity.entities.npc.NexusBladesmith;
import com.barribob.MaelstromMod.entity.entities.npc.NexusGunTrader;
import com.barribob.MaelstromMod.entity.entities.npc.NexusMageTrader;
import com.barribob.MaelstromMod.entity.entities.npc.NexusSpecialTrader;
import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerExplosion;
import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerRainbow;
import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerSwordSwing;
import com.barribob.MaelstromMod.entity.projectile.EntityGeyser;
import com.barribob.MaelstromMod.entity.projectile.EntityGoldenRune;
import com.barribob.MaelstromMod.entity.projectile.EntityHealerOrb;
import com.barribob.MaelstromMod.entity.projectile.EntityLargeGoldenRune;
import com.barribob.MaelstromMod.entity.projectile.EntityOctoMissileLauncher;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBeastAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBeastQuake;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBlackFireball;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBone;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBoneQuake;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBrownstoneCannon;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBullet;
import com.barribob.MaelstromMod.entity.projectile.ProjectileChaosFireball;
import com.barribob.MaelstromMod.entity.projectile.ProjectileExplosiveDrill;
import com.barribob.MaelstromMod.entity.projectile.ProjectileFireball;
import com.barribob.MaelstromMod.entity.projectile.ProjectileGoldenFireball;
import com.barribob.MaelstromMod.entity.projectile.ProjectileGoldenMissile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHerobrineQuake;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHorrorAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMaelstromCannon;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMaelstromMeteor;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMaelstromMissile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMaelstromQuake;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMaelstromWisp;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMeteor;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMeteorSpawner;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMonolithFireball;
import com.barribob.MaelstromMod.entity.projectile.ProjectilePiercingBullet;
import com.barribob.MaelstromMod.entity.projectile.ProjectilePillarFlames;
import com.barribob.MaelstromMod.entity.projectile.ProjectilePumpkin;
import com.barribob.MaelstromMod.entity.projectile.ProjectileQuake;
import com.barribob.MaelstromMod.entity.projectile.ProjectileRepeater;
import com.barribob.MaelstromMod.entity.projectile.ProjectileRuneWisp;
import com.barribob.MaelstromMod.entity.projectile.ProjectileSkullAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileSwampSpittle;
import com.barribob.MaelstromMod.entity.projectile.ProjectileSwordSlash;
import com.barribob.MaelstromMod.entity.projectile.ProjectileWillOTheWisp;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityBossSpawner;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityDisappearingSpawner;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityFan;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMalestromSpawner;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMegaStructure;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityTeleporter;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityUpdater;
import com.barribob.MaelstromMod.entity.util.EntityAzurePortalSpawn;
import com.barribob.MaelstromMod.entity.util.EntityCliffPortalSpawn;
import com.barribob.MaelstromMod.entity.util.EntityCrimsonTowerSpawner;
import com.barribob.MaelstromMod.entity.util.EntityNexusParticleSpawner;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * 
 * Lists all of the entities in the mod
 *
 */
public class ModEntities
{
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
    public static final int MAELSTROM_GOLDEN_BOSS = 120;
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
    public static final int GOLDEN_BULLET_ID = 215;
    public static final int GOLDEN_REPEATER_ID = 216;
    public static final int PILLAR_FLAMES_ID = 217;
    public static final int GOLDEN_RUNE_ID = 218;
    public static final int GOLDEN_MAGE_ATTACK_ID = 220;
    public static final int OCTO_MISSILES_ID = 221;
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

    public static final int BOSS_EXPERIENCE = 1000;
    public static final int MINIBOSS_EXPERIENCE = 100;

    private static final Map<Class<? extends Entity>, String> ID_MAP = new HashMap<Class<? extends Entity>, String>();

    public static void registerEntities()
    {
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
	registerEntity("maelstrom_golden_boss", EntityMaelstromGoldenBoss.class, MAELSTROM_GOLDEN_BOSS, 70, cliff_maelstrom);
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
	registerEntity("octo_missiles", EntityOctoMissileLauncher.class, OCTO_MISSILES_ID, 30);
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

	registerEntity("explosion_particle", ParticleSpawnerExplosion.class, PARTICLE_START_ID++, 20);
	registerEntity("black_gold_sword_particle", ParticleSpawnerSwordSwing.class, PARTICLE_START_ID++, 20);
	registerEntity("rainbow_particle", ParticleSpawnerRainbow.class, PARTICLE_START_ID++, 20);

	registerTileEntity(TileEntityMalestromSpawner.class, "spawner");
	registerTileEntity(TileEntityDisappearingSpawner.class, "maelstrom_spawner");
	registerTileEntity(TileEntityMegaStructure.class, "mega_structure");
	registerTileEntity(TileEntityTeleporter.class, "nexus_teleporter");
	registerTileEntity(TileEntityBossSpawner.class, "nexus_spawner");
	registerTileEntity(TileEntityUpdater.class, "updater");
	registerTileEntity(TileEntityFan.class, "fan");
    }

    public static String getID(Class<? extends Entity> entity)
    {
	if (ID_MAP.containsKey(entity))
	{
	    return Reference.MOD_ID + ":" + ID_MAP.get(entity);
	}
	throw new IllegalArgumentException("Mapping of an entity has not be registered for the maelstrom mod spawner system.");
    }

    private static void registerEntityWithID(String name, Class<? extends Entity> entity, int id, int range, Vec3i eggColor)
    {
	EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, true, eggColor.getX(), eggColor.getY());
	ID_MAP.put(entity, name);
    }

    private static void registerEntityWithID(String name, Class<? extends Entity> entity, int id, int range)
    {
	EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, true);
	ID_MAP.put(entity, name);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, Vec3i eggColor)
    {
	EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, true, eggColor.getX(), eggColor.getY());
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range)
    {
	EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, true);
    }

    private static void registerFastProjectile(String name, Class<? extends Entity> entity, int id, int range)
    {
	EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, false);
    }

    private static void registerTileEntity(Class<? extends TileEntity> entity, String name)
    {
	GameRegistry.registerTileEntity(entity, new ResourceLocation(Reference.MOD_ID + ":" + name));
    }
}
