package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.projectile.ProjectileShadeAttack;
import com.barribob.MaelstromMod.entity.render.RenderInvisibleProjectile;
import com.barribob.MaelstromMod.entity.render.RenderShade;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler
{
    public static void registerEntityRenderers()
    {
	RenderingRegistry.registerEntityRenderingHandler(EntityShade.class, new IRenderFactory<EntityShade>()
	{
	    @Override
	    public Render<? super EntityShade> createRenderFor(RenderManager manager)
	    {
		return new RenderShade(manager);
	    }
	});

	RenderingRegistry.registerEntityRenderingHandler(ProjectileShadeAttack.class, new IRenderFactory<ProjectileShadeAttack>()
	{
	    @Override
	    public Render<? super ProjectileShadeAttack> createRenderFor(RenderManager manager)
	    {
		return new RenderInvisibleProjectile(manager, Minecraft.getMinecraft().getRenderItem());
	    }
	});
    }
}
