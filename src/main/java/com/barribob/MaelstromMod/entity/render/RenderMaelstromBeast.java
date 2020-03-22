package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromBeast;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromBeast;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderMaelstromBeast extends RenderModEntity<EntityMaelstromBeast>
{
    public ResourceLocation TEXTURES_1 = new ResourceLocation(Reference.MOD_ID + ":textures/entity/maelstrom_beast.png");
    public ResourceLocation TEXTURES_2 = new ResourceLocation(Reference.MOD_ID + ":textures/entity/skeleton_minotaur.png");

    public RenderMaelstromBeast(RenderManager rendermanagerIn)
    {
	super(rendermanagerIn, "", new ModelMaelstromBeast());
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMaelstromBeast entity)
    {
	if (entity.isRaged())
	{
	    return TEXTURES_2;
	}
	return TEXTURES_1;
    }
}
