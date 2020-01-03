package com.barribob.MaelstromMod.util;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;

/**
 * 
 * The data for the elemental system
 *
 */
public enum Element
{
    AZURE(new Vec3d(0.5, 0.5, 1.0), "\u03A6", TextFormatting.AQUA, 0),
    GOLDEN(new Vec3d(1.0, 1.0, 0.5), "\u03A9", TextFormatting.YELLOW, 1),
    CRIMSON(new Vec3d(1.0, 0.5, 0.5), "\u03A3", TextFormatting.RED, 2),
    NONE(ModColors.WHITE, " ", TextFormatting.WHITE, 3);

    public Vec3d sweepColor;
    public String symbol;
    public TextFormatting textColor;
    public int id;
    private static final Map<Integer, Element> FROM_ID = Maps.<Integer, Element>newHashMap();

    private Element(Vec3d sweepColor, String symbol, TextFormatting textColor, int id)
    {
	this.sweepColor = sweepColor;
	this.symbol = symbol;
	this.textColor = textColor;
	this.id = id;
    }

    public boolean matchesElement(Element e)
    {
	return this == e;
    }

    public static Element getElementFromId(int id)
    {
	return FROM_ID.get(Integer.valueOf(id));
    }

    static
    {
	for (Element element : values())
	{
	    FROM_ID.put(Integer.valueOf(element.id), element);
	}
    }
}
