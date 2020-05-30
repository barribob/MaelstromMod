package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.entity.entities.gauntlet.EntityMaelstromGauntlet;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

// Made with Blockbench
// Paste this code into your mod.
// Make sure to generate all required imports

public class ModelMaelstromGauntlet extends ModelBBAnimated
{
    private final ModelRenderer all;
    private final ModelRenderer wrist;
    private final ModelRenderer palm;
    private final ModelRenderer bone11;
    private final ModelRenderer bone;
    private final ModelRenderer bottomthumb;
    private final ModelRenderer tophand;
    private final ModelRenderer ring;
    private final ModelRenderer bone7;
    private final ModelRenderer bone8;
    private final ModelRenderer pinky;
    private final ModelRenderer bone9;
    private final ModelRenderer bone10;
    private final ModelRenderer middle;
    private final ModelRenderer bone5;
    private final ModelRenderer bone6;
    private final ModelRenderer pointer;
    private final ModelRenderer bone3;
    private final ModelRenderer bone4;
    private final ModelRenderer bone12;
    private final ModelRenderer bottomthumb2;
    private final ModelRenderer thumb;
    private final ModelRenderer bone2;

    public ModelMaelstromGauntlet()
    {
	textureWidth = 256;
	textureHeight = 256;

	all = new ModelRenderer(this);
	all.setRotationPoint(3.0F, -1.0F, -2.0F);

	wrist = new ModelRenderer(this);
	wrist.setRotationPoint(0.0F, 20.0F, 0.0F);
	all.addChild(wrist);
	wrist.cubeList.add(new ModelBox(wrist, 58, 41, -13.0F, -6.0F, -2.0F, 20, 6, 8, 0.0F, false));
	wrist.cubeList.add(new ModelBox(wrist, 0, 49, -15.0F, -2.0F, -3.0F, 24, 7, 10, 0.0F, false));
	wrist.cubeList.add(new ModelBox(wrist, 81, 69, 3.0F, -0.5F, -4.0F, 4, 4, 12, 0.0F, false));
	wrist.cubeList.add(new ModelBox(wrist, 78, 0, -5.0F, -0.5F, -4.0F, 4, 4, 12, 0.0F, false));
	wrist.cubeList.add(new ModelBox(wrist, 53, 17, -16.0F, -0.5F, 0.0F, 26, 4, 4, 0.0F, false));
	wrist.cubeList.add(new ModelBox(wrist, 62, 25, -13.0F, -0.5F, -4.0F, 4, 4, 12, 0.0F, false));

	palm = new ModelRenderer(this);
	palm.setRotationPoint(0.0F, 19.0F, 0.0F);
	all.addChild(palm);
	palm.cubeList.add(new ModelBox(palm, 0, 17, -14.0F, -28.0F, -3.0F, 22, 23, 9, 0.0F, false));
	palm.cubeList.add(new ModelBox(palm, 59, 59, -20.0448F, -28.3405F, -3.01F, 7, 13, 9, 0.0F, false));
	palm.cubeList.add(new ModelBox(palm, 113, 85, -8.5F, -25.0F, -4.0F, 10, 10, 1, 0.0F, false));
	palm.cubeList.add(new ModelBox(palm, 0, 128, -9.5F, -24.0F, -3.95F, 12, 8, 1, 0.0F, false));
	palm.cubeList.add(new ModelBox(palm, 0, 128, -7.5F, -26.0F, -3.95F, 8, 12, 1, 0.0F, false));

	bone11 = new ModelRenderer(this);
	bone11.setRotationPoint(-3.0F, -18.0F, 7.0F);
	setRotationAngle(bone11, 0.0F, 0.0F, -0.7854F);
	palm.addChild(bone11);
	bone11.cubeList.add(new ModelBox(bone11, 23, 66, -3.0F, -3.0F, -1.0F, 6, 6, 1, 0.0F, false));
	bone11.cubeList.add(new ModelBox(bone11, 82, 55, -2.0F, -2.0F, -0.5F, 4, 4, 1, 0.0F, false));
	bone11.cubeList.add(new ModelBox(bone11, 0, 49, 4.0F, -6.0F, -1.0F, 2, 9, 1, 0.0F, false));
	bone11.cubeList.add(new ModelBox(bone11, 69, 0, -3.0F, -6.0F, -1.0F, 6, 2, 1, 0.0F, false));

	bone = new ModelRenderer(this);
	bone.setRotationPoint(-14.0498F, -5.0628F, 1.5F);
	setRotationAngle(bone, 0.0F, 0.0F, -0.5236F);
	palm.addChild(bone);
	bone.cubeList.add(new ModelBox(bone, 32, 72, -0.053F, -11.8983F, -4.504F, 6, 12, 9, 0.0F, false));

	bottomthumb = new ModelRenderer(this);
	bottomthumb.setRotationPoint(0.0F, 1.0F, 0.0F);
	palm.addChild(bottomthumb);
	bottomthumb.cubeList.add(new ModelBox(bottomthumb, 62, 81, 8.0F, -29.0F, -3.0F, 5, 12, 9, 0.0F, false));

	tophand = new ModelRenderer(this);
	tophand.setRotationPoint(-5.0F, -7.0F, 2.4F);
	setRotationAngle(tophand, 0.3491F, 0.0F, 0.0F);
	all.addChild(tophand);
	tophand.cubeList.add(new ModelBox(tophand, 0, 0, -15.0F, -10.0F, -5.4F, 30, 8, 9, 0.0F, false));

	ring = new ModelRenderer(this);
	ring.setRotationPoint(-4.5F, -10.0F, -0.9F);
	tophand.addChild(ring);
	ring.cubeList.add(new ModelBox(ring, 0, 105, -4.0F, -9.0F, -3.5F, 7, 9, 7, 0.0F, false));

	bone7 = new ModelRenderer(this);
	bone7.setRotationPoint(0.0F, -8.0F, 2.0F);
	setRotationAngle(bone7, 0.3491F, 0.0F, 0.0F);
	ring.addChild(bone7);
	bone7.cubeList.add(new ModelBox(bone7, 28, 93, -4.0F, -11.0F, -5.5F, 7, 10, 7, 0.0F, false));

	bone8 = new ModelRenderer(this);
	bone8.setRotationPoint(0.0F, -10.0F, -2.0F);
	setRotationAngle(bone8, 0.3491F, 0.0F, 0.0F);
	bone7.addChild(bone8);
	bone8.cubeList.add(new ModelBox(bone8, 56, 102, -4.0F, -10.0F, -3.5F, 7, 9, 7, 0.0F, false));

	pinky = new ModelRenderer(this);
	pinky.setRotationPoint(-12.5F, -10.5F, -0.9F);
	tophand.addChild(pinky);
	pinky.cubeList.add(new ModelBox(pinky, 113, 14, -2.5F, -5.5F, -3.5F, 5, 6, 5, 0.0F, false));

	bone9 = new ModelRenderer(this);
	bone9.setRotationPoint(0.0F, -5.0F, 0.0F);
	setRotationAngle(bone9, 0.3491F, 0.0F, 0.0F);
	pinky.addChild(bone9);
	bone9.cubeList.add(new ModelBox(bone9, 114, 41, -2.5F, -6.5F, -3.5F, 5, 6, 5, 0.0F, false));

	bone10 = new ModelRenderer(this);
	bone10.setRotationPoint(0.5F, -7.5F, -1.5F);
	setRotationAngle(bone10, 0.3491F, 0.0F, 0.0F);
	bone9.addChild(bone10);
	bone10.cubeList.add(new ModelBox(bone10, 113, 74, -3.0F, -4.0F, -2.0F, 5, 5, 5, 0.0F, false));

	middle = new ModelRenderer(this);
	middle.setRotationPoint(4.0F, -9.0F, -0.9F);
	tophand.addChild(middle);
	middle.cubeList.add(new ModelBox(middle, 0, 87, -4.0F, -12.0F, -3.5F, 7, 11, 7, 0.0F, false));

	bone5 = new ModelRenderer(this);
	bone5.setRotationPoint(0.0F, -11.0F, 2.0F);
	setRotationAngle(bone5, 0.3491F, 0.0F, 0.0F);
	middle.addChild(bone5);
	bone5.cubeList.add(new ModelBox(bone5, 90, 90, -4.0F, -12.0F, -5.5F, 7, 11, 7, 0.0F, false));

	bone6 = new ModelRenderer(this);
	bone6.setRotationPoint(0.0F, -11.0F, -2.0F);
	setRotationAngle(bone6, 0.3491F, 0.0F, 0.0F);
	bone5.addChild(bone6);
	bone6.cubeList.add(new ModelBox(bone6, 94, 25, -4.0F, -10.0F, -3.5F, 7, 9, 7, 0.0F, false));

	pointer = new ModelRenderer(this);
	pointer.setRotationPoint(11.5F, -10.0F, -0.9F);
	tophand.addChild(pointer);
	pointer.cubeList.add(new ModelBox(pointer, 28, 110, -3.5046F, -10.0871F, -3.5F, 7, 10, 6, 0.0F, false));

	bone3 = new ModelRenderer(this);
	bone3.setRotationPoint(-0.0006F, -9.0349F, 1.0F);
	setRotationAngle(bone3, 0.3491F, 0.0F, 0.0F);
	pointer.addChild(bone3);
	bone3.cubeList.add(new ModelBox(bone3, 110, 110, -3.504F, -9.0522F, -4.5F, 7, 8, 6, 0.0F, false));

	bone4 = new ModelRenderer(this);
	bone4.setRotationPoint(0.0F, -8.0F, -2.0F);
	setRotationAngle(bone4, 0.3491F, 0.0F, 0.0F);
	bone3.addChild(bone4);
	bone4.cubeList.add(new ModelBox(bone4, 110, 0, -3.504F, -9.0522F, -2.5F, 7, 8, 6, 0.0F, false));

	bone12 = new ModelRenderer(this);
	bone12.setRotationPoint(6.0F, -2.0F, 4.6F);
	setRotationAngle(bone12, 0.0F, 0.0F, -0.7854F);
	tophand.addChild(bone12);
	bone12.cubeList.add(new ModelBox(bone12, 62, 25, -6.0F, -12.0F, -1.5F, 4, 4, 1, 0.0F, false));
	bone12.cubeList.add(new ModelBox(bone12, 0, 21, -10.5F, -16.5F, -1.0F, 3, 3, 1, 0.0F, false));
	bone12.cubeList.add(new ModelBox(bone12, 0, 17, -5.5F, -11.5F, -1.0F, 3, 3, 1, 0.0F, false));
	bone12.cubeList.add(new ModelBox(bone12, 0, 4, 4.5F, -1.5F, -1.0F, 3, 3, 1, 0.0F, false));
	bone12.cubeList.add(new ModelBox(bone12, 0, 0, -0.5F, -6.5F, -1.0F, 3, 3, 1, 0.0F, false));
	bone12.cubeList.add(new ModelBox(bone12, 82, 25, -11.0F, -17.0F, -1.5F, 4, 4, 1, 0.0F, false));
	bone12.cubeList.add(new ModelBox(bone12, 78, 6, 4.0F, -2.0F, -1.5F, 4, 4, 1, 0.0F, false));
	bone12.cubeList.add(new ModelBox(bone12, 62, 30, -1.0F, -7.0F, -1.5F, 4, 4, 1, 0.0F, false));

	bottomthumb2 = new ModelRenderer(this);
	bottomthumb2.setRotationPoint(2.0F, 7.0F, 1.5F);
	setRotationAngle(bottomthumb2, 0.5236F, 0.0F, 0.6981F);
	all.addChild(bottomthumb2);
	bottomthumb2.cubeList.add(new ModelBox(bottomthumb2, 0, 66, 1.5199F, -10.6251F, -4.48F, 7, 12, 9, 0.0F, false));

	thumb = new ModelRenderer(this);
	thumb.setRotationPoint(5.6284F, -10.6423F, 0.0F);
	setRotationAngle(thumb, 0.0F, 0.0F, -0.4363F);
	bottomthumb2.addChild(thumb);
	thumb.cubeList.add(new ModelBox(thumb, 101, 55, -4.5223F, -11.8904F, -3.5F, 7, 13, 6, 0.0F, false));

	bone2 = new ModelRenderer(this);
	bone2.setRotationPoint(0.0F, -11.0F, 0.0F);
	thumb.addChild(bone2);
	bone2.cubeList.add(new ModelBox(bone2, 84, 108, -4.4918F, -11.8316F, -3.5F, 7, 11, 6, 0.0F, false));
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
	if (entityIn instanceof EntityMaelstromGauntlet)
	{
	    this.all.rotateAngleX = (float) Math.toRadians(((EntityMaelstromGauntlet) entityIn).getLook());
	}

	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
	all.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
	modelRenderer.rotateAngleX = x;
	modelRenderer.rotateAngleY = y;
	modelRenderer.rotateAngleZ = z;
    }
}