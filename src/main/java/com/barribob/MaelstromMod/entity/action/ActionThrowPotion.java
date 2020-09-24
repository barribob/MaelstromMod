package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.util.ModRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.MathHelper;

/**
 * Taken from EntityWitch.java
 * Throws a potion given the potion type
 */
public class ActionThrowPotion implements IAction {
    private Item potionType;

    public ActionThrowPotion(Item potionType) {
        this.potionType = potionType;
    }

    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
        double d0 = target.posY + target.getEyeHeight() - 1.100000023841858D;
        double d1 = target.posX + target.motionX - actor.posX;
        double d2 = d0 - actor.posY;
        double d3 = target.posZ + target.motionZ - actor.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3);
        PotionType[] potions = {PotionTypes.POISON, PotionTypes.SLOWNESS, PotionTypes.WEAKNESS, PotionTypes.HARMING};
        PotionType potiontype = ModRandom.choice(potions);

        EntityPotion entitypotion = new EntityPotion(actor.world, actor, PotionUtils.addPotionToItemStack(new ItemStack(potionType), potiontype));
        entitypotion.rotationPitch -= -20.0F;
        entitypotion.shoot(d1, d2 + f * 0.2F, d3, 0.75F, 8.0F);
        actor.world.playSound((EntityPlayer) null, actor.posX, actor.posY, actor.posZ, SoundEvents.ENTITY_WITCH_THROW, actor.getSoundCategory(), 1.0F, 0.8F + actor.world.rand.nextFloat() * 0.4F);
        actor.world.spawnEntity(entitypotion);
    }
}
