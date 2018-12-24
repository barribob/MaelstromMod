package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.model.ModelShade;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * Renders the shade monster (links the textures, the class, and the model)
 *
 */
public class RenderShade extends RenderLiving<EntityShade>
{

    public static final ResourceLocation TEXTURES = new ResourceLocation(
	    Reference.MOD_ID + ":textures/entity/shade.png");

    public RenderShade(RenderManager rendermanagerIn)
    {
	super(rendermanagerIn, new ModelShade(), 0.5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityShade entity)
    {
	return TEXTURES;
    }

    @Override
    public void doRender(EntityShade entity, double x, double y, double z, float entityYaw, float partialTicks)
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
