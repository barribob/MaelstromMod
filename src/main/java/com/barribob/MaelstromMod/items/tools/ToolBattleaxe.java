package com.barribob.MaelstromMod.items.tools;

import com.barribob.MaelstromMod.items.ISweepAttackOverride;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * A slower melee weapons with a large sweep attack
 */
public class ToolBattleaxe extends ToolSword implements ISweepAttackOverride {
    public ToolBattleaxe(String name, ToolMaterial material, float level) {
        super(name, material, level);
    }

    /**
     * Increased sweep attack
     */
    @Override
    public void doSweepAttack(EntityPlayer player, EntityLivingBase target) {
        ModUtils.doSweepAttack(player, target, getElement(), (e) -> {
        }, 10, 2);
    }

    @Override
    public float getAttackDamage() {
        return super.getAttackDamage() * 1.25f;
    }

    @Override
    protected double getAttackSpeed() {
        return -3.1D;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("large_sweep_attack"));
    }
}
