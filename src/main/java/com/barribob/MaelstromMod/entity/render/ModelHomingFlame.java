package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.client.model.ModelShulkerBullet;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class ModelHomingFlame extends ModelShulkerBullet {
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        Vec3d lookVec = ModUtils.getEntityVelocity(entityIn).normalize();
        Vec3d direction = ModUtils.rotateVector2(lookVec, lookVec.crossProduct(ModUtils.Y_AXIS), (ageInTicks * 10) % 360);
        Vec2f pitchYaw = ModUtils.getPitchYaw(direction);
        renderer.rotateAngleX = (float) Math.toRadians(pitchYaw.y);
        renderer.rotateAngleY = (float) Math.toRadians(pitchYaw.x);
    }
}
