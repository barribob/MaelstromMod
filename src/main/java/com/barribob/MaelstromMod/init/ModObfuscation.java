package com.barribob.MaelstromMod.init;

import java.util.HashMap;
import java.util.Map;

public class ModObfuscation {
    public static final Map<String, String> toObfuscated = new HashMap<String, String>();

    static {
        toObfuscated.put("rotateAngleX", "field_78795_f");
        toObfuscated.put("rotateAngleY", "field_78796_g");
        toObfuscated.put("rotateAngleZ", "field_78808_h");
    }
}
