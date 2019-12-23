package com.barribob.MaelstromMod.invasion;

public interface IInvasion
{
    public boolean isInvaded();

    public void setInvaded(boolean invaded);

    public void setInvasionTime(long time);

    public long getInvasionTime();

    public void update();

    public boolean shouldDoInvasion();
}
