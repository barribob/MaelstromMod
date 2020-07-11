package com.barribob.MaelstromMod.entity.animation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Manages animations of all entities. All that needs to be done is that it should be added to the entity to start the animation. Animations get automatically removed when the entity is removed or
 * dies.
 *
 * @author Barribob
 */
@Mod.EventBusSubscriber(value = Side.CLIENT)
@SideOnly(Side.CLIENT)
public class AnimationManager {
    private static Map<EntityLivingBase, Map<String, BBAnimation>> animations = new HashMap<EntityLivingBase, Map<String, BBAnimation>>();
    private static Map<ModelBase, Map<ModelRenderer, float[]>> defaultModelValues = new HashMap<ModelBase, Map<ModelRenderer, float[]>>();

    public static void updateAnimation(EntityLivingBase entity, String animationId, boolean remove) {
        if (remove) {
            removeAnimation(entity, animationId);
            return;
        }

        if (!animations.containsKey(entity)) {
            animations.put(entity, new HashMap<String, BBAnimation>());
        }

        if (!animations.get(entity).containsKey(animationId)) {
            animations.get(entity).put(animationId, new BBAnimation(animationId));
        }

        animations.get(entity).get(animationId).startAnimation();
    }

    private static void removeAnimation(EntityLivingBase entity, String animationId) {
        if (animations.containsKey(entity)) {
            if (animations.get(entity).containsKey(animationId)) {
                animations.get(entity).remove(animationId);
            }
        }
    }

    /**
     * Receive periodic updates to looping animation in case the animation is destroyed under certain conditions If the animation exists, it will not be updated
     */
    public static void updateLoopingAnimation(EntityLivingBase entity, String animationId) {
        if (!animations.containsKey(entity)) {
            animations.put(entity, new HashMap<String, BBAnimation>());
        }

        if (!animations.get(entity).containsKey(animationId)) {
            animations.get(entity).put(animationId, new BBAnimation(animationId));
        }
    }

    /**
     * Update the animations of every entity one tick forward
     *
     * @param event
     */
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == Phase.END && !Minecraft.getMinecraft().isGamePaused()) {
            List<EntityLivingBase> entitiesToRemove = new ArrayList<EntityLivingBase>();
            for (Entry<EntityLivingBase, Map<String, BBAnimation>> entry : animations.entrySet()) {
                EntityLivingBase entity = entry.getKey();

                if (!entity.isAddedToWorld() && entity.isDead) {
                    entitiesToRemove.add(entity);
                    continue;
                }

                // Pause animation on death
                if (entity.getHealth() <= 0) {
                    continue;
                }

                List<String> animsToRemove = new ArrayList<String>();
                for (Entry<String, BBAnimation> kv : entry.getValue().entrySet()) {
                    if (kv.getValue().isEnded()) {
                        animsToRemove.add(kv.getKey());
                    } else {
                        kv.getValue().update();
                    }
                }

                // Remove ended animations
                for (String id : animsToRemove) {
                    entry.getValue().remove(id);
                }
            }

            // Remove entities not in this world anymore
            for (EntityLivingBase entity : entitiesToRemove) {
                animations.remove(entity);
            }
        }
    }

    /**
     * This has to be separate from the animation below because we want to add in passive head and arm animations in the model itself.
     *
     * @param model
     */
    public static void resetModel(ModelBase model) {
        /**
         * This part solves an issue that comes from the fact that all instances of a particular entity share the same model and thus will each alter the models values. Those values can carry over to the next
         * entity who uses the model, so those values have to be reset before each entity get rendered with the model.
         */
        if (defaultModelValues.containsKey(model)) {
            for (ModelRenderer renderer : model.boxList) {
                float[] values = defaultModelValues.get(model).get(renderer);
                renderer.rotateAngleX = values[0];
                renderer.rotateAngleY = values[1];
                renderer.rotateAngleZ = values[2];
                renderer.offsetX = values[3];
                renderer.offsetY = values[4];
                renderer.offsetZ = values[5];
                renderer.rotationPointX = values[6];
                renderer.rotationPointY = values[7];
                renderer.rotationPointZ = values[8];
            }
        } else {
            defaultModelValues.put(model, new HashMap<ModelRenderer, float[]>());
            for (ModelRenderer renderer : model.boxList) {
                defaultModelValues.get(model).put(renderer, new float[]{
                        renderer.rotateAngleX,
                        renderer.rotateAngleY,
                        renderer.rotateAngleZ,
                        renderer.offsetX,
                        renderer.offsetY,
                        renderer.offsetZ,
                        renderer.rotationPointX,
                        renderer.rotationPointY,
                        renderer.rotationPointZ,
                });
            }
        }
    }

    public static void setModelRotations(ModelBase model, EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks) {
        // Update all models for each entity
        if (animations.containsKey(entity)) {
            for (BBAnimation animation : animations.get(entity).values()) {
                animation.setModelRotations(model, limbSwing, limbSwingAmount, entity.getHealth() <= 0 ? 0 : partialTicks);
            }
        }
    }
}
