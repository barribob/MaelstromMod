package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.model.ModelAnimatedBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderAnimatedBiped extends RenderBiped {
    private ResourceLocation textures;

    public RenderAnimatedBiped(RenderManager renderManagerIn, ModelAnimatedBiped modelBipedIn, float shadowSize, ResourceLocation textures) {
        super(renderManagerIn, modelBipedIn, shadowSize);
        this.textures = textures;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return this.textures;
    }
}
