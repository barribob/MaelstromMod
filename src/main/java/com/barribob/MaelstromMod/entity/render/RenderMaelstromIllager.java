package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromIllager;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromIllager;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCreeperCharge;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * Renders illager armor when arms are raised, otherwise it renders the illager
 * model normally
 *
 */
public class RenderMaelstromIllager extends RenderModEntity<EntityMaelstromIllager>
{
    private static final DynamicTexture TEXTURE_BRIGHTNESS = new DynamicTexture(16, 16);

    public RenderMaelstromIllager(RenderManager rendermanagerIn)
    {
	super(rendermanagerIn, "maelstrom_illager.png", new ModelMaelstromIllager());
	this.addLayer(new LayerMaelstromIllagerArmor(this));
    }
    
    /*
     * Same as overriden version, except blue when the blow is blocked
     */
    @Override
    protected boolean setBrightness(EntityMaelstromIllager entity, float partialTicks, boolean combineTextures)
    {
	float f = entity.getBrightness();
	int i = this.getColorMultiplier(entity, f, partialTicks);
	boolean flag = (i >> 24 & 255) > 0;
	boolean flag1 = entity.hurtTime > 0 || entity.deathTime > 0;

	if (!flag && !flag1)
	{
	    return false;
	}
	else if (!flag && !combineTextures)
	{
	    return false;
	}
	else
	{
	    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	    GlStateManager.enableTexture2D();
	    GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
	    GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	    GlStateManager.enableTexture2D();
	    GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
	    this.brightnessBuffer.position(0);

	    if (flag1)
	    {
		if (entity.blockedBlow())
		{
		    this.brightnessBuffer.put(0.3F);
		    this.brightnessBuffer.put(0.3F);
		    this.brightnessBuffer.put(1.0F);
		    this.brightnessBuffer.put(0.3F);
		}
		else
		{
		    this.brightnessBuffer.put(1.0F);
		    this.brightnessBuffer.put(0.0F);
		    this.brightnessBuffer.put(0.0F);
		    this.brightnessBuffer.put(0.3F);
		}
	    }
	    else
	    {
		float f1 = (float) (i >> 24 & 255) / 255.0F;
		float f2 = (float) (i >> 16 & 255) / 255.0F;
		float f3 = (float) (i >> 8 & 255) / 255.0F;
		float f4 = (float) (i & 255) / 255.0F;
		this.brightnessBuffer.put(f2);
		this.brightnessBuffer.put(f3);
		this.brightnessBuffer.put(f4);
		this.brightnessBuffer.put(1.0F - f1);
	    }

	    this.brightnessBuffer.flip();
	    GlStateManager.glTexEnv(8960, 8705, this.brightnessBuffer);
	    GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
	    GlStateManager.enableTexture2D();
	    GlStateManager.bindTexture(TEXTURE_BRIGHTNESS.getGlTextureId());
	    GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
	    GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
	    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	    return true;
	}
    }
}
