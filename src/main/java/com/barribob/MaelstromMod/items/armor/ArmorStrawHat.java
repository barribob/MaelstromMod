package com.barribob.MaelstromMod.items.armor;

import com.barribob.MaelstromMod.items.armor.model.ModelStrawHat;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmorStrawHat extends ModArmorBase {
    public ArmorStrawHat(String name, ArmorMaterial materialIn, int renderIndex, EntityEquipmentSlot equipmentSlotIn, float maelstrom_armor, String textureName) {
        super(name, materialIn, renderIndex, equipmentSlotIn, maelstrom_armor, textureName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected ModelBiped getCustomModel() {
        return new ModelStrawHat();
    }
}
