package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromIllager;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Similar to the creeper charged layer: renders an aura around the illager when summoning
 */
@SideOnly(Side.CLIENT)
public class LayerMaelstromIllagerArmor implements LayerRenderer<EntityMaelstromIllager> {
    private static final ResourceLocation ARMOR_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/entity/illager_armor.png");
    private final RenderMaelstromIllager render;

    public LayerMaelstromIllagerArmor(RenderMaelstromIllager render) {
        this.render = render;
    }

    @Override
    public void doRenderLayer(EntityMaelstromIllager entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!entitylivingbaseIn.isSwingingArms()) {
            float f1 = 0.8F;
            this.render.bindTexture(ARMOR_TEXTURE);
            RenderUtils.renderAura(entitylivingbaseIn, () -> {
                float ticks = entitylivingbaseIn.ticksExisted + partialTicks;
                GlStateManager.translate(ticks * 0.01F, ticks * 0.01F, 0.0F);
                GlStateManager.color(f1, f1, f1, 1.0F);
            }, () -> {
                Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
                GlStateManager.scale(1.02, 1.02, 1.02);
                this.render.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            });
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}