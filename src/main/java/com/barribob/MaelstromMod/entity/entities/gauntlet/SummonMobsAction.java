package com.barribob.MaelstromMod.entity.entities.gauntlet;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.Entity;

public class SummonMobsAction implements IGauntletAction {

    private final Runnable spawningAlgorithm;
    private final EntityLeveledMob entity;
    private final Entity fistHitbox;
    private boolean isDefending;

    public SummonMobsAction(Runnable spawningAlgorithm, EntityLeveledMob entity, Entity fistHitbox) {
        this.spawningAlgorithm = spawningAlgorithm;
        this.entity = entity;
        this.fistHitbox = fistHitbox;
    }

    @Override
    public void doAction() {
        ModBBAnimations.animation(entity, "gauntlet.summon", false);
        int closeFistTime = 10;
        int openFistTime = 210;
        int summoningTime = openFistTime - closeFistTime;
        entity.addEvent(() -> {
            this.isDefending = true;
            fistHitbox.width = 2.5f;
            fistHitbox.height = 2f;
        }, closeFistTime);
        entity.addEvent(() -> {
            this.isDefending = false;
            fistHitbox.width = 0;
            fistHitbox.height = 0;
        }, openFistTime);

        int spawnInterval = Math.max(1, summoningTime / entity.getMobConfig().getInt("mobs_per_spawn"));
        for (int i = closeFistTime; i < summoningTime; i += spawnInterval) {
            entity.addEvent(spawningAlgorithm, i);
        }
    }

    @Override
    public void update() {
        if(this.isDefending) entity.world.setEntityState(entity, ModUtils.SECOND_PARTICLE_BYTE);
    }

    @Override
    public boolean isImmuneToDamage() {
        return isDefending;
    }

    @Override
    public int attackLength() {
        return 210;
    }
}
