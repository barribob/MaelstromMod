package com.barribob.MaelstromMod.event_handlers;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.renderer.CliffCloudRenderer;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Method;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class FogHandler {
    public static float CLIFF_FOG_HEIGHT = 45.55f;
    public static final int SWAMP_FOG_LAYERS = 8;
    public static final int SWAMP_FOG_FADE_START = 5;
    private static final float CLOUD_FOG_HEIGHT = 239.25f;

    private static net.minecraftforge.client.IRenderHandler swampFogRenderer = new CliffCloudRenderer();

    private static Method setupFog;

    /**
     * Altering the fog density through the render fog event because the fog density
     * event is a pain because you have to override it for some reason
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent()
    public static void onFogDensityRender(EntityViewRenderEvent.RenderFogEvent event) {
        if (event.getEntity().dimension == ModConfig.world.fracture_dimension_id) {
            int fogStartY = 70;
            float maxFog = 0.085f;
            float fogDensity = 0.005f;

            /**
             * alters fog based on the height of the player
             */
            if (event.getEntity().posY < fogStartY) {
                fogDensity += (float) (fogStartY - event.getEntity().posY) * (maxFog / fogStartY);
            }

            GlStateManager.setFog(GlStateManager.FogMode.EXP);
            GlStateManager.setFogDensity(fogDensity);
        } else if (event.getEntity().dimension == ModConfig.world.cliff_dimension_id) {
            double posY = event.getEntity().getPositionEyes((float) event.getRenderPartialTicks()).y;
            if (posY < CLIFF_FOG_HEIGHT + SWAMP_FOG_LAYERS + SWAMP_FOG_FADE_START) {
                double maxFogThickness = 0.07f;
                double minFogThickness = 0.005f;
                double distanceFromMax = posY - CLIFF_FOG_HEIGHT;
                double closenessToMax = distanceFromMax / (SWAMP_FOG_LAYERS + SWAMP_FOG_FADE_START);
                double fogThickness = maxFogThickness * MathHelper.clamp(1 - closenessToMax, 0, 1);
                GlStateManager.setFog(GlStateManager.FogMode.EXP);
                GlStateManager.setFogDensity((float) Math.max(fogThickness, minFogThickness));
            } else {
                float fogStartY = CLOUD_FOG_HEIGHT;
                float maxFogY = 2;
                float maxFog = 0.045f;
                float fogDensity = 0.005f;

                if (event.getEntity().posY > fogStartY) {
                    fogDensity += Math.min(1, Math.max(0, event.getEntity().posY - fogStartY) / maxFogY) * maxFog;
                }

                GlStateManager.setFog(GlStateManager.FogMode.EXP);
                GlStateManager.setFogDensity(fogDensity);
            }
        } else if (event.getEntity().dimension == ModConfig.world.dark_nexus_dimension_id) {
            GlStateManager.setFog(GlStateManager.FogMode.EXP);
            GlStateManager.setFogDensity(0.07f);
        }
    }

    private static Vec3d interpolateFogColor(Entity renderEntity, Vec3d fog1, Vec3d fog2, float transitionStart, float transitionLength) {
        float alpha = ModUtils.clamp((renderEntity.posY - transitionStart) / transitionLength, 0, 1);
        return fog1.scale(1 - alpha).add(fog2.scale(alpha));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent()
    public static void onFogColor(EntityViewRenderEvent.FogColors event) {
        if (event.getEntity().dimension == ModConfig.world.cliff_dimension_id) {
            Vec3d originalColor = new Vec3d(event.getRed(), event.getGreen(), event.getBlue());
            Vec3d cloudColor = new Vec3d(0.5, 0.43, 0.5);
            Vec3d color = interpolateFogColor(event.getEntity(), originalColor, cloudColor.scale(Math.sqrt(originalColor.lengthSquared() / cloudColor.lengthSquared())), CLOUD_FOG_HEIGHT, 2);
            Vec3d color2 = interpolateFogColor(event.getEntity(), ModColors.SWAMP_FOG.scale(Math.sqrt(color.lengthSquared() / ModColors.SWAMP_FOG.lengthSquared())), color, CLIFF_FOG_HEIGHT, 1);
            event.setRed((float) color2.x);
            event.setGreen((float) color2.y);
            event.setBlue((float) color2.z);
        }

        if (event.getEntity().dimension == ModConfig.world.dark_nexus_dimension_id) {
            event.setBlue(0);
            event.setRed(0);
            event.setGreen(0);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent()
    public static void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        if (ModConfig.shaders.render_fog) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.getRenderViewEntity().dimension == ModConfig.world.cliff_dimension_id) {
                if (setupFog == null) {
                    try {
                        setupFog = ReflectionHelper.findMethod(EntityRenderer.class, "setupFog", "func_78468_a", int.class, float.class);
                        setupFog.setAccessible(true);
                    } catch (Exception e) {
                        System.out.println("Failed to render fog: " + e);
                    }
                }

                if (setupFog != null) {
                    try {
                        if (mc.getRenderViewEntity().posY > CLIFF_FOG_HEIGHT) {
                            setupFog.invoke(mc.entityRenderer, 0, event.getPartialTicks());
                            swampFogRenderer.render(event.getPartialTicks(), Minecraft.getMinecraft().world, Minecraft.getMinecraft());
                            GlStateManager.disableFog();
                        }
                    } catch (Exception e) {
                        System.out.println("Failed to render fog: " + e);
                        GlStateManager.disableFog();
                    }
                }
            }
        }
    }
}
