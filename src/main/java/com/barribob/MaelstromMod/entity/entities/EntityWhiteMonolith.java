package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.tileentity.TileEntityTeleporter;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.teleporter.NexusToOverworldTeleporter;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;
import com.barribob.MaelstromMod.world.gen.nexus.WorldGenNexusTeleporter;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityWhiteMonolith extends EntityLeveledMob {
    public static final int DEATH_TIME = 600;

    public EntityWhiteMonolith(World worldIn) {
	super(worldIn);
	this.setImmovable(true);
	this.setNoGravity(true);
	this.setSize(2.2f, 4.5f);
	this.setLevel(1);
	this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes() {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300);
	this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
    }

    @Override
    public void onUpdate() {
	super.onUpdate();
	this.setRotation(0, 0);
	this.setRotationYawHead(0);

	if (world.isRemote) {
	    return;
	}

	if (rand.nextInt(6) == 0) {
	    world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
	}

	// Remove minions after the boss fight
	if (this.ticksExisted == 1) {
	    ModUtils.getEntitiesInBox(this, getEntityBoundingBox().grow(15, 2, 15)).stream().filter((e) -> e instanceof EntityMaelstromMob).forEach((e) -> {
		e.hurtResistantTime = 0;
		e.attackEntityFrom(DamageSource.causeMobDamage(this), 50);
	    });
	}

	if (this.ticksExisted > DEATH_TIME - 40) {
	    world.setEntityState(this, ModUtils.PARTICLE_BYTE);
	    world.playSound(this.posX, NexusToOverworldTeleporter.yPortalOffset, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 5.0f, 1.0f, false);
	}

	if (this.ticksExisted > DEATH_TIME) {
	    // Add the portal with a teleporter that teleports down to (about) the position of this entity
	    WorldGenStructure portal = new WorldGenStructure("nexus/nexus_portal") {
		@Override
		protected void handleDataMarker(String function, BlockPos pos, World worldIn, java.util.Random rand) {
		    if (function.startsWith("teleporter")) {
			world.setBlockState(pos, ModBlocks.NEXUS_TELEPORTER.getDefaultState());
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity instanceof TileEntityTeleporter) {
			    ((TileEntityTeleporter) tileentity).setRelTeleportPos(new Vec3d(new BlockPos(EntityWhiteMonolith.this).west(3).down().subtract(pos)));
			}
		    }
		};
	    };
	    BlockPos size = portal.getSize(world);
	    BlockPos pos = new BlockPos(this.posX, NexusToOverworldTeleporter.yPortalOffset - 2, this.posZ).subtract(new BlockPos(size.getX() * 0.5f, 0, size.getZ() * 0.5f));
	    portal.generateStructure(world, pos, Rotation.NONE);
	    
	    // The teleport location into the sky
	    BlockPos teleportToPos = pos.add(new BlockPos(3, 2, 3));
	    BlockPos relativeTeleport = teleportToPos.subtract(new BlockPos(this).down());
	    new WorldGenNexusTeleporter(new Vec3d(relativeTeleport)).generate(world, rand, new BlockPos(this).add(new BlockPos(0, -1, -1)), Rotation.NONE);
	    
	    this.setDead();
	}

	BlockPos random = new BlockPos(ModRandom.randVec().scale(10).add(getPositionVector()));
	if (world.getBlockState(random).getBlock().equals(ModBlocks.MAELSTROM_BRICKS) || world.getBlockState(random).getBlock().equals(Blocks.OBSIDIAN)) {
	    world.setBlockState(random, Blocks.QUARTZ_BLOCK.getDefaultState());
	}
    }

    @Override
    public void handleStatusUpdate(byte id) {
	if (id == ModUtils.PARTICLE_BYTE) {
	    ModUtils.performNTimes(5, (i) -> {
		this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + ModRandom.getFloat(5),
			NexusToOverworldTeleporter.yPortalOffset + ModRandom.getFloat(5), this.posZ + ModRandom.getFloat(5), 0, 0, 0);
	    });
	}
	else if (id == ModUtils.SECOND_PARTICLE_BYTE) {
	    ParticleManager.spawnFirework(world, getPositionVector().add(ModRandom.randVec().scale(2)).add(ModUtils.yVec(2)), ModColors.WHITE, new Vec3d(0, 2.0, 0));
	    ParticleManager.spawnFirework(world, getPositionVector().add(ModRandom.randVec().scale(2)).add(ModUtils.yVec(2)), ModColors.YELLOW, new Vec3d(0, 2.0, 0));
	    Vec3d pos = ModRandom.randVec().scale(2).add(ModUtils.yVec(2));
	    world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.x, pos.y, pos.z, 0, 2.0f, 0);
	}
	super.handleStatusUpdate(id);
    }

    // Cannot be attacked
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
	return false;
    }

    @Override
    protected boolean canDespawn() {
	return false;
    }
}
