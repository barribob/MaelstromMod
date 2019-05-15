package com.barribob.MaelstromMod.entity.action;

import java.util.List;
import java.util.function.Consumer;

import com.barribob.MaelstromMod.entity.entities.EntityHerobrineOne;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.Vec3d;

/*
 * Deals damage in an area around the entity
 */
public class ActionSpinSlash extends Action
{
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target)
    {
	float size = 2.2f;
	List<EntityLivingBase> entities = ModUtils.getEntitiesInBox(actor, actor.getEntityBoundingBox().grow(size, 0.5f, size));

	Consumer<EntityLivingBase> attack = e -> e.attackEntityFrom(ModDamageSource.causeMaelstromMeleeDamage(actor), actor.getAttack());

	if (entities != null)
	{
	    entities.forEach(attack);
	}

	actor.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F / (actor.getRNG().nextFloat() * 0.4F + 0.8F));

	if(actor instanceof EntityHerobrineOne)
	{
	    actor.world.setEntityState(actor, EntityHerobrineOne.slashParticleByte);
	}
    }
}
