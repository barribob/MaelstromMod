package com.barribob.MaelstromMod.entity.util;

import java.util.HashMap;
import java.util.function.Supplier;

import com.barribob.MaelstromMod.entity.action.Action;
import com.barribob.MaelstromMod.entity.animation.Animation;
import com.barribob.MaelstromMod.entity.animation.AnimationNone;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * Designed to organize multiple attacks for a single entity Uses bytes to
 * handle the animations on the server side smoothly
 *
 */
public class ComboAttack
{
    private HashMap<Byte, Action> actions = new HashMap<Byte, Action>();

    @SideOnly(Side.CLIENT)
    private HashMap<Byte, Supplier<Animation>> animations;
    private byte currentAttack;

    public void setCurrentAttack(byte b)
    {
	currentAttack = b;
    }

    public byte getCurrentAttack()
    {
	return currentAttack;
    }

    public Action getCurrentAttackAction()
    {
	return getAction(currentAttack);
    }

    @SideOnly(Side.CLIENT)
    public void setAttack(byte b, Action action, Supplier<Animation> anim)
    {
	if (animations == null)
	{
	    animations = new HashMap<Byte, Supplier<Animation>>();
	}
	setAttack(b, action);
	animations.put(b, anim);
    }

    public void setAttack(byte b, Action action)
    {
	actions.put(b, action);
    }

    @SideOnly(Side.CLIENT)
    public Animation getAnimation(byte b)
    {
	if (animations == null)
	{
	    return new AnimationNone();
	}
	if (!animations.containsKey(b))
	{
	    throw new IllegalArgumentException("The byte " + b + " does not correspond to an attack");
	}
	return animations.get(b).get();
    }

    private Action getAction(byte b)
    {
	if (!actions.containsKey(b))
	{
	    throw new IllegalArgumentException("The byte " + b + " does not correspond to an attack");
	}
	return actions.get(b);
    }
}
