package com.barribob.MaelstromMod.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class RenderUtils
{
    /*
     * Draws an element above the entity kind of like a name tag
     */
    public static void drawElement(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch,
	    boolean isThirdPersonFrontal, boolean isSneaking)
    {
	GlStateManager.pushMatrix();
	GlStateManager.translate(x, y, z);
	GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
	GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
	GlStateManager.rotate((isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0F, 0.0F, 0.0F);
	GlStateManager.scale(-0.025F, -0.025F, 0.025F);
	GlStateManager.disableLighting();
	fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, isSneaking ? 553648127 : -1);
	GlStateManager.enableLighting();
	GlStateManager.disableBlend();
	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	GlStateManager.popMatrix();
    }
}
