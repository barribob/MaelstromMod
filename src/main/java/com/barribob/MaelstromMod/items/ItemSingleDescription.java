package com.barribob.MaelstromMod.items;

import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemSingleDescription extends ItemBase {
    private String desc;

    public ItemSingleDescription(String name, String desc, CreativeTabs tab) {
        super(name, tab);
        this.desc = desc;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc(desc));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
