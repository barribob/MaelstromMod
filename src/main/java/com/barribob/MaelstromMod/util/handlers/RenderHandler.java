package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.entity.entities.EntityAzureGolem;
import com.barribob.MaelstromMod.entity.entities.EntityAzureVillager;
import com.barribob.MaelstromMod.entity.entities.EntityBeast;
import com.barribob.MaelstromMod.entity.entities.EntityCliffFly;
import com.barribob.MaelstromMod.entity.entities.EntityCliffGolem;
import com.barribob.MaelstromMod.entity.entities.EntityDreamElk;
import com.barribob.MaelstromMod.entity.entities.EntityFloatingSkull;
import com.barribob.MaelstromMod.entity.entities.EntityGoldenBoss;
import com.barribob.MaelstromMod.entity.entities.EntityGoldenMage;
import com.barribob.MaelstromMod.entity.entities.EntityGoldenPillar;
import com.barribob.MaelstromMod.entity.entities.EntityGoldenShade;
import com.barribob.MaelstromMod.entity.entities.EntityHerobrineOne;
import com.barribob.MaelstromMod.entity.entities.EntityHorror;
import com.barribob.MaelstromMod.entity.entities.EntityIronShade;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromBeast;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromGoldenBoss;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromIllager;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMage;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromWitch;
import com.barribob.MaelstromMod.entity.entities.EntityMonolith;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.entities.EntitySwampCrawler;
import com.barribob.MaelstromMod.entity.entities.Herobrine;
import com.barribob.MaelstromMod.entity.entities.npc.NexusArmorer;
import com.barribob.MaelstromMod.entity.entities.npc.NexusBladesmith;
import com.barribob.MaelstromMod.entity.entities.npc.NexusGunTrader;
import com.barribob.MaelstromMod.entity.entities.npc.NexusMageTrader;
import com.barribob.MaelstromMod.entity.entities.npc.NexusSpecialTrader;
import com.barribob.MaelstromMod.entity.model.ModelAnimatedBiped;
import com.barribob.MaelstromMod.entity.model.ModelArmorer;
import com.barribob.MaelstromMod.entity.model.ModelBeast;
import com.barribob.MaelstromMod.entity.model.ModelBladesmith;
import com.barribob.MaelstromMod.entity.model.ModelCliffFly;
import com.barribob.MaelstromMod.entity.model.ModelDreamElk;
import com.barribob.MaelstromMod.entity.model.ModelFloatingSkull;
import com.barribob.MaelstromMod.entity.model.ModelGoldenBoss;
import com.barribob.MaelstromMod.entity.model.ModelGoldenPillar;
import com.barribob.MaelstromMod.entity.model.ModelGoldenShade;
import com.barribob.MaelstromMod.entity.model.ModelGunTrader;
import com.barribob.MaelstromMod.entity.model.ModelHorror;
import com.barribob.MaelstromMod.entity.model.ModelIronShade;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromWitch;
import com.barribob.MaelstromMod.entity.model.ModelMageTrader;
import com.barribob.MaelstromMod.entity.model.ModelNexusSaiyan;
import com.barribob.MaelstromMod.entity.model.ModelSwampCrawler;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBone;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBullet;
import com.barribob.MaelstromMod.entity.projectile.ProjectileGoldenBullet;
import com.barribob.MaelstromMod.entity.projectile.ProjectileSwampSpittle;
import com.barribob.MaelstromMod.entity.render.RenderAnimatedBiped;
import com.barribob.MaelstromMod.entity.render.RenderAzureGolem;
import com.barribob.MaelstromMod.entity.render.RenderAzureVillager;
import com.barribob.MaelstromMod.entity.render.RenderHerobrine;
import com.barribob.MaelstromMod.entity.render.RenderMaelstromBeast;
import com.barribob.MaelstromMod.entity.render.RenderMaelstromIllager;
import com.barribob.MaelstromMod.entity.render.RenderModEntity;
import com.barribob.MaelstromMod.entity.render.RenderMonolith;
import com.barribob.MaelstromMod.entity.render.RenderProjectile;
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
	registerModEntityRenderer(EntityShade.class, new ModelGoldenShade(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/azure_shade.png"));
	registerModEntityRenderer(EntityHorror.class, new ModelHorror(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/horror.png"));
	registerModEntityRenderer(EntityDreamElk.class, new ModelDreamElk(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/dream_elk.png"));
	registerModEntityRenderer(EntityBeast.class, new ModelBeast(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/beast.png"));
	registerModEntityRenderer(EntityMaelstromMage.class, new ModelGoldenShade(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/azure_shade.png"));
	registerModEntityRenderer(EntityFloatingSkull.class, new ModelFloatingSkull(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/floating_skull.png"));
	registerHerobrineRenderer(Herobrine.class, new ResourceLocation(Reference.MOD_ID + ":textures/entity/herobrine_1.png"));
	registerHerobrineRenderer(EntityHerobrineOne.class, new ResourceLocation(Reference.MOD_ID + ":textures/entity/shadow_clone.png"));
	registerModEntityRenderer(NexusGunTrader.class, new ModelGunTrader(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/gun_trader.png"));
	registerModEntityRenderer(NexusMageTrader.class, new ModelMageTrader(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/mage_trader.png"));
	registerModEntityRenderer(NexusArmorer.class, new ModelArmorer(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/armorer.png"));
	registerModEntityRenderer(NexusBladesmith.class, new ModelBladesmith(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/bladesmith.png"));
	registerModEntityRenderer(NexusSpecialTrader.class, new ModelNexusSaiyan(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/nexus_saiyan.png"));
	registerModEntityRenderer(EntityGoldenPillar.class, new ModelGoldenPillar(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/golden_pillar.png"));
	registerModEntityRenderer(EntityGoldenShade.class, new ModelGoldenShade(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/golden_shade.png"));
	registerModEntityRenderer(EntityGoldenMage.class, new ModelGoldenShade(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/golden_shade.png"));
	registerModEntityRenderer(EntityGoldenBoss.class, new ModelGoldenBoss(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/golden_boss.png"));
	registerModEntityRenderer(EntityMaelstromGoldenBoss.class, new ModelGoldenBoss(),
		new ResourceLocation(Reference.MOD_ID + ":textures/entity/maelstrom_golden_boss.png"));
	registerModEntityRenderer(EntityMaelstromWitch.class, new ModelMaelstromWitch(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/maelstrom_witch.png"));
	registerModEntityRenderer(EntitySwampCrawler.class, new ModelSwampCrawler(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/swamp_crawler.png"));
	registerModEntityRenderer(EntityIronShade.class, new ModelIronShade(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/iron_shade.png"));
	registerModEntityRenderer(EntityCliffFly.class, new ModelCliffFly(), new ResourceLocation(Reference.MOD_ID + ":textures/entity/cliff_fly.png"));

	registerProjectileRenderer(Projectile.class, ModItems.INVISIBLE);
	registerProjectileRenderer(ProjectileBullet.class, ModItems.IRON_PELLET);
	registerProjectileRenderer(EntityPortalSpawn.class, ModItems.INVISIBLE);
	registerProjectileRenderer(ProjectileGoldenBullet.class, ModItems.GOLD_PELLET);
	registerProjectileRenderer(EntityNexusParticleSpawner.class, ModItems.INVISIBLE);
	registerProjectileRenderer(ProjectileSwampSpittle.class, ModItems.SWAMP_SLIME);
	registerProjectileRenderer(EntityParticleSpawner.class, ModItems.INVISIBLE);
	registerProjectileRenderer(ProjectileBone.class, Items.BONE);

	RenderingRegistry.registerEntityRenderingHandler(EntityAzureVillager.class, new IRenderFactory<EntityAzureVillager>()
	{
	    @Override
	    public Render<? super EntityAzureVillager> createRenderFor(RenderManager manager)
	    {
		return new RenderAzureVillager(manager);
	    }
	});

	RenderingRegistry.registerEntityRenderingHandler(EntityMaelstromIllager.class, new IRenderFactory<EntityMaelstromIllager>()
	{
	    @Override
	    public Render<? super EntityMaelstromIllager> createRenderFor(RenderManager manager)
	    {
		return new RenderMaelstromIllager(manager);
	    }
	});

	RenderingRegistry.registerEntityRenderingHandler(EntityAzureGolem.class, new IRenderFactory<EntityAzureGolem>()
	{
	    @Override
	    public Render<? super EntityAzureGolem> createRenderFor(RenderManager manager)
	    {
		return new RenderAzureGolem(manager, new ResourceLocation(Reference.MOD_ID + ":textures/entity/azure_golem.png"));
	    }
	});

	RenderingRegistry.registerEntityRenderingHandler(EntityCliffGolem.class, new IRenderFactory<EntityCliffGolem>()
	{
	    @Override
	    public Render<? super EntityCliffGolem> createRenderFor(RenderManager manager)
	    {
		return new RenderAzureGolem(manager, new ResourceLocation(Reference.MOD_ID + ":textures/entity/cliff_golem.png"));
	    }
	});

	RenderingRegistry.registerEntityRenderingHandler(EntityMaelstromBeast.class, new IRenderFactory<EntityMaelstromBeast>()
	{
	    @Override
	    public Render<? super EntityMaelstromBeast> createRenderFor(RenderManager manager)
	    {
		return new RenderMaelstromBeast(manager);
	    }
	});

	RenderingRegistry.registerEntityRenderingHandler(EntityMonolith.class, new IRenderFactory<EntityMonolith>()
	{
	    @Override
	    public Render<? super EntityMonolith> createRenderFor(RenderManager manager)
	    {
		return new RenderMonolith(manager);
	    }
	});
    }

    /**
     * Register an entity with herobrine eyes
     */
    private static <T extends Entity, V extends RenderModEntity> void registerHerobrineRenderer(Class<T> entityClass, ResourceLocation textures)
    {
	RenderingRegistry.registerEntityRenderingHandler(entityClass, new IRenderFactory<T>()
	{
	    @Override
	    public Render<? super T> createRenderFor(RenderManager manager)
	    {
		return new RenderHerobrine(manager, textures);
	    }
	});
    }

    /**
     * Registers an entity with a model and sets it up for rendering
     */
    private static <T extends Entity, U extends ModelBase, V extends RenderModEntity> void registerModEntityRenderer(Class<T> entityClass, U model, ResourceLocation textures)
    {
	RenderingRegistry.registerEntityRenderingHandler(entityClass, new IRenderFactory<T>()
	{
	    @Override
	    public Render<? super T> createRenderFor(RenderManager manager)
	    {
		return new RenderModEntity(manager, textures, model);
	    }
	});
    }

    /**
     * Registers a biped to render
     */
    private static <T extends Entity, U extends ModelAnimatedBiped, V extends RenderModEntity> void registerModBipedRenderer(Class<T> entityClass, U model,
	    ResourceLocation textures, float shadowSize)
    {
	RenderingRegistry.registerEntityRenderingHandler(entityClass, new IRenderFactory<T>()
	{
	    @Override
	    public Render<? super T> createRenderFor(RenderManager manager)
	    {
		return new RenderAnimatedBiped(manager, model, shadowSize, textures);
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
