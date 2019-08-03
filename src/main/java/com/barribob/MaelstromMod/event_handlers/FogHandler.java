package com.barribob.MaelstromMod.event_handlers;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.renderer.CliffCloudRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber()
public class FogHandler
{
    private static net.minecraftforge.client.IRenderHandler fogRenderer = new CliffCloudRenderer();

    /**
     * Altering the fog density through the render fog event because the fog density
     * event is a pain because you have to override it for some reason
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent()
    public static void onFogDensityRender(EntityViewRenderEvent.RenderFogEvent event)
    {
	if (event.getEntity().dimension == ModConfig.fracture_dimension_id)
	{
	    int fogStartY = 60;
	    float maxFog = 0.085f;
	    float fogDensity = 0.005f;

	    /**
	     * alters fog based on the height of the player
	     */
	    if (event.getEntity().posY < fogStartY)
	    {
		fogDensity += (float) (fogStartY - event.getEntity().posY) * (maxFog / fogStartY);
	    }

	    GlStateManager.setFog(GlStateManager.FogMode.EXP);
	    GlStateManager.setFogDensity(fogDensity);
	}
	else if (event.getEntity().dimension == ModConfig.cliff_dimension_id)
	{
	    if (event.getEntity().posY < CliffCloudRenderer.FOG_HEIGHT)
	    {
		GlStateManager.setFog(GlStateManager.FogMode.EXP);
		GlStateManager.setFogDensity(0.07f);
	    }
	}
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent()
    public static void onRenderWorldLastEvent(RenderWorldLastEvent event)
    {
	if (Minecraft.getMinecraft().getRenderViewEntity().dimension == ModConfig.cliff_dimension_id)
	{
	    fogRenderer.render(event.getPartialTicks(), Minecraft.getMinecraft().world, Minecraft.getMinecraft());
	}
    }
}
