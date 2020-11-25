package com.barribob.MaelstromMod.potions;

import com.barribob.MaelstromMod.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class ModPotion extends Potion {
    public ModPotion(String name, boolean isBadEffectIn, int liquidColorIn, int iconX, int iconY) {
        super(isBadEffectIn, liquidColorIn);
        this.setIconIndex(iconX, iconY);
        this.setPotionName(Reference.MOD_ID + ".effect." + name);
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID + ":" + name));
    }

    @Override
    public boolean hasStatusIcon() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/potion_effects.png"));
        return true;
    }
}
