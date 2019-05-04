package com.barribob.MaelstromMod.entity.tileentity;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 
 * Spawns herobrine in a flash of lightning
 *
 */
public class HerobrineSpawnerLogic extends DisappearingSpawnerLogic
{
    public HerobrineSpawnerLogic(Supplier<World> world, Supplier<BlockPos> pos, Block block)
    {
	super(world, pos, block);
    }
    
    @Override
    protected void onSpawn(World world, BlockPos blockpos)
    {
	world.addWeatherEffect(new EntityLightningBolt(world, blockpos.getX(), blockpos.getY() + 2, blockpos.getZ(), false));
        super.onSpawn(world, blockpos);
    }
}