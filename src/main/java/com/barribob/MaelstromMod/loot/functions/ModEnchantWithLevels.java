package com.barribob.MaelstromMod.loot.functions;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

import java.util.Random;

public class ModEnchantWithLevels extends LootFunction {
    private final RandomValueRange randomLevel;

    protected ModEnchantWithLevels(LootCondition[] conditionsIn, RandomValueRange randomRange) {
        super(conditionsIn);
        this.randomLevel = randomRange;
    }

    @Override
    public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
        if (stack.getItem() instanceof ItemSword && rand.nextFloat() < 0.2) {
            float sharpnessDamage = 0.5f;
            int level = this.randomLevel.generateInt(rand);
            float swordDamage = ModItems.BASE_MELEE_DAMAGE * ModConfig.balance.weapon_damage * LevelHandler.getMultiplierFromLevel(level); // Calculate the standard sword damage
            int maxSharpness = (int) Math.round((swordDamage * (Math.pow(ModConfig.balance.progression_scale, 2) - 1)) / sharpnessDamage); // Approximate the max sharpness to be about two levels

            stack.addEnchantment(Enchantments.SHARPNESS, Math.max(5, rand.nextInt(maxSharpness) + 1));
            return stack;
        }

        return EnchantmentHelper.addRandomEnchantment(rand, stack, ModRandom.range(1, 31), true);
    }

    public static class Serializer extends LootFunction.Serializer<ModEnchantWithLevels> {
        public Serializer() {
            super(new ResourceLocation(Reference.MOD_ID + ":enchant"), ModEnchantWithLevels.class);
        }

        @Override
        public void serialize(JsonObject object, ModEnchantWithLevels functionClazz, JsonSerializationContext serializationContext) {
            object.add("level", serializationContext.serialize(functionClazz.randomLevel));
        }

        @Override
        public ModEnchantWithLevels deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
            RandomValueRange randomvaluerange = JsonUtils.deserializeClass(object, "level", deserializationContext, RandomValueRange.class);
            return new ModEnchantWithLevels(conditionsIn, randomvaluerange);
        }
    }
}
