package com.barribob.MaelstromMod.items.armor;

import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ModModeledArmor extends ModArmorBase
{
    private String textureName;
    private ModelBiped model;
    public ModModeledArmor(String name, ArmorMaterial materialIn, EntityEquipmentSlot equipmentSlotIn, float maelstrom_armor, String textureName, ModelBiped model)
    {
	super(name, materialIn, 1, equipmentSlotIn, maelstrom_armor);
	this.textureName = textureName;
	this.model = model;
    }

    /*
     * Sets up a custom armor model
     */
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default)
    {
        if(!itemStack.isEmpty() && itemStack.getItem() instanceof ModModeledArmor)
        {
            model.bipedHead.showModel = (armorSlot == EntityEquipmentSlot.HEAD);
            model.bipedBody.showModel = (armorSlot == EntityEquipmentSlot.CHEST);
            model.bipedLeftArm.showModel = (armorSlot == EntityEquipmentSlot.CHEST);
            model.bipedRightArm.showModel = (armorSlot == EntityEquipmentSlot.CHEST);
            model.bipedLeftLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS || armorSlot == EntityEquipmentSlot.FEET);
            model.bipedRightLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS || armorSlot == EntityEquipmentSlot.FEET);
            
            model.isChild = _default.isChild;
            model.isRiding = _default.isRiding;
            model.isSneak = _default.isSneak;
            model.rightArmPose = _default.rightArmPose;
            model.leftArmPose = _default.leftArmPose;
            
            return model;
        }
        return null;
    }
    
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return Reference.MOD_ID + ":textures/models/armor/" + this.textureName;
    }
}
