package com.barribob.MaelstromMod;

import com.barribob.MaelstromMod.commands.CommandDimensionTeleport;
import com.barribob.MaelstromMod.commands.CommandInvasion;
import com.barribob.MaelstromMod.commands.CommandReloadConfigs;
import com.barribob.MaelstromMod.config.JsonConfigManager;
import com.barribob.MaelstromMod.init.*;
import com.barribob.MaelstromMod.loot.functions.ModEnchantWithLevels;
import com.barribob.MaelstromMod.proxy.CommonProxy;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import com.barribob.MaelstromMod.world.gen.WorldGenCustomStructures;
import com.barribob.MaelstromMod.world.gen.WorldGenOre;
import com.typesafe.config.Config;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

/**
 * Main mod class Many of the base boilerplate here is thanks to loremaster's
 * tutorials https://www.youtube.com/channel/UC3n-lKS-MYlunVtErgzSFZg Entities,
 * world generation, and dimension frameworks are thanks to Harry Talks
 * https://www.youtube.com/channel/UCUAawSqNFBEj-bxguJyJL9g Also thanks to
 * Julian Abelar for a bunch of modding tutorials and articles
 * https://jabelarminecraft.blogspot.com/
 * <p>
 * Also other tools that I used: World Edit from Single Player Commands, as well as MCEdit
 */
@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, updateJSON = "https://raw.githubusercontent.com/miyo6032/MaelstromMod/LibraryIntegration/update.json")
public class Main {
    @Instance
    public static Main instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    public static SimpleNetworkWrapper network;

    public static final JsonConfigManager CONFIG_MANAGER = new JsonConfigManager();
    public static Config itemsConfig;

    public static Logger log;

    public static final String CONFIG_DIRECTORY_NAME = "Maelstrom Mod";

    /**
     * Basically initializes the entire mod by calling all of the init methods in
     * the static classes
     */
    @EventHandler
    public static void PreInit(FMLPreInitializationEvent event) {
        log = event.getModLog();

        loadConfigs();

        GameRegistry.registerWorldGenerator(new WorldGenOre(), 2);
        GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 3);

        ModEntities.registerEntities();
        proxy.init();

        ModBBAnimations.registerAnimations();
        ModDimensions.registerDimensions();
        LootFunctionManager.registerFunction(new ModEnchantWithLevels.Serializer());
    }

    @EventHandler
    public static void Init(FMLInitializationEvent event) {
        ModRecipes.init();
        SoundsHandler.registerSounds();
        ModStructures.registerStructures();
        ModProfessions.associateCareersAndTrades();
    }

    @EventHandler
    public static void PostInit(FMLPostInitializationEvent event) {

    }

    @EventHandler
    public static void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandDimensionTeleport());
        event.registerServerCommand(new CommandReloadConfigs());
        event.registerServerCommand(new CommandInvasion());
    }

    public static void loadConfigs() {
        itemsConfig = CONFIG_MANAGER.handleConfigLoad(CONFIG_DIRECTORY_NAME, "items");
    }
}
