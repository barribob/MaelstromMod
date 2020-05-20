package com.barribob.MaelstromMod.entity.render;

import com.barribob.MaelstromMod.entity.entities.gauntlet.EntityMaelstromGauntlet;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromGauntlet;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderMaelstromGauntlet extends RenderModEntity<EntityMaelstromGauntlet> {
    public RenderMaelstromGauntlet(RenderManager rendermanagerIn, String... textures) {
	super(rendermanagerIn, new ModelMaelstromGauntlet(), textures);
    }

    /**
     * Change to hurt texture whenever the gauntlet takes damage
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityMaelstromGauntlet entity) {
	if (entity.hurtTime > 0) {
	    return new ResourceLocation(Reference.MOD_ID + ":textures/entity/maelstrom_gauntlet_hurt.png");
	}
	else if (entity.getHealth() / entity.getMaxHealth() < 0.55) {
	    return new ResourceLocation(Reference.MOD_ID + ":textures/entity/maelstrom_gauntlet_low_health.png");
	}
	return super.getEntityTexture(entity);
    }
}
