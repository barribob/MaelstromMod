package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.init.DimensionInit;

import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber()
public class ModEventHandler
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onFogDensityRender(EntityViewRenderEvent.FogDensity event)
    {
//	if (event.getEntity().dimension == DimensionInit.DIMENSION_AZURE_ID)
//	{
//	    int fogStartY = 60;
//	    float maxFog = 0.1f;
//	    float fogDensity = 0.01f;
//
//	    /**
//	     * alters fog based on the height of the player
//	     */
//	    if (event.getEntity().posY < fogStartY)
//	    {
//		fogDensity += (float) (fogStartY - event.getEntity().posY) * (maxFog / fogStartY);
//	    }
//
//	    event.setDensity(fogDensity);
//	    event.setCanceled(true);
//	}
    }
}
