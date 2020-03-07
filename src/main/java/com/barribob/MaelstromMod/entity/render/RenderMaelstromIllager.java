package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromIllager;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromIllager;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;

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
}
