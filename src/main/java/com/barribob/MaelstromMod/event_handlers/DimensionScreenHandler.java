package com.barribob.MaelstromMod.event_handlers;

import com.barribob.MaelstromMod.gui.GuiModDownloadTerrain;
import com.barribob.MaelstromMod.init.ModDimensions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber()
public class DimensionScreenHandler
{  
    /**
     * Adds a new loading screen (credit to twilight forest mod for this idea) 
     * https://github.com/TeamTwilight/twilightforest/blob/1.12.x/src/main/java/twilightforest/client/LoadingScreenListener.java
     */
    @SubscribeEvent
    public static void onGuiOpenEvent(GuiOpenEvent event)
    {
	Minecraft mc = FMLClientHandler.instance().getClient();
	if(event.getGui() instanceof GuiDownloadTerrain && mc.player != null && mc.player.dimension == ModDimensions.DIMENSION_AZURE_ID)
	{
	    event.setGui(new GuiModDownloadTerrain());
	}
    }
}
