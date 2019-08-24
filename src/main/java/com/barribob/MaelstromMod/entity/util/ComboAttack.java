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
    private Byte currentAttack;

    public void setCurrentAttack(Byte b)
    {
	currentAttack = b;
    }

    public Byte getCurrentAttack()
    {
	return currentAttack;
    }

    public Action getCurrentAttackAction()
    {
	return getAction(currentAttack);
    }

    @SideOnly(Side.CLIENT)
    public void addAttack(Byte b, Action action, Supplier<Animation> anim)
    {
	if (animations == null)
	{
	    animations = new HashMap<Byte, Supplier<Animation>>();
	}
	if (actions.containsKey(b) || animations.containsKey(b))
	{
	    throw new IllegalArgumentException("The byte " + b + " was already registered.");
	}
	addAttack(b, action);
	animations.put(b, anim);
    }

    public void addAttack(Byte b, Action action)
    {
	if (actions.containsKey(b))
	{
	    throw new IllegalArgumentException("The byte " + b + " was already registered.");
	}
	actions.put(b, action);
    }

    @SideOnly(Side.CLIENT)
    public Animation getAnimation(Byte b)
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

    private Action getAction(Byte b)
    {
	if (!actions.containsKey(b))
	{
	    throw new IllegalArgumentException("The byte " + b + " does not correspond to an attack");
	}
	return actions.get(b);
    }
}
