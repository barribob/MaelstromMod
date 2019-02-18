package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.model.ModelHorror;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * Renders an entity with a generic type, texture, and model passed in.
 *
 */
public class RenderModEntity<T extends EntityLiving> extends RenderLiving<T>
{
    public ResourceLocation TEXTURES;

    public <U extends ModelBase> RenderModEntity(RenderManager rendermanagerIn, ResourceLocation textures, U modelClass)
    {
	super(rendermanagerIn, modelClass, 0.5f);
	this.TEXTURES = textures;
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity)
    {
	return TEXTURES;
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
	if (!entity.isInvisible())
	{
	    // The blending here allows for rendering of translucent textures
	    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	    GlStateManager.enableNormalize();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
	    super.doRender(entity, x, y, z, entityYaw, partialTicks);
	    GlStateManager.disableBlend();
	    GlStateManager.disableNormalize();
	}
	else
	{
	    super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
    }
}
