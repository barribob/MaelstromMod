package com.barribob.MaelstromMod.invasion;

import com.barribob.MaelstromMod.config.ModConfig;

public class Invasion implements IInvasion
{
    private long timeUntilInvasion = ModConfig.world.invasionTime * 20;
    private boolean invaded = false;

    @Override
    public boolean isInvaded()
    {
	return this.invaded;
    }

    @Override
    public void setInvaded(boolean invaded)
    {
	this.invaded = invaded;
    }

    @Override
    public void update()
    {
	this.timeUntilInvasion = Math.max(0, this.timeUntilInvasion - 1);
    }

    @Override
    public boolean shouldDoInvasion()
    {
	return !invaded && timeUntilInvasion <= 0;
    }

    @Override
    public void setInvasionTime(long time)
    {
	this.timeUntilInvasion = time;
    }

    @Override
    public long getInvasionTime()
    {
	return this.timeUntilInvasion;
    }
}
