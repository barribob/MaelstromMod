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

    @Config.LangKey(config + "world")
    public static WorldCat world = new WorldCat();

    @Config.LangKey(config + "gui")
    public static GuiCat gui = new GuiCat(0, 0, 0, 0, true, true);

    @Config.LangKey(config + "balancing")
    public static BalanceCat balance = new BalanceCat(2, 1);

    public static class GuiCat
    {
	@Config.LangKey(config + "armor_bar_x")
	public int maelstrom_armor_bar_offset_x;

	@Config.LangKey(config + "armor_bar_y")
	public int maelstrom_armor_bar_offset_y;

	@Config.LangKey(config + "mana_bar_x")
	public int maelstrom_mana_bar_offset_x;

	@Config.LangKey(config + "mana_bar_y")
	public int maelstrom_mana_bar_offset_y;

	@Config.LangKey(config + "show_cooldown_bar")
	public boolean showGunCooldownBar;

	@Config.LangKey(config + "show_mana_bar")
	public boolean showManaBar;

	public GuiCat(int x, int y, int mana_x, int mana_y, boolean showCooldown, boolean showMana)
	{
	    this.maelstrom_armor_bar_offset_x = x;
	    this.maelstrom_armor_bar_offset_y = y;
	    this.maelstrom_mana_bar_offset_x = mana_x;
	    this.maelstrom_mana_bar_offset_y = mana_y;
	    showGunCooldownBar = showCooldown;
	    showManaBar = showMana;
	}
    }

    public static class BalanceCat
    {
	@Config.LangKey(config + "scale")
	@Config.RangeDouble(min = 1.1, max = 3)
	@Config.Comment("Determines how rapidly the weapons, armor, and mobs grow in difficulty.")
	public float progression_scale;

	@Config.LangKey(config + "mob_damage")
	@Config.RangeDouble(min = 0.5, max = 3)
	@Config.Comment("Scales the base damage of mobs in this mod.")
	public float mob_damage;

	public BalanceCat(float progression_scale, float mob_damage)
	{
	    this.progression_scale = progression_scale;
	    this.mob_damage = mob_damage;
	}
    }

    public static class WorldCat
    {
	@Config.RequiresMcRestart
	@Config.LangKey(config + "fracture_dimension_id")
	public int fracture_dimension_id = 125;

	@Config.RequiresMcRestart
	@Config.LangKey(config + "nexus_dimension_id")
	public int nexus_dimension_id = 126;

	@Config.RequiresMcRestart
	@Config.LangKey(config + "cliff_dimension_id")
	public int cliff_dimension_id = 127;

	@Config.RequiresMcRestart
	@Config.LangKey(config + "dark_nexus_dimension_id")
	public int dark_nexus_dimension_id = 128;

	@Config.RequiresWorldRestart
	@Config.LangKey(config + "spawn_island")
	public boolean spawn_island = true;

	@Config.LangKey(config + "spawn_island")
	public int invasionTime = 200;
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
