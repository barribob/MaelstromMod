package com.barribob.MaelstromMod.blocks;

import java.util.Random;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMaelstrom extends BlockBase
{
    protected static final AxisAlignedBB MAELSTROM_COLLISION_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
    protected final int damage;
    
    public BlockMaelstrom(String name, Material material, float hardness, float resistance, SoundType soundType, int damage)
    {
	super(name, material, hardness, resistance, soundType);
	this.damage = damage;
    }
    
    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }
   
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return MAELSTROM_COLLISION_AABB;
    }
    
    /**
     * Called When an Entity Collided with the Block
     */
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
	if(entityIn instanceof EntityLivingBase && !(entityIn instanceof EntityMaelstromMob)) 
	{
	    entityIn.attackEntityFrom(DamageSource.CACTUS, damage);
	}
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
	for(int i = 0; i < 3; i++) {	    
	    ParticleManager.spawnMaelstromParticle(worldIn, rand, new Vec3d(pos.getX() + rand.nextDouble(), pos.getY() + 1.1f, pos.getZ() + rand.nextDouble()));
	}
	if(rand.nextInt(3) == 0)
	{
	    ParticleManager.spawnMaelstromPotionParticle(worldIn, rand, new Vec3d(pos.getX() + rand.nextDouble(), pos.getY() + 1.1f, pos.getZ() + rand.nextDouble()));
	}
    }
}
