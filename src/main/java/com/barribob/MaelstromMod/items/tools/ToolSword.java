package com.barribob.MaelstromMod.items.tools;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.init.ModCreativeTabs;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.items.ILeveledItem;
import com.barribob.MaelstromMod.items.ISweepAttackOverride;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.IElement;
import com.barribob.MaelstromMod.util.IHasModel;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ToolSword extends ItemSword implements IHasModel, ISweepAttackOverride, ILeveledItem, IElement
{
    private float level;
    private Consumer<List<String>> information = (info) -> {
    };
    private Element element = Element.NONE;

    public ToolSword(String name, ToolMaterial material, float level)
    {
	super(material);
	setUnlocalizedName(name);
	setRegistryName(name);
	setCreativeTab(ModCreativeTabs.ITEMS);
	ModItems.ITEMS.add(this);
	this.level = level;
    }

    public ToolSword(String name, ToolMaterial material, float level, Element element)
    {
	this(name, material, level);
	this.element = element;
    }

    @Override
    public void registerModels()
    {
	Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public float getLevel()
    {
	return level;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit
     * damage.
     */
    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
	Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

	if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
	{
	    multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.getAttackDamage(), 0));
	    multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", getAttackSpeed(), 0));
	}

	return multimap;
    }

    @Override
    public float getAttackDamage()
    {
	return super.getAttackDamage() * LevelHandler.getMultiplierFromLevel(level) * ModConfig.balance.weapon_damage;
    }

    protected double getAttackSpeed()
    {
	return -2.4000000953674316D;
    }

    public Item setInformation(Consumer<List<String>> information)
    {
	this.information = information;
	return this;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add(ModUtils.getDisplayLevel(level));
	if (!element.equals(element.NONE))
	{
	    tooltip.add(ModUtils.getElementalTooltip(element));
	}
	information.accept(tooltip);
    }

    @Override
    public void doSweepAttack(EntityPlayer player, EntityLivingBase target)
    {
	ModUtils.doSweepAttack(player, target, element, (e) -> {
	});
    }

    public static UUID getAttackDamageModifier()
    {
	return ATTACK_DAMAGE_MODIFIER;
    }

    @Override
    public Element getElement()
    {
	return element;
    }
}
