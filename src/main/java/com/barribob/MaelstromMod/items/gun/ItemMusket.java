package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/*
 * The musket shoots a single bullet, and has melee damage
 */
public class ItemMusket extends ItemGun
{
    private float meleeDamage = 5;
    
    public ItemMusket(String name, float level)
    {
	super(name, 40, 8, level);
    }

    /**
     * Shoot a single bullet
     */
    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
	world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.5F,
		0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

	float inaccuracy = 0.0f;
	float velocity = 5.0f;

	Projectile projectile = factory.get(world, player, stack, this.getEnchantedDamage(stack));
	projectile.setElement(getElement());
	projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity, inaccuracy);
	projectile.setTravelRange(50);

	world.spawnEntity(projectile);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("musket"));
    }
    
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
	Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
	float attackDamage = this.meleeDamage * LevelHandler.getMultiplierFromLevel(this.getLevel()) * ModConfig.balance.weapon_damage;

	if (slot == EntityEquipmentSlot.MAINHAND)
        {
	    multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
}
