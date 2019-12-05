package com.barribob.MaelstromMod.mana;

public class Mana implements IMana
{
    private float mana = 0;
    private boolean locked = true;
    public static final float MAX_MANA = 20;

    @Override
    public void consume(float amount)
    {
	if (locked)
	    return;
	this.mana = Math.max(0, this.mana - amount);
    }

    @Override
    public void replenish(float amount)
    {
	if (locked)
	    return;
	this.mana = Math.min(MAX_MANA, mana + amount);
    }

    @Override
    public void set(float amount)
    {
	this.mana = Math.min(Math.max(amount, 0), MAX_MANA);
    }

    @Override
    public float getMana()
    {
	return mana;
    }

    @Override
    public boolean isLocked()
    {
	return locked;
    }

    @Override
    public void setLocked(boolean locked)
    {
	this.locked = locked;
    }
}
