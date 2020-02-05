package com.barribob.MaelstromMod.util.handlers;

import java.util.function.Function;

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
import com.barribob.MaelstromMod.entity.model.ModelArmorer;
import com.barribob.MaelstromMod.entity.model.ModelBeast;
import com.barribob.MaelstromMod.entity.model.ModelBladesmith;
import com.barribob.MaelstromMod.entity.model.ModelCliffFly;
import com.barribob.MaelstromMod.entity.model.ModelDreamElk;
import com.barribob.MaelstromMod.entity.model.ModelFloatingSkull;
import com.barribob.MaelstromMod.entity.model.ModelGoldenBoss;
import com.barribob.MaelstromMod.entity.model.ModelGoldenPillar;
import com.barribob.MaelstromMod.entity.model.ModelGunTrader;
import com.barribob.MaelstromMod.entity.model.ModelHorror;
import com.barribob.MaelstromMod.entity.model.ModelIronShade;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromLancer;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromMage;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromWarrior;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromWitch;
import com.barribob.MaelstromMod.entity.model.ModelMageTrader;
import com.barribob.MaelstromMod.entity.model.ModelNexusSaiyan;
import com.barribob.MaelstromMod.entity.model.ModelSwampCrawler;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBlackFireball;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBone;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBullet;
import com.barribob.MaelstromMod.entity.projectile.ProjectileFireball;
import com.barribob.MaelstromMod.entity.projectile.ProjectileGoldenBullet;
import com.barribob.MaelstromMod.entity.projectile.ProjectileGoldenFireball;
import com.barribob.MaelstromMod.entity.projectile.ProjectileGoldenMissile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHorrorAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMonolithFireball;
import com.barribob.MaelstromMod.entity.projectile.ProjectileSwampSpittle;
import com.barribob.MaelstromMod.entity.render.RenderAzureGolem;
import com.barribob.MaelstromMod.entity.render.RenderAzureVillager;
import com.barribob.MaelstromMod.entity.render.RenderChaosKnight;
import com.barribob.MaelstromMod.entity.render.RenderHerobrine;
import com.barribob.MaelstromMod.entity.render.RenderMaelstromBeast;
import com.barribob.MaelstromMod.entity.render.RenderMaelstromIllager;
import com.barribob.MaelstromMod.entity.render.RenderModEntity;
import com.barribob.MaelstromMod.entity.render.RenderMonolith;
import com.barribob.MaelstromMod.entity.render.RenderProjectile;
import com.barribob.MaelstromMod.entity.render.RenderWhiteMonolith;
import com.barribob.MaelstromMod.entity.util.EntityNexusParticleSpawner;
import com.barribob.MaelstromMod.entity.util.EntityParticleSpawner;
import com.barribob.MaelstromMod.entity.util.EntityPortalSpawn;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler
{
    public static void registerEntityRenderers()
    {
	registerModEntityRenderer(EntityShade.class, new ModelMaelstromWarrior(), "shade_base.png", "shade_azure.png", "shade_golden.png");
	registerModEntityRenderer(EntityHorror.class, new ModelHorror(), "horror.png");
	registerModEntityRenderer(EntityDreamElk.class, new ModelDreamElk(), "dream_elk.png");
	registerModEntityRenderer(EntityBeast.class, new ModelBeast(), "beast.png");
	registerModEntityRenderer(EntityMaelstromMage.class, new ModelMaelstromMage(), "maelstrom_mage.png", "maelstrom_mage_azure.png", "maelstrom_mage_golden.png");
	registerModEntityRenderer(EntityFloatingSkull.class, new ModelFloatingSkull(), "floating_skull.png");
	registerModEntityRenderer(Herobrine.class, (manager) -> new RenderHerobrine(manager, new ResourceLocation(Reference.MOD_ID + ":textures/entity/herobrine_1.png")));
	registerModEntityRenderer(EntityHerobrineOne.class, (manager) -> new RenderHerobrine(manager, new ResourceLocation(Reference.MOD_ID + ":textures/entity/shadow_clone.png")));
	registerModEntityRenderer(NexusGunTrader.class, new ModelGunTrader(), "gun_trader.png");
	registerModEntityRenderer(NexusMageTrader.class, new ModelMageTrader(), "mage_trader.png");
	registerModEntityRenderer(NexusArmorer.class, new ModelArmorer(), "armorer.png");
	registerModEntityRenderer(NexusBladesmith.class, new ModelBladesmith(), "bladesmith.png");
	registerModEntityRenderer(NexusSpecialTrader.class, new ModelNexusSaiyan(), "nexus_saiyan.png");
	registerModEntityRenderer(EntityGoldenPillar.class, new ModelGoldenPillar(), "golden_pillar.png");
	registerModEntityRenderer(EntityGoldenBoss.class, new ModelGoldenBoss(), "golden_boss.png");
	registerModEntityRenderer(EntityMaelstromGoldenBoss.class, new ModelGoldenBoss(), "maelstrom_golden_boss.png");
	registerModEntityRenderer(EntityMaelstromWitch.class, new ModelMaelstromWitch(), "maelstrom_witch.png");
	registerModEntityRenderer(EntitySwampCrawler.class, new ModelSwampCrawler(), "swamp_crawler.png");
	registerModEntityRenderer(EntityIronShade.class, new ModelIronShade(), "iron_shade.png");
	registerModEntityRenderer(EntityCliffFly.class, new ModelCliffFly(), "cliff_fly.png");
	registerModEntityRenderer(EntityAzureVillager.class, (manager) -> new RenderAzureVillager(manager));
	registerModEntityRenderer(EntityMaelstromIllager.class, (manager) -> new RenderMaelstromIllager(manager));
	registerModEntityRenderer(EntityAzureGolem.class, (manager) -> new RenderAzureGolem(manager, "azure_golem.png"));
	registerModEntityRenderer(EntityCliffGolem.class, (manager) -> new RenderAzureGolem(manager, "cliff_golem.png"));
	registerModEntityRenderer(EntityMaelstromBeast.class, (manager) -> new RenderMaelstromBeast(manager));
	registerModEntityRenderer(EntityMonolith.class, (manager) -> new RenderMonolith(manager));
	registerModEntityRenderer(EntityWhiteMonolith.class, (manager) -> new RenderWhiteMonolith(manager));
	registerModEntityRenderer(EntityMaelstromLancer.class, new ModelMaelstromLancer(), "maelstrom_lancer.png", "maelstrom_lancer_azure.png", "maelstrom_lancer_golden.png");
	registerModEntityRenderer(EntityChaosKnight.class, (manager) -> new RenderChaosKnight(manager, "chaos_knight.png"));

	registerProjectileRenderer(Projectile.class, ModItems.INVISIBLE);
	registerProjectileRenderer(ProjectileBullet.class, ModItems.IRON_PELLET);
	registerProjectileRenderer(EntityPortalSpawn.class, ModItems.INVISIBLE);
	registerProjectileRenderer(ProjectileGoldenBullet.class, ModItems.GOLD_PELLET);
	registerProjectileRenderer(EntityNexusParticleSpawner.class, ModItems.INVISIBLE);
	registerProjectileRenderer(ProjectileSwampSpittle.class, ModItems.SWAMP_SLIME);
	registerProjectileRenderer(EntityParticleSpawner.class, ModItems.INVISIBLE);
	registerProjectileRenderer(ProjectileBone.class, Items.BONE);
	registerProjectileRenderer(ProjectileHorrorAttack.class, ModItems.MAELSTROM_PELLET);
	registerProjectileRenderer(ProjectileFireball.class, Items.FIRE_CHARGE);
	registerProjectileRenderer(ProjectileBlackFireball.class, Items.FIRE_CHARGE);
	registerProjectileRenderer(ProjectileGoldenFireball.class, Items.FIRE_CHARGE);
	registerProjectileRenderer(ProjectileMonolithFireball.class, Items.FIRE_CHARGE);
	registerProjectileRenderer(ProjectileGoldenMissile.class, ModItems.GOLD_PELLET);

    }

    /**
     * Registers an entity with a model and sets it up for rendering
     */
    private static <T extends Entity, U extends ModelBase, V extends RenderModEntity> void registerModEntityRenderer(Class<T> entityClass, U model, String... textures)
    {
	registerModEntityRenderer(entityClass, (manager) -> new RenderModEntity(manager, model, textures));
    }

    private static <T extends Entity, U extends ModelBase, V extends RenderModEntity> void registerModEntityRenderer(Class<T> entityClass, Function<RenderManager, Render<? super T>> renderClass)
    {
	RenderingRegistry.registerEntityRenderingHandler(entityClass, new IRenderFactory<T>()
	{
	    @Override
	    public Render<? super T> createRenderFor(RenderManager manager)
	    {
		return renderClass.apply(manager);
	    }
	});
    }

    /**
     * Makes a projectile render with the given item
     * 
     * @param projectileClass
     */
    private static <T extends Entity> void registerProjectileRenderer(Class<T> projectileClass, Item item)
    {
	RenderingRegistry.registerEntityRenderingHandler(projectileClass, new IRenderFactory<T>()
	{
	    @Override
	    public Render<? super T> createRenderFor(RenderManager manager)
	    {
		return new RenderProjectile(manager, Minecraft.getMinecraft().getRenderItem(), item);
	    }
	});
    }
}
