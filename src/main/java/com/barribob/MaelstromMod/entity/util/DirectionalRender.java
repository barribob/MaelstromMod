package com.barribob.MaelstromMod.entity.util;

import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Allows mobs to communicate directions so that it can be indicated on the client (in the form of lazers for example)
 *
 * @author Barribob
 */
public interface DirectionalRender {
    @SideOnly(Side.CLIENT)
    public void setRenderDirection(Vec3d dir);
}
