package com.barribob.MaelstromMod.world.dimension.crimson_kingdom;

import java.util.Random;

import com.barribob.MaelstromMod.entity.entities.EntityHorror;
import com.barribob.MaelstromMod.entity.entities.EntityIronShade;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromHealer;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromLancer;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMage;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic.MobSpawnData;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class WorldGenCrimsonKingdomChunk extends WorldGenStructure
{

    public WorldGenCrimsonKingdomChunk(int x, int z)
    {
	super("crimson_kingdom/crimson_kingdom_" + x + "_" + z);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
	generateStructure(worldIn, position, Rotation.NONE);
	return true;
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand)
    {
	super.handleDataMarker(function, pos, worldIn, rand);
	worldIn.setBlockToAir(pos);
	if (function.startsWith("enemy 4") || function.startsWith("enemy 5") | function.startsWith("enemy 6"))
	{
	    int level = ModUtils.tryParseInt(function.split(" ")[1], 5);
	    worldIn.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
	    TileEntity tileentity = worldIn.getTileEntity(pos);
	    if (tileentity instanceof TileEntityMobSpawner)
	    {
		((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(
			new MobSpawnData[] {
				new MobSpawnData(ModEntities.getID(EntityShade.class), new Element[] { Element.CRIMSON, Element.NONE }, new int[] { 1, 3 }, 1),
				new MobSpawnData(ModEntities.getID(EntityMaelstromLancer.class), new Element[] { Element.CRIMSON, Element.NONE }, new int[] { 1, 4 }, 1),
				new MobSpawnData(ModEntities.getID(EntityMaelstromMage.class), new Element[] { Element.CRIMSON, Element.NONE }, new int[] { 1, 4 }, 1),
				new MobSpawnData(ModEntities.getID(EntityHorror.class), Element.NONE)
			},
			new int[] { 2, 2, 2, 1 },
			ModRandom.range(3, 5),
			level,
			25);
	    }
	}
	else if (function.startsWith("ranger 5") || function.startsWith("ranger 6"))
	{
	    int level = ModUtils.tryParseInt(function.split(" ")[0], 5);
	    worldIn.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
	    TileEntity tileentity = worldIn.getTileEntity(pos);
	    if (tileentity instanceof TileEntityMobSpawner)
	    {
		((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(
			new MobSpawnData(ModEntities.getID(EntityMaelstromMage.class), new Element[] { Element.CRIMSON, Element.NONE }, new int[] { 1, 2 }, 1),
			1,
			level,
			25);
	    }
	}
	else if (function.startsWith("miniboss"))
	{
	    worldIn.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
	    TileEntity tileentity = worldIn.getTileEntity(pos);
	    if (tileentity instanceof TileEntityMobSpawner)
	    {
		((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(
			new MobSpawnData(ModEntities.getID(EntityIronShade.class), Element.CRIMSON),
			1,
			LevelHandler.CRIMSON_END,
			25);
	    }
	}
	else if (function.startsWith("healer"))
	{
	    int level = ModUtils.tryParseInt(function.split(" ")[0], 5);
	    worldIn.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
	    TileEntity tileentity = worldIn.getTileEntity(pos);
	    if (tileentity instanceof TileEntityMobSpawner)
	    {
		((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(
			new MobSpawnData(ModEntities.getID(EntityMaelstromHealer.class), Element.NONE),
			1,
			level,
			25);
	    }
	}
	else if (function.startsWith("chest minecart"))
	{
	    TileEntity tileentity = worldIn.getTileEntity(pos.down());

	    if (tileentity instanceof TileEntityLockableLoot)
	    {
		((TileEntityLockableLoot) tileentity).setLootTable(LootTableList.CHESTS_ABANDONED_MINESHAFT, rand.nextLong());
	    }
	}
	else if (function.startsWith("garbage"))
	{
	    TileEntity tileentity = worldIn.getTileEntity(pos.down());

	    if (tileentity instanceof TileEntityLockableLoot)
	    {
		((TileEntityLockableLoot) tileentity).setLootTable(LootTableList.CHESTS_SPAWN_BONUS_CHEST, rand.nextLong());
	    }
	}
	else if (function.startsWith("chest"))
	{
	    int level = ModUtils.tryParseInt(function.split(" ")[0], 5);
	    ResourceLocation loot = level == 5 ? LootTableHandler.CRIMSON_5_CHEST : LootTableHandler.CRIMSON_6_CHEST;
	    TileEntity tileentity = worldIn.getTileEntity(pos.down());

	    if (tileentity instanceof TileEntityLockableLoot)
	    {
		((TileEntityLockableLoot) tileentity).setLootTable(loot, rand.nextLong());
	    }
	}
    }
}
