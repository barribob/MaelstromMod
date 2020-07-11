package com.barribob.MaelstromMod.entity.entities.herobrine_state;

import com.barribob.MaelstromMod.entity.entities.Herobrine;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModProfessions;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.TimedMessager;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

public class StateCrimsonDimension extends HerobrineState implements IMerchant {
    protected final MerchantRecipeList buyingList = new MerchantRecipeList();
    protected EntityPlayer buyingPlayer;
    protected boolean gtfo = false;
    private boolean leftClickMessage = false;
    private TimedMessager messager;

    public StateCrimsonDimension(Herobrine herobrine) {
        super(herobrine);
        for (EntityVillager.ITradeList list : ModProfessions.HEROBRINE_MAELSTROM_KEY.getTrades(0)) {
            list.addMerchantRecipe(this, this.buyingList, this.herobrine.getRNG());
        }
    }

    @Override
    public String getNbtString() {
        return "crimson_dimension";
    }

    @Override
    public void rightClick(EntityPlayer player) {
        if (!this.gtfo) {
            this.messageToPlayers.accept("herobrine_crimson_0");
            this.gtfo = true;
        }
        if (herobrine.isEntityAlive() && this.buyingPlayer == null) {
            this.setCustomer(player);
            player.displayVillagerTradeGui(this);
        }
    }

    @Override
    public void leftClick(Herobrine herobrine) {
        if (!this.leftClickMessage) {
            messageToPlayers.accept("herobrine_crimson_dimension_1");
            leftClickMessage = true;
        }
        super.leftClick(herobrine);
    }

    @Override
    public void setCustomer(EntityPlayer player) {
        this.buyingPlayer = player;
    }

    @Override
    public EntityPlayer getCustomer() {
        return this.buyingPlayer;
    }

    @Override
    public MerchantRecipeList getRecipes(EntityPlayer player) {
        return this.buyingList;
    }

    @Override
    public void setRecipes(MerchantRecipeList recipeList) {
    }

    @Override
    public void useRecipe(MerchantRecipe recipe) {
        messager = new TimedMessager(
                new String[]{"herobrine_leaving_0", "herobrine_leaving_1", "herobrine_leaving_2", ""},
                new int[]{60, 180, 240, 280},
                (s) -> {
                    if (!world.isRemote) {
                        world.newExplosion(this.herobrine, this.herobrine.posX, this.herobrine.posY, this.herobrine.posZ, 2, false, false);
                        world.playSound(null, this.herobrine.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.HOSTILE, 1.0f, 1.0f);
                        for (int i = 0; i < 10; i++) {
                            Vec3d pos = ModRandom.randVec().scale(3);
                            pos = pos.subtract(ModUtils.yVec(pos.y));
                            BlockPos blockPos = this.herobrine.getPosition().down().add(new BlockPos(pos));
                            if (!world.isAirBlock(blockPos)) {
                                world.setBlockState(blockPos, ModBlocks.BLACK_SKY.getDefaultState());
                            }
                        }
                        this.herobrine.setDead();
                    }
                });
    }

    @Override
    public void verifySellingItem(ItemStack stack) {
    }

    @Override
    public ITextComponent getDisplayName() {
        ITextComponent itextcomponent = new TextComponentTranslation("herobrine_trading", new Object[0]);
        itextcomponent.getStyle().setInsertion(herobrine.getCachedUniqueIdString());
        return itextcomponent;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public BlockPos getPos() {
        return new BlockPos(herobrine);
    }

    @Override
    public void update() {
        if (messager != null) {
            messager.Update(world, messageToPlayers);
        }
        super.update();
    }
}
