package com.barribob.MaelstromMod.items.tools;

import java.util.UUID;

import com.barribob.MaelstromMod.items.IExtendedReach;
import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

/**
 * 
 * Holds reach properties for an extended reach tool
 *
 */
public class ToolExtendedReachSword extends ToolSword implements IExtendedReach
{
    private static final UUID REACH_MODIFIER = UUID.fromString("a6323e02-d8e9-44c6-b941-f5d7155bb406");
    private float reach;

    public ToolExtendedReachSword(String name, float reach, ToolMaterial material, float level)
    {
	super(name, material, level);
	this.reach = reach;
    }

    public float getReach()
    {
	return this.reach;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit
     * damage.
     */
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
	Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

	if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
	{
	    multimap.put("extended_reach", new AttributeModifier(REACH_MODIFIER, "Extended Reach Modifier", this.reach - 3.0D, 0));
	}
	return multimap;
    }
}
