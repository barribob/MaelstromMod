package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.model.ModelAnimatedBiped;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderHerobrine extends RenderAnimatedBiped
{
    public RenderHerobrine(RenderManager renderManagerIn, ResourceLocation resourceLocation)
    {
	super(renderManagerIn, new ModelAnimatedBiped(), 0.5f, resourceLocation);
	this.addLayer(new LayerHerobrineEyes());
    }

    @Override
    public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks)
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

    private class LayerHerobrineEyes implements LayerRenderer<EntityLeveledMob>
    {
	private final ResourceLocation EYES = new ResourceLocation(Reference.MOD_ID + ":textures/entity/herobrine_eyes.png");

	@Override
	public void doRenderLayer(EntityLeveledMob entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
		float headPitch, float scale)
	{
	    boolean flag = entitylivingbaseIn.isInvisible();
	    GlStateManager.depthMask(!flag);
	    bindTexture(EYES);
	    GlStateManager.matrixMode(5890);
	    GlStateManager.loadIdentity();
	    float f = entitylivingbaseIn.ticksExisted + partialTicks;
	    GlStateManager.matrixMode(5888);
	    GlStateManager.enableBlend();
	    float f1 = 1.0F;
	    GlStateManager.color(f1, f1, f1, 1.0F);
	    GlStateManager.disableLighting();
	    GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
	    Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
	    GlStateManager.pushMatrix();
	    GlStateManager.scale(1.0, 1.0, 1.02);
	    getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	    GlStateManager.popMatrix();
	    Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
	    GlStateManager.matrixMode(5890);
	    GlStateManager.loadIdentity();
	    GlStateManager.matrixMode(5888);
	    GlStateManager.enableLighting();
	    GlStateManager.disableBlend();
	    GlStateManager.depthMask(flag);
	}

	@Override
	public boolean shouldCombineTextures()
	{
	    return false;
	}
    }
}
