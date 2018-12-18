package com.barribob.MaelstromMod.world.dimension.azure_dimension;

import java.util.Random;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

public class MapGenAzureRavine extends MapGenBase
{
    protected static final IBlockState FLOWING_LIQUID = Blocks.FLOWING_WATER.getDefaultState();
    protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
    private final float[] rs = new float[1024];

    // I have no idea what the last three parameters are supposed to do
    protected void addTunnel(long rand_long, int originalX, int originalY, ChunkPrimer chunkPrimer, double x, double y, double z, float randFloat1, float randFloat2, float randFloat3, int specialInt1, int specialInt2, double specialDouble)
    {
        Random random = new Random(rand_long);
        double d0 = (double)(originalX * 16 + 8);
        double d1 = (double)(originalY * 16 + 8);
        float f = 0.0F;
        float f1 = 0.0F;

        if (specialInt2 <= 0)
        {
            int i = this.range * 16 - 16;
            specialInt2 = i - random.nextInt(i / 4);
        }

        boolean flag1 = false;

        if (specialInt1 == -1)
        {
            specialInt1 = specialInt2 / 2;
            flag1 = true;
        }

        float f2 = 1.0F;

        for (int j = 0; j < 256; ++j)
        {
            if (j == 0 || random.nextInt(3) == 0)
            {
                f2 = 1.0F + random.nextFloat() * random.nextFloat();
            }

            this.rs[j] = f2 * f2;
        }

        for (; specialInt1 < specialInt2; ++specialInt1)
        {
            double d9 = 1.5D + (double)(MathHelper.sin((float)specialInt1 * (float)Math.PI / (float)specialInt2) * randFloat1);
            double yRange = d9 * specialDouble;
            d9 = d9 * ((double)random.nextFloat() * 0.25D + 0.75D);
            yRange = yRange * ((double)random.nextFloat() * 0.25D + 0.75D);
            float f3 = MathHelper.cos(randFloat3);
            float f4 = MathHelper.sin(randFloat3);
            x += (double)(MathHelper.cos(randFloat2) * f3);
            y += (double)f4;
            z += (double)(MathHelper.sin(randFloat2) * f3);
            randFloat3 = randFloat3 * 0.7F;
            randFloat3 = randFloat3 + f1 * 0.05F;
            randFloat2 += f * 0.05F;
            f1 = f1 * 0.8F;
            f = f * 0.5F;
            f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

            if (flag1 || random.nextInt(4) != 0)
            {
                double d3 = x - d0;
                double d4 = z - d1;
                double d5 = (double)(specialInt2 - specialInt1);
                double d6 = (double)(randFloat1 + 2.0F + 16.0F);

                if (d3 * d3 + d4 * d4 - d5 * d5 > d6 * d6)
                {
                    return;
                }

                if (x >= d0 - 16.0D - d9 * 2.0D && z >= d1 - 16.0D - d9 * 2.0D && x <= d0 + 16.0D + d9 * 2.0D && z <= d1 + 16.0D + d9 * 2.0D)
                {
                    int k2 = MathHelper.floor(x - d9) - originalX * 16 - 1;
                    int k = MathHelper.floor(x + d9) - originalX * 16 + 1;
                    int min_y = MathHelper.floor(y - yRange) - 1;
                    int start_y = MathHelper.floor(y + yRange) + 1;
                    int i3 = MathHelper.floor(z - d9) - originalY * 16 - 1;
                    int i1 = MathHelper.floor(z + d9) - originalY * 16 + 1;

                    if (k2 < 0)
                    {
                        k2 = 0;
                    }

                    if (k > 16)
                    {
                        k = 16;
                    }

                    if (min_y < 1)
                    {
                        min_y = 1;
                    }

                    if (start_y > 248)
                    {
                        start_y = 248;
                    }

                    if (i3 < 0)
                    {
                        i3 = 0;
                    }

                    if (i1 > 16)
                    {
                        i1 = 16;
                    }

                    boolean flag2 = false;

                    for (int j1 = k2; !flag2 && j1 < k; ++j1)
                    {
                        for (int k1 = i3; !flag2 && k1 < i1; ++k1)
                        {
                            for (int l1 = start_y + 1; !flag2 && l1 >= min_y - 1; --l1)
                            {
                                if (l1 >= 0 && l1 < 256)
                                {
                                    if (isOceanBlock(chunkPrimer, j1, l1, k1, originalX, originalY))
                                    {
                                        flag2 = true;
                                    }

                                    if (l1 != min_y - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1)
                                    {
                                        l1 = min_y;
                                    }
                                }
                            }
                        }
                    }

                    if (!flag2)
                    {
                        for (int j3 = k2; j3 < k; ++j3)
                        {
                            double d10 = ((double)(j3 + originalX * 16) + 0.5D - x) / d9;

                            for (int i2 = i3; i2 < i1; ++i2)
                            {
                                double d7 = ((double)(i2 + originalY * 16) + 0.5D - z) / d9;
                                boolean flag = false;

                                if (d10 * d10 + d7 * d7 < 1.0D)
                                {
                                    for (int final_y = start_y; final_y > min_y; --final_y)
                                    {
                                        double d8 = ((double)(final_y - 1) + 0.5D - y) / yRange;

                                        if ((d10 * d10 + d7 * d7) * (double)this.rs[final_y - 1] + d8 * d8 / 6.0D < 1.0D)
                                        {
                                            if (isTopBlock(chunkPrimer, j3, final_y, i2, originalX, originalY))
                                            {
                                                flag = true;
                                            }

                                            digBlock(chunkPrimer, j3, final_y, i2, originalX, originalY, flag);
                                        }
                                    }
                                }
                            }
                        }

                        if (flag1)
                        {
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Recursively called by generate()
     */
    protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int originalX, int originalZ, ChunkPrimer chunkPrimerIn)
    {
    	int chance = 5;
    	float length = 2.5f;
    	float scale = 1.0f;
    	double height = (double)(70);
    	double depthFactor = 12.0D;
        if (this.rand.nextInt(chance) == 0)
        {
            double d0 = (double)(chunkX * 16 + this.rand.nextInt(16));
            double d2 = (double)(chunkZ * 16 + this.rand.nextInt(16));
            int i = 1;

            for (int j = 0; j < 1; ++j)
            {
            	// Responsible for the relative length of the ravine
                float f = rand.nextFloat() * ((float)Math.PI * 2F) * length;
                
                // Has something to do with the curvature (gets used in the sin and cos function for generation)
                float f1 = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
                
                // Handles the relative scale of the ravine (height and width)
                float f2 = (rand.nextFloat() * 1.0F + 1.3f) * 2.0F * scale;
                this.addTunnel(this.rand.nextLong(), originalX, originalZ, chunkPrimerIn, d0, height, d2, f2, f, f1, 0, 0, depthFactor);
            }
        }
    }
    protected boolean isOceanBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ)
    {
        net.minecraft.block.Block block = data.getBlockState(x, y, z).getBlock();
        return block== Blocks.FLOWING_WATER || block == Blocks.WATER;
    }

    //Determine if the block at the specified location is the top block for the biome, we take into account
    //Vanilla bugs to make sure that we generate the map the same way vanilla does.
    private boolean isTopBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ)
    {
        net.minecraft.world.biome.Biome biome = world.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
        IBlockState state = data.getBlockState(x, y, z);
        return (state.getBlock() == biome.topBlock);
    }

    /**
     * Digs out the current block, default implementation removes stone, filler, and top block
     * Sets the block to lava if y is less then 10, and air other wise.
     * If setting to air, it also checks to see if we've broken the surface and if so
     * tries to make the floor the biome's top block
     *
     * @param data Block data array
     * @param index Pre-calculated index into block data
     * @param x local X position
     * @param y local Y position
     * @param z local Z position
     * @param chunkX Chunk X position
     * @param chunkZ Chunk Y position
     * @param foundTop True if we've encountered the biome's top block. Ideally if we've broken the surface.
     */
    protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop)
    {
        net.minecraft.world.biome.Biome biome = world.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
        IBlockState state = data.getBlockState(x, y, z);
        IBlockState top = biome.topBlock;
        IBlockState filler = biome.fillerBlock;
        Block stone = ModBlocks.DARK_AZURE_STONE;
        
        if (state.getBlock() == stone || state.getBlock() == top.getBlock() || state.getBlock() == filler.getBlock())
        {
            if (y - 1 < 10)
            {
                data.setBlockState(x, y, z, FLOWING_LIQUID);
            }
            else
            {
                data.setBlockState(x, y, z, AIR);
                
                // Set anything that has been dug out to have grass below
                data.setBlockState(x, y - 1, z, top.getBlock().getDefaultState());
                
                if (foundTop && data.getBlockState(x, y - 1, z).getBlock() == filler.getBlock())
                {
                    data.setBlockState(x, y - 1, z, top.getBlock().getDefaultState());
                }
            }
        }
    }
}