package com.barribob.MaelstromMod.util;

import net.minecraft.util.math.Vec3d;

public class ModColors
{
    public static final Vec3d YELLOW = new Vec3d(0.8, 0.8, 0.4);
    public static final Vec3d PURPLE = new Vec3d(0.8, 0.4, 0.8);
    public static final Vec3d CLIFF_STONE = new Vec3d(0.7, 0.7, 0.5);
    public static final Vec3d WHITE = new Vec3d(1, 1, 1);
    public static final Vec3d BROWNSTONE = new Vec3d(0.8, 0.5, 0.0);
    public static final Vec3d AZURE = new Vec3d(0.2, 0.8, 0.8);
    public static final Vec3d ORANGE = new Vec3d(0.9, 0.7, 0.4);
    public static final Vec3d RED = new Vec3d(0.9, 0.1, 0.1);
    public static final Vec3d GREEN = new Vec3d(0.1, 0.9, 0.1);
    public static final Vec3d BLUE = new Vec3d(0.1, 0.1, 0.8);
    public static final Vec3d MAELSTROM = new Vec3d(0.3, 0.2, 0.4);
    public static final Vec3d DARK_GREY = new Vec3d(0.2, 0.2, 0.2);

    public static Vec3d variateColor(Vec3d baseColor, float variance)
    {
	float f = ModRandom.getFloat(variance);

	return new Vec3d((float) Math.min(Math.max(0, baseColor.x + f), 1),
		(float) Math.min(Math.max(0, baseColor.y + f), 1),
		(float) Math.min(Math.max(0, baseColor.z + f), 1));
    }

    public static int toIntegerColor(int r, int g, int b, int a)
    {
	int i = r << 16;
	i += g << 8;
	i += b;
	i += a << 24;
	return i;
    }
}
