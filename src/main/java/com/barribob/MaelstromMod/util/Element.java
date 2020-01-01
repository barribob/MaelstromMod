package com.barribob.MaelstromMod.util;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;

/**
 * 
 * The data for the elemental system
 *
 */
public enum Element
{
    AZURE(new Vec3d(0.5, 0.5, 1.0), "\u03A6", TextFormatting.AQUA),
    GOLDEN(new Vec3d(1.0, 1.0, 0.5), "\u03A9", TextFormatting.YELLOW),
    CRIMSON(new Vec3d(1.0, 0.5, 0.5), "\u03A3", TextFormatting.RED),
    NONE(ModColors.WHITE, " ", TextFormatting.WHITE);

    public Vec3d sweepColor;
    public String symbol;
    public TextFormatting textColor;

    private Element(Vec3d sweepColor, String symbol, TextFormatting textColor)
    {
	this.sweepColor = sweepColor;
	this.symbol = symbol;
	this.textColor = textColor;
    }

    public boolean matchesElement(Element e)
    {
	return this == e;
    }
}
