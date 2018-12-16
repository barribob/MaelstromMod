package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.model.ModelShade;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * Renders the shade monster (links the textures, the class, and the model)
 *
 */
public class RenderShade extends RenderLiving<EntityShade>{

	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/entity/shade.png");
	
	public RenderShade(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelShade(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityShade entity) {
		return TEXTURES;
	}

}
