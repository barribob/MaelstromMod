package com.barribob.MaelstromMod.entity.util;

import java.util.HashMap;
import java.util.function.Supplier;

import com.barribob.MaelstromMod.entity.action.Action;
import com.barribob.MaelstromMod.entity.animation.Animation;
import com.barribob.MaelstromMod.entity.animation.AnimationNone;

/**
 * 
 * Designed to organize multiple attacks for a single entity
 * Uses bytes to handle the animations on the server side smoothly
 *
 */
public class ComboAttack
{
    private HashMap<Byte, Action> actions = new HashMap<Byte, Action>();
    private HashMap<Byte, Supplier<Animation>> animations = new HashMap<Byte, Supplier<Animation>>();
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

    public void addAttack(Byte b, Action action, Supplier<Animation> anim)
    {
	if (actions.containsKey(b) || animations.containsKey(b))
	{
	    throw new IllegalArgumentException("The byte " + b + " was already registered.");
	}
	actions.put(b, action);
	animations.put(b, anim);
    }

    public void addAttack(Byte b, Action action)
    {
	addAttack(b, action, () -> new AnimationNone());
    }

    public Animation getAnimation(Byte b)
    {
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
