package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromIllager;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromIllager;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * Renders illager armor when arms are raised, otherwise it renders the illager
 * model normally
 *
 */
public class RenderMaelstromIllager extends RenderLiving<EntityMaelstromIllager>
{
    public ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/entity/maelstrom_illager.png");
    private static final DynamicTexture TEXTURE_BRIGHTNESS = new DynamicTexture(16, 16);

    public RenderMaelstromIllager(RenderManager rendermanagerIn)
    {
	super(rendermanagerIn, new ModelMaelstromIllager(), 0.5f);
	this.addLayer(new LayerMaelstromIllagerArmor(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMaelstromIllager entity)
    {
	return TEXTURES;
    }
}
