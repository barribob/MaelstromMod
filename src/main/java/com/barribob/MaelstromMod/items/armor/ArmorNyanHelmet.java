package com.barribob.MaelstromMod.items.armor;

import java.util.List;

import com.barribob.MaelstromMod.items.armor.model.ModelNyanHelmet;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmorNyanHelmet extends ModArmorBase
{
    public ArmorNyanHelmet(String name, ArmorMaterial materialIn, int renderIndex, EntityEquipmentSlot equipmentSlotIn, float maelstrom_armor, String textureName)
    {
	super(name, materialIn, renderIndex, equipmentSlotIn, maelstrom_armor, textureName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected ModelBiped getCustomModel()
    {
	return new ModelNyanHelmet();
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add("Full set bonus: Rainbows upon sprinting");
	super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
