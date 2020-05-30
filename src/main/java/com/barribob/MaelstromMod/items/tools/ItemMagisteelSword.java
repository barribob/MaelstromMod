package com.barribob.MaelstromMod.items.tools;

import java.util.List;

import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileSwordSlash;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemMagisteelSword extends ToolSword
{
    public ItemMagisteelSword(String name, ToolMaterial material, float level, Element element)
    {
	super(name, material, level, element);
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
	if (!entityLiving.world.isRemote && entityLiving instanceof EntityPlayer)
	{
	    EntityPlayer player = (EntityPlayer) entityLiving;
	    float attackDamage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
	    float atkCooldown = player.getCooledAttackStrength(0.5F);

	    if (atkCooldown > 0.9F)
	    {
		Projectile proj = new ProjectileSwordSlash(player.world, player, attackDamage);
		proj.setElement(this.getElement());
		proj.setTravelRange(4.5f);
		proj.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5f, 0);
		player.world.spawnEntity(proj);
		player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F,
			0.9F);
		if (!player.capabilities.isCreativeMode)
		{
		    stack.damageItem(1, player);
		}
	    }
	}
	return false;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("magisteel_sword"));
    }
}
