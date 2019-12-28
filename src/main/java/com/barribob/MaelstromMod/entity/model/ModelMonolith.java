package com.barribob.MaelstromMod.entity.model;
//Made with Blockbench

import com.barribob.MaelstromMod.entity.entities.EntityMonolith;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelMonolith extends ModelAnimated
{
    public ModelRenderer shell3;
    private final ModelRenderer bb_main;
    private final ModelRenderer center;
    private final ModelRenderer shell1;
    private final ModelRenderer shell2;
    public ModelRenderer body2;
    public ModelRenderer body1;

    public ModelMonolith()
    {
	textureWidth = 256;
	textureHeight = 256;

	shell3 = new ModelRenderer(this);
	shell3.setRotationPoint(0.0F, 24.0F, 0.0F);
	shell3.cubeList.add(new ModelBox(shell3, 116, 116, -4.0F, -71.0F, -11.0F, 8, 61, 22, 0.0F, false));

	bb_main = new ModelRenderer(this);
	bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
	bb_main.cubeList.add(new ModelBox(bb_main, 140, 0, -19.5F, -7.0F, -3.5F, 16, 7, 17, 0.0F, false));
	bb_main.cubeList.add(new ModelBox(bb_main, 44, 145, -20.5F, -8.0F, -12.5F, 17, 8, 9, 0.0F, false));
	bb_main.cubeList.add(new ModelBox(bb_main, 140, 48, -3.5F, -10.0F, -13.5F, 11, 10, 14, 0.0F, false));
	bb_main.cubeList.add(new ModelBox(bb_main, 36, 0, 7.5F, -9.0F, -12.5F, 11, 9, 13, 0.0F, false));
	bb_main.cubeList.add(new ModelBox(bb_main, 140, 24, -3.5F, -9.0F, 0.5F, 16, 9, 15, 0.0F, false));
	bb_main.cubeList.add(new ModelBox(bb_main, 154, 109, 12.5F, -7.0F, 0.5F, 6, 7, 13, 0.0F, false));

	center = new ModelRenderer(this);
	center.setRotationPoint(-0.5F, 30.0F, 12.5F);
	setRotationAngle(center, 0.0F, 0.0F, 0.7854F);
	center.cubeList.add(new ModelBox(center, 116, 78, -50.2193F, -50.7193F, -19.5F, 17, 17, 14, 0.0F, false));

	shell1 = new ModelRenderer(this);
	shell1.setRotationPoint(-13.5F, 24.0F, 0.0F);
	shell1.cubeList.add(new ModelBox(shell1, 0, 0, -3.5F, -71.0F, -11.0F, 7, 69, 22, 0.0F, false));

	shell2 = new ModelRenderer(this);
	shell2.setRotationPoint(13.5F, 24.0F, 0.0F);
	shell2.cubeList.add(new ModelBox(shell2, 58, 58, -3.5F, -67.0F, -11.0F, 7, 65, 22, 0.0F, false));

	body2 = new ModelRenderer(this);
	body2.setRotationPoint(7.0F, 24.0F, 0.0F);
	body2.cubeList.add(new ModelBox(body2, 0, 95, -3.0F, -65.0F, -8.0F, 6, 56, 16, 0.0F, false));

	body1 = new ModelRenderer(this);
	body1.setRotationPoint(-6.0F, 24.0F, 0.0F);
	body1.cubeList.add(new ModelBox(body1, 94, 0, -5.0F, -69.0F, -8.0F, 7, 62, 16, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
	GlStateManager.pushMatrix();
	GlStateManager.rotate(90, 0, 1, 0);
	shell3.render(f5);
	bb_main.render(f5);
	center.render(f5);
	shell1.render(f5);
	shell2.render(f5);
	body2.render(f5);
	body1.render(f5);
	GlStateManager.popMatrix();
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
	if (entity instanceof EntityMonolith)
	{
	    if (((EntityMonolith) entity).isTransformed())
	    {
		int offset = 35;
		shell3 = new ModelRenderer(this);
		shell3.setRotationPoint(0.0F, 24.0F, 0.0F);
		shell3.cubeList.add(new ModelBox(shell3, 116, 116, -4.0F, -71.0F + offset, -11.0F, 8, 61 - offset, 22, 0.0F, false));

		body2 = new ModelRenderer(this);
		body2.setRotationPoint(7.0F, 24.0F, 0.0F);
		body2.cubeList.add(new ModelBox(body2, 0, 95, -3.0F, -65.0F + offset, -8.0F, 6, 56 - offset, 16, 0.0F, false));

		body1 = new ModelRenderer(this);
		body1.setRotationPoint(-6.0F, 24.0F, 0.0F);
		body1.cubeList.add(new ModelBox(body1, 94, 0, -5.0F, -69.0F + offset, -8.0F, 7, 62 - offset, 16, 0.0F, false));
	    }
	    else
	    {
		shell3 = new ModelRenderer(this);
		shell3.setRotationPoint(0.0F, 24.0F, 0.0F);
		shell3.cubeList.add(new ModelBox(shell3, 116, 116, -4.0F, -71.0F, -11.0F, 8, 61, 22, 0.0F, false));

		body2 = new ModelRenderer(this);
		body2.setRotationPoint(7.0F, 24.0F, 0.0F);
		body2.cubeList.add(new ModelBox(body2, 0, 95, -3.0F, -65.0F, -8.0F, 6, 56, 16, 0.0F, false));

		body1 = new ModelRenderer(this);
		body1.setRotationPoint(-6.0F, 24.0F, 0.0F);
		body1.cubeList.add(new ModelBox(body1, 94, 0, -5.0F, -69.0F, -8.0F, 7, 62, 16, 0.0F, false));
	    }
	}
	super.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTickTime);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
	modelRenderer.rotateAngleX = x;
	modelRenderer.rotateAngleY = y;
	modelRenderer.rotateAngleZ = z;
    }
}