package com.barribob.MaelstromMod.items.tools;

import com.barribob.MaelstromMod.items.IExtendedReach;
import com.barribob.MaelstromMod.items.ISweepAttackOverride;
import com.barribob.MaelstromMod.util.ModUtils;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

/**
 * Holds reach properties for an extended reach tool
 */
public class ToolDragonslayer extends ToolSword implements IExtendedReach, ISweepAttackOverride {
    private static final UUID REACH_MODIFIER = UUID.fromString("a6323e02-d8e9-44c6-b941-f5d7155bb406");
    private float reach = 5;

    public ToolDragonslayer(String name, ToolMaterial material, float level) {
        super(name, material, level);
    }

    @Override
    public float getReach() {
        return this.reach;
    }

    /**
     * Increased sweep attack
     */
    @Override
    public void doSweepAttack(EntityPlayer player, EntityLivingBase target) {
        float maxDistanceSq = (float) Math.pow(this.reach, 2);
        float targetEntitySize = (float) 2.0D;

        ModUtils.doSweepAttack(player, target, getElement(), (e) -> {
        }, maxDistanceSq, targetEntitySize);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(REACH_MODIFIER, "Extended Reach Modifier", this.reach - 3.0D, 0).setSaved(false));
        }
        return multimap;
    }

    @Override
    public float getAttackDamage() {
        return super.getAttackDamage() * 1.5f;
    }

    @Override
    protected double getAttackSpeed() {
        return -3.2D;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("dragonslayer"));
    }
}
