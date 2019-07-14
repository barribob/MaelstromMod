package com.barribob.MaelstromMod.items.tools;

import java.util.List;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ToolVenomDagger extends ToolDagger
{
    public ToolVenomDagger(String name, ToolMaterial material, float level)
    {
	super(name, material, level);
    }

    // Add a poison effect on a full attack
    @Override
    public void doSweepAttack(EntityPlayer player, EntityLivingBase target)
    {	
	target.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 1));
	float f = 0.5f;
	for(int i = 0; i < 5; i++)
	{
	    ParticleManager.spawnEffect(player.world, new Vec3d(target.posX, target.posY + 1, target.posZ).add(new Vec3d(ModRandom.getFloat(f), ModRandom.getFloat(f), ModRandom.getFloat(f))), new Vec3d(0.2, 0.5, 0.2));
	}
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + "Poisons on charged attack.");
    }
}
