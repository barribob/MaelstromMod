package com.barribob.MaelstromMod.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;

public class DarkNexusSkyRenderHandler extends IRenderHandler
{
    private static final ResourceLocation END_SKY_TEXTURES = new ResourceLocation("textures/environment/end_sky.png");

    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc)
    {
	TextureManager renderEngine = mc.getTextureManager();
	GlStateManager.disableFog();
	GlStateManager.disableAlpha();
	GlStateManager.enableBlend();
	GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
		GlStateManager.DestFactor.ZERO);
	RenderHelper.disableStandardItemLighting();
	GlStateManager.depthMask(false);
	renderEngine.bindTexture(END_SKY_TEXTURES);
	Tessellator tessellator = Tessellator.getInstance();
	BufferBuilder bufferbuilder = tessellator.getBuffer();

	for (int k1 = 0; k1 < 6; ++k1)
	{
	    GlStateManager.pushMatrix();

	    if (k1 == 1)
	    {
		GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
	    }

	    if (k1 == 2)
	    {
		GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
	    }

	    if (k1 == 3)
	    {
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
	    }

	    if (k1 == 4)
	    {
		GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
	    }

	    if (k1 == 5)
	    {
		GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
	    }

	    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
	    bufferbuilder.pos(-100.0D, -100.0D, -100.0D).tex(0.0D, 0.0D).color(40, 40, 40, 255).endVertex();
	    bufferbuilder.pos(-100.0D, -100.0D, 100.0D).tex(0.0D, 16.0D).color(40, 40, 40, 255).endVertex();
	    bufferbuilder.pos(100.0D, -100.0D, 100.0D).tex(16.0D, 16.0D).color(40, 40, 40, 255).endVertex();
	    bufferbuilder.pos(100.0D, -100.0D, -100.0D).tex(16.0D, 0.0D).color(40, 40, 40, 255).endVertex();
	    tessellator.draw();
	    GlStateManager.popMatrix();
	}

	GlStateManager.depthMask(true);
	GlStateManager.enableTexture2D();
	GlStateManager.enableAlpha();
    }
}
