package com.barribob.MaelstromMod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelChaosShield extends ModelBase
{
    private final ModelRenderer bb_main;

    public ModelChaosShield()
    {
	textureWidth = 64;
	textureHeight = 64;

	bb_main = new ModelRenderer(this);
	bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
	bb_main.cubeList.add(new ModelBox(bb_main, 18, 33, -3.0F, -2.0F, -8.0F, 6, 2, 1, 0.0F, false));
	bb_main.cubeList.add(new ModelBox(bb_main, 10, 27, -7.0F, -6.0F, -8.0F, 14, 4, 1, 0.0F, false));
	bb_main.cubeList.add(new ModelBox(bb_main, 4, 13, -10.0F, -16.0F, -8.0F, 20, 10, 1, 0.0F, false));
	bb_main.cubeList.add(new ModelBox(bb_main, 4, 24, -10.0F, -30.0F, -8.0F, 20, 2, 1, 0.0F, false));
	bb_main.cubeList.add(new ModelBox(bb_main, 15, 32, -5.0F, -32.0F, -8.0F, 9, 2, 1, 0.0F, false));
	bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -12.0F, -28.0F, -8.0F, 24, 12, 1, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
	bb_main.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
	modelRenderer.rotateAngleX = x;
	modelRenderer.rotateAngleY = y;
	modelRenderer.rotateAngleZ = z;
    }
}