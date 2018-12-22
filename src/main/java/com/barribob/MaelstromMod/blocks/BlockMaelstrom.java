package com.barribob.MaelstromMod.blocks;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleSpell;
import net.minecraft.client.particle.ParticleSuspendedTown;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMaelstrom extends BlockLeavesBase
{
    protected static final AxisAlignedBB MAELSTROM_COLLISION_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
    protected final int damage;
    int[] surroundings;
    
    public BlockMaelstrom(String name, float hardness, float resistance, SoundType soundType, int damage)
    {
	super(name, hardness, resistance, soundType);
	this.damage = damage;
	this.setLightLevel(0.5f);
	this.setHarvestLevel("pickaxe", 1);
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
	if(entityIn instanceof EntityLivingBase) 
	{
	    entityIn.attackEntityFrom(DamageSource.CACTUS, damage);
	}
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        for (int i = 0; i < 3; ++i)
        {
            double d0 = (double)pos.getX() + rand.nextDouble();
            double d1 = (double)pos.getY() + 1.1F;
            double d2 = (double)pos.getZ() + rand.nextDouble();
            Particle particle = new ParticleSuspendedTown.Factory().createParticle(0, worldIn, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            particle.setRBGColorF(0.5f, 0.3f, 0.5f);
            Minecraft.getMinecraft().effectRenderer.addEffect(particle);
        }
        
        if(rand.nextInt(20) == 0) {
            double d0 = (double)pos.getX() + rand.nextDouble();
            double d1 = (double)pos.getY() + 1.1F;
            double d2 = (double)pos.getZ() + rand.nextDouble();
            Particle particle = new ParticleSpell.Factory().createParticle(0, worldIn, d0, d1, d2, 0.0D, 0.1D, 0.0D);
            particle.setRBGColorF(0.5f, 0.3f, 0.5f);
            Minecraft.getMinecraft().effectRenderer.addEffect(particle);
        }
    }
    
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (((Boolean)state.getValue(CHECK_DECAY)).booleanValue() && ((Boolean)state.getValue(DECAYABLE)).booleanValue())
            {
                int i = 6;
                int j = 10;
                int k = pos.getX();
                int l = pos.getY();
                int i1 = pos.getZ();
                int j1 = 32;
                int k1 = 1024;
                int l1 = 16;

                if (this.surroundings == null)
                {
                    this.surroundings = new int[32768];
                }

                if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent decaying leaves from updating neighbors and loading unloaded chunks
                if (worldIn.isAreaLoaded(pos, j)) // Forge: extend range from 5 to 6 to account for neighbor checks in world.markAndNotifyBlock -> world.updateObservingBlocksAt
                {
                    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
                    for (int i2 = -i; i2 <= i; ++i2)
                    {
                        for (int j2 = -i; j2 <= i; ++j2)
                        {
                            for (int k2 = -i; k2 <= i; ++k2)
                            {
                                IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2));
                                Block block = iblockstate.getBlock();

                                if (block != ModBlocks.AZURE_MAELSTROM_CORE)
                                {
                                    if (block == ModBlocks.AZURE_MAELSTROM)
                                    {
                                        this.surroundings[(i2 + l1) * k1 + (j2 + l1) * j1 + k2 + l1] = -2;
                                    }
                                    else
                                    {
                                        this.surroundings[(i2 + l1) * k1 + (j2 + l1) * j1 + k2 + l1] = -1;
                                    }
                                }
                                else
                                {
                                    this.surroundings[(i2 + l1) * k1 + (j2 + l1) * j1 + k2 + l1] = 0;
                                }
                            }
                        }
                    }

                    for (int i3 = 1; i3 <= i; ++i3)
                    {
                        for (int j3 = -i; j3 <= i; ++j3)
                        {
                            for (int k3 = -i; k3 <= i; ++k3)
                            {
                                for (int l3 = -i; l3 <= i; ++l3)
                                {
                                    if (this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1] == i3 - 1)
                                    {
                                        if (this.surroundings[(j3 + l1 - 1) * k1 + (k3 + l1) * j1 + l3 + l1] == -2)
                                        {
                                            this.surroundings[(j3 + l1 - 1) * k1 + (k3 + l1) * j1 + l3 + l1] = i3;
                                        }

                                        if (this.surroundings[(j3 + l1 + 1) * k1 + (k3 + l1) * j1 + l3 + l1] == -2)
                                        {
                                            this.surroundings[(j3 + l1 + 1) * k1 + (k3 + l1) * j1 + l3 + l1] = i3;
                                        }

                                        if (this.surroundings[(j3 + l1) * k1 + (k3 + l1 - 1) * j1 + l3 + l1] == -2)
                                        {
                                            this.surroundings[(j3 + l1) * k1 + (k3 + l1 - 1) * j1 + l3 + l1] = i3;
                                        }

                                        if (this.surroundings[(j3 + l1) * k1 + (k3 + l1 + 1) * j1 + l3 + l1] == -2)
                                        {
                                            this.surroundings[(j3 + l1) * k1 + (k3 + l1 + 1) * j1 + l3 + l1] = i3;
                                        }

                                        if (this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + (l3 + l1 - 1)] == -2)
                                        {
                                            this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + (l3 + l1 - 1)] = i3;
                                        }

                                        if (this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1 + 1] == -2)
                                        {
                                            this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1 + 1] = i3;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                int l2 = this.surroundings[16912];

                if (l2 >= 0)
                {
                    worldIn.setBlockState(pos, state.withProperty(CHECK_DECAY, Boolean.valueOf(false)), i);
                }
                else
                {
                    worldIn.setBlockToAir(pos);
                }
            }
        }
    }
}
