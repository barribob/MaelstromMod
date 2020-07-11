package com.barribob.MaelstromMod.entity.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;

public class RenderScaledMob<T extends EntityLiving> extends RenderModEntity<T> {
    protected float scale;

    public <U extends ModelBase> RenderScaledMob(RenderManager rendermanagerIn, String textures, U modelClass, float scale) {
        super(rendermanagerIn, textures, modelClass);
        this.scale = scale;
    }

    @Override
    protected void preRenderCallback(T entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(scale, scale, scale);
    }
}
