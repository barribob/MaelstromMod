package com.barribob.MaelstromMod.gui;

import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A custom dimension loading screen
 */
@SideOnly(Side.CLIENT)
public class GuiModDownloadTerrain extends GuiDownloadTerrain {
    private ResourceLocation background = new ResourceLocation(Reference.MOD_ID + ":textures/gui/dark_azure_stone.png");
    private String dimension;

    public GuiModDownloadTerrain(ResourceLocation backgroundTile, String dimension) {
        this.background = backgroundTile;
        this.dimension = dimension;
    }

    /**
     * Draws the background (i is always 0 as of 1.2.2)
     */
    public void drawBackground(int tint) {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        this.mc.getTextureManager().bindTexture(background);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        float color = 0.6f;
        float alpha = 1f;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0.0D, (double) height, 0.0D).tex(0.0D, (double) ((float) height / f + (float) tint)).color(color, color, color, alpha).endVertex();
        bufferbuilder.pos((double) width, (double) height, 0.0D).tex((double) ((float) width / f), (double) ((float) height / f + (float) tint)).color(color, color, color, alpha).endVertex();
        bufferbuilder.pos((double) width, 0.0D, 0.0D).tex((double) ((float) width / f), (double) tint).color(color, color, color, alpha).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, 0.0D).tex(0.0D, (double) tint).color(color, color, color, alpha).endVertex();
        tessellator.draw();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawBackground(0);
        this.drawCenteredString(this.fontRenderer, I18n.format("multiplayer.entering"), this.width / 2, this.height / 2 - 50, 16777215);
        this.drawCenteredString(this.fontRenderer, I18n.format("multiplayer." + dimension), this.width / 2, this.height / 2 - 25, 16777215);
    }
}
