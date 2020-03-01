package com.barribob.MaelstromMod.entity.render;

import org.lwjgl.opengl.GL11;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.IElement;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.RenderUtils;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * Renders an entity with a generic type, texture, and model passed in.
 *
 */
public class RenderModEntity<T extends EntityLiving> extends RenderLiving<T>
{
    public String[] TEXTURES;
    private ResourceLocation DEATH_TEXTURES;

    public <U extends ModelBase> RenderModEntity(RenderManager rendermanagerIn, String textures, U modelClass)
    {
	this(rendermanagerIn, modelClass, new String[] { textures });
    }

    public <U extends ModelBase> RenderModEntity(RenderManager rendermanagerIn, U modelClass, String... textures)
    {
	super(rendermanagerIn, modelClass, 0.5f);
	if (textures.length == 0)
	{
	    throw new IllegalArgumentException("Must provide at least one texture to render an entity.");
	}
	this.TEXTURES = textures;
	this.DEATH_TEXTURES = new ResourceLocation(String.format("%s:textures/entity/disintegration_%d_%d.png", Reference.MOD_ID, modelClass.textureWidth, modelClass.textureHeight));
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity)
    {
	String texture = TEXTURES[0];
	if (entity instanceof IElement)
	{
	    IElement e = (IElement) entity;
	    if (e.getElement().equals(Element.AZURE) && TEXTURES.length >= 2)
	    {
		texture = TEXTURES[1];
	    }
	    else if (e.getElement().equals(Element.GOLDEN) && TEXTURES.length >= 3)
	    {
		texture = TEXTURES[2];
	    }
	    else if (e.getElement().equals(Element.GOLDEN) && TEXTURES.length >= 4)
	    {
		texture = TEXTURES[3];
	    }
	}

	return new ResourceLocation(Reference.MOD_ID + ":textures/entity/" + texture);
    }

    @Override
    protected void applyRotations(T entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
	if (entityLiving instanceof EntityMaelstromMob)
	{
	    GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
	}
	else
	{
	    super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
    }

    @Override
    protected void renderModel(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
	if (entity instanceof EntityMaelstromMob)
	{
	    if (entity.getHealth() <= 0)
	    {
		float f = entity.deathTime / (15f); // The alpha value required to render a pixel (multiplied by 95 because the actual image was erased by 95)
//		GlStateManager.depthFunc(GL11.GL_LEQUAL);
//		GlStateManager.enableAlpha();
//		GlStateManager.alphaFunc(GL11.GL_GREATER, f);
		this.bindTexture(DEATH_TEXTURES);
		
		/**
		 * We've gotten somewhere with this problem. The only thing that really doesn't work here is the GL GREATER which will render mobs through walls
		 * try this masking somehow: https://stackoverflow.com/questions/5097145/opengl-mask-with-multiple-textures
		 * Actually maybe use the stencil buffer or something like it: https://stackoverflow.com/questions/292071/opengl-how-to-implement-an-eraser-tool
		 * I believe we can do this with stencil buffering hopefully.
		 */
		GlStateManager.colorMask(false, false, false, false);
		GlStateManager.depthMask(false);
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFFFFFFFF);
		GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);

		// Rendering similar to the creeper aura thingy
//		GlStateManager.matrixMode(5890);
//		GlStateManager.loadIdentity();
//		GlStateManager.matrixMode(5888);
//		GlStateManager.enableBlend();
//		GlStateManager.color(0.1f, 0.2f, 0.5f, 0.5F); // Color it purple
//		GlStateManager.disableLighting();
//		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
//		GlStateManager.scale(1.01, 1.01, 1.01);
		this.mainModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
//		GlStateManager.matrixMode(5890);
//		GlStateManager.loadIdentity();
//		GlStateManager.matrixMode(5888);
//		GlStateManager.enableLighting();
//		GlStateManager.disableBlend();
		
		GL11.glStencilFunc(GL11.GL_EQUAL, 0, 0xFFFFFFFF);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
		GlStateManager.colorMask(true, true, true, true);
		GlStateManager.depthMask(true);

//		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
//		GlStateManager.depthFunc(GL11.GL_GREATER); // This makes it so that only the stuff that's rendered before gets rendered in the real render below
	    }

	    this.bindEntityTexture(entity);
	    this.mainModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

	    GlStateManager.depthFunc(GL11.GL_LEQUAL);
	}
	else
	{
	    super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
	}
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
	if (!entity.isInvisible())
	{
	    // The blending here allows for rendering of translucent textures
	    GlStateManager.enableNormalize();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
	    super.doRender(entity, x, y, z, entityYaw, partialTicks);
	    GlStateManager.disableBlend();
	    GlStateManager.disableNormalize();

	    if (entity instanceof EntityLeveledMob)
	    {
		((EntityLeveledMob) entity).doRender(this.renderManager, x, y, z, entityYaw, partialTicks);
		if (ModConfig.entities.displayLevel)
		{
		    this.renderLivingLabel(entity, "Level: " + ((EntityLeveledMob) entity).getLevel(), x, y, z, 10);
		}
	    }

	    if (entity instanceof IElement && !((IElement) entity).getElement().equals(Element.NONE))
	    {
		double d0 = entity.getDistanceSq(this.renderManager.renderViewEntity);
		double maxDistance = 20;

		if (d0 <= maxDistance * maxDistance)
		{
		    boolean flag = entity.isSneaking();
		    float f = this.renderManager.playerViewY;
		    float f1 = this.renderManager.playerViewX;
		    boolean flag1 = this.renderManager.options.thirdPersonView == 2;
		    float f2 = entity.height + 0.5F - (flag ? 0.25F : 0.0F);
		    int verticalOffset = this.canRenderName(entity) ? -6 : 0;
		    float scale = (float) entity.getEntityBoundingBox().getAverageEdgeLength();
		    RenderUtils.drawElement(this.getFontRendererFromRenderManager(), ((IElement) entity).getElement().textColor + ((IElement) entity).getElement().symbol, (float) x, (float) y + f2, (float) z, verticalOffset, f, f1, flag1, flag,
			    entity.ticksExisted, partialTicks, scale);
		}
	    }
	}
	else
	{
	    super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
    }
}
