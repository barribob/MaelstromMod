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
    public static BalanceCat balance = new BalanceCat();

    @Config.LangKey(config + "entities")
    public static EntityCat entities = new EntityCat();

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
	public float progression_scale = 1.25f;

	@Config.LangKey(config + "mob_damage")
	@Config.RangeDouble(min = 0.5, max = 3)
	@Config.Comment("Scales the base damage of mobs in this mod.")
	public float mob_damage = 1.25f;

	@Config.LangKey(config + "mob_armor")
	@Config.RangeDouble(min = 0.1, max = 1.0)
	@Config.Comment("Amount of additional damage reduction on mobs.")
	public float mob_armor = 1.0f;

	@Config.LangKey(config + "weapon_damage")
	@Config.RangeDouble(min = 1.0, max = 3)
	@Config.Comment("Base damage multiplier for weapons in this mod.")
	public float weapon_damage = 1.0f;

	@Config.LangKey(config + "armor_toughness")
	@Config.RangeDouble(min = 0.5, max = 5)
	@Config.Comment("Specifies the strength of the mod's base armor material.")
	public float armor_toughness = 3.0f;

	@Config.LangKey(config + "elemental_factor")
	@Config.RangeDouble(min = 1.0, max = 3)
	@Config.Comment("Represents how important using the correct color (or element) is")
	public float elemental_factor = 1.5f;
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


	@Config.LangKey(config + "invasion_time")
	@Config.Comment("How many seconds before attempting to spawn the invasion tower. Cannot be adjusted after the world is created.")
	@Config.RangeInt(min = 60, max = 60 * 1000)
	public int invasionTime = 60 * 180; // Default 180 minutes before invasion
    }

    public static class EntityCat
    {
	@Config.LangKey(config + "use_vanilla_pathfinding")
	@Config.Comment("If there is another mod that improves the vanilla pathfinding ai, then set this to true. Takes effect after reloading the world.")
	public boolean useVanillaPathfinding = false;

	@Config.LangKey(config + "attack_all")
	@Config.Comment("Whether maelstrom mobs should attack any living entity they see. Takes effect after reloading the world.")
	public boolean attackAll = true;

	@Config.LangKey(config + "display_mob_level")
	@Config.Comment("Display the level of most mobs above their nametag.")
	public boolean displayLevel = false;
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
