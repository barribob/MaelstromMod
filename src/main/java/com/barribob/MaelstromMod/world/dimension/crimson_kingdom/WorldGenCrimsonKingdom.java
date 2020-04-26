package com.barribob.MaelstromMod.world.dimension.crimson_kingdom;

import java.util.Random;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromLancer;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMage;
import com.barribob.MaelstromMod.entity.entities.EntityShade;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic.MobSpawnData;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.IStructure;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.loot.LootTableList;

public class WorldGenCrimsonKingdom extends WorldGenerator implements IStructure
{
    public static final int SLICES = 64;

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
	for (int slice = 0; slice < SLICES; slice++)
	{
	    WorldGenStructure structure = new WorldGenStructure("crimson_kingdom/crimson_kingdom_" + slice)
	    {
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
		    if (function.startsWith("enemy lvl4") || function.startsWith("enemy lvl5") | function.startsWith("enemy lvl6"))
		    {
			worldIn.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TileEntityMobSpawner)
			{
			    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(
				    new MobSpawnData[] {
					    new MobSpawnData(ModEntities.getID(EntityShade.class), new Element[] { Element.CRIMSON, Element.NONE }, new int[] { 1, 4 }, 1),
					    new MobSpawnData(ModEntities.getID(EntityMaelstromLancer.class), new Element[] { Element.CRIMSON, Element.NONE }, new int[] { 1, 4 }, 1),
					    new MobSpawnData(ModEntities.getID(EntityMaelstromMage.class), new Element[] { Element.CRIMSON, Element.NONE }, new int[] { 1, 4 }, 1)
				    },
				    new int[] { 2, 2, 2 },
				    3,
				    LevelHandler.CLIFF_ENDGAME,
				    25);
			}
		    }
		    if (function.startsWith("ranger lvl5") || function.startsWith("ranger lvl6"))
		    {
			worldIn.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TileEntityMobSpawner)
			{
			    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(
				    new MobSpawnData(ModEntities.getID(EntityMaelstromMage.class), new Element[] { Element.CRIMSON, Element.NONE }, new int[] { 1, 2 }, 1),
				    1,
				    LevelHandler.CLIFF_ENDGAME,
				    25);
			}
		    }
		    if (function.startsWith("chest minecart"))
		    {
			TileEntity tileentity = worldIn.getTileEntity(pos.down());

			if (tileentity instanceof TileEntityChest)
			{
			    ((TileEntityChest) tileentity).setLootTable(LootTableList.CHESTS_ABANDONED_MINESHAFT, rand.nextLong());
			}
		    }
		}
	    };


	    structure.generate(worldIn, rand, position);
	    position = position.up(structure.getSize(worldIn).getY());
	}
	return true;
    }
}
