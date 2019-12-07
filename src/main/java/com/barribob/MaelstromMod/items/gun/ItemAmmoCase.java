package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.init.ModCreativeTabs;
import com.barribob.MaelstromMod.items.ILeveledItem;
import com.barribob.MaelstromMod.items.ItemBase;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAmmoCase extends ItemBase implements ILeveledItem
{
    private float level;

    public ItemAmmoCase(String name, float level)
    {
	super(name, ModCreativeTabs.ALL);
	this.level = level;
	this.setMaxDamage(Math.round(LevelHandler.getMultiplierFromLevel(level) * 100));
	this.setMaxStackSize(1);
    }

    @Override
    public float getLevel()
    {
	return this.level;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add(ModUtils.getDisplayLevel(this.level));
    }
}
