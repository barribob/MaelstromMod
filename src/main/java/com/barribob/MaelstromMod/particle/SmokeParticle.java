package com.barribob.MaelstromMod.particle;

import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SmokeParticle extends Particle
{
    float smokeParticleScale;
    public static final ResourceLocation PARTICLE = new ResourceLocation(Reference.MOD_ID + ":textures/particle/smoke.png");

    private SmokeParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46347_8_, double p_i46347_10_, double p_i46347_12_)
    {
	this(worldIn, xCoordIn, yCoordIn, zCoordIn, p_i46347_8_, p_i46347_10_, p_i46347_12_, 1.0F);
    }

    protected SmokeParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46348_8_, double p_i46348_10_, double p_i46348_12_, float p_i46348_14_)
    {
	super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
	this.motionX *= 0.10000000149011612D;
	this.motionY *= 0.10000000149011612D;
	this.motionZ *= 0.10000000149011612D;
	this.motionX += p_i46348_8_;
	this.motionY += p_i46348_10_;
	this.motionZ += p_i46348_12_;
	float f = (float) (Math.random() * 0.30000001192092896D);
	this.particleRed = f;
	this.particleGreen = f;
	this.particleBlue = f;
	this.particleScale *= p_i46348_14_;
	this.smokeParticleScale = this.particleScale;
	this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
	this.particleMaxAge = (int) (this.particleMaxAge * p_i46348_14_);
    }

    /**
     * Renders the particle
     */
    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
	Minecraft.getMinecraft().getTextureManager().bindTexture(PARTICLE);
	float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
	f = MathHelper.clamp(f, 0.0F, 1.0F);
	this.particleScale = this.smokeParticleScale * f;
	Tessellator tessellator = Tessellator.getInstance();
	BufferBuilder bufferbuilder = tessellator.getBuffer();
	bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
	super.renderParticle(bufferbuilder, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	tessellator.draw();
    }

    @Override
    public int getFXLayer()
    {
	return 3;
    }

    @Override
    public void setParticleTextureIndex(int particleTextureIndex)
    {
	this.particleTextureIndexX = particleTextureIndex % 16;
	this.particleTextureIndexY = particleTextureIndex / 16;
    }

    @Override
    public void onUpdate()
    {
	this.prevPosX = this.posX;
	this.prevPosY = this.posY;
	this.prevPosZ = this.posZ;

	if (this.particleAge++ >= this.particleMaxAge)
	{
	    this.setExpired();
	}

	this.setParticleTextureIndex((int) (7 * (this.particleAge / (float) this.particleMaxAge)));
	this.motionY += 0.004D;
	this.move(this.motionX, this.motionY, this.motionZ);

	if (this.posY == this.prevPosY)
	{
	    this.motionX *= 1.1D;
	    this.motionZ *= 1.1D;
	}

	this.motionX *= 0.9599999785423279D;
	this.motionY *= 0.9599999785423279D;
	this.motionZ *= 0.9599999785423279D;

	if (this.onGround)
	{
	    this.motionX *= 0.699999988079071D;
	    this.motionZ *= 0.699999988079071D;
	}
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
    {
	@Override
	public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
	{
	    return new SmokeParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
	}
    }
}