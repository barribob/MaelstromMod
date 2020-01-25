package com.barribob.MaelstromMod.particle;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

public enum EnumModParticles
{
    SWEEP_ATTACK(45, 3),
    EFFECT(46, 3);

    private final int particleID;
    private final int argumentCount;
    private static final Map<Integer, EnumModParticles> PARTICLES = Maps.<Integer, EnumModParticles>newHashMap();

    private EnumModParticles(int particleIDIn, int argumentCountIn)
    {
	this.particleID = particleIDIn;
	this.argumentCount = argumentCountIn;
    }

    private EnumModParticles(int particleIDIn)
    {
	this(particleIDIn, 0);
    }

    public int getParticleID()
    {
	return this.particleID;
    }

    public int getArgumentCount()
    {
	return this.argumentCount;
    }

    /**
     * Gets the relative EnumParticleTypes by id.
     */
    @Nullable
    public static EnumModParticles getParticleFromId(int particleId)
    {
	return PARTICLES.get(Integer.valueOf(particleId));
    }

    static
    {
	for (EnumModParticles enumparticletypes : values())
	{
	    PARTICLES.put(Integer.valueOf(enumparticletypes.getParticleID()), enumparticletypes);
	}
    }
}