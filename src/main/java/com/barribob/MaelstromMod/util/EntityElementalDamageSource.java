package com.barribob.MaelstromMod.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;

public class EntityElementalDamageSource extends EntityDamageSource implements IElement
{
    Element element;

    public EntityElementalDamageSource(String damageTypeIn, Entity damageSourceEntityIn, Element element)
    {
	super(damageTypeIn, damageSourceEntityIn);
	this.element = element;
    }

    @Override
    public Element getElement()
    {
	return element;
    }
}
