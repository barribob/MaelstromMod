package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityChaosKnight;
import com.barribob.MaelstromMod.entity.model.ModelChaosKnight;
import com.barribob.MaelstromMod.entity.model.ModelChaosShield;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class RenderChaosKnight extends RenderModEntity<EntityChaosKnight>
{
    private static final DynamicTexture TEXTURE_BRIGHTNESS = new DynamicTexture(16, 16);

    public RenderChaosKnight(RenderManager rendermanagerIn, String... textures)
    {
	super(rendermanagerIn, new ModelChaosKnight(), textures);
	this.addLayer(new LayerShield(new ModelChaosShield()));
    }

    private class LayerShield implements LayerRenderer<EntityChaosKnight>
    {
	private ModelChaosShield shield;
	private final ResourceLocation SHIELD = new ResourceLocation(Reference.MOD_ID + ":textures/entity/chaos_shield.png");

	public LayerShield(ModelChaosShield shield)
	{
	    this.shield = shield;
	}

	@Override
	public void doRenderLayer(EntityChaosKnight knight, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
	    if (!knight.isInvisible() && !knight.isSwingingArms())
	    {
		bindTexture(SHIELD);
		GlStateManager.matrixMode(5890);
		GlStateManager.loadIdentity();
		float f = knight.ticksExisted + partialTicks;
		GlStateManager.translate(0.0f, f * 0.02f, 0.0F);
		GlStateManager.matrixMode(5888);
		GlStateManager.enableBlend();
		GlStateManager.color(1, 1, 1, 0.3F);
		GlStateManager.disableLighting();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
		GlStateManager.pushMatrix();
		GlStateManager.scale(1.1, 1.1, 1.0);
		GlStateManager.translate(0.15f, -0.5f, -0.1F);
		shield.render(knight, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(5890);
		GlStateManager.loadIdentity();
		GlStateManager.matrixMode(5888);
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
	    }
	}

	@Override
	public boolean shouldCombineTextures()
	{
	    return false;
	}

    }
}
