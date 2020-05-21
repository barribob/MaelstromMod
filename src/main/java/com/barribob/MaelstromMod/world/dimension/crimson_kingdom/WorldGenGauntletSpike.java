package com.barribob.MaelstromMod.world.dimension.crimson_kingdom;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenGauntletSpike extends WorldGenStructure {

    public WorldGenGauntletSpike() {
	super("crimson_kingdom/gauntlet_spike");
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand) {
	super.handleDataMarker(function, pos, worldIn, rand);
	worldIn.setBlockToAir(pos);
	if (function.startsWith("chest")) {
	    TileEntity tileentity = worldIn.getTileEntity(pos.down());

	    if (tileentity instanceof TileEntityLockableLoot) {
		((TileEntityLockableLoot) tileentity).setLootTable(LootTableHandler.GAUNTLET_CHEST, rand.nextLong());
	    }
	}
	else if (function.startsWith("boss chest")) {
	    TileEntity tileentity = worldIn.getTileEntity(pos.down());

	    if (tileentity instanceof TileEntityLockableLoot) {
		((TileEntityLockableLoot) tileentity).setInventorySlotContents(13, new ItemStack(ModItems.MAELSTROM_KEY));
	    }
	}

    }
}
