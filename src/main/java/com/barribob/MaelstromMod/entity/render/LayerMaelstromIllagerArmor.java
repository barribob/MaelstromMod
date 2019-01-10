package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromIllager;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromIllager;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * Similar to the creeper charged layer: renders an aura around the illager when summoning
 *
 */
@SideOnly(Side.CLIENT)
public class LayerMaelstromIllagerArmor implements LayerRenderer<EntityMaelstromIllager>
{
    private static final ResourceLocation ARMOR_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/entity/illager_armor.png");
    private final RenderMaelstromIllager render;
    private final ModelMaelstromIllager model = new ModelMaelstromIllager();

    public LayerMaelstromIllagerArmor(RenderMaelstromIllager render)
    {
        this.render = render;
    }

    public void doRenderLayer(EntityMaelstromIllager entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entitylivingbaseIn.isSwingingArms())
        {
            boolean flag = entitylivingbaseIn.isInvisible();
            GlStateManager.depthMask(!flag);
            this.render.bindTexture(ARMOR_TEXTURE);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f = (float)entitylivingbaseIn.ticksExisted + partialTicks;
            GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float f1 = 0.8F;
            GlStateManager.color(f1, f1, f1, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            this.model.setModelAttributes(this.render.getMainModel());
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.model.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale * 1.1f);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(flag);
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}