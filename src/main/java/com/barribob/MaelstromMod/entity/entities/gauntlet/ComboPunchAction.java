package com.barribob.MaelstromMod.entity.entities.gauntlet;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.util.ModRandom;

import java.util.Random;

public class ComboPunchAction implements IGauntletAction {
    private final ModRandom.RandomCollection<IGauntletAction> punchActions;
    private final EntityLeveledMob entity;
    private final Random rand = new Random();
    private IGauntletAction currentAction;
    int numPunches;

    public ComboPunchAction(ModRandom.RandomCollection<IGauntletAction> punchActions, EntityLeveledMob entity) {
        this.punchActions = punchActions;
        this.entity = entity;
    }

    @Override
    public void doAction() {
        numPunches = 2 + rand.nextInt(4);
        for (int i = 0; i < numPunches; i++) {
            entity.addEvent(() -> {
                currentAction = punchActions.next();
                currentAction.doAction();
            }, i * 60);
        }
    }

    @Override
    public void update() {
        if (currentAction != null) {
            currentAction.update();
        }
    }

    @Override
    public int attackLength() {
        return ((numPunches - 1) * 60) + 53;
    }

    @Override
    public boolean shouldExplodeUponImpact() {
        return currentAction != null && currentAction.shouldExplodeUponImpact();
    }

    @Override
    public boolean isImmuneToDamage() {
        return currentAction != null && currentAction.isImmuneToDamage();
    }
}
