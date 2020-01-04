package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromWarrior;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderMaelstromWarrior extends RenderModEntity<EntityShade>
{
    private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/entity/shade_base.png");
    private static final ResourceLocation AZURE_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/entity/shade_azure.png");
    private static final ResourceLocation GOLDEN_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/entity/shade_golden.png");

    public RenderMaelstromWarrior(RenderManager rendermanagerIn)
    {
	super(rendermanagerIn, DEFAULT_TEXTURE, new ModelMaelstromWarrior());
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityShade entity)
    {
	if(entity.getElement().equals(Element.AZURE))
	{
	    return AZURE_TEXTURE;
	}
	else if (entity.getElement().equals(Element.GOLDEN))
	{
	    return GOLDEN_TEXTURE;
	}

	return DEFAULT_TEXTURE;
    }
}
