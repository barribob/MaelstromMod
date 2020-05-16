package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.items.ItemModElytra;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelElytra;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Taken from {@code LayerElytra}
 */
@SideOnly(Side.CLIENT)
public class LayerModElytra implements LayerRenderer<EntityLivingBase> {
    /** Instance of the player renderer. */
    protected final RenderLivingBase<?> renderPlayer;
    /** The model used by the Elytra. */
    private final ModelElytra modelElytra = new ModelElytra();

    public LayerModElytra(RenderLivingBase<?> renderer) {
	this.renderPlayer = renderer;
    }

    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
	ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

	if (itemstack.getItem() instanceof ItemModElytra) {
	    ResourceLocation elytraTexture = new ResourceLocation(itemstack.getItem().getArmorTexture(itemstack, entitylivingbaseIn, EntityEquipmentSlot.CHEST, ""));

	    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

	    if (entitylivingbaseIn instanceof AbstractClientPlayer) {
		AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer) entitylivingbaseIn;

		if (abstractclientplayer.isPlayerInfoSet() && abstractclientplayer.getLocationElytra() != null) {
		    this.renderPlayer.bindTexture(abstractclientplayer.getLocationElytra());
		}
		else if (abstractclientplayer.hasPlayerInfo() && abstractclientplayer.getLocationCape() != null && abstractclientplayer.isWearing(EnumPlayerModelParts.CAPE)) {
		    this.renderPlayer.bindTexture(abstractclientplayer.getLocationCape());
		}
		else {
		    this.renderPlayer.bindTexture(elytraTexture);
		}
	    }
	    else {
		this.renderPlayer.bindTexture(elytraTexture);
	    }

	    GlStateManager.pushMatrix();
	    GlStateManager.translate(0.0F, 0.0F, 0.125F);
	    this.modelElytra.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
	    this.modelElytra.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

	    if (itemstack.isItemEnchanted()) {
		LayerArmorBase.renderEnchantedGlint(this.renderPlayer, entitylivingbaseIn, this.modelElytra, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
	    }

	    GlStateManager.disableBlend();
	    GlStateManager.popMatrix();
	}
    }

    @Override
    public boolean shouldCombineTextures() {
	return false;
    }
}