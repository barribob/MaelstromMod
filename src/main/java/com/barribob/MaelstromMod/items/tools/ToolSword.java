package com.barribob.MaelstromMod.items.tools;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerSwordSwing;
import com.barribob.MaelstromMod.init.ModCreativeTabs;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.items.ILeveledItem;
import com.barribob.MaelstromMod.items.ISweepAttackOverride;
import com.barribob.MaelstromMod.util.IHasModel;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ToolSword extends ItemSword implements IHasModel, ISweepAttackOverride, ILeveledItem
{
    private float level;
    private Consumer<List<String>> information = (info) -> {
    };

    public ToolSword(String name, ToolMaterial material, float level)
    {
	super(material);
	setUnlocalizedName(name);
	setRegistryName(name);
	setCreativeTab(ModCreativeTabs.ALL);
	ModItems.ITEMS.add(this);
	this.level = level;
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
	return super.getAttackDamage() * LevelHandler.getMultiplierFromLevel(level);
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
	information.accept(tooltip);
    }

    @Override
    public void doSweepAttack(EntityPlayer player, EntityLivingBase target)
    {
	float attackDamage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
	float sweepDamage = Math.min(0.15F + EnchantmentHelper.getSweepingDamageRatio(player), 1) * attackDamage;
	float maxDistanceSq = 9.0f;

	for (EntityLivingBase entitylivingbase : player.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(1.0, 0.25D, 1.0)))
	{
	    if (entitylivingbase != player && entitylivingbase != target && !player.isOnSameTeam(entitylivingbase) && player.getDistanceSq(entitylivingbase) < maxDistanceSq)
	    {
		entitylivingbase.knockBack(player, 0.4F, MathHelper.sin(player.rotationYaw * 0.017453292F), (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
		entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), sweepDamage);
	    }
	}

	player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 0.9F);
	player.spawnSweepParticles();

	Entity particle = new ParticleSpawnerSwordSwing(player.world);
	particle.copyLocationAndAnglesFrom(target);
	player.world.spawnEntity(particle);
    }

    public static UUID getAttackDamageModifier()
    {
	return ATTACK_DAMAGE_MODIFIER;
    }
}
