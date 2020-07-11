package com.barribob.MaelstromMod.entity.util;

/**
 * Override to implement functionality whenever an entity lands on the ground
 * after being airborne
 */
public interface LeapingEntity {
    public boolean isLeaping();

    public void setLeaping(boolean leaping);

    public void onStopLeaping();
}
