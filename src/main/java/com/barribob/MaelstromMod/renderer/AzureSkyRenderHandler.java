package com.barribob.MaelstromMod.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.client.IRenderHandler;

public class AzureSkyRenderHandler extends IRenderHandler {
    /**
     * The class that FML accepts to render the sky
     */
    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        AzureSkyRenderer renderer = new AzureSkyRenderer(mc, world);
        renderer.renderSky(partialTicks, 2);
    }
}
