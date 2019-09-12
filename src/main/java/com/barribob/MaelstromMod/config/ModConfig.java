package com.barribob.MaelstromMod.config;

import com.barribob.MaelstromMod.util.Reference;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * Configuration file for the mod
 *
 */
@Config(modid = Reference.MOD_ID, name = "MaelstromMod")
@Mod.EventBusSubscriber()
public class ModConfig
{
    @Config.Ignore
    private static final String config = Reference.MOD_ID + ".config.";

    @Config.RequiresMcRestart
    @Config.LangKey(config + "fracture_dimension_id")
    public static int fracture_dimension_id = 125;

    @Config.RequiresMcRestart
    @Config.LangKey(config + "nexus_dimension_id")
    public static int nexus_dimension_id = 126;

    @Config.RequiresMcRestart
    @Config.LangKey(config + "cliff_dimension_id")
    public static int cliff_dimension_id = 127;

    @Config.RequiresWorldRestart
    @Config.LangKey(config + "spawn_island")
    public static boolean spawn_island = true;

    @Config.LangKey(config + "gui")
    public static GuiCat gui = new GuiCat(0, 0, true);

    @Config.LangKey(config + "scale")
    @Config.RangeDouble(min = 1.1, max = 3)
    @Config.Comment("Determines how rapidly the weapons, armor, and mobs grow in difficulty.")
    public static float progression_scale = 2;

    public static class GuiCat
    {
	@Config.LangKey(config + "armor_bar_x")
	public int maelstrom_armor_bar_offset_x;

	@Config.LangKey(config + "armor_bar_y")
	public int maelstrom_armor_bar_offset_y;

	@Config.LangKey(config + "show_cooldown_bar")
	public boolean showGunCooldownBar;

	public GuiCat(int x, int y, boolean showCooldown)
	{
	    this.maelstrom_armor_bar_offset_x = x;
	    this.maelstrom_armor_bar_offset_y = y;
	    showGunCooldownBar = showCooldown;
	}
    }

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
	if (event.getModID().equals(Reference.MOD_ID))
	{
	    ConfigManager.sync(Reference.MOD_ID, Type.INSTANCE);
	}
    }
}
