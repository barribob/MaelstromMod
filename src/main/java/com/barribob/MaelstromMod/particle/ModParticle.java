package com.barribob.MaelstromMod.particle;

import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ModParticle extends Particle {
    public int minIndex;
    public int indexRange;
    public float animationSpeed;
    public boolean isLit;

    public ModParticle(World worldIn, Vec3d pos, Vec3d motion, float scale, int age, boolean isLit) {
        super(worldIn, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
        this.particleScale = scale;
        this.particleMaxAge = age;
        this.motionX = motion.x;
        this.motionY = motion.y;
        this.motionZ = motion.z;
        this.isLit = isLit;
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(ModUtils.PARTICLE);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        super.renderParticle(bufferbuilder, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        tessellator.draw();
    }

    @Override
    public int getFXLayer() {
        return 3;
    }

    @Override
    public void setParticleTextureIndex(int particleTextureIndex) {
        this.particleTextureIndexX = particleTextureIndex % 16;
        this.particleTextureIndexY = particleTextureIndex / 16;
    }

    @Override
    public void onUpdate() {
        this.setParticleTextureIndex(minIndex + (int) (this.indexRange * (((this.particleAge * this.animationSpeed) % this.particleMaxAge) / this.particleMaxAge)));

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setExpired();
        }

        this.move(this.motionX, this.motionY, this.motionZ);
    }

    public void setParticleTextureRange(int minIndex, int range, float speed) {
        if (minIndex < 0 || range < 0 || minIndex + range > 255 || speed < 0) {
            throw new IllegalArgumentException("Texture ranges are invalid.");
        }

        this.minIndex = minIndex;
        this.indexRange = range;
        animationSpeed = speed;
        this.setParticleTextureIndex(minIndex + (int) (this.indexRange * (((this.particleAge * this.animationSpeed) % this.particleMaxAge) / this.particleMaxAge)));
    }

    @Override
    public int getBrightnessForRender(float p_189214_1_) {
        // Light like the firework particle
        if (isLit) {
            float f = (this.particleAge + p_189214_1_) / this.particleMaxAge;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            int i = super.getBrightnessForRender(p_189214_1_);
            int j = i & 255;
            int k = i >> 16 & 255;
            j = j + (int) (f * 15.0F * 16.0F);

            if (j > 240) {
                j = 240;
            }

            return j | k << 16;
        }
        return super.getBrightnessForRender(p_189214_1_);
    }
}
