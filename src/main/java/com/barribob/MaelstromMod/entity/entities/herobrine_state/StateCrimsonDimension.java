package com.barribob.MaelstromMod.entity.entities.herobrine_state;

import com.barribob.MaelstromMod.entity.entities.Herobrine;
import com.barribob.MaelstromMod.util.TimedMessager;

public class StateCrimsonDimension extends HerobrineState
{
    private TimedMessager messager;
    private boolean leftClickMessage = false;

    public StateCrimsonDimension(Herobrine herobrine)
    {
	super(herobrine);
	messager = new TimedMessager(
		new String[] { "herobrine_crimson_dimension_0", "" },
		new int[] { 60, 61 },
		(s) -> {
	});
    }
    
    @Override
    public void update()
    {
	messager.Update(world, messageToPlayers);
    }

    @Override
    public void leftClick(Herobrine herobrine)
    {
	if (!this.leftClickMessage)
	{
	    messageToPlayers.accept("herobrine_crimson_dimension_1");
	    leftClickMessage = true;
	}
	super.leftClick(herobrine);
    }

    @Override
    public String getNbtString()
    {
	return "crimson_dimension";
    }

}
