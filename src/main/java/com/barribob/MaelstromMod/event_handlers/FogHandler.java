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

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class FogHandler
{
    @SideOnly(Side.CLIENT)
    private static net.minecraftforge.client.IRenderHandler fogRenderer = new CliffCloudRenderer();

    /**
     * Altering the fog density through the render fog event because the fog density
     * event is a pain because you have to override it for some reason
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent()
    public static void onFogDensityRender(EntityViewRenderEvent.RenderFogEvent event)
    {
	if (event.getEntity().dimension == ModConfig.world.fracture_dimension_id)
	{
	    int fogStartY = 70;
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
	else if (event.getEntity().dimension == ModConfig.world.cliff_dimension_id)
	{
	    if (event.getEntity().posY < CliffCloudRenderer.FOG_HEIGHT)
	    {
		GlStateManager.setFog(GlStateManager.FogMode.EXP);
		GlStateManager.setFogDensity(0.07f);
	    }
	    else
	    {
		int fogStartY = 240;
		float maxFog = 0.025f;
		float fogDensity = 0.005f;

		if (event.getEntity().posY > fogStartY && event.getEntity().posY < 260)
		{
		    fogDensity += (float) (event.getEntity().posY - fogStartY) / fogStartY;
		}

		GlStateManager.setFog(GlStateManager.FogMode.EXP);
		GlStateManager.setFogDensity(fogDensity);
	    }
	}
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent()
    public static void onFogColor(EntityViewRenderEvent.FogColors event)
    {
	if (event.getEntity().dimension == ModConfig.world.cliff_dimension_id && event.getEntity().posY > 240)
	{
	    float fadeY = 4;
	    float alpha = (float) Math.min((event.getEntity().posY - 240) / fadeY, 1);
	    float one_minus_alpha = 1 - alpha;
	    event.setBlue(0.3f * alpha + event.getBlue() * one_minus_alpha);
	    event.setRed(0.3f * alpha + event.getRed() * one_minus_alpha);
	    event.setGreen(0.27f * alpha + event.getGreen() * one_minus_alpha);
	}
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent()
    public static void onRenderWorldLastEvent(RenderWorldLastEvent event)
    {
	if (Minecraft.getMinecraft().getRenderViewEntity().dimension == ModConfig.world.cliff_dimension_id)
	{
	    fogRenderer.render(event.getPartialTicks(), Minecraft.getMinecraft().world, Minecraft.getMinecraft());
	}
    }
}
