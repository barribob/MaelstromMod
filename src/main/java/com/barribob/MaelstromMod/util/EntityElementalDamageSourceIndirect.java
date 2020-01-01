package com.barribob.MaelstromMod.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSourceIndirect;

public class EntityElementalDamageSourceIndirect extends EntityDamageSourceIndirect implements IElement
{
    Element element;

    public EntityElementalDamageSourceIndirect(String damageTypeIn, Entity source, Entity indirectEntityIn, Element element)
    {
	super(damageTypeIn, source, indirectEntityIn);
	this.element = element;
    }

    @Override
    public Element getElement()
    {
	return element;
    }

}
