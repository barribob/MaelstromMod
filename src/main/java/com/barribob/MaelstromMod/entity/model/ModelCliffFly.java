package com.barribob.MaelstromMod.entity.model;
//Made with Blockbench

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCliffFly extends ModelAnimated
{
    private final ModelRenderer body;
    private final ModelRenderer body2;
    public final ModelRenderer leftBackWing;
    public final ModelRenderer leftBackWing2;
    public final ModelRenderer rightBackWing;
    public final ModelRenderer rightBackWing2;
    private final ModelRenderer tail1;
    private final ModelRenderer tail2;
    private final ModelRenderer tail3;
    private final ModelRenderer head;
    public final ModelRenderer leftFrontWing;
    public final ModelRenderer leftFrontWing1;
    public final ModelRenderer rightFrontWing;
    public final ModelRenderer rightFrontWing2;

    public ModelCliffFly()
    {
	textureWidth = 64;
	textureHeight = 64;

	body = new ModelRenderer(this);
	body.setRotationPoint(0.0F, 24.0F, 4.0F);
	setRotationAngle(body, 0.1745F, 0.0F, 0.0F);
	body.cubeList.add(new ModelBox(body, 14, 21, -2.0F, -20.0F, -0.5F, 4, 6, 2, 0.0F, false));

	body2 = new ModelRenderer(this);
	body2.setRotationPoint(0.0F, -14.0F, 0.5F);
	setRotationAngle(body2, -0.2618F, 0.0F, 0.0F);
	body.addChild(body2);
	body2.cubeList.add(new ModelBox(body2, 20, 4, -2.0F, 0.0F, -1.0F, 4, 6, 2, 0.0F, false));

	leftBackWing = new ModelRenderer(this);
	leftBackWing.setRotationPoint(2.0F, 3.0F, 0.0F);
	body2.addChild(leftBackWing);
	leftBackWing.cubeList.add(new ModelBox(leftBackWing, 20, 31, 0.0F, -3.0F, -0.5F, 4, 5, 1, 0.0F, false));

	leftBackWing2 = new ModelRenderer(this);
	leftBackWing2.setRotationPoint(4.0F, -0.5F, 0.0F);
	leftBackWing.addChild(leftBackWing2);
	leftBackWing2.cubeList.add(new ModelBox(leftBackWing2, 20, 12, 0.0F, -2.5F, -0.5F, 8, 1, 1, 0.0F, false));
	leftBackWing2.cubeList.add(new ModelBox(leftBackWing2, 0, 15, 0.0F, -1.5F, 0.0F, 10, 4, 0, 0.0F, false));

	rightBackWing = new ModelRenderer(this);
	rightBackWing.setRotationPoint(-2.0F, 3.0F, 0.0F);
	body2.addChild(rightBackWing);
	rightBackWing.cubeList.add(new ModelBox(rightBackWing, 10, 29, -4.0F, -3.0F, -0.5F, 4, 5, 1, 0.0F, false));

	rightBackWing2 = new ModelRenderer(this);
	rightBackWing2.setRotationPoint(-4.0F, -0.5F, 0.0F);
	rightBackWing.addChild(rightBackWing2);
	rightBackWing2.cubeList.add(new ModelBox(rightBackWing2, 18, 19, -8.0F, -2.5F, -0.5F, 8, 1, 1, 0.0F, false));
	rightBackWing2.cubeList.add(new ModelBox(rightBackWing2, 0, 11, -10.0F, -1.5F, 0.0F, 10, 4, 0, 0.0F, false));

	tail1 = new ModelRenderer(this);
	tail1.setRotationPoint(0.0F, 6.0F, 0.0F);
	setRotationAngle(tail1, -0.4363F, 0.0F, 0.0F);
	body2.addChild(tail1);
	tail1.cubeList.add(new ModelBox(tail1, 26, 21, -1.5F, -1.0F, -0.5F, 3, 4, 1, 0.0F, false));

	tail2 = new ModelRenderer(this);
	tail2.setRotationPoint(0.0F, 2.0F, 0.0F);
	setRotationAngle(tail2, -0.6109F, 0.0F, 0.0F);
	tail1.addChild(tail2);
	tail2.cubeList.add(new ModelBox(tail2, 26, 26, -2.0F, 0.0F, -1.0F, 4, 3, 2, 0.0F, false));

	tail3 = new ModelRenderer(this);
	tail3.setRotationPoint(0.0F, 2.0F, 0.0F);
	setRotationAngle(tail3, -0.3491F, 0.0F, 0.0F);
	tail2.addChild(tail3);
	tail3.cubeList.add(new ModelBox(tail3, 0, 0, -3.0F, 0.0F, -2.0F, 6, 3, 4, 0.0F, false));

	head = new ModelRenderer(this);
	head.setRotationPoint(0.0F, -20.0F, 0.5F);
	body.addChild(head);
	head.cubeList.add(new ModelBox(head, 0, 21, -2.0F, -3.0F, -1.5F, 4, 3, 3, 0.0F, false));

	leftFrontWing = new ModelRenderer(this);
	leftFrontWing.setRotationPoint(2.0F, -17.0F, 0.5F);
	body.addChild(leftFrontWing);
	leftFrontWing.cubeList.add(new ModelBox(leftFrontWing, 30, 31, 0.0F, -3.0F, -0.5F, 4, 5, 1, 0.0F, false));

	leftFrontWing1 = new ModelRenderer(this);
	leftFrontWing1.setRotationPoint(4.0F, -0.5F, 0.0F);
	leftFrontWing.addChild(leftFrontWing1);
	leftFrontWing1.cubeList.add(new ModelBox(leftFrontWing1, 20, 14, 0.0F, -2.5F, -0.5F, 8, 1, 1, 0.0F, false));
	leftFrontWing1.cubeList.add(new ModelBox(leftFrontWing1, 16, 0, 0.0F, -1.5F, 0.0F, 10, 4, 0, 0.0F, false));

	rightFrontWing = new ModelRenderer(this);
	rightFrontWing.setRotationPoint(-2.0F, -17.0F, 0.5F);
	body.addChild(rightFrontWing);
	rightFrontWing.cubeList.add(new ModelBox(rightFrontWing, 0, 27, -4.0F, -3.0F, -0.5F, 4, 5, 1, 0.0F, false));

	rightFrontWing2 = new ModelRenderer(this);
	rightFrontWing2.setRotationPoint(-4.0F, -0.5F, 0.0F);
	rightFrontWing.addChild(rightFrontWing2);
	rightFrontWing2.cubeList.add(new ModelBox(rightFrontWing2, 0, 19, -8.0F, -2.5F, -0.5F, 8, 1, 1, 0.0F, false));
	rightFrontWing2.cubeList.add(new ModelBox(rightFrontWing2, 0, 7, -10.0F, -1.5F, 0.0F, 10, 4, 0, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
	body.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
	modelRenderer.rotateAngleX = x;
	modelRenderer.rotateAngleY = y;
	modelRenderer.rotateAngleZ = z;
    }
}