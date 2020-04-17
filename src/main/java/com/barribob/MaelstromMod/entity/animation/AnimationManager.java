package com.barribob.MaelstromMod.entity.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Manages animations of all entities. All that needs to be done is that it should be added to the entity to start the animation. Animations get automatically removed when the entity is removed or
 * dies.
 * 
 * @author Barribob
 *
 */
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class AnimationManager
{
    @SideOnly(Side.CLIENT)
    private static Map<EntityLivingBase, Map<String, BBAnimation>> animations = new HashMap<EntityLivingBase, Map<String, BBAnimation>>();
    @SideOnly(Side.CLIENT)
    private static Map<ModelBase, Map<ModelRenderer, float[]>> defaultModelValues = new HashMap<ModelBase, Map<ModelRenderer, float[]>>();

    public static void addAnimation(EntityLivingBase entity, String animationId)
    {
	if (animations.containsKey(entity))
	{
	    animations.get(entity).get(animationId).startAnimation();
	}

	animations.put(entity, new HashMap<String, BBAnimation>());
	animations.get(entity).put(animationId, new BBAnimation(animationId));
    }

    /**
     * Update the animations of every entity one tick forward
     * 
     * @param event
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent()
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
	if (event.phase == Phase.END)
	{
	    List<EntityLivingBase> toRemove = new ArrayList<EntityLivingBase>();
	    for (Entry<EntityLivingBase, Map<String, BBAnimation>> entry : animations.entrySet())
	    {
		EntityLivingBase entity = entry.getKey();

		if (!entity.isAddedToWorld())
		{
		    toRemove.add(entity);
		    continue;
		}

		// Pause animation on death
		if (entity.getHealth() < 0)
		{
		    continue;
		}

		for (BBAnimation animation : entry.getValue().values())
		{
		    animation.update();
		}
	    }

	    for (EntityLivingBase entity : toRemove)
	    {
		animations.remove(entity);
	    }
	}
    }

    @SideOnly(Side.CLIENT)
    public static void setModelRotations(ModelBase model, EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks)
    {
	/**
	 * This part solves an issue that comes from the fact that all instances of a particular entity share the same model and thus will each alter the models values. Those values can carry over to the next
	 * entity who uses the model, so those values have to be reset before each entity get rendered with the model.
	 */
	if (defaultModelValues.containsKey(model))
	{
	    for (ModelRenderer renderer : model.boxList)
	    {
		float[] values = defaultModelValues.get(model).get(renderer);
		renderer.rotateAngleX = values[0];
		renderer.rotateAngleY = values[1];
		renderer.rotateAngleZ = values[2];
	    }
	}
	else
	{
	    defaultModelValues.put(model, new HashMap<ModelRenderer, float[]>());
	    for (ModelRenderer renderer : model.boxList)
	    {
		defaultModelValues.get(model).put(renderer, new float[] { renderer.rotateAngleX, renderer.rotateAngleY, renderer.rotateAngleZ });
	    }
	}

	// Update all models for each entity
	if (animations.containsKey(entity))
	{
	    for (BBAnimation animation : animations.get(entity).values())
	    {
		animation.setModelRotations(model, limbSwing, limbSwingAmount, partialTicks);
	    }
	}
    }
}
