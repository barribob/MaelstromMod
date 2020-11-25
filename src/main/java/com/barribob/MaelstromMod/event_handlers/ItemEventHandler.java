package com.barribob.MaelstromMod.event_handlers;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerRainbow;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.init.ModPotions;
import com.barribob.MaelstromMod.mana.IMana;
import com.barribob.MaelstromMod.mana.ManaProvider;
import com.barribob.MaelstromMod.packets.MessageMana;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.typesafe.config.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber()
public class ItemEventHandler {

    // Test function to make sure the lookVec and pitch utilities are correct
//    public static void drawLookVec(EntityPlayer player) {
//        Vec3d vec = ModUtils.getLookVec((float) ModUtils.toPitch(player.getLookVec()), player.rotationYaw).scale(6);
//        ModUtils.lineCallback(player.getPositionVector(), vec.add(player.getPositionVector()), 20, (v, i) -> ParticleManager.spawnEffect(player.world, v, ModColors.BLUE));
//    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            Item heldItem = player.getHeldItem(EnumHand.MAIN_HAND).getItem();
            Item offhandItem = player.getHeldItem(EnumHand.OFF_HAND).getItem();

            Item helmet = player.inventory.armorInventory.get(3).getItem();
            Item chestplate = player.inventory.armorInventory.get(2).getItem();
            Item leggings = player.inventory.armorInventory.get(1).getItem();
            Item boots = player.inventory.armorInventory.get(0).getItem();
            
            Config bonusConfig = Main.itemsConfig.getConfig("full_set_bonuses");

            if (((heldItem.equals(ModItems.BAKUYA) && offhandItem.equals(ModItems.KANSHOU)) || (heldItem.equals(ModItems.KANSHOU) && offhandItem.equals(ModItems.BAKUYA))) && bonusConfig.getBoolean("kanshou_bakuya")) {
                player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20, 1));
            }

            if (!player.world.isRemote && player instanceof EntityPlayerMP && (heldItem.equals(ModItems.CROSS_OF_AQUA) || offhandItem.equals(ModItems.CROSS_OF_AQUA))) {
                IMana mana = player.getCapability(ManaProvider.MANA, null);
                if (!mana.isLocked() && mana.getMana() > 0) {
                    if (!player.capabilities.isCreativeMode && player.ticksExisted % 40 == 0) {
                        mana.consume(1);
                        Main.network.sendTo(new MessageMana(mana.getMana()), (EntityPlayerMP) player);
                    }
                    player.addPotionEffect(new PotionEffect(ModPotions.water_strider, 40));
                }
            }

            if (bonusConfig.getBoolean("nyan") &&
                    helmet.equals(ModItems.NYAN_HELMET) &&
                    chestplate.equals(ModItems.NYAN_CHESTPLATE) &&
                    leggings.equals(ModItems.NYAN_LEGGINGS) &&
                    boots.equals(ModItems.NYAN_BOOTS) && player.isSprinting()) {
                Entity particle = new ParticleSpawnerRainbow(player.world);
                Vec3d pos = player.getPositionVector().subtract(new Vec3d(player.getLookVec().x, 0, player.getLookVec().z));
                particle.setPosition(pos.x, pos.y, pos.z);
                player.world.spawnEntity(particle);
            }

            if (bonusConfig.getBoolean("goltox") &&
                    helmet.equals(ModItems.GOLTOX_HELMET) &&
                    chestplate.equals(ModItems.GOLTOX_CHESTPLATE) &&
                    leggings.equals(ModItems.GOLTOX_LEGGINGS) &&
                    boots.equals(ModItems.GOLTOX_BOOTS) &&
                    player.ticksExisted % 20 == 0) {
                if (player.isPotionActive(MobEffects.POISON)) {
                    PotionEffect effect = player.getActivePotionEffect(MobEffects.POISON);
                    player.removeActivePotionEffect(MobEffects.POISON);
                    player.addPotionEffect(new PotionEffect(MobEffects.POISON, effect.getDuration() - 20, effect.getAmplifier()));
                }
            }

            if (!player.world.isRemote && player.ticksExisted % 40 == 0) {
                if (bonusConfig.getBoolean("black_gold") &&
                        helmet.equals(ModItems.BLACK_GOLD_HELMET) &&
                        chestplate.equals(ModItems.BLACK_GOLD_CHESTPLATE) &&
                        leggings.equals(ModItems.BLACK_GOLD_LEGGINGS) &&
                        boots.equals(ModItems.BLACK_GOLD_BOOTS)) {
                    player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 60, 0));
                }

                if (bonusConfig.getBoolean("maelstrom") &&
                        helmet.equals(ModItems.MAELSTROM_HELMET) &&
                        chestplate.equals(ModItems.MAELSTROM_CHESTPLATE) &&
                        leggings.equals(ModItems.MAELSTROM_LEGGINGS) &&
                        boots.equals(ModItems.MAELSTROM_BOOTS)) {
                    player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 60, 0));
                }

                if (bonusConfig.getBoolean("swamp") &&
                        helmet.equals(ModItems.SWAMP_HELMET) &&
                        chestplate.equals(ModItems.SWAMP_CHESTPLATE) &&
                        leggings.equals(ModItems.SWAMP_LEGGINGS) &&
                        boots.equals(ModItems.SWAMP_BOOTS)) {
                    player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 60, 0));
                }

                if (bonusConfig.getBoolean("energetic_steel") &&
                        helmet.equals(ModItems.ENERGETIC_STEEL_HELMET) &&
                        chestplate.equals(ModItems.ENERGETIC_STEEL_CHESTPLATE) &&
                        leggings.equals(ModItems.ENERGETIC_STEEL_LEGGINGS) &&
                        boots.equals(ModItems.ENERGETIC_STEEL_BOOTS)) {
                    player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 60, 0));
                    player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 60, 1));
                }

                if (bonusConfig.getBoolean("fadesteel") &&
                        helmet.equals(ModItems.FADESTEEL_HELMET) &&
                        chestplate.equals(ModItems.FADESTEEL_CHESTPLATE) &&
                        leggings.equals(ModItems.FADESTEEL_LEGGINGS) &&
                        boots.equals(ModItems.FADESTEEL_BOOTS)) {
                    player.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.357);
                }
                // Using 0.357 as a value unlikely to be chosen by modders, so I can expect to enable and disable this without conflicting
                else if (player.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue() == 0.357) {
                    player.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0);
                }

                if (bonusConfig.getBoolean("elysium") &&
                        helmet.equals(ModItems.ELYSIUM_HELMET) &&
                        chestplate.equals(ModItems.ELYSIUM_CHESTPLATE) &&
                        leggings.equals(ModItems.ELYSIUM_LEGGINGS) &&
                        boots.equals(ModItems.ELYSIUM_BOOTS)) {
                    // Every 2 seconds * 60 = approximately every 2 minutes
                    if (player.getRNG().nextInt(60) == 0) {
                        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 140, 3));
                    }
                }
            }

            if (player.world.isRemote && chestplate.equals(ModItems.ELYSIUM_WINGS) && player.isElytraFlying()) {
                double speed = Math.max(0.1, Math.min(1, new Vec3d(player.motionX, 0, player.motionZ).lengthVector() + player.motionY));
                ParticleManager.spawnFirework(player.world, player.getPositionEyes(1).add(ModUtils.getRelativeOffset(player, new Vec3d(0, 0, -speed))), ModColors.RED);
                ParticleManager.spawnFirework(player.world, player.getPositionEyes(1).add(ModUtils.getRelativeOffset(player, new Vec3d(0, 0, speed))), ModColors.RED);
            }
        }
    }
}
