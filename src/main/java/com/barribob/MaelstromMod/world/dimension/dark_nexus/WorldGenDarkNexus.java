package com.barribob.MaelstromMod.world.dimension.dark_nexus;

import com.barribob.MaelstromMod.entity.entities.Herobrine;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic.MobSpawnData;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenDarkNexus extends WorldGenStructure {
    public WorldGenDarkNexus() {
        super("nexus/dark_nexus");
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        this.generateStructure(worldIn, position, Rotation.NONE);
        return true;
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand) {
        worldIn.setBlockToAir(pos);
        if (function.startsWith("herobrine")) {
            worldIn.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMobSpawner) {
                ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(new MobSpawnData(ModEntities.getID(Herobrine.class), Element.NONE), 1, LevelHandler.INVASION, 20);
            }
        } else if (function.startsWith("cookie_stash")) {
            TileEntity tileentity = worldIn.getTileEntity(pos.down());

            if (tileentity instanceof TileEntityLockableLoot) {
                ItemStack herobrinesCookies = new ItemStack(Items.COOKIE, 13);
                herobrinesCookies.setStackDisplayName(new TextComponentTranslation("herobrines_cookies").getFormattedText());
                ((TileEntityLockableLoot) tileentity).setInventorySlotContents(13, herobrinesCookies);
            }
        }
    }
}
