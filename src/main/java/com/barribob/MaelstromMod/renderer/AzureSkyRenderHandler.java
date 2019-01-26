package com.barribob.MaelstromMod.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.IRenderHandler;

public class AzureSkyRenderHandler extends IRenderHandler
{
    /**
     * The class that FML accepts to render the sky
     */
    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc)
    {
	AzureSkyRenderer renderer = new AzureSkyRenderer(mc, world);
	renderer.renderSky(partialTicks, 2);
    }
}
