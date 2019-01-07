package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMaelstromStray extends RenderSkeleton
{
    private static final ResourceLocation STRAY_SKELETON_TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/entity/maelstrom_stray.png");

    public RenderMaelstromStray(RenderManager p_i47191_1_)
    {
        super(p_i47191_1_);
        this.addLayer(new LayerMaelstromStrayClothing(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(AbstractSkeleton entity)
    {
        return STRAY_SKELETON_TEXTURES;
    }
}