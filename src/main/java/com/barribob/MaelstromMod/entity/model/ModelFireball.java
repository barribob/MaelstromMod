package com.barribob.MaelstromMod.entity.model;

import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

// Made with Blockbench

public class ModelFireball extends ModelBase {
    private final ModelRenderer bone;

    public ModelFireball() {
	textureWidth = 128;
	textureHeight = 128;

	bone = new ModelRenderer(this);
	bone.setRotationPoint(0.0F, 16.0F, 0.0F);
	bone.cubeList.add(new ModelBox(bone, 0, 64, -4.0F, -7.0F, -4.0F, 8, 14, 8, 0.0F, false));
	bone.cubeList.add(new ModelBox(bone, 34, 12, -5.0F, -6.0F, -5.0F, 10, 12, 10, 0.0F, false));
	bone.cubeList.add(new ModelBox(bone, 32, 70, -2.0F, -8.0F, -2.0F, 4, 16, 4, 0.0F, false));
	bone.cubeList.add(new ModelBox(bone, 0, 22, -4.0F, -4.0F, -7.0F, 8, 8, 14, 0.0F, false));
	bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -5.0F, -6.0F, 10, 10, 12, 0.0F, false));
	bone.cubeList.add(new ModelBox(bone, 0, 44, -2.0F, -2.0F, -8.0F, 4, 4, 16, 0.0F, false));
	bone.cubeList.add(new ModelBox(bone, 40, 54, -7.0F, -4.0F, -4.0F, 14, 8, 8, 0.0F, false));
	bone.cubeList.add(new ModelBox(bone, 32, 0, -8.0F, -2.0F, -2.0F, 16, 4, 4, 0.0F, false));
	bone.cubeList.add(new ModelBox(bone, 34, 34, -6.0F, -5.0F, -5.0F, 12, 10, 10, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float ticks, float f3, float f4, float f5) {
	// Grow in size at beginning of life
	float scale = Math.min(1, ticks / 10);
	GlStateManager.scale(scale, scale, scale);
	bone.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
	// Do some rotations
	Vec3d lookVec = entityIn.getLookVec();
	Vec3d direction = ModUtils.rotateVector(lookVec, lookVec.crossProduct(new Vec3d(0, 1, 0)), (ageInTicks / 10) % 360);
	bone.rotateAngleX = (float) ModUtils.toPitch(direction);
	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
	modelRenderer.rotateAngleX = x;
	modelRenderer.rotateAngleY = y;
	modelRenderer.rotateAngleZ = z;
    }
}