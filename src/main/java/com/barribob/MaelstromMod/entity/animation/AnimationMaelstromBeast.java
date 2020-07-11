package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.entity.model.ModelMaelstromBeast;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class AnimationMaelstromBeast extends StreamAnimation<ModelMaelstromBeast> {
    public AnimationMaelstromBeast(List animations) {
        super(animations);
    }

    @Override
    public void setModelRotations(ModelMaelstromBeast model, float limbSwing, float limbSwingAmount, float partialTicks) {
        float limbSwingFactor = 0.4f;
        model.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * limbSwingFactor;
        model.rightArm.rotateAngleZ = 0;

        model.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * limbSwingFactor;

        model.body.rotateAngleX = model.defaultBodyRotation;
        model.body.rotateAngleY = 0;

        model.hammer_handle.rotateAngleX = 0;

        model.lowerJaw.rotateAngleX = 0;
        super.setModelRotations(model, limbSwing, limbSwingAmount, partialTicks);
    }
}
