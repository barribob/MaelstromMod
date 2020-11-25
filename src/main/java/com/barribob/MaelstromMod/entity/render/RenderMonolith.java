package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.EntityMonolith;
import com.barribob.MaelstromMod.entity.model.ModelMonolith;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.RenderUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class RenderMonolith extends RenderModEntity<EntityMonolith> {
    public ResourceLocation TEXTURES_1 = new ResourceLocation(Reference.MOD_ID + ":textures/entity/monolith.png");
    public ResourceLocation TEXTURES_2 = new ResourceLocation(Reference.MOD_ID + ":textures/entity/monolith_blue.png");
    public ResourceLocation TEXTURES_3 = new ResourceLocation(Reference.MOD_ID + ":textures/entity/monolith_red.png");
    public ResourceLocation TEXTURES_4 = new ResourceLocation(Reference.MOD_ID + ":textures/entity/monolith_yellow.png");

    public RenderMonolith(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelMonolith(), "monolith.png");
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMonolith entity) {
        switch (entity.getAttackColor()) {
            case EntityMonolith.noAttack:
                return TEXTURES_1;
            case EntityMonolith.blueAttack:
                return TEXTURES_2;
            case EntityMonolith.redAttack:
                return TEXTURES_3;
            case EntityMonolith.yellowAttack:
                return TEXTURES_4;
        }
        return TEXTURES_1;
    }

    @Override
    public void doRender(EntityMonolith entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        // Render the monolith lazer. Taken from the guardian lazer thingy
        if (entity.getTarget().isPresent()) {
            RenderUtils.drawLazer(renderManager, entity.getPositionVector().add(ModUtils.yVec(entity.getEyeHeight())), entity.getTarget().get(), new Vec3d(x, y, z), new Vec3d(1, 0, 0), entity, partialTicks);
        }
    }
}
