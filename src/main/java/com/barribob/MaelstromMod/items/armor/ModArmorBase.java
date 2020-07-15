package com.barribob.MaelstromMod.items.armor;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.init.ModCreativeTabs;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.items.ILeveledItem;
import com.barribob.MaelstromMod.util.*;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.UUID;

/**
 * The base for armor for the mod
 * <p>
 * Defines the new maelstrom armor type
 * <p>
 * Allows for new armor models to be included
 * <p>
 * Also allows for textures independent of the armor material
 */
public class ModArmorBase extends ItemArmor implements IHasModel, ILeveledItem, IElement {
    private static final UUID[] ARMOR_MODIFIERS = new UUID[]{UUID.fromString("a3578781-e4a8-4d70-9d32-cd952aeae1df"),
            UUID.fromString("e2d1f056-f539-48c7-b353-30d7a367ebd0"), UUID.fromString("db13047a-bb47-4621-a025-65ed22ce461a"),
            UUID.fromString("abb5df20-361d-420a-8ec7-4bdba33378eb")};

    private float level;
    private static final int[] armor_fractions = {4, 7, 8, 5};
    private static final int armor_total = 24;
    private String textureName;
    private Element element = Element.NONE;
    private String armorBonusDesc = "";

    public ModArmorBase(String name, ArmorMaterial materialIn, int renderIndex, EntityEquipmentSlot equipmentSlotIn, float level, String textureName) {
        super(materialIn, renderIndex, equipmentSlotIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTabs.ITEMS);
        this.level = level;
        this.textureName = textureName;
        ModItems.ITEMS.add(this);
    }

    public float getElementalArmor(Element element) {
        if (element.matchesElement(getElement())) {
            float fullArmorReduction = 1 - (1 / ModConfig.balance.elemental_factor);
            float armorFraction = this.armor_fractions[this.armorType.getIndex()] / (float) armor_total;
            return fullArmorReduction * armorFraction;
        }
        return 0; // Does not add any reduction at all
    }

    /**
     * Get the calculated reduction in armor based on the armor factor
     */
    public float getMaelstromArmor(ItemStack stack) {
        float total_armor_reduction = 1 - LevelHandler.getArmorFromLevel(this.level);
        float armor_type_fraction = this.armor_fractions[this.armorType.getIndex()] / (float) armor_total;
        return total_armor_reduction * armor_type_fraction;
    }

    /**
     * Gets the armor bars for display Directly calculates from the fraction of the
     * maelstrom_armor_factor, which represents the total armor bars of any given
     * armor set
     */
    public float getMaelstromArmorBars() {
        if(ModConfig.gui.disableMaelstromArmorItemTooltips) {
            return 0;
        }
        float armor_type_fraction = this.armor_fractions[this.armorType.getIndex()] / (float) armor_total;
        return this.getLevel() * armor_type_fraction;
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == this.armorType) {
            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", this.damageReduceAmount, 0));

            // Override armor toughness to make is adjustable in game
            multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor toughness", ModConfig.balance.armor_toughness, 0));
            multimap.put("maelstrom_armor", new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Maelstrom Armor modifier", Math.round(this.getMaelstromArmorBars() * 10) / 10.0f, 0));
        }

        return multimap;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(!ModConfig.gui.disableMaelstromArmorItemTooltips) {
            tooltip.add(ModUtils.getDisplayLevel((this.getLevel())));
        }

        if (!element.equals(element.NONE) && !ModConfig.gui.disableElementalVisuals) {
            tooltip.add(ModUtils.translateDesc("elemental_armor_desc", element.textColor + element.symbol + TextFormatting.GRAY,
                    ModUtils.ROUND.format(100 * getElementalArmor(element)) + "%"));
        }
        if (!this.armorBonusDesc.isEmpty()) {
            tooltip.add(ModUtils.translateDesc(this.armorBonusDesc));
        }
    }

    public ModArmorBase setArmorBonusDesc(String armorBonusDesc) {
        this.armorBonusDesc = armorBonusDesc;
        return this;
    }

    protected ModelBiped getCustomModel() {
        return null;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if (this.getCustomModel() != null) {
            return Reference.MOD_ID + ":textures/models/armor/" + this.textureName;
        }

        // Basically the normal string that is generated by minecraft's default armor
        int layer = slot == EntityEquipmentSlot.LEGS ? 2 : 1;
        String t = type == null ? "" : String.format("_%s", type);
        return String.format("%s:textures/models/armor/%s_layer_%d%s.png", Reference.MOD_ID, this.textureName, layer, t);
    }

    /*
     * Sets up a custom armor model
     */
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        if (this.getCustomModel() != null && !itemStack.isEmpty() && itemStack.getItem() instanceof ModArmorBase) {
            ModelBiped model = getCustomModel();
            model.bipedHead.showModel = (armorSlot == EntityEquipmentSlot.HEAD);
            model.bipedBody.showModel = (armorSlot == EntityEquipmentSlot.CHEST);
            model.bipedLeftArm.showModel = (armorSlot == EntityEquipmentSlot.CHEST);
            model.bipedRightArm.showModel = (armorSlot == EntityEquipmentSlot.CHEST);
            model.bipedLeftLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS || armorSlot == EntityEquipmentSlot.FEET);
            model.bipedRightLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS || armorSlot == EntityEquipmentSlot.FEET);

            model.isChild = _default.isChild;
            model.isRiding = _default.isRiding;
            model.isSneak = _default.isSneak;
            model.rightArmPose = _default.rightArmPose;
            model.leftArmPose = _default.leftArmPose;

            return model;
        }
        return null;
    }

    @Override
    public float getLevel() {
        return this.level;
    }

    @Override
    public Element getElement() {
        return element;
    }

    public ModArmorBase setElement(Element element) {
        this.element = element;
        return this;
    }
}
