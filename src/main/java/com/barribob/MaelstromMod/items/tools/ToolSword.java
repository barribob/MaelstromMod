package com.barribob.MaelstromMod.items.tools;

import java.util.List;
import java.util.function.Consumer;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.init.ModCreativeTabs;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.IHasModel;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ToolSword extends ItemSword implements IHasModel
{
    private final float attackDamage;
    private float level;
    private Consumer<List<String>> information = (info) -> {};

    public ToolSword(String name, ToolMaterial material, float level)
    {
	super(material);
	setUnlocalizedName(name);
	setRegistryName(name);
	setCreativeTab(ModCreativeTabs.ALL);
	ModItems.ITEMS.add(this);
	this.level = level;

	this.attackDamage = material.getAttackDamage() * LevelHandler.getMultiplierFromLevel(level);
    }

    @Override
    public void registerModels()
    {
	Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

    public float getLevel()
    {
	return level;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit
     * damage.
     */
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
	Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

	if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
	{
	    multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) this.attackDamage, 0));
	    multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
	}

	return multimap;
    }

    public Item setInformation(Consumer<List<String>> information)
    {
	this.information = information;
	return this;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add(TextFormatting.GRAY + "Level " + TextFormatting.DARK_GREEN + this.level);
	information.accept(tooltip);
    }
}
