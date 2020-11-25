package com.barribob.MaelstromMod.event_handlers;

import com.barribob.MaelstromMod.util.Reference;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Event listener for syncing up the config file
 * https://github.com/MinecraftForge/MinecraftForge/blob/603903db507a483fefd90445fd2b3bdafeb4b5e0/src/test/java/net/minecraftforge/debug/ConfigTest.java
 */
public class ConfigSync {
    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            ConfigManager.sync(Reference.MOD_ID, Type.INSTANCE);
        }
    }
}
