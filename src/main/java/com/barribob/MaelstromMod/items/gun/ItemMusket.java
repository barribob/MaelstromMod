package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.entity.projectile.ProjectileBullet;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
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
    private float meleeDamage;
    
    public ItemMusket(String name, int cooldown, int maxDamage, float meleeDamage, Item ammo, CreativeTabs tab)
    {
	super(name, cooldown, maxDamage, ammo, tab);
	this.meleeDamage = meleeDamage;
    }

    /**
     * Shoot a single bullet
     */
    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
	world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.5F,
		0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

	int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
	int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
	float inaccuracy = 5.0f;
	float velocity = 4.0f;

	ProjectileBullet projectile = new ProjectileBullet(world, player, 6 + power * 0.75f);
	projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity, inaccuracy);
	projectile.setKnockback(knockback);
	projectile.setTravelRange(40f);

	if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
	{
	    projectile.setFire(100);
	}

	world.spawnEntity(projectile);
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on
     * material.
     */
    public int getItemEnchantability()
    {
	return 1;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add(TextFormatting.GRAY + "Required Ammo: Metal Pellet");
	tooltip.add(TextFormatting.GRAY + "Shoots a medium range bullet: acts as a melee weapon");
    }
    
    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.meleeDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
}
