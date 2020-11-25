package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityGoldenBoss;
import com.barribob.MaelstromMod.entity.model.ModelStatueOfNirvana;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderStatueOfNirvana extends RenderModEntity<EntityGoldenBoss> {
    private final ResourceLocation STATUE = new ResourceLocation(Reference.MOD_ID, "textures/entity/statue.png");
    private final ResourceLocation STATUE_ATTACKING = new ResourceLocation(Reference.MOD_ID, "textures/entity/statue_attacking.png");
    private final ResourceLocation DAMAGED_STATUE = new ResourceLocation(Reference.MOD_ID, "textures/entity/statue_damaged.png");
    private final ResourceLocation DAMAGED_STATUE_ATTACKING = new ResourceLocation(Reference.MOD_ID, "textures/entity/statue_damaged_attacking.png");

    public RenderStatueOfNirvana(RenderManager rendermanagerIn) {
        super(rendermanagerIn, "statue.png", new ModelStatueOfNirvana());
        this.addLayer(new LayerStatueOfNirvanaArmor(this));
        this.addLayer(new LayerEyes());
    }

    private class LayerEyes implements LayerRenderer<EntityGoldenBoss> {
        private final ResourceLocation EYES = new ResourceLocation(Reference.MOD_ID, "textures/entity/statue_eyes.png");
        private final ResourceLocation DAMAGED_EYES = new ResourceLocation(Reference.MOD_ID, "textures/entity/statue_damaged_eye.png");

        public void doRenderLayer(EntityGoldenBoss entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            if (entity.getAttackCount() != 0) {
                RenderStatueOfNirvana.this.bindTexture(entity.isSecondPhase() ? DAMAGED_EYES : EYES);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

                GlStateManager.depthMask(!entity.isInvisible());

                int i = 61680;
                int j = i % 65536;
                int k = 0;
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
                GlStateManager.scale(1.02, 1.0, 1.02);
                getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
                i = entity.getBrightnessForRender();
                j = i % 65536;
                k = i / 65536;
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
                setLightmap(entity);
                GlStateManager.disableBlend();
            }
        }

        public boolean shouldCombineTextures() {
            return false;
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityGoldenBoss entity) {
        if (entity.getAttackCount() == 0) {
            return entity.isSecondPhase() ? DAMAGED_STATUE : STATUE;
        }
        return entity.isSecondPhase() ? DAMAGED_STATUE_ATTACKING : STATUE_ATTACKING;
    }
}
