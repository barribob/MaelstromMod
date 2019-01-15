package com.barribob.MaelstromMod.renderer;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/**
 * 
 * Get input from the mouse button to find raycast targets
 *
 */
public class InputOverrides
{
    /**
     * Sourced from the EntityRenderer class to get reach longer than 6
     */
    public static RayTraceResult getMouseOver(float partialTicks, Minecraft mc, double hitRange)
    {
	// Get the player viewing
	Entity player = mc.getRenderViewEntity();
	Entity pointedEntity = null;
	RayTraceResult result;
	double raycastLength = 20;

	if (player != null)
	{
	    if (mc.world != null)
	    {
		// Holds the reach distance for blocks (not entities)
		double d0 = raycastLength;
		result = player.rayTrace(d0, partialTicks);
		Vec3d playerEyes = player.getPositionEyes(partialTicks);
		boolean outOfHitRange = false;

		// Used in determining if the entity is within reach
		double d1 = d0;

		if (d0 > hitRange) // 3.0 is the entity hit distance I think
		{
		    outOfHitRange = true;
		}

		if (result != null)
		{
		    d1 = result.hitVec.distanceTo(playerEyes);
		}

		Vec3d vec3d1 = player.getLook(1.0F);
		Vec3d vec3d2 = playerEyes.addVector(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0);
		Vec3d vec3d3 = null;
		List<Entity> list = mc.world.getEntitiesInAABBexcluding(player,
			player.getEntityBoundingBox().expand(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0).grow(1.0D, 1.0D, 1.0D),
			Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
			{
			    public boolean apply(@Nullable Entity p_apply_1_)
			    {
				return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
			    }
			}));

		// A third double used for determining if an entity is in reach
		double d2 = d1;

		// Search for the pointed entity in the list of entities
		for (int j = 0; j < list.size(); ++j)
		{
		    Entity entity1 = list.get(j);
		    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow((double) entity1.getCollisionBorderSize());
		    RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(playerEyes, vec3d2);

		    if (axisalignedbb.contains(playerEyes)) // If the entity is literally in the players eyes?
		    {
			if (d2 >= 0.0D)
			{
			    pointedEntity = entity1;
			    vec3d3 = raytraceresult == null ? playerEyes : raytraceresult.hitVec;
			    d2 = 0.0D;
			}
		    }
		    else if (raytraceresult != null)
		    {
			double d3 = playerEyes.distanceTo(raytraceresult.hitVec);

			if (d3 < d2 || d2 == 0.0D)
			{
			    if (entity1.getLowestRidingEntity() == player.getLowestRidingEntity() && !entity1.canRiderInteract()) // Not sure what this is
			    {
				if (d2 == 0.0D)
				{
				    pointedEntity = entity1;
				    vec3d3 = raytraceresult.hitVec;
				}
			    }
			    else
			    {
				pointedEntity = entity1;
				vec3d3 = raytraceresult.hitVec;
				d2 = d3;
			    }
			}
		    }
		}

		if (pointedEntity != null && outOfHitRange && playerEyes.distanceTo(vec3d3) > hitRange)
		{
		    return new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, (EnumFacing) null, new BlockPos(vec3d3));
		}

		if (pointedEntity != null && (d2 < d1 || result == null))
		{
		    return new RayTraceResult(pointedEntity, vec3d3);
		}
	    }
	}

	return null;
    }
}
