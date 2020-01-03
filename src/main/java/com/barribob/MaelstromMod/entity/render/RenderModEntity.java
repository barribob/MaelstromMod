package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.IElement;
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
    public ResourceLocation TEXTURES;

    public <U extends ModelBase> RenderModEntity(RenderManager rendermanagerIn, ResourceLocation textures, U modelClass)
    {
	super(rendermanagerIn, modelClass, 0.5f);
	this.TEXTURES = textures;
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity)
    {
	return TEXTURES;
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
	if (!entity.isInvisible())
	{
	    // The blending here allows for rendering of translucent textures
	    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	    GlStateManager.enableNormalize();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
	    super.doRender(entity, x, y, z, entityYaw, partialTicks);
	    GlStateManager.disableBlend();
	    GlStateManager.disableNormalize();

	    if (entity instanceof IElement && !((IElement) entity).getElement().equals(Element.NONE))
	    {
		double d0 = entity.getDistanceSq(this.renderManager.renderViewEntity);
		double maxDistance = 10;

		if (d0 <= maxDistance * maxDistance)
		{
		    boolean flag = entity.isSneaking();
		    float f = this.renderManager.playerViewY;
		    float f1 = this.renderManager.playerViewX;
		    boolean flag1 = this.renderManager.options.thirdPersonView == 2;
		    float f2 = entity.height + 0.5F - (flag ? 0.25F : 0.0F);
		    int verticalOffset = this.canRenderName(entity) ? -6 : 0;
		    RenderUtils.drawElement(this.getFontRendererFromRenderManager(), ((IElement) entity).getElement().textColor + ((IElement) entity).getElement().symbol, (float) x, (float) y + f2, (float) z, verticalOffset, f, f1, flag1, flag);
		}
	    }
	}
	else
	{
	    super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
    }
}
