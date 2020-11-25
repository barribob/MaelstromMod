package com.barribob.MaelstromMod.util;

import com.barribob.MaelstromMod.entity.animation.Animation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IAnimatedMob {
    public static final byte animationByte = 13;

    @SideOnly(Side.CLIENT)
    public Animation getCurrentAnimation();
}
