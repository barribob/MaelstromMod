package com.barribob.MaelstromMod.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class RenderUtils
{
    private static final ResourceLocation GUARDIAN_BEAM_TEXTURE = new ResourceLocation("textures/entity/guardian_beam.png");

    /*
     * Draws an element above the entity kind of like a name tag
     */
    public static void drawElement(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch,
	    boolean isThirdPersonFrontal, boolean isSneaking, int ticks, float partialTicks, float scale)
    {
	GlStateManager.pushMatrix();
	GlStateManager.translate(x, y, z);
	GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
	GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
	GlStateManager.rotate((isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0F, 0.0F, 0.0F);
	GlStateManager.scale(-0.025F * scale, -0.025F * scale, 0.025F * scale);
	GlStateManager.disableLighting();
	GlStateManager.enableAlpha();
	GlStateManager.enableBlend();
	GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
	// Oscillating color
	float alpha = (float) ((Math.sin((ticks + partialTicks) * 0.1) * 0.15f) + 0.8f);
	fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, ModColors.toIntegerColor(255, 255, 255, (int) (alpha * 255)));
	GlStateManager.enableLighting();
	GlStateManager.disableBlend();
	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	GlStateManager.popMatrix();
    }

    public static void drawLazer(RenderManager renderManager, Vec3d startPos, Vec3d endPos, Vec3d offset, Vec3d color, EntityLiving entity, float partialTicks) {
	renderManager.renderEngine.bindTexture(GUARDIAN_BEAM_TEXTURE);
	drawBeam(renderManager, startPos, endPos, offset, color, entity, partialTicks);
    }

    /**
     * Draws a beam, requires a bound texture
     * 
     * @param renderManager
     * @param pos1
     * @param pos2
     * @param offset
     * @param color
     * @param entity
     * @param partialTicks
     */
    public static void drawBeam(RenderManager renderManager, Vec3d startPos, Vec3d endPos, Vec3d offset, Vec3d color, Entity entity, float partialTicks) {
	drawBeam(renderManager, startPos, endPos, offset, color, entity, partialTicks, new Vec3d(1, 1, 1));
    }

    public static void drawBeam(RenderManager renderManager, Vec3d startPos, Vec3d endPos, Vec3d offset, Vec3d color, Entity entity, float partialTicks, Vec3d scale) {
	Tessellator tessellator = Tessellator.getInstance();
	BufferBuilder bufferbuilder = tessellator.getBuffer();
	GlStateManager.glTexParameteri(3553, 10242, 10497);
	GlStateManager.glTexParameteri(3553, 10243, 10497);
	GlStateManager.disableLighting();
	GlStateManager.disableCull();
	GlStateManager.disableBlend();
	GlStateManager.depthMask(true);
	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
	GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

	float time = entity.world.getTotalWorldTime() + partialTicks;
	float f3 = time * 0.5F % 1.0F;
	double timeLooped = time * 0.05D * -1.5D;

	GlStateManager.pushMatrix();
	GlStateManager.translate((float) offset.x, (float) offset.y + entity.getEyeHeight(), (float) offset.z);

	Vec3d line = endPos.subtract(startPos);
	double lineLength = line.lengthVector();
	Vec3d lineDir = line.normalize();

	float angle1 = (float) Math.acos(lineDir.y);
	float angle2 = (float) Math.atan2(lineDir.z, lineDir.x);

	GlStateManager.rotate((float) Math.toDegrees((Math.PI / 2F + -angle2)), 0.0F, 1.0F, 0.0F);
	GlStateManager.rotate((float) Math.toDegrees(angle1), 1.0F, 0.0F, 0.0F);
	GlStateManager.scale(scale.x, scale.y, scale.z);

	bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

	int red = (int) (color.x * 255);
	int green = (int) (color.y * 255);
	int blue = (int) (color.z * 255);

	double d4 = 0.0D + Math.cos(timeLooped + 2.356194490192345D) * 0.282D;
	double d5 = 0.0D + Math.sin(timeLooped + 2.356194490192345D) * 0.282D;
	double d6 = 0.0D + Math.cos(timeLooped + (Math.PI / 4D)) * 0.282D;
	double d7 = 0.0D + Math.sin(timeLooped + (Math.PI / 4D)) * 0.282D;
	double d8 = 0.0D + Math.cos(timeLooped + 3.9269908169872414D) * 0.282D;
	double d9 = 0.0D + Math.sin(timeLooped + 3.9269908169872414D) * 0.282D;
	double d10 = 0.0D + Math.cos(timeLooped + 5.497787143782138D) * 0.282D;
	double d11 = 0.0D + Math.sin(timeLooped + 5.497787143782138D) * 0.282D;
	double d12 = 0.0D + Math.cos(timeLooped + Math.PI) * 0.2D;
	double d13 = 0.0D + Math.sin(timeLooped + Math.PI) * 0.2D;
	double d14 = 0.0D + Math.cos(timeLooped + 0.0D) * 0.2D;
	double d15 = 0.0D + Math.sin(timeLooped + 0.0D) * 0.2D;
	double d16 = 0.0D + Math.cos(timeLooped + (Math.PI / 2D)) * 0.2D;
	double d17 = 0.0D + Math.sin(timeLooped + (Math.PI / 2D)) * 0.2D;
	double d18 = 0.0D + Math.cos(timeLooped + (Math.PI * 3D / 2D)) * 0.2D;
	double d19 = 0.0D + Math.sin(timeLooped + (Math.PI * 3D / 2D)) * 0.2D;
	double d22 = -1.0F + f3;
	double d23 = lineLength * 2.5D + d22;
	bufferbuilder.pos(d12, lineLength, d13).tex(0.4999D, d23).color(red, green, blue, 255).endVertex();
	bufferbuilder.pos(d12, 0.0D, d13).tex(0.4999D, d22).color(red, green, blue, 255).endVertex();
	bufferbuilder.pos(d14, 0.0D, d15).tex(0.0D, d22).color(red, green, blue, 255).endVertex();
	bufferbuilder.pos(d14, lineLength, d15).tex(0.0D, d23).color(red, green, blue, 255).endVertex();
	bufferbuilder.pos(d16, lineLength, d17).tex(0.4999D, d23).color(red, green, blue, 255).endVertex();
	bufferbuilder.pos(d16, 0.0D, d17).tex(0.4999D, d22).color(red, green, blue, 255).endVertex();
	bufferbuilder.pos(d18, 0.0D, d19).tex(0.0D, d22).color(red, green, blue, 255).endVertex();
	bufferbuilder.pos(d18, lineLength, d19).tex(0.0D, d23).color(red, green, blue, 255).endVertex();
	double d24 = 0.0D;

	if (entity.ticksExisted % 2 == 0) {
	    d24 = 0.5D;
	}

	bufferbuilder.pos(d4, lineLength, d5).tex(0.5D, d24 + 0.5D).color(red, green, blue, 255).endVertex();
	bufferbuilder.pos(d6, lineLength, d7).tex(1.0D, d24 + 0.5D).color(red, green, blue, 255).endVertex();
	bufferbuilder.pos(d10, lineLength, d11).tex(1.0D, d24).color(red, green, blue, 255).endVertex();
	bufferbuilder.pos(d8, lineLength, d9).tex(0.5D, d24).color(red, green, blue, 255).endVertex();
	tessellator.draw();
	GlStateManager.popMatrix();
    }
}
