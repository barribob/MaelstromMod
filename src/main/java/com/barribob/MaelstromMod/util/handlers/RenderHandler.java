package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.entity.entities.EntityBeast;
import com.barribob.MaelstromMod.entity.entities.EntityDreamElk;
import com.barribob.MaelstromMod.entity.entities.EntityHorror;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromIllager;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.model.ModelBeast;
import com.barribob.MaelstromMod.entity.model.ModelDreamElk;
import com.barribob.MaelstromMod.entity.model.ModelHorror;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromIllager;
import com.barribob.MaelstromMod.entity.model.ModelShade;
import com.barribob.MaelstromMod.entity.projectile.ProjectileBeastAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHorrorAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileShadeAttack;
import com.barribob.MaelstromMod.entity.render.RenderInvisibleProjectile;
import com.barribob.MaelstromMod.entity.render.RenderModEntity;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler
{
    public static void registerEntityRenderers()
    {
	registerModEntityRenderer(EntityShade.class, ModelShade.class, new ResourceLocation(Reference.MOD_ID + ":textures/entity/shade.png"));
	registerModEntityRenderer(EntityHorror.class, ModelHorror.class, new ResourceLocation(Reference.MOD_ID + ":textures/entity/horror.png"));
	registerModEntityRenderer(EntityDreamElk.class, ModelDreamElk.class, new ResourceLocation(Reference.MOD_ID + ":textures/entity/dream_elk.png"));
	registerModEntityRenderer(EntityBeast.class, ModelBeast.class, new ResourceLocation(Reference.MOD_ID + ":textures/entity/beast.png"));
	registerModEntityRenderer(EntityMaelstromIllager.class, ModelMaelstromIllager.class, new ResourceLocation(Reference.MOD_ID + ":textures/entity/maelstrom_illager.png"));

	registerInvisibleProjectileRenderer(ProjectileShadeAttack.class);
	registerInvisibleProjectileRenderer(ProjectileHorrorAttack.class);
	registerInvisibleProjectileRenderer(ProjectileBeastAttack.class);
    }

    /*
     * Registers an entity with a model and sets it up for rendering
     */
    private static <T extends EntityLiving, U extends ModelBase> void registerModEntityRenderer(Class<T> entityClass, Class<U> modelClass,
	    ResourceLocation textures)
    {
	RenderingRegistry.registerEntityRenderingHandler(entityClass, new IRenderFactory<T>()
	{
	    @Override
	    public Render<? super T> createRenderFor(RenderManager manager)
	    {
		try
		{
		    return new RenderModEntity(manager, textures, modelClass);
		}
		catch (InstantiationException e)
		{
		    e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
		    e.printStackTrace();
		}
		return null;
	    }
	});
    }

    /**
     * Makes a projectile render invisibly
     * 
     * @param projectileClass
     */
    private static <T extends EntityThrowable> void registerInvisibleProjectileRenderer(Class<T> projectileClass)
    {
	RenderingRegistry.registerEntityRenderingHandler(projectileClass, new IRenderFactory<T>()
	{
	    @Override
	    public Render<? super T> createRenderFor(RenderManager manager)
	    {
		return new RenderInvisibleProjectile(manager, Minecraft.getMinecraft().getRenderItem());
	    }
	});
    }
}
