package com.barribob.MaelstromMod.entity.entities.gauntlet;

import com.barribob.MaelstromMod.entity.util.IAttackInitiator;
import net.minecraft.entity.EntityLivingBase;

import java.util.function.Function;

public class GauntletAttackInitiator implements IAttackInitiator {
    private int attackTime;
    private final int startingCooldown;
    private int attackCooldown;
    private final Function<EntityLivingBase, IGauntletAction> attack;
    private final Function<EntityLivingBase, IGauntletAction> overrideAction;

    public GauntletAttackInitiator(int startingCooldown, Function<EntityLivingBase, IGauntletAction> attack, Function<EntityLivingBase, IGauntletAction> overrideAction) {
        this.attack = attack;
        this.overrideAction = overrideAction;
        this.startingCooldown = startingCooldown;
        this.attackTime = startingCooldown;
    }

    @Override
    public void update(EntityLivingBase target) {
        attackTime--;
        if (attackTime <= 0) {
            IGauntletAction action = attack.apply(target);
            attackTime = action.attackLength() + action.attackCooldown();
            attackCooldown = action.attackCooldown();
        }
        else if (attackTime <= attackCooldown) {
            IGauntletAction action = overrideAction.apply(target);
            if (action != null) {
                attackTime = action.attackLength() + action.attackCooldown();
                attackCooldown = action.attackCooldown();
            }
        }
    }

    @Override
    public void resetTask() {
        this.attackTime = Math.max(attackTime, startingCooldown);
        attackCooldown = 0;
    }
}
