package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.render.RenderShade;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
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
	}
}
