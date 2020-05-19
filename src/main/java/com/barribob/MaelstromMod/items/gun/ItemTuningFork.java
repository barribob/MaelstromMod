package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.util.EntityTuningForkLazer;
import com.barribob.MaelstromMod.init.ModCreativeTabs;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemTuningFork extends ItemStaff {
    public ItemTuningFork(String name, float level) {
	super(name, 10, 60, ModItems.STAFF_USE_TIME, level, ModCreativeTabs.ITEMS);
    }

    public float getBaseDamage() {
	return 24 * ModConfig.balance.weapon_damage;
    }

    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack) {
	world.playSound(null, player.getPosition(), SoundEvents.BLOCK_NOTE_BELL, SoundCategory.NEUTRAL, 0.5F, 1.0f + ModRandom.getFloat(0.2f));
	world.playSound(null, player.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL, 1.0F, 1.0f + ModRandom.getFloat(0.2f));

	Vec3d lazerEnd = player.getPositionEyes(1).add(player.getLookVec().scale(40));

	// Ray trace both blocks and entities
	RayTraceResult raytraceresult = world.rayTraceBlocks(player.getPositionEyes(1), lazerEnd, false, true, false);
	if (raytraceresult != null) {
	    // If we hit a block, make sure that any collisions with entities are detected up to the hit block
	    lazerEnd = raytraceresult.hitVec;
	}

	EntityLivingBase closestEntity = null;
	for (Entity entity : ModUtils.findEntitiesInLine(player.getPositionEyes(1), lazerEnd, world, player)) {
	    if (entity instanceof EntityLivingBase && (closestEntity == null || entity.getDistanceSq(player) < closestEntity.getDistanceSq(player))) {
		closestEntity = (EntityLivingBase) entity;
	    }
	}

	if (closestEntity != null) {
	    if (closestEntity instanceof IEntityMultiPart) {
		for (Entity entity : closestEntity.getParts()) {
		    RayTraceResult result = entity.getEntityBoundingBox().calculateIntercept(player.getPositionEyes(1), lazerEnd);
		    if (result != null) {
			if (entity instanceof MultiPartEntityPart) {
			    ((IEntityMultiPart) closestEntity).attackEntityFromPart((MultiPartEntityPart) entity, ModDamageSource.causeElementalPlayerDamage(player, getElement()),
				    ModUtils.getEnchantedDamage(stack, this.getLevel(), this.getBaseDamage()));
			}
		    }
		}
	    }
	    else {
		lazerEnd = closestEntity.getEntityBoundingBox().calculateIntercept(player.getPositionEyes(1), lazerEnd).hitVec;
		closestEntity.attackEntityFrom(ModDamageSource.causeElementalPlayerDamage(player, getElement()), ModUtils.getEnchantedDamage(stack, this.getLevel(), this.getBaseDamage()));
	    }
	}

	// Spawn an entity to render the ray and additional particles
	EntityTuningForkLazer renderer = new EntityTuningForkLazer(world, player.getPositionEyes(1).add(ModUtils.getAxisOffset(player.getLookVec(), new Vec3d(0.5, 0, 0.5))));
	renderer.setPosition(lazerEnd.x, lazerEnd.y, lazerEnd.z);
	world.spawnEntity(renderer);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(ModUtils.getDamageTooltip(ModUtils.getEnchantedDamage(stack, this.getLevel(), getBaseDamage())));
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("tuning_fork"));
    }

    @Override
    public boolean doesDamage() {
	return true;
    }
}
