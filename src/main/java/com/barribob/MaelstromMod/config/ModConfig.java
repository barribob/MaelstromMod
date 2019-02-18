package com.barribob.MaelstromMod.config;

import com.barribob.MaelstromMod.util.Reference;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.Type;

/**
 * 
 * Configuration file for the mod
 *
 */
@LangKey("config_test.config.types")
@Config(modid = Reference.MOD_ID, type = Type.INSTANCE, name = Reference.MOD_ID)
public class ModConfig
{
    @Config.RequiresMcRestart
    @Name("Fracture Dimension Id")
    public static int fracture_dimension_id = 125;

    @Name("Gui")
    public static GuiCat gui = new GuiCat(0, 0);
    
    @Config.RequiresMcRestart
    @Name("Progression Scale")
    public static float progression_scale = 2;

    public static class GuiCat
    {
	@Config.RequiresMcRestart
	public int maelstrom_armor_bar_offset_x;
	@Config.RequiresMcRestart
	public int maelstrom_armor_bar_offset_y;

	public GuiCat(int x, int y)
	{
	    this.maelstrom_armor_bar_offset_x = x;
	    this.maelstrom_armor_bar_offset_y = y;
	}
    }
}
