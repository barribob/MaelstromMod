package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityWhiteMonolith;
import com.barribob.MaelstromMod.entity.model.ModelMonolith;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.teleporter.NexusToOverworldTeleporter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class RenderWhiteMonolith extends RenderLiving<EntityWhiteMonolith> {
    public ResourceLocation MONOLITH = new ResourceLocation(Reference.MOD_ID + ":textures/entity/monolith_white.png");
    public static final ResourceLocation TEXTURE_BEACON_BEAM = new ResourceLocation(Reference.MOD_ID + ":textures/entity/monolith_beam.png");
    public static final ResourceLocation MONOLITH_DISINTEGRATE = new ResourceLocation(Reference.MOD_ID + ":textures/entity/monolith_disintegrate.png");

    public RenderWhiteMonolith(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelMonolith(), 1);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityWhiteMonolith entity) {
        return MONOLITH;
    }

    @Override
    protected void renderModel(EntityWhiteMonolith entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
                               float scaleFactor) {
        if (entitylivingbaseIn.ticksExisted > 0) {
            float f = entitylivingbaseIn.ticksExisted / (float) EntityWhiteMonolith.DEATH_TIME;
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1 - f);
        }
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        GlStateManager.disableBlend();
    }

    @Override
    public void doRender(EntityWhiteMonolith entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.pushMatrix();
        // GlStateManager.disableFog();
        GlStateManager.alphaFunc(516, 0.1F);
        this.bindTexture(TEXTURE_BEACON_BEAM);
        int beamHeight = NexusToOverworldTeleporter.yPortalOffset - (int) y;
        TileEntityBeaconRenderer.renderBeamSegment(x - 0.5, y, z - 0.5, partialTicks, 1.0f, entity.world.getTotalWorldTime(), 0, beamHeight,
                EnumDyeColor.WHITE.getColorComponentValues(), 0.5f, 0.75f);
        // GlStateManager.enableFog();
        GlStateManager.popMatrix();
    }
}
