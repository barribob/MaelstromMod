package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityHerobrineOne;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;

/*
 * Deals damage in an area around the entity
 */
public class ActionSpinSlash extends Action {
    private float size = 2.2f;

    public ActionSpinSlash() {
    }

    public ActionSpinSlash(float size) {
        this.size = size;
    }

    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
        ModUtils.handleAreaImpact(size, (e) -> actor.getAttack(), actor, actor.getPositionVector(), ModDamageSource.causeElementalMeleeDamage(actor, actor.getElement()), 0.3f, actor.getElement().matchesElement(Element.CRIMSON) ? 3 : 0, false);

        actor.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F / (actor.getRNG().nextFloat() * 0.4F + 0.8F));

        actor.world.setEntityState(actor, EntityHerobrineOne.slashParticleByte);
    }
}
