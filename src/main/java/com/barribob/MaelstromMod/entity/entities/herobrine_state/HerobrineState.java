package com.barribob.MaelstromMod.entity.entities.herobrine_state;

import java.util.function.Consumer;

import com.barribob.MaelstromMod.entity.entities.Herobrine;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * Allows easy swapping of states for the herobrine npc
 */
public abstract class HerobrineState
{
    World world;
    Herobrine herobrine;

    protected Consumer<String> messageToPlayers = (message) -> {
	if (message != "")
	{
	    for (EntityPlayer player : herobrine.bossInfo.getPlayers())
	    {
		player.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + herobrine.getDisplayName().getFormattedText() + ": " + TextFormatting.WHITE)
			.appendSibling(new TextComponentTranslation(ModUtils.LANG_CHAT + message)));
	    }
	}
    };

    public HerobrineState(Herobrine herobrine)
    {
	this.world = herobrine.world;
	this.herobrine = herobrine;
    }

    public abstract String getNbtString();

    public void update()
    {
    }

    public void rightClick(EntityPlayer player)
    {
    }

    public void leftClick(Herobrine herobrine)
    {
    }
}
