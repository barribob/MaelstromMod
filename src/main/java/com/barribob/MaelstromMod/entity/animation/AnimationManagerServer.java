package com.barribob.MaelstromMod.entity.animation;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.packets.MessageLoopAnimationUpdate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Purpose of a server animation manager is to make sure client doesn't "lose" looping animations over time. I don't with normal animations because I don't expect them to be longer than a few seconds.
 *
 * @author Barribob
 */
@Mod.EventBusSubscriber
public class AnimationManagerServer {
    private static Map<EntityLivingBase, Map<String, Integer>> loopingAnimations = new HashMap<EntityLivingBase, Map<String, Integer>>();
    /**
     * How often the server should send updates of an animation to the client. The reason why we need the server to periodically update all clients is because if an animation is added before a client
     * joins the world (is especially problematic for looping animations) or a more interesting problem where going out of the "tracking range" of an enemy will cause that enemy to effectively be
     * destroyed on the client side. This means that any existing animations will disappear when the enemy comes into view again.
     */
    private static final int REFRESH_RATE = 20;

    public static void updateLooping(EntityLivingBase entity, String animationId, boolean remove) {
        if (remove) {
            if (loopingAnimations.containsKey(entity)) {
                if (loopingAnimations.get(entity).containsKey(animationId)) {
                    loopingAnimations.get(entity).remove(animationId);
                    return;
                }
            }
        }

        if (!loopingAnimations.containsKey(entity)) {
            loopingAnimations.put(entity, new HashMap<String, Integer>());
        }

        if (!loopingAnimations.get(entity).containsKey(animationId)) {
            loopingAnimations.get(entity).put(animationId, entity.ticksExisted);
        }
    }

    /**
     * The server periodically will update the clients with any looping animations it finds to solve the problem with failing looping animations as mentioned above
     *
     * @param event
     */
    @SubscribeEvent
    public static void onServerTick(TickEvent.WorldTickEvent event) {
        if (event.phase == Phase.END) {
            List<EntityLivingBase> entitiesToRemove = new ArrayList<EntityLivingBase>();
            for (Entry<EntityLivingBase, Map<String, Integer>> entry : loopingAnimations.entrySet()) {
                EntityLivingBase entity = entry.getKey();

                if (!entity.isAddedToWorld() && entity.isDead) {
                    entitiesToRemove.add(entity);
                    continue;
                }

                for (Entry<String, Integer> kv : entry.getValue().entrySet()) {
                    if (kv.getValue() % REFRESH_RATE == 0) {
                        Main.network.sendToAllTracking(new MessageLoopAnimationUpdate(ModBBAnimations.getAnimationId(kv.getKey()), entity.getEntityId()), entity);
                    }
                    kv.setValue(kv.getValue() + 1);
                }
            }

            // Remove entities not in this world anymore
            for (EntityLivingBase entity : entitiesToRemove) {
                loopingAnimations.remove(entity);
            }
        }
    }

}
