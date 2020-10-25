package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.gauntlet.EntityCrimsonCrystal;
import com.barribob.MaelstromMod.entity.model.ModelCrimsonCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.Vec3d;

public class RenderCrimsonCrystal extends RenderNonLivingEntity<EntityCrimsonCrystal> {
    public RenderCrimsonCrystal(RenderManager renderManagerIn, String texture, float yRenderOffset) {
        super(renderManagerIn, texture, new ModelCrimsonCrystal(), yRenderOffset);
    }

    @Override
    protected void renderModel(EntityCrimsonCrystal entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200, 0);
        Vec3d particleColor = entity.getParticleColor();
        GlStateManager.color((float) particleColor.x, (float) particleColor.y, (float) particleColor.z, 1.0F);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        GlStateManager.scale(0.05, 0.05, 0.05);
        mainModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, 1);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }
}
