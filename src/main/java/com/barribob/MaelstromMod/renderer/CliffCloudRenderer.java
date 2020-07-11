package com.barribob.MaelstromMod.renderer;

import com.barribob.MaelstromMod.event_handlers.FogHandler;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.util.function.Predicate;

/**
 * Sourced from CloudRenderer.java
 *
 * @author micha
 */
public class CliffCloudRenderer extends IRenderHandler implements ISelectiveResourceReloadListener {
    private VertexBuffer vbo;
    private int displayList = -1;
    private static final VertexFormat FORMAT = DefaultVertexFormats.POSITION_TEX_COLOR;
    private int renderDistance = -1;
    private final Minecraft mc = Minecraft.getMinecraft();
    private final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/environment/cliff_swamp_fog.png");
    private static final int TOP_SECTIONS = 12; // Number of slices a top face will span.
    private static final float PX_SIZE = 1 / 256F;
    private static final float ALPHA = 0.7F;
    private int texW;
    private int texH;
    private static final boolean WIREFRAME = false;
    private DynamicTexture COLOR_TEX = null;

    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        Entity entity = mc.getRenderViewEntity();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0f);

        for (int i = 0; i < FogHandler.SWAMP_FOG_LAYERS; i++) {
            float y = FogHandler.CLIFF_FOG_HEIGHT + i;
            int maxAlpha = 80;
            double entityHeight = entity.getPositionEyes(partialTicks).y;
            double distanceFromPlane = entityHeight - (y + entity.getEyeHeight());
            // As the player approaches the plane, fade it out
            double alpha = maxAlpha * MathHelper.clamp(distanceFromPlane / FogHandler.SWAMP_FOG_FADE_START, 0, 1);

            this.renderPlane(partialTicks, world, mc, y, ModColors.SWAMP_FOG.scale(255), (int) alpha);
        }

        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
    }

    public void renderPlane(float partialTicks, WorldClient world, Minecraft mc, float height, Vec3d colorTweak, int alpha) {
        if (!isBuilt()) {
            reloadTextures();
        }
        checkSettings();

        Entity entity = mc.getRenderViewEntity();

        double totalOffset = partialTicks + entity.ticksExisted;

        double x = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks + totalOffset * 0.01;
        double y = height + entity.getEyeHeight() - (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks);
        double z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;

        int scale = getScale();

        // Integer UVs to translate the texture matrix by.
        int offU = fullCoord(x, scale);
        int offV = fullCoord(z, scale);

        GlStateManager.pushMatrix();

        // Translate by the remainder after the UV offset.
        GlStateManager.translate((offU * scale) - x, y, (offV * scale) - z);

        // Modulo to prevent texture samples becoming inaccurate at extreme offsets.
        offU = offU % texW;
        offV = offV % texH;

        // Translate the texture.
        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GlStateManager.translate(offU * PX_SIZE, offV * PX_SIZE, 0);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);

        GlStateManager.disableCull();

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        if (COLOR_TEX == null)
            COLOR_TEX = new DynamicTexture(1, 1);

        // Apply a color multiplier through a texture upload if shaders aren't supported.
        COLOR_TEX.getTextureData()[0] = alpha << 24 | ((int) (colorTweak.x)) << 16 | ((int) (colorTweak.y)) << 8 | (int) (colorTweak.z);
        COLOR_TEX.updateDynamicTexture();

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.bindTexture(COLOR_TEX.getGlTextureId());
        GlStateManager.enableTexture2D();

        // Bind the clouds texture last so the shader's sampler2D is correct.
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        mc.renderEngine.bindTexture(texture);

        ByteBuffer buffer = Tessellator.getInstance().getBuffer().getByteBuffer();

        // Set up pointers for the display list/VBO.
        if (OpenGlHelper.useVbo()) {
            vbo.bindBuffer();

            int stride = FORMAT.getNextOffset();
            GlStateManager.glVertexPointer(3, GL11.GL_FLOAT, stride, 0);
            GlStateManager.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            GlStateManager.glTexCoordPointer(2, GL11.GL_FLOAT, stride, 12);
            GlStateManager.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            GlStateManager.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, stride, 20);
            GlStateManager.glEnableClientState(GL11.GL_COLOR_ARRAY);
        } else {
            buffer.limit(FORMAT.getNextOffset());
            for (int i = 0; i < FORMAT.getElementCount(); i++)
                FORMAT.getElements().get(i).getUsage().preDraw(FORMAT, i, FORMAT.getNextOffset(), buffer);
            buffer.position(0);
        }

        // Depth pass to prevent insides rendering from the outside.
        GlStateManager.colorMask(false, false, false, false);
        if (OpenGlHelper.useVbo())
            vbo.drawArrays(GL11.GL_QUADS);
        else
            GlStateManager.callList(displayList);

        // Full render.
        if (!mc.gameSettings.anaglyph) {
            GlStateManager.colorMask(true, true, true, true);
        } else {
            switch (EntityRenderer.anaglyphField) {
                case 0:
                    GlStateManager.colorMask(false, true, true, true);
                    break;
                case 1:
                    GlStateManager.colorMask(true, false, false, true);
                    break;
            }
        }

        // Wireframe for debug.
        if (WIREFRAME) {
            GlStateManager.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
            GlStateManager.glLineWidth(2.0F);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.disableFog();
            if (OpenGlHelper.useVbo())
                vbo.drawArrays(GL11.GL_QUADS);
            else
                GlStateManager.callList(displayList);
            GlStateManager.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.enableFog();
        }

        if (OpenGlHelper.useVbo()) {
            vbo.drawArrays(GL11.GL_QUADS);
            vbo.unbindBuffer(); // Unbind buffer and disable pointers.
        } else {
            GlStateManager.callList(displayList);
        }

        buffer.limit(0);
        for (int i = 0; i < FORMAT.getElementCount(); i++)
            FORMAT.getElements().get(i).getUsage().postDraw(FORMAT, i, FORMAT.getNextOffset(), buffer);
        buffer.position(0);

        // Disable our coloring.
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

        // Reset texture matrix.
        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);

        GlStateManager.disableBlend();
        GlStateManager.enableCull();

        GlStateManager.popMatrix();
    }

    public void checkSettings() {
        renderDistance = mc.gameSettings.renderDistanceChunks;

        if (isBuilt() && (mc.gameSettings.renderDistanceChunks != renderDistance)) {
            dispose();
        }

        if (!isBuilt()) {
            build();
        }
    }

    private void dispose() {
        if (vbo != null) {
            vbo.deleteGlBuffers();
            vbo = null;
        }
        if (displayList >= 0) {
            GLAllocation.deleteDisplayLists(displayList);
            displayList = -1;
        }
    }

    private boolean isBuilt() {
        return OpenGlHelper.useVbo() ? vbo != null : displayList >= 0;
    }

    private void build() {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();

        if (OpenGlHelper.useVbo())
            vbo = new VertexBuffer(FORMAT);
        else
            GlStateManager.glNewList(displayList = GLAllocation.generateDisplayLists(1), GL11.GL_COMPILE);

        vertices(buffer);

        if (OpenGlHelper.useVbo()) {
            buffer.finishDrawing();
            buffer.reset();
            vbo.bufferData(buffer.getByteBuffer());
        } else {
            tess.draw();
            GlStateManager.glEndList();
        }
    }

    private int getScale() {
        return 8;
    }

    private float ceilToScale(float value) {
        float scale = getScale();
        return MathHelper.ceil(value / scale) * scale;
    }

    private void vertices(BufferBuilder buffer) {
        float scale = getScale();

        float bCol = 1F;

        float sectEnd = ceilToScale((renderDistance * 2) * 16);
        float sectStart = -sectEnd;

        float sectStep = ceilToScale(sectEnd * 2 / TOP_SECTIONS);
        float sectPx = PX_SIZE / scale;

        buffer.begin(GL11.GL_QUADS, FORMAT);

        float sectX0 = sectStart;
        float sectX1 = sectX0;

        while (sectX1 < sectEnd) {
            sectX1 += sectStep;

            if (sectX1 > sectEnd)
                sectX1 = sectEnd;

            float sectZ0 = sectStart;
            float sectZ1 = sectZ0;

            while (sectZ1 < sectEnd) {
                sectZ1 += sectStep;

                if (sectZ1 > sectEnd)
                    sectZ1 = sectEnd;

                float u0 = sectX0 * sectPx;
                float u1 = sectX1 * sectPx;
                float v0 = sectZ0 * sectPx;
                float v1 = sectZ1 * sectPx;

                // Bottom
                buffer.pos(sectX0, 0, sectZ0).tex(u0, v0).color(bCol, bCol, bCol, ALPHA).endVertex();
                buffer.pos(sectX1, 0, sectZ0).tex(u1, v0).color(bCol, bCol, bCol, ALPHA).endVertex();
                buffer.pos(sectX1, 0, sectZ1).tex(u1, v1).color(bCol, bCol, bCol, ALPHA).endVertex();
                buffer.pos(sectX0, 0, sectZ1).tex(u0, v1).color(bCol, bCol, bCol, ALPHA).endVertex();

                sectZ0 = sectZ1;
            }

            sectX0 = sectX1;
        }
    }

    private int fullCoord(double coord, int scale) { // Corrects misalignment of UV offset when on negative coords.
        return ((int) coord / scale) - (coord < 0 ? 1 : 0);
    }

    private void reloadTextures() {
        if (mc.renderEngine != null) {
            mc.renderEngine.bindTexture(texture);
            texW = GlStateManager.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
            texH = GlStateManager.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
        }
    }

    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager, @Nonnull Predicate<IResourceType> resourcePredicate) {
        reloadTextures();
    }
}
