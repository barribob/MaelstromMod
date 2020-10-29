package com.barribob.MaelstromMod.entity.entities.gauntlet;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class MovementTracker {
    public final Entity entity;
    private final int maxTicksTracking;
    private final List<Vec3d> positions = new ArrayList<>();

    public MovementTracker(Entity entity, int maxTicksTracking) {
        this.entity = entity;
        this.maxTicksTracking = maxTicksTracking;
    }

    public Vec3d getMovementOverTicks(int ticks) {
        if(ticks > maxTicksTracking) throw new IllegalArgumentException("Ticks was larger than tick tracking");
        ticks = Math.min(ticks, positions.size() - 1);
        Vec3d firstPos = positions.get(0);
        Vec3d secondPos = positions.get(ticks);
        return secondPos.subtract(firstPos);
    }

    public void onUpdate() {
        positions.add(entity.getPositionVector());
        if(positions.size() > maxTicksTracking) positions.remove(0);
    }
}
