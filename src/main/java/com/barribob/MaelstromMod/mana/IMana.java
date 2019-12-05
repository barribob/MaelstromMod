package com.barribob.MaelstromMod.mana;

public interface IMana
{
    public void consume(float amount);

    public void replenish(float amount);

    public void set(float amount);

    public float getMana();

    public boolean isLocked();

    public void setLocked(boolean locked);
}
