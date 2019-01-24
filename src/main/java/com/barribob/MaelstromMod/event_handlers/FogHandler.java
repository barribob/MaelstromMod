package com.barribob.MaelstromMod.event_handlers;

import org.lwjgl.opengl.GLContext;

import com.barribob.MaelstromMod.config.ModConfig;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber()
public class FogHandler
{
    /**
     * Altering the fog density through the render fog event because the fog density event is a pain because
     * you have to override it for some reason
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent()
    public static void onFogDensityRender(EntityViewRenderEvent.RenderFogEvent event)
    {
	if (event.getEntity().dimension == ModConfig.fracture_dimension_id)
	{
	    int fogStartY = 60;
	    float maxFog = 0.1f;
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
    }
}
