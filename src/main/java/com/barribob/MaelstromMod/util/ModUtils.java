package com.barribob.MaelstromMod.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;

public class ModUtils
{
    public static List<EntityLivingBase> getEntitiesInBox(Entity entity, AxisAlignedBB bb)
    {
	List<Entity> list = entity.world.getEntitiesWithinAABBExcludingEntity(entity, bb);
	
	if(list != null)
	{	    
	    Predicate<Entity> isInstance = i -> i instanceof EntityLivingBase;
	    Function<Entity, EntityLivingBase> cast = i -> (EntityLivingBase)i;
	    
	    return list.stream().filter(isInstance).map(cast).collect(Collectors.toList());
	}
	
	return null;
    }
}
